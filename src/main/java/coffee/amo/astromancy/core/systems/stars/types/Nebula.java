package coffee.amo.astromancy.core.systems.stars.types;

import net.minecraft.nbt.CompoundTag;

import java.awt.*;

public class Nebula extends AstralObject {
    private Color color;

    public Nebula(String name) {
        super(name);
    }

    public Nebula(String name, Color color) {
        super(name);
        this.color = color;
    }

    public Nebula() {
        super();
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public CompoundTag toNbt() {
        CompoundTag tag = super.toNbt();
        tag.putInt("color", color.getRGB());
        return tag;
    }

    public static Nebula fromNbt(CompoundTag tag) {
        Nebula nebula = new Nebula();
        nebula.setName(tag.getString("name"));
        nebula.setUuid(tag.getUUID("uuid"));
        nebula.setSize(tag.getInt("size"));
        nebula.setColor(new Color(tag.getInt("color")));
        return nebula;
    }

    @Override
    public String toString() {
        return "Nebula{" +
                "color=" + color +
                '}';
    }
}
