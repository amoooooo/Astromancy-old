package coffee.amo.astromancy.core.setup.content.item.tabs;

import coffee.amo.astromancy.core.registration.ItemRegistry;
import coffee.amo.astromancy.core.systems.aspecti.Aspecti;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
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
        return ItemRegistry.ASPECTI_PHIAL.get().getDefaultInstance();
    }

    @Override
    public void fillItemList(NonNullList<ItemStack> pItems) {
        super.fillItemList(pItems);
        for(Aspecti aspecti : Aspecti.values()) {
            if(aspecti == Aspecti.EMPTY){
                continue;
            }
            pItems.add(addAspectiPhial(ItemRegistry.ASPECTI_PHIAL.get().getDefaultInstance(), aspecti.ordinal()));
        }
    }

    private ItemStack addAspectiPhial(ItemStack stack, int aspecti) {
        CompoundTag tag = new CompoundTag();
        tag.putInt("aspecti", aspecti);
        tag.putInt("count", 16);
        stack.getOrCreateTag().put("aspecti", tag);
        return stack;
    }
}
