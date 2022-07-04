package coffee.amo.astromancy.core.packets;

import coffee.amo.astromancy.core.registration.LevelEventTypeRegistry;
import coffee.amo.astromancy.core.systems.levelevent.LevelEventType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SyncLevelEventPacket {
    String type;
    public boolean start;
    public CompoundTag data;

    public SyncLevelEventPacket(String type, boolean start, CompoundTag data){
        this.type = type;
        this.start = start;
        this.data = data;
    }

    public void encode(FriendlyByteBuf buf){
        buf.writeUtf(type);
        buf.writeBoolean(start);
        buf.writeNbt(data);
    }

    public static SyncLevelEventPacket decode(FriendlyByteBuf buf){
        return new SyncLevelEventPacket(buf.readUtf(), buf.readBoolean(), buf.readNbt());
    }

    public void handle(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            LevelEventType event = LevelEventTypeRegistry.EVENT_TYPES.get(type);
            ClientLevel level = Minecraft.getInstance().level;;
            //WorldEventHandler.addWorldEvent(level, start, event.createInstance(data));
        });
    }
}
