package coffee.amo.astromancy.common.blockentity.jar;

import coffee.amo.astromancy.Astromancy;
import coffee.amo.astromancy.aequivaleo.GlyphEntry;
import coffee.amo.astromancy.client.helper.ClientRenderHelper;
import coffee.amo.astromancy.common.block.jar.JarBlock;
import coffee.amo.astromancy.core.handlers.CapabilityGlyphHandler;
import coffee.amo.astromancy.core.registration.BlockEntityRegistration;
import coffee.amo.astromancy.core.registration.ItemRegistry;
import coffee.amo.astromancy.core.systems.glyph.Glyph;
import coffee.amo.astromancy.core.systems.glyph.GlyphStack;
import coffee.amo.astromancy.core.systems.glyph.GlyphStackHandler;
import coffee.amo.astromancy.core.systems.glyph.IGlyphHandler;
import coffee.amo.astromancy.core.systems.blockentity.AstromancyBlockEntity;
import coffee.amo.astromancy.core.util.AstromancyKeys;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class JarBlockEntity extends AstromancyBlockEntity {

    public int clientLookAtTicks;
    public boolean label = false;
    public Direction labelDirection = Direction.UP;
    private final GlyphStackHandler tank;
    private final LazyOptional<IGlyphHandler> holder;

    public JarBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.tank = new GlyphStackHandler(256, GlyphStack::updateEmpty);
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
        if(level.getBlockState(getBlockPos()).getValue(JarBlock.COUNT) != (int) Math.ceil(tank.getGlyphStack().getAmount()/16F)){
            level.setBlock(getBlockPos(), level.getBlockState(getBlockPos()).setValue(JarBlock.COUNT, (int) Math.ceil(tank.getGlyphStack().getAmount()/16F)), 2);
        }
    }

    @Override
    public InteractionResult onUse(Player player, InteractionHand hand, BlockHitResult ray) {
        if(!level.isClientSide){
            ItemStack heldItem = player.getItemInHand(hand);
            if(heldItem.getItem().equals(ItemRegistry.VELLUM.get()) && !label){
                label = true;
                labelDirection = ray.getDirection();
                heldItem.shrink(1);
                setChanged();
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 2|4|16);
                level.playSound(null, getBlockPos(), SoundEvents.BOOK_PUT, SoundSource.BLOCKS, 1, 1);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    public Glyph getGlyph() {
        return tank.getGlyphStack().getGlyph();
    }

    public int getAmount() {
        return tank.getGlyphStack().getAmount();
    }

    public boolean isEmpty() {
        return tank.getGlyphStack().isEmpty();
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        holder.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put(AstromancyKeys.KEY_GLYPH_TAG, tank.serializeNBT());
        pTag.putBoolean(AstromancyKeys.KEY_JAR_LABEL, label);
        pTag.putInt(AstromancyKeys.KEY_JAR_DIRECTION, labelDirection.ordinal());
        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        tank.deserializeNBT(pTag.getCompound(AstromancyKeys.KEY_GLYPH_TAG));
        label = pTag.getBoolean(AstromancyKeys.KEY_JAR_LABEL);
        labelDirection = Direction.values()[pTag.getInt(AstromancyKeys.KEY_JAR_DIRECTION)];
        super.load(pTag);
    }

    @Override
    public void onBreak(@Nullable Player player) {
        if(label) {
            popLabel();
        }
        super.onBreak(player);
    }

    public MutableComponent getGlyphComponent() {
        return (Component.literal(""))
                .append(Component.literal("[").withStyle(s -> s.withFont(Astromancy.astromancy("glyph"))))
                .append(Component.translatable("space.0").withStyle(s -> s.withFont(Astromancy.astromancy("negative_space"))))
                .append(Component.translatable("space.-1").withStyle(s -> s.withFont(Astromancy.astromancy("negative_space"))))
                .append(Component.literal(tank.getGlyphStack().getGlyph().symbol()).withStyle(style -> style.withFont(Astromancy.astromancy("glyph"))))
                .append(Component.translatable("space.0").withStyle(s -> s.withFont(Astromancy.astromancy("negative_space"))))
                .append(Component.translatable("space.-1").withStyle(s -> s.withFont(Astromancy.astromancy("negative_space"))))
                .append(GlyphEntry.intToComponent(tank.getGlyphStack().getAmount()))
                .append(Component.literal("]").withStyle(s -> s.withFont(Astromancy.astromancy("glyph"))));
    }

    public Component getGlyphSymbolComponent(){
        MutableComponent tc = Component.literal("");
        if(tank != null){
            tc.append(Component.literal(tank.getGlyphStack().getGlyph().symbol()).withStyle(style -> style.withFont(Astromancy.astromancy("glyph"))));
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
        ItemEntity e = new ItemEntity(this.level, this.worldPosition.getX() + 0.5, this.worldPosition.getY() + 0.5, this.worldPosition.getZ() + 0.5, ItemRegistry.VELLUM.get().getDefaultInstance());
        level.addFreshEntity(e);
        setChanged();
        level.playSound(null, getBlockPos(), SoundEvents.BOOK_PUT, SoundSource.BLOCKS, 1, 1.7f);
        level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 2|4|16);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if(cap == CapabilityGlyphHandler.GLYPH_HANDLER_CAPABILITY)
            return holder.cast();
        return super.getCapability(cap, side);
    }
}
