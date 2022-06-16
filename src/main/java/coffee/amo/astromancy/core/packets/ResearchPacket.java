package coffee.amo.astromancy.core.packets;

import coffee.amo.astromancy.client.research.ClientResearchHolder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ResearchPacket {
    public final String researchId;

    public ResearchPacket(String researchId) {
        this.researchId = researchId;
    }

    public static void encode(ResearchPacket packet, FriendlyByteBuf buffer){
        buffer.writeUtf(packet.researchId);
    }

    public static ResearchPacket decode(FriendlyByteBuf buffer){
        return new ResearchPacket(buffer.readUtf());
    }

    public static void handle(ResearchPacket packet, Supplier<NetworkEvent.Context> contextSupplier){
        contextSupplier.get().enqueueWork(() -> {
            ClientResearchHolder.addResearch(packet.researchId);
        });
        contextSupplier.get().setPacketHandled(true);
    }
}
