package coffee.amo.astromancy.client.screen.stellalibri;

import coffee.amo.astromancy.Astromancy;
import coffee.amo.astromancy.client.screen.stellalibri.objects.EntryObject;
import coffee.amo.astromancy.client.screen.stellalibri.pages.BookPage;
import com.mojang.blaze3d.vertex.PoseStack;
import com.sammy.ortus.handlers.ScreenParticleHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;

import static coffee.amo.astromancy.client.screen.stellalibri.BookScreen.isHovering;
import static coffee.amo.astromancy.client.screen.stellalibri.BookScreen.renderTexture;
import static com.sammy.ortus.systems.rendering.particle.screen.base.ScreenParticle.RenderOrder.BEFORE_TOOLTIPS;

public class EntryScreen extends Screen {
    public static final ResourceLocation BOOK_TEXTURE = Astromancy.astromancy("textures/gui/book/entry.png");

    public static EntryScreen screen;
    public static EntryObject openObject;

    public final int bookWidth = 256;
    public final int bookHeight = 181;

    public int grouping;

    protected EntryScreen() {
        super(new TranslatableComponent("astromancy.gui.entry.title"));
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
        if (grouping < openEntry.pages.size() / 2f - 1) {
            renderTexture(BOOK_TEXTURE, ps, guiLeft + bookWidth - 32, guiTop + 164, 13, 185, 11, 6, 256, 256);
            if (isHovering(mouseX, mouseY, guiLeft + bookWidth - 32, guiTop + 164, 11, 6)) {
                renderTexture(BOOK_TEXTURE, ps, guiLeft + bookWidth - 32, guiTop + 164, 13, 190, 11, 6, 256, 256);
            } else {
                renderTexture(BOOK_TEXTURE, ps, guiLeft + bookWidth - 32, guiTop + 164, 13, 185, 11, 6, 256, 256);
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
        ScreenParticleHandler.renderParticles(BEFORE_TOOLTIPS);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int guiLeft = (width - bookWidth) / 2;
        int guiTop = (height - bookHeight) / 2;
        if (isHovering(mouseX, mouseY, guiLeft + 24, guiTop + 164, 11, 6)) {
            previousPage(true);
            return true;
        }
        if (isHovering(mouseX, mouseY, guiLeft + bookWidth - 32, guiTop + 164, 11, 6)) {
            nextPage();
            return true;
        }
        return false;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scroll) {
        if (scroll > 0) {
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
        ScreenParticleHandler.wipeParticles();
        openObject.exit();
    }

    public void playSound() {
        Player playerEntity = Minecraft.getInstance().player;
        playerEntity.playNotifySound(SoundEvents.BOOK_PAGE_TURN, SoundSource.PLAYERS, 1.0f, 1.0f);
    }

    public static void openScreen(EntryObject newObject) {
        Minecraft.getInstance().setScreen(getInstance(newObject));
        ScreenParticleHandler.wipeParticles();
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
