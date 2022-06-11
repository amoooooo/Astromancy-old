package coffee.amo.astromancy.core.systems.stars.classification;

import coffee.amo.astromancy.core.systems.stars.Star;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Constellation {
    // Star names are the quadrant suits + number
    public Star[][] starsByQuadrant;
    public String name;
    public int height;
    public Constellation(String name, int height) {
        starsByQuadrant = new Star[10][10];
        this.name = name;
    }

    public CompoundTag toNbt(){
        CompoundTag tag = new CompoundTag();
        tag.putString("name", name);
        tag.putInt("height", height);
        List<CompoundTag> starTags = new ArrayList<>();
        for(Star[] stars : starsByQuadrant){
            for(Star star : stars){
                if(star != null){
                    starTags.add(star.toNbt());
                }
            }
        }
        ListTag listTag = new ListTag();
        listTag.addAll(starTags);
        tag.put("stars", listTag);
        return tag;
    }

    public static Constellation fromNbt(CompoundTag tag){
        Constellation constellation = new Constellation(tag.getString("name"), tag.getInt("height"));
        List<Tag> starTags = tag.getList("stars", Tag.TAG_COMPOUND).stream().toList();
        for(Tag starTag : starTags){
            Star star = Star.fromNbt((CompoundTag) starTag);
            constellation.starsByQuadrant[star.getQuadrantCoordinates().getFirst()][star.getQuadrantCoordinates().getSecond()] = star;
        }
        return constellation;
    }

    public boolean containsStar(Star star) {
        return starsByQuadrant[star.getQuadrantCoordinates().getFirst()][star.getQuadrantCoordinates().getSecond()] != null;
    }

    public Collection<Star> getStars() {
        List<Star> stars = new ArrayList<>();
        for(Star[] quadrant : starsByQuadrant){
            for(Star star : quadrant){
                if(star != null){
                    stars.add(star);
                }
            }
        }
        return stars;
    }

    public int getHeight() {
        return height;
    }

    public void addStar(Star star) {
        starsByQuadrant[star.getQuadrantCoordinates().getFirst()][star.getQuadrantCoordinates().getSecond()] = star;
    }
}
