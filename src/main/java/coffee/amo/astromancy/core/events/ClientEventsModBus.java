package coffee.amo.astromancy.core.events;

import coffee.amo.astromancy.Astromancy;
import coffee.amo.astromancy.common.blockentity.jar.JarBlockEntity;
import coffee.amo.astromancy.common.item.AspectiPhial;
import coffee.amo.astromancy.core.handlers.CapabilityAspectiHandler;
import coffee.amo.astromancy.core.registration.BlockRegistration;
import coffee.amo.astromancy.core.registration.ItemRegistry;
import coffee.amo.astromancy.core.systems.aspecti.Aspecti;
import coffee.amo.astromancy.core.systems.aspecti.AspectiStackHandler;
import coffee.amo.astromancy.core.systems.aspecti.IAspectiHandler;
import coffee.amo.astromancy.core.util.AstroKeys;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = "astromancy", bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientEventsModBus {
    @SubscribeEvent
    public static void registerItemColors(final ColorHandlerEvent.Item event){
        Astromancy.LOGGER.debug("Registering item colors");

        event.getItemColors().register((stack, tintIndex) -> {
            if(tintIndex == 1)
                return Aspecti.fromItemStack(stack).color().getRGB();
            return 0xFFFFFF;
        }, ItemRegistry.ASPECTI_PHIAL.get());

        event.getItemColors().register((stack, tintIndex) -> {
            LazyOptional<IAspectiHandler> cap = stack.getCapability(CapabilityAspectiHandler.ASPECTI_HANDLER_CAPABILITY);
            if(cap.isPresent() && tintIndex == 1) {
                IAspectiHandler handler = cap.orElseThrow(() -> new IllegalStateException("AspectiHandler is not present"));
                if(handler instanceof AspectiStackHandler ash)
                    return ash.getAspectiStack().getAspecti().color().getRGB();
            }
            return 0xFFFFFF;
        }, BlockRegistration.JAR.get());
    }

    @SubscribeEvent
    public static void registerBlockColors(final ColorHandlerEvent.Block event){
        Astromancy.LOGGER.debug("Registering block colors");
        event.getBlockColors().register((state, world, pos, tintIndex) -> {
            if(tintIndex == 1){
                if(world.getBlockEntity(pos) instanceof JarBlockEntity jb) {
                    return jb.getAspecti().color().getRGB();
                }
            }
            return 0xFFFFFFFF;
        }, BlockRegistration.JAR.get());
    }
}
