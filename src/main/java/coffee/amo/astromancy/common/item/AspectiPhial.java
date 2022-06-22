package coffee.amo.astromancy.common.item;

import coffee.amo.astromancy.Astromancy;
import coffee.amo.astromancy.aequivaleo.AspectiEntry;
import coffee.amo.astromancy.aequivaleo.AspectiHelper;
import coffee.amo.astromancy.core.handlers.CapabilityAspectiHandler;
import coffee.amo.astromancy.core.helpers.StringHelper;
import coffee.amo.astromancy.core.registration.ItemRegistry;
import coffee.amo.astromancy.core.systems.aspecti.*;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Locale;

public class AspectiPhial extends ItemAspectiNonConsumableContainer {
    private final ItemStack container;
    public AspectiPhial(Properties pProperties) {
        super(pProperties, ItemRegistry.EMPTY_ASPECTI_PHIAL.get().getDefaultInstance(), 16);
        container = ItemRegistry.EMPTY_ASPECTI_PHIAL.get().getDefaultInstance();
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        if(pStack.getCapability(CapabilityAspectiHandler.ASPECTI_HANDLER_ITEM_CAPABILITY).isPresent()){
            ICapabilityProvider provider = pStack;
            LazyOptional<IAspectiHandlerItem> aspectiHandlerItem = provider.getCapability(CapabilityAspectiHandler.ASPECTI_HANDLER_ITEM_CAPABILITY);
            aspectiHandlerItem.ifPresent(s -> {
                IAspectiHandlerItem handler = aspectiHandlerItem.orElseThrow(NullPointerException::new);
                Aspecti asp = handler.getAspectiInTank(0).getAspecti();
                pTooltipComponents.add(new TextComponent(asp.name()));
            });
//            if(aspectiHandlerItem.isPresent()) {
//                IAspectiHandlerItem handler = aspectiHandlerItem.orElseThrow(NullPointerException::new);
//                Aspecti asp = handler.getAspectiInTank(1).getAspecti();
//                TextComponent tc = new TextComponent("");
//                tc.append(new TextComponent("[").withStyle(s->s.withFont(Astromancy.astromancy("aspecti"))));
//                tc.append(new TranslatableComponent("space.0").withStyle(s -> s.withFont(Astromancy.astromancy("negative_space"))));
//                tc.append(new TranslatableComponent("space.-1").withStyle(s -> s.withFont(Astromancy.astromancy("negative_space"))));
//                tc.append(new TextComponent(asp.symbol()).withStyle(style -> style.withFont(Astromancy.astromancy("aspecti"))));
//                tc.append(new TranslatableComponent("space.0").withStyle(s -> s.withFont(Astromancy.astromancy("negative_space"))));
//                tc.append(new TranslatableComponent("space.-1").withStyle(s -> s.withFont(Astromancy.astromancy("negative_space"))));
//                tc.append(AspectiEntry.intToTextComponent(handler.getAspectiInTank(1).getCount()).append(new TextComponent("]").withStyle(s -> s.withFont(Astromancy.astromancy("aspecti")))));
//                pTooltipComponents.add(tc);
//            }
        }
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        ItemStack stack = pContext.getItemInHand();
        LazyOptional<IAspectiHandlerItem> aspectiHandlerItem = stack.getCapability(CapabilityAspectiHandler.ASPECTI_HANDLER_ITEM_CAPABILITY);
        aspectiHandlerItem.ifPresent(s -> {
            s.fill(new AspectiStack(Aspecti.LILITHIA, 16), IAspectiHandler.AspectiAction.EXECUTE);
        });
        return super.useOn(pContext);
    }

    @Override
    public Component getName(ItemStack pStack) {
        if(pStack.getCapability(CapabilityAspectiHandler.ASPECTI_HANDLER_ITEM_CAPABILITY).isPresent()){
            LazyOptional<IAspectiHandlerItem> aspectiHandlerItem = pStack.getCapability(CapabilityAspectiHandler.ASPECTI_HANDLER_ITEM_CAPABILITY);
            if(aspectiHandlerItem.isPresent()) {
                IAspectiHandlerItem handler = aspectiHandlerItem.orElseThrow(NullPointerException::new);
                Aspecti asp = handler.getAspectiInTank(1).getAspecti();
                return new TextComponent("Crude " + StringHelper.capitalize(asp.name().toLowerCase(Locale.ROOT)) + " Phial");
            }
        }
        return super.getName(pStack);
    }
}
