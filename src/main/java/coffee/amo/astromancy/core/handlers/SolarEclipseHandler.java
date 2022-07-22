package coffee.amo.astromancy.core.handlers;

import coffee.amo.astromancy.core.packets.SolarEclipsePacket;
import coffee.amo.astromancy.core.util.StarSavedData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PacketDistributor;

public class SolarEclipseHandler {

    public static boolean solarEclipseEnabledClient = false;

    public static boolean isEnabled(ServerLevel level) {
        return StarSavedData.get().isEclipseEnabled();
    }

    public static void setEnabled(ServerLevel level, boolean val) {
        StarSavedData.get().setEclipseEnabled(true);
    }

    public static int getDaysTil(ServerLevel level) {
        return StarSavedData.get().getDaysTilEclipse();
    }

    public static void setDaysTil(ServerLevel level, int val) {
        StarSavedData.get().setDaysTilEclipse(val);
    }

    public static float getSkyDarkenClient(float in) {
        return 0.05f;
    }

    public static Vec3 getDaySkyColor(Vec3 in) {
        return in.multiply(new Vec3(0.1, 0.1, 0.1));
    }

    public static Vec3 getCloudColor(Vec3 in) {
        return in.multiply(new Vec3(0.1, 0.1, 0.1));
    }

    public static int getSkyDarken() {
        return 15;
    }

    public static Vec3 getOverworldFogColor(Vec3 in) {
        return in.multiply(new Vec3(.1, .1, .1));
    }
}
