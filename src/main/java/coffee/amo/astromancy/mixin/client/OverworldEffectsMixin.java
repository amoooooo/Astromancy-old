package coffee.amo.astromancy.mixin.client;

import coffee.amo.astromancy.core.handlers.SolarEclipseHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DimensionSpecialEffects.OverworldEffects.class)
public class OverworldEffectsMixin {

    @Inject(at = @At("RETURN"), method = "getBrightnessDependentFogColor", cancellable = true)
    public void getBrightnessDependentFogColorForEclipse(Vec3 color, float brightness, CallbackInfoReturnable<Vec3> cir){
        if(SolarEclipseHandler.solarEclipseEnabledClient){
            float mult = ((Minecraft.getInstance().level.getDayTime() / 1000f) / 3)/8;
            cir.setReturnValue(SolarEclipseHandler.getOverworldFogColor(cir.getReturnValue()).scale(mult));
        }
    }
}
