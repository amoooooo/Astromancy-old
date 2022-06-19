package coffee.amo.astromancy.core.handlers;

import coffee.amo.astromancy.core.capability.IPlayerResearch;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public class PlayerResearchHandler {
    public static Capability<IPlayerResearch> RESEARCH_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {});
}
