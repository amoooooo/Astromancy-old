package coffee.amo.astromancy.core.registration;

import coffee.amo.astromancy.Astromancy;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SoundRegistry {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Astromancy.MODID);

    public static final RegistryObject<SoundEvent> RESEARCH_WRITE = register(new SoundEvent(Astromancy.astromancy("write")));
    public static final RegistryObject<SoundEvent> JAR = register(new SoundEvent(Astromancy.astromancy("jar")));
    public static final RegistryObject<SoundEvent> GRIND = register(new SoundEvent(Astromancy.astromancy("grind")));

    public static RegistryObject<SoundEvent> register(SoundEvent soundEvent) {
        return SOUNDS.register(soundEvent.getLocation().getPath(), () -> soundEvent);
    }
}
