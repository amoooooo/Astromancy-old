package coffee.amo.astromancy.common.item;

import coffee.amo.astromancy.core.helpers.StringHelper;
import coffee.amo.astromancy.core.systems.aspecti.Aspecti;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
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
            pTooltipComponents.add(new TextComponent(StringHelper.capitalize(Aspecti.values()[((CompoundTag) pStack.getTag().get("aspecti")).getInt("aspecti")].name().toLowerCase(Locale.ROOT)) + ": " + ((CompoundTag) pStack.getTag().get("aspecti")).getInt("count")).withStyle(s -> s.withColor(ChatFormatting.GOLD)));
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
