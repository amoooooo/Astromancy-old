package coffee.amo.astromancy.core.systems.stars.classification;

import net.minecraft.util.random.Weight;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.util.random.WeightedRandomList;

public enum SpectralIntensityBand implements WeightedEntry {
    O(30000, 50000, 5),
    B(10000, 29999, 10),
    A(7500, 9999, 20),
    F(6000, 7499, 20),
    G(5200, 5999, 40),
    K(3700, 5199, 40),
    M(2400, 3699, 60);
    ;

    private int lowerBound;
    private int upperBound;
    private int chance;

    SpectralIntensityBand(int lowerBound, int upperBound, int chance) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.chance = chance;
    }

    public int getLowerBound() {
        return lowerBound;
    }

    public int getUpperBound() {
        return upperBound;
    }

    @Override
    public Weight getWeight() {
        return Weight.of(chance);
    }

    public static WeightedRandomList<SpectralIntensityBand> bands = WeightedRandomList.create(
            O,
            B,
            A,
            F,
            G,
            K,
            M
    );
}
