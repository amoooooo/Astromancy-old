package coffee.amo.astromancy.mixin.client;

import coffee.amo.astromancy.Astromancy;
import coffee.amo.astromancy.client.systems.ClientConstellationHolder;
import coffee.amo.astromancy.core.systems.stars.classification.ConstellationInstance;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FogType;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import javax.annotation.Nullable;

@Mixin(LevelRenderer.class)
public class LevelRendererMixin {


    @Shadow
    @Nullable
    private ClientLevel level;

    @Inject(at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;mulPose(Lcom/mojang/math/Quaternion;)V", ordinal = 4, shift = At.Shift.AFTER), method = "renderSky", locals = LocalCapture.CAPTURE_FAILHARD)
    public void renderConstellations(PoseStack p_202424_, Matrix4f p_202425_, float p_202426_, Camera p_202427_, boolean p_202428_, Runnable p_202429_, CallbackInfo ci, FogType fogtype, Vec3 vec3, float f, float f1, float f2, BufferBuilder bufferbuilder, ShaderInstance shaderinstance, float[] afloat, float f11) {
        if (!level.dimension().equals(Level.OVERWORLD)) return;
        float starBrightness = this.level.getStarBrightness(p_202426_) * f11;
        if (starBrightness > 0.0F) {
            p_202424_.pushPose();
            for (ConstellationInstance constellationInstance : ClientConstellationHolder.getConstellationInstances()) {
                if(Math.round((level.getGameTime() / 24000f)+1) % constellationInstance.getDaysVisible() == 0){
                    float dayScale = this.level.getTimeOfDay(p_202426_) * 0.8f;
                    float rotFactorZ = (dayScale * constellationInstance.getOffset())+25f %360;
                    float rotFactorX = (dayScale * -constellationInstance.getOffset())+174 %360;
                    float rotFactorY = (dayScale * constellationInstance.getOffset())+248 %360;
                    p_202424_.mulPose(Vector3f.ZN.rotationDegrees(rotFactorZ));
                    p_202424_.mulPose(Vector3f.XP.rotationDegrees(rotFactorX));
                    p_202424_.mulPose(Vector3f.YN.rotationDegrees(rotFactorY));
                    float rotMult = Math.min(1, (rotFactorX / 360f) * (rotFactorY / 360f) * (rotFactorZ / 360f));
                    RenderSystem.setShaderColor(starBrightness * rotMult, starBrightness * rotMult, starBrightness* rotMult, 1.0F);
                    Matrix4f matrix4f = p_202424_.last().pose();
                    float k = 20f;
                    float offset = 100;
                    RenderSystem.setShader(GameRenderer::getPositionTexShader);
                    RenderSystem.setShaderTexture(0, constellationInstance.getConstellation().getIcon());
                    bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
                    bufferbuilder.vertex(matrix4f, -k, offset, -k).uv(0.0F, 0.0F).endVertex();
                    bufferbuilder.vertex(matrix4f, k, offset, -k).uv(1.0F, 0.0F).endVertex();
                    bufferbuilder.vertex(matrix4f, k, offset, k).uv(1.0F, 1.0F).endVertex();
                    bufferbuilder.vertex(matrix4f, -k, offset, k).uv(0.0F, 1.0F).endVertex();
                    BufferUploader.drawWithShader(bufferbuilder.end());
                    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                }
            }
            p_202424_.popPose();
        }
    }
}
