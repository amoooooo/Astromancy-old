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
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Locale;

public class JarItem extends BlockItem {
    public JarItem(Block pBlock, Properties pProperties) {
        super(pBlock, pProperties);
    }

    @Nonnull
    @Override
    public Component getName(ItemStack stack) {
        // TODO: Refactor name
        if(stack.hasTag() && stack.getTag().get("BlockEntityTag") != null){
            if(((CompoundTag)((CompoundTag)((CompoundTag) stack.getTag().get("BlockEntityTag")).get("Aspecti")).get("AspectiStack")).getInt("Type") != 0){
                CompoundTag tag = (CompoundTag) ((CompoundTag) ((CompoundTag) stack.getTag().get("BlockEntityTag")).get("Aspecti")).get("AspectiStack");
                return Component.translatable("block.astromancy.jar").append(" of " + StringHelper.capitalize(Aspecti.values()[tag.getInt("Type")].name().toLowerCase(Locale.ROOT)) + " Slurry");
            }
        }
        return super.getName(stack);
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable Level pLevel, @Nonnull List<Component> pTooltipComponents, @Nonnull TooltipFlag pIsAdvanced) {
        super.appendHoverText(stack, pLevel, pTooltipComponents, pIsAdvanced);
        // maybe refactor?
        if(stack.hasTag() && stack.getTag().get("BlockEntityTag") != null){
            if(((CompoundTag)((CompoundTag)((CompoundTag) stack.getTag().get("BlockEntityTag")).get("Aspecti")).get("AspectiStack")).getInt("Type") != 0){
                CompoundTag tag = (CompoundTag) ((CompoundTag) ((CompoundTag) stack.getTag().get("BlockEntityTag")).get("Aspecti")).get("AspectiStack");
                MutableComponent tc = Component.literal("");
                tc.append(Component.literal("[").withStyle(s -> s.withFont(Astromancy.astromancy("aspecti"))));
                tc.append(Component.translatable("space.0").withStyle(s -> s.withFont(Astromancy.astromancy("negative_space"))));
                tc.append(Component.translatable("space.-1").withStyle(s -> s.withFont(Astromancy.astromancy("negative_space"))));
                tc.append(Component.literal(Aspecti.values()[tag.getInt("Type")].symbol()).withStyle(style -> style.withFont(Astromancy.astromancy("aspecti"))));
                tc.append(Component.translatable("space.0").withStyle(s -> s.withFont(Astromancy.astromancy("negative_space"))));
                tc.append(Component.translatable("space.-1").withStyle(s -> s.withFont(Astromancy.astromancy("negative_space"))));
                tc.append(AspectiEntry.intToComponent(tag.getInt("Amount")).append(Component.literal("]").withStyle(s -> s.withFont(Astromancy.astromancy("aspecti")))));
                pTooltipComponents.add(tc);
            }
        }
    }

    @Nonnull
    @Override
    public InteractionResult useOn(@Nonnull UseOnContext pContext) {
        Level level = pContext.getLevel();
        BlockPos pos = pContext.getClickedPos();
        BlockEntity te = level.getBlockEntity(pos);
        if(te != null) {
            ItemStack ph = pContext.getItemInHand();
            LazyOptional<IAspectiHandler> jarCap = ph.getCapability(CapabilityAspectiHandler.ASPECTI_HANDLER_CAPABILITY);
            LazyOptional<IAspectiHandler> targetCap = te.getCapability(CapabilityAspectiHandler.ASPECTI_HANDLER_CAPABILITY);
            if(jarCap.isPresent() && targetCap.isPresent()) {
                IAspectiHandler jar = jarCap.orElseThrow(() -> new IllegalStateException("AspectiHandler is not present"));
                IAspectiHandler target = targetCap.orElseThrow(() -> new IllegalStateException("AspectiHandler is not present"));
                Player player = pContext.getPlayer();
                if(jar.getAspectiInTank(0).isEmpty()) {
                    AspectiStack aspectiStack = target.drain(jar.getTankCapacity(0), true);
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
                    AspectiStack aspectiStack = jar.getAspectiInTank(0).copy();
                    aspectiStack.setAmount(target.fill(aspectiStack, true));
                    if(!aspectiStack.isEmpty()) {
                        target.fill(aspectiStack, false);
                        if(player != null && !player.isCreative()) {
                            ph.shrink(1);
                            int newAmount = jar.getAspectiInTank(0).getAmount() - aspectiStack.getAmount();
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
    public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @javax.annotation.Nullable CompoundTag nbt) {
        AspectiStackHandler.Provider provider = new AspectiStackHandler.Provider(256, stack);
        provider.readFromItemStack(stack);
        return provider;
    }

    public static ItemStack createForAspecti(AspectiStack pInstance) {
        ItemStack stack = new ItemStack(ItemRegistry.JAR.get());
        stack.getCapability(CapabilityAspectiHandler.ASPECTI_HANDLER_CAPABILITY).ifPresent(handler -> {
            if(handler instanceof AspectiStackHandler ash) {
                ash.setCapacity(256);
                ash.setAspectiStack(pInstance);
                stack.getOrCreateTag().put(AstroKeys.KEY_ASPECTI_TAG, ash.serializeNBT());
            }
        });
        return stack;
    }
}
