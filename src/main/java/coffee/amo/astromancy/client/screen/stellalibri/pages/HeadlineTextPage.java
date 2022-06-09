package coffee.amo.astromancy.client.screen.stellalibri.pages;

import coffee.amo.astromancy.Astromancy;
import coffee.amo.astromancy.client.screen.stellalibri.BookScreen;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;

public class HeadlineTextPage extends BookPage
{
    private final String headlineTranslationKey;
    private final String translationKey;
    public HeadlineTextPage(String headlineTranslationKey, String translationKey)
    {
        super(Astromancy.astromancy("textures/gui/book/pages/headline_page.png"));
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
        int guiLeft = guiLeft();
        int guiTop = guiTop();
        Component component = new TranslatableComponent(headlineTranslationKey());
        BookScreen.renderText(poseStack, component, guiLeft+75 - minecraft.font.width(component.getString())/2,guiTop+10);
        BookScreen.renderWrappingText(poseStack, translationKey(), guiLeft+16,guiTop+31,120);
    }

    @Override
    public void renderRight(Minecraft minecraft, PoseStack poseStack, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks)
    {
        int guiLeft = guiLeft();
        int guiTop = guiTop();
        Component component = new TranslatableComponent(headlineTranslationKey());
        BookScreen.renderText(poseStack, component, guiLeft+218 - minecraft.font.width(component.getString())/2,guiTop+10);
        BookScreen.renderWrappingText(poseStack, translationKey(), guiLeft+158,guiTop+31,120);
    }
}