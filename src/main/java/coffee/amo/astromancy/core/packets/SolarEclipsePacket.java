package coffee.amo.astromancy.core.packets;

import coffee.amo.astromancy.core.handlers.SolarEclipseHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.LogicalSidedProvider;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;

import java.util.Optional;
import java.util.function.Supplier;

public class SolarEclipsePacket {
    public boolean bool;
    public boolean initialized = true;

    public SolarEclipsePacket(boolean bool){
        this.bool = bool;
        initialized = true;
    }

    public SolarEclipsePacket(){
        initialized = false;
    }

    public static void encode(SolarEclipsePacket packet, FriendlyByteBuf buffer){
        buffer.writeBoolean(packet.bool);
    }

    public static SolarEclipsePacket decode(FriendlyByteBuf buffer){
        return new SolarEclipsePacket(buffer.readBoolean());
    }

    public static void handle(SolarEclipsePacket packet, Supplier<NetworkEvent.Context> contextSupplier){
        contextSupplier.get().enqueueWork(() -> {
            NetworkEvent.Context ctx = contextSupplier.get();
            LogicalSide sideReceived = ctx.getDirection().getReceptionSide();
            if (sideReceived != LogicalSide.CLIENT) {
                return;
            }
            if (!packet.initialized) {
                return;
            }
            Optional<Level> clientWorld = LogicalSidedProvider.CLIENTWORLD.get(sideReceived);
            if (clientWorld.isEmpty()) {
                return;
            }
            SolarEclipseHandler.solarEclipseEnabledClient = packet.bool;
        });
        contextSupplier.get().setPacketHandled(true);
    }
}
