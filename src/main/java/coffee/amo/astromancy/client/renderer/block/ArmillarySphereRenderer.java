package coffee.amo.astromancy.client.renderer.block;

import coffee.amo.astromancy.Astromancy;
import coffee.amo.astromancy.common.blockentity.armillary_sphere.ArmillarySphereCoreBlockEntity;
import coffee.amo.astromancy.core.registration.RenderTypeRegistry;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;



public class ArmillarySphereRenderer implements BlockEntityRenderer<ArmillarySphereCoreBlockEntity> {

    private final static ResourceLocation BEAM = Astromancy.astromancy("textures/vfx/light_trail.png");
    private final static RenderType BEAM_TYPE = RenderTypeRegistry.additiveTexture(BEAM);

    private final Font font;

    private final Vector3f[] offsets = new Vector3f[]{
            new Vector3f(1.1f, 0, -1.1f),
            new Vector3f(1.1f, 0, 1.1f),
            new Vector3f(-1.1f, 0, 1.1f),
            new Vector3f(-1.1f, 0, -1.1f)
    };
    private final Vector3f[] offsets2 = new Vector3f[]{
            new Vector3f(1, 0, -1),
            new Vector3f(1, 0, 1),
            new Vector3f(-1, 0, 1),
            new Vector3f(-1, 0, -1)
    };
    private final Vector3f[] offsets3 = new Vector3f[]{
            new Vector3f(0.9f, 0, -0.9f),
            new Vector3f(0.9f, 0, 0.9f),
            new Vector3f(-0.9f, 0, 0.9f),
            new Vector3f(-0.9f, 0, -0.9f)
    };

    public ArmillarySphereRenderer(BlockEntityRendererProvider.Context context) {
        this.font = context.getFont();
    }

    @Override
    public void render(ArmillarySphereCoreBlockEntity pBlockEntity, float pPartialTick, PoseStack ps, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        ps.pushPose();
        float fac = ((((pBlockEntity.ticksActive) + pPartialTick) * 2 + 2)) * (pBlockEntity.toggled ? 1 : 0);
        //fac *=  ((float)pBlockEntity.ticksActive / 10.0f);
        float scale = Math.max(1 - (pBlockEntity.ticksActive / 300.0f), 0.1f);
        float speed = 1 + (pBlockEntity.ticksActive / 150.0f);
        double actualDistance = Minecraft.getInstance().player.position().distanceTo(Vec3.atCenterOf(pBlockEntity.getBlockPos()));
        double distance = actualDistance > 10 ? 0 : actualDistance;
        double distanceFactor = Math.max(0, Math.max(0, distance / 10.0));
        if (pBlockEntity.requirementBool) {
            drawRequirements(ps, pBufferSource, pBlockEntity, distanceFactor, FastColor.ARGB32.multiply(0xFFFFFFFF, FastColor.ARGB32.color(Math.max(0x04, Math.round((1.0f - (float) actualDistance / 10.0f) * 255.0f)), 255, 255, 255)));
        }
        if(pBlockEntity.star != null){
            drawStar(ps, pBufferSource, pBlockEntity, distanceFactor, FastColor.ARGB32.multiply(0xFFFFFFFF, FastColor.ARGB32.color(Math.max(0x04, Math.round((1.0f - (float) actualDistance / 10.0f) * 255.0f)), 255, 255, 255)));
        }
        if(!pBlockEntity.inventory.isEmpty()){
            drawTiers(ps, pBufferSource, pBlockEntity, distanceFactor, scale, speed, fac);
        }
        ps.popPose();
    }
    private static void drawLineBetween(MultiBufferSource buffer, PoseStack mstack, Vec3 local, Vec3 target, float lineWidth, int r, int g, int b, int a) {
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
    private void drawCurrent(MultiBufferSource buffer, PoseStack ps, ArmillarySphereCoreBlockEntity blockEntity, double distanceFactor, int color) {
        ps.pushPose();
        ps.translate(0.5, 1.5, 0.5);
        ps.mulPose(Vector3f.XP.rotation(135));
        Vec3 player = Minecraft.getInstance().player.getEyePosition();
        Vec3 center = new Vec3(blockEntity.getBlockPos().getX() + 0.5, blockEntity.getBlockPos().getY() + 1.5, blockEntity.getBlockPos().getZ() + 0.5);

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
        ps.translate(0,0,-0.6);
        ps.scale(0.01f * (float)Math.max(0.7, distanceFactor), 0.01f *  (float)Math.max(0.7, distanceFactor), 0.01f *  (float)Math.max(0.7, distanceFactor));
        //ps.mulPose(Vector3f.ZP.rotationDegrees(180));
        ps.translate(0, -font.lineHeight * blockEntity.getAspectiInstances().size() / 2.0f, 0);
        for (Component requirement : blockEntity.pairToComponent(blockEntity.getMatchFromInventory())) {
            ps.translate(-font.width(requirement) / 2.0f, 0, 0);
            font.draw(ps, requirement, 0, 0, color);
            ps.translate(font.width(requirement) / 2.0f, 0, 0);
            ps.translate(0, font.lineHeight, 0);
        }
        ps.popPose();
    }

    private void drawRequirements(PoseStack ps, MultiBufferSource buffer, ArmillarySphereCoreBlockEntity blockEntity, double distanceFactor, int color){
        drawCurrent(buffer, ps, blockEntity, distanceFactor, color);
        ps.pushPose();
        ps.translate(0.5, 0.35, 0.5);
        ps.mulPose(Vector3f.XP.rotation(135));
        Vec3 player = Minecraft.getInstance().player.getEyePosition();
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
        ps.translate(0,0,-0.5);
        ps.scale(0.015f * (float)Math.max(0.7, distanceFactor), 0.015f *  (float)Math.max(0.7, distanceFactor), 0.015f *  (float)Math.max(0.7, distanceFactor));
        //ps.mulPose(Vector3f.ZP.rotationDegrees(180));
        ps.translate(0, -font.lineHeight * blockEntity.getAspectiInstances().size() / 2.0f, 0);
        for (Component requirement : blockEntity.getAspectiInstances()) {
            ps.translate(-font.width(requirement) / 2.0f, 0, 0);
            font.draw(ps, requirement, 0, 0, color);
            ps.translate(font.width(requirement) / 2.0f, 0, 0);
            ps.translate(0, font.lineHeight, 0);
        }
        ps.popPose();
    }

    private void drawStar(PoseStack ps, MultiBufferSource buffer, ArmillarySphereCoreBlockEntity blockEntity, double distanceFactor, int color){
        ps.pushPose();
        ps.translate(0.5, 0.05, 0.5);
        ps.mulPose(Vector3f.XP.rotation(135));
        Vec3 player = Minecraft.getInstance().player.getEyePosition();
        Vec3 center = new Vec3(blockEntity.getBlockPos().getX() + 0.5, blockEntity.getBlockPos().getY() + 1.0f, blockEntity.getBlockPos().getZ() + 0.5);

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
        ps.translate(0,0,-0.6);
        ps.scale(0.015f * (float)Math.max(0.7, distanceFactor), 0.015f *  (float)Math.max(0.7, distanceFactor), 0.015f *  (float)Math.max(0.7, distanceFactor));
        //ps.mulPose(Vector3f.ZP.rotationDegrees(180));
        ps.translate(0, -font.lineHeight * blockEntity.getAspectiInstances().size() / 2.0f, 0);
        for (String requirement : blockEntity.star.getString()) {
            ps.translate(-font.width(requirement) / 2.0f, 0, 0);
            font.draw(ps, requirement, 0, 0, color);
            ps.translate(font.width(requirement) / 2.0f, 0, 0);
            ps.translate(0, font.lineHeight, 0);
        }
        ps.popPose();
    }

    private void drawTiers(PoseStack ps, MultiBufferSource buffer, ArmillarySphereCoreBlockEntity blockEntity, double distanceFactor, float scale, float speed, float fac){
        ps.pushPose();
        ps.translate(0.5, 1.5, 0.5);
        ps.scale(scale, scale, scale);
        ps.scale(0.5f, 0.5f, 0.5f);
        ps.mulPose(Vector3f.XN.rotationDegrees(fac * speed));
        ps.mulPose(Vector3f.YP.rotationDegrees(fac * speed));
        for (int i = 0; i < 4; i++) {
            if (blockEntity.inventory.items.get(i) != ItemStack.EMPTY.getItem() || blockEntity.toggled) {
                ps.pushPose();
                drawLineBetween(buffer, ps, new Vec3(offsets[i]), new Vec3(offsets[(i + 1) % 4]), 0.01f, 255, 255, 255, 255);
                ps.translate(offsets[i].x(), offsets[i].y(), offsets[i].z());
                ps.mulPose(Vector3f.YN.rotationDegrees(fac));
                ps.translate(0, -0.175, 0);
                Minecraft.getInstance().getItemRenderer().renderStatic(blockEntity.inventory.getStacks().get(i), ItemTransforms.TransformType.GROUND, 15728640, OverlayTexture.NO_OVERLAY, ps, buffer, 1);
                ps.popPose();
            }
        }
        ps.mulPose(Vector3f.ZP.rotationDegrees(90));
        ps.mulPose(Vector3f.ZP.rotationDegrees(fac * 1.3f * speed));
        for (int i = 4; i < 8; i++) {
            if (blockEntity.inventory.items.get(i) != ItemStack.EMPTY.getItem() || blockEntity.toggled) {
                ps.pushPose();
                drawLineBetween(buffer, ps, new Vec3(offsets2[i - 4]), new Vec3(offsets2[(i - 4 + 1) % 4]), 0.01f, 255, 255, 255, 255);
                ps.translate(offsets2[i - 4].x(), offsets2[i - 4].y(), offsets2[i - 4].z());
                ps.mulPose(Vector3f.ZN.rotationDegrees(fac * 1.3f));
                ps.scale(0.8f, 0.8f, 0.8f);
                ps.translate(0, -0.175, 0);
                Minecraft.getInstance().getItemRenderer().renderStatic(blockEntity.inventory.getStacks().get(i), ItemTransforms.TransformType.GROUND, 15728640, OverlayTexture.NO_OVERLAY, ps, buffer, 1);
                ps.popPose();
            }
        }
        ps.mulPose(Vector3f.XP.rotationDegrees(90));
        ps.mulPose(Vector3f.XP.rotationDegrees(fac * 1.5f * speed));
        for (int i = 8; i < 12; i++) {
            if (blockEntity.inventory.items.get(i) != ItemStack.EMPTY.getItem() || blockEntity.toggled) {
                ps.pushPose();
                drawLineBetween(buffer, ps, new Vec3(offsets3[i - 8]), new Vec3(offsets3[(i - 8 + 1) % 4]), 0.01f, 255, 255, 255, 255);
                ps.translate(offsets3[i - 8].x(), offsets3[i - 8].y(), offsets3[i - 8].z());
                ps.mulPose(Vector3f.XN.rotationDegrees(fac * 1.5f));
                ps.scale(0.8f, 0.8f, 0.8f);
                ps.translate(0, -0.175, 0);
                Minecraft.getInstance().getItemRenderer().renderStatic(blockEntity.inventory.getStacks().get(i), ItemTransforms.TransformType.GROUND, 15728640, OverlayTexture.NO_OVERLAY, ps, buffer, 1);
                ps.popPose();
            }
        }
        ps.popPose();
    }
}
