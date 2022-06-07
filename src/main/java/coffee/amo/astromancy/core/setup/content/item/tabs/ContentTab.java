package coffee.amo.astromancy.core.setup.content.item.tabs;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import static coffee.amo.astromancy.Astromancy.MODID;

public class ContentTab extends CreativeModeTab {
    public static final ContentTab INSTANCE = new ContentTab();
    public static @NotNull ContentTab get(){return INSTANCE;}
    public ContentTab() {
        super(MODID);
    }

    @Override
    public ItemStack makeIcon() {
        return ItemStack.EMPTY;
    }
}
