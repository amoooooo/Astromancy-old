package coffee.amo.astromancy.core.helpers;

import coffee.amo.astromancy.core.systems.stars.Star;
import coffee.amo.astromancy.core.systems.stars.StarUtils;
import coffee.amo.astromancy.core.systems.stars.classification.StarColors;
import coffee.amo.astromancy.core.systems.stars.types.StarType;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.Level;
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
        ps.translate((size) * multiplier, (size) * multiplier, (size) * multiplier);
        //renderText(ps, star.getName(), buff, font);
        //ps.mulPose(Vector3f.XP.rotationDegrees(fac));
        ps.mulPose(Vector3f.YP.rotationDegrees(fac));
        ps.mulPose(Vector3f.ZP.rotationDegrees(fac));
        ps.translate(-(size / 2) * multiplier, -(size / 2) * multiplier, -(size / 2) * multiplier);
        RenderHelper.renderInvertedCube(ps, buff, size * multiplier, RenderType.lightning(), getStarColor(star));
        ps.translate((size / 5) * multiplier, (size / 5) * multiplier, (size / 5) * multiplier);
        RenderHelper.renderInvertedCube(ps, buff, ((size / 7) * 4) * multiplier, RenderType.lightning(), getStarColor(star).brighter().brighter());
        //renderDisc(ps, 0.1f,buff, RenderType.lightning(), pPartialTick);
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
}
