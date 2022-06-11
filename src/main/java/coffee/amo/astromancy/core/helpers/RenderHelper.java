package coffee.amo.astromancy.core.helpers;

import coffee.amo.astromancy.core.systems.stars.Star;
import coffee.amo.astromancy.core.systems.stars.StarUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import com.sammy.ortus.helpers.util.Color;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;

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

    // todo: use Hek's AT alternative, add blockpos arguments, use that for center
    public static void renderFacingQuad(PoseStack ps, float size, VertexConsumer buff, Color color) {
//        Vec3 player = Minecraft.getInstance().player.getEyePosition();
//        Vec3 center = new Vec3(ps.last().pose().m03 + 0.5, ps.last().pose().m13 + 1.0f, ps.last().pose().m23 + 0.5);
//
//        Vec3 startYaw = new Vec3(0.0, 0.0, 1.0);
//        Vec3 endYaw = new Vec3(player.x, 0.0, player.z).subtract(new Vec3(center.x, 0.0, center.z)).normalize();
//        Vec3 d = player.subtract(center);
//
//        // Find angle between start & end in yaw
//        float yaw = (float) Math.toDegrees(Math.atan2(endYaw.x - startYaw.x, endYaw.z - startYaw.z)) + 90;
//
//        // Find angle between start & end in pitch
//        float pitch = (float) Math.toDegrees(Math.atan2(Math.sqrt(d.z * d.z + d.x * d.x), d.y) + Math.PI);
//
//        Quaternion Q = Quaternion.ONE.copy();
//
//        // doubling to account for how quats work
//        Q.mul(new Quaternion(new Vector3f(0.0f, 1.0f, 0.0f), -yaw * 2, true));
//        Q.mul(new Quaternion(new Vector3f(1.0f, 0.0f, 0.0f), pitch + 90, true));
//        //Q.mul(-1);
//        ps.mulPose(Q);
        buff.vertex(ps.last().pose(), 0, size, 0).color(color.getRGB()).uv(0, 1).uv2(0, 1).endVertex();
        buff.vertex(ps.last().pose(), size, size, 0).color(color.getRGB()).uv(1, 1).uv2(1, 1).endVertex();
        buff.vertex(ps.last().pose(), size, 0, 0).color(color.getRGB()).uv(1, 0).uv2(1, 0).endVertex();
        buff.vertex(ps.last().pose(), 0, 0, 0).color(color.getRGB()).uv(0, 0).uv2(0, 0).endVertex();
    }

    private static void renderQuad(PoseStack ps, float size, VertexConsumer buff, Color color) {
        buff.vertex(ps.last().pose(), 0, size, 0).color(color.getRGB()).uv2(0, 1).uv(0, 1).overlayCoords(0, 1).normal(0, 1, 0).endVertex();
        buff.vertex(ps.last().pose(), size, size, 0).color(color.getRGB()).uv2(1, 1).uv(1, 1).overlayCoords(1, 1).normal(1, 1, 0).endVertex();
        buff.vertex(ps.last().pose(), size, 0, 0).color(color.getRGB()).uv2(1, 0).uv(1, 0).overlayCoords(1, 0).normal(1, 0, 0).endVertex();
        buff.vertex(ps.last().pose(), 0, 0, 0).color(color.getRGB()).uv2(0, 0).uv(0, 0).overlayCoords(0, 0).normal(0, 0, 0).endVertex();
    }

    public static void renderStar(PoseStack ps, MultiBufferSource buff, Star star, BlockEntity blockEntity, float pPartialTick) {
        float multiplier = (float) Math.max(Math.min(1.5, star.getType().getMassMultiplier() / 10.5f), 0.5f);
        float fac = (blockEntity.getLevel().getGameTime() + pPartialTick) * multiplier;
        ps.pushPose();
        Vec3 offset = StarUtils.generatePosition(star);
        ps.translate(offset.x * 1.5, offset.y * 5, offset.z * 1.5);
        ps.translate(0.0175f * multiplier, 0.0175f * multiplier, 0.0175f * multiplier);
        ps.mulPose(Vector3f.XP.rotationDegrees(fac));
        ps.mulPose(Vector3f.YP.rotationDegrees(fac));
        ps.mulPose(Vector3f.ZP.rotationDegrees(fac));
        ps.translate(-0.0175f * multiplier, -0.0175f * multiplier, -0.0175f * multiplier);
        RenderHelper.renderInvertedCube(ps, buff, 0.035f * multiplier, RenderType.lightning(), getStarColor(star, blockEntity.getLevel()));
        ps.translate(0.005f * multiplier, 0.005f * multiplier, 0.005f * multiplier);
        RenderHelper.renderInvertedCube(ps, buff, 0.025f * multiplier, RenderType.lightning(), getStarColor(star, blockEntity.getLevel()).mixWith(Color.WHITE, 0.35f));
        ps.popPose();
    }

    private static Color getStarColor(Star star, Level level) {
        switch (star.getType()) {
            case HYPERGIANT:
                return new Color(255, 183, 128, 255);

            case SUPERGIANT:
                return new Color(161, 236, 247, 255);
            case BRIGHT_GIANT:
                return new Color(161, 236, 247, 255);
            case GIANT:
                return new Color(255, 183, 128, 255);
            case SUBGIANT:
                return new Color(161, 236, 247, 255);
            case MAIN_SEQUENCE:
                return new Color(255, 183, 128, 255);
            case DWARF:
                return new Color(255, 183, 128, 255);
            case SUBDWARF:
                return new Color(161, 236, 247, 255);
            case WHITE_DWARF:
                return new Color(200, 200, 200, 255);
            case CRIMSON:
                return new Color(235, 47, 75, 255);
            case PURE:
                return new Color(200, 200, 200, 255);
            case DARK:
                return new Color(255, 0, 255, 255);
            case EMPTY:
                return new Color(1, 1, 1, 255);
            case HELL:
                return new Color(255, 75, 0, 255);
            default:
                return new Color(135, 135, 135, 255);
        }
    }
}
