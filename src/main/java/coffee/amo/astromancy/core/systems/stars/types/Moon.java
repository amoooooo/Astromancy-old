package coffee.amo.astromancy.core.systems.stars.types;

import net.minecraft.nbt.CompoundTag;

import java.util.UUID;

public class Moon extends AstralObject {
    private int mass;
    private float axisTilt;
    private float rotationSpeed;
    private float orbitSpeed;
    private float orbitRadius;
    private float orbitAngle;
    public Moon(String name) {
        super(name);
    }

    public Moon() {
        super();
    }

    public Moon(String name, UUID uuid, int size) {
        super(name, uuid, size);
    }

public Moon(String name, UUID uuid, int mass, float axisTilt, float rotationSpeed, float orbitSpeed, float orbitRadius, float orbitAngle) {
        super(name);
        this.mass = mass;
        this.axisTilt = axisTilt;
        this.rotationSpeed = rotationSpeed;
        this.orbitSpeed = orbitSpeed;
        this.orbitRadius = orbitRadius;
        this.orbitAngle = orbitAngle;
    }

    public int getMass() {
        return mass;
    }

    public void setMass(int mass) {
        this.mass = mass;
    }

    public float getAxisTilt() {
        return axisTilt;
    }

    public void setAxisTilt(float axisTilt) {
        this.axisTilt = axisTilt;
    }

    public float getRotationSpeed() {
        return rotationSpeed;
    }

    public void setRotationSpeed(float rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
    }

    public float getOrbitSpeed() {
        return orbitSpeed;
    }

    public void setOrbitSpeed(float orbitSpeed) {
        this.orbitSpeed = orbitSpeed;
    }

    public float getOrbitRadius() {
        return orbitRadius;
    }

    public void setOrbitRadius(float orbitRadius) {
        this.orbitRadius = orbitRadius;
    }

    public float getOrbitAngle() {
        return orbitAngle;
    }

    public void setOrbitAngle(float orbitAngle) {
        this.orbitAngle = orbitAngle;
    }

    public void tick() {
        this.orbitAngle += this.orbitSpeed;
        this.orbitAngle %= 360;
    }

    public CompoundTag toNbt(){
        CompoundTag tag = new CompoundTag();
        tag.putString("name", this.getName());
        tag.putUUID("uuid", this.getUuid());
        tag.putInt("size", this.getSize());
        tag.putInt("mass", this.getMass());
        tag.putFloat("axisTilt", this.getAxisTilt());
        tag.putFloat("rotationSpeed", this.getRotationSpeed());
        tag.putFloat("orbitSpeed", this.getOrbitSpeed());
        tag.putFloat("orbitRadius", this.getOrbitRadius());
        tag.putFloat("orbitAngle", this.getOrbitAngle());
        return tag;
    }

    public static Moon fromNbt(CompoundTag tag) {
        Moon moon = new Moon();
        moon.setName(tag.getString("name"));
        moon.setUuid(tag.getUUID("uuid"));
        moon.setSize(tag.getInt("size"));
        moon.setMass(tag.getInt("mass"));
        moon.setAxisTilt(tag.getFloat("axisTilt"));
        moon.setRotationSpeed(tag.getFloat("rotationSpeed"));
        moon.setOrbitSpeed(tag.getFloat("orbitSpeed"));
        moon.setOrbitRadius(tag.getFloat("orbitRadius"));
        moon.setOrbitAngle(tag.getFloat("orbitAngle"));
        return moon;
    }
}
