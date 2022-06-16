package coffee.amo.astromancy.common.item;

import coffee.amo.astromancy.client.screen.stellalibri.BookScreen;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
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
            pPlayer.swing(pUsedHand);
            return InteractionResultHolder.success(pPlayer.getItemInHand(pUsedHand));
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }

    public void setOpenness(float openness) {
        this.openness = openness;
    }

    public static float getOpenness(StellaLibri sl) {
        return sl.openness;
    }
}
