package coffee.amo.astromancy.core.registration;

import coffee.amo.astromancy.Astromancy;
import coffee.amo.astromancy.common.recipe.MortarRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RecipeRegistry {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Astromancy.MODID);

    public static final RegistryObject<RecipeSerializer<MortarRecipe>> MORTAR_RECIPE = RECIPE_SERIALIZERS.register("mortar", () -> MortarRecipe.Serializer.INSTANCE);
}
