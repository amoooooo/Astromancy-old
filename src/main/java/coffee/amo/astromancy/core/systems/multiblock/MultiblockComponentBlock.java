package coffee.amo.astromancy.core.systems.multiblock;

import coffee.amo.astromancy.core.registration.BlockEntityRegistration;
import coffee.amo.astromancy.core.systems.block.AstromancyEntityBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class MultiblockComponentBlock extends AstromancyEntityBlock<MultiblockComponentEntity> implements IAstromancyMultiblockComponent {
    public MultiblockComponentBlock(BlockBehaviour.Properties properties) {
        super(properties);
        setBlockEntity(BlockEntityRegistration.MULTIBLOCK_COMPONENT);
    }
}
