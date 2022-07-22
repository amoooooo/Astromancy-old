package coffee.amo.astromancy.core.packets;

import coffee.amo.astromancy.client.research.ClientResearchHolder;
import coffee.amo.astromancy.core.systems.research.ResearchObject;
import coffee.amo.astromancy.core.systems.research.ResearchProgress;
import coffee.amo.astromancy.core.systems.research.ResearchType;
import coffee.amo.astromancy.core.systems.research.ResearchTypeRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.network.NetworkEvent;

import java.util.List;
import java.util.function.Supplier;

public class ClientboundResearchPacket {
    public final String researchId;
    public final boolean silent;
    public final boolean complete;
    public final int ordinal;

    public ClientboundResearchPacket(String researchId, boolean silent, boolean complete, int ordinal) {
        this.researchId = researchId;
        this.silent = silent;
        this.complete = complete;
        this.ordinal = ordinal;
    }

    public static void encode(ClientboundResearchPacket packet, FriendlyByteBuf buffer){
        buffer.writeUtf(packet.researchId);
        buffer.writeBoolean(packet.silent);
        buffer.writeBoolean(packet.complete);
        buffer.writeInt(packet.ordinal);
    }

    public static ClientboundResearchPacket decode(FriendlyByteBuf buffer){
        return new ClientboundResearchPacket(buffer.readUtf(), buffer.readBoolean(), buffer.readBoolean(), buffer.readInt());
    }

    public static void handle(ClientboundResearchPacket packet, Supplier<NetworkEvent.Context> contextSupplier){
        contextSupplier.get().enqueueWork(() -> {
            if(!ClientResearchHolder.contains(packet.researchId)){
                List<ResearchType> researchObjects = ResearchTypeRegistry.RESEARCH_TYPES.get().getValues().stream().toList();
                for (ResearchType type : researchObjects) {
                    ResearchObject object = (ResearchObject) type;
                    if(object.identifier.equals(packet.researchId)){
                        ClientResearchHolder.addResearch(object, ResearchProgress.values()[packet.ordinal]);
                        break;
                    }
                }
            }
            if(!packet.silent){
                Minecraft.getInstance().player.playNotifySound(SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.PLAYERS, 1.0F, 1.0F);
            }
        });
        contextSupplier.get().setPacketHandled(true);
    }
}
