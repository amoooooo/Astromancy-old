package coffee.amo.astromancy.common.blockentity;

import com.sammy.ortus.helpers.BlockHelper;
import com.sammy.ortus.systems.blockentity.ItemHolderBlockEntity;
import com.sammy.ortus.systems.blockentity.OrtusBlockEntityInventory;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

// TODO: fix inventory
public class ArmillarySphereBlockEntity extends ItemHolderBlockEntity {
    public OrtusBlockEntityInventory ring2;
    public OrtusBlockEntityInventory ring3;
    public boolean toggled = false;

    public ArmillarySphereBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        inventory = new OrtusBlockEntityInventory(12, 1) {
            @Override
            public void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                BlockHelper.updateAndNotifyState(level, worldPosition);
            }
        };
    }

    @Override
    public InteractionResult onUse(Player player, InteractionHand hand) {
        if (player.getItemInHand(hand).isEmpty() && !player.isShiftKeyDown()) {
            toggled = !toggled;

            return InteractionResult.SUCCESS;
        } else if (player.getItemInHand(hand).isEmpty() && player.isShiftKeyDown()) {
            if (!inventory.isEmpty()) {
                inventory.interact(player.level, player, hand);
                return InteractionResult.SUCCESS;
            }
        }
        inventory.interact(player.level, player, hand);
        return InteractionResult.SUCCESS;
    }

    @Override
    public void onBreak(@Nullable Player player) {
        inventory.dumpItems(level, worldPosition);
    }

    @Override
    protected void saveAdditional(CompoundTag compound) {
        inventory.save(compound);
        super.saveAdditional(compound);
    }

    @Override
    public void load(CompoundTag compound) {
        inventory.load(compound);
        super.load(compound);
    }
}
