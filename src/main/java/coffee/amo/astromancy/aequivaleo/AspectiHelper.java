package coffee.amo.astromancy.aequivaleo;

import com.google.common.collect.Sets;
import com.ldtteam.aequivaleo.api.IAequivaleoAPI;
import com.ldtteam.aequivaleo.api.compound.CompoundInstance;
import com.ldtteam.aequivaleo.api.results.IEquivalencyResults;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;


public class AspectiHelper {

    public static IEquivalencyResults getResults(ResourceKey<Level> levelKey) {
        return IAequivaleoAPI.getInstance().getEquivalencyResults(levelKey);
    }

    public static AspectiEntry getEntry(ResourceKey<Level> levelKey, ItemStack item) {
        List<CompoundInstance> finalResults = new ArrayList<>(getResults(levelKey).dataFor(item));
        if (item.hasContainerItem() && !item.getContainerItem().equals(item)){
            Set<CompoundInstance> containerValues = getResults(levelKey).dataFor(item.getContainerItem());
            for(CompoundInstance containerValue : containerValues) {
                Optional<CompoundInstance> existingValue = finalResults.stream().filter(c -> c.getType().equals(containerValue.getType())).findFirst();
                if (existingValue.isPresent()) {
                    CompoundInstance replacement = new CompoundInstance(containerValue.getType(), existingValue.get().getAmount() + containerValue.getAmount());
                    finalResults.remove(existingValue.get());
                    finalResults.add(replacement);
                } else {
                    finalResults.add(containerValue);
                }
            }
        }
        return new AspectiEntry(Sets.newHashSet(finalResults));
    }
}
