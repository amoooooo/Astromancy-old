package coffee.amo.astromancy.core.registration;

import net.minecraft.world.level.block.SoundType;
import net.minecraftforge.common.util.ForgeSoundType;

public class SoundTypes {
    //break, step, place, hit, fall
    public static final SoundType JAR = new ForgeSoundType(1, 1, SoundRegistry.JAR_BREAK, SoundRegistry.JAR_HIT, SoundRegistry.JAR_PLACE, SoundRegistry.JAR_HIT, SoundRegistry.JAR_HIT);
}
