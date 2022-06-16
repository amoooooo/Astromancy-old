package coffee.amo.astromancy.core.packets;

import coffee.amo.astromancy.client.packets.ClientPacketUtils;
import coffee.amo.astromancy.core.systems.aspecti.Aspecti;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ArmillarySpherePacket{

    public final BlockPos pos;
    public final Map<Aspecti, Integer> aspecti;
    public final int size;

    public ArmillarySpherePacket(BlockPos pos, Map<Aspecti, Integer> aspecti) {
        this.pos = pos;
        this.aspecti = aspecti;
        this.size = aspecti.size();
    }

    public static void encode(ArmillarySpherePacket packet, FriendlyByteBuf buffer) {
        buffer.writeBlockPos(packet.pos);
        buffer.writeVarInt(packet.size);
        packet.aspecti.forEach((aspecti, amount) -> {
            buffer.writeVarInt(aspecti.ordinal());
            buffer.writeVarInt(amount);
        });
    }

    public static ArmillarySpherePacket decode(FriendlyByteBuf buffer) {
        BlockPos pos = buffer.readBlockPos();
        int size = buffer.readVarInt();
        Map<Aspecti, Integer> map = new HashMap<>();
        for (int i = 0; i < size; i++) {
            map.put(Aspecti.values()[buffer.readVarInt()], buffer.readVarInt());
        }
        return new ArmillarySpherePacket(pos, map);
    }

    public static void handle(ArmillarySpherePacket packet, Supplier<NetworkEvent.Context> contextSupplier) {
        contextSupplier.get().enqueueWork(() -> {
            ClientPacketUtils.armSphereHandle(packet);
        });
        contextSupplier.get().setPacketHandled(true);
    }
}
