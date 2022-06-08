package coffee.amo.astromancy.aequivaleo;

import coffee.amo.astromancy.aequivaleo.compound.AspectiCompoundType;
import net.minecraft.util.Mth;

public class AspectiInstance {
    public AspectiCompoundType type;
    public double amount;
    public AspectiInstance(AspectiCompoundType type, double amount) {
        this.type = type;
        this.amount = Mth.ceil(amount);
    }
}
