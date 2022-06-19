package coffee.amo.astromancy.core.capability;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

import java.util.List;

public interface IPlayerResearch {

    List<String> research();

    void addResearch(Player player, String researchId);

    void removeResearch(Player player, String researchId);

    boolean contains(Player player, String researchId);

    CompoundTag toNBT(CompoundTag tag);

    void fromNBT(CompoundTag tag);
}
