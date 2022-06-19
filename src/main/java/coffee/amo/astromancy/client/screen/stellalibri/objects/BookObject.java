package coffee.amo.astromancy.client.screen.stellalibri.objects;

import coffee.amo.astromancy.client.screen.stellalibri.BookEntry;
import coffee.amo.astromancy.client.screen.stellalibri.BookScreen;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.TextComponent;

import java.util.List;

import static coffee.amo.astromancy.client.screen.stellalibri.BookScreen.screen;

public class BookObject {
    public boolean isHovering;
    // TODO: Make this a list, check of BookObject has children in the render method, if so, render those.
    public List<BookObject> children;
    public String identifier;
    public float hover;
    public int posX;
    public int posY;
    public int localX;
    public int localY;
    public int width;
    public int height;

    public BookObject(int posX, int posY, int width, int height, String identifier, int localX, int localY) {
        this.localX = localX;
        this.localY = localY;
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.identifier = identifier;
    }
    public BookObject(int posX, int posY, int width, int height, BookObject... child)
    {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.children = List.of(child);
    }
    public int hoverCap()
    {
        return 20;
    }

    public void render(Minecraft minecraft, PoseStack poseStack, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks)
    {

    }

    public void lockedRender(Minecraft minecraft, PoseStack poseStack, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks)
    {

    }

    public void renderChildren(Minecraft minecraft, PoseStack poseStack, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks){

    }
    public void lateRender(Minecraft minecraft, PoseStack poseStack, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks) {

    }
    public void lateLockedRender(Minecraft minecraft, PoseStack poseStack, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks) {
        if (isHovering)
        {
            screen.renderComponentTooltip(poseStack, List.of(new TextComponent("Locked")), mouseX, mouseY, minecraft.font);
        }
    }
    public void click(float xOffset, float yOffset, double mouseX, double mouseY)
    {

    }
    public void clickLocked(float xOffset, float yOffset, double mouseX, double mouseY)
    {

    }
    public void exit()
    {

    }
    public boolean isHovering(float xOffset, float yOffset, double mouseX, double mouseY)
    {
        return BookScreen.isHovering(mouseX,mouseY, offsetPosX(xOffset), offsetPosY(yOffset), width, height);
    }
    public int offsetPosX(float xOffset)
    {
        int guiLeft = (width - screen.bookWidth) / 2;
        return (int) (guiLeft+ this.posX + xOffset);
    }
    public int offsetPosY(float yOffset)
    {
        int guiTop = (height - screen.bookHeight) / 2;
        return (int) (guiTop + this.posY + yOffset);
    }
}
