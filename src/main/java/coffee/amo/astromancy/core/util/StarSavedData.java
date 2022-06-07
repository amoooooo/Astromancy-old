package coffee.amo.astromancy.core.util;

import coffee.amo.astromancy.core.systems.stars.Star;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraftforge.server.ServerLifecycleHooks;

import java.util.ArrayList;
import java.util.List;

public class StarSavedData extends SavedData {
    private final List<Star> stars;

    public StarSavedData() {
        this.stars = new ArrayList<>();
    }

    public CompoundTag save(CompoundTag pCompoundTag) {
        ListTag tag = new ListTag();
        for (Star star : stars) {
            tag.add(star.toNbt());
        }
        pCompoundTag.put("stars", tag);
        return pCompoundTag;
    }

    public StarSavedData load(CompoundTag pCompoundTag) {
        ListTag tag = pCompoundTag.getList("stars", Tag.TAG_COMPOUND);
        for (int i = 0; i < tag.size(); i++) {
            CompoundTag starTag = tag.getCompound(i);
            Star star = Star.fromNbt(starTag);
            stars.add(star);
        }
        return this;
    }

    public static StarSavedData get(){
        return ServerLifecycleHooks.getCurrentServer().getLevel(Level.OVERWORLD).getDataStorage().computeIfAbsent(p -> new StarSavedData().load(p), StarSavedData::new, "astromancy_stars");
    }

    public void addStar(Star star) {
        stars.add(star);
        this.setDirty();
    }

    public void removeStar(Star star) {
        stars.remove(star);
        this.setDirty();
    }

    public List<Star> getStars() {
        return stars;
    }

    public boolean containsStar(Star star) {
        return stars.contains(star);
    }
    public Star findStar(String uuid){
        for(Star star : stars){
            if(star.uuid.toString().equals(uuid)){
                return star;
            }
        }
        return null;
    }
}
