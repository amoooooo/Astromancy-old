package coffee.amo.astromancy.common.blockentity.mortar;

import coffee.amo.astromancy.common.item.PestleItem;
import coffee.amo.astromancy.common.recipe.MortarRecipe;
import coffee.amo.astromancy.core.helpers.BlockHelper;
import coffee.amo.astromancy.core.registration.BlockEntityRegistration;
import coffee.amo.astromancy.core.systems.blockentity.AstromancyBlockEntityInventory;
import coffee.amo.astromancy.core.systems.blockentity.ItemHolderBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
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
    public MortarRecipe recipe;
    public int progress;
    public int cooldown;
    public int spins;

    public MortarBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistration.MORTAR.get(), pos, state);
        inventory = new AstromancyBlockEntityInventory(6, 1) {
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
        if(player.getItemInHand(hand).isEmpty() && !player.isCrouching()){
            if(spins < 5){
                spins++;
            } else {
                craftItem(this);
            }
            player.sendMessage(new TextComponent(String.valueOf(spins)), player.getUUID());
            return InteractionResult.SUCCESS;
        }
        return super.onUse(player, hand);
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
        System.out.println(match);

        if(match.isPresent()) {
            for(int i = 0; i < entity.inventory.nonEmptyItemAmount; i++){
                entity.inventory.extractItem(i, 1, false);
            }
            entity.inventory.setStackInSlot(0, match.get().getResultItem());
            entity.progress = 0;
            entity.spins = 0;
            entity.cooldown = 0;
        }
    }

    @Override
    public void tick() {
        super.tick();
        if(cooldown < 10 && spins > 1){
            cooldown++;
        }
    }

    @Override
    public void onBreak(@Nullable Player player) {
        inventory.dumpItems(level, worldPosition);
    }
}
