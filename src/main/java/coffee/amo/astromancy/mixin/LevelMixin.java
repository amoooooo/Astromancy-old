package coffee.amo.astromancy.mixin;

import coffee.amo.astromancy.core.handlers.SolarEclipseHandler;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Level.class)
public class LevelMixin {

    @Shadow
    private int skyDarken;

    @Inject(at = @At("RETURN"), method = "updateSkyBrightness")
    public void updateSkyBrightnessForEclipse(CallbackInfo ci){
        Level world = (Level) (Object) this;
        if(world.getServer() == null) return;
        if(world.isClientSide) return;
        if(world instanceof ServerLevel se){
            /*
            * This has to be the Overworld.
            */
            if(se.getServer().getLevel(Level.OVERWORLD) == null) return;
            if(SolarEclipseHandler.isEnabled(se) && world.dimension().equals(Level.OVERWORLD)){
                skyDarken = SolarEclipseHandler.getSkyDarken();
            }
        } else {
            if (SolarEclipseHandler.solarEclipseEnabledClient && world.dimension().equals(Level.OVERWORLD)){
                skyDarken = SolarEclipseHandler.getSkyDarken();
            }
        }
    }
}
