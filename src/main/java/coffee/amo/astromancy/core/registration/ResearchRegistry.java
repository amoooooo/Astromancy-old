package coffee.amo.astromancy.core.registration;

import coffee.amo.astromancy.Astromancy;
import coffee.amo.astromancy.core.systems.research.ResearchObject;
import coffee.amo.astromancy.core.systems.research.ResearchTypeRegistry;
import net.minecraft.world.item.Items;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;

public class ResearchRegistry {
    public static final List<RegistryObject<ResearchObject>> RESEARCH_LIST = new ArrayList<>();
    private static final DeferredRegister<ResearchObject> RESEARCH = DeferredRegister.create(ResearchTypeRegistry.registryLocation, Astromancy.MODID);
    public static final RegistryObject<ResearchObject> INTRODUCTION = RESEARCH.register("introduction", () -> new ResearchObject("introduction",0,0,"important")
            .setIcon(ItemRegistry.STELLA_LIBRI.get().getDefaultInstance()));
    public static final RegistryObject<ResearchObject> STELLARITE = RESEARCH.register("stellarite", () -> new ResearchObject("stellarite",-1,-1,"").setIcon(ItemRegistry.STELLARITE.get().getDefaultInstance()));
    public static final RegistryObject<ResearchObject> CRUCIBLE = RESEARCH.register("crucible", () -> new ResearchObject("crucible",-1,-2,"").setIcon(ItemRegistry.CRUCIBLE.get().getDefaultInstance()));
    public static final RegistryObject<ResearchObject> JAR = RESEARCH.register("jar", () -> new ResearchObject("jars",-1,-1,"").setIcon(ItemRegistry.JAR.get().getDefaultInstance()));
    public static final RegistryObject<ResearchObject> AURUMIC_BRASS = RESEARCH.register("aurumic_brass", () -> new ResearchObject("aurumic_brass",1,-1,"").setIcon(ItemRegistry.AURUMIC_BRASS_INGOT.get().getDefaultInstance()));
    public static final RegistryObject<ResearchObject> ARMILLARY_SPHERE = RESEARCH.register("armillary_sphere", () -> new ResearchObject("armillary_sphere",2,0,"").setIcon(ItemRegistry.ARMILLARY_SPHERE.get().getDefaultInstance()));
    public static final RegistryObject<ResearchObject> ARCANA_SEQUENCE = RESEARCH.register("arcana_sequence", () -> new ResearchObject("arcana_sequence",1,1,"").setIcon(ItemRegistry.ARCANA_SEQUENCE.get().getDefaultInstance()));
    public static final RegistryObject<ResearchObject> GLYPH_PHIAL = RESEARCH.register("glyph_phial", () -> new ResearchObject("glyph_phial",0,0,"important").setIcon(ItemRegistry.GLYPH_PHIAL.get().getDefaultInstance()));
    public static final RegistryObject<ResearchObject> STARGAZING = RESEARCH.register("stargazing", () -> new ResearchObject("stargazing",0,0,"").setIcon(Items.SPYGLASS.getDefaultInstance()));
    public static final RegistryObject<ResearchObject> SOLAR_ECLIPSE = RESEARCH.register("solar_eclipse", () -> new ResearchObject("solar_eclipse",-1,-1,"").setIcon(Items.SUNFLOWER.getDefaultInstance()));
    public static final RegistryObject<ResearchObject> STELLAR_OBSERVATORY = RESEARCH.register("stellar_observatory", () -> new ResearchObject("stellar_observatory",3,0,"").setIcon(ItemRegistry.STAR_GATEWAY.get().getDefaultInstance()));
    public static final RegistryObject<ResearchObject> TEST_1 = RESEARCH.register("test_1", () -> new ResearchObject("test_1",-1,1,"").setIcon(Items.STONE.getDefaultInstance()));
    public static final RegistryObject<ResearchObject> GLYPH = RESEARCH.register("glyph", () -> new ResearchObject("glyph",-2f,1.5f,"important").setIcon(ItemRegistry.GLYPH_RESEARCH_ICON.get().getDefaultInstance()));

    public static final RegistryObject<ResearchObject> FLEETING_TEST = RESEARCH.register("fleeting_test", () -> new ResearchObject("fleeting_test",-3,0,"fleeting").setIcon(Items.GLOW_INK_SAC.getDefaultInstance()));

    public static void register(){
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        RESEARCH.register(bus);
        Astromancy.LOGGER.info("REGISTERING" + RESEARCH.getEntries().stream().map(RegistryObject::getId).toList());
    }

    public static void doSetup(){
        INTRODUCTION.get().addChildren(STELLARITE.get(), AURUMIC_BRASS.get(), TEST_1.get());
        STELLARITE.get().addChild(CRUCIBLE.get());
        AURUMIC_BRASS.get().addChild(ARMILLARY_SPHERE.get());
        GLYPH_PHIAL.get().addChild(JAR.get());
        STARGAZING.get().addChild(SOLAR_ECLIPSE.get());
        ARMILLARY_SPHERE.get().addChildren(ARCANA_SEQUENCE.get(), STELLAR_OBSERVATORY.get());
    }
}
