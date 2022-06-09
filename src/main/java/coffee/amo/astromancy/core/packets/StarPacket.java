package coffee.amo.astromancy.core.packets;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;

public class StarPacket {
    public final BlockPos pos;
    public final CompoundTag tag;

    public StarPacket(BlockPos pos, CompoundTag tag) {
        this.pos = pos;
        this.tag = tag;
    }
}
