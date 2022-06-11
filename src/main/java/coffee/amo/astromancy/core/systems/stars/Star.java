package coffee.amo.astromancy.core.systems.stars;

import coffee.amo.astromancy.core.systems.stars.classification.*;
import com.mojang.datafixers.util.Pair;
import com.sammy.ortus.helpers.util.Color;
import net.minecraft.nbt.CompoundTag;

import java.util.ArrayList;
import java.util.List;


public class Star {
    public final StarClass type;
    private String name;
    private int luminosity;
    private float strength;
    private float mass;
    private Color color;
    private Constellation constellation;
    private Pair<Quadrant, Quadrant> quadrants;
    private Pair<Integer, Integer> quadrantCoordinates;
    private float randomOffset;

    public Star(StarClass type) {
        this.type = type;
    }

    public static Star fromNbt(CompoundTag tag) {
        Star star = new Star(StarClass.fromString(tag.getString("type")));
        star.name = tag.getString("name");
        star.constellation = Constellations.findByName(tag.getString("constellation"));
        star.luminosity = tag.getInt("luminosity");
        star.strength = tag.getFloat("strength");
        star.mass = tag.getFloat("mass");
        star.quadrantCoordinates = new Pair<>(tag.getInt("quadrantX"), tag.getInt("quadrantY"));
        star.quadrants = new Pair<>(Quadrants.findQuadrantFromName(tag.getString("quadrant1")), Quadrants.findQuadrantFromName(tag.getString("quadrant2")));
        star.color = new Color(tag.getInt("red"), tag.getInt("green"), tag.getInt("blue"), tag.getInt("alpha"));
        star.randomOffset = tag.getFloat("randomOffset");
        return star;
    }

    public StarClass getType() {
        return type;
    }

    public Constellation getConstellation() {
        return constellation;
    }

    public void setConstellation(Constellation constellation) {
        this.constellation = constellation;
    }

    public int getLuminosity() {
        return luminosity;
    }

    public void setLuminosity(int luminosity) {
        this.luminosity = luminosity;
    }

    public float getStrength() {
        return strength;
    }

    public void setStrength(float strength) {
        this.strength = strength;
    }

    public float getMass() {
        return mass;
    }

    public void setMass(float mass) {
        this.mass = mass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Pair<Integer, Integer> getQuadrantCoordinates() {
        return quadrantCoordinates;
    }

    public void setQuadrantCoordinates(Pair<Integer, Integer> quadrantCoordinates) {
        this.quadrantCoordinates = quadrantCoordinates;
    }

    public Pair<Quadrant, Quadrant> getQuadrants() {
        return quadrants;
    }

    public void setQuadrants(Quadrant quadrant1, Quadrant quadrant2) {
        this.quadrants = new Pair<>(quadrant1, quadrant2);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public float getRandomOffset() {
        return randomOffset;
    }

    public void setRandomOffset(float randomOffset) {
        this.randomOffset = randomOffset;
    }

    public CompoundTag toNbt() {
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.putString("name", name);
        compoundTag.putString("type", type.getType());
        compoundTag.putString("constellation", constellation.name);
        compoundTag.putInt("luminosity", luminosity);
        compoundTag.putFloat("strength", strength);
        compoundTag.putFloat("mass", mass);
        compoundTag.putInt("quadrantX", quadrantCoordinates.getFirst());
        compoundTag.putInt("quadrantY", quadrantCoordinates.getSecond());
        compoundTag.putString("quadrant1", quadrants.getFirst().name);
        compoundTag.putString("quadrant2", quadrants.getSecond().name);
        compoundTag.putInt("red", color.getRed());
        compoundTag.putInt("green", color.getGreen());
        compoundTag.putInt("blue", color.getBlue());
        compoundTag.putInt("alpha", color.getAlpha());
        compoundTag.putFloat("randomOffset", randomOffset);
        return compoundTag;
    }

    //toString()
    public List<String> getString() {
        List<String> list = new ArrayList<>();
        list.add("Name: " + name);
        list.add("Constellation: " + constellation.name);
        list.add("Type: " + type.getType());
        return list;
    }

    public List<String> getStringDetailed() {
        List<String> list = new ArrayList<>();
        list.add("Name: " + name);
        list.add("Constellation: " + constellation.name);
        list.add("Type: " + type.getType());
        list.add("Luminosity: " + luminosity);
        list.add("Strength: " + strength);
        list.add("Mass: " + mass);
        list.add("Quadrant X: " + quadrantCoordinates.getFirst());
        list.add("Quadrant Y: " + quadrantCoordinates.getSecond());
        list.add("Quadrant 1: " + quadrants.getFirst().name);
        list.add("Quadrant 2: " + quadrants.getSecond().name);
        return list;
    }

    public Quadrant getQuadrant() {
        return quadrants.getFirst();
    }
}
