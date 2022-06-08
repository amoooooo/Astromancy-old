package coffee.amo.astromancy.client.renderer.block;

import coffee.amo.astromancy.Astromancy;
import coffee.amo.astromancy.common.blockentity.ArmillarySphereBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import com.sammy.ortus.setup.OrtusRenderTypeRegistry;
import com.sammy.ortus.systems.rendering.VFXBuilders;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

import java.awt.*;

import static com.mojang.blaze3d.vertex.DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP;
import static com.sammy.ortus.handlers.RenderHandler.DELAYED_RENDER;

public class ArmillarySphereRenderer implements BlockEntityRenderer<ArmillarySphereBlockEntity> {

    private final static ResourceLocation BEAM = Astromancy.astromancy("textures/vfx/light_trail.png");
    private final static RenderType BEAM_TYPE = OrtusRenderTypeRegistry.ADDITIVE_TEXTURE.apply(BEAM);

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

    }

    // TODO: fix line/do cube
    @Override
    public void render(ArmillarySphereBlockEntity pBlockEntity, float pPartialTick, PoseStack ps, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        ps.pushPose();
        float fac = (((pBlockEntity.getLevel().getGameTime() + pPartialTick) * 2 + 2) * (pBlockEntity.toggled ? 1 : 0));// * ((float)pBlockEntity.ticksActive/600);
        VertexConsumer consumer = DELAYED_RENDER.getBuffer(BEAM_TYPE);
        ps.translate(0.5,1.5,0.5);
        ps.scale(0.5f,0.5f,0.5f);
        ps.mulPose(Vector3f.XN.rotationDegrees(fac));
        ps.mulPose(Vector3f.YP.rotationDegrees(fac));
        for(int i = 0; i < 4; i++) {
            if(pBlockEntity.inventory.items.get(i) != ItemStack.EMPTY.getItem()) {
                ps.pushPose();
                drawLineBetween(pBufferSource, ps, new Vec3(offsets[i]), new Vec3(offsets[(i+1)%4]), 0.01f, 255, 255, 255, 255);
                ps.translate(offsets[i].x(), offsets[i].y(), offsets[i].z());
                ps.mulPose(Vector3f.YN.rotationDegrees(fac));
                Minecraft.getInstance().getItemRenderer().renderStatic(pBlockEntity.inventory.items.get(i).getDefaultInstance(), ItemTransforms.TransformType.GROUND, 15728640, OverlayTexture.NO_OVERLAY, ps, pBufferSource, 1);
                ps.popPose();
            }
        }
        ps.mulPose(Vector3f.ZP.rotationDegrees(90));
        ps.mulPose(Vector3f.ZP.rotationDegrees(fac * 1.3f));
        for(int i = 4; i < 8; i++){
            if(pBlockEntity.inventory.items.get(i) != ItemStack.EMPTY.getItem()) {
                ps.pushPose();
                drawLineBetween(pBufferSource, ps, new Vec3(offsets2[i-4]), new Vec3(offsets2[(i-4+1)%4]), 0.01f, 255, 255, 255, 255);
                ps.translate(offsets2[i-4].x(), offsets2[i-4].y(), offsets2[i-4].z());
                ps.mulPose(Vector3f.ZN.rotationDegrees(fac * 1.3f));
                ps.scale(0.8f,0.8f,0.8f);
                Minecraft.getInstance().getItemRenderer().renderStatic(pBlockEntity.inventory.items.get(i).getDefaultInstance(), ItemTransforms.TransformType.GROUND, 15728640, OverlayTexture.NO_OVERLAY, ps, pBufferSource, 1);
                ps.popPose();
            }
        }
        ps.mulPose(Vector3f.XP.rotationDegrees(90));
        ps.mulPose(Vector3f.XP.rotationDegrees(fac * 1.5f));
        for(int i = 8; i < 12; i++){
            if(pBlockEntity.inventory.items.get(i) != ItemStack.EMPTY.getItem()) {
                ps.pushPose();
                drawLineBetween(pBufferSource, ps, new Vec3(offsets3[i-8]), new Vec3(offsets3[(i-8+1)%4]), 0.01f, 255, 255, 255, 255);
                ps.translate(offsets3[i-8].x(), offsets3[i-8].y(), offsets3[i-8].z());
                ps.mulPose(Vector3f.XN.rotationDegrees(fac * 1.5f));
                ps.scale(0.8f,0.8f,0.8f);
                Minecraft.getInstance().getItemRenderer().renderStatic(pBlockEntity.inventory.items.get(i).getDefaultInstance(), ItemTransforms.TransformType.GROUND, 15728640, OverlayTexture.NO_OVERLAY, ps, pBufferSource, 1);
                ps.popPose();
            }
        }
        ps.popPose();
    }

    private static void drawLineBetween(MultiBufferSource buffer, PoseStack mstack, Vec3 local, Vec3 target, float lineWidth, int r, int g, int b, int a)
    {
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
        builder.vertex(matrix, -halfWidth, 0,        0).color(r, g, b, a).uv2(0xF000F0).endVertex();
        builder.vertex(matrix,  halfWidth, 0,        0).color(r, g, b, a).uv2(0xF000F0).endVertex();
        builder.vertex(matrix,  halfWidth, 0, distance).color(r, g, b, a).uv2(0xF000F0).endVertex();
        builder.vertex(matrix, -halfWidth, 0, distance).color(r, g, b, a).uv2(0xF000F0).endVertex();

        //Draw vertical Quad
        builder.vertex(matrix, 0, -halfWidth,        0).color(r, g, b, a).uv2(0xF000F0).endVertex();
        builder.vertex(matrix, 0,  halfWidth,        0).color(r, g, b, a).uv2(0xF000F0).endVertex();
        builder.vertex(matrix, 0,  halfWidth, distance).color(r, g, b, a).uv2(0xF000F0).endVertex();
        builder.vertex(matrix, 0, -halfWidth, distance).color(r, g, b, a).uv2(0xF000F0).endVertex();

        mstack.popPose();
    }
}
