package coffee.amo.astromancy.common.item;

import coffee.amo.astromancy.core.registration.ItemRegistry;
import coffee.amo.astromancy.core.systems.stars.Star;
import coffee.amo.astromancy.core.systems.stars.StarUtils;
import coffee.amo.astromancy.core.systems.stars.classification.Quadrant;
import coffee.amo.astromancy.core.util.StarSavedData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.List;

public class ArcanaSequence extends Item {
    public ArcanaSequence(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if(!pLevel.isClientSide){
            if(pPlayer.getItemInHand(pUsedHand).getItem() == ItemRegistry.ARCANA_SEQUENCE.get()){
                if(pPlayer.getItemInHand(pUsedHand).hasTag()){
                    List<String> messages = Star.fromNbt((CompoundTag) pPlayer.getItemInHand(pUsedHand).getTag().get("star")).getStringDetailed();
                    for(String message : messages){
                        pPlayer.sendMessage(new TextComponent(message), pPlayer.getUUID());
                    }
                    return InteractionResultHolder.success(pPlayer.getItemInHand(pUsedHand));
                } else if(pPlayer.isShiftKeyDown()){
                    for(Quadrant q : StarSavedData.get().getStars()){
                        for(Star star : q.getStars()){
                            pPlayer.sendMessage(new TextComponent(star.getType().getType()), pPlayer.getUUID());
                        }
                    }
                }
            }
        }
    return InteractionResultHolder.pass(pPlayer.getItemInHand(pUsedHand));
    }
}
