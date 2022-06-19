package coffee.amo.astromancy.core.packets;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class BookStatePacket {
    public final int I;
    public BookStatePacket(int i){
        I = i;
    }

    public static void encode(BookStatePacket packet, FriendlyByteBuf buffer){
        buffer.writeInt(packet.I);
    }

    public static BookStatePacket decode(FriendlyByteBuf buffer){
        int i = buffer.readInt();
        return new BookStatePacket(i);
    }

    public static void handle(BookStatePacket packet, Supplier<NetworkEvent.Context> contextSupplier){
        contextSupplier.get().enqueueWork(() -> {
            if(contextSupplier.get().getDirection().getReceptionSide().isServer()){
                contextSupplier.get().getSender().getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTag().putInt("openness", packet.I);
            }
        });
        contextSupplier.get().setPacketHandled(true);
    }
}
