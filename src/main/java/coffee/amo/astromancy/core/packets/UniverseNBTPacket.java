package coffee.amo.astromancy.core.packets;

import coffee.amo.astromancy.Astromancy;
import coffee.amo.astromancy.client.systems.ClientUniverseHolder;
import coffee.amo.astromancy.core.systems.stars.systems.Supercluster;
import coffee.amo.astromancy.core.systems.stars.systems.Universe;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.LogicalSidedProvider;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;

import java.io.IOException;
import java.util.Optional;
import java.util.function.Supplier;

public class UniverseNBTPacket {
    public final CompoundTag nbt;
    public final int index;

    public UniverseNBTPacket(CompoundTag nbt, int index) {
        this.nbt = nbt;
        this.index = index;
    }

    public static void encode(UniverseNBTPacket packet, FriendlyByteBuf buffer) {
        buffer.writeNbt(packet.nbt);
        buffer.writeInt(packet.index);
    }

    public static UniverseNBTPacket decode(FriendlyByteBuf buffer) {
        return new UniverseNBTPacket(buffer.readNbt(), buffer.readInt());
    }

    public static void handle(UniverseNBTPacket packet, Supplier<NetworkEvent.Context> contextSupplier) {
        contextSupplier.get().enqueueWork(() -> {
            ClientUniverseHolder.addSupercluster(Supercluster.fromNbt(packet.nbt), packet.index);
            Astromancy.LOGGER.info("Parsed universe data");
        });
        contextSupplier.get().setPacketHandled(true);
    }

}
