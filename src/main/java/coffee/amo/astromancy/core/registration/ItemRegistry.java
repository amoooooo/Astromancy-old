package coffee.amo.astromancy.core.registration;

import coffee.amo.astromancy.Astromancy;
import coffee.amo.astromancy.common.item.ArcanaSequence;
import coffee.amo.astromancy.common.item.StellaLibri;
import coffee.amo.astromancy.core.setup.content.item.tabs.ContentTab;
import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.world.item.Item;

public class ItemRegistry {
    private static final Registrate REGISTRATE = Astromancy.registrate().creativeModeTab(ContentTab::get);

    public static final ItemEntry<StellaLibri> STELLA_LIBRI = REGISTRATE.item("stella_libri", StellaLibri::new).register();
    public static final ItemEntry<ArcanaSequence> ARCANA_SEQUENCE = REGISTRATE.item("arcana_sequence", ArcanaSequence::new).register();

    public static ItemEntry<Item> itemRegister(String name){
        return Astromancy.registrate().item(name,Item::new).tab(ContentTab::get).register();
    }

    public static void register() {}
}
