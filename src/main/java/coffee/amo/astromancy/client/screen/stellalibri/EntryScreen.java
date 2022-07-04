package coffee.amo.astromancy.client.screen.stellalibri;

import coffee.amo.astromancy.Astromancy;
import coffee.amo.astromancy.client.screen.stellalibri.objects.EntryObject;
import coffee.amo.astromancy.client.screen.stellalibri.pages.BookPage;
import coffee.amo.astromancy.core.handlers.AstromancyPacketHandler;
import coffee.amo.astromancy.core.packets.ResearchNotePacket;
import coffee.amo.astromancy.core.packets.ServerboundResearchPacket;
import coffee.amo.astromancy.core.registration.ItemRegistry;
import coffee.amo.astromancy.core.registration.SoundRegistry;
import coffee.amo.astromancy.core.systems.research.ResearchProgress;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraftforge.network.PacketDistributor;

import static coffee.amo.astromancy.client.screen.stellalibri.BookScreen.isHovering;
import static coffee.amo.astromancy.client.screen.stellalibri.BookScreen.renderTexture;

public class EntryScreen extends Screen {
    public static final ResourceLocation BOOK_TEXTURE = Astromancy.astromancy("textures/gui/book/entry.png");
    public static final ResourceLocation UNREAD_TEXTURE = Astromancy.astromancy("textures/gui/book/pages/unread.png");

    public static EntryScreen screen;
    public static EntryObject openObject;
    private boolean cannotResearch;
    private long delayTime;
    private static Component cannotResearchMessage1 = Component.translatable("astromancy.research.ongoing1");
    private static Component cannotResearchMessage2 = Component.translatable("astromancy.research.ongoing2");
    private static Component noSuppliesMessage1 = Component.translatable("astromancy.research.nosupplies1");
    private static Component noSuppliesMessage2 = Component.translatable("astromancy.research.nosupplies2");

    public final int bookWidth = 256;
    public final int bookHeight = 181;

    public int grouping;

    protected EntryScreen() {
        super(Component.translatable("astromancy.gui.entry.title"));
    }

    @Override
    public void render(PoseStack ps, int mouseX, int mouseY, float partialTicks) {
        BookEntry openEntry = openObject.entry;
        renderBackground(ps);
        super.render(ps, mouseX, mouseY, partialTicks);
        int guiLeft = (width - bookWidth) / 2;
        int guiTop = (height - bookHeight) / 2;
        renderTexture(BOOK_TEXTURE, ps, guiLeft, guiTop, 1, 1, bookWidth, bookHeight, 256, 256);
        if (!openEntry.pages.isEmpty()) {
            int openPages = grouping * 2;
            for (int i = openPages; i < openPages + 2; i++) {
                if (i < openEntry.pages.size()) {
                    BookPage page = openEntry.pages.get(i);
                    if (i % 2 == 0) {
                        page.renderBackgroundLeft(minecraft, ps, BookScreen.screen.xOffset, BookScreen.screen.yOffset, mouseX, mouseY, partialTicks);
                    } else {
                        page.renderBackgroundRight(minecraft, ps, BookScreen.screen.xOffset, BookScreen.screen.yOffset, mouseX, mouseY, partialTicks);
                    }
                }
            }
        }
        renderTexture(BOOK_TEXTURE, ps, guiLeft + 24, guiTop + 164, 1, 185, 11, 6, 256, 256);
        if (isHovering(mouseX, mouseY, guiLeft + 24, guiTop + 164, 11, 6)) {
            renderTexture(BOOK_TEXTURE, ps, guiLeft + 24, guiTop + 164, 1, 190, 11, 6, 256, 256);
        } else {
            renderTexture(BOOK_TEXTURE, ps, guiLeft + 24, guiTop + 164, 1, 185, 11, 6, 256, 256);
        }
        if(openObject.research.locked.equals(ResearchProgress.IN_PROGRESS) && isHovering(mouseX, mouseY, guiLeft + 58, guiTop + 158, 16, 16)){
            renderTexture(UNREAD_TEXTURE, ps, guiLeft + 58, guiTop + 158, 16, 0, 16, 16, 48, 16);
        } else if (openObject.research.locked.equals(ResearchProgress.COMPLETED)) {
            renderTexture(UNREAD_TEXTURE, ps, guiLeft + 58, guiTop + 158, 32, 0, 16, 16, 48, 16);
        } else {
            renderTexture(UNREAD_TEXTURE, ps, guiLeft + 58, guiTop + 158, 0, 0, 16, 16, 48, 16);
        }
        if (grouping < openEntry.pages.size() / 2f - 1 && openObject.research.locked.equals(ResearchProgress.COMPLETED)) {
            renderTexture(BOOK_TEXTURE, ps, guiLeft + bookWidth - 32, guiTop + 164, 13, 185, 11, 6, 256, 256);
            if (isHovering(mouseX, mouseY, guiLeft + bookWidth - 32, guiTop + 164, 11, 6)) {
                renderTexture(BOOK_TEXTURE, ps, guiLeft + bookWidth - 32, guiTop + 164, 13, 190, 11, 6, 256, 256);
            } else {
                renderTexture(BOOK_TEXTURE, ps, guiLeft + bookWidth - 32, guiTop + 164, 13, 185, 11, 6, 256, 256);
            }
        }
        cannotResearch = Minecraft.getInstance().player.level.getGameTime() < delayTime;
        if(cannotResearch){
            if(Minecraft.getInstance().player.getInventory().contains(ItemRegistry.RESEARCH_NOTE.get().getDefaultInstance())){
                ps.pushPose();
                ps.scale(0.65f, 0.65f, 0.65f);
                font.draw(ps,cannotResearchMessage1, guiLeft + 84, guiTop + 310, 0xFFFF0000);
                font.drawShadow(ps,cannotResearchMessage1, guiLeft + 84, guiTop + 311, 0x22FF0000);
                font.drawShadow(ps,cannotResearchMessage1, guiLeft + 85, guiTop + 310, 0x22FF0000);
                font.draw(ps,cannotResearchMessage2, guiLeft + 84, guiTop + 310 + 10, 0xFFFF0000);
                font.drawShadow(ps,cannotResearchMessage2, guiLeft + 85, guiTop + 310 + 10, 0x22FF0000);
                font.drawShadow(ps,cannotResearchMessage2, guiLeft + 84, guiTop + 311 + 10, 0x22FF0000);
                ps.popPose();
            } else {
                ps.pushPose();
                ps.scale(0.65f, 0.65f, 0.65f);
                font.draw(ps,noSuppliesMessage1, guiLeft + 84, guiTop + 310, 0xFFFF0000);
                font.drawShadow(ps,noSuppliesMessage1, guiLeft + 84, guiTop + 311, 0x22FF0000);
                font.drawShadow(ps,noSuppliesMessage1, guiLeft + 85, guiTop + 310, 0x22FF0000);
                font.draw(ps,noSuppliesMessage2, guiLeft + 84, guiTop + 310 + 10, 0xFFFF0000);
                font.drawShadow(ps,noSuppliesMessage2, guiLeft + 85, guiTop + 310 + 10, 0x22FF0000);
                font.drawShadow(ps,noSuppliesMessage2, guiLeft + 84, guiTop + 311 + 10, 0x22FF0000);
                ps.popPose();
            }
        }
        if (!openEntry.pages.isEmpty()) {
            int openPages = grouping * 2;
            for (int i = openPages; i < openPages + 2; i++) {
                if (i < openEntry.pages.size()) {
                    BookPage page = openEntry.pages.get(i);
                    if (i % 2 == 0) {
                        page.renderLeft(minecraft, ps, BookScreen.screen.xOffset, BookScreen.screen.yOffset, mouseX, mouseY, partialTicks);
                    } else {
                        page.renderRight(minecraft, ps, BookScreen.screen.xOffset, BookScreen.screen.yOffset, mouseX, mouseY, partialTicks);
                    }
                }
            }
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int guiLeft = (width - bookWidth) / 2;
        int guiTop = (height - bookHeight) / 2;
        if (isHovering(mouseX, mouseY, guiLeft + 24, guiTop + 164, 11, 6)) {
            previousPage(true);
            return true;
        }
        if(isHovering(mouseX, mouseY, guiLeft + 58, guiTop + 158, 16, 16)){
            if(openObject.research.locked.equals(ResearchProgress.IN_PROGRESS) && !Minecraft.getInstance().player.getInventory().contains(ItemRegistry.RESEARCH_NOTE.get().getDefaultInstance())){
                if(!Minecraft.getInstance().player.getInventory().contains(Items.PAPER.getDefaultInstance()) || !Minecraft.getInstance().player.getInventory().contains(Items.INK_SAC.getDefaultInstance())){
                    delayTime = Minecraft.getInstance().player.level.getGameTime() + 40;
                    Minecraft.getInstance().player.playNotifySound(SoundEvents.VILLAGER_NO, SoundSource.MASTER, 1.0f, 1.0f);
                    return true;
                }
                AstromancyPacketHandler.INSTANCE.send(PacketDistributor.SERVER.noArg(), new ResearchNotePacket(openObject.identifier));
                Minecraft.getInstance().player.playNotifySound(SoundRegistry.RESEARCH_WRITE.get(), SoundSource.MASTER, 1.0f, 1.0f);
            } else if (!openObject.research.locked.equals(ResearchProgress.COMPLETED)){
                delayTime = Minecraft.getInstance().player.level.getGameTime() + 40;
                Minecraft.getInstance().player.playNotifySound(SoundEvents.VILLAGER_NO, SoundSource.MASTER, 1.0f, 1.0f);
            }
        }
        if (isHovering(mouseX, mouseY, guiLeft + bookWidth - 32, guiTop + 164, 11, 6)) {
            nextPage();
            return true;
        }
        return false;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scroll) {
        if (scroll > 0 && openObject.research.locked.equals(ResearchProgress.COMPLETED)) {
            nextPage();
        } else {
            previousPage(false);
        }
        return super.mouseScrolled(mouseX, mouseY, scroll);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (Minecraft.getInstance().options.keyInventory.matches(keyCode, scanCode)) {
            close(false);
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public void onClose() {
        close(false);
    }

    public void nextPage() {
        if (grouping < openObject.entry.pages.size() / 2f - 1) {
            grouping += 1;
            screen.playSound();
        }
    }

    public void previousPage(boolean ignore) {
        if (grouping > 0) {
            grouping -= 1;
            screen.playSound();
        } else {
            close(ignore);
        }
    }

    public void close(boolean ignoreNextInput) {
        BookScreen.openScreen(ignoreNextInput);
        openObject.exit();
    }

    public void playSound() {
        Player playerEntity = Minecraft.getInstance().player;
        playerEntity.playNotifySound(SoundEvents.BOOK_PAGE_TURN, SoundSource.PLAYERS, 1.0f, 1.0f);
    }

    public static void openScreen(EntryObject newObject) {
        Minecraft.getInstance().setScreen(getInstance(newObject));
        screen.playSound();
    }

    public static EntryScreen getInstance(EntryObject newObject) {
        if (screen == null || !newObject.equals(openObject)) {
            screen = new EntryScreen();
            openObject = newObject;
        }
        return screen;
    }
}
