package coffee.amo.astromancy.common.blockentity;

import coffee.amo.astromancy.core.systems.stars.Star;
import coffee.amo.astromancy.core.systems.stars.StarUtils;
import coffee.amo.astromancy.core.util.StarSavedData;
import com.sammy.ortus.systems.blockentity.OrtusBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class StarGatewayBlockEntity extends OrtusBlockEntity {
    public Star star;
    public StarGatewayBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public InteractionResult onUse(Player player, InteractionHand hand) {
        if(!level.isClientSide && star == null) {
            star = StarUtils.generateStar(level);
            //serverLevel.getDataStorage().set(worldPosition.toString(), );
            return InteractionResult.SUCCESS;
        } else if (!level.isClientSide && star != null) {
            player.sendMessage(new TextComponent(StarSavedData.get(level.getServer()).findStarFromName(star.getName()).getName()), player.getUUID());
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        if(star != null) {
            pTag.putString("star_name", star.getName());
        }
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        if(pTag.contains("star_uuid")) {
            star = StarSavedData.get().findStarFromName(pTag.getString("star_name"));
        }
    }

    @Override
    public AABB getRenderBoundingBox() {
        return INFINITE_EXTENT_AABB;
    }
}
