package coffee.amo.astromancy.core.packets;

import coffee.amo.astromancy.client.systems.ClientConstellationHolder;
import coffee.amo.astromancy.core.systems.stars.Star;
import coffee.amo.astromancy.core.systems.stars.classification.ConstellationInstance;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.LogicalSidedProvider;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class StarDataPacket {
    public final List<ConstellationInstance> constellationInstances;

    public StarDataPacket(List<ConstellationInstance> constellationInstances) {
        this.constellationInstances = constellationInstances;
    }

    public static void encode(StarDataPacket packet, FriendlyByteBuf buffer){
        buffer.writeInt(packet.constellationInstances.size());
        for(ConstellationInstance q : packet.constellationInstances){
            buffer.writeNbt(q.toNbt());
        }
    }

    public static StarDataPacket decode(FriendlyByteBuf buffer) {
        List<ConstellationInstance> q = new ArrayList<>();
        int size = buffer.readInt();
        for(int i = 0; i < size; i++){
            q.add(ConstellationInstance.fromNbt(buffer.readNbt()));
        }
        return new StarDataPacket(q);
    }

    public static void handle(StarDataPacket packet, Supplier<NetworkEvent.Context> contextSupplier) {
        contextSupplier.get().enqueueWork(() -> {
            NetworkEvent.Context ctx = contextSupplier.get();
            LogicalSide sideReceived = ctx.getDirection().getReceptionSide();
            if (sideReceived != LogicalSide.CLIENT) {
                return;
            }
            Optional<Level> clientWorld = LogicalSidedProvider.CLIENTWORLD.get(sideReceived);
            if (clientWorld.isEmpty()) {
                return;
            }
            ClientConstellationHolder.constellationInstances = packet.constellationInstances;
            System.out.println("Parsed star data");
        });
        contextSupplier.get().setPacketHandled(true);
    }
}
