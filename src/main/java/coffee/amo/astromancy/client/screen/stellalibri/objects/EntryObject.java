package coffee.amo.astromancy.client.screen.stellalibri.objects;

import coffee.amo.astromancy.client.research.ClientResearchHolder;
import coffee.amo.astromancy.client.screen.stellalibri.BookEntry;
import coffee.amo.astromancy.client.screen.stellalibri.EntryScreen;
import coffee.amo.astromancy.client.screen.stellalibri.tab.BookTab;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.TranslatableComponent;

import java.util.Arrays;
import java.util.List;

import static coffee.amo.astromancy.client.screen.stellalibri.BookScreen.*;

public class EntryObject extends BookObject {
    public final BookEntry entry;
    public final String identifier;
    public EntryObject(BookEntry entry, int posX, int posY, String identifier, int localX, int localY) {
        super(posX, posY, 32, 32, identifier, localX, localY);
        this.entry = entry;
        this.identifier = identifier;
    }

    public EntryObject(BookEntry entry, int posX, int posY, String identifier, List<BookObject> child, int localX, int localY) {
        super(posX, posY, 32, 32, identifier, localX, localY);
        this.entry = entry;
        this.identifier = identifier;
        this.children = child;
    }

    @Override
    public void click(float xOffset, float yOffset, double mouseX, double mouseY)
    {
        if(checkEntries(screen.tab)){
            EntryScreen.openScreen(this);
        }
    }

    private boolean checkEntries(BookTab tab) {
        for (BookObject entry : tab.entries) {
            if (entry.identifier.equals(identifier)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void render(Minecraft minecraft, PoseStack poseStack, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks)
    {
        int posX = offsetPosX(xOffset);
        int posY = offsetPosY(yOffset);
        if (!children.isEmpty()) {
            for (BookObject child : children) {
                if (ClientResearchHolder.getResearch().contains(child.identifier)) {
                    if (child.localX > this.localX && child.localY == this.localY) {
                        renderTransparentTexture(HORIZONTAL_LINE, poseStack, posX + 14, posY + 16, 0, 0, 32, 6, 32, 6);
                    } else if (child.localX == this.localX && child.localY < this.localY) {
                        renderTransparentTexture(VERTICAL_LINE, poseStack, posX + 14, posY + 29, 0, 0, 6, 27, 6, 32);
                    } else if (child.localX < this.localX && child.localY == this.localY) {
                        renderTransparentTexture(HORIZONTAL_LINE, poseStack, posX - 14, posY + 16, 0, 0, 32, 6, 32, 6);
                    } else if (child.localX == this.localX && child.localY > this.localY) {
                        renderTransparentTexture(VERTICAL_LINE, poseStack, posX + 14, posY - 19, 0, 0, 6, 27, 6, 32);
                    }
                }
            }
        }
        renderTexture(FRAME_TEXTURE, poseStack, posX + 6, posY + 8, 80, 232, 22, 22, 256, 256);
        poseStack.pushPose();
        poseStack.scale(0.5f, 0.5f, 0.5f);
        minecraft.getItemRenderer().renderAndDecorateItem(entry.iconStack, posX + 9, posY + 11);
        poseStack.popPose();
    }

    @Override
    public void lateRender(Minecraft minecraft, PoseStack poseStack, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks) {
        if (isHovering)
        {
            screen.renderComponentTooltip(poseStack, Arrays.asList(new TranslatableComponent(entry.translationKey()), new TranslatableComponent(entry.descriptionTranslationKey()).withStyle(ChatFormatting.GRAY)), mouseX, mouseY, minecraft.font);
        }
    }
}
