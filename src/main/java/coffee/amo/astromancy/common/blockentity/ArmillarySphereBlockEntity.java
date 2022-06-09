package coffee.amo.astromancy.common.blockentity;

import coffee.amo.astromancy.aequivaleo.AspectiEntry;
import coffee.amo.astromancy.aequivaleo.AspectiHelper;
import coffee.amo.astromancy.aequivaleo.AspectiInstance;
import coffee.amo.astromancy.core.systems.aspecti.Aspecti;
import com.google.common.collect.Lists;
import com.sammy.ortus.helpers.BlockHelper;
import com.sammy.ortus.systems.blockentity.ItemHolderBlockEntity;
import com.sammy.ortus.systems.blockentity.OrtusBlockEntityInventory;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.*;

// TODO: fix inventory
public class ArmillarySphereBlockEntity extends ItemHolderBlockEntity {
    public boolean toggled = false;
    public int ticksActive = 0;
    public boolean requirementBool = false;
    public Map<Aspecti, Integer> requirements = new HashMap<>();
    public Map<Aspecti, Integer> contains = new HashMap<>();

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
        if (!level.isClientSide && !requirementBool){
            generateRequirements(level);
        }
        if (!level.isClientSide && toggled) {
            ticksActive++;
            if (ticksActive >= 60) {
                toggled = false;
                ticksActive = 0;
            }
        }
    }

    public void generateRequirements(Level level){
        int aspectiCount = level.random.nextInt(5) + 1;
        List<Aspecti> shuffledAspecti = Lists.newArrayList(Aspecti.values());
        Collections.shuffle(shuffledAspecti);
        shuffledAspecti.subList(0, aspectiCount).forEach(aspecti -> {
            int amount = level.random.nextInt(31);
            requirements.put(aspecti, amount);
        });
        requirementBool = true;
    }

    public Map<Aspecti, Integer> getMatchFromInventory(){
        Map<Aspecti, Integer> match = new HashMap<>();
        for(ItemStack itemStack : inventory.getStacks()){
            if(itemStack.isEmpty()){
                continue;
            }
            AspectiEntry entry = AspectiHelper.getEntry(level.dimension(), itemStack);

            for (AspectiInstance instance : entry.aspecti) {
                int count = (int)Math.ceil(instance.amount);
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
        if (player.getItemInHand(hand).isEmpty() && !player.isShiftKeyDown() && inventory.getSlots() == 12) {
            if(!player.level.isClientSide) {
                toggled = !toggled;
                System.out.println(requirements);
                if (checkMatch(getMatchFromInventory())) {
                    player.sendMessage(new TextComponent("Match!"), player.getUUID());
                }
            }
            return InteractionResult.SUCCESS;
        } else if (player.getItemInHand(hand).isEmpty() && player.isShiftKeyDown()) {
            if (!inventory.isEmpty()) {
                inventory.interact(player.level, player, hand);
                return InteractionResult.SUCCESS;
            }
        } else if (player.getItemInHand(hand).isEmpty() && !player.isShiftKeyDown() && inventory.getSlots() < 12 && !requirementBool) {
            generateRequirements(level);
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
        super.saveAdditional(compound);
    }

    @Override
    public void load(CompoundTag compound) {
        inventory.load(compound);
        super.load(compound);
    }
}
