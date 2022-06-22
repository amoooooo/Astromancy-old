package coffee.amo.astromancy.client.screen.stellalibri.pages;

import coffee.amo.astromancy.Astromancy;
import coffee.amo.astromancy.client.screen.stellalibri.BookScreen;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;

import java.lang.ref.WeakReference;

public class TextPage extends BookPage {
    public final String translationKey;

    public TextPage(String translationKey) {
        super(Astromancy.astromancy("textures/gui/book/pages/entry.png"));
        this.translationKey = translationKey;
    }

    public String translationKey() {
        return "astromancy.gui.book.entry.page.text." + translationKey;
    }

    @Override
    public void renderLeft(Minecraft minecraft, PoseStack poseStack, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks) {
        int guiLeft = guiLeft();
        int guiTop = guiTop();
        BookScreen.renderWrappingText(poseStack, translationKey(), guiLeft+19,guiTop+31,100);
    }

    @Override
    public void renderRight(Minecraft minecraft, PoseStack poseStack, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks) {
        int guiLeft = guiLeft();
        int guiTop = guiTop();
        BookScreen.renderWrappingText(poseStack, translationKey(), guiLeft+138,guiTop+31,100);
    }
}
