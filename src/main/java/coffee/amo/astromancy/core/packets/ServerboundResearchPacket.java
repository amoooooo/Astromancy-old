package coffee.amo.astromancy.core.packets;

import coffee.amo.astromancy.core.handlers.PlayerResearchHandler;
import coffee.amo.astromancy.core.systems.research.ResearchObject;
import coffee.amo.astromancy.core.systems.research.ResearchTypeRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ServerboundResearchPacket {
    public final String researchId;

    public ServerboundResearchPacket(String researchId) {
        this.researchId = researchId;
    }

    public static void encode(ServerboundResearchPacket packet, FriendlyByteBuf buffer){
        buffer.writeUtf(packet.researchId);
    }

    public static ServerboundResearchPacket decode(FriendlyByteBuf buffer){
        return new ServerboundResearchPacket(buffer.readUtf());
    }

    public static void handle(ServerboundResearchPacket packet, Supplier<NetworkEvent.Context> contextSupplier){
        contextSupplier.get().enqueueWork(() -> {
            contextSupplier.get().getSender().getCapability(PlayerResearchHandler.RESEARCH_CAPABILITY, null).ifPresent(research -> {
                ResearchTypeRegistry.RESEARCH_TYPES.get().getValues().forEach(s -> {
                    ResearchObject object = (ResearchObject) s;
                    if(object.identifier.equals(packet.researchId)){
                        research.addResearch(contextSupplier.get().getSender(), object);
                    }
                });
            });
        });
        contextSupplier.get().setPacketHandled(true);
    }
}
