package coffee.amo.astromancy.common.item;

import coffee.amo.astromancy.core.systems.research.ResearchHelper;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class AlchemicalBrassIngot extends Item {
    public AlchemicalBrassIngot(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if(!pLevel.isClientSide && !pPlayer.isShiftKeyDown() && pPlayer.isCreative()){
            ResearchHelper.addResearch("introduction", pPlayer);
            pPlayer.sendMessage(new TextComponent("You have learned the basics of Astromancy!"), pPlayer.getUUID());
        } else if (!pLevel.isClientSide && pPlayer.isCrouching() && pPlayer.isCreative()){
            ResearchHelper.removeResearch("introduction", pPlayer);
            pPlayer.sendMessage(new TextComponent("You have forgotten the basics of Astromancy!"), pPlayer.getUUID());
        } else if (!pLevel.isClientSide && !pPlayer.isCreative()){
            pPlayer.sendMessage(new TextComponent("You must be in creative mode to learn the basics of Astromancy!"), pPlayer.getUUID());
            ResearchHelper.addResearch("armillary_sphere", pPlayer);
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }
}
