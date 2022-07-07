package coffee.amo.astromancy.core.systems.lumen;

import net.minecraft.util.random.Weight;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.util.random.WeightedRandomList;

public enum Lumen implements WeightedEntry {
    NONE("None", 0, 1),
    DECONSTRUCTIVE("Deconstructive", 45, 1),
    CONSTRUCTIVE("Constructive", 35, 1),
    PURE("Pure", 10, 1.3f),
    DENATURED("Denatured", 10, 1.5f),
    EMPTY("Null", 5, 2.0f);

    private final String type;
    private final int chance;
    private final float powerMultiplier;

    Lumen(String type, int chance, float powerMultiplier) {
        this.type = type;
        this.chance = chance;
        this.powerMultiplier = powerMultiplier;
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
}
