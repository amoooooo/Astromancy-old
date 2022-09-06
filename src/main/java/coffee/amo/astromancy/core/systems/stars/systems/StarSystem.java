package coffee.amo.astromancy.core.systems.stars.systems;

import coffee.amo.astromancy.core.systems.math.UniversalConstants;
import coffee.amo.astromancy.core.systems.stars.types.AstralObject;
import coffee.amo.astromancy.core.systems.stars.types.Planet;
import coffee.amo.astromancy.core.systems.stars.types.Star;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class StarSystem {
    private String name;
    private Star[] stars = new Star[2];
    private Planet[] planets = new Planet[8];
    private List<AstralObject> solarSystemObjects = new ArrayList<>();
    public Vec3 position = Vec3.ZERO;
    public double starOrbitSpeed = 0;

    public StarSystem() {
        this.name = "Unnamed Star System";
        this.stars = new Star[2];
        int maxPlanets = 0;
        for (Star star : stars) {
            if(star == null) {
                maxPlanets += 1;
                continue;
            }
            switch(star.starClass){
                case ULTRAGIANT -> maxPlanets += 15;
                case SUPERGIANT -> maxPlanets += 13;
                case GIANT -> maxPlanets += 11;
                case MAIN_SEQUENCE -> maxPlanets += 9;
                case DWARF -> maxPlanets += 7;
                case WHITE_DWARF -> maxPlanets += 3;
            }
        }
        this.planets = new Planet[maxPlanets];
        this.solarSystemObjects = new ArrayList<>();
    }

    public StarSystem(Star[] stars) {
        this.stars = stars;
        int maxPlanets = 0;
        for (Star star : stars) {
            if(star == null) maxPlanets+= 1;
            switch(star.starClass){
                case ULTRAGIANT -> maxPlanets += 15;
                case SUPERGIANT -> maxPlanets += 13;
                case GIANT -> maxPlanets += 11;
                case MAIN_SEQUENCE -> maxPlanets += 9;
                case DWARF -> maxPlanets += 7;
                case WHITE_DWARF -> maxPlanets += 3;
            }
        }
        this.planets = new Planet[maxPlanets];
        this.solarSystemObjects = new ArrayList<>();
    }

    public StarSystem(Star[] stars, Planet[] planets) {
        this.stars = stars;
        this.planets = planets;
        this.solarSystemObjects = new ArrayList<>();
    }

    public StarSystem(Star[] stars, Planet[] planets, List<AstralObject> solarSystemObjects) {
        this.stars = stars;
        this.planets = planets;
        this.solarSystemObjects = solarSystemObjects;
    }

    public Star[] getStars() {
        return stars;
    }

    public void setStars(Star[] stars) {
        this.stars = stars;
    }

    public Planet[] getPlanets() {
        return planets;
    }

    public void setPlanets(Planet[] planets) {
        this.planets = planets;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<AstralObject> getSolarSystemObjects() {
        return solarSystemObjects;
    }

    public void setSolarSystemObjects(List<AstralObject> solarSystemObjects) {
        this.solarSystemObjects = solarSystemObjects;
    }

    public void addSolarSystemObject(AstralObject object){
        this.solarSystemObjects.add(object);
    }

    public void removeSolarSystemObject(AstralObject object){
        this.solarSystemObjects.remove(object);
    }

    public void removeSolarSystemObject(UUID uuid){
        this.solarSystemObjects.removeIf(object -> object.getUuid().equals(uuid));
    }

    public void removeSolarSystemObject(String name){
        this.solarSystemObjects.removeIf(object -> object.getName().equals(name));
    }

    public Vec3 getPosition() {
        return position;
    }

    public void setPosition(Vec3 position) {
        this.position = position;
    }

    public CompoundTag toNbt(){
        CompoundTag tag = new CompoundTag();
        tag.putString("name", this.name);
        CompoundTag postag = new CompoundTag();
        postag.putDouble("x", this.position.x);
        postag.putDouble("y", this.position.y);
        postag.putDouble("z", this.position.z);
        tag.put("position", postag);
        ListTag stars = new ListTag();
        for (Star star : this.stars) {
            if(star == null) continue;
            stars.add(star.toNbt());
        }
        tag.put("stars", stars);
        ListTag planets = new ListTag();
        for (Planet planet : this.planets) {
            if(planet == null) continue;
            planets.add(planet.toNbt());
        }
        tag.put("planets", planets);
        ListTag objects = new ListTag();
        for (AstralObject object : this.solarSystemObjects) {
            if(object == null) continue;
            objects.add(object.toNbt());
        }
        tag.put("objects", objects);
        tag.putDouble("starOrbitSpeed", this.starOrbitSpeed);
        return tag;
    }

    public static StarSystem fromNbt(CompoundTag tag){
        StarSystem system = new StarSystem();
        system.setName(tag.getString("name"));
        ListTag stars = tag.getList("stars", 10);
        Star[] starArray = new Star[stars.size()];
        for (int i = 0; i < stars.size(); i++) {
            starArray[i] = Star.fromNbt(stars.getCompound(i));
        }
        system.setStars(starArray);
        ListTag planets = tag.getList("planets", 10);
        Planet[] planetArray = new Planet[planets.size()];
        for (int i = 0; i < planets.size(); i++) {
            planetArray[i] = Planet.fromNbt(planets.getCompound(i));
        }
        system.setPlanets(planetArray);
        ListTag objects = tag.getList("objects", 10);
        List<AstralObject> objectList = new ArrayList<>();
        for (int i = 0; i < objects.size(); i++) {
            objectList.add(AstralObject.fromNbt(objects.getCompound(i)));
        }
        system.setSolarSystemObjects(objectList);
        system.setPosition(new Vec3(tag.getCompound("position").getDouble("x"), tag.getCompound("position").getDouble("y"), tag.getCompound("position").getDouble("z")));
        system.setStarOrbitSpeed(tag.getDouble("starOrbitSpeed"));
        return system;
    }

    public void setStarOrbitSpeed(double starOrbitSpeed) {
        this.starOrbitSpeed = starOrbitSpeed;
    }

    @Override
    public String toString() {
        return "StarSystem{" +
                "name='" + name + '\'' +
                ", stars=" + Arrays.toString(stars) +
                ", planets=" + Arrays.toString(planets) +
                ", solarSystemObjects=" + Arrays.toString(solarSystemObjects.stream().map(AstralObject::toString).toArray()) +
                ", position=" + position +
                '}';
    }

    public void init(){
        for(int i = 0; i < this.planets.length; i++){
            if(this.planets[i] == null) continue;
            this.planets[i].position.add(i, 0, 0);
        }
        if(this.stars[1] != null) {
            double a1 = UniversalConstants.binaryStarCenterOfMass(this.stars[0].getMass(), this.stars[1].getMass(), 1).getFirst();
            double a2 = UniversalConstants.binaryStarCenterOfMass(this.stars[0].getMass(), this.stars[1].getMass(), 2).getSecond();
            this.starOrbitSpeed = UniversalConstants.binaryStarOrbitSpeed(this.stars[0].getMass(), this.stars[1].getMass(), a1 + a2);
            this.stars[0].position.add(a1, 0, 0);
            this.stars[1].position.add(-a2, 0, 0);
        }
    }

    public void tick(){
        if(this.stars[1] != null) {
            this.stars[0].position.yRot((float) this.starOrbitSpeed);
            this.stars[1].position.yRot((float) -this.starOrbitSpeed);
        }
        for(Planet planet : this.planets){
            if(planet == null) continue;
            planet.position.yRot(planet.getOrbitSpeed());
        }
    }
}
