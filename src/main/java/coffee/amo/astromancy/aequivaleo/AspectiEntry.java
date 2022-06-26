package coffee.amo.astromancy.aequivaleo;

import coffee.amo.astromancy.Astromancy;
import coffee.amo.astromancy.aequivaleo.compound.AspectiCompoundType;
import com.ldtteam.aequivaleo.api.compound.CompoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;


public class AspectiEntry {
    public List<AspectiInstance> aspecti = new ArrayList<>();
    public final Component LBRACKET = Component.literal("[").withStyle(s -> s.withFont(Astromancy.astromancy("aspecti")));
    public final Component RBRACKET =  Component.literal("]").withStyle(s -> s.withFont(Astromancy.astromancy("aspecti")));
    public Component PLUS_1 =  Component.translatable("space.0").withStyle(s -> s.withFont(Astromancy.astromancy("negative_space")));
    public Component NEGATIVE_1 = Component.translatable("space.-1").withStyle(s -> s.withFont(Astromancy.astromancy("negative_space")));

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

    public MutableComponent getTooltip(){
        MutableComponent tooltip = LBRACKET.copy();
        tooltip.append(PLUS_1);
        tooltip.append(NEGATIVE_1);
        for(AspectiInstance instance : aspecti){
            tooltip.append(Component.literal(instance.type.aspecti.symbol()).withStyle(s -> s.withFont(Astromancy.astromancy("aspecti"))));
            tooltip.append(PLUS_1);
            tooltip.append(NEGATIVE_1);
            tooltip.append(intToComponent((int)instance.amount)).withStyle(s -> s.withFont(Astromancy.astromancy("aspecti")));
            tooltip.append(PLUS_1);
        }
        tooltip.append(RBRACKET);
        return tooltip;
    }

    public static MutableComponent intToComponent(int i){
        String num = Integer.toString(i);
        char[] chars = num.toCharArray();
        MutableComponent result = Component.literal("");
        for(char c : chars){
            result.append(Component.literal(String.valueOf(c)).withStyle(s -> s.withFont(Astromancy.astromancy("aspecti"))));
            result.append(Component.translatable("space.-1").withStyle(s -> s.withFont(Astromancy.astromancy("negative_space"))));
        }
        return result;
    }
}
