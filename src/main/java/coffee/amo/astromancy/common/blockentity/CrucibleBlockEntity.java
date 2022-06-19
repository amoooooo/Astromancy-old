package coffee.amo.astromancy.common.blockentity;

import coffee.amo.astromancy.core.registration.BlockEntityRegistration;
import coffee.amo.astromancy.core.systems.blockentity.AstromancyBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class CrucibleBlockEntity extends AstromancyBlockEntity {
    public CrucibleBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public CrucibleBlockEntity(BlockPos pos, BlockState state){
        super(BlockEntityRegistration.CRUCIBLE.get(), pos, state);
    }
}
