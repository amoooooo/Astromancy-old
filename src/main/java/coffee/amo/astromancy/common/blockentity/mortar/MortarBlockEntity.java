package coffee.amo.astromancy.common.blockentity.mortar;

import coffee.amo.astromancy.Astromancy;
import coffee.amo.astromancy.common.item.PestleItem;
import coffee.amo.astromancy.common.recipe.MortarRecipe;
import coffee.amo.astromancy.core.helpers.BlockHelper;
import coffee.amo.astromancy.core.registration.BlockEntityRegistration;
import coffee.amo.astromancy.core.registration.SoundRegistry;
import coffee.amo.astromancy.core.systems.blockentity.AstromancyBlockEntityInventory;
import coffee.amo.astromancy.core.systems.blockentity.ItemHolderBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Optional;

public class MortarBlockEntity extends ItemHolderBlockEntity {
    public AstromancyBlockEntityInventory output;
    public MortarRecipe recipe;
    public int progress;
    public int cooldown;
    public int spins;
    public int spinning = 0;

    public MortarBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistration.MORTAR.get(), pos, state);
        inventory = new AstromancyBlockEntityInventory(6, 1) {
            @Override
            public void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                BlockHelper.updateAndNotifyState(level, pos);
            }
        };
        output = new AstromancyBlockEntityInventory(1, 1) {
            @Override
            public void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                BlockHelper.updateAndNotifyState(level, pos);
            }
        };
    }

    public MortarBlockEntity(BlockEntityType<? extends MortarBlockEntity> type, BlockPos pos, BlockState state) {
        super(type, pos, state);

    }

    @Override
    public InteractionResult onUse(Player player, InteractionHand hand) {
        if(player.getItemInHand(hand).isEmpty() && !player.isCrouching() && output.isEmpty() && !inventory.isEmpty()){
            level.playSound(null, worldPosition, SoundRegistry.GRIND.get(), SoundSource.BLOCKS, 1f, 1f);
            spinning = 1;
            if(spins < 5){
                spins++;
            } else {
                craftItem(this);
            }
            return InteractionResult.SUCCESS;
        }
        if(spins > 5){
            spins = 0;
        }
        super.onUse(player, hand);
        if(!output.isEmpty()){
            output.interact(level, player, hand);
        }
        return InteractionResult.SUCCESS;
    }

    private static boolean hasRecipe(MortarBlockEntity entity){
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.inventory.getSlots());
        for(int i = 0; i < entity.inventory.getSlots(); i++){
            inventory.setItem(i, entity.inventory.extractItem(i, 1, true));
        }
        Optional<MortarRecipe> match = level.getRecipeManager().getRecipeFor(MortarRecipe.Type.INSTANCE, inventory, level);

        return match.isPresent();
    }

    private static void craftItem(MortarBlockEntity entity){
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.inventory.getSlots());
        for(int i = 0; i < entity.inventory.getSlots(); i++){
            inventory.setItem(i, entity.inventory.extractItem(i, 1, false));
        }
        Optional<MortarRecipe> match = level.getRecipeManager().getRecipeFor(MortarRecipe.Type.INSTANCE, inventory, level);
        Astromancy.LOGGER.info(match);

        if(match.isPresent()) {
            for(int i = 0; i < entity.inventory.nonEmptyItemAmount; i++){
                entity.inventory.extractItem(i, 1, false);
            }
            entity.output.setStackInSlot(0, match.get().getResultItem());
            entity.progress = 0;
            entity.spins = 0;
            entity.cooldown = 0;
        }
    }

    @Override
    public void tick() {
        super.tick();
        spinning = 0;
        if(cooldown < 10 && spins > 1){
            cooldown++;
        }
    }

    @Override
    public void onBreak(@Nullable Player player) {
        inventory.dumpItems(level, worldPosition);
    }
}
