package coffee.amo.astromancy.client.packets;

import coffee.amo.astromancy.common.blockentity.armillary_sphere.ArmillarySphereCoreBlockEntity;
import coffee.amo.astromancy.core.packets.ArmillarySpherePacket;
import coffee.amo.astromancy.core.packets.StarPacket;
import coffee.amo.astromancy.core.systems.stars.Star;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.Level;

public class ClientPacketUtils {
    public static void armSphereHandle(ArmillarySpherePacket packet){
        Level level = Minecraft.getInstance().level;
        if (level.getBlockEntity(packet.pos) instanceof ArmillarySphereCoreBlockEntity blockEntity) {
            blockEntity.requirements = packet.aspecti;
        }
    }

    public static void starPacketHandle(StarPacket packet) {
        Level level = Minecraft.getInstance().level;
        if (level.getBlockEntity(packet.pos) instanceof ArmillarySphereCoreBlockEntity blockEntity) {
            blockEntity.star = Star.fromNbt(packet.tag);
        }
    }

//    public static void jarUpdateHandle(JarUpdatePacket packet) {
//        Level level = Minecraft.getInstance().level;
//        if (level.getBlockEntity(packet.pos) instanceof JarBlockEntity blockEntity) {
//            blockEntity.setAspecti(Aspecti.values()[packet.aspecti]);
//            blockEntity.setCount(packet.count);
//            blockEntity.label = packet.label;
//            blockEntity.labelDirection = Direction.values()[packet.labelDirection];
//        }
//    }
}
