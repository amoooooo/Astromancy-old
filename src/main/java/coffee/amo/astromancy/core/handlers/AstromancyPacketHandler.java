package coffee.amo.astromancy.core.handlers;

import coffee.amo.astromancy.Astromancy;
import coffee.amo.astromancy.common.blockentity.ArmillarySphereBlockEntity;
import coffee.amo.astromancy.core.packets.ArmillarySpherePacket;
import coffee.amo.astromancy.core.packets.SolarEclipsePacket;
import coffee.amo.astromancy.core.packets.StarDataPacket;
import coffee.amo.astromancy.core.packets.StarPacket;
import coffee.amo.astromancy.core.systems.aspecti.Aspecti;
import coffee.amo.astromancy.core.systems.stars.Star;
import coffee.amo.astromancy.core.systems.stars.classification.Constellation;
import coffee.amo.astromancy.core.systems.stars.classification.Constellations;
import coffee.amo.astromancy.core.systems.stars.classification.Quadrant;
import coffee.amo.astromancy.core.systems.stars.classification.Quadrants;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.LogicalSidedProvider;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.*;
import java.util.function.Supplier;

public class AstromancyPacketHandler {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(Astromancy.MODID, "astromancy"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );
    private static int ID = 0;

    public static void init() {
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
            for (int i = 0; i < size; i++) {
                map.put(Aspecti.values()[buffer.readVarInt()], buffer.readVarInt());
            }
            return new ArmillarySpherePacket(pos, map);
        }, (packet, context) -> andHandling(context, () -> {
            Level level = Minecraft.getInstance().level;
            if (level.getBlockEntity(packet.pos) instanceof ArmillarySphereBlockEntity blockEntity) {
                blockEntity.requirements = packet.aspecti;
            }
        }));

        INSTANCE.registerMessage(ID++, StarPacket.class, (packet, buffer) -> {
            buffer.writeBlockPos(packet.pos);
            buffer.writeNbt(packet.tag);
        }, buffer -> {
            return new StarPacket(buffer.readBlockPos(), buffer.readNbt());
        }, (packet, context) -> andHandling(context, () -> {
            Level level = Minecraft.getInstance().level;
            if (level.getBlockEntity(packet.pos) instanceof ArmillarySphereBlockEntity blockEntity) {
                blockEntity.star = Star.fromNbt(packet.tag);
            }
        }));

        INSTANCE.registerMessage(ID++, SolarEclipsePacket.class, (packet, buffer) -> {
            buffer.writeBoolean(packet.bool);
        }, buffer -> {
            return new SolarEclipsePacket(buffer.readBoolean());
        }, (packet, context) -> andHandling(context, () -> {
            NetworkEvent.Context ctx = context.get();
            LogicalSide sideReceived = ctx.getDirection().getReceptionSide();
            if (sideReceived != LogicalSide.CLIENT) {
                return;
            }
            if (!packet.initialized) {
                return;
            }
            Optional<Level> clientWorld = LogicalSidedProvider.CLIENTWORLD.get(sideReceived);
            if (clientWorld.isEmpty()) {
                return;
            }
            SolarEclipseHandler.solarEclipseEnabledClient = packet.bool;
        }));

        INSTANCE.registerMessage(ID++, StarDataPacket.class, (packet, buffer) -> {
            for(Constellation cons : packet.constellations){
                buffer.writeNbt(cons.toNbt());
            }
        }, buffer -> {
            List<Constellation> constellations = new ArrayList<>();
            for(int i = 0; i < 21; i++){
                constellations.add(Constellation.fromNbt(buffer.readNbt()));
            }
            return new StarDataPacket(constellations);
        }, (packet, context) -> andHandling(context, () -> {
            NetworkEvent.Context ctx = context.get();
            LogicalSide sideReceived = ctx.getDirection().getReceptionSide();
            if (sideReceived != LogicalSide.CLIENT) {
                return;
            }
            Optional<Level> clientWorld = LogicalSidedProvider.CLIENTWORLD.get(sideReceived);
            if (clientWorld.isEmpty()) {
                return;
            }
            for(Constellation cons : packet.constellations){
                Constellations.setConstellation(cons);
            }
            System.out.println("Parsed star data");
        }));
    }

    private static void andHandling(final Supplier<NetworkEvent.Context> contextSupplier, final Runnable enqueuedWork) {
        contextSupplier.get().enqueueWork(enqueuedWork);
        contextSupplier.get().setPacketHandled(true);
    }
}
