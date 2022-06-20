package coffee.amo.astromancy.core.systems.aspecti;

import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

public interface IAspectiHandlerItem extends IAspectiHandler{

    @Nonnull
    ItemStack getContainer();
}
