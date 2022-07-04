package coffee.amo.astromancy.common.item;

import coffee.amo.astromancy.Astromancy;
import coffee.amo.astromancy.aequivaleo.GlyphEntry;
import coffee.amo.astromancy.core.handlers.CapabilityGlyphHandler;
import coffee.amo.astromancy.core.helpers.StringHelper;
import coffee.amo.astromancy.core.registration.ItemRegistry;
import coffee.amo.astromancy.core.systems.glyph.Glyph;
import coffee.amo.astromancy.core.systems.glyph.GlyphStack;
import coffee.amo.astromancy.core.systems.glyph.GlyphStackHandler;
import coffee.amo.astromancy.core.systems.glyph.IGlyphHandler;
import coffee.amo.astromancy.core.util.AstromancyKeys;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
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
            if(((CompoundTag)((CompoundTag)((CompoundTag) stack.getTag().get("BlockEntityTag")).get("Glyph")).get("GlyphStack")).getInt("Type") != 0){
                CompoundTag tag = (CompoundTag) ((CompoundTag) ((CompoundTag) stack.getTag().get("BlockEntityTag")).get("Glyph")).get("GlyphStack");
                return Component.translatable("block.astromancy.jar").append(" of " + StringHelper.capitalize(Glyph.values()[tag.getInt("Type")].name().toLowerCase(Locale.ROOT)) + " Slurry");
            }
        }
        return super.getName(stack);
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable Level pLevel, @Nonnull List<Component> pTooltipComponents, @Nonnull TooltipFlag pIsAdvanced) {
        super.appendHoverText(stack, pLevel, pTooltipComponents, pIsAdvanced);
        // maybe refactor?
        if(stack.hasTag() && stack.getTag().get("BlockEntityTag") != null){
            if(((CompoundTag)((CompoundTag)((CompoundTag) stack.getTag().get("BlockEntityTag")).get("Glyph")).get("GlyphStack")).getInt("Type") != 0){
                CompoundTag tag = (CompoundTag) ((CompoundTag) ((CompoundTag) stack.getTag().get("BlockEntityTag")).get("Glyph")).get("GlyphStack");
                MutableComponent tc = Component.literal("");
                tc.append(Component.literal("[").withStyle(s -> s.withFont(Astromancy.astromancy("glyph"))));
                tc.append(Component.translatable("space.0").withStyle(s -> s.withFont(Astromancy.astromancy("negative_space"))));
                tc.append(Component.translatable("space.-1").withStyle(s -> s.withFont(Astromancy.astromancy("negative_space"))));
                tc.append(Component.literal(Glyph.values()[tag.getInt("Type")].symbol()).withStyle(style -> style.withFont(Astromancy.astromancy("glyph"))));
                tc.append(Component.translatable("space.0").withStyle(s -> s.withFont(Astromancy.astromancy("negative_space"))));
                tc.append(Component.translatable("space.-1").withStyle(s -> s.withFont(Astromancy.astromancy("negative_space"))));
                tc.append(GlyphEntry.intToComponent(tag.getInt("Amount")).append(Component.literal("]").withStyle(s -> s.withFont(Astromancy.astromancy("glyph")))));
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
            LazyOptional<IGlyphHandler> jarCap = ph.getCapability(CapabilityGlyphHandler.GLYPH_HANDLER_CAPABILITY);
            LazyOptional<IGlyphHandler> targetCap = te.getCapability(CapabilityGlyphHandler.GLYPH_HANDLER_CAPABILITY);
            if(jarCap.isPresent() && targetCap.isPresent()) {
                IGlyphHandler jar = jarCap.orElseThrow(() -> new IllegalStateException("GlyphHandler is not present"));
                IGlyphHandler target = targetCap.orElseThrow(() -> new IllegalStateException("GlyphHandler is not present"));
                Player player = pContext.getPlayer();
                if(jar.getGlyphInTank(0).isEmpty()) {
                    GlyphStack glyphStack = target.drain(jar.getTankCapacity(0), true);
                    if(!glyphStack.isEmpty()) {
                        target.drain(glyphStack, false);
                        if(player != null && !player.isCreative()) {
                            ph.shrink(1);
                            player.addItem(createForGlyph(glyphStack));
                            player.playSound(SoundEvents.ITEM_PICKUP, 1.0F, 1.0F);
                        }
                        te.setChanged();
                    }
                } else {
                    GlyphStack glyphStack = jar.getGlyphInTank(0).copy();
                    glyphStack.setAmount(target.fill(glyphStack, true));
                    if(!glyphStack.isEmpty()) {
                        target.fill(glyphStack, false);
                        if(player != null && !player.isCreative()) {
                            ph.shrink(1);
                            int newAmount = jar.getGlyphInTank(0).getAmount() - glyphStack.getAmount();
                            glyphStack.setAmount(newAmount);
                            player.addItem(createForGlyph(newAmount > 0 ? glyphStack : new GlyphStack()));
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
        GlyphStackHandler.Provider provider = new GlyphStackHandler.Provider(256, stack);
        provider.readFromItemStack(stack);
        return provider;
    }

    public static ItemStack createForGlyph(GlyphStack pInstance) {
        ItemStack stack = new ItemStack(ItemRegistry.JAR.get());
        stack.getCapability(CapabilityGlyphHandler.GLYPH_HANDLER_CAPABILITY).ifPresent(handler -> {
            if(handler instanceof GlyphStackHandler ash) {
                ash.setCapacity(256);
                ash.setGlyphStack(pInstance);
                stack.getOrCreateTag().put(AstromancyKeys.KEY_GLYPH_TAG, ash.serializeNBT());
            }
        });
        return stack;
    }
}
