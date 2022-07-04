package coffee.amo.astromancy.core.systems.glyph;

import coffee.amo.astromancy.core.util.AstromancyKeys;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nonnull;

public class GlyphStack implements INBTSerializable<CompoundTag> {
    private Glyph glyph;
    private int amount;
    public static final GlyphStack EMPTY = new GlyphStack();

    public GlyphStack(Glyph glyph, int amount) {
        this.glyph = glyph;
        this.amount = amount;
    }

    public GlyphStack(GlyphStack stack, int amount) {
        this(stack.glyph, amount);
    }

    public GlyphStack() {
        this(Glyph.EMPTY, 0);
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        if(nbt != null) {
            glyph = Glyph.get(nbt.getInt(AstromancyKeys.KEY_GLYPH_TYPE));
            amount = nbt.getInt(AstromancyKeys.KEY_GLYPH_AMOUNT);
        } else setEmpty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putInt(AstromancyKeys.KEY_GLYPH_TYPE, glyph.ordinal());
        tag.putInt(AstromancyKeys.KEY_GLYPH_AMOUNT, amount);
        return tag;
    }

    public void writeToPacket(FriendlyByteBuf buf){
        buf.writeInt(glyph.ordinal());
        buf.writeInt(amount);
    }

    public static GlyphStack readFromPacket(FriendlyByteBuf buf){
        int type = buf.readInt();
        int amount = buf.readInt();
        GlyphStack as = new GlyphStack(Glyph.get(type), amount);
        as.updateEmpty(); // As a double check.
        return as;
    }

    public final Glyph getGlyph() {
        return glyph;
    }

    public int getAmount() {
        return amount;
    }

    public void setGlyph(Glyph glyph) {
        this.glyph = glyph;
        updateEmpty();
    }

    public void setAmount(int amount) {
        if (!isEmpty())
            this.amount = amount;
        updateEmpty();
    }

    public void set(Glyph glyph, int amount) {
        this.glyph = glyph;
        this.amount = amount;
        updateEmpty();
    }

    public void setEmpty() {
        glyph = Glyph.EMPTY;
        amount = 0;
    }

    public boolean isEmpty() {
        return glyph == Glyph.EMPTY || amount == 0;
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

    public GlyphStack copy() {
        return new GlyphStack(glyph, amount);
    }

    public boolean isSameGlyph(@Nonnull GlyphStack other) {
        return getGlyph() == other.getGlyph();
    }

    public boolean isSameGlyphStack(@Nonnull GlyphStack stack) {
        return isSameGlyph(stack) && amount == stack.getAmount();
    }
}
