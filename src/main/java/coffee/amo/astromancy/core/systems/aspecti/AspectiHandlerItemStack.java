package coffee.amo.astromancy.core.systems.aspecti;
/*
import coffee.amo.astromancy.core.handlers.CapabilityAspectiHandler;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class AspectiHandlerItemStack implements IAspectiHandlerItem, ICapabilityProvider {

    public static final String ASPECTI_NBT_KEY = "Aspecti";

    private final LazyOptional<IAspectiHandlerItem> holder = LazyOptional.of(() -> this);

    @Nonnull
    protected ItemStack container;
    protected int capacity;

    public AspectiHandlerItemStack(@Nonnull ItemStack container, int capacity){
        this.container = container;
        this.capacity = capacity;
    }

    public AspectiStack getAspecti() {
        CompoundTag tag = container.getTag();
        if (tag == null || !tag.contains(ASPECTI_NBT_KEY))
            return AspectiStack.EMPTY;
        return AspectiStack.fromNbt(tag.getCompound(ASPECTI_NBT_KEY));
    }

    protected void setAspecti(AspectiStack aspecti) {
        if (!container.hasTag())
            container.setTag(new CompoundTag());
        CompoundTag aspectiTag = new CompoundTag();
        aspecti.toNbt(aspectiTag);
        container.getTag().put(ASPECTI_NBT_KEY, aspectiTag);
    }

    @Override
    public int getTanks() {
        return 1;
    }

    @NotNull
    @Override
    public AspectiStack getAspectiInTank(int tank) {
        return getAspecti();
    }

    @Override
    public int getTankCapacity(int tank) {
        return capacity;
    }

    @Override
    public boolean isAspectiValid(int tank, @NotNull Aspecti aspecti) {
        return true;
    }

    @Override
    public int fill(AspectiStack resource, AspectiAction action) {
        if (container.getCount() != 1 || resource.isEmpty() || !canFillAspectiType(resource))
            return 0;
        AspectiStack contained = getAspecti();
        if (contained.isEmpty()) {
            int fillAmount = Math.min(capacity, resource.getCount());
            if(action.execute()){
                AspectiStack filled = resource.copy();
                filled.setCount(fillAmount);
                setAspecti(filled);
            }
            return fillAmount;
        } else {
            if(contained.equals(resource)){
                int fillAmount = Math.min(capacity - contained.getCount(), resource.getCount());
                if(action.execute() && fillAmount > 0){
                    contained.grow(fillAmount);
                    setAspecti(contained);
                }
                return fillAmount;
            }

            return 0;
        }
    }

    @NotNull
    @Override
    public AspectiStack drain(AspectiStack resource, AspectiAction action) {
        if(container.getCount() != 1 || resource.isEmpty() || !canDrainAspectiType(resource))
            return AspectiStack.EMPTY;
        return drain(resource.getCount(), action);
    }

    @NotNull
    @Override
    public AspectiStack drain(int maxDrain, AspectiAction action) {
        if (container.getCount() != 1 || maxDrain <= 0)
            return AspectiStack.EMPTY;
        AspectiStack contained = getAspecti();
        if (contained.isEmpty() || !canDrainAspectiType(contained))
            return AspectiStack.EMPTY;
        final int drainAmount = Math.min(contained.getCount(), maxDrain);

        AspectiStack drained = contained.copy();
        drained.setCount(drainAmount);

        if(action.execute()){
            contained.shrink(drainAmount);
            if(contained.isEmpty()){
                setContainerToEmpty();
            } else {
                setAspecti(contained);
            }
        }
        return drained;
    }

    public boolean canFillAspectiType(AspectiStack aspecti){
        return true;
    }

    public boolean canDrainAspectiType(AspectiStack aspecti){
        return true;
    }

    protected void setContainerToEmpty(){
        container.removeTagKey(ASPECTI_NBT_KEY);
    }

    @NotNull
    @Override
    public ItemStack getContainer() {
        return container;
    }
    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return CapabilityAspectiHandler.ASPECTI_HANDLER_ITEM_CAPABILITY.orEmpty(cap, holder);
    }

    public static class Consumable extends AspectiHandlerItemStack {
        public Consumable(@Nonnull ItemStack container, int capacity) {
            super(container, capacity);
        }

        @Override
        protected void setContainerToEmpty() {
            super.setContainerToEmpty();
            container.shrink(1);
        }
    }

public static class NonConsumable extends AspectiHandlerItemStack {
        protected final ItemStack emptyContainer;
        public NonConsumable(@Nonnull ItemStack container, ItemStack emptyContainer,int capacity) {
            super(container, capacity);
            this.emptyContainer = emptyContainer;
        }

        @Override
        protected void setContainerToEmpty() {
            super.setContainerToEmpty();
            container = emptyContainer;
        }
    }
}*/
