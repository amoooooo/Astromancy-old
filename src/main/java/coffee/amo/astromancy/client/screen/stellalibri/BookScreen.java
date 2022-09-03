package coffee.amo.astromancy.client.screen.stellalibri;

import coffee.amo.astromancy.Astromancy;
import coffee.amo.astromancy.client.helper.ClientRenderHelper;
import coffee.amo.astromancy.client.research.ClientResearchHolder;
import coffee.amo.astromancy.client.screen.stellalibri.objects.BookObject;
import coffee.amo.astromancy.client.screen.stellalibri.objects.EntryObject;
import coffee.amo.astromancy.client.screen.stellalibri.objects.FleetingEntryObject;
import coffee.amo.astromancy.client.screen.stellalibri.objects.ImportantEntryObject;
import coffee.amo.astromancy.client.screen.stellalibri.pages.ResearchPageRegistry;
import coffee.amo.astromancy.client.screen.stellalibri.tab.BookTab;
import coffee.amo.astromancy.common.item.StellaLibri;
import coffee.amo.astromancy.core.events.SetupAstromancyBookEntriesEvent;
import coffee.amo.astromancy.core.events.SetupAstromancyBookTabsEvent;
import coffee.amo.astromancy.core.handlers.AstromancyPacketHandler;
import coffee.amo.astromancy.core.helpers.MathHelper;
import coffee.amo.astromancy.core.packets.BookStatePacket;
import coffee.amo.astromancy.core.systems.recipe.IRecipeComponent;
import coffee.amo.astromancy.core.systems.rendering.VFXBuilders;
import coffee.amo.astromancy.core.systems.research.*;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import mezz.jei.api.helpers.IColorHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.network.PacketDistributor;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static coffee.amo.astromancy.core.registration.ItemRegistry.*;
import static net.minecraft.util.FastColor.ARGB32.color;
import static org.lwjgl.opengl.GL11C.GL_SCISSOR_TEST;

public class BookScreen extends Screen {
    public static final VFXBuilders.ScreenVFXBuilder BUILDER = VFXBuilders.createScreen().setPosTexDefaultFormat();

    public static BookScreen screen;
    public BookTab tab;
    public static ArrayList<BookEntry> ENTRIES = new ArrayList<>();
    public static ArrayList<BookObject> OBJECTS = new ArrayList<>();
    public static ArrayList<BookTab> TABS = new ArrayList<>();
    public final int parallax_width = 1024;
    public final int parallax_height = 1024;
    private final MutableComponent lockedComponent = Component.translatable("astromancy.gui.book.entry.");
    private final MutableComponent unlockedComponent = Component.translatable("astromancy.gui.book.entry.none");
    public int bookWidth = 448;
    public int bookHeight = 260;
    public int bookInsideWidth = 418;
    public int bookInsideHeight = 233;
    public float xOffset;
    public float staticxOffset;
    public float staticyOffset;
    public float yOffset;
    public float cachedXOffset;
    public float cachedYOffset;
    public boolean ignoreNextMouseInput;

    protected BookScreen() {
        super(Component.translatable("astromancy.gui.book.title"));
        minecraft = Minecraft.getInstance();
        setupEntries();
        MinecraftForge.EVENT_BUS.post(new SetupAstromancyBookEntriesEvent());
        setupObjects();
        setupTabs();
        MinecraftForge.EVENT_BUS.post(new SetupAstromancyBookTabsEvent());
        setTab(TABS.stream().filter(tab -> tab.identifier.equals("introduction")).findFirst().orElse(TABS.get(0)));
    }

    public static void setupEntries() {
        ENTRIES.clear();
        Item EMPTY = ItemStack.EMPTY.getItem();

        List<ResearchType> researchObjects = ResearchTypeRegistry.RESEARCH_TYPES.get().getValues().stream().toList();
        for (ResearchType type : researchObjects) {
            ResearchObject object = (ResearchObject) type;
            BookEntry be = new BookEntry(object.identifier, object.x, object.y, object);
            ResearchPageRegistry.pages.forEach((rl, page) -> {
                if ("important".equals(object.type)) {
                    be.setObjectSupplier(ImportantEntryObject::new);
                } else if("fleeting".equals(object.type)) {
                    be.setObjectSupplier(FleetingEntryObject::new);
                } else {
                    be.setObjectSupplier(EntryObject::new);
                }
                page.forEach((index, bp) -> {
                    if(object.getResearchName().equals(rl)){
                        be.addPage(bp);
                    }
                });
                ENTRIES.add(be);
            });
            System.out.println("REGISTERING RESEARCH: " + object.identifier);
        }
    }

    public static void setupTabs(){
        TABS.clear();
        List<ResearchTabType> tabObjects = ResearchTypeRegistry.RESEARCH_TABS.get().getValues().stream().toList();
        for(ResearchTabType type : tabObjects){
            ResearchTabObject object = (ResearchTabObject) type;
            BookTab tab;
            if(object.backgroundLocation == null){
                tab = new BookTab(object.x, object.y, object.identifier, 0, 0, object.icon, object.backgroundLocations);
                tab.tabColor = object.tabColor;
            } else {
                tab = new BookTab(object.x, object.y, object.identifier, 0, 0, object.icon, object.backgroundLocation);
                tab.tabColor = object.tabColor;
            }
            tab.iconStack = object.icon;
            object.children.forEach(child -> {
                OBJECTS.stream().filter(s -> s.identifier.equals(child.identifier)).findFirst().ifPresent(tab::addEntry);
                OBJECTS.stream().filter(s -> s.identifier.equals(child.identifier)).findFirst().ifPresent(s -> {
                    System.out.println("REGISTERING TAB: " + tab.identifier + " with entry: " + s.identifier);
                });
            });
            TABS.add(tab);
        }
        Astromancy.LOGGER.debug("Tabs: " + TABS.size());
        //aaaaaa
    }

    public static boolean isHovering(double mouseX, double mouseY, int posX, int posY, int width, int height) {
        if (!isInView(mouseX, mouseY)) {
            return false;
        }
        return mouseX > posX && mouseX < posX + width && mouseY > posY && mouseY < posY + height;
    }
    public static boolean isHoveringTab(double mouseX, double mouseY, int posX, int posY, int width, int height) {
        return mouseX > posX && mouseX < posX + width && mouseY > posY && mouseY < posY + height;
    }

    public static boolean isInView(double mouseX, double mouseY) {
        int guiLeft = (screen.width - screen.bookWidth) / 2;
        int guiTop = (screen.height - screen.bookHeight) / 2;
        return !(mouseX < guiLeft + 17) && !(mouseY < guiTop + 14) && !(mouseX > guiLeft + (screen.bookWidth - 17)) && !(mouseY > (guiTop + screen.bookHeight - 14));
    }

    public static void renderTexture(ResourceLocation texture, PoseStack poseStack, float x, float y, float u, float v, int width, int height, int textureWidth, int textureHeight) {
        BUILDER.setPositionWithWidth(x, y, width, height)
                .setShaderTexture(texture)
                .setUVWithWidth(u, v, width, height, textureWidth, textureHeight)
                .draw(poseStack);
    }

    public static void renderTransparentTexture(ResourceLocation texture, PoseStack poseStack, float x, float y, float uOffset, float vOffset, int width, int height, int textureWidth, int textureHeight) {
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
            screen.renderTooltip(poseStack, Component.translatable(stack.getDescriptionId()), mouseX, mouseY);
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
            screen.renderTooltip(poseStack, Component.translatable(stack.getDescriptionId()), mouseX, mouseY);
        }
    }

    public static void renderItem(PoseStack poseStack, ItemStack stack, int posX, int posY, int mouseX, int mouseY) {
        Minecraft.getInstance().getItemRenderer().renderAndDecorateItem(stack, posX, posY);
        Minecraft.getInstance().getItemRenderer().renderGuiItemDecorations(Minecraft.getInstance().font, stack, posX, posY, null);
        if (isHovering(mouseX, mouseY, posX, posY, 16, 16)) {
            screen.renderTooltip(poseStack, Component.translatable(stack.getDescriptionId()), mouseX, mouseY);
        }
    }

    public static void renderBlockState(PoseStack poseStack, BlockState state, int posX, int posY, int mouseX, int mouseY) {
        poseStack.pushPose();
        poseStack.translate(16.75 + posX,-8.9 + posY,0);
        poseStack.mulPose(Vector3f.XP.rotationDegrees(35));
        poseStack.mulPose(Vector3f.YP.rotationDegrees(45));
        Minecraft.getInstance().getBlockRenderer().renderSingleBlock(state, poseStack, Minecraft.getInstance().renderBuffers().bufferSource(), 15728640, OverlayTexture.NO_OVERLAY, ModelData.EMPTY, RenderType.solid());
        poseStack.popPose();
        //Minecraft.getInstance().getItemRenderer().renderAndDecorateItem(state.getBlock().asItem().getDefaultInstance(), posX, posY);
        //Minecraft.getInstance().renderBuffers().bufferSource().endBatch();
        if (isHovering(mouseX, mouseY, posX,posY, 16, 16)) {
            screen.renderTooltip(poseStack, Component.translatable(state.getBlock().asItem().getDescriptionId()), mouseX, mouseY);
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
            renderTexture(EntryScreen.BOOK_TEXTURE, poseStack, oLeft, oTop, 0, 255, 26, 26, 256, 256);

            if (vertical) {
                //bottom fade
                if (slots > 1 && i != slots - 1) {
                    renderTexture(EntryScreen.BOOK_TEXTURE, poseStack, left + 1, oTop + 19, 75, 213, 18, 2, 256, 256);
                }
                //bottommost fade
                if (i == slots - 1) {
                    renderTexture(EntryScreen.BOOK_TEXTURE, poseStack, oLeft + 1, oTop + 19, 75, 216, 18, 2, 256, 256);
                }
            } else {
                //bottom fade
                renderTexture(EntryScreen.BOOK_TEXTURE, poseStack, oLeft + 1, top + 19, 75, 216, 18, 2, 256, 256);
                if (slots > 1 && i != slots - 1) {
                    //side fade
                    renderTexture(EntryScreen.BOOK_TEXTURE, poseStack, oLeft + 19, top, 96, 192, 2, 20, 256, 256);
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
        text = Component.translatable(text).getString();
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

    public static void renderWrappingText(PoseStack mStack, String text, int x, int y, int w, boolean shadow, Color color) {
        Font font = Minecraft.getInstance().font;
        text = Component.translatable(text).getString();
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
            renderRawText(mStack, currentLine, x, y + i * (font.lineHeight + 1), getTextGlow(i / 4f), shadow, color);
        }
    }

    public static void renderText(PoseStack stack, String text, int x, int y) {
        renderText(stack, Component.translatable(text), x, y, getTextGlow(0));
    }

    public static void renderText(PoseStack stack, Component component, int x, int y) {
        String text = component.getString();
        renderRawText(stack, text, x, y, getTextGlow(0));
    }

    public static void renderText(PoseStack stack, String text, int x, int y, float glow) {
        renderText(stack, Component.translatable(text), x, y, glow);
    }

    public static void renderText(PoseStack stack, Component component, int x, int y, float glow) {
        String text = component.getString();
        renderRawText(stack, text, x, y, glow);
    }

    private static void renderRawText(PoseStack stack, String text, int x, int y, float glow) {
        Font font = Minecraft.getInstance().font;
        //182, 61, 183  227, 39, 228
        int r = (int) Mth.lerp(glow, 2, 16);
        int g = (int) Mth.lerp(glow, 2, 16);
        int b = (int) Mth.lerp(glow, 2, 16);
        font.draw(stack, text, x + 1, y + 1, color(96, 128, 128, 128));
        font.draw(stack, text, x - 1, y - 1, color(96, 255, 255, 255));

        font.draw(stack, text, x, y, color(255, r, g, b));
    }

    private static void renderRawText(PoseStack stack, String text, int x, int y, float glow, boolean shadow, Color color) {
        Font font = Minecraft.getInstance().font;
        //182, 61, 183  227, 39, 228
        int r = (int) Mth.lerp(glow, 2, 16);
        int g = (int) Mth.lerp(glow, 2, 16);
        int b = (int) Mth.lerp(glow, 2, 16);
        Color shade = new Color(Integer.parseInt("888888", 16));
        font.draw(stack, text, x + 1, y + 1, color(254, shade.getRed(), shade.getGreen(), shade.getBlue()));
        stack.translate(0,0,-0.01f);
        //font.draw(stack, text, x - 1, y - 1, color(96, 255, 255, 255));

        font.draw(stack, text, x, y, color(255, color.getRed(), color.getGreen(), color.getBlue()));

    }

    public static float getTextGlow(float offset) {
        return Mth.sin(offset + Minecraft.getInstance().player.level.getGameTime() / 40f) / 2f + 0.5f;
    }

    public static void openScreen(boolean ignoreNextMouseClick) {
        Minecraft.getInstance().setScreen(getInstance());
        screen.playSound();
        screen.ignoreNextMouseInput = ignoreNextMouseClick;
    }

    public static BookScreen getInstance() {
        if (screen == null) {
            screen = new BookScreen();
        }
        return screen;
    }

    public void setupObjects() {
        OBJECTS.clear();
        this.width = minecraft.getWindow().getGuiScaledWidth();
        this.height = minecraft.getWindow().getGuiScaledHeight();
        int guiLeft = (width - bookWidth) / 2;
        int guiTop = (height - bookHeight) / 2;
        int coreX = guiLeft + bookInsideWidth;
        int coreY = guiTop + bookInsideHeight;
        int width = 40;
        int height = 48;
        for (BookEntry entry : ENTRIES) {
            OBJECTS.add(entry.objectSupplier.getBookObject(entry, coreX + entry.xOffset * width, coreY - entry.yOffset * height, entry.children, entry.xOffset, entry.yOffset, entry.research, entry.unlocks));
        }
        for (BookObject object : OBJECTS) {
            for(ResearchObject child : object.research.children) {
                if(object.research.children.contains(child)) {
                    object.children.add(OBJECTS.stream().filter(s -> s.research == child).findFirst().orElse(null));
                }
            }
        }
        faceObject(OBJECTS.get(0));
    }

    public void faceObject(BookObject object) {
        this.width = minecraft.getWindow().getGuiScaledWidth();
        this.height = minecraft.getWindow().getGuiScaledHeight();
        int guiLeft = (width - bookWidth) / 2;
        int guiTop = (height - bookHeight) / 2;
        xOffset = -object.posX + guiLeft + bookInsideWidth;
        staticxOffset = -object.posX + guiLeft + bookInsideWidth;
        yOffset = -object.posY + guiTop + bookInsideHeight;
        staticyOffset = -object.posY + guiTop + bookInsideHeight;
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        super.render(poseStack, mouseX, mouseY, partialTicks);
        renderBackground(poseStack);
        int guiLeft = (width - bookWidth) / 2;
        int guiTop = (height - bookHeight) / 2;
        renderTabs(poseStack, mouseX, mouseY, partialTicks, guiLeft, guiTop);
        if(tab.BACKGROUND != null){
            renderBackground(tab.BACKGROUND, poseStack, 0.05f, 0.05f);
        } else {
            renderBackground(tab.backgroundParallax, poseStack, 0.095f, 0.095f);

        }
        GL11.glEnable(GL_SCISSOR_TEST);
        cut();
        renderEntries(poseStack, mouseX, mouseY, partialTicks);
        GL11.glDisable(GL_SCISSOR_TEST);
        poseStack.pushPose();
        poseStack.translate(0,0,500);
        renderTransparentTexture(BookTextures.OUTSIDE_LOC, poseStack, guiLeft, guiTop, 0, 0, bookWidth, bookHeight, 448, 260);
        poseStack.popPose();
        lateTabRender(poseStack, mouseX, mouseY, partialTicks, guiLeft, guiTop);
        lateEntryRender(poseStack, mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        xOffset += dragX;
        yOffset += dragY;
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        cachedXOffset = xOffset;
        cachedYOffset = yOffset;
        int guiLeft = (width - bookWidth) / 2;
        int guiTop = (height - bookHeight) / 2;
        for (BookTab tab : TABS) {
            if (tab.isHovering(guiLeft, guiTop, mouseX, mouseY) && (ClientResearchHolder.contains(tab.identifier) || anyMatch(ClientResearchHolder.getResearch().stream().map(r -> r.identifier).toList(), tab.entries))) {
                Minecraft.getInstance().player.playNotifySound(SoundEvents.UI_BUTTON_CLICK, SoundSource.MASTER, 0.5f, 1.0f);
                tab.click(guiLeft, guiTop, mouseX, mouseY);
                break;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {

        if (ignoreNextMouseInput) {
            ignoreNextMouseInput = false;
            return super.mouseReleased(mouseX, mouseY, button);
        }
        if (xOffset != cachedXOffset || yOffset != cachedYOffset) {
            return super.mouseReleased(mouseX, mouseY, button);
        }
        for (BookObject object : OBJECTS) {
            if(object.research.type.equals("fleeting") && !(object.research.locked.equals(ResearchProgress.IN_PROGRESS) || object.research.locked.equals(ResearchProgress.COMPLETED))) continue;
            if(tab.entries.contains(object) && (object.research.locked.equals(ResearchProgress.IN_PROGRESS) || object.research.locked.equals(ResearchProgress.COMPLETED))) {
                if (object.isHovering(xOffset, yOffset, mouseX, mouseY)) {
                    object.click(xOffset, yOffset, mouseX, mouseY);
                    break;
                }
            } else if (tab.entries.contains(object) && object.research.locked.equals(ResearchProgress.LOCKED)) {
                if (object.isHovering(xOffset, yOffset, mouseX, mouseY)) {
                    object.clickLocked(xOffset, yOffset, mouseX, mouseY);
                    break;
                }
            }
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (Minecraft.getInstance().options.keyInventory.matches(keyCode, scanCode)) {
            onClose();
            if (Minecraft.getInstance().player.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof StellaLibri sl) {
                AstromancyPacketHandler.INSTANCE.send(PacketDistributor.SERVER.noArg(), new BookStatePacket(0));
            }
            return true;
        } else if (keyCode == GLFW.GLFW_KEY_ESCAPE) {
            onClose();
            if (Minecraft.getInstance().player.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof StellaLibri sl) {
                AstromancyPacketHandler.INSTANCE.send(PacketDistributor.SERVER.noArg(), new BookStatePacket(0));
            }
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    public void renderEntries(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        for (int i = OBJECTS.size() - 1; i >= 0; i--) {
            BookObject object = OBJECTS.get(i);
            if(tab.entries.contains(object)){
                if (ClientResearchHolder.contains(object.identifier) && (object.research.locked.equals(ResearchProgress.COMPLETED) || object.research.locked.equals(ResearchProgress.IN_PROGRESS))) {
                    boolean isHovering = object.isHovering(xOffset, yOffset, mouseX, mouseY);
                    object.isHovering = isHovering;
                    object.hover = isHovering ? Math.min(object.hover++, object.hoverCap()) : Math.max(object.hover--, 0);
                    object.render(minecraft, stack, xOffset, yOffset, mouseX, mouseY, partialTicks);
                    if (!object.children.isEmpty() && (object.research.locked.equals(ResearchProgress.COMPLETED) || object.research.locked.equals(ResearchProgress.IN_PROGRESS))) {
                        object.children.forEach(c -> {
                            if(!c.isRendered){
                                if(ClientResearchHolder.contains(c.identifier) && (c.research.locked.equals(ResearchProgress.LOCKED) || c.research.locked.equals(ResearchProgress.COMPLETED) || c.research.locked.equals(ResearchProgress.IN_PROGRESS))){
                                    ClientResearchHolder.getResearch().stream().filter(r -> r.identifier.equals(c.identifier)).findFirst().ifPresent(b -> {
                                        if(!anyMatch(b.children.stream().map(e -> e.identifier).toList(), c.children)){
                                            boolean isHovering2 = c.isHovering(xOffset, yOffset, mouseX, mouseY);
                                            c.isHovering = isHovering2;
                                            c.hover = isHovering2 ? Math.min(c.hover++, c.hoverCap()) : Math.max(c.hover--, 0);
                                            c.render(minecraft, stack, xOffset, yOffset, mouseX, mouseY, partialTicks);
                                            c.isRendered = true;
                                        }
                                    });
                                } else {
                                    if(c.isRendered) c.isRendered = false;
                                    boolean isHovering2 = c.isHovering(xOffset, yOffset, mouseX, mouseY);
                                    c.isHovering = isHovering2;
                                    c.hover = isHovering2 ? Math.min(c.hover++, c.hoverCap()) : Math.max(c.hover--, 0);
                                    c.lockedRender(minecraft, stack, xOffset, yOffset, mouseX, mouseY, partialTicks);
                                    c.isRendered = true;
                                }
                            }
                        });
                    }
                } else if (object instanceof ImportantEntryObject){
                    boolean isHovering = object.isHovering(xOffset, yOffset, mouseX, mouseY);
                    object.isHovering = isHovering;
                    object.hover = isHovering ? Math.min(object.hover++, object.hoverCap()) : Math.max(object.hover--, 0);
                    object.render(minecraft, stack, xOffset, yOffset, mouseX, mouseY, partialTicks);
                }
            }
        }
        for(BookObject b : OBJECTS){
            b.isRendered = false;
        }
    }

    public void renderTabs(PoseStack stack, int mouseX, int mouseY, float partialTicks, int guiLeft, int guiTop) {
        for (int i = TABS.size() - 1; i >= 0; i--){
            int finalI = i;
            int finalI1 = i;
            ResearchTypeRegistry.RESEARCH_TABS.get().getValues().forEach(s -> {
                ResearchTabObject t = (ResearchTabObject) s;
                if(anyMatch(ClientResearchHolder.getResearch().stream().map(r -> r.identifier).toList(), TABS.get(finalI).entries)){
                    BookTab tab = TABS.get(finalI);
                    boolean isHovering = tab.isHovering(guiLeft, guiTop, mouseX, mouseY);
                    tab.isHovering = isHovering;
                    tab.hover = isHovering ? Math.min(tab.hover++, tab.hoverCap()/20f) : Math.max(tab.hover--, 0);
                    tab.render(minecraft, stack, guiLeft, guiTop, mouseX, mouseY, partialTicks, this.tab == tab);
                }
            });
        }
    }

    public void lateTabRender(PoseStack stack, int mouseX, int mouseY, float partialTicks, int guiLeft, int guiTop) {
        for (int i = TABS.size() - 1; i >= 0; i--){
            int finalI = i;
            ResearchTypeRegistry.RESEARCH_TABS.get().getValues().forEach(s -> {
                ResearchTabObject t = (ResearchTabObject) s;
                if(anyMatch(t.children.stream().map(c -> c.identifier).toList(), TABS.get(finalI).entries)){
                    BookTab tab = TABS.get(finalI);
                    tab.lateRender(minecraft, stack, guiLeft, guiTop, mouseX, mouseY, partialTicks);
                }
            });
        }
    }

    public void lateEntryRender(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        for (int i = OBJECTS.size() - 1; i >= 0; i--) {
            BookObject object = OBJECTS.get(i);
            if (ClientResearchHolder.contains(object.identifier)) {
                object.lateRender(minecraft, stack, xOffset, yOffset, mouseX, mouseY, partialTicks);
                if (!object.children.isEmpty()) {
                    object.children.forEach(c -> {
                        if(!c.isRendered){
                            if(ClientResearchHolder.contains(c.identifier)){
                                ClientResearchHolder.getResearch().stream().filter(r -> r.identifier.equals(c.identifier)).findFirst().ifPresent(b -> {
                                    if(!anyMatch(b.children.stream().map(e -> e.identifier).toList(), c.children)){
                                        c.lateRender(minecraft, stack, xOffset, yOffset, mouseX, mouseY, partialTicks);
                                        c.isRendered = true;
                                    }
                                });
                            } else {
                                MutableComponent lock = object.research.locked.equals(ResearchProgress.COMPLETED) ? unlockedComponent : lockedComponent.copy().append(object.identifier);
                                c.lateLockedRender(minecraft, stack, xOffset, yOffset, mouseX, mouseY, partialTicks, lock.getString());
                                c.isRendered = true;
                            }
                        }
                    });
                }
            }
        }
        for(BookObject b : OBJECTS){
            b.isRendered = false;
        }
    }

    public void renderBackground(ResourceLocation texture, PoseStack poseStack, float xModifier, float yModifier) {
        int guiLeft = (width - bookWidth) / 2;
        int guiTop = (height - bookHeight) / 2;
        int insideLeft = guiLeft + 13;
        int insideTop = guiTop + 12;
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
        renderTexture(texture, poseStack, insideLeft, insideTop, uOffset, vOffset, bookInsideWidth, bookInsideHeight, Math.round(parallax_width / 2.1f), Math.round(parallax_height / 2.1f));
    }

    public void renderBackground(List<ResourceLocation> textures, PoseStack poseStack, float xModifier, float yModifier) {
        int guiLeft = (width - bookWidth) / 2;
        int guiTop = (height - bookHeight) / 2;
        int insideLeft = guiLeft + 13;
        int insideTop = guiTop + 12;
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
        long dayTime = Minecraft.getInstance().level.getGameTime();
        for(int i = 0; i < textures.size(); i++){
            if(i > 1){
                uOffset *= -Math.sin(dayTime / 9500f) >= 0 ? -Math.sin(dayTime / 9500f) * (dayTime / 5000f) : Math.sin(dayTime / 9500f) * (dayTime / 5000f);
                vOffset *= -Math.sin(dayTime / 9500f) >= 0 ? -Math.sin(dayTime / 9500f) * (dayTime / 6000f) : Math.sin(dayTime / 9500f) * (dayTime / 6000f);
            }
           float mult = Math.abs((float) Math.cos(dayTime / 6000f) * (dayTime / 2000f)) - (int)Math.abs((float) Math.cos(dayTime / 6000f) * (dayTime / 2000f));
            renderTransparentTexture(textures.get(i), poseStack, insideLeft, insideTop, uOffset * (i /150f), vOffset * (i/150f), bookInsideWidth, bookInsideHeight, Math.round(parallax_width / 2f), Math.round(parallax_height / 2f));
        }
    }

    public void cut() {
        int scale = (int) getMinecraft().getWindow().getGuiScale();
        int guiLeft = (width - bookWidth) / 2;
        int guiTop = (height - bookHeight) / 2;
        int insideLeft = guiLeft + 13;
        int insideTop = guiTop + 12;
        GL11.glScissor(insideLeft * scale, insideTop * scale, bookInsideWidth * scale, (bookInsideHeight + 1) * scale); // do not ask why the 1 is needed please
    }

    public void playSound() {
        Player playerEntity = Minecraft.getInstance().player;
        playerEntity.playNotifySound(SoundEvents.BOOK_PAGE_TURN, SoundSource.PLAYERS, 1.0f, 1.0f);
    }

    public boolean anyMatch(List<String> list1, List<BookObject> list2) {
        for (BookObject object : list2) {
            if (list1.contains(object.identifier)) {
                return true;
            }
        }
        return false;
    }

    public void setTab(BookTab tab) {
        this.tab = tab;
    }
}
