package coffee.amo.astromancy.core.systems.stars.classification;

import com.mojang.datafixers.util.Pair;
import net.minecraft.util.random.Weight;
import net.minecraft.util.random.WeightedEntry;

public enum StarClass implements WeightedEntry {
    ULTRAGIANT("Ultragiant", 5, LuminosityClass.O, 30000, Pair.of(16.0f, 50.0f), 'O'),
    HYPERGIANT("Hypergiant",  10, LuminosityClass.O, 10000, Pair.of(2.1f, 15.9f), 'B'),
    SUPERGIANT("Supergiant",  20, LuminosityClass.I, 7500, Pair.of(1.4f, 2.09f), 'A'),
    GIANT("Giant", 40, LuminosityClass.III, 6000, Pair.of(1.04f, 1.39f), 'F'),
    MAIN_SEQUENCE("Main Sequence",  70, LuminosityClass.V, 5200, Pair.of(0.8f, 1.039f), 'G'),
    DWARF("Dwarf",  40, LuminosityClass.VI, 3700, Pair.of(0.45f, 0.79f), 'K'),
    WHITE_DWARF("White Dwarf",  20, LuminosityClass.VII, 2400, Pair.of(0.08f, 0.449f), 'M');

    private final String type;
    private final int chance;
    private final LuminosityClass luminosityClass;
    private final int spectralIntensity;
    private final Pair<Float, Float> massRange;
    private final Character spectralClass;

    StarClass(String type, int chance, LuminosityClass luminosityClass, int spectralIntensity, Pair<Float, Float> massRange, Character spectralClass) {
        this.type = type;
        this.chance = chance;
        this.luminosityClass = luminosityClass;
        this.spectralIntensity = spectralIntensity;
        this.massRange = massRange;
        this.spectralClass = spectralClass;
    }

    public String getType() {
        return type;
    }

    public int getSpectralIntensity() {
        return spectralIntensity;
    }

    public Character getSpectralClass() {
        return spectralClass;
    }

    public Pair<Float, Float> getMassRange() {
        return massRange;
    }

    public static StarClass getStarClassFromIntensity(int spectralIntensity) {
        // check if spectral intensity is greater than any of the star classes
        for (StarClass starClass : StarClass.values()) {
            if (spectralIntensity >= starClass.getSpectralIntensity()) {
                return starClass;
            }
        }
        // if not, return null
        return null;
    }

    public float getChance() {
        return chance;
    }

    public static StarClass fromString(String type) {
        for (StarClass starClass : StarClass.values()) {
            if (starClass.getType().equals(type)) {
                return starClass;
            }
        }
        return null;
    }

    public LuminosityClass getLuminosityClass() {
        return luminosityClass;
    }

    public static StarClass fromLuminosityClass(LuminosityClass luminosityClass) {
        for (StarClass starClass : StarClass.values()) {
            if (starClass.getLuminosityClass().equals(luminosityClass)) {
                return starClass;
            }
        }
        return null;
    }

    @Override
    public Weight getWeight() {
        return Weight.of(chance);
    }
}
