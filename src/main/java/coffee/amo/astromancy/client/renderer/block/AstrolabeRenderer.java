package coffee.amo.astromancy.client.renderer.block;

import coffee.amo.astromancy.common.blockentity.AstrolabeBlockEntity;
import coffee.amo.astromancy.core.helpers.RenderHelper;
import com.mojang.blaze3d.vertex.PoseStack;
import com.sammy.ortus.helpers.util.Color;
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
        ps.translate(0, 1.5,0);
        //RenderHelper.renderInvertedCube(ps, pBufferSource, 1, RenderType.solid(), Color.WHITE);
        ps.popPose();
    }
}
