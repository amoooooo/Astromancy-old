package coffee.amo.astromancy.core.systems.stars.types;

import net.minecraft.nbt.CompoundTag;

public class BlackHole extends AstralObject {
    private final int mass = Integer.MAX_VALUE;
    private int momentum;

    public BlackHole(String name) {
        super(name);
    }

    public BlackHole(String name, int momentum) {
        super(name);
        this.momentum = momentum;
    }

    public BlackHole() {
        super();
    }

    public int getMass() {
        return mass;
    }

    public int getMomentum() {
        return momentum;
    }

    public void setMomentum(int momentum) {
        this.momentum = momentum;
    }

    public static String generateAstralObjectName() {
        return "Black Hole";
    }

    @Override
    public CompoundTag toNbt() {
        CompoundTag tag = super.toNbt();
        tag.putInt("momentum", momentum);
        return tag;
    }

    public static BlackHole fromNbt(CompoundTag tag) {
        BlackHole blackHole = new BlackHole();
        blackHole.setName(tag.getString("name"));
        blackHole.setUuid(tag.getUUID("uuid"));
        blackHole.setSize(tag.getInt("size"));
        blackHole.setMomentum(tag.getInt("momentum"));
        return blackHole;
    }

    @Override
    public String toString() {
        return "BlackHole{" +
                "mass=" + mass +
                ", momentum=" + momentum +
                '}';
    }
}
