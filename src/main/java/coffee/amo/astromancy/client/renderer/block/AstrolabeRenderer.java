package coffee.amo.astromancy.client.renderer.block;

import coffee.amo.astromancy.common.blockentity.AstrolabeBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.sammy.ortus.systems.rendering.VFXBuilders;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;

import java.awt.*;

public class AstrolabeRenderer implements BlockEntityRenderer<AstrolabeBlockEntity> {

    // TODO: create a black box around the astrolabe, and draw a star in the area depending on stars discovered
    @Override
    public void render(AstrolabeBlockEntity pBlockEntity, float pPartialTick, PoseStack ps, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        ps.pushPose();
        ps.translate(1, 0,0);
        //VFXBuilders.createWorld().setPosColorTexLightmapDefaultFormat().setColor(Color.BLACK).
        ps.popPose();
    }
}
