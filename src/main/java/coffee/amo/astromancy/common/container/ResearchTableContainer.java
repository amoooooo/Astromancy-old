//package coffee.amo.astromancy.common.container;
//
//import coffee.amo.astromancy.common.blockentity.research_table.ResearchTableCoreBlockEntity;
//import coffee.amo.astromancy.common.item.AspectiPhial;
//import coffee.amo.astromancy.core.registration.ContainerRegistry;
//import net.minecraft.world.Container;
//import net.minecraft.world.SimpleContainer;
//import net.minecraft.world.entity.player.Inventory;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.inventory.AbstractContainerMenu;
//import net.minecraft.world.inventory.MenuType;
//import net.minecraft.world.inventory.Slot;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.item.Items;
//import net.minecraft.world.level.block.entity.BlockEntity;
//import org.jetbrains.annotations.Nullable;
//
//public class ResearchTableContainer extends AbstractContainerMenu {
//    private final ResearchTableCoreBlockEntity desk;
//    public ResearchTableContainer(int windowId, Inventory playerinv, ItemStack desk) {
//        //this(ContainerRegistry.RESEARCH_TABLE.get(), windowId, playerinv, new SimpleContainer(72));
//        super(ContainerRegistry.RESEARCH_TABLE.get(), windowId, playerinv, new SimpleContainer(72));
//    }
//
//    public ResearchTableContainer(MenuType<? extends ResearchTableContainer> containerType, int containerId, Inventory playerinv, ResearchTableCoreBlockEntity be){
//        super(containerType, containerId);
//        this.desk = be;
//        desk.startOpen(playerinv.player);
//        for (int i = 0; i < desk.getContainerSize() / 10f; ++i){
//            for (int j = 0; j < 9; ++j){
//                int index = i * 9 + j;
//                addSlot(new Slot(desk, index, 12 + j * 18, 30 + i * 18){
//                    @Override
//                    public boolean mayPlace(ItemStack pStack) {
//                        return pStack.getItem() instanceof AspectiPhial;
//                    }
//                });
//            }
//        }
//        addSlot(new Slot(desk, 0, 137, 11){
//            @Override
//            public boolean mayPlace(ItemStack pStack) {
//                return pStack.getItem() == Items.PAPER;
//            }
//        });
//        addSlot(new Slot(desk, 0, 155, 11){
//            @Override
//            public boolean mayPlace(ItemStack pStack) {
//                return pStack.getItem() == Items.INK_SAC;
//            }
//        });
//        int offset = offset();
//        for (int l = 0; l < 3; l++){
//            for (int j1 = 0; j1 < 9; j1++){
//                this.addSlot(new Slot(playerinv, j1 + (l + 1) * 9, 138 + j1 * 18, offset + 218 + l * 18));
//            }
//        }
//        for (int i1 = 0; i1 < 3; i1++){
//            for (int i2 = 0; i2 < 3; i2++){
//                this.addSlot(new Slot(playerinv, i1 + i2, 78 + i2 * 18, offset + 218 + i1 * 18));
//            }
//        }
//    }
//
//    @Override
//    public void removed(Player pPlayer) {
//        super.removed(pPlayer);
//        this.desk.stopOpen(pPlayer);
//    }
//
//    public int offset() {
//        return 0;
//    }
//
//    @Override
//    public boolean stillValid(Player pPlayer) {
//        return this.desk.stillValid(pPlayer);
//    }
//
//    @Override
//    public ItemStack quickMoveStack(Player pPlayer, int index) {
//        ItemStack itemstack = ItemStack.EMPTY;
//        Slot slot = this.slots.get(index);
//        if (slot.hasItem()) {
//            ItemStack itemstack1 = slot.getItem();
//            itemstack = itemstack1.copy();
//            if (index < this.desk.getContainerSize()) {
//                if (!this.moveItemStackTo(itemstack1, this.desk.getContainerSize(), this.slots.size(), true)) {
//                    return ItemStack.EMPTY;
//                }
//            } else if (!this.moveItemStackTo(itemstack1, 0, this.desk.getContainerSize(), false)) {
//                return ItemStack.EMPTY;
//            }
//
//            if (itemstack1.isEmpty()) {
//                slot.set(ItemStack.EMPTY);
//            } else {
//                slot.setChanged();
//            }
//        }
//        return itemstack;
//    }
//}
