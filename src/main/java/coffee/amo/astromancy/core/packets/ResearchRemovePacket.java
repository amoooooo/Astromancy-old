package coffee.amo.astromancy.core.packets;

import coffee.amo.astromancy.client.research.ClientResearchHolder;
import coffee.amo.astromancy.core.systems.research.ResearchObject;
import coffee.amo.astromancy.core.systems.research.ResearchType;
import coffee.amo.astromancy.core.systems.research.ResearchTypeRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.List;
import java.util.function.Supplier;

public class ResearchRemovePacket extends ResearchPacket{
    public ResearchRemovePacket(String researchId) {
        super(researchId, true);
    }

    public static void encode(ResearchRemovePacket packet, FriendlyByteBuf buffer){
        buffer.writeUtf(packet.researchId);
    }

    public static ResearchRemovePacket decode(FriendlyByteBuf buffer){
        return new ResearchRemovePacket(buffer.readUtf());
    }

    public static void handle(ResearchRemovePacket packet, Supplier<NetworkEvent.Context> contextSupplier){
        contextSupplier.get().enqueueWork(() -> {
            List<ResearchType> researchObjects = ResearchTypeRegistry.RESEARCH_TYPES.get().getValues().stream().toList();
            for (ResearchType type : researchObjects) {
                ResearchObject object = (ResearchObject) type;
                if (object.identifier.equals(packet.researchId)) {
                    ClientResearchHolder.rmeoveResearch(object);
                }
            }
        });
        contextSupplier.get().setPacketHandled(true);
    }
}
