//package coffee.amo.astromancy.common.block.research_table;
//
//import coffee.amo.astromancy.core.registration.ItemRegistry;
//import coffee.amo.astromancy.core.systems.multiblock.MultiblockComponentBlock;
//import net.minecraft.core.BlockPos;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.level.BlockGetter;
//import net.minecraft.world.level.block.state.BlockState;
//import net.minecraft.world.phys.HitResult;
//import org.jetbrains.annotations.Nullable;
//
//public class ResearchTableComponentBlock extends MultiblockComponentBlock {
//    public ResearchTableComponentBlock(Properties properties) {
//        super(properties);
//    }
//
//    @Override
//    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter world, BlockPos pos, Player player) {
//        return ItemRegistry.RESEARCH_TABLE.get().getDefaultInstance();
//    }
//
//    @Override
//    public void onBlockBroken(BlockState state, BlockGetter level, BlockPos pos, @Nullable Player player) {
//        super.onBlockBroken(state, level, pos, player);
//    }
//}
