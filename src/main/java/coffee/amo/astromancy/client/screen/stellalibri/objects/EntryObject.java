package coffee.amo.astromancy.client.screen.stellalibri.objects;

import coffee.amo.astromancy.client.screen.stellalibri.BookEntry;
import coffee.amo.astromancy.client.screen.stellalibri.EntryScreen;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.TranslatableComponent;

import java.util.Arrays;

import static coffee.amo.astromancy.client.screen.stellalibri.BookScreen.*;

public class EntryObject extends BookObject {
    public final BookEntry entry;
    public EntryObject(BookEntry entry, int posX, int posY){
        super(posX, posY, 32, 32);
        this.entry = entry;
    }

    @Override
    public void click(float xOffset, float yOffset, double mouseX, double mouseY)
    {
        EntryScreen.openScreen(this);
    }

    @Override
    public void render(Minecraft minecraft, PoseStack poseStack, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks)
    {
        int posX = offsetPosX(xOffset);
        int posY = offsetPosY(yOffset);
        renderTexture(FRAME_TEXTURE, poseStack, posX, posY, 0, 0, 256, 256, 256, 256);
        minecraft.getItemRenderer().renderAndDecorateItem(entry.iconStack, posX + 8, posY + 8);
    }

    @Override
    public void lateRender(Minecraft minecraft, PoseStack poseStack, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks) {
        if (isHovering)
        {
            screen.renderComponentTooltip(poseStack, Arrays.asList(new TranslatableComponent(entry.translationKey()), new TranslatableComponent(entry.descriptionTranslationKey()).withStyle(ChatFormatting.GRAY)), mouseX, mouseY, minecraft.font);
        }
    }
}
