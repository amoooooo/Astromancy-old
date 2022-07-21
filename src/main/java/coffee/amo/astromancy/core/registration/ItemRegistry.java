package coffee.amo.astromancy.core.registration;

import coffee.amo.astromancy.Astromancy;
import coffee.amo.astromancy.common.blockentity.armillary_sphere.ArmillarySphereCoreBlockEntity;
import coffee.amo.astromancy.common.item.*;
import coffee.amo.astromancy.core.setup.content.item.tabs.ContentTab;
import coffee.amo.astromancy.core.setup.content.item.tabs.PhialTab;
import coffee.amo.astromancy.core.systems.multiblock.MultiblockItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemRegistry {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Astromancy.MODID);

    public static final RegistryObject<Item> ARMILLARY_SPHERE = ITEMS.register("armillary_sphere", () -> new MultiblockItem(BlockRegistration.ARMILLARY_SPHERE.get(), DEFAULT_PROPERTIES(), ArmillarySphereCoreBlockEntity.STRUCTURE));
//    public static final RegistryObject<Item> EMPTY_ASPECTI_PHIAL = ITEMS.register("empty_aspecti_phial", () -> new EmptyAspectiPhial(PHIAL_PROPERTIES().stacksTo(16)));
    public static final RegistryObject<Item> GLYPH_PHIAL = ITEMS.register("glyph_phial", () -> new GlyphPhial(PHIAL_PROPERTIES().stacksTo(16)));
    public static final RegistryObject<Item> AURUMIC_BRASS_INGOT = ITEMS.register("aurumic_brass_ingot", () -> new AurumicBrassIngot(DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> ARMILLARY_SPHERE_CAGE = ITEMS.register("armillary_sphere_cage", () -> new ArmillarySphereCage(DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> ARCANA_SEQUENCE = ITEMS.register("arcana_sequence", () -> new ArcanaSequence(DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> STELLA_LIBRI = ITEMS.register("stella_libri", () -> new StellaLibri(DEFAULT_PROPERTIES().stacksTo(1)));
    public static final RegistryObject<BlockItem> JAR = ITEMS.register("jar", () -> new JarItem(BlockRegistration.JAR.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<BlockItem> STAR_GATEWAY = ITEMS.register("star_gateway", () -> new BlockItem(BlockRegistration.STAR_GATEWAY.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<BlockItem> ASTROLABE = ITEMS.register("astrolabe", () -> new BlockItem(BlockRegistration.ASTROLABE.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<BlockItem> CRUCIBLE = ITEMS.register("crucible", () -> new BlockItem(BlockRegistration.CRUCIBLE.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<BlockItem> STELLARITE = ITEMS.register("stellarite", () -> new BlockItem(BlockRegistration.STELLARITE.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> STELLARITE_DUST = ITEMS.register("stellarite_dust", () -> new StellariteDust(DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> RESEARCH_NOTE = ITEMS.register("research_note", () -> new ResearchNote(DEFAULT_PROPERTIES().stacksTo(1)));
    public static final RegistryObject<BlockItem> MORTAR = ITEMS.register("mortar", () -> new BlockItem(BlockRegistration.MORTAR.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> GLYPH_RESEARCH_ICON = ITEMS.register("glyph_research_icon", () -> new GlyphEntryItem(ICON_PROPERTIES().stacksTo(1)));
    public static final RegistryObject<Item> VELLUM = ITEMS.register("vellum", () -> new Item(DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> FEATHER_QUILL = ITEMS.register("feather_quill", () -> new Item(DEFAULT_PROPERTIES()));
//    public static final RegistryObject<Item> TEST_RING = ITEMS.register("test_ring", () -> new TestRing(DEFAULT_PROPERTIES().stacksTo(1)));
//    public static final RegistryObject<BlockItem> RESEARCH_TABLE = ITEMS.register("research_table", () -> new BlockItem(BlockRegistration.RESEARCH_TABLE.get(), DEFAULT_PROPERTIES()));


    public static void register() {}

    public static Item.Properties DEFAULT_PROPERTIES() {
        return new Item.Properties().tab(ContentTab.INSTANCE);
    }

    public static Item.Properties ICON_PROPERTIES() {
        return new Item.Properties().tab(null);
    }

    public static Item.Properties PHIAL_PROPERTIES() {
        return new Item.Properties().tab(PhialTab.INSTANCE);
    }
}
