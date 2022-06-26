package coffee.amo.astromancy.common.recipe;

import coffee.amo.astromancy.Astromancy;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class MortarRecipe implements Recipe<SimpleContainer> {
    private final ResourceLocation id;
    private final ItemStack output;
    private final NonNullList<Ingredient> ingredients;

    public MortarRecipe(ResourceLocation id, ItemStack output, NonNullList<Ingredient> ingredients) {
        this.id = id;
        this.output = output;
        this.ingredients = ingredients;
    }

    public ArrayList<ItemStack> getSortedCrushables(ArrayList<ItemStack> crushables){
        ArrayList<ItemStack> sortedCrushables = new ArrayList<>();
        for(ItemStack stack : crushables){
            for(Ingredient inv : this.ingredients){
                if(inv.test(stack)){
                    sortedCrushables.add(stack);
                    break;
                }
            }
        }
        return sortedCrushables;
    }

    public boolean doCrushablesMatch(ArrayList<ItemStack> crushables){
        if (this.ingredients.size() == 0) {
            return true;
        }
        if (this.ingredients.size() != crushables.size()) {
            return false;
        }
        ArrayList<ItemStack> sortedStacks = getSortedCrushables(crushables);
        if(sortedStacks.size() < this.ingredients.size()){
            return false;
        }
        for(int i = 0; i < this.ingredients.size(); i++){
            Ingredient ing = this.ingredients.get(i);
            ItemStack stack = sortedStacks.get(i);
            if(!ing.test(stack)){
                return false;
            }
        }
        return true;
    }

    public static MortarRecipe getRecipe(Level level, Predicate<MortarRecipe> predicate){
        List<MortarRecipe> recipes = getRecipes(level);
        for(MortarRecipe recipe : recipes){
            if(predicate.test(recipe)){
                return recipe;
            }
        }
        return null;
    }

    public static List<MortarRecipe> getRecipes(Level level){
        return level.getRecipeManager().getAllRecipesFor(Type.INSTANCE);
    }

    /**
     * Used to check if a recipe matches current crafting inventory
     *
     * @param pContainer
     * @param pLevel
     */
    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
            for(int i = 0; i < ingredients.size(); i++){
                if(!ingredients.get(i).test(pContainer.getItem(i))){
                    return false;
                }
            }
        return true;
    }

    /**
     * Returns an Item that is the result of this recipe
     *
     * @param pContainer
     */
    @Override
    public ItemStack assemble(SimpleContainer pContainer) {
        return output.copy();
    }

    /**
     * Used to determine if this recipe can fit in a grid of the given width/height
     *
     * @param pWidth
     * @param pHeight
     */
    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    /**
     * Get the result of this recipe, usually for display purposes (e.g. recipe book). If your recipe has more than one
     * possible result (e.g. it's dynamic and depends on its inputs), then return an empty stack.
     */
    @Override
    public ItemStack getResultItem() {
        return output.copy();
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<MortarRecipe>{
        private Type() { }
        public static final Type INSTANCE = new Type();
        public static final String ID = "mortar";
    }

    public static class Serializer implements  RecipeSerializer<MortarRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = Astromancy.astromancy("mortar");

        @Override
        public MortarRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "output"));

            JsonArray ingredients = GsonHelper.getAsJsonArray(pSerializedRecipe, "ingredients");
            NonNullList<Ingredient> ingredientList = NonNullList.withSize(ingredients.size(), Ingredient.EMPTY);

            for (int i = 0; i < ingredients.size(); i++) {
                ingredientList.set(i, Ingredient.fromJson(ingredients.get(i)));
            }
            return new MortarRecipe(pRecipeId, output, ingredientList);
        }

        @Nullable
        @Override
        public MortarRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            NonNullList<Ingredient> ingredientList = NonNullList.withSize(pBuffer.readInt(), Ingredient.EMPTY);

            for (int i = 0; i < ingredientList.size(); i++) {
                ingredientList.set(i, Ingredient.fromNetwork(pBuffer));
            }

            ItemStack output = pBuffer.readItem();
            return new MortarRecipe(pRecipeId, output, ingredientList);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, MortarRecipe pRecipe) {
            pBuffer.writeInt(pRecipe.ingredients.size());
            for (Ingredient ingredient : pRecipe.ingredients) {
                ingredient.toNetwork(pBuffer);
            }
            pBuffer.writeItem(pRecipe.output);
        }

        /**
         * Determines the type for this entry, used to look up the correct registry in the global registries list as there can only be one
         * registry per concrete class.
         *
         * @return Root registry type.
         */

        @SuppressWarnings("unchecked")
        private static <G> Class<G> castClass(Class<?> clazz) {
            return (Class<G>)clazz;
        }
    }
}
