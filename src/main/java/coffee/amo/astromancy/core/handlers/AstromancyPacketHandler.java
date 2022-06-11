package coffee.amo.astromancy.core.handlers;

import coffee.amo.astromancy.Astromancy;
import coffee.amo.astromancy.core.packets.ArmillarySpherePacket;
import coffee.amo.astromancy.core.packets.SolarEclipsePacket;
import coffee.amo.astromancy.core.packets.StarDataPacket;
import coffee.amo.astromancy.core.packets.StarPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class AstromancyPacketHandler {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(Astromancy.MODID, "astromancy"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );
    private static int ID = 0;

    public static void init() {
        INSTANCE.registerMessage(ID++, ArmillarySpherePacket.class, ArmillarySpherePacket::encode,
                ArmillarySpherePacket::decode,
                ArmillarySpherePacket::handle);

        INSTANCE.registerMessage(ID++, StarPacket.class, StarPacket::encode,
                StarPacket::decode, StarPacket::handle);

        INSTANCE.registerMessage(ID++, SolarEclipsePacket.class, SolarEclipsePacket::encode,
                SolarEclipsePacket::decode,
                SolarEclipsePacket::handle);

        INSTANCE.registerMessage(ID++, StarDataPacket.class, StarDataPacket::encode,
                StarDataPacket::decode,
                StarDataPacket::handle);
    }
}
