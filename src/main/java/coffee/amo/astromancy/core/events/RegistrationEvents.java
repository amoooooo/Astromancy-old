package coffee.amo.astromancy.core.events;

import coffee.amo.astromancy.core.registration.AttributeRegistry;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "astromancy", bus = Mod.EventBusSubscriber.Bus.MOD)
public class RegistrationEvents {
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeModificationEvent evt) {
        evt.add(EntityType.PLAYER, AttributeRegistry.ASTRAL_RESIST.get());
    }
}
