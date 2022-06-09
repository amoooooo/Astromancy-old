package coffee.amo.astromancy.common.blockentity;

import com.sammy.ortus.helpers.BlockHelper;
import com.sammy.ortus.systems.blockentity.OrtusBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class AstrolabeBlockEntity extends OrtusBlockEntity {
    public boolean toggled = false;
    public AstrolabeBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public InteractionResult onUse(Player player, InteractionHand hand) {
        if (!level.isClientSide) {
            toggled = !toggled;
            BlockHelper.updateAndNotifyState(level, worldPosition);
        }
        return InteractionResult.SUCCESS;
    }
}
