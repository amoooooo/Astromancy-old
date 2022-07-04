package coffee.amo.astromancy.aequivaleo.compound;

import coffee.amo.astromancy.core.systems.glyph.Glyph;
import com.ldtteam.aequivaleo.api.compound.type.ICompoundType;
import com.ldtteam.aequivaleo.api.compound.type.group.ICompoundTypeGroup;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

public class GlyphCompoundType implements ICompoundType {
    public final Glyph glyph;
    public final Supplier<GlyphCompoundTypeGroup> group;
    public ResourceLocation registryName;

    public GlyphCompoundType(Glyph glyph, Supplier<GlyphCompoundTypeGroup> group) {
        this.glyph = glyph;
        this.group = group;
    }

    public Glyph getGlyph() {
        return glyph;
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
        this.registryName = registryName;
        return this;
    }

    @Override
    public ResourceLocation getRegistryName() {
        return registryName;
    }
}
