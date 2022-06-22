package coffee.amo.astromancy.core.registration;

import coffee.amo.astromancy.Astromancy;
import coffee.amo.astromancy.aequivaleo.AspectiEntry;
import coffee.amo.astromancy.common.blockentity.armillary_sphere.ArmillarySphereCoreBlockEntity;
import coffee.amo.astromancy.common.item.*;
import coffee.amo.astromancy.core.helpers.StringHelper;
import coffee.amo.astromancy.core.setup.content.item.tabs.ContentTab;
import coffee.amo.astromancy.core.setup.content.item.tabs.PhialTab;
import coffee.amo.astromancy.core.systems.aspecti.Aspecti;
import coffee.amo.astromancy.core.systems.multiblock.MultiblockItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Locale;

public class ItemRegistry {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Astromancy.MODID);

    public static final RegistryObject<Item> ARMILLARY_SPHERE = ITEMS.register("armillary_sphere", () -> new MultiblockItem(BlockRegistration.ARMILLARY_SPHERE.get(), DEFAULT_PROPERTIES(), ArmillarySphereCoreBlockEntity.STRUCTURE));
    public static final RegistryObject<Item> EMPTY_ASPECTI_PHIAL = ITEMS.register("empty_aspecti_phial", () -> new EmptyAspectiPhial(PHIAL_PROPERTIES().stacksTo(16)));
    public static final RegistryObject<Item> ASPECTI_PHIAL = ITEMS.register("aspecti_phial", () -> new AspectiPhial(PHIAL_PROPERTIES().stacksTo(16)));
    public static final RegistryObject<Item> ALCHEMICAL_BRASS_INGOT = ITEMS.register("alchemical_brass_ingot", () -> new AlchemicalBrassIngot(DEFAULT_PROPERTIES()));
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


    public static void register() {}

    public static Item.Properties DEFAULT_PROPERTIES() {
        return new Item.Properties().tab(ContentTab.INSTANCE);
    }

    public static Item.Properties PHIAL_PROPERTIES() {
        return new Item.Properties().tab(PhialTab.INSTANCE);
    }
}
