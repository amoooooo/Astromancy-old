package coffee.amo.astromancy.common.item;

import coffee.amo.astromancy.core.registration.ItemRegistry;
import coffee.amo.astromancy.core.systems.stars.Star;
import coffee.amo.astromancy.core.systems.stars.classification.ConstellationInstance;
import coffee.amo.astromancy.core.util.StarSavedData;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
                    for(ConstellationInstance q : StarSavedData.get(pLevel.getServer()).getConstellationInstances()){
                        q.getStarMap().forEach((x, yMap) -> {
                            yMap.forEach((y, star) -> {
                                pPlayer.sendMessage(new TextComponent(star.getType().toString() + " " + star.getSpectralClass()), pPlayer.getUUID());
                            });
                        });
                    }
                }
            }
        }
    return InteractionResultHolder.pass(pPlayer.getItemInHand(pUsedHand));
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        if(pStack.hasTag()){
            pTooltipComponents.add(new TextComponent(((CompoundTag) pStack.getTag().get("star")).getString("name").replaceAll("\\[(.*?)\\]", "")).withStyle(s -> s.withColor(ChatFormatting.GOLD)));
            if(pIsAdvanced.isAdvanced()){
                pTooltipComponents.add(new TextComponent(((CompoundTag) pStack.getTag().get("star")).getString("name")).withStyle(s -> s.withColor(ChatFormatting.GRAY)));
            }
        }

    }
}
