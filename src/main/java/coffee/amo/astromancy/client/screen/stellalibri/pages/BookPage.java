package coffee.amo.astromancy.client.screen.stellalibri.pages;

import coffee.amo.astromancy.client.screen.stellalibri.BookScreen;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;

import static coffee.amo.astromancy.client.screen.stellalibri.EntryScreen.screen;

public class BookPage
{
    public final ResourceLocation BACKGROUND;
    public BookPage(ResourceLocation background)
    {
        this.BACKGROUND = background;
    }

    public boolean isValid()
    {
        return true;
    }
    public void renderLeft(Minecraft minecraft, PoseStack poseStack, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks)
    {

    }
    public void renderRight(Minecraft minecraft, PoseStack poseStack, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks)
    {

    }
    public void renderBackgroundLeft(Minecraft minecraft, PoseStack poseStack, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks)
    {
        int guiLeft = guiLeft();
        int guiTop = guiTop();
        BookScreen.renderTexture(BACKGROUND, poseStack,guiLeft, guiTop,1,1,screen.bookWidth-73, screen.bookHeight,128,128);
    }
    public void renderBackgroundRight(Minecraft minecraft, PoseStack poseStack, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks)
    {
        int guiLeft = guiLeft();
        int guiTop = guiTop();
        BookScreen.renderTexture(BACKGROUND, poseStack,guiLeft+147, guiTop,148,1,screen.bookWidth-73, screen.bookHeight,128,128);
    }
    public int guiLeft()
    {
        return (screen.width - screen.bookWidth) / 2;
    }
    public int guiTop()
    {
        return (screen.height - screen.bookHeight) / 2;
    }
}