package coffee.amo.astromancy.core.packets;

import coffee.amo.astromancy.client.packets.ClientPacketUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.Items;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class StarPacket {
    public final BlockPos pos;
    public final CompoundTag tag;

    public StarPacket(BlockPos pos, CompoundTag tag) {
        this.pos = pos;
        this.tag = tag;
    }

    public static void encode(StarPacket packet, FriendlyByteBuf buffer){
        buffer.writeBlockPos(packet.pos);
        buffer.writeNbt(packet.tag);
    }

    public static StarPacket decode(FriendlyByteBuf buffer){
        return new StarPacket(buffer.readBlockPos(), buffer.readNbt());
    }

    public static void handle(StarPacket packet, Supplier<NetworkEvent.Context> contextSupplier){
        contextSupplier.get().enqueueWork(() -> {
            ClientPacketUtils.starPacketHandle(packet);
        });
        contextSupplier.get().setPacketHandled(true);
    }
}
