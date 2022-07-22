package coffee.amo.astromancy.core.systems.research;

import coffee.amo.astromancy.core.handlers.AstromancyPacketHandler;
import coffee.amo.astromancy.core.packets.ClientboundResearchPacket;
import coffee.amo.astromancy.core.packets.ClientboundResearchRemovePacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.PacketDistributor;

public class ResearchHelper {
    public static void addResearch(String in, Player player){
        AstromancyPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) player), new ClientboundResearchPacket(in, false, false, ResearchProgress.IN_PROGRESS.ordinal()));
    }

    public static void removeResearch(String in, Player player){
        AstromancyPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) player), new ClientboundResearchRemovePacket(in));
    }
}
