package coffee.amo.astromancy.client.renderer.item;

import coffee.amo.astromancy.Astromancy;
import coffee.amo.astromancy.client.screen.stellalibri.BookScreen;
import coffee.amo.astromancy.common.item.StoneTabletItem;
import coffee.amo.astromancy.core.helpers.MathHelper;
import coffee.amo.astromancy.core.helpers.RenderHelper;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.awt.*;
import java.util.List;
import java.util.Map;

@Mod.EventBusSubscriber(modid = Astromancy.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class StoneTabletRenderer {
    public static final StoneTabletModel bookModel = new StoneTabletModel();
    public static final ResourceLocation bookTexture = Astromancy.astromancy("textures/item/stone_tablet_hand.png");
    private static final Minecraft mc = Minecraft.getInstance();

    @SubscribeEvent
    public static void onRenderHand(RenderHandEvent event) {
        if (event.getItemStack().getItem() instanceof StoneTabletItem) {
            event.setCanceled(true);
            PoseStack stack = event.getPoseStack();
            float xRot = event.getInterpolatedPitch(); //Minecraft.getInstance().cameraEntity.getViewXRot(event.getPartialTick());
            float yVRot = Minecraft.getInstance().cameraEntity.getViewYRot(event.getPartialTick());
            float yRot = yVRot;
            if (Minecraft.getInstance().cameraEntity instanceof LivingEntity) {
                yRot = Mth.lerp(mc.getPartialTick(), ((LivingEntity) Minecraft.getInstance().cameraEntity).yBodyRotO, ((LivingEntity) Minecraft.getInstance().cameraEntity).yBodyRot);
            }
            float scale = 0.5F;
            double distance = 2.0;
            float lookAtFactor = Mth.clamp(MathHelper.remap(xRot, 0, 45, 0.2F, 1), 0, 1);

            Vec3 position = Vec3.directionFromRotation(xRot, yVRot);
            position = position.multiply(0, 1, 0).add(0, 0, 1).normalize().scale(distance);

            stack.pushPose();
            stack.mulPose(Vector3f.YP.rotationDegrees(yVRot));
            stack.mulPose(Vector3f.YP.rotationDegrees(Mth.lerp(0.9F, -yRot, -yVRot)));

            stack.mulPose(Vector3f.YP.rotationDegrees(180.0F));
            stack.mulPose(Vector3f.ZP.rotationDegrees(180.0F));
            stack.scale(scale, scale, scale);
            stack.translate(0, 1.8 * event.getEquipProgress(), 0);

            stack.translate(Mth.lerp(lookAtFactor, position.x(), 0), Mth.lerp(lookAtFactor, position.y() + 2, 0), Mth.lerp(lookAtFactor, position.z(), distance));

            stack.mulPose(Vector3f.YP.rotationDegrees(180));
            stack.mulPose(Vector3f.XP.rotationDegrees(90));

            stack.mulPose(Vector3f.XP.rotationDegrees(Mth.lerp(lookAtFactor, -xRot, -90)));
            stack.scale(0.45F, 0.45F, 0.45F);

            //Renders the player's hands, some specific stuff to handle cases with the offhand.
            if (!mc.player.isInvisible()) {
                stack.pushPose();
                HumanoidArm mainHandSide = mc.options.mainHand().get();
                ItemStack mainHandItem = mc.player.getItemInHand(InteractionHand.MAIN_HAND);
                ItemStack offHandItem = mc.player.getItemInHand(InteractionHand.OFF_HAND);
                ItemStack rightArmItem = mainHandSide == HumanoidArm.RIGHT ? mainHandItem : offHandItem;
                ItemStack leftArmItem = mainHandSide == HumanoidArm.RIGHT ? offHandItem : mainHandItem;

                if ((rightArmItem == ItemStack.EMPTY && mainHandSide != HumanoidArm.RIGHT) || rightArmItem.getItem() instanceof StoneTabletItem) {
                    renderHand(stack, event.getMultiBufferSource(), event.getPackedLight(), HumanoidArm.RIGHT);
                }
                if ((leftArmItem == ItemStack.EMPTY && mainHandSide != HumanoidArm.LEFT) || leftArmItem.getItem() instanceof StoneTabletItem) {
                    renderHand(stack, event.getMultiBufferSource(), event.getPackedLight(), HumanoidArm.LEFT);
                }

                stack.popPose();
            }
            stack.mulPose(Vector3f.XP.rotationDegrees(40 * (MathHelper.ease(event.getEquipProgress(), MathHelper.Easing.easeInOutBack))));


            bookModel.tablet.yRot = -(float) (Math.PI / 2);
            bookModel.tablet.x = 24.5F;
            bookModel.renderToBuffer(stack, event.getMultiBufferSource().getBuffer(RenderType.entitySolid(bookTexture)), event.getPackedLight(), OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
            stack.translate(1,0,0);

            stack.pushPose();
            stack.mulPose(Vector3f.YP.rotationDegrees(90));
            stack.translate(-0.85f,0.1575f,0.4f);
            stack.scale(0.005f, 0.005f, 0.005f);
            float height = Minecraft.getInstance().font.lineHeight/10.3f;
            stack.scale(height, height, height);
            BookScreen.renderWrappingText(stack, " ", 0 ,0, 107, true, new Color(Integer.parseInt("adb0ba", 16)));
            stack.popPose();
            stack.popPose();
        }
    }

    public static void renderHand(PoseStack stack, MultiBufferSource buffer, int light, HumanoidArm side) {
        RenderSystem.setShaderTexture(0, mc.player.getSkinTextureLocation());
        PlayerRenderer playerrenderer = (PlayerRenderer) mc.getEntityRenderDispatcher().<AbstractClientPlayer>getRenderer(mc.player);
        stack.pushPose();

        float sideSign = side == HumanoidArm.RIGHT ? 1.0F : -1.0F;
        float scale = 5.2F;
        stack.scale(scale, scale, scale);
        stack.translate(0.0, 1.1, 0.6F);
        //stack.mulPose(Vector3f.YP.rotationDegrees(180.0F));
        stack.mulPose(Vector3f.XP.rotationDegrees(45.0F));
        stack.mulPose(Vector3f.XP.rotationDegrees(180.0F));
        stack.mulPose(Vector3f.ZP.rotationDegrees(sideSign * -30.0F));
        stack.translate(sideSign * -0.25F, -0.05f, 0);

        if (side == HumanoidArm.RIGHT) {
            playerrenderer.renderRightHand(stack, buffer, light, mc.player);
        } else {
            playerrenderer.renderLeftHand(stack, buffer, light, mc.player);
        }

        stack.popPose();
    }

    public static class StoneTabletModel extends Model {
        private final ModelPart tablet;

        public StoneTabletModel() {
            super(RenderType::entitySolid);
            List<ModelPart.Cube> cubes = List.of(
                    new ModelPart.Cube(0, 0, 4, 0, 0, 12, 16, 1, 0, 0, 0, false, 32, 32)
            );
            this.tablet = new ModelPart(cubes, Map.of());
        }

        @Override
        public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
            poseStack.mulPose(Vector3f.YP.rotationDegrees(90));
            poseStack.translate(-25,-25,-31f);
            poseStack.scale(50,50,50);
            tablet.render(poseStack, buffer, packedLight, packedOverlay);
        }
    }
}
