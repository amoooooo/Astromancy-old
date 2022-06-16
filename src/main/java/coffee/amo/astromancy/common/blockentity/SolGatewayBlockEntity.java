package coffee.amo.astromancy.common.blockentity;

import coffee.amo.astromancy.core.systems.blockentity.AstromancyBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class SolGatewayBlockEntity extends AstromancyBlockEntity {
    public SolGatewayBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }
}
