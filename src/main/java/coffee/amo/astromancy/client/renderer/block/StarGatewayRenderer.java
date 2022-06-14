package coffee.amo.astromancy.client.renderer.block;

import coffee.amo.astromancy.Astromancy;
import coffee.amo.astromancy.client.systems.ClientConstellationHolder;
import coffee.amo.astromancy.common.blockentity.StarGatewayBlockEntity;
import coffee.amo.astromancy.core.helpers.RenderHelper;
import coffee.amo.astromancy.core.registration.RenderTypeRegistry;
import coffee.amo.astromancy.core.systems.stars.Star;
import coffee.amo.astromancy.core.systems.stars.classification.ConstellationInstance;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

public class StarGatewayRenderer implements BlockEntityRenderer<StarGatewayBlockEntity> {
    private static final ResourceLocation sunRL = Astromancy.astromancy("textures/vfx/white.png");
    private static final RenderType sunType = RenderTypeRegistry.astrolabeStarfield(sunRL);
    private final Font font;

    public StarGatewayRenderer(BlockEntityRendererProvider.Context context) {
        this.font = context.getFont();
    }

    @Override
    public void render(StarGatewayBlockEntity pBlockEntity, float pPartialTick, PoseStack ps, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        ps.pushPose();
        ps.translate(0.07f, 1.01f, 0.07f);
        RenderHelper.renderInvertedCube(ps, pBufferSource, 4.85f, sunType);
        VertexConsumer starBuff = pBufferSource.getBuffer(RenderType.solid());
        ps.translate(0, 1.55, 0);
        if (!ClientConstellationHolder.constellationInstances.isEmpty()) {
            for (ConstellationInstance cInst : ClientConstellationHolder.getConstellationInstances()) {
                for (Star[] star : cInst.getStars()) {
                    for (Star star1 : star) {
                        if(star != null){
                            RenderHelper.renderStar(ps, 0.035f, pBufferSource, star1, pBlockEntity, pPartialTick, font, true);
                        }
                    }
                }
            }
        }
        ps.popPose();
    }

    @Override
    public boolean shouldRenderOffScreen(StarGatewayBlockEntity pBlockEntity) {
        return true;
    }

    @Override
    public boolean shouldRender(StarGatewayBlockEntity pBlockEntity, Vec3 pCameraPos) {
        return BlockEntityRenderer.super.shouldRender(pBlockEntity, pCameraPos);
    }
}
