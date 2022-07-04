package coffee.amo.astromancy.core.systems.levelevent;

import coffee.amo.astromancy.core.handlers.AstromancyPacketHandler;
import coffee.amo.astromancy.core.packets.SyncLevelEventPacket;
import coffee.amo.astromancy.core.registration.LevelEventTypeRegistry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.PacketDistributor;

import java.util.UUID;

public abstract class LevelEventInstance {
    public UUID uuid;
    public LevelEventType type;
    public boolean discarded;

    public LevelEventInstance(LevelEventType type){
        this.uuid = UUID.randomUUID();
        this.type = type;
    }

    public void sync(Level level){
        if(!level.isClientSide && isClientSynced()){
            sync(this);
        }
    }

    public boolean isClientSynced(){
        return false;
    }

    public void start(Level level){

    }

    public void tick(Level level){

    }

    public void end(Level level){
        discarded = true;
    }

    public LevelEventInstance fromNbt(CompoundTag tag){
        uuid = tag.getUUID("uuid");
        type = LevelEventTypeRegistry.EVENT_TYPES.get(tag.getString("type"));
        discarded = tag.getBoolean("discarded");
        return this;
    }

    public CompoundTag toNbt(CompoundTag tag){
        tag.putUUID("uuid", uuid);
        tag.putString("type", type.id);
        tag.putBoolean("discarded", discarded);
        return tag;
    }

    public static <T extends LevelEventInstance> void sync(T instance){
        AstromancyPacketHandler.INSTANCE.send(PacketDistributor.ALL.noArg(), new SyncLevelEventPacket(instance.type.id, true, instance.toNbt(new CompoundTag())));
    }

    public static <T extends LevelEventInstance> void sync(T instance, ServerPlayer player){
        AstromancyPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), new SyncLevelEventPacket(instance.type.id, true, instance.toNbt(new CompoundTag())));
    }
}
