package coffee.amo.magic.client.renderer.block;

import coffee.amo.magic.Magic;
import coffee.amo.magic.common.blockentity.ArmillarySphereBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import com.sammy.ortus.setup.OrtusRenderTypeRegistry;
import com.sammy.ortus.systems.rendering.VFXBuilders;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.List;

import static com.sammy.ortus.handlers.RenderHandler.DELAYED_RENDER;

public class ArmillarySphereRenderer implements BlockEntityRenderer<ArmillarySphereBlockEntity> {

    private final static ResourceLocation BEAM = Magic.magicMod("textures/vfx/light_trail.png");
    private final static RenderType BEAM_TYPE = OrtusRenderTypeRegistry.ADDITIVE_TEXTURE.apply(BEAM);
    private final Vector3f[] offsets = new Vector3f[]{
            new Vector3f(1, 0, -1),
            new Vector3f(1, 0, 1),
            new Vector3f(-1, 0, 1),
            new Vector3f(-1, 0, -1)
    };
    public ArmillarySphereRenderer(BlockEntityRendererProvider.Context context) {

    }

    // TODO: fix line/do cube
    @Override
    public void render(ArmillarySphereBlockEntity pBlockEntity, float pPartialTick, PoseStack ps, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        ps.pushPose();
        float fac = (pBlockEntity.getLevel().getGameTime() + pPartialTick) * 2 + 2;
        VertexConsumer consumer = DELAYED_RENDER.getBuffer(BEAM_TYPE);
        VertexConsumer lineConsumer = DELAYED_RENDER.getBuffer(RenderType.LINES);
        ps.translate(0.5,2.5,0.5);
        VFXBuilders.createWorld().setPosColorTexLightmapDefaultFormat().setColor(Color.WHITE).renderSphere(consumer, ps, 0.1f, 32, 32);
        ps.mulPose(Vector3f.YP.rotationDegrees(fac));
        for(int i = 0; i < pBlockEntity.inventory.items.size(); i++) {
            if(pBlockEntity.inventory.items.get(i).getDefaultInstance() != ItemStack.EMPTY) {
                ps.translate(offsets[i].x(), offsets[i].y(), offsets[i].z());
                VFXBuilders.createWorld().setPosColorTexLightmapDefaultFormat().setColor(Color.WHITE).renderBeam(consumer, ps, new Vec3(offsets[i].x(), offsets[i].y(), offsets[i].z()), new Vec3(offsets[(i+1)%4].x(), offsets[(i+1)%4].y(), offsets[(i+1)%4].z()), 0.1f);
                Minecraft.getInstance().getItemRenderer().renderStatic(pBlockEntity.inventory.items.get(i).getDefaultInstance(), ItemTransforms.TransformType.GROUND, 15728640, OverlayTexture.NO_OVERLAY, ps, pBufferSource, 1);
                ps.translate(-offsets[i].x(), -offsets[i].y(), -offsets[i].z());
            }
        }
        ps.mulPose(Vector3f.ZP.rotationDegrees(90));
        ps.mulPose(Vector3f.ZP.rotationDegrees(fac * 1.3f));
        for(int i = 0; i < pBlockEntity.ring2.items.size(); i++){
            if(pBlockEntity.ring2.items.get(i).getDefaultInstance() != ItemStack.EMPTY) {
                ps.translate(offsets[i].x(), offsets[i].y(), offsets[i].z());
                //VFXBuilders.createWorld().setPosColorTexLightmapDefaultFormat().setColor(Color.WHITE).renderBeam(consumer, ps, new Vec3(offsets[i].x(), offsets[i].y(), offsets[i].z()), new Vec3(offsets[(i+1)%4].x(), offsets[(i+1)%4].y(), offsets[(i+1)%4].z()), 0.1f);
                lineConsumer.vertex(offsets[i].x(), offsets[i].y(), offsets[i].z()).color(0f, 0.8f, 1f, 1f).endVertex();
                lineConsumer.vertex(offsets[(i+1)%4].x(), offsets[(i+1)%4].y(), offsets[(i+1)%4].z()).color(0f, 0.8f, 1f, 1f).endVertex();
                Minecraft.getInstance().getItemRenderer().renderStatic(pBlockEntity.ring2.items.get(i).getDefaultInstance(), ItemTransforms.TransformType.GROUND, 15728640, OverlayTexture.NO_OVERLAY, ps, pBufferSource, 1);
                ps.translate(-offsets[i].x(), -offsets[i].y(), -offsets[i].z());
            }
        }
        ps.mulPose(Vector3f.XP.rotationDegrees(90));
        ps.mulPose(Vector3f.XP.rotationDegrees(fac * 1.5f));
        for(int i = 0; i < pBlockEntity.ring3.items.size(); i++){
            if(pBlockEntity.ring3.items.get(i).getDefaultInstance() != ItemStack.EMPTY) {
                ps.translate(offsets[i].x(), offsets[i].y(), offsets[i].z());
                VFXBuilders.createWorld().setPosColorTexLightmapDefaultFormat().setColor(Color.WHITE).renderBeam(consumer, ps, new Vec3(offsets[i].x(), offsets[i].y(), offsets[i].z()), new Vec3(offsets[(i+1)%4].x(), offsets[(i+1)%4].y(), offsets[(i+1)%4].z()), 0.1f);
                Minecraft.getInstance().getItemRenderer().renderStatic(pBlockEntity.ring3.items.get(i).getDefaultInstance(), ItemTransforms.TransformType.GROUND, 15728640, OverlayTexture.NO_OVERLAY, ps, pBufferSource, 1);
                ps.translate(-offsets[i].x(), -offsets[i].y(), -offsets[i].z());
            }
        }
        ps.popPose();
    }
}
