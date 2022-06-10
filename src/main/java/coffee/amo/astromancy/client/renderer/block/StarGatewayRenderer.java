package coffee.amo.astromancy.client.renderer.block;

import coffee.amo.astromancy.Astromancy;
import coffee.amo.astromancy.common.blockentity.StarGatewayBlockEntity;
import coffee.amo.astromancy.core.registration.RenderTypeRegistry;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.sammy.ortus.systems.rendering.VFXBuilders;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

import java.awt.*;

import static com.sammy.ortus.handlers.RenderHandler.DELAYED_RENDER;

public class StarGatewayRenderer implements BlockEntityRenderer<StarGatewayBlockEntity> {
    private static final ResourceLocation sunRL = Astromancy.astromancy("textures/vfx/white.png");
    private static final RenderType sunType = RenderTypeRegistry.astrolabeStarfield(sunRL);

    public StarGatewayRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(StarGatewayBlockEntity pBlockEntity, float pPartialTick, PoseStack ps, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        ps.pushPose();
        ps.translate(0,1.5,0);
        VertexConsumer buff = pBufferSource.getBuffer(sunType);
        buff.vertex(ps.last().pose(), 0, 1, 0).color(0xFFFF55FF).uv(0,1).endVertex();
        buff.vertex(ps.last().pose(), 1, 1, 0).color(0xFFFF55FF).uv(1,1).endVertex();
        buff.vertex(ps.last().pose(), 1, 0, 0).color(0xFFFF55FF).uv(1,0).endVertex();
        buff.vertex(ps.last().pose(), 0, 0, 0).color(0xFFFF55FF).uv(0,0).endVertex();
        ps.popPose();
    }
}
