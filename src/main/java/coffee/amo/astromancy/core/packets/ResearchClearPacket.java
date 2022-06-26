package coffee.amo.astromancy.core.packets;

import coffee.amo.astromancy.client.research.ClientResearchHolder;
import coffee.amo.astromancy.client.systems.ClientConstellationHolder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.LogicalSidedProvider;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;

import java.util.Optional;
import java.util.function.Supplier;

public class ResearchClearPacket {
    public static void encode(ResearchClearPacket packet, FriendlyByteBuf buffer){
    }

    public static ResearchClearPacket decode(FriendlyByteBuf buffer) {
        return new ResearchClearPacket();
    }

public static void handle(ResearchClearPacket packet, Supplier<NetworkEvent.Context> contextSupplier) {
        contextSupplier.get().enqueueWork(() -> {
            NetworkEvent.Context ctx = contextSupplier.get();
            LogicalSide sideReceived = ctx.getDirection().getReceptionSide();
            if (sideReceived != LogicalSide.CLIENT) {
                return;
            }
            Optional<Level> clientWorld = LogicalSidedProvider.CLIENTWORLD.get(sideReceived);
            if (clientWorld.isEmpty()) {
                return;
            }
            ClientResearchHolder.research.clear();
            System.out.println("Cleared star data");
        });
        contextSupplier.get().setPacketHandled(true);
    }
}
