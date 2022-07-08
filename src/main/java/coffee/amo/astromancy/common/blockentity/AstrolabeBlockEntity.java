package coffee.amo.astromancy.common.blockentity;

import coffee.amo.astromancy.common.item.ArcanaSequence;
import coffee.amo.astromancy.core.handlers.CapabilityLumenHandler;
import coffee.amo.astromancy.core.helpers.BlockHelper;
import coffee.amo.astromancy.core.registration.BlockEntityRegistration;
import coffee.amo.astromancy.core.systems.blockentity.AstromancyBlockEntity;
import coffee.amo.astromancy.core.systems.lumen.ILumenHandler;
import coffee.amo.astromancy.core.systems.lumen.Lumen;
import coffee.amo.astromancy.core.systems.lumen.LumenStack;
import coffee.amo.astromancy.core.systems.lumen.LumenStackHandler;
import coffee.amo.astromancy.core.systems.stars.Star;
import coffee.amo.astromancy.core.util.AstromancyKeys;
import com.google.common.collect.Maps;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class AstrolabeBlockEntity extends AstromancyBlockEntity {
    public boolean toggled = false;
    public Star star;
    private final LumenStackHandler tank;
    private final LazyOptional<ILumenHandler> holder;
    public AstrolabeBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.tank = new LumenStackHandler(1000, LumenStack::updateEmpty);
        this.holder = LazyOptional.of(() -> tank);
    }

    public AstrolabeBlockEntity(BlockPos pos, BlockState state){
        this(BlockEntityRegistration.ASTROLABE.get(), pos, state);
    }

    @Override
    public void tick() {
        super.tick();
        if(!level.isClientSide){
            if(tank.getLumenStack().getLumen().equals(Lumen.NONE)){
                tank.setLumenStack(new LumenStack(Lumen.EMPTY, 1));
            }
            if(tank.getSpace() > 0){
                tank.fill(new LumenStack(Lumen.EMPTY, 1), false);
            }
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 2|4|16);
        }
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        holder.invalidate();
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
        if(level.isClientSide){
            player.sendSystemMessage(Component.literal("Tank: " + tank.getLumenStack().getLumen().getType() + " [" + tank.getLumenStack().getAmount() + "]"));
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void load(CompoundTag tag) {
        tank.deserializeNBT(tag.getCompound(AstromancyKeys.KEY_LUMEN_TAG));
        super.saveAdditional(tag);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        tag.put(AstromancyKeys.KEY_LUMEN_TAG, tank.serializeNBT());
        super.saveAdditional(tag);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == CapabilityLumenHandler.LUMEN_HANDLER_CAPABILITY)
            return holder.cast();
        return super.getCapability(cap, side);
    }
}
