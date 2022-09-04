package coffee.amo.astromancy.core.systems.stars.systems;

import coffee.amo.astromancy.core.systems.stars.types.AstralObject;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Galaxy {
    private String name;
    private List<StarSystem> starSystems = new ArrayList<>();
    private List<AstralObject> astralObjects = new ArrayList<>();
    public Vec3 position = Vec3.ZERO;

    public Galaxy(String name) {
        this.name = name;
    }

    public Galaxy() {
        this.name = "Unnamed Galaxy";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<StarSystem> getStarSystems() {
        return starSystems;
    }

    public void setStarSystems(List<StarSystem> starSystems) {
        this.starSystems = starSystems;
    }

    public List<AstralObject> getAstralObjects() {
        return astralObjects;
    }

    public void setAstralObjects(List<AstralObject> astralObjects) {
        this.astralObjects = astralObjects;
    }

    public static String generateGalaxyName(){
        return "Galaxy";
    }

    public Vec3 getPosition() {
        return position;
    }

    public void setPosition(Vec3 position) {
        this.position = position;
    }

    public CompoundTag toNbt(){
        CompoundTag tag = new CompoundTag();
        tag.putString("name", name);
        ListTag starSystemsTag = new ListTag();
        for (StarSystem starSystem : starSystems) {
            if(starSystem == null) continue;
            starSystemsTag.add(starSystem.toNbt());
        }
        tag.put("starSystems", starSystemsTag);
        ListTag astralObjectsTag = new ListTag();
        for (AstralObject astralObject : astralObjects) {
            if(astralObject == null) continue;
            astralObjectsTag.add(astralObject.toNbt());
        }
        tag.put("astralObjects", astralObjectsTag);
        CompoundTag positionTag = new CompoundTag();
        positionTag.putDouble("x", position.x);
        positionTag.putDouble("y", position.y);
        positionTag.putDouble("z", position.z);
        tag.put("position", positionTag);
        return tag;
    }

    public static Galaxy fromNbt(CompoundTag tag){
        Galaxy galaxy = new Galaxy();
        galaxy.setName(tag.getString("name"));
        ListTag starSystemsTag = tag.getList("starSystems", 10);
        for (int i = 0; i < starSystemsTag.size(); i++) {
            galaxy.getStarSystems().add(StarSystem.fromNbt(starSystemsTag.getCompound(i)));
        }
        ListTag astralObjectsTag = tag.getList("astralObjects", 10);
        for (int i = 0; i < astralObjectsTag.size(); i++) {
            galaxy.getAstralObjects().add(AstralObject.fromNbt(astralObjectsTag.getCompound(i)));
        }
        CompoundTag positionTag = tag.getCompound("position");
        galaxy.setPosition(new Vec3(positionTag.getDouble("x"), positionTag.getDouble("y"), positionTag.getDouble("z")));
        return galaxy;
    }

    @Override
    public String toString() {
        return "Galaxy{" +
                "name='" + name + '\'' +
                ", starSystems=" + Arrays.toString(starSystems.stream().map(StarSystem::toString).toArray()) +
                ", astralObjects=" + (astralObjects == null ? "" : Arrays.toString(astralObjects.stream().map(AstralObject::toString).toArray())) +
                ", position=" + position +
                '}';
    }
}
