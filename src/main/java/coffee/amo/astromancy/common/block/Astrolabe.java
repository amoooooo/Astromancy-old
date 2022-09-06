package coffee.amo.astromancy.common.block;

import coffee.amo.astromancy.common.blockentity.AstrolabeBlockEntity;
import coffee.amo.astromancy.core.systems.block.AstromancyEntityBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class Astrolabe<T extends AstrolabeBlockEntity> extends AstromancyEntityBlock<T> {
    public final VoxelShape SHAPE = Block.box(3D, 0.0D, 3D, 13.0D, 3.0D, 13.0D);
    public Astrolabe(Properties properties) {
        super(properties);
    }


    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public int getLightEmission(BlockState state, BlockGetter level, BlockPos pos) {
        if(level.getBlockEntity(pos) instanceof AstrolabeBlockEntity abe) {
            if(abe.star.getStars()[1] != null){
                return (int) Math.min(15,(abe.star.getStars()[0].getMass()/5000 + abe.star.getStars()[1].getMass()/5000));
            } else {
                return (int) Math.min(15,(abe.star.getStars()[0].getMass()/5000));
            }
        }
        return super.getLightEmission(state, level, pos);
    }

    @Override
    public VoxelShape getInteractionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return SHAPE;
    }
}
