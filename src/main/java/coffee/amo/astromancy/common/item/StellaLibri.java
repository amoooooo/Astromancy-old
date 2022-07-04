package coffee.amo.astromancy.common.item;

import coffee.amo.astromancy.client.screen.stellalibri.BookScreen;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class StellaLibri extends Item {
    private float openness = 0;
    public StellaLibri(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if(pLevel.isClientSide){
            BookScreen.openScreen(true);
        }
        if(!pLevel.isClientSide){
            pPlayer.getItemInHand(pUsedHand).getOrCreateTag().putInt("openness", 1);
        }
        return InteractionResultHolder.pass(pPlayer.getItemInHand(pUsedHand));
    }

    public float getOpenness(ItemStack stack) {
        return stack.getOrCreateTag().getInt("openness");
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return false;
    }

}
