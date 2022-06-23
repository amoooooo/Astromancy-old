package coffee.amo.astromancy.core.handlers;

import coffee.amo.astromancy.Astromancy;
import coffee.amo.astromancy.core.packets.*;
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

        INSTANCE.registerMessage(ID++, JarUpdatePacket.class, JarUpdatePacket::encode,
                JarUpdatePacket::decode,
                JarUpdatePacket::handle);

        INSTANCE.registerMessage(ID++, ResearchPacket.class, ResearchPacket::encode,
                ResearchPacket::decode,
                ResearchPacket::handle);

        INSTANCE.registerMessage(ID++, ResearchRemovePacket.class, ResearchRemovePacket::encode,
                ResearchRemovePacket::decode,
                ResearchRemovePacket::handle);

        INSTANCE.registerMessage(ID++, ResearchNotePacket.class, ResearchNotePacket::encode,
                ResearchNotePacket::decode,
                ResearchNotePacket::handle);

        INSTANCE.registerMessage(ID++, BookStatePacket.class, BookStatePacket::encode,
                BookStatePacket::decode,
                BookStatePacket::handle);

        INSTANCE.registerMessage(ID++, ItemSyncPacket.class, ItemSyncPacket::encode,
                ItemSyncPacket::decode,
                ItemSyncPacket::handle);

        INSTANCE.registerMessage(ID++, JarSyncPacket.class, JarSyncPacket::encode,
                JarSyncPacket::decode,
                JarSyncPacket::handle);

        INSTANCE.registerMessage(ID++, SyncLevelEventPacket.class, SyncLevelEventPacket::encode,
                SyncLevelEventPacket::decode,
                SyncLevelEventPacket::handle);

        INSTANCE.registerMessage(ID++, ServerboundResearchPacket.class, ServerboundResearchPacket::encode,
                ServerboundResearchPacket::decode,
                ServerboundResearchPacket::handle);
    }
}
