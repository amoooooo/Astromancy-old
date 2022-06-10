package coffee.amo.astromancy.core.registration;

import coffee.amo.astromancy.Astromancy;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.sammy.ortus.setup.OrtusRenderTypeRegistry;
import com.sammy.ortus.setup.OrtusShaderRegistry;
import com.sammy.ortus.systems.rendering.StateShards;
import net.minecraft.Util;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterShadersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.io.IOException;
import java.util.function.Function;

import static com.mojang.blaze3d.vertex.DefaultVertexFormat.*;
import static com.sammy.ortus.OrtusLib.ORTUS;

public class RenderTypeRegistry {
    public static RenderType astrolabeStarfield(ResourceLocation texture){
        return AstromancyRenderTypes.ASTROLABE_STARFIELD.apply(texture);
    }

    @Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Astromancy.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ShaderRegistryEvent{
        @SubscribeEvent
        public static void shaderRegistry(RegisterShadersEvent event) throws IOException {
            event.registerShader(new ShaderInstance(event.getResourceManager(), Astromancy.astromancy("rendertype_astrolabe_starfield"), POSITION_COLOR_TEX), shaderInstance -> {
                AstromancyRenderTypes.astrolabeStarfield = shaderInstance;
            });
        }
    }

    private static class AstromancyRenderTypes extends RenderType {

        private static ShaderInstance astrolabeStarfield;

        private static final ShaderStateShard RENDERTYPE_ASTROLABE_STARFIELD = new ShaderStateShard(() -> astrolabeStarfield);

        private AstromancyRenderTypes(String s, VertexFormat v, VertexFormat.Mode m, int i, boolean b, boolean b2, Runnable r, Runnable r2) {
            super(s, v, m, i, b, b2, r, r2);
            throw new IllegalStateException("This class is not meant to be constructed!");
        }

        public static Function<ResourceLocation, RenderType> ASTROLABE_STARFIELD = Util.memoize(AstromancyRenderTypes::astrolabeStarfield);

        private static RenderType astrolabeStarfield(ResourceLocation texture) {
            RenderType.CompositeState rendertype$state = RenderType.CompositeState.builder()
                    .setShaderState(RENDERTYPE_ASTROLABE_STARFIELD)
                    .setTextureState(new RenderStateShard.TextureStateShard(texture, false, false))
                    .setTransparencyState(NO_TRANSPARENCY)
                    .setLightmapState(NO_LIGHTMAP)
                    .setOverlayState(NO_OVERLAY)
                    .createCompositeState(true);
            return create("astrolabe_starfield", DefaultVertexFormat.POSITION_COLOR_TEX, VertexFormat.Mode.QUADS, 256, true, false, rendertype$state);
        }
    }
}
