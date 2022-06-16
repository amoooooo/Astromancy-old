package coffee.amo.astromancy.core.packets;

import coffee.amo.astromancy.client.research.ClientResearchHolder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ResearchRemovePacket extends ResearchPacket{
    public ResearchRemovePacket(String researchId) {
        super(researchId);
    }

    public static void encode(ResearchRemovePacket packet, FriendlyByteBuf buffer){
        buffer.writeUtf(packet.researchId);
    }

    public static ResearchRemovePacket decode(FriendlyByteBuf buffer){
        return new ResearchRemovePacket(buffer.readUtf());
    }

    public static void handle(ResearchRemovePacket packet, Supplier<NetworkEvent.Context> contextSupplier){
        contextSupplier.get().enqueueWork(() -> {
            ClientResearchHolder.removeResearch(packet.researchId);
        });
        contextSupplier.get().setPacketHandled(true);
    }
}
