package coffee.amo.astromancy.core.systems.levelevent;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;

public abstract class LevelEventRenderer<T extends LevelEventInstance> {

    public boolean canRender(T instance){
        return false;
    }

    public void render(T instance, PoseStack ps, MultiBufferSource bufferSource, float partialTicks){

    }
}
