package coffee.amo.astromancy.core.systems.stars.classification;

import net.minecraft.util.random.Weight;
import net.minecraft.util.random.WeightedEntry;

public enum StarClass implements WeightedEntry {
    HYPERGIANT("Hypergiant", 50, 10),
    SUPERGIANT("Supergiant", 35, 20),
    BRIGHT_GIANT("Bright Giant", 30, 30),
    GIANT("Giant", 25, 40),
    SUBGIANT("Subgiant", 15, 50),
    MAIN_SEQUENCE("Main Sequence", 10, 70),
    DWARF("Dwarf", 9, 40),
    SUBDWARF("Subdwarf", 7.5f, 0),
    WHITE_DWARF("White Dwarf", 5, 20),
    CRIMSON("Crimson", 15, 5),
    PURE("Pure", 10, 5),
    DARK("Dark", 10, 5),
    EMPTY("Empty", 10, 5),
    HELL("Hell", 15, 5);

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

    public static StarClass fromString(String type) {
        for (StarClass starClass : StarClass.values()) {
            if (starClass.getType().equals(type)) {
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
