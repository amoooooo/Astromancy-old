package coffee.amo.astromancy.common.block.mortar;

import coffee.amo.astromancy.common.blockentity.mortar.MortarBlockEntity;
import coffee.amo.astromancy.core.systems.block.AstromancyEntityBlock;

public class MortarBlock<T extends MortarBlockEntity> extends AstromancyEntityBlock<T> {

    public MortarBlock(Properties properties) {
        super(properties);
    }
}
