package coffee.amo.astromancy.core.systems.lumen;

import coffee.amo.astromancy.core.util.AstromancyKeys;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nonnull;

public class LumenStack implements INBTSerializable<CompoundTag> {
    private Lumen lumen;
    private int amount;
    public static final LumenStack EMPTY = new LumenStack();

    public LumenStack(Lumen lumen, int amount) {
        this.lumen = lumen;
        this.amount = amount;
    }

    public LumenStack(LumenStack stack, int amount) {
        this(stack.lumen, amount);
    }

    public LumenStack() {
        this(Lumen.DECONSTRUCTIVE, 0);
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        if(nbt != null) {
            lumen = Lumen.get(nbt.getInt(AstromancyKeys.KEY_GLYPH_TYPE));
            amount = nbt.getInt(AstromancyKeys.KEY_GLYPH_AMOUNT);
        } else setEmpty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putInt(AstromancyKeys.KEY_GLYPH_TYPE, lumen.ordinal());
        tag.putInt(AstromancyKeys.KEY_GLYPH_AMOUNT, amount);
        return tag;
    }

    public void writeToPacket(FriendlyByteBuf buf){
        buf.writeInt(lumen.ordinal());
        buf.writeInt(amount);
    }

    public static LumenStack readFromPacket(FriendlyByteBuf buf){
        int type = buf.readInt();
        int amount = buf.readInt();
        LumenStack as = new LumenStack(Lumen.get(type), amount);
        as.updateEmpty(); // As a double check.
        return as;
    }

    public final Lumen getLumen() {
        return lumen;
    }

    public int getAmount() {
        return amount;
    }

    public void setLumen(Lumen lumen) {
        this.lumen = lumen;
        updateEmpty();
    }

    public void setAmount(int amount) {
        if (!isEmpty())
            this.amount = amount;
        updateEmpty();
    }

    public void set(Lumen lumen, int amount) {
        this.lumen = lumen;
        this.amount = amount;
        updateEmpty();
    }

    public void setEmpty() {
        lumen = Lumen.NONE;
        amount = 0;
    }

    public boolean isEmpty() {
        return lumen == Lumen.NONE || amount == 0;
    }

    public void updateEmpty() {
        if(isEmpty())
            setEmpty();
    }

    public void grow(int amount) {
        setAmount(Math.min(1024, this.amount + amount)); // max of 256 in stack?
    }

    public void shrink(int amount) {
        setAmount(Math.max(0, this.amount - amount));
    }

    public LumenStack copy() {
        return new LumenStack(lumen, amount);
    }

    public boolean isSameLumen(@Nonnull LumenStack other) {
        return getLumen() == other.getLumen();
    }

    public boolean isSameLumenStack(@Nonnull LumenStack stack) {
        return isSameLumen(stack) && amount == stack.getAmount();
    }
}
