package coffee.amo.astromancy.core.systems.lumen;

import coffee.amo.astromancy.core.systems.stars.classification.star.StarType;
import net.minecraft.util.random.Weight;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.util.random.WeightedRandomList;

public enum Lumen implements WeightedEntry {
    NONE("None", 0, 1, StarType.NORMAL),
    DECONSTRUCTIVE("Deconstructive", 45, 1, StarType.NORMAL),
    CONSTRUCTIVE("Constructive", 35, 1, StarType.NORMAL),
    PURE("Pure", 10, 1.3f, StarType.PURE),
    HELL("Hell", 5, 1.5f, StarType.HELL),
    EXOTIC("Exotic", 5, 1.5f, StarType.EXOTIC),
    DENATURED("Denatured", 10, 1.5f, StarType.NORMAL),
    EMPTY("Null", 5, 2.0f, StarType.EMPTY);

    private final String type;
    private final int chance;
    private final float powerMultiplier;
    private final StarType starType;

    Lumen(String type, int chance, float powerMultiplier, StarType starType) {
        this.type = type;
        this.chance = chance;
        this.powerMultiplier = powerMultiplier;
        this.starType = starType;
    }

    public static Lumen getLumenTypeFromString(String type) {
        for (Lumen lumenType : values()) {
            if (lumenType.type.equals(type)) {
                return lumenType;
            }
        }
        return null;
    }

    public String getType() {
        return type;
    }

    public int getChance() {
        return chance;
    }

    public float getPowerMultiplier() {
        return powerMultiplier;
    }

    public static Lumen get(int i) {
        return values()[i];
    }

    @Override
    public Weight getWeight() {
        return Weight.of(chance);
    }

    public static final WeightedRandomList<Lumen> LIST = WeightedRandomList.create(Lumen.values());

    public StarType getStarType() {
        return starType;
    }
}
