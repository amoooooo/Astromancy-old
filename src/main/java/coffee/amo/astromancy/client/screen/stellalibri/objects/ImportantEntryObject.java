package coffee.amo.astromancy.client.screen.stellalibri.objects;

import coffee.amo.astromancy.client.research.ClientResearchHolder;
import coffee.amo.astromancy.client.screen.stellalibri.BookEntry;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;

import java.util.List;

import static coffee.amo.astromancy.client.screen.stellalibri.BookScreen.*;

public class ImportantEntryObject extends EntryObject {

    public ImportantEntryObject(BookEntry entry, int posX, int posY, String identifier, int localX, int localY) {
        super(entry, posX, posY, identifier, localX, localY);
    }

    public ImportantEntryObject(BookEntry bookEntry, int i, int i1, String s, List<BookObject> bookObjects, int i2, int i3) {
        super(bookEntry, i, i1, s, bookObjects, i2, i3);
    }

    @Override
    public void render(Minecraft minecraft, PoseStack poseStack, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks) {
        int posX = offsetPosX(xOffset);
        int posY = offsetPosY(yOffset);
        if (!children.isEmpty()) {
            for (BookObject child : children) {
                if (ClientResearchHolder.getResearch().contains(child.identifier)){
                    if (child.localX > this.localX && child.localY == this.localY) {
                        renderTransparentTexture(HORIZONTAL_LINE, poseStack, posX + 14, posY + 16, 0, 0, 32, 6, 32, 6);
                    } else if (child.localX == this.localX && child.localY < this.localY) {
                        renderTransparentTexture(VERTICAL_LINE, poseStack, posX + 14, posY + 24, 0, 0, 6, 32, 6, 32);
                    } else if (child.localX < this.localX && child.localY == this.localY) {
                        renderTransparentTexture(HORIZONTAL_LINE, poseStack, posX - 14, posY + 16, 0, 0, 32, 6, 32, 6);
                    } else if (child.localX == this.localX && child.localY > this.localY) {
                        renderTransparentTexture(VERTICAL_LINE, poseStack, posX + 14, posY - 19, 0, 0, 6, 27, 6, 32);
                    }
                }
            }
        }
        renderTexture(FRAME_TEXTURE, poseStack, posX + 7, posY + 8, 133, 232, 20, 22, 256, 256);
        poseStack.pushPose();
        poseStack.scale(0.5f, 0.5f, 0.5f);
        minecraft.getItemRenderer().renderGuiItem(entry.iconStack, posX + 9, posY + 10);
        poseStack.popPose();
    }
}