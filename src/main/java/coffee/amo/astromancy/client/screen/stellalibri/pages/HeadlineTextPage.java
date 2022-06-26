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

public class HeadlineTextPage extends BookPage
{
    private final String headlineTranslationKey;
    private final String translationKey;
    public HeadlineTextPage(String headlineTranslationKey, String translationKey, String research)
    {
        super(Astromancy.astromancy("textures/gui/book/pages/entry.png"), research);
        this.headlineTranslationKey = headlineTranslationKey;
        this.translationKey = translationKey;
    }

    public String headlineTranslationKey()
    {
        return "astromancy.gui.book.entry.page.headline." + headlineTranslationKey;
    }
    public String translationKey()
    {
        return "astromancy.gui.book.entry.page.text." + translationKey;
    }
    @Override
    public void renderLeft(Minecraft minecraft, PoseStack poseStack, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks)
    {
        if(hidden){if(!ClientResearchHolder.isResearchCompleted(research)){return;}}
        int guiLeft = guiLeft();
        int guiTop = guiTop();
        Component component = new TranslatableComponent(headlineTranslationKey());
        BookScreen.renderText(poseStack, component, guiLeft+64 - minecraft.font.width(component.getString())/2,guiTop+10);
        BookScreen.renderTransparentTexture(BACKGROUND, poseStack, guiLeft+23, guiTop+19, 29, 184, 86, 3, 256, 256);
        BookScreen.renderWrappingText(poseStack, translationKey(), guiLeft+19,guiTop+31,100);
    }

    @Override
    public void renderRight(Minecraft minecraft, PoseStack poseStack, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks)
    {
        if(hidden){if(!ClientResearchHolder.isResearchCompleted(research)){return;}}
        int guiLeft = guiLeft();
        int guiTop = guiTop();
        Component component = new TranslatableComponent(headlineTranslationKey());
        BookScreen.renderText(poseStack, component, guiLeft+190 - minecraft.font.width(component.getString())/2,guiTop+10);
        BookScreen.renderTransparentTexture(BACKGROUND, poseStack, guiLeft+148, guiTop+19, 29, 184, 86, 3, 256, 256);
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