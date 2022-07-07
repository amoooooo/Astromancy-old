package coffee.amo.astromancy.client.screen.stellalibri.pages;

import coffee.amo.astromancy.core.registration.BlockRegistration;
import coffee.amo.astromancy.core.registration.ResearchRegistry;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.TreeMap;

import static coffee.amo.astromancy.core.registration.ItemRegistry.*;

public class ResearchPageRegistry {
    public static HashMap<ResourceLocation, TreeMap<Integer, BookPage>> pages = new HashMap<>();

    public static void registerPage(ResourceLocation research, int page, BookPage pageObject) {
        if (!pages.containsKey(research)) {
            pages.put(research, new TreeMap<>());
        }
        pages.get(research).put(page, pageObject);
    }

    public static void registerPages(ResourceLocation research, BookPage... pages){
        for(int i = 0; i < pages.length; i++){
            registerPage(research, i, pages[i]);
        }
    }

    public static TreeMap<Integer, BookPage> getPages(ResourceLocation research) {
        return pages.get(research);
    }

    public static void register(){
        registerPage(ResearchRegistry.INTRODUCTION.get().getResearchName(), 0, new HeadlineTextPage("introduction", "introduction.a", "introduction").setHidden(false));
        registerPage(ResearchRegistry.INTRODUCTION.get().getResearchName(), 1, new TextPage("introduction.b", "introduction").setHidden(false));
        registerPage(ResearchRegistry.STELLARITE.get().getResearchName(), 0, new HeadlineTextPage("stellarite", "stellarite.a", "stellarite").setHidden(false));
        registerPage(ResearchRegistry.ARCANA_SEQUENCE.get().getResearchName(), 0, new HeadlineTextPage("arcana_sequence", "arcana_sequence.a", "arcana_sequence").setHidden(false));
        registerPage(ResearchRegistry.AURUMIC_BRASS.get().getResearchName(), 0, new HeadlineTextPage("aurumic_brass", "aurumic_brass.a", "aurumic_brass").setHidden(false));
        registerPages(ResearchRegistry.CRUCIBLE.get().getResearchName(), new HeadlineTextPage("crucible", "crucible.a", "crucible").setHidden(false), StructurePage.cruciblePage(BlockRegistration.CRUCIBLE.get()));

        registerPages(ResearchRegistry.GLYPH_PHIAL.get().getResearchName(), new HeadlineTextPage("glyph_phial", "glyph_phial.a", "glyph_phial").setHidden(false),
                new TextPage("glyph_phial.b", "glyph_phial").setHidden(false),
                CraftingPage.phialPage(GLYPH_PHIAL.get()).setHidden(false));
        registerPage(ResearchRegistry.JAR.get().getResearchName(), 0, new HeadlineTextPage("jars", "jars.a", "jars").setHidden(false));

        registerPage(ResearchRegistry.STARGAZING.get().getResearchName(), 0, new HeadlineTextPage("stargazing", "stargazing.a", "stargazing").setHidden(false));
        registerPage(ResearchRegistry.SOLAR_ECLIPSE.get().getResearchName(), 0, new HeadlineTextPage("solar_eclipse", "solar_eclipse.a", "solar_eclipse").setHidden(false));
        registerPages(ResearchRegistry.ARMILLARY_SPHERE.get().getResearchName(),
                new HeadlineTextPage("armillary_sphere", "armillary_sphere.a", "armillary_sphere").setHidden(false),
                CraftingPage.armSpherePage(ARMILLARY_SPHERE.get(), ARMILLARY_SPHERE_CAGE.get(), AURUMIC_BRASS_INGOT.get()).setHidden(true),
                CraftingPage.armCagePage(ARMILLARY_SPHERE_CAGE.get(), AURUMIC_BRASS_INGOT.get()).setHidden(true));

        registerPage(ResearchRegistry.STELLAR_OBSERVATORY.get().getResearchName(), 0, new HeadlineTextPage("stellar_observatory", "stellar_observatory.a", "stellar_observatory").setHidden(false));

        registerPage(ResearchRegistry.TEST_1.get().getResearchName(), 0, new HeadlineTextPage("test_1", "test_1.a", "test_1").setHidden(false));

        registerPage(ResearchRegistry.FLEETING_TEST.get().getResearchName(), 0, new HeadlineTextPage("fleeting_test", "fleeting_test.a", "fleeting_test").setHidden(false));

        registerPage(ResearchRegistry.GLYPH.get().getResearchName(), 0, new HeadlineTextPage("glyph", "glyph.a", "glyph").setHidden(false));
    }
}
