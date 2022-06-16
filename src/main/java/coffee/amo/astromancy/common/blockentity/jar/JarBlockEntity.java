package coffee.amo.astromancy.common.blockentity.jar;

import coffee.amo.astromancy.common.item.AspectiPhial;
import coffee.amo.astromancy.core.helpers.BlockHelper;
import coffee.amo.astromancy.core.helpers.StringHelper;
import coffee.amo.astromancy.core.registration.ItemRegistry;
import coffee.amo.astromancy.core.systems.aspecti.Aspecti;
import coffee.amo.astromancy.core.systems.blockentity.AstromancyBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Locale;

public class JarBlockEntity extends AstromancyBlockEntity {
    private int count;
    private Aspecti aspecti;
    public JarBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    // TODO: cap this at 256, or maybe make it a config option.
    // TODO: make it so the first stack added starts the count at 16
    // TODO: fix the renderer
    @Override
    public InteractionResult onUse(Player player, InteractionHand hand) {
        if(player.getItemInHand(hand).getItem() instanceof AspectiPhial && player.getItemInHand(hand).getTag() != null){
            count = count == 0 ? Aspecti.fromNbt(player.getItemInHand(hand).getTag()).getSecond() : count + Aspecti.fromNbt(player.getItemInHand(hand).getTag()).getSecond();
            aspecti = Aspecti.fromNbt(player.getItemInHand(hand).getTag()).getFirst();
            player.getItemInHand(hand).setCount(player.getItemInHand(hand).getCount() - 1);
            BlockHelper.updateAndNotifyState(level, worldPosition);
            return InteractionResult.SUCCESS;
        } else if(player.isShiftKeyDown() && player.getItemInHand(hand) == ItemStack.EMPTY && this.aspecti != null){
            CompoundTag tag = new CompoundTag();
            tag.putInt("count", 16);
            tag.putInt("aspect", aspecti.ordinal());
            ItemStack stack = new ItemStack(ItemRegistry.ASPECTI_PHIAL.get(), 1);
            stack.getOrCreateTag().put("aspecti", tag);
            player.addItem(stack);
            if(count <= 0){
                this.count = 0;
                this.aspecti = null;
            }
            this.count = count - 16;
            return InteractionResult.SUCCESS;
            //player.sendMessage(new TextComponent(count + " " + StringHelper.capitalize(aspecti.toString().toLowerCase(Locale.ROOT))), player.getUUID());
        } else if(player.getItemInHand(hand).getItem() instanceof AspectiPhial && !player.getItemInHand(hand).hasTag() && this.aspecti != null){
            CompoundTag tag = new CompoundTag();
            tag.putInt("count", 16);
            tag.putInt("aspect", aspecti.ordinal());
            if(count <= 0){
                this.count = 0;
                this.aspecti = null;
            }
            this.count = count - 16;
            player.getItemInHand(hand).getOrCreateTag().put("aspecti", tag);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    public void setCount(int count) {
        this.count = count;
    }
    public int getCount() {
        return count;
    }
    public void setAspecti(Aspecti aspecti) {
        this.aspecti = aspecti;
    }
    public Aspecti getAspecti() {
        return aspecti;
    }
}
