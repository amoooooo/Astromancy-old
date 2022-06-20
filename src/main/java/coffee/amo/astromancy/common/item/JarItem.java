package coffee.amo.astromancy.common.item;

import coffee.amo.astromancy.Astromancy;
import coffee.amo.astromancy.aequivaleo.AspectiEntry;
import coffee.amo.astromancy.common.blockentity.jar.JarBlockEntity;
import coffee.amo.astromancy.core.handlers.AstromancyPacketHandler;
import coffee.amo.astromancy.core.helpers.StringHelper;
import coffee.amo.astromancy.core.packets.JarUpdatePacket;
import coffee.amo.astromancy.core.systems.aspecti.Aspecti;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Locale;

public class JarItem extends BlockItem {
    public JarItem(Block pBlock, Properties pProperties) {
        super(pBlock, pProperties);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltip, TooltipFlag
            pFlag) {
        super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
        if (pStack.hasTag() && ((CompoundTag) pStack.getTag().get("BlockEntityTag")).getInt("aspecti") != 23) {
            TextComponent tc = new TextComponent("");
            CompoundTag tag = pStack.getOrCreateTagElement("BlockEntityTag");
            tc.append(new TextComponent("[").withStyle(s -> s.withFont(Astromancy.astromancy("aspecti"))));
            tc.append(new TranslatableComponent("space.0").withStyle(s -> s.withFont(Astromancy.astromancy("negative_space"))));
            tc.append(new TranslatableComponent("space.-1").withStyle(s -> s.withFont(Astromancy.astromancy("negative_space"))));
            tc.append(new TextComponent(Aspecti.values()[tag.getInt("aspecti")].symbol()).withStyle(style -> style.withFont(Astromancy.astromancy("aspecti"))));
            tc.append(new TranslatableComponent("space.0").withStyle(s -> s.withFont(Astromancy.astromancy("negative_space"))));
            tc.append(new TranslatableComponent("space.-1").withStyle(s -> s.withFont(Astromancy.astromancy("negative_space"))));
            tc.append(AspectiEntry.intToTextComponent(tag.getInt("count")).append(new TextComponent("]").withStyle(s -> s.withFont(Astromancy.astromancy("aspecti")))));
            pTooltip.add(tc);
        }
    }

    @Override
    public Component getName(ItemStack pStack) {
        if (pStack.hasTag() && ((CompoundTag) pStack.getTag().get("BlockEntityTag")).getInt("aspecti") != 23) {
            CompoundTag tag = (CompoundTag) pStack.getTag().get("BlockEntityTag");
            return new TranslatableComponent("block.astromancy.jar").append(" of " + StringHelper.capitalize(Aspecti.values()[tag.getInt("aspecti")].toString().toLowerCase(Locale.ROOT)) + " Slurry");
        }
        return super.getName(pStack);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        CompoundTag tag = new CompoundTag();
        tag.putInt("aspecti", 23);
        tag.putInt("count", 0);
        if(nbt != null) {
            nbt.put("BlockEntityTag", tag);
        } else {
            stack.getOrCreateTag().put("BlockEntityTag", tag);
        }
        return super.initCapabilities(stack, nbt);
    }
}
