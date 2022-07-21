package coffee.amo.astromancy.jei;

import coffee.amo.astromancy.Astromancy;
import coffee.amo.astromancy.core.registration.ItemRegistry;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

@JeiPlugin
public class AstromancyJEIPlugin implements IModPlugin {

    @Override
    public ResourceLocation getPluginUid() {
        return Astromancy.astromancy(Astromancy.MODID);
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        IModPlugin.super.registerRecipes(registration);
        registration.getIngredientManager().removeIngredientsAtRuntime(VanillaTypes.ITEM_STACK, List.of(ItemRegistry.GLYPH_RESEARCH_ICON.get().getDefaultInstance()));
    }
}
