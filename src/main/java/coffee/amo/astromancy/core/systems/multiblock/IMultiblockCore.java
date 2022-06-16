package coffee.amo.astromancy.core.systems.multiblock;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

import javax.annotation.Nullable;
import java.util.ArrayList;

public interface IMultiblockCore {
    ArrayList<BlockPos> getComponentPositions();

    MultiblockStructure getStructure();

    default void setupMultiblock(BlockPos pos) {
        getStructure().structurePieces.forEach(p -> {
            Vec3i offset = p.offset;
            getComponentPositions().add(pos.offset(offset));
        });
    }

    default void destroyMultiblock(@Nullable Player player, Level level, BlockPos pos) {
        getComponentPositions().forEach(p -> {
            if (level.getBlockEntity(p) instanceof MultiblockComponentEntity) {
                level.setBlock(p, Blocks.AIR.defaultBlockState(),0);
            }
        });
        boolean dropBlock = player == null || !player.isCreative();
        if (level.getBlockEntity(pos) instanceof MultiblockCoreEntity) {
            if(dropBlock) {
                level.destroyBlock(pos, dropBlock);
            } else {
                level.setBlock(pos, Blocks.AIR.defaultBlockState(),0);
            }
        }
    }
}
