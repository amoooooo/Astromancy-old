package coffee.amo.astromancy.common.block.mortar;

import coffee.amo.astromancy.common.blockentity.mortar.MortarBlockEntity;
import coffee.amo.astromancy.core.systems.block.AstromancyEntityBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.stream.Stream;

public class MortarBlock<T extends MortarBlockEntity> extends AstromancyEntityBlock<T> {
    public VoxelShape SHAPE = Stream.of(
            Block.box(12, 1, 3, 13, 5, 13),
            Block.box(4, 0, 4, 12, 2, 12),
            Block.box(4, 1, 12, 12, 5, 13),
            Block.box(3, 1, 3, 4, 5, 13),
            Block.box(4, 1, 3, 12, 5, 4)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    public MortarBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }
}
