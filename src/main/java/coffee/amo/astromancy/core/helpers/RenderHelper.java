package coffee.amo.astromancy.core.helpers;

import coffee.amo.astromancy.Astromancy;
import coffee.amo.astromancy.core.registration.RenderTypeRegistry;
import coffee.amo.astromancy.core.systems.stars.Star;
import coffee.amo.astromancy.core.systems.stars.StarUtils;
import coffee.amo.astromancy.core.systems.stars.classification.StarColors;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;

import java.awt.*;

public class RenderHelper {

    public static void renderInvertedCube(PoseStack ps, MultiBufferSource buffer, float size, RenderType renderType) {
        ps.pushPose();
        ps.translate(-(Math.floor(size / 2)), 0, -(Math.floor(size / 2)));
        ps.mulPose(Vector3f.XP.rotationDegrees(90));
        VertexConsumer buff = buffer.getBuffer(renderType);
        renderQuad(ps, size, buff);
        ps.mulPose(Vector3f.XP.rotationDegrees(90));
        ps.translate(0, -size, 0);
        renderQuad(ps, size, buff);
        ps.mulPose(Vector3f.XP.rotationDegrees(90));
        ps.translate(0, -size, 0);
        renderQuad(ps, size, buff);
        ps.mulPose(Vector3f.XP.rotationDegrees(90));
        ps.translate(0, -size, 0);
        renderQuad(ps, size, buff);
        ps.mulPose(Vector3f.YP.rotationDegrees(270));
        ps.translate(-size, 0, 0);
        renderQuad(ps, size, buff);
        ps.mulPose(Vector3f.YP.rotationDegrees(180));
        ps.translate(-size, 0, size);
        renderQuad(ps, size, buff);
        ps.popPose();
    }

    public static void renderNormalCuboid(PoseStack ps, MultiBufferSource buffer, float size, RenderType renderType) {
        ps.pushPose();
        ps.translate((Math.floor(size / 2)), 0, (Math.floor(size / 2)));
        ps.mulPose(Vector3f.XP.rotationDegrees(90));
        VertexConsumer buff = buffer.getBuffer(renderType);
        renderQuad(ps, size, buff);
        ps.mulPose(Vector3f.XP.rotationDegrees(90));
        ps.translate(0, size, 0);
        renderQuad(ps, size, buff);
        ps.mulPose(Vector3f.XP.rotationDegrees(90));
        ps.translate(0, size, 0);
        renderQuad(ps, size, buff);
        ps.mulPose(Vector3f.XP.rotationDegrees(90));
        ps.translate(0, size, 0);
        renderQuad(ps, -size, buff);
        ps.mulPose(Vector3f.YP.rotationDegrees(270));
        ps.translate(size, 0, 0);
        renderQuad(ps, size, buff);
        ps.mulPose(Vector3f.YP.rotationDegrees(180));
        ps.translate(size, 0, -size);
        renderQuad(ps, size, buff);
        ps.popPose();
    }

    public static void renderInvertedCube(PoseStack ps, MultiBufferSource buffer, float size, RenderType renderType, Color color) {
        ps.pushPose();
        ps.translate(-(Math.floor(size / 2)), 0, -(Math.floor(size / 2)));
        ps.mulPose(Vector3f.XP.rotationDegrees(90));
        VertexConsumer buff = buffer.getBuffer(renderType);
        renderQuad(ps, size, buff, color);
        ps.mulPose(Vector3f.XP.rotationDegrees(90));
        ps.translate(0, -size, 0);
        renderQuad(ps, size, buff, color);
        ps.mulPose(Vector3f.XP.rotationDegrees(90));
        ps.translate(0, -size, 0);
        renderQuad(ps, size, buff, color);
        ps.mulPose(Vector3f.XP.rotationDegrees(90));
        ps.translate(0, -size, 0);
        renderQuad(ps, size, buff, color);
        ps.mulPose(Vector3f.YP.rotationDegrees(270));
        ps.translate(-size, 0, 0);
        renderQuad(ps, size, buff, color);
        ps.mulPose(Vector3f.YP.rotationDegrees(180));
        ps.translate(-size, 0, size);
        renderQuad(ps, size, buff, color);
        ps.popPose();
    }


    private static void renderQuad(PoseStack ps, float size, VertexConsumer buff) {
        buff.vertex(ps.last().pose(), 0, size, 0).color(0xFFFF55FF).uv(0, 1).endVertex();
        buff.vertex(ps.last().pose(), size, size, 0).color(0xFFFF55FF).uv(1, 1).endVertex();
        buff.vertex(ps.last().pose(), size, 0, 0).color(0xFFFF55FF).uv(1, 0).endVertex();
        buff.vertex(ps.last().pose(), 0, 0, 0).color(0xFFFF55FF).uv(0, 0).endVertex();
    }

    public static void renderQuad(PoseStack ps, float size, VertexConsumer buff, Color color) {
        buff.vertex(ps.last().pose(), 0, size, 0).color(color.getRGB()).uv2(0, 1).uv(0, 1).overlayCoords(0, 1).normal(0, 1, 0).endVertex();
        buff.vertex(ps.last().pose(), size, size, 0).color(color.getRGB()).uv2(1, 1).uv(1, 1).overlayCoords(1, 1).normal(1, 1, 0).endVertex();
        buff.vertex(ps.last().pose(), size, 0, 0).color(color.getRGB()).uv2(1, 0).uv(1, 0).overlayCoords(1, 0).normal(1, 0, 0).endVertex();
        buff.vertex(ps.last().pose(), 0, 0, 0).color(color.getRGB()).uv2(0, 0).uv(0, 0).overlayCoords(0, 0).normal(0, 0, 0).endVertex();
    }

    public static void renderQuad(PoseStack ps, float xSize, float ySize, VertexConsumer buff, int color) {
        buff.vertex(ps.last().pose(), 0, ySize, 0).color(color).uv(0, 1).uv2(0, 1).normal(0, 1, 0).endVertex();
        buff.vertex(ps.last().pose(), xSize, ySize, 0).color(color).uv(1, 1).uv2(1, 1).normal(1, 1, 0).endVertex();
        buff.vertex(ps.last().pose(), xSize, 0, 0).color(color).uv(1, 0).uv2(1, 0).normal(1, 0, 0).endVertex();
        buff.vertex(ps.last().pose(), 0, 0, 0).color(color).uv(0, 0).uv2(0, 0).normal(0, 0, 0).endVertex();
    }

    public static void renderStar(PoseStack ps, float size, MultiBufferSource buff, Star star, BlockEntity blockEntity, float pPartialTick, Font font, boolean offsets) {
        float massMult = Math.min(star.getMass() / 275f, 1.75f);
        float multiplier = Math.max(massMult, 0.5f);
        float fac = (blockEntity.getLevel().getGameTime() + pPartialTick) * multiplier;
        ps.pushPose();
        if (offsets) {
            Vec3 offset = StarUtils.generatePosition(star, blockEntity.getLevel());
            ps.translate(offset.x / 2, (offset.y + (star.getRandomOffset() * 200)) / 1.75f + 0.05f, offset.z / 2);
        }
        ps.translate(0.5f, 0.5f, 0.5f);
        ps.mulPose(Vector3f.YP.rotationDegrees(fac));
        ps.mulPose(Vector3f.ZP.rotationDegrees(fac));
        ps.translate(-(size / 2) * multiplier, -(size / 2) * multiplier, -(size / 2) * multiplier);
        RenderHelper.renderInvertedCube(ps, buff, size * multiplier, RenderTypeRegistry.additiveTexture(Astromancy.astromancy("textures/vfx/white.png")), getStarColor(star));
        ps.translate((size / 5) * multiplier, (size / 5) * multiplier, (size / 5) * multiplier);
        RenderHelper.renderInvertedCube(ps, buff, ((size / 7) * 4) * multiplier, RenderTypeRegistry.additiveTexture(Astromancy.astromancy("textures/vfx/white.png")), getStarColor(star).brighter().brighter());
        Vec3 offset = Vec3.atLowerCornerOf(blockEntity.getBlockPos().offset(ps.last().pose().m03, ps.last().pose().m13, ps.last().pose().m23));
        ps.popPose();
    }

    public static void renderText(PoseStack ps, String text, MultiBufferSource buffer, Font font) {
        ps.pushPose();
        ps.mulPose(Vector3f.XP.rotation(135));
        Vec3 player = new Vec3(0, 1.5f, 0);
        Vec3 center = new Vec3(ps.last().pose().m03, ps.last().pose().m13, ps.last().pose().m23);

        Vec3 startYaw = new Vec3(0.0, 0.0, 1.0);
        Vec3 endYaw = new Vec3(player.x, 0.0, player.z).subtract(new Vec3(center.x, 0.0, center.z)).normalize();
        Vec3 d = player.subtract(center);

        // Find angle between start & end in yaw
        float yaw = (float) Math.toDegrees(Math.atan2(endYaw.x - startYaw.x, endYaw.z - startYaw.z)) + 90;

        // Find angle between start & end in pitch
        float pitch = (float) Math.toDegrees(Math.atan2(Math.sqrt(d.z * d.z + d.x * d.x), d.y) + Math.PI);

        Quaternion Q = Quaternion.ONE.copy();

        // doubling to account for how quats work
        Q.mul(new Quaternion(new Vector3f(0.0f, 1.0f, 0.0f), -yaw * 2, true));
        Q.mul(new Quaternion(new Vector3f(1.0f, 0.0f, 0.0f), pitch + 90, true));
        //Q.mul(-1);
        ps.mulPose(Q);
        ps.scale(0.007f, 0.007f, 0.007f);
        ps.translate(-60, 10, 10f);
        ps.translate(font.width(text), 0, 0);
        font.draw(ps, text, 0, 0, Color.WHITE.getRGB());
        ps.translate(-font.width(text), 0, 0);
        ps.popPose();
    }

    public static void doFacingFromBlock(PoseStack ps, BlockEntity blockEntity){
        //ps.mulPose(Vector3f.XP.rotation(135));
        Vec3 player = new Vec3(0, 1.5f, 0);
        Vec3 center = new Vec3(blockEntity.getBlockPos().getX() + 0.5, blockEntity.getBlockPos().getY() + 0.5, blockEntity.getBlockPos().getZ() + 0.5);

        Vec3 startYaw = new Vec3(0.0, 0.0, 1.0);
        Vec3 endYaw = new Vec3(player.x, 0.0, player.z).subtract(new Vec3(center.x, 0.0, center.z)).normalize();
        Vec3 d = player.subtract(center);

        // Find angle between start & end in yaw
        float yaw = (float) Math.toDegrees(Math.atan2(endYaw.x - startYaw.x, endYaw.z - startYaw.z)) + 90;

        // Find angle between start & end in pitch
        float pitch = (float) Math.toDegrees(Math.atan2(Math.sqrt(d.z * d.z + d.x * d.x), d.y) + Math.PI);

        Quaternion Q = Quaternion.ONE.copy();

        // doubling to account for how quats work
        Q.mul(new Quaternion(new Vector3f(0.0f, 1.0f, 0.0f), -yaw * 2, true));
        Q.mul(new Quaternion(new Vector3f(1.0f, 0.0f, 0.0f), pitch + 90, true));
        //Q.mul(-1);
        ps.mulPose(Q);
    }

    public static void renderDisc(PoseStack ps, float size, MultiBufferSource buffer, RenderType type, float partialTick) {
        ps.pushPose();
        VertexConsumer consumer = buffer.getBuffer(type);
        ps.translate(-size / 2.5, -size / 2.5, 0.01f);
        renderQuad(ps, size, consumer, new Color(255, 0, 0, 255));
        ps.translate(size / 2.5, size / 2.5, -0.01f);
        ps.mulPose(Vector3f.XP.rotationDegrees(180));
        ps.translate(-size / 2.5, -size / 2.5, -0.01f);
        renderQuad(ps, size, consumer, new Color(255, 0, 0, 255));
        ps.popPose();
    }

    private static Color getStarColor(Star star) {
        Color color = new Color(0,0,0,255);
        color = switch (star.getSpectralClass()) {
            case 'O' -> StarColors.O.getColor();
            case 'B' -> StarColors.B.getColor();
            case 'A' -> StarColors.A.getColor();
            case 'F' -> StarColors.F.getColor();
            case 'G' -> StarColors.G.getColor();
            case 'K' -> StarColors.K.getColor();
            case 'M' -> StarColors.M.getColor();
            default -> new Color(0, 0, 0, 255);
        };
        color = switch (star.getType()) {
            case EXOTIC -> StarColors.EXOTIC.getColor();
            case EMPTY -> StarColors.EMPTY.getColor();
            case PURE -> StarColors.PURE.getColor();
            case HELL -> StarColors.HELL.getColor();
            default -> color;
        };
        return color;
    }

    private static void drawHorizontalLineBetween(MultiBufferSource buffer, PoseStack mstack, Vec3 local, Vec3 target, float lineWidth, int r, int g, int b, int a) {
        VertexConsumer builder = buffer.getBuffer(RenderType.leash());

        //Calculate yaw
        float rotY = (float) Mth.atan2(target.x - local.x, target.z - local.z);

        //Calculate pitch
        double distX = target.x - local.x;
        double distZ = target.z - local.z;
        float rotX = (float) Mth.atan2(target.y - local.y, Mth.sqrt((float) (distX * distX + distZ * distZ)));

        mstack.pushPose();

        //Translate to start point
        mstack.translate(local.x, local.y, local.z);
        //Rotate to point towards end point
        mstack.mulPose(Vector3f.YP.rotation(rotY));
        mstack.mulPose(Vector3f.XN.rotation(rotX));

        //Calculate distance between points -> length of the line
        float distance = (float) local.distanceTo(target);

        Matrix4f matrix = mstack.last().pose();
        float halfWidth = lineWidth / 2F;

        //Draw horizontal quad
        builder.vertex(matrix, -halfWidth, 0, 0).color(r, g, b, a).uv2(0xF000F0).endVertex();
        builder.vertex(matrix, halfWidth, 0, 0).color(r, g, b, a).uv2(0xF000F0).endVertex();
        builder.vertex(matrix, halfWidth, 0, distance).color(r, g, b, a).uv2(0xF000F0).endVertex();
        builder.vertex(matrix, -halfWidth, 0, distance).color(r, g, b, a).uv2(0xF000F0).endVertex();

        //Draw vertical Quad
        builder.vertex(matrix, 0, -halfWidth, 0).color(r, g, b, a).uv2(0xF000F0).endVertex();
        builder.vertex(matrix, 0, halfWidth, 0).color(r, g, b, a).uv2(0xF000F0).endVertex();
        builder.vertex(matrix, 0, halfWidth, distance).color(r, g, b, a).uv2(0xF000F0).endVertex();
        builder.vertex(matrix, 0, -halfWidth, distance).color(r, g, b, a).uv2(0xF000F0).endVertex();

        mstack.popPose();
    }

    public static boolean isLookingAt(LocalPlayer player, Vec3 target, double accuracy){
        Vec3 diff = new Vec3(target.x - player.getX(), target.y - player.getEyeY(), target.z - player.getZ());
        double length = diff.length();
        double dot = player.getViewVector(1.0f).normalize().dot(diff.normalize());
        return dot > 1.0D - accuracy / length;
    }

    public static void renderSkybox(PoseStack pPoseStack, ResourceLocation texture, float partialTick, float gameTime, float starVis) {
        pPoseStack.pushPose();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.depthMask(false);
        RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
        RenderSystem.setShaderTexture(0, texture);
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferbuilder = tesselator.getBuilder();
        pPoseStack.mulPose(Vector3f.YP.rotationDegrees((gameTime * 0.05F)));

        for(int i = 0; i < 6; ++i) {
            pPoseStack.pushPose();
            if (i == 1) {
                pPoseStack.mulPose(Vector3f.XP.rotationDegrees(90.0F));
            }

            if (i == 2) {
                pPoseStack.mulPose(Vector3f.XP.rotationDegrees(-90.0F));
            }

            if (i == 3) {
                pPoseStack.mulPose(Vector3f.XP.rotationDegrees(180.0F));
            }

            if (i == 4) {
                pPoseStack.mulPose(Vector3f.ZP.rotationDegrees(90.0F));
            }

            if (i == 5) {
                pPoseStack.mulPose(Vector3f.ZP.rotationDegrees(-90.0F));
            }

            float alpha = starVis;

            Matrix4f matrix4f = pPoseStack.last().pose();
            bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
            bufferbuilder.vertex(matrix4f, -90.0F, -90.0F, -90.0F).uv(0.0F, 0.0F).color(0.2f, 0.2f, 0.2f, alpha).endVertex();
            bufferbuilder.vertex(matrix4f, -90.0F, -90.0F, 90.0F).uv(0.0F, 1.0F).color(0.2f, 0.2f, 0.2f, alpha).endVertex();
            bufferbuilder.vertex(matrix4f, 90.0F, -90.0F, 90.0F).uv(1.0F, 1.0F).color(0.2f, 0.2f, 0.2f, alpha).endVertex();
            bufferbuilder.vertex(matrix4f, 90.0F, -90.0F, -90.0F).uv(1.0F, 0.0F).color(0.2f, 0.2f, 0.2f, alpha).endVertex();
            tesselator.end();
            pPoseStack.popPose();
        }

        RenderSystem.depthMask(true);
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
        pPoseStack.popPose();
    }
}
