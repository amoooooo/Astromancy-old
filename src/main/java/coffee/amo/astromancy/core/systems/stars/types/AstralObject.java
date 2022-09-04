package coffee.amo.astromancy.core.systems.stars.types;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.phys.Vec3;

import java.util.UUID;

public class AstralObject {
    private UUID uuid;
    private String name;
    private int size;
    public Vec3 position = Vec3.ZERO;

    public AstralObject(String name) {
        this.name = name;
        this.uuid = UUID.randomUUID();
        this.size = 1;
    }

    public AstralObject(String name, UUID uuid, int size) {
        this.name = name;
        this.uuid = uuid;
        this.size = size;
    }

    public AstralObject() {
        this.name = generateAstralObjectName();
        this.uuid = UUID.randomUUID();
        this.size = 1;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Vec3 getPosition() {
        return position;
    }

    public void setPosition(Vec3 position) {
        this.position = position;
    }

    public static String generateAstralObjectName(){
        return "Astral Object";
    }

    @Override
    public String toString() {
        return "AstralObject{" +
                "uuid=" + uuid +
                ", name='" + name + '\'' +
                ", size=" + size +
                ", position=" + position +
                '}';
    }

    public CompoundTag toNbt(){
        CompoundTag tag = new CompoundTag();
        tag.putString("name", this.name);
        tag.putUUID("uuid", this.uuid);
        tag.putInt("size", this.size);
        return tag;
    }

    public static AstralObject fromNbt(CompoundTag tag){
        AstralObject object = new AstralObject();
        object.name = tag.getString("name");
        object.uuid = tag.getUUID("uuid");
        object.size = tag.getInt("size");
        return object;
    }


}
