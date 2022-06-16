package coffee.amo.astromancy.core.registration;

import coffee.amo.astromancy.Astromancy;
import coffee.amo.astromancy.common.blockentity.armillary_sphere.ArmillarySphereCoreBlockEntity;
import coffee.amo.astromancy.common.item.*;
import coffee.amo.astromancy.core.setup.content.item.tabs.ContentTab;
import coffee.amo.astromancy.core.systems.multiblock.MultiblockItem;
import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemRegistry {
    private static final Registrate REGISTRATE = Astromancy.registrate().creativeModeTab(ContentTab::get);

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Astromancy.MODID);

    public static final ItemEntry<StellaLibri> STELLA_LIBRI = REGISTRATE.item("stella_libri", StellaLibri::new).register();
    public static final ItemEntry<ArcanaSequence> ARCANA_SEQUENCE = REGISTRATE.item("arcana_sequence", ArcanaSequence::new).register();
    public static final ItemEntry<ArmillarySphereCage> ARMILLARY_SPHERE_CAGE = REGISTRATE.item("armillary_sphere_cage", ArmillarySphereCage::new).register();
    public static final ItemEntry<AlchemicalBrassIngot> ALCHEMICAL_BRASS_INGOT = REGISTRATE.item("alchemical_brass_ingot", AlchemicalBrassIngot::new).register();
    public static final ItemEntry<AspectiPhial> ASPECTI_PHIAL = REGISTRATE.item("aspecti_phial", AspectiPhial::new).register();

    public static final RegistryObject<Item> ARMILLARY_SPHERE = ITEMS.register("armillary_sphere", () -> new MultiblockItem(BlockRegistration.ARMILLARY_SPHERE.get(), DEFAULT_PROPERTIES(), ArmillarySphereCoreBlockEntity.STRUCTURE));

    public static ItemEntry<Item> itemRegister(String name){
        return Astromancy.registrate().item(name,Item::new).tab(ContentTab::get).register();
    }

    public static void register() {}

    public static Item.Properties DEFAULT_PROPERTIES() {
        return new Item.Properties().tab(ContentTab.INSTANCE);
    }
}
