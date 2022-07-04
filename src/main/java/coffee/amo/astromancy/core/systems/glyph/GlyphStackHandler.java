package coffee.amo.astromancy.core.systems.glyph;

import coffee.amo.astromancy.core.handlers.CapabilityGlyphHandler;
import coffee.amo.astromancy.core.util.AstromancyKeys;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class GlyphStackHandler implements IGlyphHandler, INBTSerializable<CompoundTag> {

    private int capacity;
    private Consumer<GlyphStack> updateCallback;
    private Predicate<GlyphStack> validator;
    private GlyphStack glyphStack = new GlyphStack();

    public GlyphStackHandler(int capacity, Consumer<GlyphStack> updateCallback, Predicate<GlyphStack> validator) {
        this.capacity = capacity;
        this.updateCallback = updateCallback;
        this.validator = validator;
    }

    public GlyphStackHandler(int capacity, Consumer<GlyphStack> updateCallback) {
        this(capacity, updateCallback, v -> true);
    }

    public GlyphStackHandler(int capacity) {
        this(capacity, GlyphStack::updateEmpty);
    }

    // getters

    public int getCapacity() {
        return capacity;
    }

    public Consumer<GlyphStack> getUpdateCallback() {
        return updateCallback;
    }

    public Predicate<GlyphStack> getValidator() {
        return validator;
    }

    public GlyphStack getGlyphStack() {
        return glyphStack;
    }

    // setters

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setUpdateCallback(Consumer<GlyphStack> updateCallback) {
        this.updateCallback = updateCallback;
    }

    public void setValidator(Predicate<GlyphStack> validator) {
        this.validator = validator;
    }

    public void setGlyphStack(GlyphStack stack) {
        this.glyphStack = stack;
    }

    // other methods

    public boolean isGlyphValid(GlyphStack stack) {
        return validator.test(stack);
    }

    public boolean isEmpty() {
        return glyphStack.isEmpty();
    }

    public void setEmpty() {
        glyphStack = new GlyphStack();
    }

    // serialization

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putInt(AstromancyKeys.KEY_GLYPH_CAPACITY, capacity);
        tag.put(AstromancyKeys.KEY_GLYPH_STACK, glyphStack.serializeNBT());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        if(nbt != null) {
            capacity = nbt.getInt(AstromancyKeys.KEY_GLYPH_CAPACITY);
            glyphStack.deserializeNBT(nbt.getCompound(AstromancyKeys.KEY_GLYPH_STACK));
        } else {
            capacity = 0;
            setEmpty();
        }
    }

    // capability

    @Override
    public int getTanks() {
        return 1;
    }

    @Nonnull
    @Override
    public GlyphStack getGlyphInTank(int tank) {
        return getGlyphStack();
    }

    @Override
    public int getTankCapacity(int tank) {
        return getCapacity();
    }

    @Override
    public boolean isGlyphValid(int tank, @Nonnull GlyphStack stack) {
        return isGlyphValid(stack);
    }

    @Override
    public int fill(GlyphStack stack, boolean simulate) {
        if(stack.isEmpty() || !isGlyphValid(stack))
            return 0; // empty or not allowed stack
        if(!stack.isEmpty() && !glyphStack.isEmpty() && !glyphStack.isSameGlyph(stack))
            return 0; // incompatible stacks

        int filled = Math.min(getSpace(), stack.getAmount());
        if(!simulate) { // modify only when not simulated
            if (glyphStack.isEmpty())
                glyphStack.set(stack.getGlyph(), filled);
            else glyphStack.grow(filled);
            if (filled > 0) // notify if amount has changed
                onContentsChanged();
        }
        return filled;
    }

    @Nonnull
    @Override
    public GlyphStack drain(GlyphStack stack, boolean simulate) {
        if (stack.isEmpty() || !glyphStack.isSameGlyph(stack))
            return GlyphStack.EMPTY;
        return drain(stack.getAmount(), simulate);
    }

    @Nonnull
    @Override
    public GlyphStack drain(int maxDrain, boolean simulate) {
        int drained = Math.min(glyphStack.getAmount(), maxDrain);
        GlyphStack stack = new GlyphStack(glyphStack, drained);
        if(!simulate && drained > 0) {
            glyphStack.shrink(drained);
            onContentsChanged();
        }
        return stack;
    }

    // helper methods

    protected void onContentsChanged() {
        updateCallback.accept(glyphStack);
    }

    public int getSpace() {
        return Math.max(0, capacity - glyphStack.getAmount());
    }

    // capability provider

    public static class Provider implements ICapabilityProvider {

        private final GlyphStackHandler handler;
        private final LazyOptional<IGlyphHandler> optional;

        public Provider(int capacity, ItemStack updateCallbackItemStack) {
            handler = new GlyphStackHandler(capacity) {
                @Override
                public void onContentsChanged() {
                    handler.updateCallback.accept(handler.glyphStack);
                    writeToItemStack(updateCallbackItemStack);
                }
            };
            optional = LazyOptional.of(() -> handler);
        }

        // Not needed for this example, but here for completeness.
        public void invalidate() {
            optional.invalidate();
        }

        // Not using INBTSerializable to avoid the default, ugly serialization.
        public void writeToItemStack(ItemStack stack) {
            stack.getOrCreateTag().put(AstromancyKeys.KEY_GLYPH_TAG, handler.serializeNBT());
        }

        public void readFromItemStack(ItemStack stack) {
            if(stack.hasTag()) {
                CompoundTag tag = stack.getOrCreateTag();
                handler.deserializeNBT(tag.getCompound(AstromancyKeys.KEY_GLYPH_TAG));
            } else {
                handler.setCapacity(0);
                handler.setEmpty();
            }
        }

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, Direction side) {
            return CapabilityGlyphHandler.GLYPH_HANDLER_CAPABILITY.orEmpty(cap, optional);
        }
    }
}

