//package coffee.amo.astromancy.common.blockentity.research_table;
//
//import coffee.amo.astromancy.common.container.ResearchTableContainer;
//import coffee.amo.astromancy.core.registration.BlockEntityRegistration;
//import coffee.amo.astromancy.core.registration.BlockRegistration;
//import coffee.amo.astromancy.core.systems.multiblock.HorizontalDirectionStructure;
//import coffee.amo.astromancy.core.systems.multiblock.MultiblockCoreEntity;
//import coffee.amo.astromancy.core.systems.multiblock.MultiblockStructure;
//import net.minecraft.core.BlockPos;
//import net.minecraft.core.Direction;
//import net.minecraft.nbt.CompoundTag;
//import net.minecraft.network.chat.Component;
//import net.minecraft.network.chat.TextComponent;
//import net.minecraft.server.level.ServerPlayer;
//import net.minecraft.world.*;
//import net.minecraft.world.entity.player.Inventory;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.inventory.AbstractContainerMenu;
//import net.minecraft.world.level.block.entity.BlockEntity;
//import net.minecraft.world.level.block.entity.BlockEntityType;
//import net.minecraft.world.level.block.state.BlockState;
//import net.minecraft.world.phys.BlockHitResult;
//import net.minecraftforge.common.capabilities.Capability;
//import net.minecraftforge.common.util.LazyOptional;
//import net.minecraftforge.items.CapabilityItemHandler;
//import net.minecraftforge.items.IItemHandler;
//import net.minecraftforge.items.ItemStackHandler;
//import net.minecraftforge.network.NetworkHooks;
//import org.jetbrains.annotations.NotNull;
//import org.jetbrains.annotations.Nullable;
//
//import java.util.function.Supplier;
//
//public class ResearchTableCoreBlockEntity extends MultiblockCoreEntity implements MenuProvider {
//    private final ItemStackHandler itemHandler = new ItemStackHandler(72){
//        @Override
//        protected void onContentsChanged(int slot) {
//            setChanged();
//        }
//    };
//
//    @Override
//    public InteractionResult onUse(Player player, InteractionHand hand, BlockHitResult ray) {
//        if(!level.isClientSide){
//            BlockEntity be = level.getBlockEntity(worldPosition);
//            if(be instanceof ResearchTableCoreBlockEntity){
//                NetworkHooks.openGui((ServerPlayer)player, (ResearchTableCoreBlockEntity)be, worldPosition);
//            } else {
//                throw new IllegalStateException("Container provider is missing!")
//            }
//        }
//        return super.onUse(player, hand, ray);
//    }
//
//    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
//
//    public static final Supplier<HorizontalDirectionStructure> STRUCTURE = () -> (HorizontalDirectionStructure.of(new MultiblockStructure.StructurePiece(1,0,0, BlockRegistration.RESEARCH_TABLE_COMPONENT.get().defaultBlockState())));
//    public ResearchTableCoreBlockEntity(BlockEntityType<?> type, MultiblockStructure structure, BlockPos pos, BlockState state) {
//        super(type, structure, pos, state);
//    }
//
//    public ResearchTableCoreBlockEntity(BlockPos pos, BlockState state){
//        super(BlockEntityRegistration.RESEARCH_TABLE.get(), STRUCTURE.get(), pos, state);
//    }
//
//    @Override
//    public InteractionResult onUse(Player player, InteractionHand hand) {
//        return super.onUse(player, hand);
//    }
//
//    @Override
//    public Component getDisplayName() {
//        return new TextComponent("Research Table");
//    }
//
//    @Nullable
//    @Override
//    public AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory, Player pPlayer) {
//        return new ResearchTableContainer(pContainerId, pInventory, pPlayer, new SimpleContainer(pInventory.items));
//    }
//
//    @NotNull
//    @Override
//    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
//        if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
//            return lazyItemHandler.cast();
//        }
//        return super.getCapability(cap, side);
//    }
//
//    @Override
//    public void onLoad() {
//        super.onLoad();
//        lazyItemHandler = LazyOptional.of(() -> itemHandler);
//    }
//
//    @Override
//    public void invalidateCaps() {
//        super.invalidateCaps();
//        lazyItemHandler.invalidate();
//    }
//
//    @Override
//    protected void saveAdditional(CompoundTag pTag) {
//        pTag.put("Inventory", itemHandler.serializeNBT());
//        super.saveAdditional(pTag);
//    }
//
//    @Override
//    public void load(CompoundTag pTag) {
//        super.load(pTag);
//        itemHandler.deserializeNBT(pTag.getCompound("Inventory"));
//    }
//
//    public void drops(){
//        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
//        for (int i = 0; i < itemHandler.getSlots(); i++ ){
//            inventory.setItem(i, itemHandler.getStackInSlot(i));
//        }
//
//        Containers.dropContents(this.level, this.worldPosition, inventory);
//    }
//}
