package coffee.amo.astromancy.common.block;

import coffee.amo.astromancy.common.blockentity.ArmillarySphereBlockEntity;
import com.sammy.ortus.systems.block.OrtusEntityBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.stream.Stream;

public class ArmillarySphere<T extends ArmillarySphereBlockEntity> extends OrtusEntityBlock<T> {
    public final VoxelShape SHAPE = Stream.of(
            Block.box(7.5, 6, 4.5, 8.5, 11, 5.5),
            Block.box(7.5, 5, 4.5, 8.5, 6, 11.5),
            Block.box(7.5, 11, 4.5, 8.5, 12, 11.5),
            Block.box(7.5, 6, 10.5, 8.5, 11, 11.5),
            Block.box(6.5, 8, 9.5, 9.5, 9, 10.5),
            Block.box(6.5, 8, 5.5, 9.5, 9, 6.5),
            Block.box(9.5, 8, 5.5, 10.5, 9, 10.5),
            Block.box(5.5, 8, 5.5, 6.5, 9, 10.5),
            Block.box(7.5, 8, 7.5, 8.5, 9, 8.5),
            Block.box(4, 0, 4, 12, 1, 12),
            Block.box(7, 1, 7, 9, 5, 9)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    public ArmillarySphere(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH));
    }

    @Override
    public BlockState rotate(BlockState pState, Rotation pRotation) {
        return pState.setValue(FACING, pRotation.rotate(pState.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }
}
