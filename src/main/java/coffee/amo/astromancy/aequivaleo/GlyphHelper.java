package coffee.amo.astromancy.aequivaleo;

import com.google.common.collect.Sets;
import com.ldtteam.aequivaleo.api.IAequivaleoAPI;
import com.ldtteam.aequivaleo.api.compound.CompoundInstance;
import com.ldtteam.aequivaleo.api.results.IEquivalencyResults;
import net.minecraft.Util;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.*;


public class GlyphHelper {

    public static IEquivalencyResults getResults(ResourceKey<Level> levelKey) {
        return IAequivaleoAPI.getInstance().getEquivalencyResults(levelKey);
    }

    public static GlyphEntry getEntry(ResourceKey<Level> levelKey, ItemStack item) {
        List<CompoundInstance> finalResults = new ArrayList<>(getResults(levelKey).dataFor(item.getItem().getDefaultInstance()));
        if (item.hasCraftingRemainingItem() && !item.getCraftingRemainingItem().equals(item)){
            Set<CompoundInstance> containerValues = getResults(levelKey).dataFor(item.getCraftingRemainingItem());
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
        return new GlyphEntry(Sets.newHashSet(finalResults));
    }

    public static final Map<Integer, String> small = Util.make(() -> {
        Map<Integer, String> map = new HashMap<>();
        map.put(0, "\u2080");
        map.put(1, "\u2081");
        map.put(2, "\u2082");
        map.put(3, "\u2083");
        map.put(4, "\u2084");
        map.put(5, "\u2085");
        map.put(6, "\u2086");
        map.put(7, "\u2087");
        map.put(8, "\u2088");
        map.put(9, "\u2089");
        return map;
    });

    public static String convertDoubleToSmallString(int number){
        List<Character> list = Integer.toString(number).chars().mapToObj(c -> (char) c).toList();
        StringBuilder result = new StringBuilder();
        for(Character c : list){
            if(c == '.'){
                result.append("\u002E");
            } else {
                result.append(small.get(Integer.parseInt(c.toString())));
            }
        }
        return result.toString();
    }
}
