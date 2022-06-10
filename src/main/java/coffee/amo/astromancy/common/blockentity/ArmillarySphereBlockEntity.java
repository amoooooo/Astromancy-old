package coffee.amo.astromancy.common.blockentity;

import coffee.amo.astromancy.Astromancy;
import coffee.amo.astromancy.aequivaleo.AspectiEntry;
import coffee.amo.astromancy.aequivaleo.AspectiHelper;
import coffee.amo.astromancy.aequivaleo.AspectiInstance;
import coffee.amo.astromancy.common.item.ArcanaSequence;
import coffee.amo.astromancy.core.handlers.AstromancyPacketHandler;
import coffee.amo.astromancy.core.packets.ArmillarySpherePacket;
import coffee.amo.astromancy.core.packets.StarPacket;
import coffee.amo.astromancy.core.registration.AspectiRegistry;
import coffee.amo.astromancy.core.systems.aspecti.Aspecti;
import coffee.amo.astromancy.core.systems.stars.Star;
import coffee.amo.astromancy.core.systems.stars.StarUtils;
import com.google.common.collect.Lists;
import com.sammy.ortus.helpers.BlockHelper;
import com.sammy.ortus.systems.blockentity.ItemHolderBlockEntity;
import com.sammy.ortus.systems.blockentity.OrtusBlockEntityInventory;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.TextComponentTagVisitor;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;

import java.util.*;

// TODO: fix inventory
public class ArmillarySphereBlockEntity extends ItemHolderBlockEntity {
    public boolean toggled = false;
    public int ticksActive = 0;
    public boolean requirementBool = false;
    public Map<Aspecti, Integer> requirements = new HashMap<>();
    public UUID playerUUID = null;
    public Star star;
    public String starName;

    public ArmillarySphereBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        inventory = new OrtusBlockEntityInventory(12, 1) {
            @Override
            public void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                BlockHelper.updateAndNotifyState(level, worldPosition);
            }
        };
    }

    @Override
    public void tick() {
        super.tick();
        if (toggled) {
            ticksActive++;
            //System.out.println(ticksActive);
            if (ticksActive >= 300) {
                toggled = false;
                ticksActive = 0;
                //System.out.println(toggled);
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
    }

    public void generateRequirements(Level level) {
        int aspectiCount = level.random.nextInt(5) + 1;
        List<Aspecti> shuffledAspecti = Lists.newArrayList(Aspecti.values());
        Collections.shuffle(shuffledAspecti);
        shuffledAspecti.subList(0, aspectiCount).forEach(aspecti -> {
            int amount = level.random.nextInt(15) + 1;
            requirements.put(aspecti, amount);
        });
        requirementBool = true;
    }

    public Map<Aspecti, Integer> getMatchFromInventory() {
        Map<Aspecti, Integer> match = new HashMap<>();
        for (ItemStack itemStack : inventory.getStacks()) {
            if (itemStack.isEmpty()) {
                continue;
            }
            AspectiEntry entry = AspectiHelper.getEntry(level.dimension(), itemStack);

            for (AspectiInstance instance : entry.aspecti) {
                int count = (int) Math.ceil(instance.amount);
                match.compute(instance.type.aspecti, (aspecti, integer) -> integer == null ? count : integer + count);
            }
        }
        return match;
    }

    public boolean checkMatch(Map<Aspecti, Integer> match) {
        return requirements.entrySet().stream().allMatch(entry -> {
            Integer actual = match.get(entry.getKey());
            return actual != null && actual >= entry.getValue();
        });
    }

    @Override
    public InteractionResult onUse(Player player, InteractionHand hand) {
        if (player.getItemInHand(hand).isEmpty() && !player.isShiftKeyDown() && inventory.getSlots() == 12 && requirementBool) {
            for (ItemStack itemStack : inventory.getStacks()) {
                if (itemStack.isEmpty()) {
                    itemStack = Items.AIR.getDefaultInstance();
                }
            }
            if (checkMatch(getMatchFromInventory())) {
                toggled = true;
                playerUUID = player.getUUID();
                player.playNotifySound(SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.BLOCKS, 2f, 2f);
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
    }

    @Override
    protected void saveAdditional(CompoundTag compound) {
        inventory.save(compound);
        ListTag listTag = new ListTag();
        requirements.forEach((aspecti, integer) -> {
            CompoundTag tag = new CompoundTag();
            tag.putString("aspecti", aspecti.name());
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
            requirements.put(Aspecti.valueOf(tag1.getString("aspecti")), tag1.getInt("amount"));
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
        requirements.forEach((aspecti, integer) -> {
            list.add(aspecti.name() + ": " + integer);
        });
        return list;
    }

    public List<TextComponent> getAspectiInstances(){
        List<TextComponent> list = new ArrayList<>();
        requirements.forEach((aspecti, integer) -> {
            list.add((TextComponent) new TextComponent(aspecti.symbol()).withStyle(style -> style.withFont(Astromancy.astromancy("aspecti"))).append(new TextComponent(" " + integer.toString()).withStyle(style -> style.withFont(Style.DEFAULT_FONT))));
        });
        return list;
    }

    public List<TextComponent> pairToTextComponent(Map<Aspecti, Integer> match){
        List<TextComponent> list = new ArrayList<>();
        match.forEach((aspecti, integer) -> {
            list.add((TextComponent) new TextComponent(aspecti.symbol()).withStyle(style -> style.withFont(Astromancy.astromancy("aspecti"))).append(new TextComponent(" " + integer.toString()).withStyle(style -> style.withFont(Style.DEFAULT_FONT))));
        });
        return list;
    }

    public List<String> pairToStringList(Map<Aspecti, Integer> match) {
        List<String> list = new ArrayList<>();
        match.forEach((aspecti, integer) -> {
            list.add(aspecti.name() + ": " + integer);
        });
        return list;
    }
}
