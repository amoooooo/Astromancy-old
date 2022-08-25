package coffee.amo.astromancy.client.helper;

import coffee.amo.astromancy.Astromancy;
import coffee.amo.astromancy.common.block.jar.JarBlock;
import coffee.amo.astromancy.common.blockentity.jar.JarBlockEntity;
import coffee.amo.astromancy.core.registration.ItemRegistry;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class ClientRenderHelper {
    public static boolean isSolarEclipse = false;
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

    public static void setIsSolarEclipse(boolean val) {
        isSolarEclipse = val;
    }
    
    public static void renderWaypointStars(PoseStack poseStack, Matrix4f pProjectionMatrix, float pPartialTick, Camera pCamera, boolean p_202428_, Runnable pSkyFogSetup, CallbackInfo ci, BufferBuilder bufferbuilder){
        poseStack.pushPose();
        Matrix4f matrix4f = poseStack.last().pose();
        RenderSystem.setShader(GameRenderer::getPositionColorTexShader);
        RenderSystem.setShaderTexture(0, Astromancy.astromancy("textures/vfx/white.png"));
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR_TEX);
        bufferbuilder.vertex(matrix4f, 15f, 15f, 15f).color(255,255,255,255).uv(0,0).endVertex();
        bufferbuilder.vertex(matrix4f, 15f, 15f, -15f).color(255,255,255,255).uv(1,0).endVertex();
        bufferbuilder.vertex(matrix4f, -15f, 15f, -15f).color(255,255,255,255).uv(1,1).endVertex();
        bufferbuilder.vertex(matrix4f, -15f, 15f, 15f).color(255,255,255,255).uv(0,1).endVertex();
        BufferUploader.drawWithShader(bufferbuilder.end());
        poseStack.popPose();
    }

    public static void renderOverlay(float screenWidth, float screenHeight, BufferBuilder bufferbuilder, Tesselator tesselator, PoseStack ps){
        if(Minecraft.getInstance().player.getUseItem().is(ItemRegistry.SKYWATCHERS_LOOKING_GLASS.get())){
            LocalPlayer player = Minecraft.getInstance().player;
            RenderSystem.disableDepthTest();
            RenderSystem.depthMask(false);
            RenderSystem.defaultBlendFunc();
            RenderSystem.setShader(GameRenderer::getPositionColorShader);
            float centerX = screenWidth / 2f;
            float centerY = screenHeight / 2f;
            float scale = 16;
//            bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
//            bufferbuilder.vertex(centerX - scale, centerY - scale, -90.0D).color(255,255,255,255).endVertex();
//            bufferbuilder.vertex(centerX - scale, centerY + scale, -90.0D).color(255,255,255,255).endVertex();
//            bufferbuilder.vertex(centerX + scale, centerY + scale, -90.0D).color(255,255,255,255).endVertex();
//            bufferbuilder.vertex(centerX + scale, centerY - scale, -90.0D).color(255,255,255,255).endVertex();
//            BufferUploader.drawWithShader(bufferbuilder.end());
            RenderSystem.enableDepthTest();
            RenderSystem.depthMask(true);
            if(Minecraft.getInstance().hitResult != null && Minecraft.getInstance().hitResult.getType().equals(HitResult.Type.ENTITY)){
                EntityHitResult entityHitResult = (EntityHitResult) Minecraft.getInstance().hitResult;
                MultiBufferSource multiBufferSource = Minecraft.getInstance().renderBuffers().bufferSource();
                LivingEntity livingEntity = (LivingEntity) entityHitResult.getEntity();
                InventoryScreen.renderEntityInInventory((int) centerX, (int) centerY, 1, 0,0, livingEntity);
            }
        }
    }
}
