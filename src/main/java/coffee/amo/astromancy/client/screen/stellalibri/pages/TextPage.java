package coffee.amo.astromancy.client.screen.stellalibri.pages;

import coffee.amo.astromancy.Astromancy;
import coffee.amo.astromancy.client.research.ClientResearchHolder;
import coffee.amo.astromancy.client.screen.stellalibri.BookScreen;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;

import java.lang.ref.WeakReference;

import static coffee.amo.astromancy.client.screen.stellalibri.EntryScreen.screen;

public class TextPage extends BookPage {
    public final String translationKey;

    public TextPage(String translationKey, String research) {
        super(Astromancy.astromancy("textures/gui/book/pages/entry.png"), research);
        this.translationKey = translationKey;
    }

    public String translationKey() {
        return "astromancy.gui.book.entry.page.text." + translationKey;
    }

    @Override
    public void renderLeft(Minecraft minecraft, PoseStack poseStack, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks) {
        if(hidden){if(!ClientResearchHolder.isResearchCompleted(translationKey)){return;}}
        int guiLeft = guiLeft();
        int guiTop = guiTop();
        BookScreen.renderWrappingText(poseStack, translationKey(), guiLeft+19,guiTop+31,100);
    }

    @Override
    public void renderRight(Minecraft minecraft, PoseStack poseStack, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks) {
        if(hidden){if(!ClientResearchHolder.isResearchCompleted(translationKey)){return;}}
        int guiLeft = guiLeft();
        int guiTop = guiTop();
        BookScreen.renderWrappingText(poseStack, translationKey(), guiLeft+138,guiTop+31,100);
    }

    @Override
    public void renderBackgroundLeft(Minecraft minecraft, PoseStack poseStack, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks) {
        if (hidden) {
            if (!ClientResearchHolder.isResearchCompleted(research)) {
                int guiLeft = guiLeft();
                int guiTop = guiTop();
                BookScreen.renderTexture(PLAIN, poseStack,guiLeft, guiTop,1,1,screen.bookWidth-128, screen.bookHeight,256,256);
                return;
            }
        }
        super.renderBackgroundLeft(minecraft, poseStack, xOffset, yOffset, mouseX, mouseY, partialTicks);
    }

    @Override
    public void renderBackgroundRight(Minecraft minecraft, PoseStack poseStack, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks) {
        if (hidden) {
            if (!ClientResearchHolder.isResearchCompleted(research)) {
                int guiLeft = guiLeft();
                int guiTop = guiTop();
                BookScreen.renderTexture(PLAIN, poseStack,guiLeft+127, guiTop,128,1,screen.bookWidth-128, screen.bookHeight,256,256);
                return;
            }
        }
        super.renderBackgroundRight(minecraft, poseStack, xOffset, yOffset, mouseX, mouseY, partialTicks);
    }
}
