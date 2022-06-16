package coffee.amo.astromancy.core.helpers;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class BlockHelper {
    public static Vec3 fromBlockPos(BlockPos pos) {
        return new Vec3(pos.getX(), pos.getY(), pos.getZ());
    }

    public static void saveBlockPos(CompoundTag compoundNBT, BlockPos pos) {
        compoundNBT.putInt("X", pos.getX());
        compoundNBT.putInt("Y", pos.getY());
        compoundNBT.putInt("Z", pos.getZ());
    }

    /**
     * Saves a block position to nbt with extra text to differentiate it.
     */
    public static void saveBlockPos(CompoundTag compoundNBT, BlockPos pos, String extra) {
        compoundNBT.putInt(extra + "_X", pos.getX());
        compoundNBT.putInt(extra + "_Y", pos.getY());
        compoundNBT.putInt(extra + "_Z", pos.getZ());
    }

    /**
     * Loads a block position from nbt.
     */
    public static BlockPos loadBlockPos(CompoundTag tag) {
        return tag.contains("X") ? new BlockPos(tag.getInt("X"), tag.getInt("Y"), tag.getInt("Z")) : null;
    }

    /**
     * Loads a block position from nbt with extra text as input.
     */
    public static BlockPos loadBlockPos(CompoundTag tag, String extra) {
        return tag.contains(extra + "_X") ? new BlockPos(tag.getInt(extra + "_X"), tag.getInt(extra + "_Y"), tag.getInt(extra + "_Z")) : null;
    }
    public static void updateAndNotifyState(Level level, BlockPos pos) {
        updateAndNotifyState(level.getBlockState(pos), level, pos);
    }

    public static void updateAndNotifyState(BlockState state, Level level, BlockPos pos) {
        updateState(state, level, pos);
        state.updateNeighbourShapes(level, pos, 2);
        level.updateNeighbourForOutputSignal(pos, state.getBlock());
    }

    public static void updateState(BlockState state, Level level, BlockPos pos) {
        level.sendBlockUpdated(pos, state, state, 2);
        level.blockEntityChanged(pos);
    }
}
