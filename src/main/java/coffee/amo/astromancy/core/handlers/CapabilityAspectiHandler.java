package coffee.amo.astromancy.core.handlers;

import coffee.amo.astromancy.core.systems.aspecti.IAspectiHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;

public class CapabilityAspectiHandler {

    public static Capability<IAspectiHandler> ASPECTI_HANDLER_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {});

    public static void init(){
        MinecraftForge.EVENT_BUS.addListener(CapabilityAspectiHandler::register);
    }

    public static void register(RegisterCapabilitiesEvent event){
        event.register(IAspectiHandler.class);
    }
}
