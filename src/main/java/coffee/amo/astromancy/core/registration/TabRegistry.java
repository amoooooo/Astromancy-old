package coffee.amo.astromancy.core.registration;

import coffee.amo.astromancy.Astromancy;
import coffee.amo.astromancy.client.screen.stellalibri.BookTextures;
import coffee.amo.astromancy.core.systems.research.ResearchTabObject;
import coffee.amo.astromancy.core.systems.research.ResearchTypeRegistry;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.awt.*;

public class TabRegistry {
    private static final DeferredRegister<ResearchTabObject> RESEARCH_TABS = DeferredRegister.create(ResearchTypeRegistry.tabLoc, Astromancy.MODID);
    public static final RegistryObject<ResearchTabObject> INTRODUCTION = RESEARCH_TABS.register("introduction",
            () -> new ResearchTabObject("introduction", 1, 1, BookTextures.INTRO_BACKGROUND, ItemRegistry.STELLA_LIBRI.get().getDefaultInstance())
                    .setColor(new Color(227,205,131, 255)));
    public static final RegistryObject<ResearchTabObject> ALCHEMY = RESEARCH_TABS.register("alchemy",
            () -> new ResearchTabObject("alchemy", 1, 2, Astromancy.astromancy("textures/gui/book/test/blue2.png"), ItemRegistry.CRUCIBLE.get().getDefaultInstance())
                    .setColor(new Color(242, 99, 118, 255)));
    public static final RegistryObject<ResearchTabObject> STARGAZING = RESEARCH_TABS.register("stargazing",
            () -> new ResearchTabObject("stargazing", 1, 3, Astromancy.astromancy("textures/gui/book/test/green_planets.png"), ItemRegistry.ARMILLARY_SPHERE.get().getDefaultInstance())
                    .setColor(new Color(174, 153, 232, 255)));

    public static void register() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        RESEARCH_TABS.register(bus);
        Astromancy.LOGGER.debug("REGISTERING TABS: " + RESEARCH_TABS.getEntries().stream().map(RegistryObject::getId).toList());
    }

    public static void doSetup() {
        INTRODUCTION.get().addChildren(ResearchRegistry.INTRODUCTION.get(),
                ResearchRegistry.STELLARITE.get(),
                ResearchRegistry.CRUCIBLE.get(),
                ResearchRegistry.AURUMIC_BRASS.get(),
                ResearchRegistry.ARCANA_SEQUENCE.get(),
                ResearchRegistry.ARMILLARY_SPHERE.get(),
                ResearchRegistry.STELLAR_OBSERVATORY.get(),
                ResearchRegistry.TEST_1.get(),
                ResearchRegistry.GLYPH.get(),
                ResearchRegistry.FLEETING_TEST.get());
        ALCHEMY.get().addChildren(ResearchRegistry.GLYPH_PHIAL.get(),
                ResearchRegistry.JAR.get());
        STARGAZING.get().addChildren(ResearchRegistry.STARGAZING.get(),
                ResearchRegistry.SOLAR_ECLIPSE.get());
       RESEARCH_TABS.getEntries().stream().map(RegistryObject::get).toList().forEach(r -> {
            Astromancy.LOGGER.info("REGISTERED TAB: " + r.getIdentifier() + " with children: " + r.getChildren().stream().map(s -> s.identifier).toList());
        });
    }
}
