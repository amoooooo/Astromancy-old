package coffee.amo.astromancy.client.screen.stellalibri.objects;

import coffee.amo.astromancy.client.screen.stellalibri.BookEntry;
import coffee.amo.astromancy.client.screen.stellalibri.BookScreen;
import coffee.amo.astromancy.core.systems.research.ResearchObject;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraftforge.common.util.LazyOptional;

import java.util.List;

import static coffee.amo.astromancy.client.screen.stellalibri.BookScreen.screen;

public class BookObject {
    public boolean isHovering;
    // TODO: Make this a list, check of BookObject has children in the render method, if so, render those.
    public List<BookObject> children;
    public ResearchObject research;
    public List<ResearchObject> unlocks;
    public String identifier;
    public boolean isRendered = false;
    public float hover;
    public float posX;
    public float posY;
    public float localX;
    public float localY;
    public int width;
    public int height;

    public BookObject(float posX, float posY, int width, int height, float localX, float localY, ResearchObject research) {
        this.localX = localX;
        this.localY = localY;
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.identifier = research.identifier;
        this.research = research;
    }
    public BookObject(float posX, float posY, int width, int height, ResearchObject research, BookObject... child)
    {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.children = List.of(child);
        this.research = research;
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
    public void lateLockedRender(Minecraft minecraft, PoseStack poseStack, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks, String... parents) {
        if (isHovering)
        {
            screen.renderComponentTooltip(poseStack, List.of(Component.literal("Locked")), mouseX, mouseY, minecraft.font);
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
        return (int) (guiLeft+ (this.posX * 1.05) + xOffset);
    }
    public int offsetPosY(float yOffset)
    {
        int guiTop = (height - screen.bookHeight) / 2;
        return (int) (guiTop + (this.posY * 1.05) + yOffset);
    }
}
