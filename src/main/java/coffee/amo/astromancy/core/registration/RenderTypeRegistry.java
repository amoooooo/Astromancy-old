package coffee.amo.astromancy.core.registration;

import coffee.amo.astromancy.Astromancy;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.sammy.ortus.setup.OrtusRenderTypeRegistry;
import com.sammy.ortus.systems.rendering.StateShards;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlas;

import static com.mojang.blaze3d.vertex.DefaultVertexFormat.*;

public class RenderTypeRegistry extends OrtusRenderTypeRegistry{
    public static final RenderType SUN = OrtusRenderTypeRegistry.createGenericRenderType(Astromancy.MODID, "sun", BLOCK, VertexFormat.Mode.QUADS, ShaderRegistry.SUN.shard, StateShards.ADDITIVE_TRANSPARENCY, TextureAtlas.LOCATION_PARTICLES);

    public RenderTypeRegistry(String p_110161_, Runnable p_110162_, Runnable p_110163_) {
        super(p_110161_, p_110162_, p_110163_);
    }
}
