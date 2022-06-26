package coffee.amo.astromancy.core.packets;
/* i don't think you need it
import coffee.amo.astromancy.client.packets.ClientPacketUtils;
import coffee.amo.astromancy.common.blockentity.jar.JarBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class JarSyncPacket {
    public final int dir;
    public final BlockPos pos;

    public JarSyncPacket(int dir, BlockPos pos) {
        this.dir = dir;
        this.pos = pos;
    }

    public static void encode(JarSyncPacket packet, FriendlyByteBuf buffer) {
        buffer.writeVarInt(packet.dir);
        buffer.writeBlockPos(packet.pos);
    }

    public static JarSyncPacket decode(FriendlyByteBuf buffer) {
        int dir = buffer.readVarInt();
        BlockPos pos = buffer.readBlockPos();
        return new JarSyncPacket(dir, pos);
    }

    public static void handle(JarSyncPacket packet, Supplier<NetworkEvent.Context> contextSupplier) {
        contextSupplier.get().enqueueWork(() -> {
            if(contextSupplier.get().getSender().level.getBlockEntity(packet.pos) instanceof JarBlockEntity je){
                je.labelDirection = Direction.values()[packet.dir];
            }
        });
        contextSupplier.get().setPacketHandled(true);
    }
}*/
