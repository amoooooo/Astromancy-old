package coffee.amo.astromancy.core.packets;

import coffee.amo.astromancy.client.research.ClientResearchHolder;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.SoundType;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ResearchPacket {
    public final String researchId;
    public final boolean silent;

    public ResearchPacket(String researchId, boolean silent) {
        this.researchId = researchId;
        this.silent = silent;
    }

    public static void encode(ResearchPacket packet, FriendlyByteBuf buffer){
        buffer.writeUtf(packet.researchId);
        buffer.writeBoolean(packet.silent);
    }

    public static ResearchPacket decode(FriendlyByteBuf buffer){
        return new ResearchPacket(buffer.readUtf(), buffer.readBoolean());
    }

    public static void handle(ResearchPacket packet, Supplier<NetworkEvent.Context> contextSupplier){
        contextSupplier.get().enqueueWork(() -> {
            if(!ClientResearchHolder.getResearch().contains(packet.researchId)){
                ClientResearchHolder.addResearch(packet.researchId);
            }
            if(!packet.silent){
                Minecraft.getInstance().player.playNotifySound(SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.PLAYERS, 1.0F, 1.0F);
            }
        });
        contextSupplier.get().setPacketHandled(true);
    }
}
