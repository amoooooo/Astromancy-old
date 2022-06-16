package coffee.amo.astromancy.core.systems.stars.types;

import net.minecraft.util.random.Weight;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.util.random.WeightedRandomList;

public enum StarType implements WeightedEntry {
    NORMAL("Normal", 100),
    CRIMSON("Crimson",  5),
    PURE("Pure", 5),
    DARK("Dark", 5),
    EMPTY("Empty",  5),
    HELL("Hell",  5);

    private final String name;
    private final int chance;

    StarType(String name, int chance) {
        this.name = name;
        this.chance = chance;
    }

    @Override
    public Weight getWeight() {
        return Weight.of(chance);
    }

    public String getType(){
        return name;
    }

    public static WeightedRandomList<StarType> list = WeightedRandomList.create(
            NORMAL,
            CRIMSON,
            PURE,
            DARK,
            EMPTY,
            HELL
    );
}
