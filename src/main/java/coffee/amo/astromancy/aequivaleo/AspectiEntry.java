package coffee.amo.astromancy.aequivaleo;

import coffee.amo.astromancy.Astromancy;
import coffee.amo.astromancy.aequivaleo.compound.AspectiCompoundType;
import com.ldtteam.aequivaleo.api.compound.CompoundInstance;
import net.minecraft.network.chat.TextComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class AspectiEntry {
    public List<AspectiInstance> aspecti = new ArrayList<>();

    public AspectiEntry(Set<CompoundInstance> compoundInstances) {
        this.aspecti.addAll(compoundInstances.stream()
                .filter(c -> c.getType() instanceof AspectiCompoundType)
                .filter(c -> c.getAmount() > 0)
                .map(c -> new AspectiInstance(Objects.requireNonNull(((AspectiCompoundType) c.getType())), c.getAmount())).toList()
        );
    }

    public String asString(){
        StringBuilder result = new StringBuilder();
        for(AspectiInstance instance : aspecti){
            result.append(instance.type.aspecti.toString()).append(" ").append("[").append(instance.amount).append("]");
        }
        return result.toString();
    }

    // sort by amount
    public AspectiEntry sort(){
        List<AspectiInstance> sorted = new ArrayList<>(aspecti);
        sorted.sort((a, b) -> Double.compare(b.amount, a.amount));
        aspecti = sorted;
        return this;
    }

    public TextComponent getTooltip(){
        TextComponent tooltip = new TextComponent("");
        for(AspectiInstance instance : aspecti){
            tooltip.append(new TextComponent(instance.type.aspecti.symbol()).withStyle(s -> s.withFont(Astromancy.astromancy("aspecti"))));
            tooltip.append(new TextComponent(AspectiHelper.convertDoubleToSmallString((int) instance.amount)));
            tooltip.append(" ");
        }
        return tooltip;
    }
}
