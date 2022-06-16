package coffee.amo.astromancy.core.systems.rendering;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;

import java.awt.*;
import java.util.function.Supplier;

public class VFXBuilders {
    public static ScreenVFXBuilder createScreen() {
        return new ScreenVFXBuilder();
    }

    public static class ScreenVFXBuilder {
        float r = 1, g = 1, b = 1, a = 1;
        int light = -1;
        float u0 = 0, v0 = 0, u1 = 1, v1 = 1;
        float x0 = 0, y0 = 0, x1 = 1, y1 = 1;

        VertexFormat format;
        Supplier<ShaderInstance> shader = GameRenderer::getPositionTexShader;
        ResourceLocation texture;
        ScreenVertexPlacementSupplier supplier;
        BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();

        public ScreenVFXBuilder setPosTexDefaultFormat() {
            supplier = (b, l, x, y, u, v) -> b.vertex(l, x, y, 0).uv(u, v).endVertex();
            format = DefaultVertexFormat.POSITION_TEX;
            return this;
        }

        public ScreenVFXBuilder setPosColorTexDefaultFormat() {
            supplier = (b, l, x, y, u, v) -> b.vertex(l, x, y, 0).color(this.r, this.g, this.b, this.a).uv(u, v).endVertex();
            format = DefaultVertexFormat.POSITION_COLOR_TEX;
            return this;
        }

        public ScreenVFXBuilder setPosColorTexLightmapDefaultFormat() {
            supplier = (b, l, x, y, u, v) -> b.vertex(l, x, y, 0).color(this.r, this.g, this.b, this.a).uv(u, v).uv2(this.light).endVertex();
            format = DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP;
            return this;
        }

        public ScreenVFXBuilder setFormat(VertexFormat format) {
            this.format = format;
            return this;
        }

        public ScreenVFXBuilder setShaderTexture(ResourceLocation texture) {
            this.texture = texture;
            return this;
        }
        public ScreenVFXBuilder setShader(Supplier<ShaderInstance> shader) {
            this.shader = shader;
            return this;
        }

        public ScreenVFXBuilder setShader(ShaderInstance shader) {
            this.shader = ()->shader;
            return this;
        }

        public ScreenVFXBuilder setVertexSupplier(ScreenVertexPlacementSupplier supplier) {
            this.supplier = supplier;
            return this;
        }

        public ScreenVFXBuilder overrideBufferBuilder(BufferBuilder builder) {
            this.bufferbuilder = builder;
            return this;
        }

        public ScreenVFXBuilder setLight(int light) {
            this.light = light;
            return this;
        }

        public ScreenVFXBuilder setColor(Color color) {
            return setColor(color.getRed(), color.getGreen(), color.getBlue());
        }

        public ScreenVFXBuilder setColor(Color color, float a) {
            return setColor(color).setAlpha(a);
        }

        public ScreenVFXBuilder setColor(float r, float g, float b, float a) {
            return setColor(r, g, b).setAlpha(a);
        }

        public ScreenVFXBuilder setColor(float r, float g, float b) {
            this.r = r / 255f;
            this.g = g / 255f;
            this.b = b / 255f;
            return this;
        }

        public ScreenVFXBuilder setAlpha(float a) {
            this.a = a;
            return this;
        }

        public ScreenVFXBuilder setPositionWithWidth(float x, float y, float width, float height) {
            return setPosition(x, y, x + width, y + height);
        }

        public ScreenVFXBuilder setPosition(float x0, float y0, float x1, float y1) {
            this.x0 = x0;
            this.y0 = y0;
            this.x1 = x1;
            this.y1 = y1;
            return this;
        }

        public ScreenVFXBuilder setUVWithWidth(float u, float v, float width, float height, float canvasSize) {
            return setUVWithWidth(u, v, width, height, canvasSize, canvasSize);
        }

        public ScreenVFXBuilder setUVWithWidth(float u, float v, float width, float height, float canvasSizeX, float canvasSizeY) {
            return setUVWithWidth(u / canvasSizeX, v / canvasSizeY, width / canvasSizeX, height / canvasSizeY);
        }

        public ScreenVFXBuilder setUVWithWidth(float u, float v, float width, float height) {
            this.u0 = u;
            this.v0 = v;
            this.u1 = (u + width);
            this.v1 = (v + height);
            return this;
        }

        public ScreenVFXBuilder setUV(float u0, float v0, float u1, float v1, float canvasSize) {
            return setUV(u0, v0, u1, v1, canvasSize, canvasSize);
        }

        public ScreenVFXBuilder setUV(float u0, float v0, float u1, float v1, float canvasSizeX, float canvasSizeY) {
            return setUV(u0 / canvasSizeX, v0 / canvasSizeY, u1 / canvasSizeX, v1 / canvasSizeY);
        }

        public ScreenVFXBuilder setUV(float u0, float v0, float u1, float v1) {
            this.u0 = u0;
            this.v0 = v0;
            this.u1 = u1;
            this.v1 = v1;
            return this;
        }

        public ScreenVFXBuilder blit(PoseStack stack) {
            Matrix4f last = stack.last().pose();
            RenderSystem.setShader(shader);
            if (texture != null) {
                RenderSystem.setShaderTexture(0, texture);
            }
            supplier.placeVertex(bufferbuilder, last, x0, y1, u0, v1);
            supplier.placeVertex(bufferbuilder, last, x1, y1, u1, v1);
            supplier.placeVertex(bufferbuilder, last, x1, y0, u1, v0);
            supplier.placeVertex(bufferbuilder, last, x0, y0, u0, v0);
            return this;
        }

        public ScreenVFXBuilder draw(PoseStack stack) {
            if (bufferbuilder.building()) {
                bufferbuilder.end();
            }
            begin();
            blit(stack);
            end();
            return this;
        }

        public ScreenVFXBuilder endAndProceed() {
            return end().begin();
        }

        public ScreenVFXBuilder begin() {
            bufferbuilder.begin(VertexFormat.Mode.QUADS, format);
            return this;
        }

        public ScreenVFXBuilder end() {
            bufferbuilder.end();
            BufferUploader.end(bufferbuilder);
            return this;
        }

        private interface ScreenVertexPlacementSupplier {
            void placeVertex(BufferBuilder bufferBuilder, Matrix4f last, float x, float y, float u, float v);
        }
    }
}
