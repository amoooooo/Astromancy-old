package coffee.amo.astromancy.common.item;

import coffee.amo.astromancy.Astromancy;
import coffee.amo.astromancy.aequivaleo.AspectiEntry;
import coffee.amo.astromancy.aequivaleo.AspectiHelper;
import coffee.amo.astromancy.core.helpers.StringHelper;
import coffee.amo.astromancy.core.systems.aspecti.Aspecti;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Locale;

public class AspectiPhial extends Item {
    public AspectiPhial(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        if(pStack.hasTag()){
            TextComponent tc = new TextComponent("");
            tc.append(new TextComponent("[").withStyle(s->s.withFont(Astromancy.astromancy("aspecti"))));
            tc.append(new TranslatableComponent("space.0").withStyle(s -> s.withFont(Astromancy.astromancy("negative_space"))));
            tc.append(new TranslatableComponent("space.-1").withStyle(s -> s.withFont(Astromancy.astromancy("negative_space"))));
            tc.append(new TextComponent(Aspecti.values()[((CompoundTag) pStack.getTag().get("aspecti")).getInt("aspecti")].symbol()).withStyle(style -> style.withFont(Astromancy.astromancy("aspecti"))));
            tc.append(new TranslatableComponent("space.0").withStyle(s -> s.withFont(Astromancy.astromancy("negative_space"))));
            tc.append(new TranslatableComponent("space.-1").withStyle(s -> s.withFont(Astromancy.astromancy("negative_space"))));
            tc.append(AspectiEntry.intToTextComponent(((CompoundTag)pStack.getTag().get("aspecti")).getInt("count")).append(new TextComponent("]").withStyle(s -> s.withFont(Astromancy.astromancy("aspecti")))));
            pTooltipComponents.add(tc);
        }
    }

    @Override
    public Component getName(ItemStack pStack) {
        if(pStack.hasTag()){
            return new TextComponent(StringHelper.capitalize(Aspecti.values()[((CompoundTag) pStack.getTag().get("aspecti")).getInt("aspecti")].name().toLowerCase(Locale.ROOT)) + " Phial");
        }
        return super.getName(pStack);
    }
}
