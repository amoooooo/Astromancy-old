package coffee.amo.astromancy.client.screen.stellalibri;

import coffee.amo.astromancy.Astromancy;
import coffee.amo.astromancy.client.screen.stellalibri.objects.BookObject;
import coffee.amo.astromancy.client.screen.stellalibri.objects.ImportantEntryObject;
import coffee.amo.astromancy.client.screen.stellalibri.pages.HeadlineTextPage;
import coffee.amo.astromancy.core.events.SetupAstromancyBookEntriesEvent;
import coffee.amo.astromancy.core.registration.ItemRegistry;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.sammy.ortus.handlers.ScreenParticleHandler;
import com.sammy.ortus.helpers.RenderHelper;
import com.sammy.ortus.systems.recipe.IRecipeComponent;
import com.sammy.ortus.systems.rendering.VFXBuilders;
import com.sammy.ortus.systems.rendering.particle.screen.base.ScreenParticle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static net.minecraft.util.FastColor.ARGB32.color;

public class BookScreen extends Screen {
    public static final VFXBuilders.ScreenVFXBuilder BUILDER = VFXBuilders.createScreen().setPosTexDefaultFormat();

    public static final ResourceLocation FRAME_TEXTURE = Astromancy.astromancy("textures/gui/book/frame.png");
    public static final ResourceLocation BACKGROUND_TEXTURE = Astromancy.astromancy("textures/gui/book/background.png");

    public int bookWidth = 256;
    public int bookHeight = 230;
    public int bookInsideWidth = 224;
    public int bookInsideHeight = 194;

    public final int parallax_width = 1024;
    public final int parallax_height = 2560;
    public static BookScreen screen;
    public float xOffset;
    public float yOffset;
    public float cachedXOffset;
    public float cachedYOffset;
    public boolean ignoreNextMouseInput;

    public static ArrayList<BookEntry> entries = new ArrayList<>();
    public static ArrayList<BookObject> objects = new ArrayList<>();

    protected BookScreen() {
        super(new TranslatableComponent("astromancy.gui.book.title"));
        minecraft = Minecraft.getInstance();
        setupEntries();
        MinecraftForge.EVENT_BUS.post(new SetupAstromancyBookEntriesEvent());
        setupObjects();
    }

    public static void setupEntries(){
        entries.clear();
        Item EMPTY = ItemStack.EMPTY.getItem();

        entries.add(new BookEntry("introduction", ItemRegistry.STELLA_LIBRI.get(), 0, 0)
                .setObjectSupplier(ImportantEntryObject::new)
                .addPage(new HeadlineTextPage("introduction", "introduction,a")));
    }

    public void setupObjects(){
        objects.clear();
        this.width = minecraft.getWindow().getGuiScaledWidth();
        this.height = minecraft.getWindow().getGuiScaledHeight();
        int guiLeft = (width - bookWidth) / 2;
        int guiTop = (height - bookHeight) / 2;
        int coreX = guiLeft + bookInsideWidth;
        int coreY = guiTop + bookInsideHeight;
        int width = 40;
        int height = 48;
        for(BookEntry entry : entries){
            objects.add(entry.objectSupplier.getBookObject(entry, coreX + entry.xOffset * width, coreY - entry.yOffset * height));
        }
        faceObject(objects.get(0));
    }

    public void faceObject(BookObject object){
        this.width = minecraft.getWindow().getGuiScaledWidth();
        this.height = minecraft.getWindow().getGuiScaledHeight();
        int guiLeft = (width - bookWidth) / 2;
        int guiTop = (height - bookHeight) / 2;
        xOffset = -object.posX + guiLeft + bookInsideWidth;
        yOffset = object.posY + guiTop + bookInsideHeight;
    }

    @Override
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pPoseStack);
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        int guiLeft = (width - bookWidth) / 2;
        int guiTop = (height - bookHeight) / 2;

        renderBackground(BACKGROUND_TEXTURE, pPoseStack, 0.1f, 0.4f);
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        cut();

        renderEntries(pPoseStack, pMouseX, pMouseY, pPartialTick);
        ScreenParticleHandler.renderParticles(ScreenParticle.RenderOrder.BEFORE_TOOLTIPS);
        GL11.glDisable(GL11.GL_SCISSOR_TEST);

        renderTexture(FRAME_TEXTURE, pPoseStack, guiLeft, guiTop, 1, 1, bookWidth, bookHeight, 512, 512);
        lateEntryRender(pPoseStack, pMouseX, pMouseY, pPartialTick);
    }

    @Override
    public boolean mouseDragged(double pMouseX, double pMouseY, int pButton, double pDragX, double pDragY) {
        xOffset += pDragX;
        yOffset += pDragY;
        return super.mouseDragged(pMouseX, pMouseY, pButton, pDragX, pDragY);
    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        cachedXOffset = xOffset;
        cachedYOffset = yOffset;
        return super.mouseClicked(pMouseX, pMouseY, pButton);
    }

    @Override
    public boolean mouseReleased(double pMouseX, double pMouseY, int pButton) {
        if(ignoreNextMouseInput){
            ignoreNextMouseInput = false;
            return super.mouseReleased(pMouseX, pMouseY, pButton);
        }
        if (xOffset != cachedXOffset || yOffset != cachedYOffset) {
            return super.mouseReleased(pMouseX, pMouseY, pButton);
        }
        for(BookObject object : objects){
            if (object.isHovering(xOffset, yOffset, pMouseX, pMouseY)){
                object.click(xOffset, yOffset, pMouseX, pMouseY);
                break;
            }
        }
        return super.mouseReleased(pMouseX, pMouseY, pButton);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
        if(Minecraft.getInstance().options.keyInventory.matches(pKeyCode, pScanCode)){
            onClose();
            return true;
        }
        return super.keyPressed(pKeyCode, pScanCode, pModifiers);
    }

    public void renderEntries(PoseStack stack, int mouseX, int mouseY, float partialTicks){
        for(int i = objects.size() - 1; i >= 0; i--){
            BookObject object = objects.get(i);
            boolean isHovering = object.isHovering(xOffset, yOffset, mouseX, mouseY);
            object.hover = isHovering ? Math.min(object.hover++, object.hoverCap()) : Math.max(object.hover--, 0);
            object.render(minecraft, stack, xOffset, yOffset, mouseX, mouseY, partialTicks);
        }
    }

    public void lateEntryRender(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        for (int i = objects.size() - 1; i >= 0; i--) {
            BookObject object = objects.get(i);
            object.lateRender(minecraft, stack, xOffset, yOffset, mouseX, mouseY, partialTicks);
        }
    }

    public static boolean isHovering(double mouseX, double mouseY, int posX, int posY, int width, int height) {
        if (!isInView(mouseX, mouseY)) {
            return false;
        }
        return mouseX > posX && mouseX < posX + width && mouseY > posY && mouseY < posY + height;
    }

    public static boolean isInView(double mouseX, double mouseY) {
        int guiLeft = (screen.width - screen.bookWidth) / 2;
        int guiTop = (screen.height - screen.bookHeight) / 2;
        return !(mouseX < guiLeft + 17) && !(mouseY < guiTop + 14) && !(mouseX > guiLeft + (screen.bookWidth - 17)) && !(mouseY > (guiTop + screen.bookHeight - 14));
    }

    public void renderBackground(ResourceLocation texture, PoseStack poseStack, float xModifier, float yModifier) {
        int guiLeft = (width - bookWidth) / 2; //TODO: literally just redo this entire garbage method, please
        int guiTop = (height - bookHeight) / 2;
        int insideLeft = guiLeft + 17;
        int insideTop = guiTop + 14;
        float uOffset = (parallax_width - xOffset) * xModifier;
        float vOffset = Math.min(parallax_height - bookInsideHeight, (parallax_height - bookInsideHeight - yOffset * yModifier));
        if (vOffset <= parallax_height / 2f) {
            vOffset = parallax_height / 2f;
        }
        if (uOffset <= 0) {
            uOffset = 0;
        }
        if (uOffset > (bookInsideWidth - 8) / 2f) {
            uOffset = (bookInsideWidth - 8) / 2f;
        }
        renderTexture(texture, poseStack, insideLeft, insideTop, uOffset, vOffset, bookInsideWidth, bookInsideHeight, parallax_width / 2, parallax_height / 2);
    }

    public void cut() {
        int scale = (int) getMinecraft().getWindow().getGuiScale();
        int guiLeft = (width - bookWidth) / 2;
        int guiTop = (height - bookHeight) / 2;
        int insideLeft = guiLeft + 17;
        int insideTop = guiTop + 18;
        GL11.glScissor(insideLeft * scale, insideTop * scale, bookInsideWidth * scale, (bookInsideHeight + 1) * scale); // do not ask why the 1 is needed please
    }

    public static void renderTexture(ResourceLocation texture, PoseStack poseStack, int x, int y, float uOffset, float vOffset, int width, int height, int textureWidth, int textureHeight) {
        BUILDER.setPositionWithWidth(x, y, width, height)
                .setShaderTexture(texture)
                .setUVWithWidth(uOffset, vOffset, textureWidth, textureHeight)
                .begin()
                .blit(poseStack)
                .end();
    }

    public static void renderTransparentTexture(ResourceLocation texture, PoseStack poseStack, int x, int y, float uOffset, float vOffset, int width, int height, int textureWidth, int textureHeight) {
        RenderSystem.enableBlend();
        RenderSystem.enableDepthTest();
        renderTexture(texture, poseStack, x, y, uOffset, vOffset, width, height, textureWidth, textureHeight);
        RenderSystem.disableDepthTest();
        RenderSystem.disableBlend();
    }

    public static void renderComponents(PoseStack poseStack, List<? extends IRecipeComponent> components, int left, int top, int mouseX, int mouseY, boolean vertical) {
        List<ItemStack> items = components.stream().map(IRecipeComponent::getStack).collect(Collectors.toList());
        BookScreen.renderItemList(poseStack, items, left, top, mouseX, mouseY, vertical);
    }

    public static void renderComponent(PoseStack poseStack, IRecipeComponent component, int posX, int posY, int mouseX, int mouseY) {
        if (component.getStacks().size() == 1) {
            renderItem(poseStack, component.getStack(), posX, posY, mouseX, mouseY);
            return;
        }
        int index = (int) (Minecraft.getInstance().level.getGameTime() % (20L * component.getStacks().size()) / 20);
        ItemStack stack = component.getStacks().get(index);
        Minecraft.getInstance().getItemRenderer().renderAndDecorateItem(stack, posX, posY);
        Minecraft.getInstance().getItemRenderer().renderGuiItemDecorations(Minecraft.getInstance().font, stack, posX, posY, null);
        if (isHovering(mouseX, mouseY, posX, posY, 16, 16)) {
            screen.renderTooltip(poseStack, new TranslatableComponent(stack.getDescriptionId()), mouseX, mouseY);
        }
    }

    public static void renderItem(PoseStack poseStack, Ingredient ingredient, int posX, int posY, int mouseX, int mouseY) {
        renderItem(poseStack, List.of(ingredient.getItems()), posX, posY, mouseX, mouseY);
    }
    public static void renderItem(PoseStack poseStack, List<ItemStack> stacks, int posX, int posY, int mouseX, int mouseY) {
        if (stacks.size() == 1) {
            renderItem(poseStack, stacks.get(0), posX, posY, mouseX, mouseY);
            return;
        }
        int index = (int) (Minecraft.getInstance().level.getGameTime() % (20L * stacks.size()) / 20);
        ItemStack stack = stacks.get(index);
        Minecraft.getInstance().getItemRenderer().renderAndDecorateItem(stack, posX, posY);
        Minecraft.getInstance().getItemRenderer().renderGuiItemDecorations(Minecraft.getInstance().font, stack, posX, posY, null);
        if (isHovering(mouseX, mouseY, posX, posY, 16, 16)) {
            screen.renderTooltip(poseStack, new TranslatableComponent(stack.getDescriptionId()), mouseX, mouseY);
        }
    }
    public static void renderItem(PoseStack poseStack, ItemStack stack, int posX, int posY, int mouseX, int mouseY) {
        Minecraft.getInstance().getItemRenderer().renderAndDecorateItem(stack, posX, posY);
        Minecraft.getInstance().getItemRenderer().renderGuiItemDecorations(Minecraft.getInstance().font, stack, posX, posY, null);
        if (isHovering(mouseX, mouseY, posX, posY, 16, 16)) {
            screen.renderTooltip(poseStack, new TranslatableComponent(stack.getDescriptionId()), mouseX, mouseY);
        }
    }
    public static void renderItemList(PoseStack poseStack, List<ItemStack> items, int left, int top, int mouseX, int mouseY, boolean vertical) {
        int slots = items.size();
        renderItemFrames(poseStack, left, top, vertical, slots);
        if (vertical) {
            top -= 10 * (slots - 1);
        } else {
            left -= 10 * (slots - 1);
        }
        for (int i = 0; i < slots; i++) {
            ItemStack stack = items.get(i);
            int offset = i * 20;
            int oLeft = left + 2 + (vertical ? 0 : offset);
            int oTop = top + 2 + (vertical ? offset : 0);
            BookScreen.renderItem(poseStack, stack, oLeft, oTop, mouseX, mouseY);
        }
    }
    public static void renderItemFrames(PoseStack poseStack, int left, int top, boolean vertical, int slots) {
        if (vertical) {
            top -= 10 * (slots - 1);
        } else {
            left -= 10 * (slots - 1);
        }
        //item slot
        for (int i = 0; i < slots; i++) {
            int offset = i * 20;
            int oLeft = left + (vertical ? 0 : offset);
            int oTop = top + (vertical ? offset : 0);
            renderTexture(EntryScreen.BOOK_TEXTURE, poseStack, oLeft, oTop, 75, 192, 20, 20, 512, 512);

            if (vertical) {
                //bottom fade
                if (slots > 1 && i != slots - 1) {
                    renderTexture(EntryScreen.BOOK_TEXTURE, poseStack, left + 1, oTop + 19, 75, 213, 18, 2, 512, 512);
                }
                //bottommost fade
                if (i == slots - 1) {
                    renderTexture(EntryScreen.BOOK_TEXTURE, poseStack, oLeft + 1, oTop + 19, 75, 216, 18, 2, 512, 512);
                }
            } else {
                //bottom fade
                renderTexture(EntryScreen.BOOK_TEXTURE, poseStack, oLeft + 1, top + 19, 75, 216, 18, 2, 512, 512);
                if (slots > 1 && i != slots - 1) {
                    //side fade
                    renderTexture(EntryScreen.BOOK_TEXTURE, poseStack, oLeft + 19, top, 96, 192, 2, 20, 512, 512);
                }
            }
        }

        //crown
        int crownLeft = left + 5 + (vertical ? 0 : 10 * (slots - 1));
        renderTexture(EntryScreen.BOOK_TEXTURE, poseStack, crownLeft, top - 5, 128, 192, 10, 6, 512, 512);

        //side bars
        if (vertical) {
            renderTexture(EntryScreen.BOOK_TEXTURE, poseStack, left - 4, top - 4, 99, 200, 28, 7, 512, 512);
            renderTexture(EntryScreen.BOOK_TEXTURE, poseStack, left - 4, top + 17 + 20 * (slots - 1), 99, 192, 28, 7, 512, 512);
        }
        // top bars
        else {
            renderTexture(EntryScreen.BOOK_TEXTURE, poseStack, left - 4, top - 4, 59, 192, 7, 28, 512, 512);
            renderTexture(EntryScreen.BOOK_TEXTURE, poseStack, left + 17 + 20 * (slots - 1), top - 4, 67, 192, 7, 28, 512, 512);
        }
    }

    public static void renderWrappingText(PoseStack mStack, String text, int x, int y, int w) {
        Font font = Minecraft.getInstance().font;
        text = new TranslatableComponent(text).getString();
        List<String> lines = new ArrayList<>();
        String[] words = text.split(" ");
        String line = "";
        for (String s : words) {
            if (font.width(line) + font.width(s) > w) {
                lines.add(line);
                line = s + " ";
            } else line += s + " ";
        }
        if (!line.isEmpty()) lines.add(line);
        for (int i = 0; i < lines.size(); i++) {
            String currentLine = lines.get(i);
            renderRawText(mStack, currentLine, x, y + i * (font.lineHeight + 1), getTextGlow(i / 4f));
        }
    }

    public static void renderText(PoseStack stack, String text, int x, int y) {
        renderText(stack, new TranslatableComponent(text), x, y, getTextGlow(0));
    }

    public static void renderText(PoseStack stack, Component component, int x, int y) {
        String text = component.getString();
        renderRawText(stack, text, x, y, getTextGlow(0));
    }

    public static void renderText(PoseStack stack, String text, int x, int y, float glow) {
        renderText(stack, new TranslatableComponent(text), x, y, glow);
    }

    public static void renderText(PoseStack stack, Component component, int x, int y, float glow) {
        String text = component.getString();
        renderRawText(stack, text, x, y, glow);
    }

    private static void renderRawText(PoseStack stack, String text, int x, int y, float glow) {
        Font font = Minecraft.getInstance().font;
        //182, 61, 183  227, 39, 228
//        int r = (int) Mth.lerp(glow, 182, 227);
//        int g = (int) Mth.lerp(glow, 61, 39);
//        int b = (int) Mth.lerp(glow, 183, 228);

        font.draw(stack, text, x - 1, y, color(96, 236, 227, 214));
        font.draw(stack, text, x + 1, y, color(128, 165, 149, 142));
        font.draw(stack, text, x, y - 1, color(128, 208, 197, 183));
        font.draw(stack, text, x, y + 1, color(96, 236, 110, 226));

        font.draw(stack, text, x, y, color(255, 111, 97, 105));
    }

    public static float getTextGlow(float offset) {
        return Mth.sin(offset + Minecraft.getInstance().player.level.getGameTime() / 40f) / 2f + 0.5f;
    }

    public void playSound() {
        Player playerEntity = Minecraft.getInstance().player;
        playerEntity.playNotifySound(SoundEvents.BOOK_PAGE_TURN, SoundSource.PLAYERS, 1.0f, 1.0f);
    }

    public static void openScreen(boolean ignoreNextMouseClick) {
        Minecraft.getInstance().setScreen(getInstance());
        ScreenParticleHandler.wipeParticles();
        screen.playSound();
        screen.ignoreNextMouseInput = ignoreNextMouseClick;
    }

    public static BookScreen getInstance() {
        if (screen == null) {
            screen = new BookScreen();
        }
        return screen;
    }
}
