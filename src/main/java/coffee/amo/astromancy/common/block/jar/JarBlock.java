package coffee.amo.astromancy.common.block.jar;

import coffee.amo.astromancy.Astromancy;
import coffee.amo.astromancy.common.blockentity.jar.JarBlockEntity;
import coffee.amo.astromancy.core.registration.ItemRegistry;
import coffee.amo.astromancy.core.systems.block.AstromancyEntityBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;
import java.util.stream.Stream;

public class JarBlock<T extends JarBlockEntity> extends AstromancyEntityBlock<T> {
    private static final ResourceLocation JARDROPS = Astromancy.astromancy("jardrops");
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
    public List<ItemStack> getDrops(BlockState pState, LootContext.Builder pBuilder) {
        BlockEntity be = pBuilder.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
        if(be instanceof JarBlockEntity){
            JarBlockEntity jbe = (JarBlockEntity) be;
            pBuilder = pBuilder.withDynamicDrop(JARDROPS, (lootContext, stackConsumer) -> {
                if (jbe.getAspecti() != null) {
                    ItemStack stack = ItemRegistry.JAR.get().getDefaultInstance();
                    CompoundTag tag = (CompoundTag) stack.getOrCreateTag().get("aspecti");
                    tag.putInt("aspecti", jbe.getAspecti().ordinal());
                    tag.putInt("count", jbe.getCount());
                    stackConsumer.accept(stack);
                }
            });
        }
        return super.getDrops(pState, pBuilder);
    }


}
