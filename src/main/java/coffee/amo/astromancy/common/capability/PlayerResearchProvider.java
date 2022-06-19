package coffee.amo.astromancy.common.capability;

import coffee.amo.astromancy.core.capability.IPlayerResearch;
import coffee.amo.astromancy.core.handlers.PlayerResearchHandler;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerResearchProvider implements ICapabilitySerializable<CompoundTag> {

    private final IPlayerResearch CAP;

    public PlayerResearchProvider() {
        CAP = new PlayerResearchCapability();
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return cap == PlayerResearchHandler.RESEARCH_CAPABILITY ? LazyOptional.of(() -> CAP).cast() : LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        return CAP.toNBT(new CompoundTag());
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        CAP.fromNBT(nbt);
    }
}
