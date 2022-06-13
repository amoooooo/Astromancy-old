package coffee.amo.astromancy.aequivaleo.compound;

import coffee.amo.astromancy.core.systems.aspecti.Aspecti;
import com.ldtteam.aequivaleo.api.compound.type.ICompoundType;
import com.ldtteam.aequivaleo.api.compound.type.group.ICompoundTypeGroup;
import net.minecraftforge.registries.ForgeRegistryEntry;

import java.util.function.Supplier;

public class AspectiCompoundType extends ForgeRegistryEntry<ICompoundType> implements ICompoundType {
    public final Aspecti aspecti;
    public final Supplier<AspectiCompoundTypeGroup> group;

    public AspectiCompoundType(Aspecti aspecti, Supplier<AspectiCompoundTypeGroup> group) {
        this.aspecti = aspecti;
        this.group = group;
    }

    public Aspecti getAspecti() {
        return aspecti;
    }

    /**
     * The group this compound classification belongs to.
     * Defines the common behaviour of several compound types, and allows for them to be grouped together.
     *
     * @return The group.
     */
    @Override
    public ICompoundTypeGroup getGroup() {
        return group.get();
    }

}
