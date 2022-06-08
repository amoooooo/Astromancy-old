package coffee.amo.astromancy.common.block;

import coffee.amo.astromancy.common.blockentity.AstrolabeBlockEntity;
import com.sammy.ortus.systems.block.OrtusEntityBlock;

public class Astrolabe<T extends AstrolabeBlockEntity> extends OrtusEntityBlock<T> {
    public Astrolabe(Properties properties) {
        super(properties);
    }
}
