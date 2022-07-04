package coffee.amo.astromancy.aequivaleo;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class GlyphConversionWrapper {
    private static final GlyphConversionWrapper INVALID = new GlyphConversionWrapper();

    private final ItemStack item;

    public GlyphConversionWrapper(){
        this.item = ItemStack.EMPTY;
    }

    public GlyphConversionWrapper(ItemStack item){
        this.item = item;
    }

    public GlyphConversionWrapper(Item item){
        this.item = new ItemStack(item);
    }
}
