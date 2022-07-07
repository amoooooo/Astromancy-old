package coffee.amo.astromancy.core.systems.lumen;

import coffee.amo.astromancy.core.handlers.CapabilityLumenHandler;
import coffee.amo.astromancy.core.util.AstromancyKeys;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class LumenStackHandler implements ILumenHandler, INBTSerializable<CompoundTag> {

    private int capacity;
    private Consumer<LumenStack> updateCallback;
    private Predicate<LumenStack> validator;
    private LumenStack lumenStack = new LumenStack();

    public LumenStackHandler(int capacity, Consumer<LumenStack> updateCallback, Predicate<LumenStack> validator) {
        this.capacity = capacity;
        this.updateCallback = updateCallback;
        this.validator = validator;
    }

    public LumenStackHandler(int capacity, Consumer<LumenStack> updateCallback) {
        this(capacity, updateCallback, v -> true);
    }

    public LumenStackHandler(int capacity) {
        this(capacity, LumenStack::updateEmpty);
    }

    // getters

    public int getCapacity() {
        return capacity;
    }

    public Consumer<LumenStack> getUpdateCallback() {
        return updateCallback;
    }

    public Predicate<LumenStack> getValidator() {
        return validator;
    }

    public LumenStack getLumenStack() {
        return lumenStack;
    }

    // setters

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setUpdateCallback(Consumer<LumenStack> updateCallback) {
        this.updateCallback = updateCallback;
    }

    public void setValidator(Predicate<LumenStack> validator) {
        this.validator = validator;
    }

    public void setLumenStack(LumenStack lumenStack) {
        this.lumenStack = lumenStack;
    }

    // other methods

    public boolean isLumenValid(LumenStack stack){
        return validator.test(stack);
    }

    public boolean isEmpty() { return lumenStack.isEmpty(); }

    public void setEmpty() { lumenStack = new LumenStack(); }

    // serialization

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putInt(AstromancyKeys.KEY_LUMEN_CAPACITY, capacity);
        tag.put(AstromancyKeys.KEY_LUMEN_STACK, lumenStack.serializeNBT());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        if(nbt != null){
            capacity = nbt.getInt(AstromancyKeys.KEY_LUMEN_CAPACITY);
            lumenStack.deserializeNBT(nbt.getCompound(AstromancyKeys.KEY_LUMEN_STACK));
        } else {
            capacity = 0;
            setEmpty();
        }
    }

    @Override
    public int getTanks() {
        return 1;
    }

    @NotNull
    @Override
    public LumenStack getLumenInTank(int tank) {
        return getLumenStack();
    }

    @Override
    public int getTankCapacity(int tank) {
        return getCapacity();
    }

    @Override
    public boolean isLumenValid(int tank, @NotNull LumenStack stack) {
        return isLumenValid(stack);
    }

    @Override
    public int fill(LumenStack resource, boolean simulate) {
        if(resource.isEmpty() || !isLumenValid(resource)) return 0;
        if(!resource.isEmpty() && !lumenStack.isEmpty() && !lumenStack.isSameLumen(resource)) return 0;

        int filled = Math.min(getSpace(), resource.getAmount());
        if(!simulate){
            if(lumenStack.isEmpty())
                lumenStack.set(resource.getLumen(), filled);
            else lumenStack.grow(filled);
            if(filled > 0) onContentsChanged();;
        }
        return filled;
    }

    @NotNull
    @Override
    public LumenStack drain(LumenStack resource, boolean simulate) {
        if (resource.isEmpty() || !lumenStack.isSameLumen(resource)) return LumenStack.EMPTY;
        return drain(resource.getAmount(), simulate);
    }

    @NotNull
    @Override
    public LumenStack drain(int maxDrain, boolean simulate) {
        int drained = Math.min(lumenStack.getAmount(), maxDrain);
        LumenStack stack = new LumenStack(lumenStack, drained);
        if(!simulate && drained > 0){
            lumenStack.shrink(drained);
            onContentsChanged();
        }
        return stack;
    }

    // helper methods
    protected void onContentsChanged() {
        updateCallback.accept(lumenStack);
    }

    public int getSpace(){
        return Math.max(0, capacity - lumenStack.getAmount());
    }

    // cap provider

    public static class Provider implements ICapabilityProvider {
        private final LumenStackHandler handler;
        private final LazyOptional<ILumenHandler> optional;

        public Provider(int capacity, ItemStack updateCallbackItemStack){
            handler = new LumenStackHandler(capacity){
                @Override
                protected void onContentsChanged() {
                    handler.updateCallback.accept(handler.lumenStack);
                    writeToItemStack(updateCallbackItemStack);
                }
            };
            optional = LazyOptional.of(() -> handler);
        }

        public void invalidate() { optional.invalidate(); }

        public void writeToItemStack(ItemStack stack){
            stack.getOrCreateTag().put(AstromancyKeys.KEY_LUMEN_TAG, handler.serializeNBT());
        }

        public void readFromItemStack(ItemStack stack){
            if(stack.hasTag()){
                CompoundTag tag = stack.getOrCreateTag();
                handler.deserializeNBT(tag.getCompound(AstromancyKeys.KEY_LUMEN_TAG));
            } else {
                handler.setCapacity(0);
                handler.setEmpty();
            }
        }

        @Override
        public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
            return CapabilityLumenHandler.LUMEN_HANDLER_CAPABILITY.orEmpty(cap, optional);
        }
    }
}
