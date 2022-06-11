package coffee.amo.astromancy.core.helpers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import com.sammy.ortus.helpers.util.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Vec3i;
import net.minecraft.world.phys.Vec3;

public class RenderHelper {
    public static void renderInvertedCube(PoseStack ps, MultiBufferSource buffer, float size, RenderType renderType) {
        ps.pushPose();
        ps.translate(-(Math.floor(size/2)),0,-(Math.floor(size/2)));
        ps.mulPose(Vector3f.XP.rotationDegrees(90));
        VertexConsumer buff = buffer.getBuffer(renderType);
        renderQuad(ps, size, buff);
        ps.mulPose(Vector3f.XP.rotationDegrees(90));
        ps.translate(0,-size,0);
        renderQuad(ps, size, buff);
        ps.mulPose(Vector3f.XP.rotationDegrees(90));
        ps.translate(0,-size,0);
        renderQuad(ps, size, buff);
        ps.mulPose(Vector3f.XP.rotationDegrees(90));
        ps.translate(0,-size,0);
        renderQuad(ps, size, buff);
        ps.mulPose(Vector3f.YP.rotationDegrees(270));
        ps.translate(-size,0,0);
        renderQuad(ps, size, buff);
        ps.mulPose(Vector3f.YP.rotationDegrees(180));
        ps.translate(-size,0,size);
        renderQuad(ps, size, buff);
        ps.popPose();
    }

    public static void renderInvertedCube(PoseStack ps, MultiBufferSource buffer, float size, RenderType renderType, Color color) {
        ps.pushPose();
        ps.translate(-(Math.floor(size/2)),0,-(Math.floor(size/2)));
        ps.mulPose(Vector3f.XP.rotationDegrees(90));
        VertexConsumer buff = buffer.getBuffer(renderType);
        renderQuad(ps, size, buff, color);
        ps.mulPose(Vector3f.XP.rotationDegrees(90));
        ps.translate(0,-size,0);
        renderQuad(ps, size, buff, color);
        ps.mulPose(Vector3f.XP.rotationDegrees(90));
        ps.translate(0,-size,0);
        renderQuad(ps, size, buff, color);
        ps.mulPose(Vector3f.XP.rotationDegrees(90));
        ps.translate(0,-size,0);
        renderQuad(ps, size, buff, color);
        ps.mulPose(Vector3f.YP.rotationDegrees(270));
        ps.translate(-size,0,0);
        renderQuad(ps, size, buff, color);
        ps.mulPose(Vector3f.YP.rotationDegrees(180));
        ps.translate(-size,0,size);
        renderQuad(ps, size, buff, color);
        ps.popPose();
    }



    private static void renderQuad(PoseStack ps, float size, VertexConsumer buff) {
        buff.vertex(ps.last().pose(), 0, size, 0).color(0xFFFF55FF).uv(0,1).endVertex();
        buff.vertex(ps.last().pose(), size, size, 0).color(0xFFFF55FF).uv(1,1).endVertex();
        buff.vertex(ps.last().pose(), size, 0, 0).color(0xFFFF55FF).uv(1,0).endVertex();
        buff.vertex(ps.last().pose(), 0, 0, 0).color(0xFFFF55FF).uv(0,0).endVertex();
    }

    // todo: use Hek's AT alternative, add blockpos arguments, use that for center
    public static void renderFacingQuad(PoseStack ps, float size, VertexConsumer buff, Color color) {
        Vec3 player = Minecraft.getInstance().player.getEyePosition();
        Vec3 center = new Vec3(ps.last().pose().m03 + 0.5, ps.last().pose().m13 + 1.0f, ps.last().pose().m23 + 0.5);

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
        buff.vertex(ps.last().pose(), 0, size, 0).color(color.getRGB()).uv(0,1).uv2(0,1).endVertex();
        buff.vertex(ps.last().pose(), size, size, 0).color(color.getRGB()).uv(1,1).uv2(1,1).endVertex();
        buff.vertex(ps.last().pose(), size, 0, 0).color(color.getRGB()).uv(1,0).uv2(1,0).endVertex();
        buff.vertex(ps.last().pose(), 0, 0, 0).color(color.getRGB()).uv(0,0).uv2(0,0).endVertex();
    }

    private static void renderQuad(PoseStack ps, float size, VertexConsumer buff, Color color) {
        buff.vertex(ps.last().pose(), 0, size, 0).color(color.getRGB()).uv(0,1).uv2(0,1).endVertex();
        buff.vertex(ps.last().pose(), size, size, 0).color(color.getRGB()).uv(1,1).uv2(1,1).endVertex();
        buff.vertex(ps.last().pose(), size, 0, 0).color(color.getRGB()).uv(1,0).uv2(1,0).endVertex();
        buff.vertex(ps.last().pose(), 0, 0, 0).color(color.getRGB()).uv(0,0).uv2(0,0).endVertex();
    }
}
