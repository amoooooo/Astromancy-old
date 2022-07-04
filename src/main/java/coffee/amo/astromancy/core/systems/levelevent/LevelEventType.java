package coffee.amo.astromancy.core.systems.levelevent;

import net.minecraft.nbt.CompoundTag;

public class LevelEventType {

    public final String id;
    public final EventInstanceSupplier supplier;

    public LevelEventType(String id, EventInstanceSupplier supplier){
        this.id = id;
        this.supplier = supplier;
    }

    public LevelEventInstance createInstance(CompoundTag tag){
        return supplier.getInstance().fromNbt(tag);
    }

    public interface EventInstanceSupplier {
        LevelEventInstance getInstance();
    }
}
