package coffee.amo.astromancy.client.screen.stellalibri.objects;

import coffee.amo.astromancy.Astromancy;
import coffee.amo.astromancy.client.research.ClientResearchHolder;
import coffee.amo.astromancy.client.screen.stellalibri.BookEntry;
import coffee.amo.astromancy.client.screen.stellalibri.BookTextures;
import coffee.amo.astromancy.client.screen.stellalibri.EntryScreen;
import coffee.amo.astromancy.client.screen.stellalibri.tab.BookTab;
import coffee.amo.astromancy.core.handlers.AstromancyPacketHandler;
import coffee.amo.astromancy.core.helpers.StringHelper;
import coffee.amo.astromancy.core.packets.ResearchNotePacket;
import coffee.amo.astromancy.core.packets.ServerboundResearchPacket;
import coffee.amo.astromancy.core.registration.SoundRegistry;
import coffee.amo.astromancy.core.systems.research.ResearchObject;
import coffee.amo.astromancy.core.systems.research.ResearchProgress;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.network.PacketDistributor;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static coffee.amo.astromancy.client.screen.stellalibri.BookScreen.*;

public class EntryObject extends BookObject {
    public final BookEntry entry;
    public String identifier;
    public EntryObject(BookEntry entry, int posX, int posY, int localX, int localY, ResearchObject research) {
        super(posX, posY, 32, 32, localX, localY, research);
        this.entry = entry;
        this.identifier = entry.identifier;
    }

    public EntryObject(BookEntry entry, int posX, int posY, List<BookObject> child, int localX, int localY, ResearchObject research) {
        super(posX, posY, 32, 32, localX, localY, research);
        this.entry = entry;
        this.identifier = research.identifier;
        this.children = child;

    }

    @Override
    public void click(float xOffset, float yOffset, double mouseX, double mouseY)
    {
        if(checkEntries(screen.tab)){
            EntryScreen.openScreen(this);
        }
    }

    @Override
    public void clickLocked(float xOffset, float yOffset, double mouseX, double mouseY)
    {
        if(Minecraft.getInstance().player.getInventory().contains(Items.PAPER.getDefaultInstance()) && Minecraft.getInstance().player.getInventory().contains(Items.INK_SAC.getDefaultInstance()) && research.locked.equals(ResearchProgress.LOCKED)){
            Minecraft.getInstance().player.playSound(SoundEvents.CHAIN_BREAK, 0.5f, 1f);
            research.locked = ResearchProgress.IN_PROGRESS;
            ClientResearchHolder.getResearch().stream().filter(s -> Objects.equals(s.identifier, research.identifier)).findFirst().ifPresent(s -> s.locked = ResearchProgress.IN_PROGRESS);
            AstromancyPacketHandler.INSTANCE.send(PacketDistributor.SERVER.noArg(), new ServerboundResearchPacket(identifier, research.locked.ordinal()));
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
        if (!children.isEmpty() && (research.locked == ResearchProgress.COMPLETED || research.locked == ResearchProgress.IN_PROGRESS)) {
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
                        renderTransparentTexture(BookTextures.SPLINES, poseStack, posX - 22, posY -18, 120, 0,40,40,160,40);
                    } else if (child.localX > this.localX && child.localY < this.localY) {
                        renderTransparentTexture(BookTextures.SPLINES, poseStack, posX + 22, posY + 18, 80, 0,40,40,160,40);
                    } else if (child.localX < this.localX && child.localY < this.localY) {
                        renderTransparentTexture(BookTextures.SPLINES, poseStack, posX - 22, posY + 18, 40, 0,40,40,160,40);
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
        if( ClientResearchHolder.getResearch().stream().filter(s -> s.identifier.equals(research.identifier)).findFirst().get().locked.equals(ResearchProgress.COMPLETED)){
            renderTransparentTexture(BookTextures.ENTRIES, poseStack, posX + 5, posY + 6, 1, 27, 24, 25, 51, 105);
        } else {
            float mult = (float)Math.abs(Math.sin((Minecraft.getInstance().player.tickCount + partialTicks) / 5f) * 0.75f) + 0.25f;
            RenderSystem.setShaderColor(mult, mult, mult, 1f);
            poseStack.pushPose();
            renderTransparentTexture(BookTextures.ENTRIES, poseStack, posX + 5, posY + 6, 1, 27, 24, 25, 51, 105);
            poseStack.popPose();
            RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        }
        poseStack.pushPose();
        minecraft.getItemRenderer().renderAndDecorateItem(entry.iconStack, posX + 9, posY + 11);
        poseStack.popPose();
    }
    @Override
    public void lockedRender(Minecraft minecraft, PoseStack poseStack, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks) {
        int posX = offsetPosX(xOffset);
        int posY = offsetPosY(yOffset);
        renderTransparentTexture(BookTextures.ENTRIES, poseStack, posX + 5, posY + 6, 26, 27, 24, 25, 51, 105);
    }

    @Override
    public void lateRender(Minecraft minecraft, PoseStack poseStack, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks) {
        if (isHovering)
        {
            screen.renderComponentTooltip(poseStack, Arrays.asList(Component.translatable(entry.translationKey()), Component.translatable(entry.descriptionTranslationKey()).withStyle(ChatFormatting.GRAY)), mouseX, mouseY, minecraft.font);
        }
    }
    @Override
    public void lateLockedRender(Minecraft minecraft, PoseStack poseStack, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks, String... parents) {
        if (isHovering)
        {
            screen.renderComponentTooltip(poseStack, List.of(Component.translatable("astromancy.gui.book.entry." + research.identifier),Component.literal("Missing research: "),Component.literal(" - ").append(Component.translatable(Arrays.stream(parents).toList().get(0)))), mouseX, mouseY, minecraft.font);
        }
    }
}
