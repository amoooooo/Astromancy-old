package coffee.amo.astromancy.mixin.client;

import coffee.amo.astromancy.Astromancy;
import coffee.amo.astromancy.core.handlers.SolarEclipseHandler;
import com.mojang.blaze3d.pipeline.RenderCall;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

import static com.mojang.blaze3d.systems.RenderSystem.*;

@Mixin(RenderSystem.class)
public abstract class RenderSystemMixin {

    private static final ResourceLocation SUN = Astromancy.astromancy("textures/environment/sun.png");

    @Inject(at = @At("HEAD"), method = "setShaderTexture(ILnet/minecraft/resources/ResourceLocation;)V", cancellable = true)
    private static void setShaderTextureForEclipse(int i, ResourceLocation texture, CallbackInfo ci){
        if(SolarEclipseHandler.solarEclipseEnabledClient && Objects.equals(texture, new ResourceLocation("textures/environment/sun.png"))) {
            if (!isOnRenderThread()){
                recordRenderCall(() -> {
                    _setShaderTexture(i, SUN);
                });
            } else {
                _setShaderTexture(i, SUN);
            }
            ci.cancel();
        }
    }
}
