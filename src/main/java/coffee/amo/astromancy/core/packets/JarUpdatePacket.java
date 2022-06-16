package coffee.amo.astromancy.core.packets;

import coffee.amo.astromancy.client.packets.ClientPacketUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class JarUpdatePacket {
    public final BlockPos pos;
    public final int count;
    public final int aspecti;

    public JarUpdatePacket(BlockPos pos, int count, int aspecti) {
        this.pos = pos;
        this.count = count;
        this.aspecti = aspecti;
    }

    public static void encode(JarUpdatePacket packet, FriendlyByteBuf buffer) {
        buffer.writeBlockPos(packet.pos);
        buffer.writeVarInt(packet.count);
        buffer.writeVarInt(packet.aspecti);
    }

    public static JarUpdatePacket decode(FriendlyByteBuf buffer) {
        BlockPos pos = buffer.readBlockPos();
        int count = buffer.readVarInt();
        int aspecti = buffer.readVarInt();
        return new JarUpdatePacket(pos, count, aspecti);
    }

    public static void handle(JarUpdatePacket packet, Supplier<NetworkEvent.Context> contextSupplier) {
        contextSupplier.get().enqueueWork(() -> {
            ClientPacketUtils.jarUpdateHandle(packet);
        });
        contextSupplier.get().setPacketHandled(true);
    }
}
