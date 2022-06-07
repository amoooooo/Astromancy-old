package coffee.amo.astromancy.core.registration;

import com.sammy.ortus.systems.block.OrtusBlockProperties;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;

public class BlockPropertyRegistration {
    public static OrtusBlockProperties MAGIC_PROPERTIES() {
        return new OrtusBlockProperties(Material.METAL).sound(SoundType.LODESTONE).dynamicShape().isCutoutLayer().isViewBlocking((var1, var2, var3) -> false).noOcclusion();
    }
}
