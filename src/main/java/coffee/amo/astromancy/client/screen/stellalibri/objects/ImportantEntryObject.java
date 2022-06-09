package coffee.amo.astromancy.client.screen.stellalibri.objects;

import coffee.amo.astromancy.client.screen.stellalibri.BookEntry;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;

import static coffee.amo.astromancy.client.screen.stellalibri.BookScreen.*;

public class ImportantEntryObject extends EntryObject {

    public ImportantEntryObject(BookEntry entry, int posX, int posY) {
        super(entry, posX, posY);
    }

    @Override
    public void render(Minecraft minecraft, PoseStack poseStack, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks) {
        int posX = offsetPosX(xOffset);
        int posY = offsetPosY(yOffset);
        renderTexture(FRAME_TEXTURE, poseStack, posX, posY, 34, 252, width, height, 512, 512);
        minecraft.getItemRenderer().renderAndDecorateItem(entry.iconStack, posX + 8, posY + 8);
    }
}