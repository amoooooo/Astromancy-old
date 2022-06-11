package coffee.amo.astromancy.common.blockentity;

import coffee.amo.astromancy.common.item.ArcanaSequence;
import coffee.amo.astromancy.core.handlers.AstromancyPacketHandler;
import coffee.amo.astromancy.core.packets.StarPacket;
import coffee.amo.astromancy.core.systems.stars.Star;
import com.sammy.ortus.helpers.BlockHelper;
import com.sammy.ortus.systems.blockentity.OrtusBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.PacketDistributor;

public class AstrolabeBlockEntity extends OrtusBlockEntity {
    public boolean toggled = false;
    public Star star;
    public AstrolabeBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public InteractionResult onUse(Player player, InteractionHand hand) {
        if (player.getItemInHand(hand).getItem() instanceof ArcanaSequence) {
            toggled = !toggled;
            star = Star.fromNbt((CompoundTag) player.getItemInHand(hand).getTag().get("star"));
//            AstromancyPacketHandler.INSTANCE.send(PacketDistributor.NEAR.with(() ->
//                    new PacketDistributor.TargetPoint(
//                            this.getBlockPos().getX(),
//                            this.getBlockPos().getY(),
//                            this.getBlockPos().getZ(),
//                            128, this.level.dimension())), new StarPacket(this.getBlockPos(), star.toNbt()));
            BlockHelper.updateAndNotifyState(level, worldPosition);
        }
        return InteractionResult.SUCCESS;
    }
}
