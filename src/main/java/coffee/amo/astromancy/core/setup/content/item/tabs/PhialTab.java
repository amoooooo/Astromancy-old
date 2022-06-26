package coffee.amo.astromancy.core.setup.content.item.tabs;
/*
import coffee.amo.astromancy.core.handlers.CapabilityAspectiHandler;
import coffee.amo.astromancy.core.registration.ItemRegistry;
import coffee.amo.astromancy.core.systems.aspecti.Aspecti;
import coffee.amo.astromancy.core.systems.aspecti.AspectiStack;
import coffee.amo.astromancy.core.systems.aspecti.AspectiStackHandler;
import coffee.amo.astromancy.core.util.AstroKeys;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import static coffee.amo.astromancy.Astromancy.MODID;

public class PhialTab extends CreativeModeTab {
    public static final PhialTab INSTANCE = new PhialTab();

    public static PhialTab get() {
        return INSTANCE;
    }

    public PhialTab() {
        super(MODID + ".phialTab");
    }

    @Override
    public ItemStack makeIcon() {
        return ItemRegistry.ASPECTI_PHIAL.get().getDefaultInstance();
    }*/

//    @Override
//    public void fillItemList(NonNullList<ItemStack> pItems) {
//        super.fillItemList(pItems);
//        for(Aspecti aspecti : Aspecti.values())
//            if(aspecti != Aspecti.EMPTY)
//                pItems.add(addAspectiPhial(ItemRegistry.ASPECTI_PHIAL.get().getDefaultInstance(), aspecti));
//    }
//
//    private ItemStack addAspectiPhial(ItemStack stack, Aspecti aspecti) {
//        stack.getCapability(CapabilityAspectiHandler.ASPECTI_HANDLER_CAPABILITY).ifPresent(handler -> {
//            handler.fill(new AspectiStack(aspecti, 16), false);
//            if(handler instanceof AspectiStackHandler ash)
//                stack.getOrCreateTag().put(AstroKeys.KEY_ASPECTI_TAG, ash.serializeNBT());
//        });
////        stack.setTag(stack.getShareTag());
//        return stack;
//    }
//}
