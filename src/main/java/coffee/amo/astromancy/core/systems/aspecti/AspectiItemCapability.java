package coffee.amo.astromancy.core.systems.aspecti;

import coffee.amo.astromancy.core.handlers.CapabilityAspectiHandler;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AspectiItemCapability implements ICapabilityProvider {
    private final LazyOptional<IAspectiHandlerItem> opt;

    public AspectiItemCapability(ItemStack stack, int capacity) {
        opt = LazyOptional.of(() -> new AspectiHandlerItemStack(stack, capacity));
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return CapabilityAspectiHandler.ASPECTI_HANDLER_ITEM_CAPABILITY.orEmpty(cap, opt);
    }
}
