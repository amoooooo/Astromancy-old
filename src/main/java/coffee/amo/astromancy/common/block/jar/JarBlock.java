package coffee.amo.astromancy.common.block.jar;

import coffee.amo.astromancy.Astromancy;
import coffee.amo.astromancy.client.helper.ClientRenderHelper;
import coffee.amo.astromancy.common.blockentity.jar.JarBlockEntity;
import coffee.amo.astromancy.core.registration.ItemRegistry;
import coffee.amo.astromancy.core.systems.block.AstromancyEntityBlock;
import coffee.amo.astromancy.core.systems.blockentity.AstromancyBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Stream;

public class JarBlock<T extends JarBlockEntity> extends AstromancyEntityBlock<T> {

    public static final Property<Integer> COUNT = IntegerProperty.create("count", 0, 16);

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(COUNT, 0);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(COUNT);
        super.createBlockStateDefinition(pBuilder);
    }

    public VoxelShape SHAPE = Stream.of(
            Block.box(4, 11, 4, 12, 14, 12),
            Block.box(3, 0, 3, 13, 12, 13)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    public JarBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if(!(pNewState.is(pState.getBlock()))){
            super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult ray) {
        if (hasTileEntity(state)) {
            if (level.getBlockEntity(pos) instanceof AstromancyBlockEntity simpleBlockEntity) {
                return simpleBlockEntity.onUse(player, hand, ray);
            }
        }
        return super.use(state, level, pos, player, hand, ray);
    }

    @Override
    public void attack(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer) {
        if(!pLevel.isClientSide && pPlayer.isCrouching()){
            if(pLevel.getBlockEntity(pPos) instanceof JarBlockEntity j) {
                if(j.label){
                    j.popLabel();
                }
            }
        }
        super.attack(pState, pLevel, pPos, pPlayer);
    }
}
