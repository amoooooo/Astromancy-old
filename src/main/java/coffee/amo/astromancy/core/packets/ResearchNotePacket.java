package coffee.amo.astromancy.core.packets;

import coffee.amo.astromancy.client.research.ClientResearchHolder;
import coffee.amo.astromancy.core.registration.ItemRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.util.LogicalSidedProvider;
import net.minecraftforge.network.NetworkEvent;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.logging.Level;

public class ResearchNotePacket {
    public final String researchId;

    public ResearchNotePacket(String researchId) {
        this.researchId = researchId;
    }

    public static void encode(ResearchNotePacket packet, FriendlyByteBuf buffer){
        buffer.writeUtf(packet.researchId);
    }

    public static ResearchNotePacket decode(FriendlyByteBuf buffer){
        return new ResearchNotePacket(buffer.readUtf());
    }

    public static void handle(ResearchNotePacket packet, Supplier<NetworkEvent.Context> contextSupplier){
        contextSupplier.get().enqueueWork(() -> {
            if(contextSupplier.get().getDirection().getReceptionSide().isServer()){
                int slot = contextSupplier.get().getSender().getInventory().findSlotMatchingItem(Items.PAPER.getDefaultInstance());
                contextSupplier.get().getSender().getInventory().removeItem(slot, 1);
                ItemStack stack = new ItemStack(ItemRegistry.RESEARCH_NOTE.get());
                stack.getOrCreateTag().putString("researchId", packet.researchId);
                contextSupplier.get().getSender().addItem(stack);
            }
        });
        contextSupplier.get().setPacketHandled(true);
    }
}
