package coffee.amo.astromancy.core.handlers;

import coffee.amo.astromancy.Astromancy;
import coffee.amo.astromancy.common.blockentity.ArmillarySphereBlockEntity;
import coffee.amo.astromancy.core.packets.ArmillarySpherePacket;
import coffee.amo.astromancy.core.packets.StarPacket;
import coffee.amo.astromancy.core.systems.aspecti.Aspecti;
import coffee.amo.astromancy.core.systems.stars.Star;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class AstromancyPacketHandler {
    private static final String PROTOCOL_VERSION = "1";
    private static int ID = 0;
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(Astromancy.MODID, "astromancy"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static void init(){
        INSTANCE.registerMessage(ID++, ArmillarySpherePacket.class, (packet, buffer) -> {
            buffer.writeBlockPos(packet.pos);
            buffer.writeVarInt(packet.size);
            packet.aspecti.forEach((aspecti, amount) -> {
                buffer.writeVarInt(aspecti.ordinal());
                buffer.writeVarInt(amount);
            });
        }, buffer -> {
            BlockPos pos = buffer.readBlockPos();
            int size = buffer.readVarInt();
            Map<Aspecti, Integer> map = new HashMap<>();
            for(int i = 0; i < size; i++){
                map.put(Aspecti.values()[buffer.readVarInt()], buffer.readVarInt());
            }
            return new ArmillarySpherePacket(pos, map);
        }, (packet, context) -> andHandling(context, () -> {
            Level level = Minecraft.getInstance().level;
            if(level.getBlockEntity(packet.pos) instanceof ArmillarySphereBlockEntity blockEntity){
                blockEntity.requirements = packet.aspecti;
            }
        }) );

        INSTANCE.registerMessage(ID++, StarPacket.class, (packet, buffer) -> {
            buffer.writeBlockPos(packet.pos);
            buffer.writeNbt(packet.tag);
        }, buffer -> {
            return new StarPacket(buffer.readBlockPos(), buffer.readNbt());
        }, (packet, context) -> andHandling(context, () -> {
            Level level = Minecraft.getInstance().level;
            if(level.getBlockEntity(packet.pos) instanceof ArmillarySphereBlockEntity blockEntity){
                blockEntity.star = Star.fromNbt(packet.tag);
            }
        }) );
    }

    private static void andHandling(final Supplier<NetworkEvent.Context> contextSupplier, final Runnable enqueuedWork) {
        contextSupplier.get().enqueueWork(enqueuedWork);
        contextSupplier.get().setPacketHandled(true);
    }
}
