package coffee.amo.astromancy.client.screen.stellalibri.pages;

import coffee.amo.astromancy.Astromancy;
import coffee.amo.astromancy.client.research.ClientResearchHolder;
import coffee.amo.astromancy.client.screen.stellalibri.BookScreen;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import static coffee.amo.astromancy.client.screen.stellalibri.EntryScreen.screen;

public class StructurePage extends BookPage {
    private final BlockState[] layer1;
    private final BlockState[] layer2;
    private final BlockState[] layer3;

    public StructurePage(String research, BlockState[] layer1, BlockState[] layer2, BlockState[] layer3) {
        super(Astromancy.astromancy("textures/gui/book/pages/structure.png"), research);
        this.layer1 = layer1;
        this.layer2 = layer2;
        this.layer3 = layer3;
    }

    @Override
    public void renderLeft(Minecraft minecraft, PoseStack poseStack, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks) {
        if (hidden) {
            if (!ClientResearchHolder.isResearchCompleted(research)) {
                return;
            }
        }
        int guiLeft = guiLeft();
        int guiTop = guiTop();
        BookScreen.renderBlockState(poseStack, layer1[0], guiLeft, guiTop, mouseX, mouseY);
    }

    @Override
    public void renderRight(Minecraft minecraft, PoseStack poseStack, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks) {
        if (hidden) {
            if (!ClientResearchHolder.isResearchCompleted(research)) {
                return;
            }
        }
        int guiLeft = guiLeft();
        int guiTop = guiTop();
        poseStack.pushPose();
        for(int i = 0; i < layer1.length; i++){
            BookScreen.renderBlockState(poseStack, layer1[i], i, i, mouseX, mouseY);
        }
        //BookScreen.renderBlockState(poseStack, layer1[0], Minecraft.getInstance().renderBuffers().bufferSource(), guiLeft, guiTop, mouseX, mouseY);
        poseStack.popPose();
    }

    @Override
    public void renderBackgroundLeft(Minecraft minecraft, PoseStack poseStack, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks) {
        if (hidden) {
            if (!ClientResearchHolder.isResearchCompleted(research)) {
                int guiLeft = guiLeft();
                int guiTop = guiTop();
                BookScreen.renderTexture(PLAIN, poseStack,guiLeft, guiTop,1,1,screen.bookWidth-128, screen.bookHeight,256,256);
                return;
            }
        }
        super.renderBackgroundLeft(minecraft, poseStack, xOffset, yOffset, mouseX, mouseY, partialTicks);
    }

    @Override
    public void renderBackgroundRight(Minecraft minecraft, PoseStack poseStack, float xOffset, float yOffset, int mouseX, int mouseY, float partialTicks) {
        if (hidden) {
            if (!ClientResearchHolder.isResearchCompleted(research)) {
                int guiLeft = guiLeft();
                int guiTop = guiTop();
                BookScreen.renderTexture(PLAIN, poseStack,guiLeft+127, guiTop,128,1,screen.bookWidth-128, screen.bookHeight,256,256);
                return;
            }
        }
        super.renderBackgroundRight(minecraft, poseStack, xOffset, yOffset, mouseX, mouseY, partialTicks);
    }

    public static StructurePage cruciblePage(Block crucible){
        Block air = Blocks.AIR;
        return new StructurePage("crucible", new BlockState[]{crucible.defaultBlockState()}, new BlockState[]{air.defaultBlockState()}, new BlockState[]{air.defaultBlockState()});
    }
}
