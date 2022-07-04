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
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
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

public class GlyphPhial extends Item {

    public GlyphPhial(Properties pProperties) {
        super(pProperties);
    }

    @Nonnull
    @Override
    public Component getName(ItemStack pStack) {
        // TODO: Refactor name
        MutableComponent name = Component.literal("Crude ");
        pStack.getCapability(CapabilityGlyphHandler.GLYPH_HANDLER_CAPABILITY).ifPresent(h -> {
            name.append(Component.literal(StringHelper.capitalize(h.getGlyphInTank(0).getGlyph().name().toLowerCase(Locale.ROOT))+ " "));
        });
        name.append(Component.literal("Phial"));
        return name;
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack pStack, @Nullable Level pLevel, @Nonnull List<Component> pTooltipComponents, @Nonnull TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        // maybe refactor?
        pStack.getCapability(CapabilityGlyphHandler.GLYPH_HANDLER_CAPABILITY).ifPresent(handler -> {
            Glyph glyph = handler.getGlyphInTank(0).getGlyph();
            int amount = handler.getGlyphInTank(0).getAmount();
            MutableComponent component = Component.literal("")
                    .append(Component.literal("[").withStyle(s->s.withFont(Astromancy.astromancy("glyph"))))
                    .append(Component.translatable("space.0").withStyle(s -> s.withFont(Astromancy.astromancy("negative_space"))))
                    .append(Component.translatable("space.-1").withStyle(s -> s.withFont(Astromancy.astromancy("negative_space"))))
                    .append(Component.literal(glyph.symbol()).withStyle(style -> style.withFont(Astromancy.astromancy("glyph"))))
                    .append(Component.translatable("space.0").withStyle(s -> s.withFont(Astromancy.astromancy("negative_space"))))
                    .append(Component.translatable("space.-1").withStyle(s -> s.withFont(Astromancy.astromancy("negative_space"))))
                    .append(GlyphEntry.intToComponent(amount))
                    .append(Component.literal("]").withStyle(s -> s.withFont(Astromancy.astromancy("glyph"))));
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
            LazyOptional<IGlyphHandler> phialCap = ph.getCapability(CapabilityGlyphHandler.GLYPH_HANDLER_CAPABILITY);
            LazyOptional<IGlyphHandler> targetCap = te.getCapability(CapabilityGlyphHandler.GLYPH_HANDLER_CAPABILITY);
            if(phialCap.isPresent() && targetCap.isPresent()) {
                IGlyphHandler phial = phialCap.orElseThrow(() -> new IllegalStateException("GlyphHandler is not present"));
                IGlyphHandler target = targetCap.orElseThrow(() -> new IllegalStateException("GlyphHandler is not present"));
                Player player = pContext.getPlayer();
                if(phial.getGlyphInTank(0).isEmpty()) {
                    GlyphStack glyphStack = target.drain(phial.getTankCapacity(0), true);
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
                    GlyphStack glyphStack = phial.getGlyphInTank(0).copy();
                    glyphStack.setAmount(target.fill(glyphStack, true));
                    if(!glyphStack.isEmpty()) {
                        target.fill(glyphStack, false);
                        if(player != null && !player.isCreative()) {
                            ph.shrink(1);
                            int newAmount = phial.getGlyphInTank(0).getAmount() - glyphStack.getAmount();
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
    public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        GlyphStackHandler.Provider provider = new GlyphStackHandler.Provider(16, stack);
        provider.readFromItemStack(stack);
        return provider;
    }

    public void fillItemCategory(@Nonnull CreativeModeTab pCategory, @Nonnull NonNullList<ItemStack> pItems) {
        if (allowedIn(pCategory)) {
            for(Glyph glyph : Glyph.values())
                pItems.add(createForGlyph(new GlyphStack(glyph, glyph == Glyph.EMPTY ? 0 : 16)));
        }
    }

    public static ItemStack createForGlyph(GlyphStack pInstance) {
        ItemStack stack = new ItemStack(ItemRegistry.GLYPH_PHIAL.get());
        stack.getCapability(CapabilityGlyphHandler.GLYPH_HANDLER_CAPABILITY).ifPresent(handler -> {
            if(handler instanceof GlyphStackHandler ash) {
                ash.setCapacity(16);
                ash.setGlyphStack(pInstance);
                stack.getOrCreateTag().put(AstromancyKeys.KEY_GLYPH_TAG, ash.serializeNBT());
            }
        });
        return stack;
    }

}
