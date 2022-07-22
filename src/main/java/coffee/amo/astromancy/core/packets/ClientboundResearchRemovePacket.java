package coffee.amo.astromancy.core.packets;

import coffee.amo.astromancy.client.research.ClientResearchHolder;
import coffee.amo.astromancy.core.systems.research.ResearchObject;
import coffee.amo.astromancy.core.systems.research.ResearchProgress;
import coffee.amo.astromancy.core.systems.research.ResearchTypeRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ClientboundResearchRemovePacket extends ClientboundResearchPacket {
    public ClientboundResearchRemovePacket(String researchId) {
        super(researchId, true, false, ResearchProgress.IN_PROGRESS.ordinal());
    }

    public static void encode(ClientboundResearchRemovePacket packet, FriendlyByteBuf buffer){
        buffer.writeUtf(packet.researchId);
    }

    public static ClientboundResearchRemovePacket decode(FriendlyByteBuf buffer){
        return new ClientboundResearchRemovePacket(buffer.readUtf());
    }

    public static void handle(ClientboundResearchRemovePacket packet, Supplier<NetworkEvent.Context> contextSupplier){
        contextSupplier.get().enqueueWork(() -> {
            ResearchTypeRegistry.RESEARCH_TYPES.get().getValues().forEach(s -> {
                ResearchObject object = (ResearchObject) s;
                if(object.identifier.equals(packet.researchId)){
                    ClientResearchHolder.removeResearch(object);
                }
            });
        });
        contextSupplier.get().setPacketHandled(true);
    }
}
