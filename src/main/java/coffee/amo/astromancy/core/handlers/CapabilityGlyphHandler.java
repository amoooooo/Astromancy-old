package coffee.amo.astromancy.core.handlers;

import coffee.amo.astromancy.core.systems.glyph.IGlyphHandler;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;

public class CapabilityGlyphHandler {

    public static Capability<IGlyphHandler> GLYPH_HANDLER_CAPABILITY = CapabilityManager.get(new CapabilityToken<>(){});

    public static void register(RegisterCapabilitiesEvent event){
        event.register(IGlyphHandler.class);
    }
}
