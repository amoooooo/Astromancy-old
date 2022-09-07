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
import coffee.amo.astromancy.core.systems.stars.systems.StarSystem;
import coffee.amo.astromancy.core.systems.stars.types.Star;
import coffee.amo.astromancy.core.util.AstromancyKeys;
import coffee.amo.astromancy.core.util.StarSavedData;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Random;

public class AstrolabeBlockEntity extends AstromancyBlockEntity {
    public boolean toggled = false;
    public StarSystem star;
    private final LumenStackHandler tank;
    private final LazyOptional<ILumenHandler> holder;
    public int scale = 1;
    public AstrolabeBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        Random rand = new Random();
        this.tank = new LumenStackHandler(1000, LumenStack::updateEmpty);
        this.holder = LazyOptional.of(() -> tank);
        int randSuperCluster = rand.nextInt(StarSavedData.get().getUniverse().getSuperclusters().size()-1);
        int randGalaxy = rand.nextInt(StarSavedData.get().getUniverse().getSuperclusters().get(randSuperCluster).getGalaxies().size()-1);
        int randStarSystem = rand.nextInt(StarSavedData.get().getUniverse().getSuperclusters().get(randSuperCluster).getGalaxies().get(randGalaxy).getStarSystems().size()-1);
        this.star = StarSavedData.get().getUniverse().getSuperclusters().get(randSuperCluster).getGalaxies().get(randGalaxy).getStarSystems().get(randStarSystem);
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
        if(player.getItemInHand(hand).isEmpty()){
            if(scale != 1) scale = 4;
            else scale = 1;
        }
        if (player.getItemInHand(hand).getItem() instanceof ArcanaSequence) {
            toggled = !toggled;
            //star = Star.fromNbt((CompoundTag) player.getItemInHand(hand).getTag().get("star"));
//            AstromancyPacketHandler.INSTANCE.send(PacketDistributor.NEAR.with(() ->
//                    new PacketDistributor.TargetPoint(
//                            this.getBlockPos().getX(),
//                            this.getBlockPos().getY(),
//                            this.getBlockPos().getZ(),
//                            128, this.level.dimension())), new StarPacket(this.getBlockPos(), star.toNbt()));
            BlockHelper.updateAndNotifyState(level, worldPosition);
        }
        if(level.isClientSide){
            String starTypes = star.getStars()[1] == null ? star.getStars()[0].starType.getType() : star.getStars()[0].starType.getType() + " and " + star.getStars()[1].starType.getType();
            StringBuilder starNames = new StringBuilder();
            for(Star star : star.getStars()){
                if(star == null) continue;
                star.getLumen().forEach((lumen, integer) -> {
                    starNames.append(Math.round(integer * 100)).append("% ").append(lumen.getType()).append(" Lumen | ");
                });
            }
            Component stuff = Component.literal("Star Type: " + starTypes + " | Star Lumen: " + starNames);
            player.sendSystemMessage(stuff);
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

    @Override
    public AABB getRenderBoundingBox() {
        return INFINITE_EXTENT_AABB;
    }
}
