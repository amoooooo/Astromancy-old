package coffee.amo.astromancy.common.item;

import coffee.amo.astromancy.core.handlers.PlayerResearchHandler;
import coffee.amo.astromancy.core.registration.BlockRegistration;
import coffee.amo.astromancy.core.registration.ItemRegistry;
import coffee.amo.astromancy.core.systems.research.ResearchObject;
import coffee.amo.astromancy.core.systems.research.ResearchProgress;
import coffee.amo.astromancy.core.systems.research.ResearchTypeRegistry;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

public class StellariteDust extends Item {
    public StellariteDust(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        if(!context.getLevel().isClientSide){
            if(context.getLevel().getBlockState(context.getClickedPos()).getBlock() == Blocks.BOOKSHELF){
                context.getLevel().setBlock(context.getClickedPos(), Blocks.AIR.defaultBlockState(), 11);
                ItemEntity book = new ItemEntity(context.getLevel(), context.getClickedPos().getX(), context.getClickedPos().getY(), context.getClickedPos().getZ(), new ItemStack(ItemRegistry.STELLA_LIBRI.get()));
                book.setNoGravity(true);
                book.setDeltaMovement(0,0,0);
                book.setPos(Vec3.atCenterOf(context.getClickedPos()));
                context.getLevel().addFreshEntity(book);
                context.getPlayer().playNotifySound(SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.MASTER, 5.0f, 1.0f);
            } else if (context.getLevel().getBlockState(context.getClickedPos()).getBlock() == Blocks.CAULDRON){
                context.getLevel().setBlock(context.getClickedPos(), BlockRegistration.CRUCIBLE.get().defaultBlockState(), 11);
                context.getPlayer().playNotifySound(SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.MASTER, 5.0f, 1.0f);
                context.getPlayer().getCapability(PlayerResearchHandler.RESEARCH_CAPABILITY, null).ifPresent(research -> {
                    ResearchTypeRegistry.RESEARCH_TYPES.get().getValues().forEach(s -> {
                        ResearchObject object = (ResearchObject) s;
                        if(object.identifier.equals("crucible")){
                            research.addLockedResearch(context.getPlayer(), object);
                        }
                        if(object.identifier.equals("glyph_phial")){
                            object.locked = ResearchProgress.IN_PROGRESS;
                            research.addResearch(context.getPlayer(), object);
                        }
                    });
                });
            }
        }
        return InteractionResult.PASS;
    }
}
