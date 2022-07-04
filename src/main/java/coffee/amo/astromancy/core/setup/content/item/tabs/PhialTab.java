package coffee.amo.astromancy.core.setup.content.item.tabs;

import coffee.amo.astromancy.core.registration.ItemRegistry;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import static coffee.amo.astromancy.Astromancy.MODID;

public class PhialTab extends CreativeModeTab {
    public static final PhialTab INSTANCE = new PhialTab();
    public static @NotNull PhialTab get(){ return INSTANCE; }
    public PhialTab() {
        super(MODID + ".phialTab");
    }

    @Override
    public ItemStack makeIcon() {
        return ItemRegistry.GLYPH_PHIAL.get().getDefaultInstance();
    }

//    @Override
//    public void fillItemList(NonNullList<ItemStack> pItems) {
//        super.fillItemList(pItems);
//        for(Aspecti glyph : Aspecti.values()) {
//            if(glyph == Aspecti.EMPTY){
//                continue;
//            }
//            pItems.add(addAspectiPhial(ItemRegistry.GLYPH_PHIAL.get().getDefaultInstance(), glyph.ordinal()));
//        }
//    }
//
//    private ItemStack addAspectiPhial(ItemStack stack, int glyph) {
//        CompoundTag tag = new CompoundTag();
//        tag.putInt("glyph", glyph);
//        tag.putInt("count", 16);
//        stack.getOrCreateTag().put("glyph", tag);
//        return stack;
//    }
}
