package coffee.amo.astromancy.mixin.client;

import coffee.amo.astromancy.core.handlers.SolarEclipseHandler;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientLevel.class)
public class ClientLevelMixin {

    @Inject(at = @At("RETURN"), method = "getSkyDarken", cancellable = true)
    public void getSkyDarkenForEclipse(float pticks, CallbackInfoReturnable<Float> cir) {
        if (SolarEclipseHandler.solarEclipseEnabledClient && ((ClientLevel) (Object) this).dimension().equals(Level.OVERWORLD)) {
            cir.setReturnValue(SolarEclipseHandler.getSkyDarkenClient(cir.getReturnValueF()));
        }
    }

    @Inject(at = @At("RETURN"), method = "getCloudColor", cancellable = true)
    public void getCloudColorForEclipse(float pticks, CallbackInfoReturnable<Vec3> cir) {
        if (SolarEclipseHandler.solarEclipseEnabledClient && ((ClientLevel) (Object) this).dimension().equals(Level.OVERWORLD)) {
            cir.setReturnValue(SolarEclipseHandler.getCloudColor(cir.getReturnValue()));
        }
    }

    @Inject(at = @At("RETURN"), method = "getSkyColor", cancellable = true)
    public void getSkyColorForEclipse(Vec3 vec3, float f, CallbackInfoReturnable<Vec3> cir) {
        if (SolarEclipseHandler.solarEclipseEnabledClient && ((ClientLevel) (Object) this).dimension().equals(Level.OVERWORLD)) {
            cir.setReturnValue(SolarEclipseHandler.getDaySkyColor(cir.getReturnValue()));
        }
    }
}
