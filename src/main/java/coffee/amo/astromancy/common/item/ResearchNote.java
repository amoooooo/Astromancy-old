package coffee.amo.astromancy.common.item;

import coffee.amo.astromancy.client.research.ClientResearchHolder;
import coffee.amo.astromancy.core.handlers.PlayerResearchHandler;
import coffee.amo.astromancy.core.registration.SoundRegistry;
import coffee.amo.astromancy.core.systems.research.ResearchObject;
import coffee.amo.astromancy.core.systems.research.ResearchProgress;
import coffee.amo.astromancy.core.systems.research.ResearchType;
import coffee.amo.astromancy.core.systems.research.ResearchTypeRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ResearchNote extends Item {
    public ResearchNote(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if(!pLevel.isClientSide){
            if(pPlayer.getItemInHand(pUsedHand).hasTag()){
                List<ResearchType> researchObjects = ResearchTypeRegistry.RESEARCH_TYPES.get().getValues().stream().toList();
                for (ResearchType type : researchObjects) {
                    ResearchObject object = (ResearchObject) type;
                    if (object.identifier.equals(pPlayer.getItemInHand(pUsedHand).getTag().getString("researchId"))) {
                        ClientResearchHolder.addResearch(object);
                        if(ClientResearchHolder.containsIdentifier(object.identifier)){
                            ClientResearchHolder.getResearch().stream().filter(s -> s.identifier == object.identifier).findFirst().ifPresent(s -> {
                                s.locked = ResearchProgress.COMPLETED;
                            });
                        }
                        pPlayer.getCapability(PlayerResearchHandler.RESEARCH_CAPABILITY).ifPresent(p -> {
                            p.addResearch(pPlayer, object);
                        });
                        Minecraft.getInstance().player.playSound(SoundRegistry.RESEARCH_WRITE.get(), 0.5f, 1f);
                        pPlayer.setItemInHand(pUsedHand, ItemStack.EMPTY);
                    }
                }

            }
        }
        return InteractionResultHolder.success(pPlayer.getItemInHand(pUsedHand));
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        if(pStack.hasTag()){
            pTooltipComponents.add(new TranslatableComponent("astromancy.gui.book.entry." + pStack.getTag().getString("researchId")).withStyle(s -> s.withColor(ChatFormatting.GOLD)));
        }
    }
}
