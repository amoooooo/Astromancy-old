package coffee.amo.astromancy.client.renderer.block;

import coffee.amo.astromancy.common.blockentity.mortar.MortarBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

public class MortarRenderer implements BlockEntityRenderer<MortarBlockEntity> {
    public MortarRenderer(BlockEntityRendererProvider.Context context) {
    }
    @Override
    public void render(MortarBlockEntity pBlockEntity, float pPartialTick, PoseStack ps, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        ps.pushPose();
        ps.translate(0.5,0.1,0.5);
        ps.scale(0.4f, 0.4f, 0.4f);
        ps.mulPose(Vector3f.YP.rotationDegrees(((pBlockEntity.spinning > 0 ? 1 : 0)* pPartialTick) * 360));
        if(!pBlockEntity.inventory.isEmpty()){
            for(int i = 0; i < pBlockEntity.inventory.nonEmptyItemAmount; i++){
                ps.mulPose(Vector3f.YP.rotationDegrees((i/5f)*360));
                ps.translate(0.1,0,0);
                ps.mulPose(Vector3f.XP.rotationDegrees(40));
                ps.translate(i / 10f,0,0);
                Minecraft.getInstance().getItemRenderer().renderStatic(pBlockEntity.inventory.getStacks().get(i), ItemTransforms.TransformType.GROUND, pPackedLight, pPackedOverlay, ps, pBufferSource, 1);
            }
        }
        ps.popPose();
    }
}
