package coffee.amo.astromancy.core.systems.aspecti;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.jetbrains.annotations.Nullable;

public class ItemAspectiContainer extends Item {
    protected final int capacity;
    public ItemAspectiContainer(Properties pProperties, int pCapacity) {
        super(pProperties);
        capacity = pCapacity;
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new AspectiHandlerItemStack(stack, capacity);
    }
}
