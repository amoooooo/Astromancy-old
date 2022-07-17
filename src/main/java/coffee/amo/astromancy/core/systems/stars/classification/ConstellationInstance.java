package coffee.amo.astromancy.core.systems.stars.classification;

import coffee.amo.astromancy.core.systems.glyph.Glyph;
import coffee.amo.astromancy.core.systems.stars.Star;
import com.mojang.datafixers.util.Pair;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.levelgen.XoroshiroRandomSource;
import net.minecraft.world.level.levelgen.synth.SimplexNoise;

import java.util.HashMap;
import java.util.Map;

public class ConstellationInstance {
    private final Constellations constellation;
    private Glyph attunedGlyph;
    float offset;
    int daysVisible;
    SimplexNoise noise;
    private Map<Integer, Map<Integer, Star>> starMap = new HashMap<>();

    public ConstellationInstance(Constellations constellation) {
        this.constellation = constellation;
    }

    public void setAttunedGlyph(Glyph attunedGlyph) {
        this.attunedGlyph = attunedGlyph;
    }

    public Glyph getAttunedGlyph() {
        return attunedGlyph;
    }

    public void addStar(Star star, int x, int y) {
        if (!starMap.containsKey(x)) {
            starMap.put(x, new HashMap<>());
        }
        starMap.get(x).put(y, star);
    }

    public Star getStar(int x, int y) {
        if (!starMap.containsKey(x)) {
            return null;
        }
        return starMap.get(x).get(y);
    }

    public Constellations getConstellation() {
        return constellation;
    }


    public Map<Integer, Map<Integer, Star>> getStarMap() {
        return starMap;
    }

    public void setStars(Map<Integer, Map<Integer, Star>> stars) {
        this.starMap = stars;
    }

    public void setOffset(float offset) {
        this.offset = offset;
    }

    public SimplexNoise getNoise() {
        return noise;
    }

    public void setNoise(SimplexNoise noise) {
        this.noise = noise;
    }

    public float getOffset() {
        return offset;
    }

    public void setDaysVisible(int daysVisible) {
        this.daysVisible = daysVisible;
    }

    public int getDaysVisible() {
        return daysVisible;
    }

    public CompoundTag toNbt() {
        CompoundTag tag = new CompoundTag();
        tag.putString("constellation", constellation.name());
        // starMap to NBT using CompoundTags
        CompoundTag starTag = new CompoundTag();
        starMap.forEach((x, yMap) -> {
            yMap.forEach((y, star) -> {
                CompoundTag starCompound = new CompoundTag();
                starCompound.put(""+y, star.toNbt());
                starTag.put(""+x, starCompound);
            });
        });
        tag.putInt("Glyph", attunedGlyph.ordinal());
        tag.put("stars", starTag);
        tag.putFloat("offset", getOffset());
        tag.putInt("daysVisible", daysVisible);
        return tag;
    }

    public static ConstellationInstance fromNbt(CompoundTag tag){
        String constellationName = tag.getString("constellation");
        Map<Integer, Map<Integer, Star>> starMap = new HashMap<>();
        CompoundTag starTag = tag.getCompound("stars");
        for(String x : starTag.getAllKeys()) {
            CompoundTag xTag = starTag.getCompound(x);
            for (String y : xTag.getAllKeys()) {
                starMap.computeIfAbsent(Integer.parseInt(x), integer -> new HashMap<>()).put(Integer.parseInt(y), Star.fromNbt(xTag.getCompound(y)));
            }
        }
        ConstellationInstance constellationInstance = new ConstellationInstance(Constellations.valueOf(constellationName));
        constellationInstance.setStars(starMap);
        constellationInstance.setAttunedGlyph(Glyph.values()[tag.getInt("Glyph")]);
        constellationInstance.setOffset(tag.getFloat("offset"));
        constellationInstance.setDaysVisible(tag.getInt("daysVisible"));
        constellationInstance.setNoise(new SimplexNoise(new XoroshiroRandomSource((long) (constellationInstance.getOffset() * 100f))));
        return constellationInstance;
    }

    public Star getStar(Star star){
        for(Map<Integer, Star> xMap : starMap.values()){
            for(Star yStar : xMap.values()){
                if(yStar.equals(star)){
                    return yStar;
                }
            }
        }
        return null;
    }

    public Pair<Integer, Integer> getStarCoordinates(Star star) {
        for(Map<Integer, Star> xMap : starMap.values()){
            for(Integer y : xMap.keySet()){
                if(xMap.get(y).equals(star)){
                    return Pair.of(xMap.keySet().iterator().next(), y);
                }
            }
        }
        return null;
    }
}
