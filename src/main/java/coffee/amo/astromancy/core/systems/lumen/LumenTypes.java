package coffee.amo.astromancy.core.systems.lumen;

import net.minecraft.util.random.Weight;
import net.minecraft.util.random.WeightedEntry;

public enum LumenTypes implements WeightedEntry {
    DESTRUCTIVE("Destructive", 45, 1),
    CONSTRUCTIVE("Constructive", 35, 1),
    PURE("Pure", 10, 1.3f),
    DENATURED("Denatured", 10, 1.5f),
    NULL("Null", 5, 2.0f);

    private final String type;
    private final int chance;
    private final float powerMultiplier;

    LumenTypes(String type, int chance, float powerMultiplier) {
        this.type = type;
        this.chance = chance;
        this.powerMultiplier = powerMultiplier;
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

    @Override
    public Weight getWeight() {
        return Weight.of(chance);
    }
}
