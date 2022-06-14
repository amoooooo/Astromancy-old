package coffee.amo.astromancy.client.renderer.block;

import coffee.amo.astromancy.common.blockentity.AstrolabeBlockEntity;
import coffee.amo.astromancy.core.helpers.RenderHelper;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;


public class AstrolabeRenderer implements BlockEntityRenderer<AstrolabeBlockEntity> {
    private final Font font;
    public AstrolabeRenderer(BlockEntityRendererProvider.Context context) {
        this.font = context.getFont();
    }

    // TODO: create a black box around the astrolabe, and draw a star in the area depending on stars discovered
    @Override
    public void render(AstrolabeBlockEntity pBlockEntity, float pPartialTick, PoseStack ps, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        ps.pushPose();
        ps.translate(0, 1.5, 0);
        if(pBlockEntity.star != null){
            RenderHelper.renderStar(ps, 0.3f, pBufferSource, pBlockEntity.star, pBlockEntity, pPartialTick, font, false);
        }
        //ps.scale(1,1,0);
        ps.popPose();
    }
}
