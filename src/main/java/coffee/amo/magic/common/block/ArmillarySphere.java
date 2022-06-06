package coffee.amo.magic.common.block;

import coffee.amo.magic.common.blockentity.ArmillarySphereBlockEntity;
import com.sammy.ortus.systems.block.OrtusEntityBlock;

public class ArmillarySphere<T extends ArmillarySphereBlockEntity> extends OrtusEntityBlock<T> {
    private boolean toggled = false;
    public ArmillarySphere(Properties properties) {
        super(properties);
    }
}
