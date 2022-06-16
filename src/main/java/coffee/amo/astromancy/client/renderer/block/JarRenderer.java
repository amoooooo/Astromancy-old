package coffee.amo.astromancy.client.renderer.block;

import coffee.amo.astromancy.common.blockentity.jar.JarBlockEntity;
import coffee.amo.astromancy.core.helpers.RenderHelper;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

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
        if(pBlockEntity.getAspecti() != null){
            RenderHelper.renderText(ps, pBlockEntity.getAspecti().name() + " " + pBlockEntity.getCount(), pBufferSource, font);
        }
        ps.popPose();
    }
}
