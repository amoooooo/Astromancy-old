package coffee.amo.astromancy.aequivaleo;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class AspectiConversionWrapper {
    private static final AspectiConversionWrapper INVALID = new AspectiConversionWrapper();

    private final ItemStack item;

    public AspectiConversionWrapper(){
        this.item = ItemStack.EMPTY;
    }

    public AspectiConversionWrapper(ItemStack item){
        this.item = item;
    }

    public AspectiConversionWrapper(Item item){
        this.item = new ItemStack(item);
    }
}
