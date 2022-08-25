package coffee.amo.astromancy.mixin.client;

import coffee.amo.astromancy.Astromancy;
import coffee.amo.astromancy.client.helper.ClientRenderHelper;
import coffee.amo.astromancy.core.registration.ItemRegistry;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(Gui.class)
public class GuiMixin {

    @Shadow protected int screenWidth;

    @Shadow protected int screenHeight;

    private PoseStack poseStack;

    @Inject(method = "renderSpyglassOverlay", at = @At("TAIL"), locals = LocalCapture.CAPTURE_FAILHARD)
    public void lookingGlassOverlay(float p_168676_, CallbackInfo ci, Tesselator tesselator, BufferBuilder bufferbuilder, float f, float f1, float f2, float f3, float f4, float f5, float f6, float f7) {
        ClientRenderHelper.renderOverlay(this.screenWidth, this.screenHeight, bufferbuilder, tesselator, poseStack);
    }

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;renderSpyglassOverlay(F)V"), locals = LocalCapture.CAPTURE_FAILHARD)
    public void captureSpyglassPoseStack(PoseStack pPoseStack, float pPartialTick, CallbackInfo ci, Font font, float f){
        poseStack = pPoseStack;
    }
}
