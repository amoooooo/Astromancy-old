package coffee.amo.astromancy.core.systems.stars;

import coffee.amo.astromancy.core.systems.lumen.Lumen;
import coffee.amo.astromancy.core.systems.stars.classification.*;
import coffee.amo.astromancy.core.systems.stars.types.StarType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.util.RandomSource;

import java.util.*;


public class Star {
    public final StarClass classification;
    private String name;
    private float luminosity;
    private LuminosityClass luminosityClass;
    private float mass;
    private int spectralIntensity;
    private Character spectralClass;
    private StarType type;
    private Map<Lumen, Float> lumen = new HashMap<>();
//    private Color color;
//    private Constellation constellation;
//    private Pair<Quadrant, Quadrant> quadrants;
//    private Pair<Integer, Integer> quadrantCoordinates;
    private float randomOffset;

    public Star(int spectralIntensity) {
        Random random = new Random();
        this.spectralIntensity = spectralIntensity;
        this.classification = StarClass.getStarClassFromIntensity(spectralIntensity);
        this.luminosityClass = classification.getLuminosityClass();
        this.luminosity = random.nextFloat(luminosityClass.getLuminosityRange().getFirst(), luminosityClass.getLuminosityRange().getSecond());
        this.mass = 1000 * random.nextFloat(this.classification.getMassRange().getFirst(), this.classification.getMassRange().getSecond());
        this.randomOffset = random.nextFloat(0.01f)-0.005f;
        this.spectralClass = classification.getSpectralClass();
        this.type = StarType.list.getRandom(RandomSource.create()).isPresent() ? StarType.list.getRandom(RandomSource.create()).get() : StarType.NORMAL;
        //TODO: figure out how to add lumen types with weights
        int lumenCount = random.nextInt(3) + 1;
        float lumenStrength = 1;
        for (int i = 0; i < lumenCount - 1; i++) {
            float lumenStrengthRandom = random.nextFloat(Math.abs(lumenStrength)) + 0.1f;
            this.lumen.put(Lumen.LIST.getRandom(RandomSource.create()).get(), lumenStrengthRandom);
            lumenStrength -= lumenStrengthRandom;
        }
        this.lumen.put(Lumen.LIST.getRandom(RandomSource.create()).get(), lumenStrength);
        System.out.println("Created star with spectral intensity " + spectralIntensity);
    }

    public Star(int spectralIntensity, boolean a){
        this.spectralIntensity = spectralIntensity;
        this.classification = StarClass.getStarClassFromIntensity(spectralIntensity);
        if(a){
           System.out.println("how");
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

    public Map<Lumen, Float> getLumen() {
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

    public void setLumen(Map<Lumen, Float> lumen) {
        this.lumen = lumen;
    }

    public float getRandomOffset() {
        return randomOffset;
    }

    public void setRandomOffset(float randomOffset) {
        this.randomOffset = randomOffset;
    }

    public StarType getType() {
        return type;
    }

    public void setType(StarType type) {
        this.type = type;
    }

    public CompoundTag toNbt() {
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.putString("name", name);
        compoundTag.putString("classification", classification.getType());
        compoundTag.putFloat("luminosity", luminosity);
        compoundTag.putInt("luminosityClass", luminosityClass.ordinal());
        compoundTag.putFloat("mass", mass);
        compoundTag.putInt("spectralIntensity", spectralIntensity);
        compoundTag.putString("spectralClass", spectralClass.toString());
        compoundTag.putFloat("randomOffset", randomOffset);
        compoundTag.putInt("type", type.ordinal());
        ListTag listTag = new ListTag();
        lumen.forEach((type, strength) -> {
            CompoundTag tag = new CompoundTag();
            tag.putInt("type", type.ordinal());
            tag.putFloat("strength", strength);
            listTag.add(tag);
        });
        compoundTag.put("lumen", listTag);
        return compoundTag;
    }

    public static Star fromNbt(CompoundTag tag) {
        Star star = new Star(tag.getInt("spectralIntensity"), false);
        star.setName(tag.getString("name"));
        star.setLuminosity(tag.getFloat("luminosity"));
        star.setMass(tag.getFloat("mass"));
        star.setRandomOffset(tag.getFloat("randomOffset"));
        star.setLuminosityClass(LuminosityClass.values()[tag.getInt("luminosityClass")]);
        star.setSpectralClass(tag.getString("spectralClass").charAt(0));
        star.setType(StarType.values()[tag.getInt("type")]);
        ListTag listTag = tag.getList("lumen", Tag.TAG_COMPOUND);
        // read lumen type from nbt
        for (int i = 0; i < listTag.size(); i++) {
            CompoundTag tag1 = listTag.getCompound(i);
            star.getLumen().put(Lumen.values()[tag1.getInt("type")], tag1.getFloat("strength"));
        }
        return star;
    }

    //toString()
    public List<String> getString() {
        List<String> list = new ArrayList<>();
        list.add(name);
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
