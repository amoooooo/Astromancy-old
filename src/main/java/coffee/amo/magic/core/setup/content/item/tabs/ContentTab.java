package coffee.amo.magic.core.setup.content.item.tabs;

import coffee.amo.magic.core.registration.BlockRegistration;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import static coffee.amo.magic.Magic.MODID;
import static coffee.amo.magic.Magic.REGISTRATE;

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
