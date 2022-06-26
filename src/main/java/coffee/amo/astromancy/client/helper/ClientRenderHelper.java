package coffee.amo.astromancy.client.helper;

import coffee.amo.astromancy.common.block.jar.JarBlock;
import coffee.amo.astromancy.common.blockentity.jar.JarBlockEntity;
import coffee.amo.astromancy.core.handlers.AstromancyPacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.network.PacketDistributor;

public class ClientRenderHelper {
    public static int tickStuff(ClientLevel level, JarBlockEntity jarBlockEntity, int clientLookAtTicks) {
        if(Minecraft.getInstance().hitResult instanceof BlockHitResult && !Minecraft.getInstance().hitResult.getType().equals(HitResult.Type.MISS)){
            if(level.getBlockState(((BlockHitResult) Minecraft.getInstance().hitResult).getBlockPos()).getBlock() instanceof JarBlock && ((BlockHitResult) Minecraft.getInstance().hitResult).getBlockPos().equals(jarBlockEntity.getBlockPos())){
                if(clientLookAtTicks < 10){
                    clientLookAtTicks++;
                }
            }  else {
                clientLookAtTicks = Math.max(0, clientLookAtTicks - 2);
            }
        } else {
            clientLookAtTicks = Math.max(0, clientLookAtTicks - 2);
        }
        return clientLookAtTicks;
    }

//    public static void jarOrientation(){
//        if(Minecraft.getInstance().hitResult instanceof BlockHitResult && !Minecraft.getInstance().hitResult.getType().equals(HitResult.Type.MISS)){
//            if(Minecraft.getInstance().level.getBlockEntity(((BlockHitResult) Minecraft.getInstance().hitResult).getBlockPos()) instanceof JarBlockEntity je){
//                je.labelDirection = ((BlockHitResult) Minecraft.getInstance().hitResult).getDirection();
//                AstromancyPacketHandler.INSTANCE.send(PacketDistributor.SERVER.noArg(), new JarSyncPacket(je.labelDirection.ordinal(), je.getBlockPos()));
//            }
//        }
//    }
}
