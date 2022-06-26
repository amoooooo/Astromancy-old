package coffee.amo.astromancy.core.systems.aspecti;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;

import javax.annotation.Nonnull;

public class AspectiStack {
    private Aspecti aspecti;
    private int count;
    private boolean isEmpty;
    public static final AspectiStack EMPTY = new AspectiStack(Aspecti.EMPTY, 0);
    public AspectiStack(Aspecti aspecti, int count) {
        this.aspecti = aspecti;
        this.count = count;
    }

    public AspectiStack(AspectiStack stack, int count){
        this.aspecti = stack.aspecti;
        this.count = count;
    }

    public static AspectiStack fromNbt(CompoundTag tag){
        if(tag==null){
            return EMPTY;
        }
        if(!tag.contains("aspecti")){
            return EMPTY;
        }
        Aspecti aspecti = Aspecti.values()[tag.getInt("aspecti")];
        int count = tag.getInt("count");
        return new AspectiStack(aspecti, count);
    }

    public CompoundTag toNbt(CompoundTag nbt){
        nbt.putInt("aspecti", aspecti.ordinal());
        nbt.putInt("count", count);
        return nbt;
    }

    public void writeToPacket(FriendlyByteBuf buf){
        buf.writeInt(aspecti.ordinal());
        buf.writeInt(count);
    }

    public static AspectiStack fromPacket(FriendlyByteBuf buf){
        int aspecti = buf.readInt();
        int count = buf.readInt();
        if(Aspecti.values()[aspecti] == Aspecti.EMPTY) return EMPTY;
        return new AspectiStack(Aspecti.values()[aspecti], count);
    }

    public final Aspecti getAspecti() {
        return isEmpty ? Aspecti.EMPTY : aspecti;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    protected void updateEmpty() {
        isEmpty = getAspecti() == Aspecti.EMPTY || count <= 0;
    }

    public int getCount() {
        return isEmpty ? 0 : count;
    }

    public void setCount(int count) {
        if (getAspecti() == Aspecti.EMPTY) throw new IllegalStateException("Cannot set count on empty aspecti stack");
        this.count = count;
        updateEmpty();
    }

    public void grow(int amount){
        setCount(this.count + amount);
    }

    public void shrink(int amount){
        setCount(this.count - amount);
    }

    public AspectiStack copy(){
        return new AspectiStack(getAspecti(), count);
    }

    public boolean equals(@Nonnull AspectiStack other){
        return getAspecti() == other.getAspecti();
    }

    public boolean containsAspecti(@Nonnull AspectiStack stack){
        return equals(stack) && getCount() >= stack.getCount();
    }

    public boolean isAspectiStackIdentical(@Nonnull AspectiStack stack){
        return equals(stack) && getCount() == stack.getCount();
    }

    public final boolean equals(Object obj) {
        if(!(obj instanceof AspectiStack)){
            return false;
        }
        return equals((AspectiStack)obj);
    }
}
