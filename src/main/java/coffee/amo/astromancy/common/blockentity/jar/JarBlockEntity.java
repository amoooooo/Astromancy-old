package coffee.amo.astromancy.common.blockentity.jar;

import coffee.amo.astromancy.Astromancy;
import coffee.amo.astromancy.aequivaleo.AspectiEntry;
import coffee.amo.astromancy.client.helper.ClientRenderHelper;
import coffee.amo.astromancy.common.block.jar.JarBlock;
import coffee.amo.astromancy.common.item.AspectiPhial;
import coffee.amo.astromancy.core.handlers.AstromancyPacketHandler;
import coffee.amo.astromancy.core.handlers.CapabilityAspectiHandler;
import coffee.amo.astromancy.core.helpers.BlockHelper;
import coffee.amo.astromancy.core.helpers.StringHelper;
import coffee.amo.astromancy.core.packets.ItemSyncPacket;
import coffee.amo.astromancy.core.packets.JarUpdatePacket;
import coffee.amo.astromancy.core.registration.BlockEntityRegistration;
import coffee.amo.astromancy.core.registration.ItemRegistry;
import coffee.amo.astromancy.core.systems.aspecti.*;
import coffee.amo.astromancy.core.systems.blockentity.AstromancyBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
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
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;

public class JarBlockEntity extends AstromancyBlockEntity {
    private int count;
    private Aspecti aspecti = Aspecti.EMPTY;
    public int clientLookAtTicks;
    public boolean label = false;
    public Direction labelDirection = Direction.UP;

    protected AspectiTank tank = new AspectiTank(256);
    private final LazyOptional<IAspectiHandler> holder = LazyOptional.of(() -> tank);

    public JarBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public JarBlockEntity(BlockPos pos, BlockState state){
        super(BlockEntityRegistration.JAR.get(), pos, state);
    }

    @Override
    public void tick() {
        super.tick();
        if(level.isClientSide){
            clientLookAtTicks = ClientRenderHelper.tickStuff((ClientLevel) level, this, clientLookAtTicks);
        }
        if(level.getBlockState(getBlockPos()).getValue(JarBlock.COUNT) != (int) Math.ceil(count/16F)){
            level.setBlock(getBlockPos(), level.getBlockState(getBlockPos()).setValue(JarBlock.COUNT, (int) Math.ceil(count/16F)), 2);
        }
    }

    // TODO: cap this at 256, or maybe make it a config option.
    // TODO: make it so the first stack added starts the count at 16
    // TODO: fix the renderer
    @Override
    public InteractionResult onUse(Player player, InteractionHand hand, BlockHitResult ray) {
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
//            if (heldItem.getItem() instanceof AspectiPhial && heldItem.hasTag()) {
//                if (count <= 240) {
//                    if (aspecti == Aspecti.fromNbt(heldItem.getTag()).getFirst() || aspecti == Aspecti.EMPTY) {
//                        heldItem.shrink(1);
//                        player.addItem(new ItemStack(ItemRegistry.ASPECTI_PHIAL.get(),1));
//                        BlockHelper.updateAndNotifyState(level, worldPosition);
//                        count = count == 0 ? Aspecti.fromNbt(heldItem.getTag()).getSecond() : count + Aspecti.fromNbt(heldItem.getTag()).getSecond();
//                        aspecti = Aspecti.fromNbt(heldItem.getTag()).getFirst();
//                        AstromancyPacketHandler.INSTANCE.send(PacketDistributor.NEAR.with(() -> new PacketDistributor.TargetPoint(
//                                this.getBlockPos().getX(),
//                                this.getBlockPos().getY(),
//                                this.getBlockPos().getZ(),
//                                128, this.level.dimension())), new JarUpdatePacket(this.getBlockPos(), count, aspecti.ordinal(), label, labelDirection.ordinal()));
//                        return InteractionResult.SUCCESS;
//                    }
//                    return InteractionResult.PASS;
//                }
//                return InteractionResult.PASS;
//            }  else
            if (heldItem.getItem() instanceof AspectiPhial && heldItem.getCapability(CapabilityAspectiHandler.ASPECTI_HANDLER_ITEM_CAPABILITY).isPresent() && this.count > 0) {
                holder.ifPresent(tank -> {
                    LazyOptional<IAspectiHandlerItem> itemCap = heldItem.getCapability(CapabilityAspectiHandler.ASPECTI_HANDLER_ITEM_CAPABILITY, null);
                    itemCap.ifPresent(i -> {
                        AspectiStack as = new AspectiStack(aspecti, 16);
                        i.fill(as, IAspectiHandler.AspectiAction.EXECUTE);
                        tank.drain(as, IAspectiHandler.AspectiAction.EXECUTE);
                    });
                });
                AstromancyPacketHandler.INSTANCE.send(PacketDistributor.NEAR.with(() -> new PacketDistributor.TargetPoint(
                        this.getBlockPos().getX(),
                        this.getBlockPos().getY(),
                        this.getBlockPos().getZ(),
                        128, this.level.dimension())), new JarUpdatePacket(this.getBlockPos(), count, aspecti.ordinal(), label, labelDirection.ordinal()));
                return InteractionResult.SUCCESS;
            }
//            else if (heldItem.getItem() == ItemRegistry.JAR.get() && heldItem.getTag() != null && !player.isCrouching()) {
//                if(count <= 256 && Aspecti.values()[heldItem.getTag().getCompound("BlockEntityTag").getInt("aspecti")] == aspecti){
//                    CompoundTag tag = heldItem.getTag().getCompound("BlockEntityTag").copy();
//                    int jarCount = tag.getInt("count");
//                    int cachedJarCount = jarCount;
//                    jarCount = jarCount + count > 256 ? (jarCount + count) - 256 : 0;
//                    count = Math.min(cachedJarCount + count, 256);
//                    if(jarCount == 0){
//                        tag.putInt("count", 0);
//                        tag.putInt("aspecti", 23);
//                        heldItem.getTag().put("BlockEntityTag", tag);
//                        player.setItemInHand(hand, heldItem);
//                    } else {
//                        tag.putInt("count", jarCount);
//                        heldItem.getTag().put("BlockEntityTag", tag);
//                    }
//                    AstromancyPacketHandler.INSTANCE.send(PacketDistributor.NEAR.with(() -> new PacketDistributor.TargetPoint(
//                            this.getBlockPos().getX(),
//                            this.getBlockPos().getY(),
//                            this.getBlockPos().getZ(),
//                            128, this.level.dimension())), new JarUpdatePacket(this.getBlockPos(), count, aspecti.ordinal(), label, labelDirection.ordinal()));
//                    AstromancyPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) player), new ItemSyncPacket(ItemRegistry.JAR.get().getDefaultInstance()));
//                    return InteractionResult.SUCCESS;
//                }
//            }
        }
        return InteractionResult.SUCCESS;
    }

    public void setCount(int count) {
        this.count = count;
    }
    public int getCount() {
        return count;
    }
    public void setAspecti(Aspecti aspecti) {
        this.aspecti = aspecti;
    }
    public Aspecti getAspecti() {
        return aspecti;
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.putInt("labelDirection", labelDirection.ordinal());
        pTag.putBoolean("label", label);
        super.saveAdditional(pTag);
        tank.toNbt(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        tank.fromNbt(pTag);
        labelDirection = Direction.values()[pTag.getInt("labelDirection")];
        label = pTag.getBoolean("label");
        super.load(pTag);
    }

    @Override
    public void onBreak(@Nullable Player player) {
        if(label) {
            popLabel();
        }
        super.onBreak(player);
    }

    public TextComponent getAspectiComponent(){
        TextComponent tc = new TextComponent("");
        if(this.aspecti != null){
            tc.append(new TextComponent("[").withStyle(s -> s.withFont(Astromancy.astromancy("aspecti"))));
            tc.append(new TranslatableComponent("space.0").withStyle(s -> s.withFont(Astromancy.astromancy("negative_space"))));
            tc.append(new TranslatableComponent("space.-1").withStyle(s -> s.withFont(Astromancy.astromancy("negative_space"))));
            tc.append(new TextComponent(aspecti.symbol()).withStyle(style -> style.withFont(Astromancy.astromancy("aspecti"))));
            tc.append(new TranslatableComponent("space.0").withStyle(s -> s.withFont(Astromancy.astromancy("negative_space"))));
            tc.append(new TranslatableComponent("space.-1").withStyle(s -> s.withFont(Astromancy.astromancy("negative_space"))));
            tc.append(AspectiEntry.intToTextComponent(count).append(new TextComponent("]").withStyle(s -> s.withFont(Astromancy.astromancy("aspecti")))));
        }
        return tc;
    }

    public TextComponent getAspectiSymbolComponent(){
        TextComponent tc = new TextComponent("");
        if(this.aspecti != null){
            tc.append(new TextComponent(aspecti.symbol()).withStyle(style -> style.withFont(Astromancy.astromancy("aspecti"))));
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
        if(count == 0) {
            aspecti = Aspecti.EMPTY;
        }
        ItemEntity e = new ItemEntity(this.level, this.worldPosition.getX() + 0.5, this.worldPosition.getY() + 0.5, this.worldPosition.getZ() + 0.5, Items.PAPER.getDefaultInstance());
        level.addFreshEntity(e);
        AstromancyPacketHandler.INSTANCE.send(PacketDistributor.NEAR.with(() -> new PacketDistributor.TargetPoint(
                this.getBlockPos().getX(),
                this.getBlockPos().getY(),
                this.getBlockPos().getZ(),
                128, this.level.dimension())), new JarUpdatePacket(this.getBlockPos(), count, aspecti.ordinal(), label, labelDirection.ordinal()));
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == CapabilityAspectiHandler.ASPECTI_HANDLER_CAPABILITY)
            return holder.cast();
        return super.getCapability(cap, side);
    }
}
