package coffee.amo.astromancy.client.renderer.block;

import coffee.amo.astromancy.Astromancy;
import coffee.amo.astromancy.common.blockentity.jar.JarBlockEntity;
import coffee.amo.astromancy.core.helpers.RenderHelper;
import coffee.amo.astromancy.core.registration.RenderTypeRegistry;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.util.FastColor;
import net.minecraft.world.phys.Vec3;

public class JarRenderer implements BlockEntityRenderer<JarBlockEntity> {
    private final Font font;
    public JarRenderer(BlockEntityRendererProvider.Context context) {
        this.font = context.getFont();
    }

    @Override
    public void render(JarBlockEntity pBlockEntity, float pPartialTick, PoseStack ps, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        ps.pushPose();
        if(pBlockEntity.label) {
            renderLabel(pBlockEntity, pPartialTick, ps, pBufferSource, pPackedLight, pPackedOverlay);
        }
        ps.mulPose(Vector3f.YP.rotationDegrees(180));
        ps.translate(-0.75,0.5,0);
        if(!pBlockEntity.isEmpty()){
            float scale = Math.min(0.749f,(Math.round((pBlockEntity.getAmount() / 256f) * 16.0f) / 32.0f ) * 1 + ((pBlockEntity.getAmount() / 256f)/4f));
            ps.pushPose();
            ps.mulPose(Vector3f.ZP.rotation(135));
            ps.translate(-0.25,0,-0.5);
            LocalPlayer player = Minecraft.getInstance().player;
            Vec3 eyeVec = player.getEyePosition();
            double oldEyeY = player.yOld + player.getEyeHeight();
            Vec3 playerVec = new Vec3(player.xOld + (eyeVec.x - player.xOld) * pPartialTick, oldEyeY + (eyeVec.y - oldEyeY) * pPartialTick, player.zOld + (eyeVec.z - player.zOld) * pPartialTick);
            Vec3 center = new Vec3(pBlockEntity.getBlockPos().getX() + 0.5, pBlockEntity.getBlockPos().getY() + 0.5, pBlockEntity.getBlockPos().getZ() + 0.5);

            Vec3 startYaw = new Vec3(0.0, 0.0, 1.0);
            Vec3 endYaw = new Vec3(playerVec.x, 0.0, playerVec.z).subtract(new Vec3(center.x, 0.0, center.z)).normalize();
            Vec3 d = playerVec.subtract(center);

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
            float distance = pBlockEntity.clientLookAtTicks == 10 ? 10 : pBlockEntity.clientLookAtTicks + pPartialTick;
            ps.translate(0,0.1f,-0.5 * (distance / 10.0f));
            ps.scale(0.009f, 0.009f, 0.009f);
            ps.translate(-font.width(pBlockEntity.getGlyph().name() + " " + pBlockEntity.getAmount())/4,0,0);
            font.draw(ps, pBlockEntity.getGlyphComponent(), 0, 0, FastColor.ARGB32.multiply(0xFFFFFFFF, FastColor.ARGB32.color(Math.max(0x04, Math.round((Math.max(0.8f,pBlockEntity.clientLookAtTicks) / 10.0f) * 255.0f)), 255, 255, 255)));
            ps.popPose();
        }
        ps.popPose();
    }

    @Override
    public boolean shouldRender(JarBlockEntity pBlockEntity, Vec3 pCameraPos) {
        return true;
    }

    @Override
    public boolean shouldRenderOffScreen(JarBlockEntity pBlockEntity) {
        return true;
    }

    public void renderLabel(JarBlockEntity pBlockEntity, float pPartialTick, PoseStack ps, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay){
        ps.pushPose();
        ps.translate(0.5f,0.5f,0.5f);
        switch(pBlockEntity.labelDirection){
            case NORTH:
                break;
            case SOUTH:
                ps.mulPose(Vector3f.YP.rotationDegrees(180));
                break;
            case EAST:
                ps.mulPose(Vector3f.YP.rotationDegrees(270));
                break;
            case WEST:
                ps.mulPose(Vector3f.YP.rotationDegrees(90));
                break;
        }
        ps.translate(-0.225f,-0.35f,-0.315f);
        VertexConsumer buff = pBufferSource.getBuffer(RenderTypeRegistry.additiveTexture(Astromancy.astromancy("textures/block/label.png")));
        ps.pushPose();
        ps.translate(0.45f,0.45f,0);
        RenderHelper.renderQuad(ps, -0.45f, -0.45f, buff, 0xFFFFFFFF);
        ps.popPose();
        ps.translate(0,0,-0.0001f);
        ps.scale(0.05f, 0.05f, 0.05f);
        ps.mulPose(Vector3f.ZP.rotationDegrees(180));
        ps.translate(-font.width(pBlockEntity.getGlyphSymbolComponent()) + 0.5, -font.lineHeight + 0.5,0);
        font.draw(ps, pBlockEntity.getGlyphSymbolComponent(), 0, 0, 0xFFFFFFFF);
        ps.popPose();
    }
}
