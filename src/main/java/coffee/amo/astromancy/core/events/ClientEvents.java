package coffee.amo.astromancy.core.events;

import coffee.amo.astromancy.Astromancy;
import coffee.amo.astromancy.aequivaleo.AspectiHelper;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = "astromancy")
public class ClientEvents {

    @SubscribeEvent
    public static void aspectiTooltips(ItemTooltipEvent event){
        if(Screen.hasShiftDown()){
            TextComponent symbols = AspectiHelper.getEntry(event.getPlayer().level.dimension(), event.getItemStack()).sort().getTooltip();
            event.getToolTip().add(symbols);
        }
    }
}
