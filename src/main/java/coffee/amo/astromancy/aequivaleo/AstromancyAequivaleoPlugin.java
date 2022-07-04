package coffee.amo.astromancy.aequivaleo;

import coffee.amo.astromancy.Astromancy;
import coffee.amo.astromancy.core.registration.GlyphRegistry;
import com.ldtteam.aequivaleo.api.plugin.AequivaleoPlugin;
import com.ldtteam.aequivaleo.api.plugin.IAequivaleoPlugin;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.javafmlmod.FMLModContainer;

@AequivaleoPlugin
public class AstromancyAequivaleoPlugin implements IAequivaleoPlugin {
    public static ResourceLocation location(String name){
        return new ResourceLocation("aequivaleo", name);
    }

    public static final String ID = Astromancy.astromancy("glyph_types").toString();

    @Override
    public void onConstruction() {
        ModList.get().getModContainerById(Astromancy.MODID).ifPresent(mod -> {
            GlyphRegistry.TYPES.register(((FMLModContainer)mod).getEventBus());
            GlyphRegistry.TYPE_GROUPS.register(((FMLModContainer)mod).getEventBus());
        });
    }

    /**
     * The id of the plugin.
     * Has to be unique over all plugins.
     *
     * @return The id.
     */
    @Override
    public String getId() {
        return "Astromancy";
    }
}
