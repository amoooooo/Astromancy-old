package coffee.amo.astromancy.common.block;

import coffee.amo.astromancy.common.blockentity.StarGatewayBlockEntity;
import com.sammy.ortus.systems.block.OrtusEntityBlock;

public class StarGateway<T extends StarGatewayBlockEntity> extends OrtusEntityBlock<T> {
    public StarGateway(Properties properties) {
        super(properties);
    }
}
