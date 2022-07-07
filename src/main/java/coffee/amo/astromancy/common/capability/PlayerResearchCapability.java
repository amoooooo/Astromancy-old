package coffee.amo.astromancy.common.capability;

import coffee.amo.astromancy.Astromancy;
import coffee.amo.astromancy.core.events.PlayerResearchCompleteEvent;
import coffee.amo.astromancy.core.handlers.PlayerResearchHandler;
import coffee.amo.astromancy.core.registration.ResearchRegistry;
import coffee.amo.astromancy.core.systems.research.IPlayerResearch;
import coffee.amo.astromancy.core.handlers.AstromancyPacketHandler;
import coffee.amo.astromancy.core.packets.ResearchPacket;
import coffee.amo.astromancy.core.packets.ResearchRemovePacket;
import coffee.amo.astromancy.core.systems.research.ResearchObject;
import coffee.amo.astromancy.core.systems.research.ResearchProgress;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.network.PacketDistributor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PlayerResearchCapability implements IPlayerResearch {

    private List<ResearchObject> RESEARCH = new ArrayList<>();

    @Override
    public List<ResearchObject> research() {
        return RESEARCH;
    }

    @Override
    public void addResearch(Player player, ResearchObject researchId) {
        if(!RESEARCH.contains(researchId)) {
            researchId.locked = ResearchProgress.IN_PROGRESS;
            researchId.children.forEach(c -> {
                c.locked = ResearchProgress.UNAVAILABLE;
            });
            RESEARCH.add(researchId);
            AstromancyPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) player), new ResearchPacket(researchId.getIdentifier(), false, false, ResearchProgress.IN_PROGRESS.ordinal()));
        }
    }

    @Override
    public void addLockedResearch(Player player, ResearchObject researchId) {
        if(!RESEARCH.contains(researchId)) {
            researchId.locked = ResearchProgress.LOCKED;
            RESEARCH.add(researchId);
            researchId.children.forEach(c -> {
                c.locked = ResearchProgress.UNAVAILABLE;
            });
            AstromancyPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) player), new ResearchPacket(researchId.getIdentifier(), true, false, ResearchProgress.LOCKED.ordinal()));
        }
    }

    @Override
    public void completeResearch(Player player, ResearchObject researchId) {
        if(RESEARCH.contains(researchId)) {
            RESEARCH.stream().filter(s -> s.equals(researchId)).findFirst().ifPresent(r -> {
                r.locked = ResearchProgress.COMPLETED;
                r.children.forEach(c -> {
                    c.locked = ResearchProgress.LOCKED;
                });
                AstromancyPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) player), new ResearchPacket(r.getIdentifier(), true, true, ResearchProgress.COMPLETED.ordinal()));
            });
        } else {
            researchId.locked = ResearchProgress.COMPLETED;
            researchId.children.forEach(c -> {
                c.locked = ResearchProgress.LOCKED;
            });
            RESEARCH.add(researchId);
            AstromancyPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) player), new ResearchPacket(researchId.getIdentifier(), true, true, ResearchProgress.COMPLETED.ordinal()));
        }
        MinecraftForge.EVENT_BUS.post(new PlayerResearchCompleteEvent(player, researchId));
        onResearchCompleted(player, researchId);
    }

    @Override
    public void removeResearch(Player player, ResearchObject researchId) {
        if(RESEARCH.contains(researchId)) {
            RESEARCH.remove(researchId);
            AstromancyPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) player), new ResearchRemovePacket(researchId.getIdentifier()));
        }
    }

    @Override
    public boolean contains(Player player, ResearchObject researchId) {
        return RESEARCH.contains(researchId);
    }

    @Override
    public boolean contains(Player player, String researchId) {
        for(ResearchObject research : RESEARCH) {
            if(research.getIdentifier().equals(researchId)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public CompoundTag toNBT(CompoundTag tag) {
        ListTag research = new ListTag();
        for(ResearchObject id : RESEARCH){
            research.add(id.toNBT(new CompoundTag()));
        }
        tag.put("research", research);
        return tag;
    }

    @Override
    public void fromNBT(CompoundTag tag) {
        RESEARCH.clear();
        ListTag research = tag.getList("research", Tag.TAG_COMPOUND);
        research.forEach(rTag -> {
            RESEARCH.add(ResearchObject.fromNBT((CompoundTag) rTag));
            Astromancy.LOGGER.debug("Loaded research: " + ((CompoundTag) rTag).getString("id"));
        });
    }

    public void onResearchCompleted(Player player, ResearchObject research){
        if(Objects.equals(research.identifier, "crucible")){
            player.getCapability(PlayerResearchHandler.RESEARCH_CAPABILITY).ifPresent(r -> {
                r.addResearch(player, ResearchRegistry.GLYPH_PHIAL.get());
            });
        }
    }
}
