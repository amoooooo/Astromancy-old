package coffee.amo.astromancy.common.blockentity;

import coffee.amo.astromancy.core.systems.stars.StarUtils;
import coffee.amo.astromancy.core.systems.stars.classification.Dwarf;
import coffee.amo.astromancy.core.systems.stars.classification.Hell;
import coffee.amo.astromancy.core.systems.stars.classification.MainSequence;
import coffee.amo.astromancy.core.systems.stars.classification.StarClass;
import coffee.amo.astromancy.core.systems.stars.types.BinaryStar;
import coffee.amo.astromancy.core.systems.stars.types.SimpleStar;
import coffee.amo.astromancy.core.systems.stars.types.StarType;
import com.sammy.ortus.systems.blockentity.OrtusBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class StarGatewayBlockEntity extends OrtusBlockEntity {
    public StarType star;
    public StarGatewayBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public InteractionResult onUse(Player player, InteractionHand hand) {
        if(!level.isClientSide && star == null) {
            ServerLevel serverLevel = (ServerLevel)player.level;
             star = StarUtils.generateRandomStar();
            //serverLevel.getDataStorage().set(worldPosition.toString(), );
            return InteractionResult.SUCCESS;
        } else if (!level.isClientSide && star != null) {
            player.sendMessage(new TextComponent(star.toString()), player.getUUID());
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.SUCCESS;
    }
}
