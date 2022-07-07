package coffee.amo.astromancy.core.handlers;

import coffee.amo.astromancy.core.systems.lumen.ILumenHandler;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;

public class CapabilityLumenHandler {
    public static Capability<ILumenHandler> LUMEN_HANDLER_CAPABILITY = CapabilityManager.get(new CapabilityToken<>(){});

    public static void register(RegisterCapabilitiesEvent event) {event.register(ILumenHandler.class);}
}
