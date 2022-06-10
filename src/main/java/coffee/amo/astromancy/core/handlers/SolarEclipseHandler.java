package coffee.amo.astromancy.core.handlers;

import coffee.amo.astromancy.core.packets.SolarEclipsePacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PacketDistributor;

public class SolarEclipseHandler {

    public static boolean solarEclipseEnabledClient = false;

    public static boolean isEnabled(ServerLevel level) {
        return level.getDataStorage().computeIfAbsent(SolarEclipseData::load, SolarEclipseData::create, "astromancy").isEnabled();
    }

    public static void setEnabled(ServerLevel level, boolean val) {
        level.getDataStorage().computeIfAbsent(SolarEclipseData::load, SolarEclipseData::create, "astromancy").setEnabled(val);
    }

    public static int getDaysTil(ServerLevel level) {
        return level.getDataStorage().computeIfAbsent(SolarEclipseData::load, SolarEclipseData::create, "astromancy").getDaysTil();
    }

    public static void setDaysTil(ServerLevel level, int val) {
        level.getDataStorage().computeIfAbsent(SolarEclipseData::load, SolarEclipseData::create, "astromancy").setDaysTil(val);
    }

    public static int getDaysSince(ServerLevel level) {
        return level.getDataStorage().computeIfAbsent(SolarEclipseData::load, SolarEclipseData::create, "astromancy").getDaysSince();
    }

    public static void setDaysSince(ServerLevel level, int val) {
        level.getDataStorage().computeIfAbsent(SolarEclipseData::load, SolarEclipseData::create, "astromancy").setDaysSince(val);
    }

    public static boolean getDayAdded(ServerLevel level) {
        return level.getDataStorage().computeIfAbsent(SolarEclipseData::load, SolarEclipseData::create, "astromancy").getDayAdded();
    }

    public static void setDayAdded(ServerLevel level, boolean val) {
        level.getDataStorage().computeIfAbsent(SolarEclipseData::load, SolarEclipseData::create, "astromancy").setDayAdded(val);
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

    public static class SolarEclipseData extends SavedData {
        private boolean eclipse = false;
        private int daysTil = 0;
        private int daysSince = 0;
        private boolean dayAdded = false;

        public static SolarEclipseData create() {
            return new SolarEclipseData();
        }

        public static SolarEclipseData load(CompoundTag tag) {
            SolarEclipseData data = new SolarEclipseData();
            data.eclipse = tag.getBoolean("eclipse");
            data.daysSince = tag.getInt("daysSince");
            data.daysTil = tag.getInt("daysTil");
            data.dayAdded = tag.getBoolean("dayAdded");
            return data;
        }

        public int getDaysTil() {
            return daysTil;
        }

        public void setDaysTil(int val) {
            daysTil = val;
        }

        public int getDaysSince() {
            return daysSince;
        }

        public void setDaysSince(int val) {
            daysSince = val;
        }

        public boolean isEnabled() {
            return eclipse;
        }

        public boolean getDayAdded() {
            return dayAdded;
        }

        public void setDayAdded(boolean val) {
            dayAdded = val;
        }

        public void setEnabled(boolean val) {
            eclipse = val;
            this.sendToClient();
            this.setDirty();
        }

        public void sendToClient() {
            AstromancyPacketHandler.INSTANCE.send(PacketDistributor.ALL.noArg(), new SolarEclipsePacket(eclipse));
        }

        /**
         * Used to save the {@code SavedData} to a {@code CompoundTag}
         *
         * @param pCompoundTag the {@code CompoundTag} to save the {@code SavedData} to
         */
        @Override
        public CompoundTag save(CompoundTag pCompoundTag) {
            pCompoundTag.putBoolean("eclipse", eclipse);
            pCompoundTag.putInt("daysSince", daysSince);
            pCompoundTag.putInt("daysTil", daysTil);
            pCompoundTag.putBoolean("dayAdded", dayAdded);
            return pCompoundTag;
        }
    }
}
