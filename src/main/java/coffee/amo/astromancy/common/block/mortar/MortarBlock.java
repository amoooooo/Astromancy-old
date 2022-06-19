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
            Block.box(7.5, 0.5, 6, 8.5, 4.5, 7),
            Block.box(6, 0, 6, 10, 1, 10),
            Block.box(5, 1, 6, 6, 3, 10),
            Block.box(10, 1, 6, 11, 3, 10),
            Block.box(6, 1, 10, 10, 3, 11),
            Block.box(6, 1, 5, 10, 3, 6)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    public MortarBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }
}
