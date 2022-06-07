package coffee.amo.astromancy.core.systems.stars;

import coffee.amo.astromancy.core.systems.stars.classification.StarClass;

public abstract class AbstractStar {
    public String name;
    public StarClass type;
    public String constellation;
    public String classification;
    public String luminosityClass;
    public String luminosity;
    public float strength;
    public float mass;

    public AbstractStar(StarClass type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }
    public StarClass getType() {
        return type;
    }
    public String getConstellation() {
        return constellation;
    }
    public String getClassification() {
        return classification;
    }
    public String getLuminosityClass() {
        return luminosityClass;
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
}
