package coffee.amo.astromancy.client.screen.stellalibri.pages;

import coffee.amo.astromancy.Astromancy;
import coffee.amo.astromancy.client.screen.stellalibri.BookScreen;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;

import java.lang.ref.WeakReference;

import static coffee.amo.astromancy.client.screen.stellalibri.EntryScreen.screen;

public class BookPage {
    public final ResourceLocation BACKGROUND;
    public final ResourceLocation PLAIN = Astromancy.astromancy("textures/gui/book/pages/entry.png");
    public String research;
    public boolean hidden;
    public BookPage(ResourceLocation background, String research)
    {
        this.BACKGROUND = background;
        this.research = research;
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
        BookScreen.renderTexture(BACKGROUND, poseStack,guiLeft, guiTop,1,1,screen.bookWidth-128, screen.bookHeight,256,256);
    }
    public void renderBackgroundRight(Minecraft minecraft, PoseStack poseStack, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks)
    {
        int guiLeft = guiLeft();
        int guiTop = guiTop();
        BookScreen.renderTexture(BACKGROUND, poseStack,guiLeft+127, guiTop,128,1,screen.bookWidth-128, screen.bookHeight,256,256);
    }
    public int guiLeft()
    {
        return (screen.width - screen.bookWidth) / 2;
    }
    public int guiTop()
    {
        return (screen.height - screen.bookHeight) / 2;
    }

    public BookPage setHidden(boolean hidden)
    {
        this.hidden = hidden;
        return this;
    }
}