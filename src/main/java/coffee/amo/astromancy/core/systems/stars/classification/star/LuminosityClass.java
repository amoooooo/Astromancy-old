package coffee.amo.astromancy.core.systems.stars.classification.star;

import com.mojang.datafixers.util.Pair;
import net.minecraft.util.random.Weight;
import net.minecraft.util.random.WeightedEntry;

public enum LuminosityClass implements WeightedEntry {
    O("O", 10, Pair.of(25.0f, 50000.0f)),
    I("I", 45, Pair.of(5.0f, 24.9f)),
    II("II", 35, Pair.of(1.5f, 4.9f)),
    III("III", 10, Pair.of(1.0f, 1.49f)),
    IV("IV", 10, Pair.of(0f,0f)),
    V("V", 5, Pair.of(0.6f, 1.0f)),
    VI("VI", 5, Pair.of(0.08f, 0.6f)),
    VII("VII", 5, Pair.of(0.0f, 0.08f));

    private final String className;
    private final int chance;
    private final Pair<Float, Float> luminosityRange;

    LuminosityClass(String className, int chance, Pair<Float, Float> luminosityRange) {
        this.className = className;
        this.chance = chance;
        this.luminosityRange = luminosityRange;
    }

    public static LuminosityClass getLuminosityClassFromString(String luminosityClass) {
        for (LuminosityClass luminosityClass1 : values()) {
            if (luminosityClass1.className.equals(luminosityClass)) {
                return luminosityClass1;
            }
        }
        return null;
    }

    public String getClassName() {
        return className;
    }

    public Pair<Float, Float> getLuminosityRange() {
        return luminosityRange;
    }

    @Override
    public Weight getWeight() {
        return Weight.of(chance);
    }
}
