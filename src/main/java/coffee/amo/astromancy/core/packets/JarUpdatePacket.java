package coffee.amo.astromancy.core.packets;
/*
import coffee.amo.astromancy.client.packets.ClientPacketUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class JarUpdatePacket {
    public final BlockPos pos;
    public final int count;
    public final int glyph;
    public final boolean label;
    public final int labelDirection;

    public JarUpdatePacket(BlockPos pos, int count, int glyph, boolean label, int labelDirection) {
        this.pos = pos;
        this.count = count;
        this.glyph = glyph;
        this.label = label;
        this.labelDirection = labelDirection;
    }

    public static void encode(JarUpdatePacket packet, FriendlyByteBuf buffer) {
        buffer.writeBlockPos(packet.pos);
        buffer.writeVarInt(packet.count);
        buffer.writeVarInt(packet.glyph);
        buffer.writeBoolean(packet.label);
        buffer.writeVarInt(packet.labelDirection);
    }

    public static JarUpdatePacket decode(FriendlyByteBuf buffer) {
        BlockPos pos = buffer.readBlockPos();
        int count = buffer.readVarInt();
        int glyph = buffer.readVarInt();
        boolean label = buffer.readBoolean();
        int labelDirection = buffer.readVarInt();
        return new JarUpdatePacket(pos, count, glyph, label, labelDirection);
    }

    public static void handle(JarUpdatePacket packet, Supplier<NetworkEvent.Context> contextSupplier) {
        contextSupplier.get().enqueueWork(() -> {
            ClientPacketUtils.jarUpdateHandle(packet);
        });
        contextSupplier.get().setPacketHandled(true);
    }
}*/
