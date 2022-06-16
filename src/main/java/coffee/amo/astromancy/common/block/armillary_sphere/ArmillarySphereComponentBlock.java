package coffee.amo.astromancy.common.block.armillary_sphere;

import coffee.amo.astromancy.core.registration.ItemRegistry;
import coffee.amo.astromancy.core.systems.multiblock.MultiblockComponentBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public class ArmillarySphereComponentBlock extends MultiblockComponentBlock {
    public static final VoxelShape SHAPE = makeShape();
    public static final VoxelShape RENDER_SHAPE = makeRenderShape();

    public ArmillarySphereComponentBlock(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter world, BlockPos pos, Player player) {
        return ItemRegistry.ARMILLARY_SPHERE.get().getDefaultInstance();
    }

    @Override
    public VoxelShape getInteractionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return SHAPE;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return RENDER_SHAPE;
    }

    public static VoxelShape makeShape(){
        return getVoxelShape();
    }

    @Override
    public void onBlockBroken(BlockState state, BlockGetter getter, BlockPos pos, @Nullable Player player) {
        super.onBlockBroken(state, getter, pos, player);
        //getter.getBlockState(pos.offset(0,1,0)).getBlock().playerDestroy((Level) getter, player, pos.offset(0,1,0), state, getter.getBlockEntity(pos.offset(0,1,0)), player.getItemInHand(InteractionHand.MAIN_HAND));
    }

    private static VoxelShape getVoxelShape() {
        return Stream.of(
                Block.box(0, 7, 0, 16, 8, 16),
                Block.box(6, 6, 6, 10, 10, 10),
                Block.box(0, 0, 7, 16, 16, 9),
                Block.box(7, 0, 0, 9, 8, 16),
                Block.box(7, 0, 0, 9, 16, 16)
        ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    }

    public static VoxelShape makeRenderShape(){
        return getVoxelShape();
    }
}
