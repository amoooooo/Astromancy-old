package coffee.amo.astromancy.client.screen.stellalibri.objects;

import coffee.amo.astromancy.client.research.ClientResearchHolder;
import coffee.amo.astromancy.client.screen.stellalibri.BookEntry;
import coffee.amo.astromancy.client.screen.stellalibri.BookTextures;
import coffee.amo.astromancy.core.handlers.AstromancyPacketHandler;
import coffee.amo.astromancy.core.packets.ServerboundResearchPacket;
import coffee.amo.astromancy.core.systems.research.ResearchObject;
import coffee.amo.astromancy.core.systems.research.ResearchProgress;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Items;
import net.minecraftforge.network.PacketDistributor;

import java.util.List;

import static coffee.amo.astromancy.client.screen.stellalibri.BookScreen.*;

public class ImportantEntryObject extends EntryObject {

    public ImportantEntryObject(BookEntry entry, int posX, int posY, int localX, int localY, ResearchObject research) {
        super(entry, posX, posY, localX, localY, research);
    }

    public ImportantEntryObject(BookEntry bookEntry, int i, int i1, List<BookObject> bookObjects, int i2, int i3, ResearchObject researchObject) {
        super(bookEntry, i, i1, bookObjects, i2, i3, researchObject);
    }

    @Override
    public void clickLocked(float xOffset, float yOffset, double mouseX, double mouseY) {
        if(Minecraft.getInstance().player.getInventory().contains(Items.PAPER.getDefaultInstance()) && Minecraft.getInstance().player.getInventory().contains(Items.INK_SAC.getDefaultInstance())){
            Minecraft.getInstance().player.playSound(SoundEvents.CHAIN_BREAK, 0.5f, 1f);
            research.locked = ResearchProgress.IN_PROGRESS;
            AstromancyPacketHandler.INSTANCE.send(PacketDistributor.SERVER.noArg(), new ServerboundResearchPacket(identifier));
        }
    }

    @Override
    public void render(Minecraft minecraft, PoseStack poseStack, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks) {
        int posX = offsetPosX(xOffset);
        int posY = offsetPosY(yOffset);
        if(research.locked.equals(ResearchProgress.LOCKED)){
            lockedRender(minecraft, poseStack, xOffset, yOffset, mouseX, mouseY, partialTicks);
        } else {
            if (!children.isEmpty() && research.locked == ResearchProgress.COMPLETED) {
                for (BookObject child : children) {
                    if (ClientResearchHolder.contains(child.identifier)) {
                        // TODO: add diagonal curved lines to this and ImportantEntryObject#render}
                        if (child.localX > this.localX && child.localY == this.localY) {
                            renderTransparentTexture(BookTextures.HORIZONTAL_LINE, poseStack, posX + 14, posY + 16, 0, 0, 32, 6, 32, 6);
                        } else if (child.localX == this.localX && child.localY < this.localY) {
                            renderTransparentTexture(BookTextures.VERTICAL_LINE, poseStack, posX + 14, posY + 29, 0, 0, 6, 27, 6, 32);
                        } else if (child.localX < this.localX && child.localY == this.localY) {
                            renderTransparentTexture(BookTextures.HORIZONTAL_LINE, poseStack, posX - 14, posY + 16, 0, 0, 32, 6, 32, 6);
                        } else if (child.localX == this.localX && child.localY > this.localY) {
                            renderTransparentTexture(BookTextures.VERTICAL_LINE, poseStack, posX + 14, posY - 19, 0, 0, 6, 27, 6, 32);
                        } else if (child.localX > this.localX && child.localY > this.localY) {
                            renderTransparentTexture(BookTextures.SPLINES, poseStack, posX + 22, posY - 18, 0, 0,40,40,160,40);
                        } else if (child.localX < this.localX && child.localY > this.localY) {
                            renderTransparentTexture(BookTextures.SPLINES, poseStack, posX - 26, posY -18, 120, 0,40,40,160,40);
                        } else if (child.localX > this.localX && child.localY < this.localY) {
                            renderTransparentTexture(BookTextures.SPLINES, poseStack, posX + 22, posY + 18, 80, 0,40,40,160,40);
                        } else if (child.localX < this.localX && child.localY < this.localY) {
                            renderTransparentTexture(BookTextures.SPLINES, poseStack, posX - 26, posY + 18, 40, 0,40,40,160,40);
                        }
                    } else {
                        if (child.localX > this.localX && child.localY == this.localY) {
                            renderTransparentTexture(BookTextures.LOCKED_HORIZONTAL_LINE, poseStack, posX + 14, posY + 16, 0, 0, 32, 6, 32, 6);
                        } else if (child.localX == this.localX && child.localY < this.localY) {
                            renderTransparentTexture(BookTextures.LOCKED_VERTICAL_LINE, poseStack, posX + 14, posY + 29, 0, 0, 6, 27, 6, 32);
                        } else if (child.localX < this.localX && child.localY == this.localY) {
                            renderTransparentTexture(BookTextures.LOCKED_HORIZONTAL_LINE, poseStack, posX - 14, posY + 16, 0, 0, 32, 6, 32, 6);
                        } else if (child.localX == this.localX && child.localY > this.localY) {
                            renderTransparentTexture(BookTextures.LOCKED_VERTICAL_LINE, poseStack, posX + 14, posY - 19, 0, 0, 6, 27, 6, 32);
                        } else if (child.localX > this.localX && child.localY > this.localY) {
                            renderTransparentTexture(BookTextures.LOCKED_SPLINES, poseStack, posX + 22, posY - 18, 0, 0,40,40,160,40);
                        } else if (child.localX < this.localX && child.localY > this.localY) {
                            renderTransparentTexture(BookTextures.LOCKED_SPLINES, poseStack, posX - 22, posY -18, 120, 0,40,40,160,40);
                        } else if (child.localX > this.localX && child.localY < this.localY) {
                            renderTransparentTexture(BookTextures.LOCKED_SPLINES, poseStack, posX + 22, posY + 18, 80, 0,40,40,160,40);
                        } else if (child.localX < this.localX && child.localY < this.localY) {
                            renderTransparentTexture(BookTextures.LOCKED_SPLINES, poseStack, posX - 22, posY + 18, 40, 0,40,40,160,40);
                        }
                    }
                }
            }
            if(research.locked.equals(ResearchProgress.COMPLETED)){
                renderTexture(BookTextures.FRAME_TEXTURE, poseStack, posX + 7, posY + 8, 133, 232, 20, 22, 256, 256);
            } else {
                float mult = (float)Math.abs(Math.sin((Minecraft.getInstance().player.tickCount + partialTicks) / 5f) * 0.75f) + 0.25f;
                RenderSystem.setShaderColor(mult, mult, mult, 1f);
                renderTexture(BookTextures.FRAME_TEXTURE, poseStack, posX + 7, posY + 8, 133, 232, 20, 22, 256, 256);
                RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
            }
            poseStack.pushPose();
            poseStack.scale(0.5f, 0.5f, 0.5f);
            minecraft.getItemRenderer().renderGuiItem(entry.iconStack, posX + 9, posY + 10);
            poseStack.popPose();
        }
    }

    @Override
    public void lockedRender(Minecraft minecraft, PoseStack poseStack, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks) {
        int posX = offsetPosX(xOffset);
        int posY = offsetPosY(yOffset);
        renderTexture(BookTextures.LOCKED_ICONS, poseStack, posX + 6, posY + 8, 0, 0, 22, 22, 100, 22);
        renderTransparentTexture(BookTextures.LOCKED_CHAINS, poseStack, posX + 6, posY + 8, 0, 0, 22, 22, 100, 22);
    }
}