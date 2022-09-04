package coffee.amo.astromancy.core.systems.stars.types;

import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Planet extends AstralObject {
    private Color skyColor = Color.BLACK;
    private Color landColor = Color.BLACK;
    private Color oceanColor = Color.BLACK;
    private boolean hasOcean;
    private boolean hasLand;
    private boolean hasAtmosphere;
    private boolean hasRings;
    private boolean hasClouds;
    private boolean hasMoon;
    private int ringCount;
    private int moonCount;
    private int atmosphereThickness;
    private int atmosphereDensity;
    private int mass;
    private float axisTilt;
    private float rotationSpeed;
    private float orbitSpeed;
    private float orbitRadius;
    private float orbitAngle;
    private Attribute attribute = Attributes.MAX_HEALTH;
    private List<Moon> moons = new ArrayList<>();

    public Planet(String name) {
        super(name);
    }
    
    public Planet(String name, UUID uuid, int size) {
        super(name, uuid, size);
    }
    
    public Planet(String name, UUID uuid, Color skyColor, Color landColor, Color oceanColor, boolean hasOcean, boolean hasLand, int mass, float axisTilt, float rotationSpeed, float orbitSpeed, float orbitRadius, float orbitAngle) {
        super(name);
        this.skyColor = skyColor;
        this.landColor = landColor;
        this.oceanColor = oceanColor;
        this.hasOcean = hasOcean;
        this.hasLand = hasLand;
        this.mass = mass;
        this.axisTilt = axisTilt;
        this.rotationSpeed = rotationSpeed;
        this.orbitSpeed = orbitSpeed;
        this.orbitRadius = orbitRadius;
        this.orbitAngle = orbitAngle;
    }

    public Planet() {
        super();
    }

    public void setSkyColor(Color skyColor) {
        this.skyColor = skyColor;
    }
    
    public void setLandColor(Color landColor) {
        this.landColor = landColor;
    }
    
    public void setOceanColor(Color oceanColor) {
        this.oceanColor = oceanColor;
    }
    
    public void setHasOcean(boolean hasOcean) {
        this.hasOcean = hasOcean;
    }
    
    public void setHasLand(boolean hasLand) {
        this.hasLand = hasLand;
    }
    
    public void setMass(int mass) {
        this.mass = mass;
    }
    
    public void setAxisTilt(float axisTilt) {
        this.axisTilt = axisTilt;
    }
    
    public void setRotationSpeed(float rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
    }
    
    public void setOrbitSpeed(float orbitSpeed) {
        this.orbitSpeed = orbitSpeed;
    }
    
    public void setOrbitRadius(float orbitRadius) {
        this.orbitRadius = orbitRadius;
    }
    
    public void setOrbitAngle(float orbitAngle) {
        this.orbitAngle = orbitAngle;
    }
    
    public Color getSkyColor() {
        return skyColor;
    }
    
    public Color getLandColor() {
        return landColor;
    }
    
    public Color getOceanColor() {
        return oceanColor;
    }
    
    public boolean hasOcean() {
        return hasOcean;
    }
    
    public boolean hasLand() {
        return hasLand;
    }
    
    public int getMass() {
        return mass;
    }
    
    public float getAxisTilt() {
        return axisTilt;
    }
    
    public float getRotationSpeed() {
        return rotationSpeed;
    }
    
    public float getOrbitSpeed() {
        return orbitSpeed;
    }
    
    public float getOrbitRadius() {
        return orbitRadius;
    }
    
    public float getOrbitAngle() {
        return orbitAngle;
    }
    
    public Attribute getAttribute() {
        return attribute;
    }
    
    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }
    
    public static String generateAstralObjectName(){
        return "Planet";
    }

    public boolean hasAtmosphere() {
        return hasAtmosphere;
    }

    public void setHasAtmosphere(boolean hasAtmosphere) {
        this.hasAtmosphere = hasAtmosphere;
    }

    public boolean hasRings() {
        return hasRings;
    }

    public void setHasRings(boolean hasRings) {
        this.hasRings = hasRings;
    }

    public boolean hasClouds() {
        return hasClouds;
    }

    public void setHasClouds(boolean hasClouds) {
        this.hasClouds = hasClouds;
    }

    public boolean hasMoon() {
        return hasMoon;
    }

    public void setHasMoon(boolean hasMoon) {
        this.hasMoon = hasMoon;
    }

    public int getRingCount() {
        return ringCount;
    }

    public void setRingCount(int ringCount) {
        this.ringCount = ringCount;
    }

    public int getMoonCount() {
        return moonCount;
    }

    public void setMoonCount(int moonCount) {
        this.moonCount = moonCount;
    }

    public int getAtmosphereThickness() {
        return atmosphereThickness;
    }

    public void setAtmosphereThickness(int atmosphereThickness) {
        this.atmosphereThickness = atmosphereThickness;
    }

    public int getAtmosphereDensity() {
        return atmosphereDensity;
    }

    public void setAtmosphereDensity(int atmosphereDensity) {
        this.atmosphereDensity = atmosphereDensity;
    }

    public CompoundTag toNbt(){
        CompoundTag nbt = new CompoundTag();
        nbt.putString("name", getName());
        nbt.putUUID("uuid", getUuid());
        nbt.putInt("mass", getMass());
        nbt.putFloat("axisTilt", getAxisTilt());
        nbt.putFloat("rotationSpeed", getRotationSpeed());
        nbt.putFloat("orbitSpeed", getOrbitSpeed());
        nbt.putFloat("orbitRadius", getOrbitRadius());
        nbt.putFloat("orbitAngle", getOrbitAngle());
        nbt.putBoolean("hasAtmosphere", hasAtmosphere());
        nbt.putBoolean("hasRings", hasRings());
        nbt.putBoolean("hasClouds", hasClouds());
        nbt.putBoolean("hasMoon", hasMoon());
        nbt.putInt("ringCount", getRingCount());
        nbt.putInt("moonCount", getMoonCount());
        nbt.putInt("atmosphereThickness", getAtmosphereThickness());
        nbt.putInt("atmosphereDensity", getAtmosphereDensity());
        nbt.putInt("skyColor", getSkyColor().getRGB());
        nbt.putInt("landColor", getLandColor().getRGB());
        nbt.putInt("oceanColor", getOceanColor().getRGB());
        nbt.putBoolean("hasOcean", hasOcean());
        nbt.putBoolean("hasLand", hasLand());
        nbt.putString("attribute",Registry.ATTRIBUTE.getKey(getAttribute()).toString());
        return nbt;
    }

    public static Planet fromNbt(CompoundTag tag){
        Planet planet = new Planet();
        planet.setName(tag.getString("name"));
        planet.setUuid(tag.getUUID("uuid"));
        planet.setMass(tag.getInt("mass"));
        planet.setAxisTilt(tag.getFloat("axisTilt"));
        planet.setRotationSpeed(tag.getFloat("rotationSpeed"));
        planet.setOrbitSpeed(tag.getFloat("orbitSpeed"));
        planet.setOrbitRadius(tag.getFloat("orbitRadius"));
        planet.setOrbitAngle(tag.getFloat("orbitAngle"));
        planet.setHasAtmosphere(tag.getBoolean("hasAtmosphere"));
        planet.setHasRings(tag.getBoolean("hasRings"));
        planet.setHasClouds(tag.getBoolean("hasClouds"));
        planet.setHasMoon(tag.getBoolean("hasMoon"));
        planet.setRingCount(tag.getInt("ringCount"));
        planet.setMoonCount(tag.getInt("moonCount"));
        planet.setAtmosphereThickness(tag.getInt("atmosphereThickness"));
        planet.setAtmosphereDensity(tag.getInt("atmosphereDensity"));
        planet.setSkyColor(new Color(tag.getInt("skyColor")));
        planet.setLandColor(new Color(tag.getInt("landColor")));
        planet.setOceanColor(new Color(tag.getInt("oceanColor")));
        planet.setHasOcean(tag.getBoolean("hasOcean"));
        planet.setHasLand(tag.getBoolean("hasLand"));
        planet.setAttribute(Registry.ATTRIBUTE.get(new ResourceLocation(tag.getString("attribute"))));
        return planet;
    }

    @Override
    public String toString() {
        return "Planet{" +
                "skyColor=" + skyColor +
                ", landColor=" + landColor +
                ", oceanColor=" + oceanColor +
                ", hasOcean=" + hasOcean +
                ", hasLand=" + hasLand +
                ", hasAtmosphere=" + hasAtmosphere +
                ", hasRings=" + hasRings +
                ", hasClouds=" + hasClouds +
                ", hasMoon=" + hasMoon +
                ", ringCount=" + ringCount +
                ", moonCount=" + moonCount +
                ", atmosphereThickness=" + atmosphereThickness +
                ", atmosphereDensity=" + atmosphereDensity +
                ", mass=" + mass +
                ", axisTilt=" + axisTilt +
                ", rotationSpeed=" + rotationSpeed +
                ", orbitSpeed=" + orbitSpeed +
                ", orbitRadius=" + orbitRadius +
                ", orbitAngle=" + orbitAngle +
                ", attribute=" + attribute +
                ", moons=" + moons +
                '}';
    }
}
