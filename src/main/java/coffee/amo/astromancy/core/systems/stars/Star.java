package coffee.amo.astromancy.core.systems.stars;

import coffee.amo.astromancy.core.systems.stars.classification.Constellation;
import coffee.amo.astromancy.core.systems.stars.classification.Constellations;
import coffee.amo.astromancy.core.systems.stars.classification.Quadrant;
import coffee.amo.astromancy.core.systems.stars.classification.StarClass;
import com.mojang.datafixers.util.Pair;
import net.minecraft.nbt.CompoundTag;


public class Star {
    private String name;
    public final StarClass type;
    private int luminosity;
    private float strength;
    private float mass;
    private Constellation constellation;
    private Pair<Quadrant, Quadrant> quadrants;
    private Pair<Integer, Integer> quadrantCoordinates;

    public Star(StarClass type) {
        this.type = type;
    }

    public StarClass getType() {
        return type;
    }
    public Constellation getConstellation() {
        return constellation;
    }
    public int getLuminosity() {
        return luminosity;
    }
    public float getStrength() {
        return strength;
    }
    public float getMass() {
        return mass;
    }

    public String getName() {
        return name;
    }

    public Pair<Integer, Integer> getQuadrantCoordinates() {
        return quadrantCoordinates;
    }

    public Pair<Quadrant, Quadrant> getQuadrants() {
        return quadrants;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setLuminosity(int luminosity) {
        this.luminosity = luminosity;
    }
    public void setStrength(float strength) {
        this.strength = strength;
    }
    public void setMass(float mass) {
        this.mass = mass;
    }
    public void setConstellation(Constellation constellation) {
        this.constellation = constellation;
    }
    public void setQuadrants(Quadrant quadrant1, Quadrant quadrant2) {
        this.quadrants = new Pair<>(quadrant1, quadrant2);
    }

    public void setQuadrantCoordinates(Pair<Integer, Integer> quadrantCoordinates) {
        this.quadrantCoordinates = quadrantCoordinates;
    }

    public CompoundTag toNbt(){
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.putString("name", name);
        compoundTag.putString("type", type.toString());
        compoundTag.putString("constellation", constellation.name);
        compoundTag.putInt("luminosity", luminosity);
        compoundTag.putFloat("strength", strength);
        compoundTag.putFloat("mass", mass);
        return compoundTag;
    }

    public static Star fromNbt(CompoundTag tag){
        Star star = new Star(StarClass.valueOf(tag.getString("type")));
        star.name = tag.getString("name");
        star.constellation = Constellations.findByName(tag.getString("constellation"));
        star.luminosity = tag.getInt("luminosity");
        star.strength = tag.getFloat("strength");
        star.mass = tag.getFloat("mass");
        return star;
    }

    //toString()
    public String getString(){
        String typeString = type.toString();
        typeString = typeString.substring(0, 1).toUpperCase() + typeString.substring(1).toLowerCase();
        return
                "Name: '" + name + '\'' +
                ", Type: " + typeString +
                ", Constellation: '" + constellation.name + '\'';
    }

    public Quadrant getQuadrant() {
        return quadrants.getFirst();
    }
}
