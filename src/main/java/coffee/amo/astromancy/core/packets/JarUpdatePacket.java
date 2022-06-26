package coffee.amo.astromancy.core.packets;
/*
import coffee.amo.astromancy.client.packets.ClientPacketUtils;
import coffee.amo.astromancy.core.systems.aspecti.AspectiStackHandler;
import coffee.amo.astromancy.core.systems.aspecti.IAspectiHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class JarUpdatePacket {
    public final BlockPos pos;
    public final AspectiStackHandler tank;
    public final boolean label;
    public final int labelDirection;

    public JarUpdatePacket(BlockPos pos, AspectiStackHandler tank, boolean label, int labelDirection) {
        this.pos = pos;
        this.tank = tank;
        this.label = label;
        this.labelDirection = labelDirection;
    }

    public static void encode(JarUpdatePacket packet, FriendlyByteBuf buffer) {
        buffer.writeBlockPos(packet.pos);
        buffer.writeNbt(packet.tank.serializeNBT());
        buffer.writeBoolean(packet.label);
        buffer.writeVarInt(packet.labelDirection);
    }

    public static JarUpdatePacket decode(FriendlyByteBuf buffer) {
        BlockPos pos = buffer.readBlockPos();
        AspectiStackHandler tank = ;
        boolean label = buffer.readBoolean();
        int labelDirection = buffer.readVarInt();
        return new JarUpdatePacket(pos, tank, label, labelDirection);
    }

    public static void handle(JarUpdatePacket packet, Supplier<NetworkEvent.Context> contextSupplier) {
        contextSupplier.get().enqueueWork(() -> {
            ClientPacketUtils.jarUpdateHandle(packet);
        });
        contextSupplier.get().setPacketHandled(true);
    }
}*/
