package coffee.amo.astromancy.core.systems.stars.classification;

import coffee.amo.astromancy.core.systems.stars.Star;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Constellation {
    // Star names are the quadrant suits + number
    public String name;
    public int height;
    public Constellation(String name, int height) {
        this.name = name;
        this.height = height;
    }
}
