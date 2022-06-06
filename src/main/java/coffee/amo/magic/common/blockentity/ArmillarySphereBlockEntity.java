package coffee.amo.magic.common.blockentity;

import com.sammy.ortus.helpers.BlockHelper;
import com.sammy.ortus.systems.blockentity.ItemHolderBlockEntity;
import com.sammy.ortus.systems.blockentity.OrtusBlockEntityInventory;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

// TODO: fix inventory
public class ArmillarySphereBlockEntity extends ItemHolderBlockEntity {
    public OrtusBlockEntityInventory ring2;
    public OrtusBlockEntityInventory ring3;
    public ArmillarySphereBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        inventory = new OrtusBlockEntityInventory(4, 1){
            @Override
            public void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                BlockHelper.updateAndNotifyState(level, worldPosition);
            }
        };
        ring2 = new OrtusBlockEntityInventory(4, 1){
            @Override
            public void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                BlockHelper.updateAndNotifyState(level, worldPosition);
            }
        };
        ring3 = new OrtusBlockEntityInventory(4, 1){
            @Override
            public void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                BlockHelper.updateAndNotifyState(level, worldPosition);
            }
        };
    }
    @Override
    public InteractionResult onUse(Player player, InteractionHand hand) {
        if(!inventory.items.contains(Items.AIR)) {
            if(!ring2.items.contains(Items.AIR)) {
                if(!ring3.items.contains(Items.AIR)){
                    if(player.getItemInHand(hand).getItem().equals(ItemStack.EMPTY)){
                        super.onUse(player, hand);
                    } else {
                        return InteractionResult.PASS;
                    }

                }
                ring3.interact(player.level, player, hand);
                return InteractionResult.SUCCESS;
            }
            ring2.interact(player.level, player, hand);
            return  InteractionResult.SUCCESS;
        }
        inventory.interact(player.level, player, hand);
        return InteractionResult.SUCCESS;
    }

    @Override
    public void onBreak(@Nullable Player player) {
        inventory.dumpItems(level, worldPosition);
        ring2.dumpItems(level, worldPosition);
        ring3.dumpItems(level, worldPosition);
    }

    @Override
    protected void saveAdditional(CompoundTag compound) {
        inventory.save(compound);
        ring2.save(compound);
        ring3.save(compound);
        super.saveAdditional(compound);
    }

    @Override
    public void load(CompoundTag compound) {
        inventory.load(compound);
        ring2.load(compound);
        ring3.load(compound);
        super.load(compound);
    }
}
