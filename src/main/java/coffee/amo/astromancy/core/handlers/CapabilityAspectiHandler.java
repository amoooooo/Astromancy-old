package coffee.amo.astromancy.core.handlers;

import coffee.amo.astromancy.core.systems.aspecti.IAspectiHandler;
import coffee.amo.astromancy.core.systems.aspecti.IAspectiHandlerItem;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;

public class CapabilityAspectiHandler {

    public static Capability<IAspectiHandler> ASPECTI_HANDLER_CAPABILITY = CapabilityManager.get(new CapabilityToken<>(){});
    public static Capability<IAspectiHandlerItem> ASPECTI_HANDLER_ITEM_CAPABILITY = CapabilityManager.get(new CapabilityToken<>(){});

    public static void register(RegisterCapabilitiesEvent event){
        event.register(IAspectiHandler.class);
        event.register(IAspectiHandlerItem.class);
    }
}
