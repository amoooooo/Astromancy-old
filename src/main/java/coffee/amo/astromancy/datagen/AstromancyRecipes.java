package coffee.amo.astromancy.datagen;

import coffee.amo.astromancy.core.registration.ItemRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.Items;

import static net.minecraft.data.recipes.ShapedRecipeBuilder.shaped;
import static net.minecraft.data.recipes.ShapelessRecipeBuilder.shapeless;

import java.util.function.Consumer;

public class AstromancyRecipes extends RecipeProvider {
    public AstromancyRecipes(DataGenerator pGenerator) {
        super(pGenerator);
    }

    @Override
    public String getName() {
        return "Astromancy Recipe Provider";
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> pFinishedRecipeConsumer) {
        shaped(ItemRegistry.ARMILLARY_SPHERE_CAGE.get())
                .define('#', ItemRegistry.AURUMIC_BRASS_INGOT.get())
                .pattern(" # ")
                .pattern("# #")
                .pattern(" # ")
                .unlockedBy("has_armillary_sphere", has(ItemRegistry.AURUMIC_BRASS_INGOT.get())).save(pFinishedRecipeConsumer);
        shaped(ItemRegistry.ARMILLARY_SPHERE.get())
                .define('#', ItemRegistry.AURUMIC_BRASS_INGOT.get())
                .define('$', ItemRegistry.ARMILLARY_SPHERE_CAGE.get())
                .define('%', Items.DARK_OAK_PLANKS.asItem())
                .pattern(" $ ")
                .pattern(" % ")
                .pattern("#%#")
                .unlockedBy("has_armillary_sphere_cage", has(ItemRegistry.ARMILLARY_SPHERE_CAGE.get())).save(pFinishedRecipeConsumer);

    }
}
