package coffee.amo.astromancy.core.registration;

import coffee.amo.astromancy.Astromancy;
import coffee.amo.astromancy.client.screen.stellalibri.BookTextures;
import coffee.amo.astromancy.core.systems.research.ResearchTabObject;
import coffee.amo.astromancy.core.systems.research.ResearchTypeRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class TabRegistry {
    private static final DeferredRegister<ResearchTabObject> RESEARCH_TABS = DeferredRegister.create(ResearchTypeRegistry.tabLoc, Astromancy.MODID);
    public static final RegistryObject<ResearchTabObject> INTRODUCTION = RESEARCH_TABS.register("introduction", () -> new ResearchTabObject("introduction", 1, 1, BookTextures.TAB1_PARALLAX, ItemRegistry.STELLA_LIBRI.get().getDefaultInstance()));
    public static final RegistryObject<ResearchTabObject> ALCHEMY = RESEARCH_TABS.register("alchemy", () -> new ResearchTabObject("alchemy", 1, 2, BookTextures.BACKGROUND_TEXTURE, ItemRegistry.CRUCIBLE.get().getDefaultInstance()));
    public static final RegistryObject<ResearchTabObject> STARGAZING = RESEARCH_TABS.register("stargazing", () -> new ResearchTabObject("stargazing", 1, 3, Astromancy.astromancy("textures/gui/book/test/green_planets.png"), ItemRegistry.ARMILLARY_SPHERE.get().getDefaultInstance()));

    public static void register() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        RESEARCH_TABS.register(bus);
        Astromancy.LOGGER.debug("REGISTERING TABS: " + RESEARCH_TABS.getEntries().stream().map(RegistryObject::getId).toList());
    }

    public static void doSetup() {
        INTRODUCTION.get().addChildren(ResearchRegistry.INTRODUCTION.get(), ResearchRegistry.STELLARITE.get(), ResearchRegistry.CRUCIBLE.get(), ResearchRegistry.ALCHEMICAL_BRASS.get(), ResearchRegistry.ARCANA_SEQUENCE.get(), ResearchRegistry.ARMILLARY_SPHERE.get(), ResearchRegistry.STELLAR_OBSERVATORY.get());
        ALCHEMY.get().addChildren(ResearchRegistry.ASPECTI_PHIAL.get(), ResearchRegistry.JAR.get());
        STARGAZING.get().addChildren(ResearchRegistry.STARGAZING.get(), ResearchRegistry.SOLAR_ECLIPSE.get());
    }
}
