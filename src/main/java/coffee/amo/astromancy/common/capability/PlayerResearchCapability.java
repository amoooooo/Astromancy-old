package coffee.amo.astromancy.common.capability;

import coffee.amo.astromancy.client.research.ClientResearchHolder;
import coffee.amo.astromancy.core.systems.research.*;
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

    private List<ResearchObject> RESEARCH = new ArrayList<>();

    @Override
    public List<ResearchObject> research() {
        return RESEARCH;
    }

    @Override
    public void addResearch(Player player, ResearchObject researchId) {
        RESEARCH.add(researchId);
        AstromancyPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) player), new ResearchPacket(researchId.identifier, false));
    }

    @Override
    public void removeResearch(Player player, ResearchObject researchId) {
        RESEARCH.remove(researchId);
        AstromancyPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) player), new ResearchRemovePacket(researchId.identifier));
    }

    @Override
    public boolean contains(Player player, ResearchObject researchId) {
        return RESEARCH.contains(researchId);
    }

    @Override
    public CompoundTag toNBT(CompoundTag tag) {
        ListTag research = new ListTag();
        for(ResearchObject id : RESEARCH){
            research.add(StringTag.valueOf(id.identifier));
        }
        tag.put("research", research);
        return tag;
    }

    @Override
    public void fromNBT(CompoundTag tag) {
        RESEARCH.clear();
        ListTag research = tag.getList("research", Tag.TAG_STRING);
        for(int i = 0; i < research.size(); i++){
            List<ResearchType> researchObjects = ResearchTypeRegistry.RESEARCH_TYPES.get().getValues().stream().toList();
            for (ResearchType type : researchObjects) {
                ResearchObject object = (ResearchObject) type;
                if (object.identifier.equals(research.getString(i))) {
                    ClientResearchHolder.addResearch(object);
                    RESEARCH.add(object);
                }
            }
        }
    }

    @Override
    public void setResearchState(Player player, ResearchObject researchId, ResearchProgress progress) {
        RESEARCH.stream().filter(id -> id.identifier.equals(researchId.identifier)).findFirst().ifPresent(s -> {
            s.locked = progress;
        });
        AstromancyPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) player), new ResearchPacket(researchId.identifier, true));
    }
}
