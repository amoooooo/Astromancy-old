package coffee.amo.astromancy.common.blockentity;

import coffee.amo.astromancy.aequivaleo.AspectiEntry;
import coffee.amo.astromancy.aequivaleo.AspectiHelper;
import coffee.amo.astromancy.aequivaleo.AspectiInstance;
import coffee.amo.astromancy.aequivaleo.compound.AspectiCompoundType;
import coffee.amo.astromancy.core.registration.AspectiRegistry;
import coffee.amo.astromancy.core.systems.aspecti.Aspecti;
import coffee.amo.astromancy.core.systems.aspecti.AspectiMap;
import com.mojang.datafixers.util.Pair;
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

import java.util.ArrayList;
import java.util.List;

// TODO: fix inventory
public class ArmillarySphereBlockEntity extends ItemHolderBlockEntity {
    public boolean toggled = false;
    public int ticksActive = 0;
    public boolean requirementBool = false;
    public List<Pair<Aspecti, Integer>> requirements = new ArrayList<>();
    public List<Pair<Aspecti, Integer>> contains = new ArrayList<>();

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
        if (!level.isClientSide && toggled) {
            ticksActive++;
            if (ticksActive >= 60) {
                toggled = false;
                ticksActive = 0;
            }
        } else if (!level.isClientSide && !requirementBool){
            generateRequirements(level);
        }
    }

    public void generateRequirements(Level level){
        int aspectiCount = level.random.nextInt(5);
        for(int i = 0; i < aspectiCount; i++){
            Aspecti aspecti = AspectiMap.intMap.get(level.random.nextInt(AspectiMap.intMap.size()));
            int amount = level.random.nextInt(31);
            requirements.add(Pair.of(aspecti, amount));
        }
        requirementBool = true;
    }

    public List<Pair<Aspecti, Integer>> getMatchFromInventory(){
        List<Pair<Aspecti, Integer>> match = new ArrayList<>();
        for(ItemStack itemStack : inventory.getStacks()){
            if(itemStack.isEmpty()){
                continue;
            }
            AspectiEntry entry = AspectiHelper.getEntry(level.dimension(), itemStack);
            for(AspectiInstance instance : entry.aspecti){
                for(Pair<Aspecti, Integer> requirement : requirements){
                    if(requirement.getFirst() == instance.type.aspecti){
                        requirements.add(new Pair<>(instance.type.aspecti, requirement.getSecond () + (int)Math.ceil(instance.amount)));
                        requirements.remove(requirement);
                    } else {
                        match.add(new Pair<>(instance.type.aspecti, (int)Math.ceil(instance.amount)));
                    }
                }
            }
        }
        return match;
    }

public boolean checkMatch(List<Pair<Aspecti, Integer>> match){
        for(Pair<Aspecti, Integer> pair : match){
            for(Pair<Aspecti, Integer> requirement : requirements){
                if(pair.getFirst().equals(requirement.getFirst()) && pair.getSecond() >= requirement.getSecond()){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public InteractionResult onUse(Player player, InteractionHand hand) {
        if (player.getItemInHand(hand).isEmpty() && !player.isShiftKeyDown() && inventory.getSlots() == 12) {
            toggled = !toggled;
            System.out.println(requirements);
            if(checkMatch(getMatchFromInventory())){
                player.sendMessage(new TextComponent("Match!"), player.getUUID());
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
