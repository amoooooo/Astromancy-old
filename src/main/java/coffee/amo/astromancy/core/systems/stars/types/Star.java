package coffee.amo.astromancy.core.systems.stars.types;

import coffee.amo.astromancy.Astromancy;
import coffee.amo.astromancy.core.systems.lumen.Lumen;
import coffee.amo.astromancy.core.systems.stars.classification.star.LuminosityClass;
import coffee.amo.astromancy.core.systems.stars.classification.star.StarClass;
import coffee.amo.astromancy.core.systems.stars.classification.star.StarType;
import com.mojang.datafixers.util.Pair;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;

import java.util.*;

public class Star extends AstralObject {
    public final StarClass starClass;
    public StarType starType;
    private Pair<Float, LuminosityClass> luminosity;
    private float mass;
    private Pair<Integer, Character> spectralClass;
    private Map<Lumen, Float> lumen = new HashMap<>();
    private float renderOffset;
    public float orbitSpeed;
    private static Random random = new Random();

    public Star(String name, StarClass starClass, StarType type) {
        super(name);
        this.starClass = starClass;
        this.starType = type;
    }

    public Star(String name, UUID uuid, StarClass starClass, StarType type, int size) {
        super(name, uuid, size);
        this.starClass = starClass;
        this.starType = type;
    }

    public Star(int spectralIntensity){
        super();
        this.spectralClass = Pair.of(spectralIntensity, StarClass.getStarClassFromIntensity(spectralIntensity).getSpectralClass());
        this.starClass = StarClass.getStarClassFromIntensity(spectralIntensity);
        LuminosityClass luminosityClass = starClass.getLuminosityClass();
        this.luminosity = Pair.of(random.nextFloat(luminosityClass.getLuminosityRange().getFirst(), luminosityClass.getLuminosityRange().getSecond()) + luminosityClass.getLuminosityRange().getFirst(), luminosityClass);
        this.mass = 1000 * random.nextFloat(starClass.getMassRange().getFirst(), starClass.getMassRange().getSecond());
        this.renderOffset = random.nextFloat(0.01f)-0.005f;
        int lumenCount = random.nextInt(3) + 1;
        float lumenStrength = 1;
        for( int i = 0; i < lumenCount - 1; i++){
            float lumenStrengthRandom = random.nextFloat(Math.abs(lumenStrength)) + 0.1f;
            this.lumen.put(Lumen.LIST.getRandom(RandomSource.create()).get(), lumenStrengthRandom);
            lumenStrength -= lumenStrengthRandom;
        }
        this.lumen.put(Lumen.LIST.getRandom(RandomSource.create()).get(), lumenStrength);
        this.starType = starTypeFromHighestLumen(this.lumen);
        Astromancy.LOGGER.info("Created star: " + this.toString());
    }

    public StarClass getStarClass() {
        return starClass;
    }

    public StarType getStarType() {
        return starType;
    }

    public Pair<Float, LuminosityClass> getLuminosity() {
        return luminosity;
    }

    public float getMass() {
        return mass;
    }

    public Pair<Integer, Character> getSpectralClass() {
        return spectralClass;
    }

    public Map<Lumen, Float> getLumen() {
        return lumen;
    }

    public float getRenderOffset() {
        return renderOffset;
    }

    public void setSpectralClass(Pair<Integer, Character> spectralClass) {
        this.spectralClass = spectralClass;
    }

    public void setLuminosity(Pair<Float, LuminosityClass> luminosity) {
        this.luminosity = luminosity;
    }

    public void setMass(float mass) {
        this.mass = mass;
    }

    public void setLumen(Map<Lumen, Float> lumen) {
        this.lumen = lumen;
    }

    public void setRenderOffset(float renderOffset) {
        this.renderOffset = renderOffset;
    }

    public void setStarType(StarType starType) {
        this.starType = starType;
    }

    public String toString(){
        return "Star{" +
                "name='" + this.getName() + '\'' +
                ", uuid=" + this.getUuid() +
                ", starClass=" + starClass +
                ", starType=" + starType +
                ", luminosity=" + luminosity +
                ", mass=" + mass +
                ", spectralClass=" + spectralClass +
                ", lumen=" + lumen +
                ", renderOffset=" + renderOffset +
                '}';
    }

    public CompoundTag toNbt(){
        CompoundTag tag = new CompoundTag();
        tag.putString("name", this.getName());
        tag.putUUID("uuid", this.getUuid());
        tag.putInt("starClass", this.starClass.ordinal());
        tag.putInt("starType", this.starType.ordinal());
        tag.putFloat("luminosity", this.luminosity.getFirst());
        tag.putFloat("mass", this.mass);
        tag.putInt("spectralIntensity", this.spectralClass.getFirst());
        tag.putString("spectralClass", this.spectralClass.getSecond().toString());
        tag.putFloat("renderOffset", this.renderOffset);
        CompoundTag lumenTag = new CompoundTag();
        for(Map.Entry<Lumen, Float> entry : this.lumen.entrySet()){
            lumenTag.putFloat(entry.getKey().toString(), entry.getValue());
        }
        tag.put("lumen", lumenTag);
        tag.putInt("size", this.getSize());
        tag.putFloat("orbitSpeed", this.orbitSpeed);
        return tag;
    }

    public static Star fromNbt(CompoundTag tag){
        Star star = new Star(tag.getString("name"), UUID.fromString(tag.getString("uuid")), StarClass.values()[tag.getInt("starClass")], StarType.values()[tag.getInt("starType")], tag.getInt("size"));
        star.luminosity = Pair.of(tag.getFloat("luminosity"), star.starClass.getLuminosityClass());
        star.mass = tag.getFloat("mass");
        star.spectralClass = Pair.of(tag.getInt("spectralIntensity"), tag.getString("spectralClass").charAt(0));
        star.renderOffset = tag.getFloat("renderOffset");
        CompoundTag lumenTag = tag.getCompound("lumen");
        for(String key : lumenTag.getAllKeys()){
            star.lumen.put(Lumen.valueOf(key), lumenTag.getFloat(key));
        }
        star.orbitSpeed = tag.getFloat("orbitSpeed");
        return star;
    }

    public List<String> getStringDescription(){
        List<String> description = new ArrayList<>();
        description.add("Star: " + this.getName());
        description.add("Star Class: " + this.starClass.toString());
        description.add("Star Type: " + this.starType.toString());
        description.add("Luminosity: " + this.luminosity.getFirst());
        description.add("Mass: " + this.mass);
        description.add("Spectral Class: " + this.spectralClass.getSecond());
        description.add("Spectral Intensity: " + this.spectralClass.getFirst());
        description.add("Lumen: " + this.lumen.toString());
        description.add("Render Offset: " + this.renderOffset);
        return description;
    }

    public StarType starTypeFromHighestLumen(Map<Lumen, Float> lumenMap){
        Lumen highestLumen = null;
        float highestLumenStrength = 0;
        for(Map.Entry<Lumen, Float> entry : lumenMap.entrySet()){
            if(entry.getValue() > highestLumenStrength){
                highestLumen = entry.getKey();
                highestLumenStrength = entry.getValue();
            }
        }
        if(highestLumen == null){
            return StarType.NORMAL;
        }
        return highestLumen.getStarType();
    }

}
