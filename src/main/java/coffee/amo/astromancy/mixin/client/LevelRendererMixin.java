package coffee.amo.astromancy.mixin.client;

import coffee.amo.astromancy.Astromancy;
import coffee.amo.astromancy.client.systems.ClientConstellationHolder;
import coffee.amo.astromancy.core.systems.stars.classification.ConstellationInstance;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.datafixers.util.Pair;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import com.mojang.math.Vector4f;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FogType;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Final;
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

    @Shadow
    @Final
    private Minecraft minecraft;

    @Inject(at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;mulPose(Lcom/mojang/math/Quaternion;)V", ordinal = 4, shift = At.Shift.AFTER), method = "renderSky", locals = LocalCapture.CAPTURE_FAILHARD)
    public void renderConstellations(PoseStack p_202424_, Matrix4f p_202425_, float p_202426_, Camera p_202427_, boolean p_202428_, Runnable p_202429_, CallbackInfo ci, FogType fogtype, Vec3 vec3, float f, float f1, float f2, BufferBuilder bufferbuilder, ShaderInstance shaderinstance, float[] afloat, float f11) {
        if (!level.dimension().equals(Level.OVERWORLD)) return;
        float starBrightness = this.level.getStarBrightness(p_202426_) * f11;
        if (starBrightness > 0.0F) {
            p_202424_.pushPose();
            for (ConstellationInstance constellationInstance : ClientConstellationHolder.getConstellationInstances()) {
                if (Math.round((level.getGameTime() / 24000f) + 1) % constellationInstance.getDaysVisible() > 0) {
                    float dayScale = this.level.getTimeOfDay(p_202426_) * 0.8f;
                    float rotFactorZ = (dayScale * constellationInstance.getOffset()) + 25f % 360;
                    float rotFactorX = (dayScale * -constellationInstance.getOffset()) + 174 % 360;
                    float rotFactorY = (dayScale * constellationInstance.getOffset()) + 248 % 360;
                    p_202424_.mulPose(Vector3f.ZN.rotationDegrees(rotFactorZ));
                    p_202424_.mulPose(Vector3f.XP.rotationDegrees(rotFactorX));
                    p_202424_.mulPose(Vector3f.YN.rotationDegrees(rotFactorY));
                    float offset = 20f;
                    RenderSystem.setShaderColor(starBrightness, starBrightness, starBrightness, 1.0F);
                    Matrix4f matrix4f = p_202424_.last().pose();
                    for (int i = 0; i < constellationInstance.getConstellation().getPoints().length; i++) {
                        float k = 0.4f;
                        Pair<Float, Float> pos = constellationInstance.getConstellation().getPoints()[i];
                        RenderSystem.setShader(GameRenderer::getPositionTexShader);
                        if(i % 3 == 0)
                            RenderSystem.setShaderTexture(0, Astromancy.astromancy("textures/environment/constellations/star_big.png"));
                        else if(i % 3 == 1)
                            RenderSystem.setShaderTexture(0, Astromancy.astromancy("textures/environment/constellations/star_med.png"));
                        else
                            RenderSystem.setShaderTexture(0, Astromancy.astromancy("textures/environment/constellations/star_small.png"));
                        k *= Math.max(Math.abs(Math.cos(minecraft.level.getGameTime() / 12000f)), 0.85f);
                        float alphaMod = Math.max(0.1f,(float)Math.sin((minecraft.level.getGameTime() / (float)(i+1%2))/30) + 0.2f);
                        float flickerBrightness = (starBrightness * Math.max(1-minecraft.getDeltaFrameTime(), 0.9F));
                        RenderSystem.setShaderColor(starBrightness * alphaMod, starBrightness *  alphaMod, starBrightness *  alphaMod, 1.0F);
                        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
                        bufferbuilder.vertex(matrix4f, pos.getFirst() - k, offset, pos.getSecond() - k).uv(0.0f, 0.0f).endVertex();
                        bufferbuilder.vertex(matrix4f, pos.getFirst() + k, offset, pos.getSecond() - k).uv(1.0f, 0.0f).endVertex();
                        bufferbuilder.vertex(matrix4f, pos.getFirst() + k, offset, pos.getSecond() + k).uv(1.0f, 1.0f).endVertex();
                        bufferbuilder.vertex(matrix4f, pos.getFirst() - k, offset, pos.getSecond() + k).uv(0.0f, 1.0f).endVertex();
                        BufferUploader.drawWithShader(bufferbuilder.end());
                        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                        drawLine(matrix4f, bufferbuilder, pos, constellationInstance.getConstellation().getPoints()[i % constellationInstance.getConstellation().getPoints().length], k);
                    }
                }
            }
            p_202424_.popPose();
        }
    }

    private void drawLine(Matrix4f matrix4f, BufferBuilder bufferbuilder, Pair<Float, Float> star1, Pair<Float, Float> star2, float size) {
        size *= 4f;
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, Astromancy.astromancy("textures/environment/constellations/line.png"));
        RenderSystem.setShaderColor(0.7f, 0.7f, 0.7f, 1.0F);
        RenderSystem.disableCull();
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        bufferbuilder.vertex(matrix4f, star2.getFirst() + size, 100f, star2.getSecond() - size).uv(0.0F, 1.0F).endVertex();
        bufferbuilder.vertex(matrix4f, star2.getFirst() + size, 100f, star2.getSecond() + size).uv(1.0F, 1.0F).endVertex();
        bufferbuilder.vertex(matrix4f, star1.getFirst() + size, 100f, star1.getSecond() - size).uv(1.0F, 0.0f).endVertex();
        bufferbuilder.vertex(matrix4f, star1.getFirst() + size, 100f, star1.getSecond() + size).uv(0.0f, 0.0f).endVertex();
        BufferUploader.drawWithShader(bufferbuilder.end());
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableCull();
    }
}
