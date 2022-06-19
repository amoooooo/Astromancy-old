package coffee.amo.astromancy.aequivaleo;

import coffee.amo.astromancy.Astromancy;
import coffee.amo.astromancy.aequivaleo.compound.AspectiCompoundType;
import com.ldtteam.aequivaleo.api.compound.CompoundInstance;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;


public class AspectiEntry {
    public List<AspectiInstance> aspecti = new ArrayList<>();
    public final TextComponent LBRACKET = (TextComponent) new TextComponent("[").withStyle(s -> s.withFont(Astromancy.astromancy("aspecti")));
    public final TextComponent RBRACKET = (TextComponent) new TextComponent("]").withStyle(s -> s.withFont(Astromancy.astromancy("aspecti")));
    public TranslatableComponent PLUS_1 = (TranslatableComponent) new TranslatableComponent("space.0").withStyle(s -> s.withFont(Astromancy.astromancy("negative_space")));
    public TranslatableComponent NEGATIVE_1 = (TranslatableComponent) new TranslatableComponent("space.-1").withStyle(s -> s.withFont(Astromancy.astromancy("negative_space")));

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
        TextComponent tooltip = (TextComponent) LBRACKET.copy();
        tooltip.append(PLUS_1);
        tooltip.append(NEGATIVE_1);
        for(AspectiInstance instance : aspecti){
            tooltip.append(new TextComponent(instance.type.aspecti.symbol()).withStyle(s -> s.withFont(Astromancy.astromancy("aspecti"))));
            tooltip.append(PLUS_1);
            tooltip.append(NEGATIVE_1);
            tooltip.append(intToTextComponent((int)instance.amount)).withStyle(s -> s.withFont(Astromancy.astromancy("aspecti")));
            tooltip.append(PLUS_1);
        }
        tooltip.append(RBRACKET);
        return tooltip;
    }

    public static TextComponent intToTextComponent(int i){
        String num = Integer.toString(i);
        char[] chars = num.toCharArray();
        TextComponent result = new TextComponent("");
        for(char c : chars){
            result.append(new TextComponent(String.valueOf(c)).withStyle(s -> s.withFont(Astromancy.astromancy("aspecti"))));
            result.append(new TranslatableComponent("space.-1").withStyle(s -> s.withFont(Astromancy.astromancy("negative_space"))));
        }
        return result;
    }
}
