package coffee.amo.astromancy.core.systems.research;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class ResearchType extends ForgeRegistryEntry<ResearchType> {
    private final ResourceLocation researchName;

    public ResearchType(ResourceLocation registryName) {
        this.researchName = registryName;
    }

    public ResourceLocation getResearchName() {
        return researchName;
    }
}
