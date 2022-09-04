package coffee.amo.astromancy.core.systems.stars.systems;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Supercluster {
    private String name;
    private List<Galaxy> galaxies = new ArrayList<>();
    public Vec3 position = Vec3.ZERO;

    public Supercluster(String name) {
        this.name = name;
    }

    public Supercluster() {
        this.name = "Unnamed Supercluster";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPosition(Vec3 position) {
        this.position = position;
    }

    public Vec3 getPosition() {
        return position;
    }

    public List<Galaxy> getGalaxies() {
        return galaxies;
    }

    public void setGalaxies(List<Galaxy> galaxies) {
        this.galaxies = galaxies;
    }

    public CompoundTag toNbt(){
        CompoundTag tag = new CompoundTag();
        tag.putString("name", name);
        ListTag galaxiesTag = new ListTag();
        for (Galaxy galaxy : galaxies) {
            galaxiesTag.add(galaxy.toNbt());
        }
        tag.put("galaxies", galaxiesTag);
        CompoundTag positionTag = new CompoundTag();
        positionTag.putDouble("x", position.x);
        positionTag.putDouble("y", position.y);
        positionTag.putDouble("z", position.z);
        tag.put("position", positionTag);
        return tag;
    }

    public static Supercluster fromNbt(CompoundTag tag){
        Supercluster supercluster = new Supercluster();
        supercluster.setName(tag.getString("name"));
        ListTag galaxiesTag = tag.getList("galaxies", 10);
        for (int i = 0; i < galaxiesTag.size(); i++) {
            CompoundTag galaxyTag = galaxiesTag.getCompound(i);
            Galaxy galaxy = Galaxy.fromNbt(galaxyTag);
            supercluster.getGalaxies().add(galaxy);
        }
        CompoundTag positionTag = tag.getCompound("position");
        supercluster.setPosition(new Vec3(positionTag.getDouble("x"), positionTag.getDouble("y"), positionTag.getDouble("z")));
        return supercluster;
    }

    @Override
    public String toString() {
        return "Supercluster{" +
                "name='" + name + '\'' +
                ", galaxies=" + Arrays.toString(galaxies.stream().map(Galaxy::toString).toArray()) +
                ", position=" + position +
                '}';
    }
}
