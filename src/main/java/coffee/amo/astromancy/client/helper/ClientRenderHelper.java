package coffee.amo.astromancy.client.helper;

import coffee.amo.astromancy.common.block.jar.JarBlock;
import coffee.amo.astromancy.common.blockentity.jar.JarBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.logging.Level;

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
}
