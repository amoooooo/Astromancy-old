package coffee.amo.astromancy.core.registration;

import coffee.amo.astromancy.Astromancy;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.sammy.ortus.systems.rendering.ExtendedShaderInstance;
import com.sammy.ortus.systems.rendering.ShaderHolder;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterShadersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.io.IOException;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Astromancy.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ShaderRegistry {
    public static ShaderHolder SUN = new ShaderHolder();

    @SubscribeEvent
    public static void shaderRegistry(RegisterShadersEvent event) throws IOException {
        registerShader(event, ExtendedShaderInstance.createShaderInstance(SUN, event.getResourceManager(), Astromancy.astromancy("sun"), DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP));
    }
    public static void registerShader(RegisterShadersEvent event, ExtendedShaderInstance extendedShaderInstance) {
        event.registerShader(extendedShaderInstance, s -> ((ExtendedShaderInstance) s).getHolder().setInstance(((ExtendedShaderInstance) s)));
    }
}
