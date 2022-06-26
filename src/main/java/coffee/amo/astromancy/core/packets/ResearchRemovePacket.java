package coffee.amo.astromancy.core.packets;

import coffee.amo.astromancy.client.research.ClientResearchHolder;
import coffee.amo.astromancy.core.handlers.PlayerResearchHandler;
import coffee.amo.astromancy.core.systems.research.ResearchObject;
import coffee.amo.astromancy.core.systems.research.ResearchProgress;
import coffee.amo.astromancy.core.systems.research.ResearchTypeRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ResearchRemovePacket extends ResearchPacket{
    public ResearchRemovePacket(String researchId) {
        super(researchId, true, false, ResearchProgress.IN_PROGRESS.ordinal());
    }

    public static void encode(ResearchRemovePacket packet, FriendlyByteBuf buffer){
        buffer.writeUtf(packet.researchId);
    }

    public static ResearchRemovePacket decode(FriendlyByteBuf buffer){
        return new ResearchRemovePacket(buffer.readUtf());
    }

    public static void handle(ResearchRemovePacket packet, Supplier<NetworkEvent.Context> contextSupplier){
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
