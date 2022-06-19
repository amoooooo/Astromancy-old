package coffee.amo.astromancy.client.screen.stellalibri.pages;

import coffee.amo.astromancy.Astromancy;
import coffee.amo.astromancy.client.screen.stellalibri.BookScreen;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class CraftingPage extends BookPage {
    private final ItemStack outputStack;
    private final ItemStack[] inputStacks;

    public CraftingPage(ItemStack outputStack, ItemStack... inputStacks) {
        super(Astromancy.astromancy("textures/gui/book/pages/crafting.png"));
        this.outputStack = outputStack;
        this.inputStacks = inputStacks;
    }

    public CraftingPage(Item outputStack, Item... inputItems) {
        this(outputStack.getDefaultInstance(), inputItems);
    }

    public CraftingPage(ItemStack outputStack, Item... inputItems) {
        super(Astromancy.astromancy("textures/gui/book/pages/crafting.png"));
        this.outputStack = outputStack;

        ItemStack[] inputStacks = new ItemStack[inputItems.length];
        for (int i = 0; i < inputItems.length; i++) {
            inputStacks[i] = inputItems[i].getDefaultInstance();
        }
        this.inputStacks = inputStacks;
    }

    public void renderLeft(Minecraft minecraft, PoseStack poseStack, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks) {
        int guiLeft = guiLeft();
        int guiTop = guiTop();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int index = i * 3 + j;
                if (index < inputStacks.length && !inputStacks[index].isEmpty()) {
                    ItemStack itemStack = inputStacks[index];
                    int itemPosX = guiLeft + 36 + j * 25;
                    int itemPosY = guiTop + 22 + i * 25;
                    BookScreen.renderItem(poseStack, itemStack, itemPosX, itemPosY, mouseX, mouseY);
                }
            }
        }
        BookScreen.renderItem(poseStack, outputStack, guiLeft + 61, guiTop + 120, mouseX, mouseY);
    }

    @Override
    public void renderRight(Minecraft minecraft, PoseStack poseStack, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks) {
        int guiLeft = guiLeft();
        int guiTop = guiTop();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int index = i * 3 + j;
                if (index < inputStacks.length && !inputStacks[index].isEmpty()) {
                    ItemStack itemStack = inputStacks[index];
                    int itemPosX = guiLeft + 152 + j * 25;
                    int itemPosY = guiTop + 22 + i * 25;
                    BookScreen.renderItem(poseStack, itemStack, itemPosX, itemPosY, mouseX, mouseY);
                }
            }
        }
        BookScreen.renderItem(poseStack, outputStack, guiLeft + 177, guiTop + 120, mouseX, mouseY);
    }

    public static CraftingPage fullPage(Item output, Item input){
        return fullPage(output.getDefaultInstance(), input.getDefaultInstance());
    }

    public static CraftingPage fullPage(ItemStack output, ItemStack input){
        return new CraftingPage(output, input, input, input, input, input, input, input, input, input);
    }

    public static CraftingPage armSpherePage(Item armsphere, Item armCage, Item brass){
        ItemStack empty = ItemStack.EMPTY;
        ItemStack dark_oak_planks = Items.DARK_OAK_PLANKS.getDefaultInstance();
        return new CraftingPage(armsphere.getDefaultInstance(), empty, armCage.getDefaultInstance(), empty, empty, dark_oak_planks, empty, brass.getDefaultInstance(), dark_oak_planks, brass.getDefaultInstance());
    }

    public static CraftingPage armSpherePage(ItemStack armsphere, ItemStack armCage, ItemStack brass){
        ItemStack empty = ItemStack.EMPTY;
        ItemStack oak_plank = Items.OAK_PLANKS.getDefaultInstance();
        return new CraftingPage(armsphere, empty, armCage, empty, empty, oak_plank, empty, brass, oak_plank, brass);
    }

    public static CraftingPage armCagePage(Item armCage, Item brass){
        ItemStack empty = ItemStack.EMPTY;
        return new CraftingPage(armCage.getDefaultInstance(), empty, brass.getDefaultInstance(), empty, brass.getDefaultInstance(), empty, brass.getDefaultInstance(), empty, brass.getDefaultInstance(), empty);
    }

    public static CraftingPage armCagePage(ItemStack armCage, ItemStack brass){
        ItemStack empty = ItemStack.EMPTY;
        return new CraftingPage(armCage, empty, brass, empty, brass, empty, brass, empty, brass, empty);
    }
}
