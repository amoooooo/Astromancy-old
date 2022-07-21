package coffee.amo.astromancy.common.blockentity.armillary_sphere;

import coffee.amo.astromancy.Astromancy;
import coffee.amo.astromancy.aequivaleo.GlyphEntry;
import coffee.amo.astromancy.aequivaleo.GlyphHelper;
import coffee.amo.astromancy.aequivaleo.GlyphInstance;
import coffee.amo.astromancy.common.item.ArcanaSequence;
import coffee.amo.astromancy.core.handlers.AstromancyPacketHandler;
import coffee.amo.astromancy.core.handlers.CapabilityGlyphHandler;
import coffee.amo.astromancy.core.helpers.BlockHelper;
import coffee.amo.astromancy.core.packets.ArmillarySpherePacket;
import coffee.amo.astromancy.core.packets.StarPacket;
import coffee.amo.astromancy.core.registration.BlockEntityRegistration;
import coffee.amo.astromancy.core.registration.BlockRegistration;
import coffee.amo.astromancy.core.registration.ItemRegistry;
import coffee.amo.astromancy.core.systems.glyph.Glyph;
import coffee.amo.astromancy.core.systems.blockentity.AstromancyBlockEntityInventory;
import coffee.amo.astromancy.core.systems.multiblock.MultiblockCoreEntity;
import coffee.amo.astromancy.core.systems.multiblock.MultiblockStructure;
import coffee.amo.astromancy.core.systems.stars.Star;
import coffee.amo.astromancy.core.systems.stars.StarUtils;
import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.function.Supplier;

public class ArmillarySphereCoreBlockEntity extends MultiblockCoreEntity {
    public static final Supplier<MultiblockStructure> STRUCTURE = () -> (MultiblockStructure.of(new MultiblockStructure.StructurePiece(0,1,0, BlockRegistration.ARMILLARY_SPHERE_COMPONENT.get().defaultBlockState())));

    public AstromancyBlockEntityInventory inventory;
    public boolean toggled = false;
    public int ticksActive = 0;
    public boolean requirementBool = false;
    public Map<Glyph, Integer> requirements = new HashMap<>();
    public UUID playerUUID = null;
    public Star star;

    public ArmillarySphereCoreBlockEntity(BlockEntityType<? extends ArmillarySphereCoreBlockEntity> type, MultiblockStructure structure, BlockPos pos, BlockState state) {
        super(type, structure, pos, state);
        inventory = new AstromancyBlockEntityInventory(12, 1) {
            @Override
            public void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                BlockHelper.updateAndNotifyState(level, worldPosition);
            }
        };
    }

    public ArmillarySphereCoreBlockEntity(BlockPos pos, BlockState state){
        this(BlockEntityRegistration.ARMILLARY_SPHERE.get(), STRUCTURE.get(), pos, state);
    }

    @Override
    public void tick() {
        super.tick();
        if (toggled) {
            ticksActive++;
            //Astromancy.LOGGER.info(ticksActive);
            if (ticksActive >= 300) {
                toggled = false;
                ticksActive = 0;
                //Astromancy.LOGGER.info(toggled);
                if (!level.isClientSide) {
                    star = StarUtils.generateStar(level);
                    AstromancyPacketHandler.INSTANCE.send(PacketDistributor.NEAR.with(() ->
                            new PacketDistributor.TargetPoint(
                                    this.getBlockPos().getX(),
                                    this.getBlockPos().getY(),
                                    this.getBlockPos().getZ(),
                                    128, this.level.dimension())), new StarPacket(this.getBlockPos(), star.toNbt()));
                }
                playerUUID = null;
                inventory.clear();
                requirements.clear();
                requirementBool = false;
            }
        }
        if(level.getDayTime() == 22500){
            generateRequirements(level);
            AstromancyPacketHandler.INSTANCE.send(PacketDistributor.NEAR.with(() ->
                    new PacketDistributor.TargetPoint(
                            this.getBlockPos().getX(),
                            this.getBlockPos().getY(),
                            this.getBlockPos().getZ(),
                            128, this.level.dimension())), new ArmillarySpherePacket(this.worldPosition, requirements));
        }
    }

    public void generateRequirements(Level level) {
        int GlyphCount = level.random.nextInt(5) + 1;
        List<Glyph> shuffledGlyph = Lists.newArrayList(Glyph.values());
        shuffledGlyph.remove(Glyph.EMPTY);
        Collections.shuffle(shuffledGlyph);
        shuffledGlyph.subList(0, GlyphCount).forEach(Glyph -> {
            int amount = level.random.nextInt(15) + 1;
            requirements.put(Glyph, amount);
        });
        requirementBool = true;
    }

    public Map<Glyph, Integer> getMatchFromInventory() {
        Map<Glyph, Integer> match = new HashMap<>();
        for (ItemStack itemStack : inventory.getStacks()) {
            if (itemStack.isEmpty()) {
                continue;
            }
            if (itemStack.getItem().equals(ItemRegistry.GLYPH_PHIAL.get()) && itemStack.hasTag()){
                itemStack.getCapability(CapabilityGlyphHandler.GLYPH_HANDLER_CAPABILITY).ifPresent(s -> {
                    match.compute(s.getGlyphInTank(0).getGlyph(), (k, v) -> v == null ? s.getGlyphInTank(0).getAmount() : s.getGlyphInTank(0).getAmount() + v);
                });
                continue;
            }
            GlyphEntry entry = GlyphHelper.getEntry(level.dimension(), itemStack);

            for (GlyphInstance instance : entry.glyph) {
                int count = (int) Math.ceil(instance.amount);
                match.compute(instance.type.glyph, (Glyph, integer) -> integer == null ? count : integer + count);
            }
        }
        return match;
    }

    public boolean checkMatch(Map<Glyph, Integer> match) {
        return requirements.entrySet().stream().allMatch(entry -> {
            Integer actual = match.get(entry.getKey());
            return actual != null && actual >= entry.getValue();
        });
    }

    @Override
    public InteractionResult onUse(Player player, InteractionHand hand) {
        if (player.getItemInHand(hand).isEmpty() && !player.isShiftKeyDown() && inventory.getSlots() == 12 && requirementBool) {
//            for (ItemStack itemStack : inventory.getStacks()) {
//                if (itemStack.isEmpty()) {
//                    itemStack = Items.AIR.getDefaultInstance();
//                }
//            }
            if (checkMatch(getMatchFromInventory())) {
                toggled = true;
                playerUUID = player.getUUID();
                if(level.isClientSide) player.playSound(SoundEvents.AMETHYST_BLOCK_CHIME, 100f, 1f);
            }

            return InteractionResult.SUCCESS;
        } else if (player.getItemInHand(hand).isEmpty() && player.isShiftKeyDown()) {
            if (!inventory.isEmpty()) {
                inventory.interact(player.level, player, hand);
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.SUCCESS;
        } else if (player.getItemInHand(hand).isEmpty() && !player.isShiftKeyDown() && !requirementBool) {
            generateRequirements(level);
            AstromancyPacketHandler.INSTANCE.send(PacketDistributor.NEAR.with(() ->
                    new PacketDistributor.TargetPoint(
                            this.getBlockPos().getX(),
                            this.getBlockPos().getY(),
                            this.getBlockPos().getZ(),
                            128, this.level.dimension())), new ArmillarySpherePacket(this.worldPosition, requirements));
            return InteractionResult.SUCCESS;
        }
        if (player.getItemInHand(hand).getItem() instanceof ArcanaSequence && this.star != null) {
            player.getItemInHand(hand).getOrCreateTag().put("star", star.toNbt());
            this.star = null;
            return InteractionResult.SUCCESS;
        }
        inventory.interact(player.level, player, hand);
        return InteractionResult.SUCCESS;
    }

    @Override
    public void onBreak(@Nullable Player player) {
        inventory.dumpItems(level, worldPosition);
        super.onBreak(player);
    }

    @Override
    protected void saveAdditional(CompoundTag compound) {
        inventory.save(compound);
        ListTag listTag = new ListTag();
        requirements.forEach((Glyph, integer) -> {
            CompoundTag tag = new CompoundTag();
            tag.putString("glyph", Glyph.name());
            tag.putInt("amount", integer);
            listTag.add(tag);
        });
        compound.putBoolean("requirementBool", requirementBool);
        compound.putBoolean("toggled", toggled);
        compound.putInt("ticksActive", ticksActive);
        compound.put("requirements", listTag);
        if (playerUUID != null) {
            compound.putString("playerUUID", playerUUID.toString());
        }
        super.saveAdditional(compound);
    }

    @Override
    public void load(CompoundTag compound) {
        inventory.load(compound);
        ListTag listTag = compound.getList("requirements", Tag.TAG_COMPOUND);
        for (Tag tag : listTag) {
            CompoundTag tag1 = (CompoundTag) tag;
            requirements.put(Glyph.valueOf(tag1.getString("glyph")), tag1.getInt("amount"));
        }
        requirementBool = compound.getBoolean("requirementBool");
        toggled = compound.getBoolean("toggled");
        ticksActive = compound.getInt("ticksActive");
        if (compound.contains("playerUUID")) {
            playerUUID = UUID.fromString(compound.getString("playerUUID"));
        }
        super.load(compound);
    }

    public List<String> requirementsToStringList() {
        List<String> list = new ArrayList<>();
        requirements.forEach((Glyph, integer) -> {
            list.add(Glyph.name() + ": " + integer);
        });
        return list;
    }

    public List<Component> getGlyphInstances(){
        List<Component> list = new ArrayList<>();
        requirements.forEach((Glyph, integer) -> {
            MutableComponent tc = Component.literal("");
            tc.append(Component.literal("[").withStyle(s->s.withFont(Astromancy.astromancy("glyph"))));
            tc.append(Component.translatable("space.0").withStyle(s -> s.withFont(Astromancy.astromancy("negative_space"))));
            tc.append(Component.translatable("space.-1").withStyle(s -> s.withFont(Astromancy.astromancy("negative_space"))));
            tc.append(Component.literal(Glyph.symbol()).withStyle(style -> style.withFont(Astromancy.astromancy("glyph"))));
            tc.append(Component.translatable("space.0").withStyle(s -> s.withFont(Astromancy.astromancy("negative_space"))));
            tc.append(Component.translatable("space.-1").withStyle(s -> s.withFont(Astromancy.astromancy("negative_space"))));
            tc.append(GlyphEntry.intToComponent(integer).append(Component.literal("]").withStyle(s -> s.withFont(Astromancy.astromancy("glyph")))));
            list.add(tc);
        });
        return list;
    }

    public List<Component> pairToComponent(Map<Glyph, Integer> match){
        List<Component> list = new ArrayList<>();
        match.forEach((Glyph, integer) -> {
            MutableComponent tc = Component.literal("");
            tc.append(Component.literal("[").withStyle(s->s.withFont(Astromancy.astromancy("glyph"))));
            tc.append(Component.translatable("space.0").withStyle(s -> s.withFont(Astromancy.astromancy("negative_space"))));
            tc.append(Component.translatable("space.-1").withStyle(s -> s.withFont(Astromancy.astromancy("negative_space"))));
            tc.append(Component.literal(Glyph.symbol()).withStyle(style -> style.withFont(Astromancy.astromancy("glyph"))));
            tc.append(Component.translatable("space.0").withStyle(s -> s.withFont(Astromancy.astromancy("negative_space"))));
            tc.append(Component.translatable("space.-1").withStyle(s -> s.withFont(Astromancy.astromancy("negative_space"))));
            tc.append(GlyphEntry.intToComponent(integer).append(Component.literal("]").withStyle(s -> s.withFont(Astromancy.astromancy("glyph")))));
            list.add(tc);
        });
        return list;
    }

    public List<String> pairToStringList(Map<Glyph, Integer> match) {
        List<String> list = new ArrayList<>();
        match.forEach((Glyph, integer) -> {
            list.add(Glyph.name() + ": " + integer);
        });
        return list;
    }
    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return inventory.inventoryOptional.cast();
        }
        return super.getCapability(cap);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return inventory.inventoryOptional.cast();
        }
        return super.getCapability(cap, side);
    }

}
