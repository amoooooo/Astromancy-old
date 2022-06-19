package coffee.amo.astromancy.common.item;

import coffee.amo.astromancy.core.handlers.AstromancyPacketHandler;
import coffee.amo.astromancy.core.packets.ResearchPacket;
import coffee.amo.astromancy.core.registration.BlockRegistration;
import coffee.amo.astromancy.core.registration.ItemRegistry;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.ParticleUtils;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PacketDistributor;

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
                AstromancyPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) context.getPlayer()), new ResearchPacket("crucible", false));
                AstromancyPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) context.getPlayer()), new ResearchPacket("aspecti_phial", true));
            }
        }
        return InteractionResult.PASS;
    }
}
