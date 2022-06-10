package coffee.amo.astromancy.common.block;

import coffee.amo.astromancy.common.blockentity.SolGatewayBlockEntity;
import com.sammy.ortus.systems.block.OrtusEntityBlock;

// center block, on place, places <size> blocks in a square around it, how to make them connect?
public class SolGateway<T extends SolGatewayBlockEntity> extends OrtusEntityBlock<T> {
    public SolGateway(Properties properties) {
        super(properties);
    }
}
