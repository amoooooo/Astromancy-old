package coffee.amo.astromancy.client.renderer.block;

import coffee.amo.astromancy.Astromancy;
import coffee.amo.astromancy.common.blockentity.AstrolabeBlockEntity;
import coffee.amo.astromancy.core.helpers.RenderHelper;
import coffee.amo.astromancy.core.systems.math.UniversalConstants;
import coffee.amo.astromancy.core.systems.stars.types.AstralObject;
import coffee.amo.astromancy.core.systems.stars.types.Moon;
import coffee.amo.astromancy.core.systems.stars.types.Planet;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.function.Function;


public class AstrolabeRenderer implements BlockEntityRenderer<AstrolabeBlockEntity> {
    private final Font font;
    public AstrolabeRenderer(BlockEntityRendererProvider.Context context) {
        this.font = context.getFont();
    }

    @Override
    public void render(AstrolabeBlockEntity pBlockEntity, float pPartialTick, PoseStack ps, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        ps.pushPose();
        ps.translate(0, 0.2, 0);
        if(pBlockEntity.star.getStars()[1] != null){
            ps.pushPose();
            ps.translate(0.5, 0.5, 0.5);
            double a1 = UniversalConstants.binaryStarCenterOfMass(pBlockEntity.star.getStars()[0].getMass(), pBlockEntity.star.getStars()[1].getMass(), 0.5f).getFirst();
            double a2 = UniversalConstants.binaryStarCenterOfMass(pBlockEntity.star.getStars()[0].getMass(), pBlockEntity.star.getStars()[1].getMass(), 0.5f).getSecond();
            double orbitSpeed = UniversalConstants.binaryStarOrbitSpeed(pBlockEntity.star.getStars()[0].getMass(), pBlockEntity.star.getStars()[1].getMass(), a1 + a2) * Math.pow(10f, 3.5);
            ps.mulPose(Vector3f.YP.rotationDegrees(((Minecraft.getInstance().level.getGameTime()+pPartialTick) * (float)orbitSpeed)));
            ps.translate(-0.5, -0.5, -0.5);
            ps.pushPose();
            ps.translate(0, 0, a1);
            RenderHelper.renderStar(ps, Math.min(0.2f, pBlockEntity.star.getStars()[0].getMass()/10000f), pBufferSource, pBlockEntity.star.getStars()[0], pBlockEntity, pPartialTick, font, true);
            ps.popPose();

            ps.pushPose();
            ps.translate(0, 0, -a2);
            RenderHelper.renderStar(ps, Math.min(0.2f, pBlockEntity.star.getStars()[1].getMass()/10000f), pBufferSource, pBlockEntity.star.getStars()[1], pBlockEntity, pPartialTick, font, true);
            ps.popPose();
            ps.popPose();
        } else {
            ps.pushPose();
            if(pBlockEntity.star.getStars()[0] != null){
                RenderHelper.renderStar(ps, Math.min(0.3f, pBlockEntity.star.getStars()[0].getMass()/10000f), pBufferSource, pBlockEntity.star.getStars()[0], pBlockEntity, pPartialTick, font, true);
            }
            ps.popPose();
        }
        ps.translate(0,0.8,1);
        for(int i = 0; i < pBlockEntity.star.getPlanets().length; i++){
            Planet planet = pBlockEntity.star.getPlanets()[i];
            if(planet == null) continue;
            ps.pushPose();
            float starMass = pBlockEntity.star.getStars()[1] == null ? pBlockEntity.star.getStars()[0].getMass() : pBlockEntity.star.getStars()[0].getMass() + pBlockEntity.star.getStars()[1].getMass();
            double orbitSpeed = UniversalConstants.calculateOrbitSpeed(starMass, (1.5+i) * Math.pow(10, 6)) * Math.pow(10f, 7.5f);
            ps.mulPose(Vector3f.ZP.rotationDegrees(planet.getOrbitAngle()));
            ps.mulPose(Vector3f.YP.rotationDegrees((float) ((orbitSpeed/i) * ((Minecraft.getInstance().level.getGameTime()+pPartialTick)/10f) % 360)));
            ps.translate(0.5+(i), 0, 0);
            ps.scale(planet.getMass()/10000f, planet.getMass()/10000f, planet.getMass()/10000f);
            ps.translate(-planet.getMass()/10000f, -planet.getMass()/10000f, -planet.getMass()/10000f);
            renderPlanet(ps, pBufferSource, pPackedLight, pPackedOverlay, font, planet, pPartialTick);
            ps.popPose();
        }
        ps.pushPose();

        for(int i = 0; i < pBlockEntity.star.getSolarSystemObjects().size(); i++){
            ps.pushPose();
            ps.translate(0.5, 0, -0.5);
            AstralObject object = pBlockEntity.star.getSolarSystemObjects().get(i);
            float starMass = pBlockEntity.star.getStars()[1] == null ? pBlockEntity.star.getStars()[0].getMass() : pBlockEntity.star.getStars()[0].getMass() + pBlockEntity.star.getStars()[1].getMass();
            double a1 = UniversalConstants.binaryStarCenterOfMass(starMass, object.getSize(), i+1).getFirst();
            double a2 = UniversalConstants.binaryStarCenterOfMass(starMass, object.getSize(), i+1).getSecond();
            double orbitSpeed = UniversalConstants.binaryStarOrbitSpeed(starMass, object.getSize(), a1 + a2) * Math.pow(10f, 4);
            ps.mulPose(Vector3f.ZP.rotationDegrees(object.getAxisTilt()));
            ps.mulPose(Vector3f.YP.rotationDegrees(((Minecraft.getInstance().level.getGameTime()+pPartialTick) * (float)orbitSpeed)));
            ps.translate(0, 0, a1 * 2500* (i/2f));
            ps.scale(object.getSize()/10f, object.getSize()/10f, object.getSize()/10f);
            ps.translate(-object.getSize()/10f, 0, -object.getSize()/10f);
            renderAstralObject(ps, pBufferSource, pPackedLight, pPackedOverlay, font, object, pPartialTick);
            ps.popPose();
        }

        ps.popPose();


        ps.popPose();
    }

    public static void renderAstralObject(PoseStack ps, MultiBufferSource bufferSource, int packedLight, int packedOverlay, Font font, AstralObject object, float pTick){
        ps.pushPose();
        PlanetModelLand planetModel = new PlanetModelLand();
        PlanetModelOcean planetModelOcean = new PlanetModelOcean();
        VertexConsumer water = bufferSource.getBuffer(RenderType.entityCutout(Astromancy.astromancy("textures/planet/water.png")));
        planetModelOcean.renderToBuffer(ps, water, packedLight, packedOverlay, 0.5f, 0.5f, 0.5f, 1);
        VertexConsumer land = bufferSource.getBuffer(RenderType.entityCutout(Astromancy.astromancy("textures/planet/land.png")));
        planetModel.renderToBuffer(ps, land, packedLight, packedOverlay, 0.5f, 0.5f, 0.5f, 1);
        ps.popPose();
    }

    public static void renderPlanet(PoseStack ps, MultiBufferSource bufferSource, int packedLight, int packedOverlay, Font font, Planet planet, float pPartialTick){
        ps.pushPose();
        PlanetModelLand planetModel = new PlanetModelLand();
        PlanetModelAtmosphere planetModelAtmosphere = new PlanetModelAtmosphere();
        PlanetModelClouds planetModelClouds = new PlanetModelClouds();
        PlanetModelOcean planetModelOcean = new PlanetModelOcean();
        PlanetModelRing planetModelRing = new PlanetModelRing();
        planetModel.landColor = planet.getLandColor();
        planetModelOcean.waterColor = planet.getOceanColor();
        planetModelAtmosphere.atmosphereColor = planet.getSkyColor();
        planetModelClouds.cloudColor = Color.WHITE;
        planetModelRing.ringColor = planet.getSkyColor();
        ps.translate(0.25,-0.5,-0.75);
        ps.translate(0.25,0.25,0.25);
        ps.mulPose(Vector3f.ZP.rotationDegrees(planet.getAxisTilt()));
        ps.mulPose(Vector3f.YP.rotationDegrees(Minecraft.getInstance().level.getGameTime() + pPartialTick));
        ps.translate(-0.25,-0.25,-0.25);
        ps.scale(4,4,4);
        VertexConsumer water = bufferSource.getBuffer(RenderType.entityCutout(Astromancy.astromancy("textures/planet/water.png")));
        if(planet.hasOcean()) planetModelOcean.renderToBuffer(ps, water, packedLight, packedOverlay, planet.getOceanColor().getRed(), planet.getOceanColor().getGreen(), planet.getOceanColor().getBlue(), 1);
        else planetModel.renderToBuffer(ps, water, packedLight, packedOverlay, planet.getLandColor().getRed(), planet.getLandColor().getGreen(), planet.getLandColor().getBlue(), 1);
        VertexConsumer land = bufferSource.getBuffer(RenderType.entityCutout(Astromancy.astromancy("textures/planet/land.png")));
        if(planet.hasLand()) planetModel.renderToBuffer(ps, land, packedLight, packedOverlay, 1, 1, 1, 1);
        VertexConsumer clouds = bufferSource.getBuffer(RenderType.entityCutout(Astromancy.astromancy("textures/planet/clouds.png")));
        if(planet.hasClouds()) planetModelClouds.renderToBuffer(ps, clouds, packedLight, packedOverlay, planet.getSkyColor().darker().getRed(), planet.getSkyColor().darker().getGreen(), planet.getSkyColor().darker().getBlue(), 1);
        VertexConsumer atmosphere = bufferSource.getBuffer(RenderType.entityTranslucent(Astromancy.astromancy("textures/vfx/white.png")));
        if(planet.hasAtmosphere()) planetModelAtmosphere.renderToBuffer(ps, atmosphere, packedLight, packedOverlay,  planet.getSkyColor().getRed(), planet.getSkyColor().getGreen(), planet.getSkyColor().getBlue(), 0.25f);
        VertexConsumer ring = bufferSource.getBuffer(RenderType.entityCutout(Astromancy.astromancy("textures/planet/rings.png")));
        if(planet.getRingCount() > 0) planetModelRing.renderToBuffer(ps, ring, packedLight, packedOverlay, planet.getLandColor().getRed(), planet.getLandColor().getGreen(), planet.getLandColor().getBlue(), 1);

        if(!planet.getMoons().isEmpty()){
            ps.pushPose();
            ps.scale(0.1f, 0.1f, 0.1f);
            for(int i = 0; i < planet.getMoonCount(); i++){
                Moon moon = planet.getMoon(i);
                ps.pushPose();
                ps.translate(0.5, 0.5f, 0.5);
                ps.mulPose(Vector3f.ZP.rotationDegrees(moon.getAxisTilt()));
                ps.mulPose(Vector3f.YP.rotationDegrees((float) ((UniversalConstants.calculateOrbitSpeed(moon.getMass(), 10000) * Math.pow(10f, 7.5f)) * ((Minecraft.getInstance().level.getGameTime()+pPartialTick)/10f) % 360)));
                ps.translate(2.5f+(i/10f), 0, 0);
                ps.scale(moon.getMass()/500f, moon.getMass()/500f, moon.getMass()/500f);
                ps.translate(-moon.getMass()/700f, 0,-moon.getMass()/700f);
                water = bufferSource.getBuffer(RenderType.entityCutout(Astromancy.astromancy("textures/planet/water.png")));
                planetModelOcean.renderToBuffer(ps, water, packedLight, packedOverlay, 0.5f, 0.5f, 0.5f, 1);
                land = bufferSource.getBuffer(RenderType.entityCutout(Astromancy.astromancy("textures/planet/land.png")));
                planetModel.renderToBuffer(ps, land, packedLight, packedOverlay, 0.5f, 0.5f, 0.5f, 1);
                ps.popPose();
            }

            ps.popPose();
        }


        ps.popPose();
        ps.pushPose();
        ps.scale(0.009f,0.009f,0.009f);
        ps.mulPose(Vector3f.ZP.rotationDegrees(180));
        ps.mulPose(Vector3f.YN.rotationDegrees(90));
        font.draw(ps, planet.getName(), -0.5f, 0.5f, 0xFFFFFF);
        ps.popPose();
    }

    private static class PlanetModelLand extends Model {
        public Color landColor = new Color(0.5f, 0.5f, 0.5f);
        private final ModelPart land;
        public PlanetModelLand() {
            super(RenderType::entityCutout);
            List<ModelPart.Cube> land = List.of(
                    new ModelPart.Cube(0,0,0,0,0,2,2,2, 0, 0,0,false,2,2)
            );
            this.land = new ModelPart(land, Map.of());
        }

        @Override
        public void renderToBuffer(PoseStack pPoseStack, VertexConsumer pBuffer, int pPackedLight, int pPackedOverlay, float pRed, float pGreen, float pBlue, float pAlpha) {
            pPoseStack.pushPose();
            land.render(pPoseStack, pBuffer, pPackedLight, pPackedOverlay, landColor.getRed()/255f, landColor.getGreen()/255f, landColor.getBlue()/255f, 1);
            pPoseStack.popPose();
        }
    }

    private static class PlanetModelRing extends Model{
        public Color ringColor = new Color(0.5f, 0.5f, 0.5f);
        private final ModelPart ring;
        public PlanetModelRing() {
            super(RenderType::entityCutout);

            List<ModelPart.Cube> ring = List.of(
                    new ModelPart.Cube(0,0,0,0,0,4,0.1f,4, 0, 0,0,false,4,4)
            );
            this.ring = new ModelPart(ring, Map.of());
        }

        @Override
        public void renderToBuffer(PoseStack pPoseStack, VertexConsumer pBuffer, int pPackedLight, int pPackedOverlay, float pRed, float pGreen, float pBlue, float pAlpha) {
            pPoseStack.pushPose();
            pPoseStack.translate(-0.065,0.0625f,-0.065);
            ring.render(pPoseStack, pBuffer, pPackedLight, pPackedOverlay, ringColor.getRed() / 255f, ringColor.getGreen() / 255f, ringColor.getBlue() / 255f, 1f);
            pPoseStack.popPose();
        }
    }

    private static class PlanetModelOcean extends Model{
        public Color waterColor = new Color(0.5f, 0.5f, 0.5f);
        private final ModelPart ocean;
        public PlanetModelOcean() {
            super(RenderType::entityCutout);
            List<ModelPart.Cube> ocean = List.of(
                    new ModelPart.Cube(0,0,0,0,0,2,2,2, 0, 0,0,false,2,2)
            );
            this.ocean = new ModelPart(ocean, Map.of());
        }

        @Override
        public void renderToBuffer(PoseStack pPoseStack, VertexConsumer pBuffer, int pPackedLight, int pPackedOverlay, float pRed, float pGreen, float pBlue, float pAlpha) {
            pPoseStack.pushPose();
            ocean.render(pPoseStack, pBuffer, pPackedLight, pPackedOverlay, waterColor.getRed()/255f, waterColor.getGreen()/255f, waterColor.getBlue()/255f, 1);
            pPoseStack.popPose();
        }
    }

    private static class PlanetModelAtmosphere extends Model {
        private final ModelPart atmosphere;
        public Color atmosphereColor = new Color(0, 0, 0, 0);

        public PlanetModelAtmosphere() {
            super(RenderType::entityTranslucent);
            List<ModelPart.Cube> atmosphere = List.of(
                    new ModelPart.Cube(0,0,0,0,0,2,2,2, 0, 0,0,false,2,2)
            );
            this.atmosphere = new ModelPart(atmosphere, Map.of());

        }

        @Override
        public void renderToBuffer(PoseStack pPoseStack, VertexConsumer pBuffer, int pPackedLight, int pPackedOverlay, float pRed, float pGreen, float pBlue, float pAlpha) {
            pPoseStack.pushPose();
            pPoseStack.scale(1.1f,1.1f,1.1f);
            pPoseStack.translate(-0.005,-0.005,-0.005);
            atmosphere.render(pPoseStack, pBuffer, pPackedLight, pPackedOverlay, atmosphereColor.getRed()/255f,atmosphereColor.getGreen()/255f,atmosphereColor.getBlue()/255f,0.15f);
            pPoseStack.popPose();
        }
    }

    private static class PlanetModelClouds extends Model{
        private final ModelPart cloud;
        public Color cloudColor = new Color(0, 0, 0, 0);
        public PlanetModelClouds() {
            super(RenderType::entityCutout);
            List<ModelPart.Cube> cloud = List.of(
                    new ModelPart.Cube(0,0,0,0,0,2,2,2, 0, 0,0,false,2,2)
            );
            this.cloud = new ModelPart(cloud, Map.of());
        }

        @Override
        public void renderToBuffer(PoseStack pPoseStack, VertexConsumer pBuffer, int pPackedLight, int pPackedOverlay, float pRed, float pGreen, float pBlue, float pAlpha) {
            pPoseStack.pushPose();
            pPoseStack.scale(1.05f,1.05f,1.05f);
            pPoseStack.translate(-0.0025,-0.0025,-0.0025);
            cloud.render(pPoseStack, pBuffer, pPackedLight, pPackedOverlay,  cloudColor.getRed()/255f, cloudColor.getGreen()/255f, cloudColor.getBlue()/255f, 0.15f);
            pPoseStack.popPose();
        }
    }

    @Override
    public boolean shouldRenderOffScreen(AstrolabeBlockEntity pBlockEntity) {
        return true;
    }
}
