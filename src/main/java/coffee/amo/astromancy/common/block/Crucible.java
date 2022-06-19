package coffee.amo.astromancy.common.block;

import coffee.amo.astromancy.common.blockentity.CrucibleBlockEntity;
import coffee.amo.astromancy.core.systems.block.AstromancyEntityBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class Crucible<T extends CrucibleBlockEntity> extends AstromancyEntityBlock<T> {
    public VoxelShape SHAPE = Block.box(0,0,0,16,15,16);
    public Crucible(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }
}
