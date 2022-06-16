package coffee.amo.astromancy.common.block.armillary_sphere;

import coffee.amo.astromancy.common.blockentity.armillary_sphere.ArmillarySphereCoreBlockEntity;
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

public class ArmillarySphereCoreBlock<T extends ArmillarySphereCoreBlockEntity> extends AstromancyEntityBlock<T> {
    public static final VoxelShape SHAPE = makeShape();
    public ArmillarySphereCoreBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    public static VoxelShape makeShape(){
        return Stream.of(
                Block.box(0, 0, 0, 16, 2, 16),
                Block.box(2, 2, 2, 14, 4, 14),
                Block.box(6, 4, 6, 10, 16, 10),
                Block.box(6, 2, 0, 10, 4, 16),
                Block.box(0, 2, 6, 16, 4, 10)
        ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    }
}
