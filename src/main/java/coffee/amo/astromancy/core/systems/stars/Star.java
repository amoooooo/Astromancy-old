package coffee.amo.astromancy.core.systems.stars;

import coffee.amo.astromancy.core.systems.lumen.LumenType;
import coffee.amo.astromancy.core.systems.stars.classification.*;
import com.mojang.datafixers.util.Pair;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Star {
    public final StarClass classification;
    private String name;
    private float luminosity;
    private LuminosityClass luminosityClass;
    private float mass;
    private int spectralIntensity;
    private Character spectralClass;
    private List<Pair<LumenType, Float>> lumen;
//    private Color color;
//    private Constellation constellation;
//    private Pair<Quadrant, Quadrant> quadrants;
//    private Pair<Integer, Integer> quadrantCoordinates;
    private float randomOffset;

    public Star(String name, int spectralIntensity) {
        Random random = new Random();
        this.name = name;
        this.spectralIntensity = spectralIntensity;
        this.classification = StarClass.getStarClassFromIntensity(spectralIntensity);
        this.luminosityClass = classification.getLuminosityClass();
        this.luminosity = random.nextFloat(luminosityClass.getLuminosityRange().getFirst(), luminosityClass.getLuminosityRange().getSecond());
        this.mass = 1000 * random.nextFloat(this.classification.getMassRange().getFirst(), this.classification.getMassRange().getSecond());
        this.randomOffset = random.nextFloat(0.01f)-0.005f;
        this.spectralClass = classification.getSpectralClass();
        //TODO: figure out how to add lumen types with weights
        int lumenCount = random.nextInt(3) + 1;
        float lumenStrength = 1;
        for(int i = 0; i < lumenCount; i++) {
            float lumenStrengthRandom = random.nextFloat(0.01f, lumenStrength);
            this.lumen.add(Pair.of(LumenType.LIST.getRandom(random).get(), lumenStrengthRandom));
            lumenStrength -= lumenStrengthRandom;
        }
    }

    public StarClass getClassification() {
        return classification;
    }

    public float getLuminosity() {
        return luminosity;
    }

    public void setLuminosity(float luminosity) {
        this.luminosity = luminosity;
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

    public int getSpectralIntensity() {
        return spectralIntensity;
    }

    public Character getSpectralClass() {
        return spectralClass;
    }

    public List<Pair<LumenType, Float>> getLumen() {
        return lumen;
    }

    public void setLuminosityClass(LuminosityClass luminosityClass) {
        this.luminosityClass = luminosityClass;
    }

    public LuminosityClass getLuminosityClass() {
        return luminosityClass;
    }

    public void setSpectralClass(Character spectralClass) {
        this.spectralClass = spectralClass;
    }

    public void setSpectralIntensity(int spectralIntensity) {
        this.spectralIntensity = spectralIntensity;
    }

    public void setLumen(List<Pair<LumenType, Float>> lumen) {
        this.lumen = lumen;
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
        compoundTag.putString("classification", classification.getType());
        compoundTag.putFloat("luminosity", luminosity);
        compoundTag.putString("luminosityClass", luminosityClass.getClassName());
        compoundTag.putFloat("mass", mass);
        compoundTag.putInt("spectralIntensity", spectralIntensity);
        compoundTag.putString("spectralClass", spectralClass.toString());
        compoundTag.putFloat("randomOffset", randomOffset);
        ListTag listTag = new ListTag();
        for(Pair<LumenType, Float> lumenTypeFloatPair : lumen) {
            CompoundTag tag = new CompoundTag();
            tag.putString("type", lumenTypeFloatPair.getFirst().getType());
            tag.putFloat("strength", lumenTypeFloatPair.getSecond());
            listTag.add(tag);
        }
        compoundTag.put("lumen", listTag);
        return compoundTag;
    }

    public static Star fromNbt(CompoundTag tag) {
        Star star = new Star(tag.getString("name"), tag.getInt("spectralIntensity"));
        star.setLuminosity(tag.getFloat("luminosity"));
        star.setMass(tag.getFloat("mass"));
        star.setRandomOffset(tag.getFloat("randomOffset"));
        star.setLuminosityClass(LuminosityClass.getLuminosityClassFromString(tag.getString("luminosityClass")));
        star.setSpectralClass(tag.getString("spectralClass").charAt(0));
        ListTag listTag = tag.getList("lumen", Tag.TAG_COMPOUND);
        for(Tag tag1 : listTag) {
            CompoundTag tag2 = (CompoundTag) tag1;
            star.lumen.add(Pair.of(LumenType.getLumenTypeFromString(tag2.getString("type")), tag2.getFloat("strength")));
        }
        return star;
    }

    //toString()
    public List<String> getString() {
        List<String> list = new ArrayList<>();
        list.add("Name: " + name);
        list.add("Type: " + classification.getType());
        return list;
    }

    public List<String> getStringDetailed() {
        List<String> list = new ArrayList<>();
        list.add("Name: " + name);
        list.add("Type: " + classification.getType());
        list.add("Luminosity: " + luminosity);
        list.add("Mass: " + mass);
        return list;
    }
}
