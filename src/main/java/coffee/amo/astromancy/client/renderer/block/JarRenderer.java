package coffee.amo.astromancy.client.renderer.block;

import coffee.amo.astromancy.Astromancy;
import coffee.amo.astromancy.common.blockentity.jar.JarBlockEntity;
import coffee.amo.astromancy.core.helpers.RenderHelper;
import coffee.amo.astromancy.core.registration.RenderTypeRegistry;
import coffee.amo.astromancy.core.systems.aspecti.Aspecti;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.util.FastColor;
import net.minecraft.world.phys.Vec3;

import java.awt.*;

public class JarRenderer implements BlockEntityRenderer<JarBlockEntity> {
    private final Font font;
    public JarRenderer(BlockEntityRendererProvider.Context context) {
        this.font = context.getFont();
    }

    @Override
    public void render(JarBlockEntity pBlockEntity, float pPartialTick, PoseStack ps, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        ps.pushPose();
        ps.mulPose(Vector3f.YP.rotationDegrees(180));
        ps.translate(-0.75,0.5,0);
        //RenderHelper.renderNormalCuboid(ps, pBufferSource, 0.875f, RenderType.lightning());
        if(pBlockEntity.getAspecti() != null && pBlockEntity.getCount() > 0){
            //RenderHelper.renderText(ps, pBlockEntity.getAspecti().name() + " " + pBlockEntity.getCount(), pBufferSource, font);
            float scale = Math.min(0.749f,(Math.round((pBlockEntity.getCount() / 256f) * 16.0f) / 32.0f ) * 1 + ((pBlockEntity.getCount() / 256f)/4f));
            ps.pushPose();
            VertexConsumer buff = pBufferSource.getBuffer(RenderTypeRegistry.additiveTexture(Astromancy.astromancy("textures/vfx/white.png")));
            ps.translate(-0.05,-0.5f,-0.8);
            RenderHelper.renderQuad(ps, 0.6f, scale, buff, Aspecti.values()[pBlockEntity.getAspecti().ordinal()].color().getRGB());
            ps.mulPose(Vector3f.YP.rotationDegrees(90));
            ps.translate(-0.6f,0,0);
            RenderHelper.renderQuad(ps, 0.6f, scale, buff, Aspecti.values()[pBlockEntity.getAspecti().ordinal()].color().getRGB());
            ps.mulPose(Vector3f.YP.rotationDegrees(90));
            ps.translate(-0.6f,0,0);
            RenderHelper.renderQuad(ps, 0.6f, scale, buff, Aspecti.values()[pBlockEntity.getAspecti().ordinal()].color().getRGB());
            ps.mulPose(Vector3f.YP.rotationDegrees(90));
            ps.translate(-0.6f,0,0);
            RenderHelper.renderQuad(ps, 0.6f, scale, buff, Aspecti.values()[pBlockEntity.getAspecti().ordinal()].color().getRGB());
            ps.mulPose(Vector3f.XN.rotationDegrees(90));
            ps.translate(0,-0.55,0.001);
            RenderHelper.renderQuad(ps, 0.6f, 0.5f, buff, Aspecti.values()[pBlockEntity.getAspecti().ordinal()].color().getRGB());
            ps.mulPose(Vector3f.XN.rotationDegrees(180));
            ps.translate(0,-0.55,-scale + 0.001);
            RenderHelper.renderQuad(ps, 0.6f, 0.6f, buff, Aspecti.values()[pBlockEntity.getAspecti().ordinal()].color().getRGB());
            ps.popPose();

            ps.pushPose();
            ps.mulPose(Vector3f.ZP.rotation(135));
            ps.translate(-0.25,0,-0.5);
            Vec3 player = Minecraft.getInstance().player.getEyePosition();
            Vec3 center = new Vec3(pBlockEntity.getBlockPos().getX() + 0.5, pBlockEntity.getBlockPos().getY() + 0.5, pBlockEntity.getBlockPos().getZ() + 0.5);

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
            ps.translate(0,0.1f,-0.5 * (pBlockEntity.clientLookAtTicks / 10.0f));
            ps.scale(0.009f, 0.009f, 0.009f);
            ps.translate(-font.width(pBlockEntity.getAspecti().name() + " " + pBlockEntity.getCount())/4,0,0);
            font.draw(ps, pBlockEntity.getAspectiComponent(), 0, 0, FastColor.ARGB32.multiply(0xFFFFFFFF, FastColor.ARGB32.color(Math.max(0x04, Math.round((Math.max(0.8f,pBlockEntity.clientLookAtTicks) / 10.0f) * 255.0f)), 255, 255, 255)));
            ps.popPose();
        }
        ps.popPose();
    }
}
