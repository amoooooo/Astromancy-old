package coffee.amo.astromancy.core.systems.stars.classification;

import coffee.amo.astromancy.core.systems.stars.Star;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;

import java.util.ArrayList;
import java.util.List;

public class ConstellationInstance {
    public Constellation constellation;
    public Star[][] stars;
    public ConstellationInstance(Constellation constellation, Star[][] stars){
        this.constellation = constellation;
        this.stars = stars;
    }

    public CompoundTag toNbt(){
        CompoundTag tag = new CompoundTag();
        tag.putString("name", constellation.name);
        List<CompoundTag> starTags = new ArrayList<>();
        for(Star[] starRow : stars){
            for(Star star : starRow){
                starTags.add(star.toNbt());
            }
        }
        ListTag listTag = new ListTag();
        listTag.addAll(starTags);
        tag.put("stars", listTag);
        return tag;
    }

    public static ConstellationInstance fromNbt(CompoundTag tag) {
        Constellation constellation = Constellations.findByName(tag.getString("name"));
        ListTag starTags = tag.getList("stars", Tag.TAG_COMPOUND);
        Star[][] stars = new Star[10][10];
        for(Tag starTag : starTags){
            Star star = Star.fromNbt((CompoundTag) starTag);
            stars[star.getQuadrantCoordinates().getFirst()][star.getQuadrantCoordinates().getSecond()] = star;
        }
        return new ConstellationInstance(constellation, stars);
    }
}
