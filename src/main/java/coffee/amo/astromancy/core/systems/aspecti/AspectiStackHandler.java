package coffee.amo.astromancy.core.systems.aspecti;

import coffee.amo.astromancy.core.handlers.CapabilityAspectiHandler;
import coffee.amo.astromancy.core.util.AstroKeys;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.apache.logging.log4j.LogManager;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.logging.Logger;

public class AspectiStackHandler implements IAspectiHandler, INBTSerializable<CompoundTag> {

    private int capacity;
    private Consumer<AspectiStack> updateCallback;
    private Predicate<AspectiStack> validator;
    private AspectiStack aspectiStack = new AspectiStack();

    public AspectiStackHandler(int capacity, Consumer<AspectiStack> updateCallback, Predicate<AspectiStack> validator) {
        this.capacity = capacity;
        this.updateCallback = updateCallback;
        this.validator = validator;
    }

    public AspectiStackHandler(int capacity, Consumer<AspectiStack> updateCallback) {
        this(capacity, updateCallback, v -> true);
    }

    public AspectiStackHandler(int capacity) {
        this(capacity, AspectiStack::updateEmpty);
    }

    // getters

    public int getCapacity() {
        return capacity;
    }

    public Consumer<AspectiStack> getUpdateCallback() {
        return updateCallback;
    }

    public Predicate<AspectiStack> getValidator() {
        return validator;
    }

    public AspectiStack getAspectiStack() {
        return aspectiStack;
    }

    // setters

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setUpdateCallback(Consumer<AspectiStack> updateCallback) {
        this.updateCallback = updateCallback;
    }

    public void setValidator(Predicate<AspectiStack> validator) {
        this.validator = validator;
    }

    public void setAspectiStack(AspectiStack stack) {
        this.aspectiStack = stack;
    }

    // other methods

    public boolean isAspectiValid(AspectiStack stack) {
        return validator.test(stack);
    }

    public boolean isEmpty() {
        return aspectiStack.isEmpty();
    }

    public void setEmpty() {
        aspectiStack = new AspectiStack();
    }

    // serialization

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putInt(AstroKeys.KEY_ASPECTI_CAPACITY, capacity);
        tag.put(AstroKeys.KEY_ASPECTI_STACK, aspectiStack.serializeNBT());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        if(nbt != null) {
            capacity = nbt.getInt(AstroKeys.KEY_ASPECTI_CAPACITY);
            aspectiStack.deserializeNBT(nbt.getCompound(AstroKeys.KEY_ASPECTI_STACK));
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
    public AspectiStack getAspectiInTank(int tank) {
        return getAspectiStack();
    }

    @Override
    public int getTankCapacity(int tank) {
        return getCapacity();
    }

    @Override
    public boolean isAspectiValid(int tank, @Nonnull AspectiStack stack) {
        return isAspectiValid(stack);
    }

    @Override
    public int fill(AspectiStack stack, boolean simulate) {
        if(stack.isEmpty() || !isAspectiValid(stack))
            return 0; // empty or not allowed stack
        if(!stack.isEmpty() && !aspectiStack.isEmpty() && !aspectiStack.isSameAspecti(stack))
            return 0; // incompatible stacks

        int filled = Math.min(getSpace(), stack.getAmount());
        if(!simulate) { // modify only when not simulated
            if (aspectiStack.isEmpty())
                aspectiStack.set(stack.getAspecti(), filled);
            else aspectiStack.grow(filled);
            if (filled > 0) // notify if amount has changed
                onContentsChanged();
        }
        return filled;
    }

    @Nonnull
    @Override
    public AspectiStack drain(AspectiStack stack, boolean simulate) {
        if (stack.isEmpty() || !aspectiStack.isSameAspecti(stack))
            return AspectiStack.EMPTY;
        return drain(stack.getAmount(), simulate);
    }

    @Nonnull
    @Override
    public AspectiStack drain(int maxDrain, boolean simulate) {
        int drained = Math.min(aspectiStack.getAmount(), maxDrain);
        AspectiStack stack = new AspectiStack(aspectiStack, drained);
        if(!simulate && drained > 0) {
            aspectiStack.shrink(drained);
            onContentsChanged();
        }
        return stack;
    }

    // helper methods

    protected void onContentsChanged() {
        updateCallback.accept(aspectiStack);
    }

    public int getSpace() {
        return Math.max(0, capacity - aspectiStack.getAmount());
    }

    // capability provider

    public static class Provider implements ICapabilityProvider {

        private final AspectiStackHandler handler;
        private final LazyOptional<IAspectiHandler> optional;

        public Provider(int capacity, ItemStack updateCallbackItemStack) {
            handler = new AspectiStackHandler(capacity) {
                @Override
                public void onContentsChanged() {
                    handler.updateCallback.accept(handler.aspectiStack);
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
            stack.getOrCreateTag().put(AstroKeys.KEY_ASPECTI_TAG, handler.serializeNBT());
        }

        public void readFromItemStack(ItemStack stack) {
            if(stack.hasTag()) {
                CompoundTag tag = stack.getOrCreateTag();
                handler.deserializeNBT(tag.getCompound(AstroKeys.KEY_ASPECTI_TAG));
            } else {
                handler.setCapacity(0);
                handler.setEmpty();
            }
        }

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, Direction side) {
            return CapabilityAspectiHandler.ASPECTI_HANDLER_CAPABILITY.orEmpty(cap, optional);
        }
    }
}

