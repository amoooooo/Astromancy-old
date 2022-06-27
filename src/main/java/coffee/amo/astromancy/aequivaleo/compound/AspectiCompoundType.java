package coffee.amo.astromancy.aequivaleo.compound;

import coffee.amo.astromancy.Astromancy;
import coffee.amo.astromancy.core.registration.AspectiRegistry;
import coffee.amo.astromancy.core.systems.aspecti.Aspecti;
import com.ldtteam.aequivaleo.api.compound.type.ICompoundType;
import com.ldtteam.aequivaleo.api.compound.type.group.ICompoundTypeGroup;
import com.ldtteam.aequivaleo.api.util.ModRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Locale;
import java.util.function.Supplier;

public class AspectiCompoundType implements ICompoundType {
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

    @Override
    public ICompoundType setRegistryName(ResourceLocation registryName) {
        return null;
    }

    @Override
    public ResourceLocation getRegistryName() {
        return null;
    }
}
