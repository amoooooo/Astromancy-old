package coffee.amo.astromancy.core.systems.stars.classification;

import net.minecraft.util.random.Weight;
import net.minecraft.util.random.WeightedEntry;

public enum StarClass implements WeightedEntry {
    HYPERGIANT("Hypergiant", 150, 10),
    SUPERGIANT("Supergiant", 100, 20),
    BRIGHT_GIANT("Bright Giant", 50, 30),
    GIANT("Giant", 25, 40),
    SUBGIANT("Subgiant", 10, 50),
    MAIN_SEQUENCE("Main Sequence", 1, 70),
    DWARF("Dwarf", 0.9f, 40),
    SUBDWARF("Subdwarf", 0.75f, 0),
    WHITE_DWARF("White Dwarf", 0.5f, 20),
    CRIMSON("Crimson", 1, 5),
    PURE("Pure", 1, 5),
    DARK("Dark", 1, 5),
    EMPTY("Empty", 1, 5),
    HELL("Hell", 1, 5);

    private final String type;
    private final float massMultiplier;
    private final int chance;

    StarClass(String type, float massMultiplier, int chance) {
        this.type = type;
        this.massMultiplier = massMultiplier;
        this.chance = chance;
    }
    public String getType() {
        return type;
    }

    public float getMassMultiplier() {
        return massMultiplier;
    }
    public float getChance() {
        return chance;
    }

    @Override
    public Weight getWeight() {
        return Weight.of(chance);
    }
}
