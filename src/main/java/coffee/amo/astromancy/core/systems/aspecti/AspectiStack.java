package coffee.amo.astromancy.core.systems.aspecti;

import coffee.amo.astromancy.core.util.AstroKeys;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nonnull;

public class AspectiStack implements INBTSerializable<CompoundTag> {
    private Aspecti aspecti;
    private int amount;
    public static final AspectiStack EMPTY = new AspectiStack();

    public AspectiStack(Aspecti aspecti, int amount) {
        this.aspecti = aspecti;
        this.amount = amount;
    }

    public AspectiStack(AspectiStack stack, int amount) {
        this(stack.aspecti, amount);
    }

    public AspectiStack() {
        this(Aspecti.EMPTY, 0);
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        if(nbt != null) {
            aspecti = Aspecti.get(nbt.getInt(AstroKeys.KEY_ASPECTI_TYPE));
            amount = nbt.getInt(AstroKeys.KEY_ASPECTI_AMOUNT);
        } else setEmpty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putInt(AstroKeys.KEY_ASPECTI_TYPE, aspecti.ordinal());
        tag.putInt(AstroKeys.KEY_ASPECTI_AMOUNT, amount);
        return tag;
    }

    public void writeToPacket(FriendlyByteBuf buf){
        buf.writeInt(aspecti.ordinal());
        buf.writeInt(amount);
    }

    public static AspectiStack readFromPacket(FriendlyByteBuf buf){
        int type = buf.readInt();
        int amount = buf.readInt();
        AspectiStack as = new AspectiStack(Aspecti.get(type), amount);
        as.updateEmpty(); // As a double check.
        return as;
    }

    public final Aspecti getAspecti() {
        return aspecti;
    }

    public int getAmount() {
        return amount;
    }

    public void setAspecti(Aspecti aspecti) {
        this.aspecti = aspecti;
        updateEmpty();
    }

    public void setAmount(int amount) {
        if (!isEmpty())
            this.amount = amount;
        updateEmpty();
    }

    public void set(Aspecti aspecti, int amount) {
        this.aspecti = aspecti;
        this.amount = amount;
        updateEmpty();
    }

    public void setEmpty() {
        aspecti = Aspecti.EMPTY;
        amount = 0;
    }

    public boolean isEmpty() {
        return aspecti == Aspecti.EMPTY || amount == 0;
    }

    public void updateEmpty() {
        if(isEmpty())
            setEmpty();
    }

    public void grow(int amount) {
        setAmount(Math.min(256, this.amount + amount)); // max of 256 in stack?
    }

    public void shrink(int amount) {
        setAmount(Math.max(0, this.amount - amount));
    }

    public AspectiStack copy() {
        return new AspectiStack(aspecti, amount);
    }

    public boolean isSameAspecti(@Nonnull AspectiStack other) {
        return getAspecti() == other.getAspecti();
    }

    public boolean isSameAspectiStack(@Nonnull AspectiStack stack) {
        return isSameAspecti(stack) && amount == stack.getAmount();
    }
}
