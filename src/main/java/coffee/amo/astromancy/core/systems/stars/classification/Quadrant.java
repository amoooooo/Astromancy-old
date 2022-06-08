package coffee.amo.astromancy.core.systems.stars.classification;

import coffee.amo.astromancy.core.systems.stars.Star;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;

import java.util.*;

public class Quadrant {
    public Direction direction;
    public String name;
    public List<Constellation> constellations = new ArrayList<>();
    public Quadrant(Direction direction, String name){
        this.direction = direction;
        this.name = name;
    }

    public void addConstellation(Constellation constellation){
        constellations.add(constellation);
    }

    public Quadrant addConstellations(Constellation... constellations){
        this.constellations.addAll(Arrays.asList(constellations));
        return this;
    }

    //toNbt
    public CompoundTag toNbt(){
        CompoundTag tag = new CompoundTag();
        tag.putString("name", name);
        tag.putString("direction", direction.name());
        List<CompoundTag> constellationTags = new ArrayList<>();
        for(Constellation constellation : constellations){
            constellationTags.add(constellation.toNbt());
        }
        ListTag listTag = new ListTag();
        listTag.addAll(constellationTags);
        tag.put("constellations", listTag);
        return tag;
    }

    //fromNbt
    public static Quadrant fromNbt(CompoundTag tag){
        Quadrant quadrant = new Quadrant(Direction.byName(tag.getString("direction")), tag.getString("name"));
        ListTag constellationTags = tag.getList("constellations", Tag.TAG_COMPOUND);
        for(Tag constellationTag : constellationTags){
            Constellation constellation = Constellation.fromNbt((CompoundTag) constellationTag);
            quadrant.addConstellation(constellation);
        }
        return quadrant;
    }

    public String getName(){
        return name;
    }

    public boolean containsStar(Star star) {
        for(Constellation constellation : constellations){
            if(constellation.containsStar(star)){
                return true;
            }
        }
        return false;
    }

    public Collection<Star> getStars() {
        List<Star> stars = new ArrayList<>();
        for(Constellation constellation : constellations){
            stars.addAll(constellation.getStars());
        }
        return stars;
    }

    public void addStar(Star star) {
        for(Constellation constellation : constellations){
            if(Objects.equals(constellation.name, star.getConstellation().name)){
                constellation.addStar(star);
                return;
            }
        }
    }
}
