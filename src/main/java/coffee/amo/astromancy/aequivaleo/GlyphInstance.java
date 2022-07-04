package coffee.amo.astromancy.aequivaleo;

import coffee.amo.astromancy.aequivaleo.compound.GlyphCompoundType;
import net.minecraft.util.Mth;

public class GlyphInstance {
    public GlyphCompoundType type;
    public double amount;
    public GlyphInstance(GlyphCompoundType type, double amount) {
        this.type = type;
        this.amount = Mth.ceil(amount);
    }
}
