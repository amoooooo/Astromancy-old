package coffee.amo.astromancy.core.util;

import coffee.amo.astromancy.core.handlers.AstromancyPacketHandler;
import coffee.amo.astromancy.core.packets.StarDataPacket;
import coffee.amo.astromancy.core.systems.stars.Star;
import coffee.amo.astromancy.core.systems.stars.classification.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.server.ServerLifecycleHooks;

import java.util.ArrayList;
import java.util.List;

public class StarSavedData extends SavedData {
    private static List<Quadrant> quadrants = List.of(
            Quadrants.STARS,
            Quadrants.PENTACLES,
            Quadrants.SWORDS,
            Quadrants.WANDS
    );

    public StarSavedData() {
    }

    public static StarSavedData get() {
        return ServerLifecycleHooks.getCurrentServer().getLevel(Level.OVERWORLD).getDataStorage().computeIfAbsent(p -> new StarSavedData().load(p), StarSavedData::new, "astromancy_stars");
    }

    // TODO: nest the stars in Quadrant -> Constellation -> Star and dont save the Quadrants or Constellations in the Star NBT
    public CompoundTag save(CompoundTag pCompoundTag) {
        ListTag tag = new ListTag();
        for (Quadrant quadrant : quadrants) {
            tag.add(quadrant.toNbt());
        }
        pCompoundTag.put("quadrants", tag);
        return pCompoundTag;
    }

    // TODO: Read the jank from above properly
    public StarSavedData load(CompoundTag pCompoundTag) {
        ListTag tag = pCompoundTag.getList("stars", Tag.TAG_COMPOUND);
        for (int i = 0; i < tag.size(); i++) {
            CompoundTag starTag = tag.getCompound(i);
            Quadrant quadrant = Quadrant.fromNbt(starTag);
            for(Quadrant q : quadrants){
                if(q.getName().equals(quadrant.getName())){
                    quadrants.set(i, quadrant);
                }
            }
        }
        return this;
    }

    public void addQuadrant(Quadrant quadrant) {
        quadrants.add(quadrant);
        this.setDirty();
    }

    public void addStar(Star star) {
        for(Quadrant q : quadrants){
            if(q.equals(star.getQuadrant())){
                q.addStar(star);
                this.setDirty();
                AstromancyPacketHandler.INSTANCE.send(PacketDistributor.ALL.noArg(), new StarDataPacket(getStars()));
                return;
            }
        }
    }

    public List<Constellation> getConstellations() {
        List<Constellation> constellations = new ArrayList<>();
        for (Quadrant quadrant : quadrants) {
            constellations.addAll(quadrant.constellations);
        }
        return constellations;
    }

    public void removeQuadrant(Quadrant quadrant) {
        quadrants.remove(quadrant);
        this.setDirty();
    }

    public List<Quadrant> getStars() {
        return quadrants;
    }

    public boolean containsStar(Star star) {
        return quadrants.stream().anyMatch(quadrant -> quadrant.containsStar(star));
    }

    public Star findStarFromName(String name) {
        return quadrants.stream().flatMap(quadrant -> quadrant.getStars().stream()).filter(star -> star.getName().equals(name)).findFirst().orElse(null);
    }
}
