package coffee.amo.astromancy.aequivaleo.compound;

import coffee.amo.astromancy.aequivaleo.AspectiInstance;
import com.ldtteam.aequivaleo.api.compound.CompoundInstance;
import com.ldtteam.aequivaleo.api.compound.container.ICompoundContainer;
import com.ldtteam.aequivaleo.api.compound.type.group.ICompoundTypeGroup;
import com.ldtteam.aequivaleo.api.mediation.IMediationCandidate;
import com.ldtteam.aequivaleo.api.mediation.IMediationEngine;
import com.ldtteam.aequivaleo.api.recipe.equivalency.IEquivalencyRecipe;
import com.ldtteam.aequivaleo.vanilla.api.recipe.equivalency.ITagEquivalencyRecipe;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistryEntry;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.Set;

public class AspectiCompoundTypeGroup extends ForgeRegistryEntry<ICompoundTypeGroup> implements ICompoundTypeGroup {
    /**
     * The mediation engine.
     *
     * @return The mediation engine.
     */
    @Override
    public @NotNull IMediationEngine getMediationEngine() {
        return context -> {
            return context
                    .getCandidates()
                    .stream()
                    .min((i1, i2) -> {
                        if(i1.isSourceIncomplete() && !i2.isSourceIncomplete())
                            return 1;
                         if(!i1.isSourceIncomplete() && i2.isSourceIncomplete())
                            return -1;
                         if (i1.getValues().isEmpty() && !i2.getValues().isEmpty())
                            return 1;
                         if (!i1.getValues().isEmpty() && i2.getValues().isEmpty())
                            return -1;
                         return (int) ((int) (i1.getValues().stream().mapToDouble(CompoundInstance::getAmount)).sum() -
                                                          i2.getValues().stream().mapToDouble(CompoundInstance::getAmount).sum());
                    }).map(IMediationCandidate::getValues);
        };
    }

    @Override
    public String getDirectoryName() {
        return "astromancy/aspecti";
    }

    /**
     * Allows the group to indicate if an incomplete recipe is allowed to process compounds of this group.
     *
     * @param recipe The recipe in question.
     * @return True to allow, false to disallow.
     */
    @Override
    public boolean shouldIncompleteRecipeBeProcessed(@NotNull IEquivalencyRecipe recipe) {
        return false;
    }

    /**
     * Indicates if the given instance is allowed to contribute to a given recipe.
     *
     * @param recipe           The recipe in question.
     * @param compoundInstance The instance that should be contribute or not.
     * @return True when the compound instance should contribute, false when not.
     */
    @Override
    public boolean canContributeToRecipeAsInput(IEquivalencyRecipe recipe, CompoundInstance compoundInstance) {
        return !(recipe instanceof ITagEquivalencyRecipe<?>);
    }

    /**
     * Indicates if the given instance is allowed to contribute from a given recipe.
     *
     * @param recipe           The recipe in question.
     * @param compoundInstance The instance that should contribute or not.
     * @return True when the compound instance should contribute, false when not.
     */
    @Override
    public boolean canContributeToRecipeAsOutput(IEquivalencyRecipe recipe, CompoundInstance compoundInstance) {
        return !(recipe instanceof ITagEquivalencyRecipe<?>);
    }

    /**
     * Indicates if the given compound instance is valid for the compound container.
     *
     * @param wrapper          The container.
     * @param compoundInstance The instance.
     * @return True when instance is valid, false when not.
     */
    @Override
    public boolean isValidFor(ICompoundContainer<?> wrapper, CompoundInstance compoundInstance) {
        Object contents = wrapper.getContents();
        return contents instanceof ItemStack || contents instanceof Item || contents instanceof FluidStack;
    }

    @Override
    public Optional<?> convertToCacheEntry(ICompoundContainer<?> container, Set<CompoundInstance> instances) {
        if(!isValidForAspectiRecipe(container)){
            return Optional.empty();
        }
        return Optional.of(new AspectiInstance((AspectiCompoundType) instances, instances.stream().mapToDouble(CompoundInstance::getAmount).sum()));
    }

    private boolean isValidForAspectiRecipe(ICompoundContainer<?> wrapper) {
        return wrapper.getContents() instanceof ItemStack;
    }
}
