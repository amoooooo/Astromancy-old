package coffee.amo.astromancy.client.renderer.block;

import coffee.amo.astromancy.Astromancy;
import coffee.amo.astromancy.common.blockentity.AstrolabeBlockEntity;
import coffee.amo.astromancy.core.helpers.RenderHelper;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.sammy.ortus.helpers.util.Color;
import com.sammy.ortus.setup.OrtusRenderTypeRegistry;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;


public class AstrolabeRenderer implements BlockEntityRenderer<AstrolabeBlockEntity> {

    public AstrolabeRenderer(BlockEntityRendererProvider.Context context) {
    }

    // TODO: create a black box around the astrolabe, and draw a star in the area depending on stars discovered
    @Override
    public void render(AstrolabeBlockEntity pBlockEntity, float pPartialTick, PoseStack ps, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        ps.pushPose();
        float fac = pBlockEntity.getLevel().getGameTime() + pPartialTick + (5 * 1);
        ps.translate(-0.2, 1.5,0);
        ps.mulPose(Vector3f.XP.rotationDegrees(45));
        ps.mulPose(Vector3f.YP.rotationDegrees(45));
//        RenderHelper.renderInvertedCube(ps, pBufferSource, 1, OrtusRenderTypeRegistry.TRANSPARENT_TEXTURE.apply(Astromancy.astromancy("textures/vfx/white.png")), new Color(173, 142, 245));
//        ps.translate(0.04,0.04,0.04);
//        RenderHelper.renderInvertedCube(ps, pBufferSource, 0.9f, OrtusRenderTypeRegistry.TRANSPARENT_TEXTURE.apply(Astromancy.astromancy("textures/vfx/white.png")), new Color(71,35,153));
//        ps.translate(0.04,0.04,0.04);
//        RenderHelper.renderInvertedCube(ps, pBufferSource, 0.8f, OrtusRenderTypeRegistry.TRANSPARENT_TEXTURE.apply(Astromancy.astromancy("textures/vfx/white.png")), new Color(122,70,242));
        ps.popPose();
    }
}
