package coffee.amo.astromancy.common.item;

import coffee.amo.astromancy.Astromancy;
import coffee.amo.astromancy.aequivaleo.AspectiEntry;
import coffee.amo.astromancy.core.handlers.CapabilityAspectiHandler;
import coffee.amo.astromancy.core.helpers.StringHelper;
import coffee.amo.astromancy.core.registration.ItemRegistry;
import coffee.amo.astromancy.core.systems.aspecti.Aspecti;
import coffee.amo.astromancy.core.systems.aspecti.AspectiStack;
import coffee.amo.astromancy.core.systems.aspecti.AspectiStackHandler;
import coffee.amo.astromancy.core.systems.aspecti.IAspectiHandler;
import coffee.amo.astromancy.core.util.AstroKeys;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Locale;

public class AspectiPhial extends Item {

    public AspectiPhial(Properties pProperties) {
        super(pProperties);
    }

    @Nonnull
    @Override
    public Component getName(ItemStack pStack) {
        // TODO: Refactor name
        MutableComponent name = new TextComponent("Crude ");
        pStack.getCapability(CapabilityAspectiHandler.ASPECTI_HANDLER_CAPABILITY).ifPresent(h -> {
            name.append(new TextComponent(StringHelper.capitalize(h.getAspectiInTank(0).getAspecti().name().toLowerCase(Locale.ROOT))+ " "));
        });
        name.append(new TextComponent("Phial"));
        return name;
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack pStack, @Nullable Level pLevel, @Nonnull List<Component> pTooltipComponents, @Nonnull TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        // maybe refactor?
        pStack.getCapability(CapabilityAspectiHandler.ASPECTI_HANDLER_CAPABILITY).ifPresent(handler -> {
            Aspecti aspecti = handler.getAspectiInTank(0).getAspecti();
            int amount = handler.getAspectiInTank(0).getAmount();
            MutableComponent component = new TextComponent("")
                    .append(new TextComponent("[").withStyle(s->s.withFont(Astromancy.astromancy("aspecti"))))
                    .append(new TranslatableComponent("space.0").withStyle(s -> s.withFont(Astromancy.astromancy("negative_space"))))
                    .append(new TranslatableComponent("space.-1").withStyle(s -> s.withFont(Astromancy.astromancy("negative_space"))))
                    .append(new TextComponent(aspecti.symbol()).withStyle(style -> style.withFont(Astromancy.astromancy("aspecti"))))
                    .append(new TranslatableComponent("space.0").withStyle(s -> s.withFont(Astromancy.astromancy("negative_space"))))
                    .append(new TranslatableComponent("space.-1").withStyle(s -> s.withFont(Astromancy.astromancy("negative_space"))))
                    .append(AspectiEntry.intToTextComponent(amount))
                    .append(new TextComponent("]").withStyle(s -> s.withFont(Astromancy.astromancy("aspecti"))));
            pTooltipComponents.add(component);
        });
    }

    @Nonnull
    @Override
    public InteractionResult useOn(@Nonnull UseOnContext pContext) {
        Level level = pContext.getLevel();
        BlockPos pos = pContext.getClickedPos();
        BlockEntity te = level.getBlockEntity(pos);
        if(te != null) {
            ItemStack ph = pContext.getItemInHand();
            LazyOptional<IAspectiHandler> phialCap = ph.getCapability(CapabilityAspectiHandler.ASPECTI_HANDLER_CAPABILITY);
            LazyOptional<IAspectiHandler> targetCap = te.getCapability(CapabilityAspectiHandler.ASPECTI_HANDLER_CAPABILITY);
            if(phialCap.isPresent() && targetCap.isPresent()) {
                IAspectiHandler phial = phialCap.orElseThrow(() -> new IllegalStateException("AspectiHandler is not present"));
                IAspectiHandler target = targetCap.orElseThrow(() -> new IllegalStateException("AspectiHandler is not present"));
                Player player = pContext.getPlayer();
                if(phial.getAspectiInTank(0).isEmpty()) {
                    AspectiStack aspectiStack = target.drain(phial.getTankCapacity(0), true);
                    if(!aspectiStack.isEmpty()) {
                        target.drain(aspectiStack, false);
                        if(player != null && !player.isCreative()) {
                            ph.shrink(1);
                            player.addItem(createForAspecti(aspectiStack));
                            player.playSound(SoundEvents.ITEM_PICKUP, 1.0F, 1.0F);
                        }
                        te.setChanged();
                    }
                } else {
                    AspectiStack aspectiStack = phial.getAspectiInTank(0).copy();
                    aspectiStack.setAmount(target.fill(aspectiStack, true));
                    if(!aspectiStack.isEmpty()) {
                        target.fill(aspectiStack, false);
                        if(player != null && !player.isCreative()) {
                            ph.shrink(1);
                            int newAmount = phial.getAspectiInTank(0).getAmount() - aspectiStack.getAmount();
                            aspectiStack.setAmount(newAmount);
                            player.addItem(createForAspecti(newAmount > 0 ? aspectiStack : new AspectiStack()));
                            player.playSound(SoundEvents.ITEM_PICKUP, 1.0F, 1.0F);
                        }
                        te.setChanged();
                    }
                }
                return InteractionResult.SUCCESS;
            }
        }
        return super.useOn(pContext);
    }

    @Override
    public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        AspectiStackHandler.Provider provider = new AspectiStackHandler.Provider(16, stack);
        provider.readFromItemStack(stack);
        return provider;
    }

    public void fillItemCategory(@Nonnull CreativeModeTab pCategory, @Nonnull NonNullList<ItemStack> pItems) {
        if (allowdedIn(pCategory)) {
            for(Aspecti aspecti : Aspecti.values())
                pItems.add(createForAspecti(new AspectiStack(aspecti, aspecti == Aspecti.EMPTY ? 0 : 16)));
        }
    }

    public static ItemStack createForAspecti(AspectiStack pInstance) {
        ItemStack stack = new ItemStack(ItemRegistry.ASPECTI_PHIAL.get());
        stack.getCapability(CapabilityAspectiHandler.ASPECTI_HANDLER_CAPABILITY).ifPresent(handler -> {
            if(handler instanceof AspectiStackHandler ash) {
                ash.setCapacity(16);
                ash.setAspectiStack(pInstance);
                stack.getOrCreateTag().put(AstroKeys.KEY_ASPECTI_TAG, ash.serializeNBT());
            }
        });
        return stack;
    }

}
