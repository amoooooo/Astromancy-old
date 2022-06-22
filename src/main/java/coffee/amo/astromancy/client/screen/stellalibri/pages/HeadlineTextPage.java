package coffee.amo.astromancy.client.screen.stellalibri.pages;

import coffee.amo.astromancy.Astromancy;
import coffee.amo.astromancy.client.screen.stellalibri.BookScreen;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;

import java.lang.ref.WeakReference;

public class HeadlineTextPage extends BookPage
{
    private final String headlineTranslationKey;
    private final String translationKey;
    public HeadlineTextPage(String headlineTranslationKey, String translationKey)
    {
        super(Astromancy.astromancy("textures/gui/book/pages/entry.png"));
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
        BookScreen.renderText(poseStack, component, guiLeft+64 - minecraft.font.width(component.getString())/2,guiTop+10);
        BookScreen.renderTransparentTexture(BACKGROUND, poseStack, guiLeft+23, guiTop+19, 29, 184, 86, 3, 256, 256);
        BookScreen.renderWrappingText(poseStack, translationKey(), guiLeft+19,guiTop+31,100);
    }

    @Override
    public void renderRight(Minecraft minecraft, PoseStack poseStack, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks)
    {
        int guiLeft = guiLeft();
        int guiTop = guiTop();
        Component component = new TranslatableComponent(headlineTranslationKey());
        BookScreen.renderText(poseStack, component, guiLeft+190 - minecraft.font.width(component.getString())/2,guiTop+10);
        BookScreen.renderTransparentTexture(BACKGROUND, poseStack, guiLeft+148, guiTop+19, 29, 184, 86, 3, 256, 256);
        BookScreen.renderWrappingText(poseStack, translationKey(), guiLeft+138,guiTop+31,100);
    }
}