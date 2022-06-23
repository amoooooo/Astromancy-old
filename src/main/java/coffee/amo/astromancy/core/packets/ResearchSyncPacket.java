package coffee.amo.astromancy.core.packets;

import coffee.amo.astromancy.client.research.ClientResearchHolder;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ResearchSyncPacket {
    public final String researchId;
    public final boolean silent;

    public ResearchSyncPacket(String researchId, boolean silent) {
        this.researchId = researchId;
        this.silent = silent;
    }

    public static void encode(ResearchSyncPacket packet, FriendlyByteBuf buffer){
        buffer.writeUtf(packet.researchId);
        buffer.writeBoolean(packet.silent);
    }

    public static ResearchSyncPacket decode(FriendlyByteBuf buffer){
        return new ResearchSyncPacket(buffer.readUtf(), buffer.readBoolean());
    }

    public static void handle(ResearchSyncPacket packet, Supplier<NetworkEvent.Context> contextSupplier){
        contextSupplier.get().enqueueWork(() -> {
            if(!ClientResearchHolder.contains(packet.researchId)){
                ClientResearchHolder.addResearch(packet.researchId);
            } else {
                ClientResearchHolder.completeResearch(packet.researchId);
            }
            if(!packet.silent){
                Minecraft.getInstance().player.playNotifySound(SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.PLAYERS, 1.0F, 1.0F);
            }
        });
        contextSupplier.get().setPacketHandled(true);
    }
}
