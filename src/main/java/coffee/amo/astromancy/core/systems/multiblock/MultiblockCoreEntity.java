package coffee.amo.astromancy.core.systems.multiblock;

import coffee.amo.astromancy.core.systems.blockentity.AstromancyBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.ArrayList;

public abstract class MultiblockCoreEntity extends AstromancyBlockEntity implements IMultiblockCore {
    ArrayList<BlockPos> componentPositions = new ArrayList<>();

    public final MultiblockStructure structure;

    public MultiblockCoreEntity(BlockEntityType<?> type, MultiblockStructure structure, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.structure = structure;
        setupMultiblock(pos);
    }

    @Override
    public MultiblockStructure getStructure() {
        return structure;
    }

    @Override
    public ArrayList<BlockPos> getComponentPositions() {
        return componentPositions;
    }

    @Override
    public void onBreak(@Nullable Player player) {
        destroyMultiblock(player, level, worldPosition);
    }
}
