package coffee.amo.astromancy.core.systems.research;

import coffee.amo.astromancy.Astromancy;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.NewRegistryEvent;
import net.minecraftforge.registries.RegistryBuilder;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = Astromancy.MODID)
public class ResearchTypeRegistry {
    public static final ResourceLocation registryLocation = Astromancy.astromancy("research_types");
    public static Supplier<IForgeRegistry<ResearchType>> RESEARCH_TYPES;

    @SubscribeEvent
    public static void onNewRegistry(NewRegistryEvent event){
        RegistryBuilder<ResearchType> registryBuilder = new RegistryBuilder<>();
        registryBuilder.setName(registryLocation);
        registryBuilder.setType(ResearchType.class);
        RESEARCH_TYPES = event.create(registryBuilder);
    }
}
