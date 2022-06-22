package coffee.amo.astromancy.client.screen.stellalibri.pages;

import coffee.amo.astromancy.core.registration.ResearchRegistry;
import coffee.amo.astromancy.core.systems.research.ResearchTypeRegistry;
import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.TreeMap;

public class ResearchPageRegistry {
    public static HashMap<ResourceLocation, TreeMap<Integer, BookPage>> pages = new HashMap<>();

    public static void registerPage(ResourceLocation research, int page, BookPage pageObject) {
        if (!pages.containsKey(research)) {
            pages.put(research, new TreeMap<>());
        }
        pages.get(research).put(page, pageObject);
    }

    public static TreeMap<Integer, BookPage> getPages(ResourceLocation research) {
        return pages.get(research);
    }

    public static void register(){
        registerPage(ResearchRegistry.INTRODUCTION.get().getResearchName(), 0, new HeadlineTextPage("introduction", "introduction.a"));
        registerPage(ResearchRegistry.INTRODUCTION.get().getResearchName(), 1, new TextPage("introduction.b"));
        registerPage(ResearchRegistry.STELLARITE.get().getResearchName(), 0, new HeadlineTextPage("stellarite", "stellarite.a"));
        registerPage(ResearchRegistry.ARCANA_SEQUENCE.get().getResearchName(), 0, new HeadlineTextPage("arcana_sequence", "arcana_sequence.a"));
        registerPage(ResearchRegistry.ALCHEMICAL_BRASS.get().getResearchName(), 0, new TextPage("alchemical_brass.a"));
        registerPage(ResearchRegistry.ARMILLARY_SPHERE.get().getResearchName(), 0, new HeadlineTextPage("armillary_sphere", "armillary_sphere.a"));
        registerPage(ResearchRegistry.CRUCIBLE.get().getResearchName(), 0, new HeadlineTextPage("crucible", "crucible.a"));

        registerPage(ResearchRegistry.ASPECTI_PHIAL.get().getResearchName(), 0, new HeadlineTextPage("aspecti_phial", "aspecti_phial.a"));
        registerPage(ResearchRegistry.JAR.get().getResearchName(), 0, new HeadlineTextPage("jars", "jars.a"));
    }
}
