package coffee.amo.astromancy.mixin.client;

import coffee.amo.astromancy.Astromancy;
import coffee.amo.astromancy.client.systems.ClientConstellationHolder;
import coffee.amo.astromancy.core.systems.stars.classification.ConstellationInstance;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import com.mojang.math.Vector4f;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.XoroshiroRandomSource;
import net.minecraft.world.level.levelgen.synth.PerlinNoise;
import net.minecraft.world.level.levelgen.synth.SimplexNoise;
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

    private Matrix4f pose;

    @Inject(at = @At(value = "HEAD"), method = "renderSky")
    public void capturePose(PoseStack p_202424_, Matrix4f p_202425_, float p_202426_, Camera p_202427_, boolean p_202428_, Runnable p_202429_, CallbackInfo ci) {
        this.pose = p_202424_.last().pose();
    }

    @Inject(at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;mulPose(Lcom/mojang/math/Quaternion;)V", ordinal = 4, shift = At.Shift.AFTER), method = "renderSky", locals = LocalCapture.CAPTURE_FAILHARD)
    public void renderConstellations(PoseStack p_202424_, Matrix4f p_202425_, float p_202426_, Camera p_202427_, boolean p_202428_, Runnable p_202429_, CallbackInfo ci, FogType fogtype, Vec3 vec3, float f, float f1, float f2, BufferBuilder bufferbuilder, ShaderInstance shaderinstance, float[] afloat, float f11) {
        if (!level.dimension().equals(Level.OVERWORLD)) return;
        float starBrightness = this.level.getStarBrightness(p_202426_) * f11;
        if (starBrightness > 0.0F) {
            p_202424_.pushPose();
            for (ConstellationInstance constellationInstance : ClientConstellationHolder.getConstellationInstances()) {
                SimplexNoise noise = constellationInstance.getNoise();
                if(Math.round((level.getGameTime() / 24000f)+1) % constellationInstance.getDaysVisible() == 0){
                    float dayScale = this.level.getTimeOfDay(p_202426_) * 0.8f;
                    float rotFactorZ = (dayScale * constellationInstance.getOffset())+25f %360;
                    float rotFactorX = (dayScale * -constellationInstance.getOffset())+174 %360;
                    float rotFactorY = (dayScale * constellationInstance.getOffset())+248 %360;
                    p_202424_.mulPose(Vector3f.ZN.rotationDegrees(rotFactorZ));
                    p_202424_.mulPose(Vector3f.XP.rotationDegrees(rotFactorX));
                    p_202424_.mulPose(Vector3f.YN.rotationDegrees(rotFactorY));
                    float offset = 100f;
                    var v4f = new Vector4f(new Vector3f(0f, offset, 0f));
                    v4f.transform(this.pose);
                    var untransformedVector = new Vector3f(v4f.x(), v4f.y(), v4f.z());
                    var vector4f = new Vector4f(untransformedVector);
                    vector4f.transform(p_202424_.last().pose());
                    //var transformedVector = new Vector3f(vector4f.x(), vector4f.y(), vector4f.z());
                    //transformedVector.normalize();
                    untransformedVector.normalize();
                    float rotMult = 1f;
                    float testMult = (float) Math.max(0.25f,Math.sin(Minecraft.getInstance().level.getGameTime() + (constellationInstance.getOffset()*10) / 100f));
                    testMult *= Minecraft.getInstance().level.random.nextFloat() > 0.9f ? 0.95f : 1f;

                    RenderSystem.setShaderColor(starBrightness * rotMult, starBrightness * rotMult, starBrightness* rotMult, 1.0F);
                    Matrix4f matrix4f = p_202424_.last().pose();
                    float k = 20f;
                    RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
                    RenderSystem.setShaderTexture(0, constellationInstance.getConstellation().getIcon());
                    bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
                    bufferbuilder.vertex(matrix4f, -k, offset, -k).uv(0.0F, 0.0F).color(1f,1f,1f, (float)noise.getValue(1 + Minecraft.getInstance().level.getGameTime()/5000f,1,Minecraft.getInstance().level.getGameTime()/5000f)).endVertex();
                    bufferbuilder.vertex(matrix4f, k, offset, -k).uv(1.0F, 0.0F).color(1,1,1, (float)noise.getValue(128,128 + Minecraft.getInstance().level.getGameTime()/5000f,Minecraft.getInstance().level.getGameTime()/5000f)).endVertex();
                    bufferbuilder.vertex(matrix4f, k, offset, k).uv(1.0F, 1.0F).color(1,1,1, (float)noise.getValue(256 + Minecraft.getInstance().level.getGameTime()/5000f,256,Minecraft.getInstance().level.getGameTime()/5000f)).endVertex();
                    bufferbuilder.vertex(matrix4f, -k, offset, k).uv(0.0F, 1.0F).color(1,1,1, (float)noise.getValue(512,512 + Minecraft.getInstance().level.getGameTime()/5000f,Minecraft.getInstance().level.getGameTime()/5000f)).endVertex();
                    BufferUploader.drawWithShader(bufferbuilder.end());
                    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                }
            }
            p_202424_.popPose();
        }
    }
}
