package coffee.amo.astromancy.client.screen.stellalibri.tab;

import coffee.amo.astromancy.client.screen.stellalibri.BookScreen;
import coffee.amo.astromancy.client.screen.stellalibri.BookTextures;
import coffee.amo.astromancy.client.screen.stellalibri.objects.BookObject;
import coffee.amo.astromancy.core.helpers.MathHelper;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;

import java.awt.*;
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
    public float hoverAmount;
    public int posX;
    public int posY;
    public int localX;
    public int localY;
    public int width = 23;
    public int height = 24;
    public ItemStack iconStack;
    public Color tabColor;

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
        if(isHovering && hoverAmount < 1.5f) hoverAmount += Minecraft.getInstance().getDeltaFrameTime() / 4;
        else if (!isHovering && hoverAmount > 0) hoverAmount -= Minecraft.getInstance().getDeltaFrameTime() / 4;
        hoverAmount = Mth.clamp(hoverAmount, 0, 1.5f);
        posX -= MathHelper.ease(hoverAmount, MathHelper.Easing.easeOutCubic) * 18;
        float mult = 1;
        if(selected) mult = 1.35f;
        RenderSystem.setShaderColor((tabColor.getRed()/255f) * mult, (tabColor.getGreen()/255f)* mult, (tabColor.getBlue()/255f)* mult, 1);
        renderTransparentTexture(BookTextures.TAB, poseStack, posX, posY, 0, 0, 58, 24, 58, 24);
        poseStack.pushPose();
        poseStack.scale(0.5f, 0.5f, 0.5f);
        minecraft.getItemRenderer().renderAndDecorateItem(iconStack, posX + 5, posY + 4);
        poseStack.popPose();
    }

    public int hoverCap(){
        return 20;
    }

    public void lateRender(Minecraft minecraft, PoseStack poseStack, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks) {
        if (isHovering)
        {
            poseStack.pushPose();
            poseStack.translate(0,0,550);
            screen.renderComponentTooltip(poseStack, List.of(Component.translatable(translationKey())), mouseX, mouseY, minecraft.font);
            poseStack.popPose();
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

    public static List<Color> tabColors = List.of(
            Color.RED.darker(),
            Color.GREEN.darker(),
            Color.BLUE.darker(),
            Color.YELLOW.darker(),
            Color.CYAN.darker(),
            Color.MAGENTA.darker(),
            Color.ORANGE.darker()
    );
}
