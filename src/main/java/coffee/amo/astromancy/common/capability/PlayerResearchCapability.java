package coffee.amo.astromancy.common.capability;

import coffee.amo.astromancy.core.capability.IPlayerResearch;
import coffee.amo.astromancy.core.handlers.AstromancyPacketHandler;
import coffee.amo.astromancy.core.packets.ResearchPacket;
import coffee.amo.astromancy.core.packets.ResearchRemovePacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.PacketDistributor;

import java.util.ArrayList;
import java.util.List;

public class PlayerResearchCapability implements IPlayerResearch {

    private List<String> RESEARCH = new ArrayList<>();

    @Override
    public List<String> research() {
        return RESEARCH;
    }

    @Override
    public void addResearch(Player player, String researchId) {
        RESEARCH.add(researchId);
        AstromancyPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) player), new ResearchPacket(researchId, false));
    }

    @Override
    public void removeResearch(Player player, String researchId) {
        RESEARCH.remove(researchId);
        AstromancyPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) player), new ResearchRemovePacket(researchId));
    }

    @Override
    public boolean contains(Player player, String researchId) {
        return RESEARCH.contains(researchId);
    }

    @Override
    public CompoundTag toNBT(CompoundTag tag) {
        ListTag research = new ListTag();
        for(String id : RESEARCH){
            research.add(StringTag.valueOf(id));
        }
        tag.put("research", research);
        return tag;
    }

    @Override
    public void fromNBT(CompoundTag tag) {
        RESEARCH.clear();
        ListTag research = tag.getList("research", Tag.TAG_STRING);
        for(int i = 0; i < research.size(); i++){
            RESEARCH.add(research.getString(i));
        }
    }
}
