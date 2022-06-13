package coffee.amo.astromancy.core.events;

import coffee.amo.astromancy.Astromancy;
import coffee.amo.astromancy.aequivaleo.AspectiHelper;
import coffee.amo.astromancy.common.item.ArcanaSequence;
import coffee.amo.astromancy.core.registration.ItemRegistry;
import coffee.amo.astromancy.core.systems.stars.Star;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = "astromancy")
public class ClientEvents {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void aspectiTooltips(ItemTooltipEvent event){
        if(event.getItemStack().getItem() instanceof ArcanaSequence){
            if(event.getItemStack().hasTag()){
                Star star = Star.fromNbt((CompoundTag) event.getItemStack().getTag().get("star"));
                String name = star.getName().replaceAll("\\[(.*?)\\]", "");
                event.getToolTip().add(new TextComponent(name).withStyle(s -> s.withColor(ChatFormatting.GOLD)));
                if(Screen.hasShiftDown()){
                    event.getToolTip().add(new TextComponent("[" + star.getQuadrantCoordinates().getFirst() + " of " + star.getQuadrants().getFirst().getName() + "], [" + star.getQuadrantCoordinates().getSecond() + " of " + star.getQuadrants().getSecond().name + "]").withStyle(s -> s.withColor(ChatFormatting.GRAY)));
                }
            }
        }
        if(Screen.hasShiftDown()){
            TextComponent symbols = AspectiHelper.getEntry(event.getPlayer().level.dimension(), event.getItemStack()).sort().getTooltip();
            event.getToolTip().add(symbols);
        }

    }
}
