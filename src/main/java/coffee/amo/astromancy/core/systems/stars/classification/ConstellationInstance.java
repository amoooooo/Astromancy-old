package coffee.amo.astromancy.core.systems.stars.classification;

import coffee.amo.astromancy.core.systems.stars.Star;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;

import java.util.ArrayList;
import java.util.List;

public class ConstellationInstance {
    private final Constellations constellation;
    private Star[][] stars = new Star[20][20];

    public ConstellationInstance(Constellations constellation) {
        this.constellation = constellation;
    }

    public void addStar(Star star, int x, int y) {
        stars[x][y] = star;
    }

    public Star getStar(int x, int y) {
        return stars[x][y];
    }

    public Constellations getConstellation() {
        return constellation;
    }

    public Star[][] getStars() {
        return stars;
    }

    public void setStars(Star[][] stars) {
        this.stars = stars;
    }

    public CompoundTag toNbt() {
        CompoundTag tag = new CompoundTag();
        tag.putString("constellation", constellation.name());
        ListTag starTag = new ListTag();
        // add stars to the list, each star is its own compound tag and the key is the quadrant coordinates
        for (int x = 0; x < 20; x++) {
            ListTag yTag = new ListTag();
            starTag.add(x, yTag);
            for (int y = 0; y < 20; y++) {
                if (stars[x][y] != null) {
                    yTag.add(y, stars[x][y].toNbt());
                }
            }
        }
        tag.put("stars", starTag);
        return tag;
    }

    public static ConstellationInstance fromNbt(CompoundTag tag){
        String constellationName = tag.getString("constellation");
        Star[][] starsByQuadrant = new Star[20][20];
        ListTag starTag = tag.getList("stars", Tag.TAG_LIST);
        for (int x = 0; x < starTag.size(); x++) {
            ListTag yList = starTag.getList(x);
            for (int y = 0; y < yList.size(); y++) {
                starsByQuadrant[x][y] = Star.fromNbt(yList.getCompound(y));
            }
        }
        ConstellationInstance constellationInstance = new ConstellationInstance(Constellations.valueOf(constellationName));
        constellationInstance.setStars(starsByQuadrant);
        return constellationInstance;
    }

    public Star getStar(Star star) {
        for (int x = 0; x < 20; x++) {
            for (int y = 0; y < 20; y++) {
                if (stars[x][y] != null && stars[x][y].equals(star)) {
                    return stars[x][y];
                }
            }
        }
        return null;
    }
}
