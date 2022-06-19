package coffee.amo.astromancy.core.packets;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ItemSyncPacket {
    public final ItemStack stack;

    public ItemSyncPacket(ItemStack stack) {
        this.stack = stack;
    }

    public static void encode(ItemSyncPacket packet, FriendlyByteBuf buffer) {
        buffer.writeItem(packet.stack);
    }

    public static ItemSyncPacket decode(FriendlyByteBuf buffer) {
        return new ItemSyncPacket(buffer.readItem());
    }

    public static void handle(ItemSyncPacket packet, Supplier<NetworkEvent.Context> contextSupplier) {
        contextSupplier.get().enqueueWork(() -> {
            if (contextSupplier.get().getDirection().getReceptionSide().isServer()) {
                contextSupplier.get().getSender().setItemInHand(InteractionHand.MAIN_HAND, packet.stack);
            }
        });
        contextSupplier.get().setPacketHandled(true);
    }
}
