package coffee.amo.astromancy.core.util;

import coffee.amo.astromancy.Astromancy;
import coffee.amo.astromancy.core.handlers.AstromancyPacketHandler;
import coffee.amo.astromancy.core.packets.StarDataPacket;
import coffee.amo.astromancy.core.systems.glyph.Glyph;
import coffee.amo.astromancy.core.systems.stars.Star;
import coffee.amo.astromancy.core.systems.stars.classification.ConstellationInstance;
import coffee.amo.astromancy.core.systems.stars.classification.Constellations;
import com.google.common.collect.Lists;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.levelgen.XoroshiroRandomSource;
import net.minecraft.world.level.levelgen.synth.SimplexNoise;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.server.ServerLifecycleHooks;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class StarSavedData extends SavedData {
    private List<ConstellationInstance> constellationInstanceList = new ArrayList<>();
    protected Random random = new Random();

    public StarSavedData() {
        List<Glyph> shuffledGlyph = Lists.newArrayList(Glyph.values());
        shuffledGlyph.remove(Glyph.EMPTY);
        Collections.shuffle(shuffledGlyph);
        for(Constellations c : Constellations.values()){
            constellationInstanceList.add(new ConstellationInstance(c));
        }
        AtomicInteger i = new AtomicInteger();
        shuffledGlyph.subList(0, constellationInstanceList.size()).forEach(glyph -> {
            constellationInstanceList.get(i.get()).setAttunedGlyph(glyph);
            constellationInstanceList.get(i.get()).setOffset(random.nextFloat(360));
            constellationInstanceList.get(i.get()).setDaysVisible(random.nextInt(6) + 1);
            constellationInstanceList.get(i.get()).setNoise(new SimplexNoise(new XoroshiroRandomSource((long) (constellationInstanceList.get(i.get()).getOffset() * 100f))));
            i.getAndIncrement();
        });
        Star sun = new Star(5200);
        sun.setName("The Sun");
        constellationInstanceList.get(8).addStar(sun, 10, 10);
        constellationInstanceList.forEach(c -> {
            Astromancy.LOGGER.info(c.getConstellation().getName() + ": " + c.getAttunedGlyph().name());
            Astromancy.LOGGER.debug(c.getConstellation().getName() + ": " + c.getAttunedGlyph().name() + ", Offset: " + c.getOffset() + ", Visible every " + c.getDaysVisible() + " days.");
        });
    }

    public static StarSavedData get(MinecraftServer server) {
        return server.overworld().getDataStorage().computeIfAbsent(StarSavedData::load, StarSavedData::new, "astromancy_stars");
    }

    public static StarSavedData get() {
        return get(ServerLifecycleHooks.getCurrentServer());
    }

    // TODO: nest the stars in Quadrant -> Constellation -> Star and dont save the Quadrants or Constellations in the Star NBT
    public CompoundTag save(CompoundTag pCompoundTag) {
        ListTag tag = new ListTag();
        for (ConstellationInstance constellationInstance : constellationInstanceList) {
            tag.add(constellationInstance.toNbt());
        }
        pCompoundTag.put("constellations", tag);
        return pCompoundTag;
    }

    // TODO: Read the jank from above properly
    public static StarSavedData load(CompoundTag pCompoundTag) {
        ListTag tag = pCompoundTag.getList("constellations", Tag.TAG_COMPOUND);
        List<ConstellationInstance> constList = new ArrayList<>();
        for (Tag t : tag) {
            constList.add(ConstellationInstance.fromNbt((CompoundTag) t));
        }
        StarSavedData starSavedData = new StarSavedData();
        starSavedData.constellationInstanceList = constList;
        return starSavedData;
    }

    public void addStar(Star star, int x, int y, Constellations constel) {
        for (ConstellationInstance constellationInstance : constellationInstanceList) {
            if (constellationInstance.getConstellation() == constel) {
                constellationInstance.addStar(star, x, y);
                AstromancyPacketHandler.INSTANCE.send(PacketDistributor.ALL.noArg(), new StarDataPacket(constellationInstanceList));
                return;
            }
        }
    }

    public Star getStar(int x, int y, Constellations constel) {
        for (ConstellationInstance constellationInstance : constellationInstanceList) {
            if (constellationInstance.getConstellation() == constel) {
                return constellationInstance.getStar(x, y);
            }
        }
        return null;
    }

    public ConstellationInstance getConstellationInstance(Constellations constel) {
        for (ConstellationInstance constellationInstance : constellationInstanceList) {
            if (constellationInstance.getConstellation() == constel) {
                return constellationInstance;
            }
        }
        return null;
    }

    public List<ConstellationInstance> getConstellationInstances() {
        return constellationInstanceList;
    }

    public List<Star> getStars(Constellations constel) {
        List<Star> stars = new ArrayList<>();
        for (ConstellationInstance constellationInstance : constellationInstanceList) {
            if (constellationInstance.getConstellation() == constel) {
                for (int x = 0; x < 20; x++) {
                    for (int y = 0; y < 20; y++) {
                        if (constellationInstance.getStar(x, y) != null) {
                            stars.add(constellationInstance.getStar(x, y));
                        }
                    }
                }
            }
        }
        return stars;
    }

    public boolean doesStarExist(Star star) {
        for (ConstellationInstance constellationInstance : constellationInstanceList) {
            if (constellationInstance.getStar(star) != null) {
                return true;
            }
        }
        return false;
    }

    public Star findStarFromName(String name) {
        for (ConstellationInstance constellationInstance : constellationInstanceList) {
            for (int x = 0; x < 20; x++) {
                for (int y = 0; y < 20; y++) {
                    if (constellationInstance.getStar(x, y) != null) {
                        if (constellationInstance.getStar(x, y).getName().equals(name)) {
                            return constellationInstance.getStar(x, y);
                        }
                    }
                }
            }
        }
        return null;
    }

    public ConstellationInstance findConstellationFromStar(Star star) {
        for (ConstellationInstance constellationInstance : constellationInstanceList) {
            if (constellationInstance.getStar(star) != null) {
                return constellationInstance;
            }
        }
        return null;
    }

    @Override
    public boolean isDirty() {
        return true;
    }
}
