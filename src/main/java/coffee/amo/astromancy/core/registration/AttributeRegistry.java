package coffee.amo.astromancy.core.registration;

import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class AttributeRegistry {
    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(ForgeRegistries.Keys.ATTRIBUTES, "astromancy");

    public static final RegistryObject<Attribute> ASTRAL_RESIST = ATTRIBUTES.register("astral_resist", () -> new RangedAttribute("astral_resist", 0D, 0D, 1D).setSyncable(true));

    public static void register() {
    }
}
