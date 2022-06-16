package coffee.amo.astromancy.core.events;

import coffee.amo.astromancy.Astromancy;
import coffee.amo.astromancy.core.registration.ItemRegistry;
import coffee.amo.astromancy.core.systems.aspecti.Aspecti;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = "astromancy", bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientEventsModBus {
    @SubscribeEvent
    public static void registerItemColors(final ColorHandlerEvent.Item event){
        Astromancy.LOGGER.debug("Registering item colors");
        event.getItemColors().register((stack, tintIndex) -> {
            if(stack.hasTag()){
                if(tintIndex == 1){
                    return Aspecti.values()[((CompoundTag) stack.getTag().get("aspecti")).getInt("aspecti")].color().getRGB();
                }
            }
            return 0xFFFFFF;
        }, ItemRegistry.ASPECTI_PHIAL.get());
    }
}
