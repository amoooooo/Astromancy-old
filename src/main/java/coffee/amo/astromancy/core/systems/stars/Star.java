package coffee.amo.astromancy.core.systems.stars;

import coffee.amo.astromancy.core.systems.stars.classification.Constellation;
import coffee.amo.astromancy.core.systems.stars.classification.Constellations;
import coffee.amo.astromancy.core.systems.stars.classification.StarClass;
import net.minecraft.nbt.CompoundTag;

import java.util.UUID;

public class Star {
    public UUID uuid;
    public String name;
    public final StarClass type;
    public String luminosity;
    public float strength;
    public float mass;
    public Constellation constellation;

    public Star(StarClass type) {
        this.type = type;
        this.uuid = UUID.randomUUID();
    }

    public StarClass getType() {
        return type;
    }
    public Constellation getConstellation() {
        return constellation;
    }
    public String getLuminosity() {
        return luminosity;
    }
    public float getStrength() {
        return strength;
    }
    public float getMass() {
        return mass;
    }

    public CompoundTag toNbt(){
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.putString("uuid", uuid.toString());
        compoundTag.putString("name", name);
        compoundTag.putString("type", type.toString());
        compoundTag.putString("constellation", constellation.name);
        compoundTag.putString("luminosity", luminosity);
        compoundTag.putFloat("strength", strength);
        compoundTag.putFloat("mass", mass);
        return compoundTag;
    }

    public static Star fromNbt(CompoundTag tag){
        Star star = new Star(StarClass.valueOf(tag.getString("type")));
        star.uuid = UUID.fromString(tag.getString("uuid"));
        star.name = tag.getString("name");
        star.constellation = Constellations.findByName(tag.getString("constellation"));
        star.luminosity = tag.getString("luminosity");
        star.strength = tag.getFloat("strength");
        star.mass = tag.getFloat("mass");
        return star;
    }

    //toString()
    public String getString(){
        return
                "Name: '" + name + '\'' +
                ", Type:" + type +
                ", Constellation: '" + constellation.name + '\'';
    }
}
