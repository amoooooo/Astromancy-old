package coffee.amo.astromancy.core.packets;

import coffee.amo.astromancy.client.packets.ClientPacketUtils;
import coffee.amo.astromancy.core.systems.glyph.Glyph;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ArmillarySpherePacket{

    public final BlockPos pos;
    public final Map<Glyph, Integer> glyph;
    public final int size;

    public ArmillarySpherePacket(BlockPos pos, Map<Glyph, Integer> glyph) {
        this.pos = pos;
        this.glyph = glyph;
        this.size = glyph.size();
    }

    public static void encode(ArmillarySpherePacket packet, FriendlyByteBuf buffer) {
        buffer.writeBlockPos(packet.pos);
        buffer.writeVarInt(packet.size);
        packet.glyph.forEach((glyph, amount) -> {
            buffer.writeVarInt(glyph.ordinal());
            buffer.writeVarInt(amount);
        });
    }

    public static ArmillarySpherePacket decode(FriendlyByteBuf buffer) {
        BlockPos pos = buffer.readBlockPos();
        int size = buffer.readVarInt();
        Map<Glyph, Integer> map = new HashMap<>();
        for (int i = 0; i < size; i++) {
            map.put(Glyph.values()[buffer.readVarInt()], buffer.readVarInt());
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
