package coffee.amo.astromancy.core.systems.aspecti;
/*
import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.function.Predicate;

public class AspectiTank implements IAspectiHandler, IAspectiTank {

    protected Predicate<AspectiStack> validator;
    @Nonnull
    protected AspectiStack aspecti = AspectiStack.EMPTY;
    protected int capacity;

    public AspectiTank(int capacity){
        this(capacity, e -> true);
    }

    public AspectiTank(int capacity, Predicate<AspectiStack> validator){
        this.capacity = capacity;
        this.validator = validator;
    }

    public AspectiTank setCapacity(int capacity){
        this.capacity = capacity;
        return this;
    }

    public AspectiTank setValidator(Predicate<AspectiStack> validator){
        this.validator = validator;
        return this;
    }

    public int getCapacity(){
        return capacity;
    }

    @Nonnull
    public AspectiStack getAspecti(){
        return aspecti;
    }

    public int getAspectiAmount(){
        return aspecti.getCount();
    }

    public AspectiTank fromNbt(CompoundTag nbt) {
        AspectiStack aspectiStack = AspectiStack.fromNbt(nbt);
        setAspecti(aspectiStack);
        return this;
    }

    public CompoundTag toNbt(CompoundTag nbt) {
        aspecti.toNbt(nbt);
        return nbt;
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
        return getCapacity();
    }

    @Override
    public boolean isAspectiValid(int tank, @NotNull Aspecti aspecti) {
        return false;
    }

    @Override
    public boolean isAspectiValid(@NotNull AspectiStack aspecti) {
        return validator.test(aspecti);
    }

    @Override
    public int fill(AspectiStack resource, AspectiAction action) {
        if(resource.isEmpty() || !isAspectiValid(resource)){
            return 0;
        }
        if(action.simulate()){
            if(aspecti.isEmpty()){
                return Math.min(capacity, resource.getCount());
            }
            if(!aspecti.equals(resource)){
                return 0;
            }
            return Math.min(capacity - aspecti.getCount(), resource.getCount());
        }
        if(aspecti.isEmpty()){
            aspecti = new AspectiStack(resource, Math.min(capacity, resource.getCount()));
            onContentsChanged();
            return aspecti.getCount();
        }
        if (!aspecti.equals(resource)) {
            return 0;
        }
        int filled = capacity - aspecti.getCount();

        if (resource.getCount() < filled) {
            aspecti.grow(resource.getCount());
            filled = resource.getCount();
        } else {
            aspecti.setCount(capacity);
        }
        if (filled > 0)
            onContentsChanged();
        return filled;
    }

    @NotNull
    @Override
    public AspectiStack drain(AspectiStack resource, AspectiAction action) {
        if (resource.isEmpty() || !resource.equals(aspecti)){
            return AspectiStack.EMPTY;
        }
        return drain(resource.getCount(), action);
    }

    protected void onContentsChanged(){

    }

    @NotNull
    @Override
    public AspectiStack drain(int maxDrain, AspectiAction action) {
        int drained = maxDrain;
        if (aspecti.getCount() < drained) {
            drained = aspecti.getCount();
        }
        AspectiStack stack = new AspectiStack(aspecti, drained);
        if(action.execute() && drained > 0){
            aspecti.shrink(drained);
            onContentsChanged();
        }
        return stack;
    }

    public void setAspecti(AspectiStack aspecti) {
        this.aspecti = aspecti;
    }

    public boolean isEmpty(){
        return aspecti.isEmpty();
    }

    public int getSpace(){
        return Math.max(0, capacity - aspecti.getCount());
    }
}*/
