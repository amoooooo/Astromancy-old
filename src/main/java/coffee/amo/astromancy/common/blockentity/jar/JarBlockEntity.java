package coffee.amo.astromancy.common.blockentity.jar;

import coffee.amo.astromancy.Astromancy;
import coffee.amo.astromancy.aequivaleo.AspectiEntry;
import coffee.amo.astromancy.client.helper.ClientRenderHelper;
import coffee.amo.astromancy.common.block.jar.JarBlock;
import coffee.amo.astromancy.core.handlers.AstromancyPacketHandler;
import coffee.amo.astromancy.core.handlers.CapabilityAspectiHandler;
import coffee.amo.astromancy.core.registration.BlockEntityRegistration;
import coffee.amo.astromancy.core.systems.aspecti.Aspecti;
import coffee.amo.astromancy.core.systems.aspecti.AspectiStack;
import coffee.amo.astromancy.core.systems.aspecti.AspectiStackHandler;
import coffee.amo.astromancy.core.systems.aspecti.IAspectiHandler;
import coffee.amo.astromancy.core.systems.blockentity.AstromancyBlockEntity;
import coffee.amo.astromancy.core.util.AstroKeys;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.network.PacketDistributor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class JarBlockEntity extends AstromancyBlockEntity {

    public int clientLookAtTicks;
    public boolean label = false;
    public Direction labelDirection = Direction.UP;
    private final AspectiStackHandler tank;
    private final LazyOptional<IAspectiHandler> holder;

    public JarBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.tank = new AspectiStackHandler(256, AspectiStack::updateEmpty);
        this.holder = LazyOptional.of(() -> tank);
    }

    public JarBlockEntity(BlockPos pos, BlockState state){
        this(BlockEntityRegistration.JAR.get(), pos, state);
    }

    @Override
    public void tick() {
        super.tick();
        if(level.isClientSide){
            clientLookAtTicks = ClientRenderHelper.tickStuff((ClientLevel) level, this, clientLookAtTicks);
        }
        if(level.getBlockState(getBlockPos()).getValue(JarBlock.COUNT) != (int) Math.ceil(tank.getAspectiStack().getAmount()/16F)){
            level.setBlock(getBlockPos(), level.getBlockState(getBlockPos()).setValue(JarBlock.COUNT, (int) Math.ceil(tank.getAspectiStack().getAmount()/16F)), 2);
        }
    }

    // TODO: cap this at 256, or maybe make it a config option.
    // TODO: make it so the first stack added starts the count at 16
    // TODO: fix the renderer
    @Override
    public InteractionResult onUse(Player player, InteractionHand hand, BlockHitResult ray) {
        if(!level.isClientSide){
            ItemStack heldItem = player.getItemInHand(hand);
            if(heldItem.getItem().equals(Items.PAPER) && !label){
                label = true;
                labelDirection = ray.getDirection();
                heldItem.shrink(1);
                setChanged();
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 2|4|16);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;

        /* idk what is all of this doing here
        if(!level.isClientSide){
            ItemStack heldItem = player.getItemInHand(hand);
            if(heldItem.getItem().equals(Items.PAPER)){
                if(!label){
                    label = true;
                    labelDirection = ray.getDirection();
                    heldItem.shrink(1);
                    AstromancyPacketHandler.INSTANCE.send(PacketDistributor.NEAR.with(() -> new PacketDistributor.TargetPoint(
                            this.getBlockPos().getX(),
                            this.getBlockPos().getY(),
                            this.getBlockPos().getZ(),
                            128, this.level.dimension())), new JarUpdatePacket(this.getBlockPos(), count, aspecti.ordinal(), label, labelDirection.ordinal()));
                }
            }
            if (heldItem.getItem() instanceof AspectiPhial && heldItem.hasTag()) {
                if (count <= 240) {
                    if (aspecti == Aspecti.fromNbt(heldItem.getTag()).getFirst() || aspecti == Aspecti.EMPTY) {
                        heldItem.shrink(1);
                        player.addItem(new ItemStack(ItemRegistry.ASPECTI_PHIAL.get(),1));
                        BlockHelper.updateAndNotifyState(level, worldPosition);
                        count = count == 0 ? Aspecti.fromNbt(heldItem.getTag()).getSecond() : count + Aspecti.fromNbt(heldItem.getTag()).getSecond();
                        aspecti = Aspecti.fromNbt(heldItem.getTag()).getFirst();
                        AstromancyPacketHandler.INSTANCE.send(PacketDistributor.NEAR.with(() -> new PacketDistributor.TargetPoint(
                                this.getBlockPos().getX(),
                                this.getBlockPos().getY(),
                                this.getBlockPos().getZ(),
                                128, this.level.dimension())), new JarUpdatePacket(this.getBlockPos(), count, aspecti.ordinal(), label, labelDirection.ordinal()));
                        return InteractionResult.SUCCESS;
                    }
                    return InteractionResult.PASS;
                }
                return InteractionResult.PASS;
            }  else if (heldItem.getItem() instanceof AspectiPhial && !heldItem.hasTag() && this.aspecti != Aspecti.EMPTY && this.count > 0) {
                CompoundTag tag = new CompoundTag();
                tag.putInt("count", 16);
                tag.putInt("aspecti", aspecti.ordinal());
                ItemStack stack = new ItemStack(ItemRegistry.ASPECTI_PHIAL.get(), 1);
                stack.getOrCreateTag().put("aspecti", tag);
                player.addItem(stack);
                heldItem.shrink(1);
                if (count - 16 <= 0) {
                    this.count = 0;
                    if(!label){
                        this.aspecti = Aspecti.EMPTY;
                    }
                } else {
                    this.count = count - 16;
                }
                AstromancyPacketHandler.INSTANCE.send(PacketDistributor.NEAR.with(() -> new PacketDistributor.TargetPoint(
                        this.getBlockPos().getX(),
                        this.getBlockPos().getY(),
                        this.getBlockPos().getZ(),
                        128, this.level.dimension())), new JarUpdatePacket(this.getBlockPos(), count, aspecti.ordinal(), label, labelDirection.ordinal()));
                return InteractionResult.SUCCESS;
            } else if (heldItem.getItem() == ItemRegistry.JAR.get() && heldItem.getTag() != null && !player.isCrouching()) {
                if(count <= 256 && Aspecti.values()[heldItem.getTag().getCompound("BlockEntityTag").getInt("aspecti")] == aspecti){
                    CompoundTag tag = heldItem.getTag().getCompound("BlockEntityTag").copy();
                    int jarCount = tag.getInt("count");
                    int cachedJarCount = jarCount;
                    jarCount = jarCount + count > 256 ? (jarCount + count) - 256 : 0;
                    count = Math.min(cachedJarCount + count, 256);
                    if(jarCount == 0){
                        tag.putInt("count", 0);
                        tag.putInt("aspecti", 23);
                        heldItem.getTag().put("BlockEntityTag", tag);
                        player.setItemInHand(hand, heldItem);
                    } else {
                        tag.putInt("count", jarCount);
                        heldItem.getTag().put("BlockEntityTag", tag);
                    }
                    AstromancyPacketHandler.INSTANCE.send(PacketDistributor.NEAR.with(() -> new PacketDistributor.TargetPoint(
                            this.getBlockPos().getX(),
                            this.getBlockPos().getY(),
                            this.getBlockPos().getZ(),
                            128, this.level.dimension())), new JarUpdatePacket(this.getBlockPos(), count, aspecti.ordinal(), label, labelDirection.ordinal()));
                    AstromancyPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) player), new ItemSyncPacket(ItemRegistry.JAR.get().getDefaultInstance()));
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return InteractionResult.SUCCESS;*/
    }

    public Aspecti getAspecti() {
        return tank.getAspectiStack().getAspecti();
    }

    public int getAmount() {
        return tank.getAspectiStack().getAmount();
    }

    public boolean isEmpty() {
        return tank.getAspectiStack().isEmpty();
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        holder.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put(AstroKeys.KEY_ASPECTI_TAG, tank.serializeNBT());
        pTag.putBoolean(AstroKeys.KEY_JAR_LABEL, label);
        pTag.putInt(AstroKeys.KEY_JAR_DIRECTION, labelDirection.ordinal());
        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        tank.deserializeNBT(pTag.getCompound(AstroKeys.KEY_ASPECTI_TAG));
        label = pTag.getBoolean(AstroKeys.KEY_JAR_LABEL);
        labelDirection = Direction.values()[pTag.getInt(AstroKeys.KEY_JAR_DIRECTION)];
        super.load(pTag);
    }

    @Override
    public void onBreak(@Nullable Player player) {
        if(label) {
            popLabel();
        }
        super.onBreak(player);
    }

    public MutableComponent getAspectiComponent() {
        return ((MutableComponent)Component.literal(""))
                .append(Component.literal("[").withStyle(s -> s.withFont(Astromancy.astromancy("aspecti"))))
                .append(Component.translatable("space.0").withStyle(s -> s.withFont(Astromancy.astromancy("negative_space"))))
                .append(Component.translatable("space.-1").withStyle(s -> s.withFont(Astromancy.astromancy("negative_space"))))
                .append(Component.literal(tank.getAspectiStack().getAspecti().symbol()).withStyle(style -> style.withFont(Astromancy.astromancy("aspecti"))))
                .append(Component.translatable("space.0").withStyle(s -> s.withFont(Astromancy.astromancy("negative_space"))))
                .append(Component.translatable("space.-1").withStyle(s -> s.withFont(Astromancy.astromancy("negative_space"))))
                .append(AspectiEntry.intToComponent(tank.getAspectiStack().getAmount()))
                .append(Component.literal("]").withStyle(s -> s.withFont(Astromancy.astromancy("aspecti"))));
    }

    public Component getAspectiSymbolComponent(){
        MutableComponent tc = Component.literal("");
        if(tank != null){
            tc.append(Component.literal(tank.getAspectiStack().getAspecti().symbol()).withStyle(style -> style.withFont(Astromancy.astromancy("aspecti"))));
        }
        return tc;
    }

    @Override
    public AABB getRenderBoundingBox() {
        return INFINITE_EXTENT_AABB;
    }

    public void popLabel(){
        label = false;
        labelDirection = Direction.values()[5];
        ItemEntity e = new ItemEntity(this.level, this.worldPosition.getX() + 0.5, this.worldPosition.getY() + 0.5, this.worldPosition.getZ() + 0.5, Items.PAPER.getDefaultInstance());
        level.addFreshEntity(e);
        setChanged();
        level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 2|4|16);
//        AstromancyPacketHandler.INSTANCE.send(PacketDistributor.NEAR.with(() -> new PacketDistributor.TargetPoint(
//                this.getBlockPos().getX(),
//                this.getBlockPos().getY(),
//                this.getBlockPos().getZ(),
//                128, this.level.dimension())), new JarUpdatePacket(this.getBlockPos(), count, aspecti.ordinal(), label, labelDirection.ordinal()));
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if(cap == CapabilityAspectiHandler.ASPECTI_HANDLER_CAPABILITY)
            return holder.cast();
        return super.getCapability(cap, side);
    }
}
