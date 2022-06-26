package coffee.amo.astromancy.core.systems.research;

import net.minecraft.resources.ResourceLocation;

public class ResearchTabType {
    private final ResourceLocation tabName;

    public ResearchTabType(ResourceLocation tabName) {
        this.tabName = tabName;
    }

    public ResourceLocation getTabName() {
        return tabName;
    }
}
