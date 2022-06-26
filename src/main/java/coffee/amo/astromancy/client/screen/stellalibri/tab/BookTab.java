package coffee.amo.astromancy.client.screen.stellalibri.tab;

import coffee.amo.astromancy.client.screen.stellalibri.BookScreen;
import coffee.amo.astromancy.client.screen.stellalibri.BookTextures;
import coffee.amo.astromancy.client.screen.stellalibri.objects.BookObject;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static coffee.amo.astromancy.client.screen.stellalibri.BookScreen.*;

public class BookTab {
    public boolean isHovering;
    public List<BookObject> entries = new ArrayList<>();
    public ResourceLocation BACKGROUND;
    public List<ResourceLocation> backgroundParallax;
    public String identifier;
    public float hover;
    public int posX;
    public int posY;
    public int localX;
    public int localY;
    public int width = 23;
    public int height = 24;
    public ItemStack iconStack;

    public BookTab(int posX, int posY, String identifier, int localX, int localY, ItemStack iconStack, ResourceLocation background) {
        this.localX = localX;
        this.localY = localY;
        this.posX = posX * -22;
        this.posY = posY * 24;
        this.identifier = identifier;
        this.iconStack = iconStack;
        this.BACKGROUND = background;
    }

    public BookTab(int posX, int posY, String identifier, int localX, int localY, ItemStack iconStack, List<ResourceLocation> parallax) {
        this.localX = localX;
        this.localY = localY;
        this.posX = posX * -22;
        this.posY = posY * 24;
        this.identifier = identifier;
        this.iconStack = iconStack;
        this.backgroundParallax = parallax;
    }

    public String translationKey(){
        return "astromancy.gui.book.tab." + identifier;
    }

    public BookTab addEntry(BookObject entry) {
        this.entries.add(entry);
        return this;
    }

    public BookTab addEntries(List<BookObject> entries){
        this.entries.addAll(entries);
        return this;
    }

    public void render(Minecraft minecraft, PoseStack poseStack, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks, boolean selected) {
        int posX = offsetPosX(xOffset);
        int posY = offsetPosY(yOffset);
        if(!selected){
            renderTransparentTexture(BookTextures.FRAME_TEXTURE, poseStack, posX, posY, 156, 232, 23, 24, 256, 256);
        } else {
            renderTransparentTexture(BookTextures.FRAME_TEXTURE, poseStack, posX, posY, 184, 232, 23, 24, 256, 256);
        }
        poseStack.pushPose();
        poseStack.scale(0.5f, 0.5f, 0.5f);
        minecraft.getItemRenderer().renderAndDecorateItem(iconStack, posX + 6, posY + 4);
        poseStack.popPose();
    }

    public int hoverCap(){
        return 20;
    }

    public void lateRender(Minecraft minecraft, PoseStack poseStack, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks) {
        if (isHovering)
        {
            screen.renderComponentTooltip(poseStack, List.of(Component.translatable(translationKey())), mouseX, mouseY, minecraft.font);
        }
    }
    public boolean isHovering(float xOffset, float yOffset, double mouseX, double mouseY)
    {
        return BookScreen.isHoveringTab(mouseX,mouseY, offsetPosX(xOffset), offsetPosY(yOffset), width, height);
    }

    public void click(float xOffset, float yOffset, double mouseX, double mouseY) {
        if (isHovering(xOffset, yOffset, mouseX, mouseY)) {
            screen.setTab(this);
        }
    }
    public int offsetPosX(float xOffset)
    {
        int guiLeft = (width - screen.bookWidth) / 2;
        return (int) (this.posX + xOffset);
    }
    public int offsetPosY(float yOffset)
    {
        int guiTop = (height - screen.bookHeight) / 2;
        return (int) (this.posY + yOffset);
    }
}
