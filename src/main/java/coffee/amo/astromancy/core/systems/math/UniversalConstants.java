package coffee.amo.astromancy.core.systems.math;

import com.mojang.datafixers.util.Pair;

public class UniversalConstants {
    public static final double GRAVITATIONAL_CONSTANT = 6.67408e-11;
    public static double calculateOrbitSpeed(double mass, double radius) {
        return Math.sqrt((GRAVITATIONAL_CONSTANT * mass) / radius);
    }

    public static Pair<Double, Double> binaryStarCenterOfMass(double mass1, double mass2, double distance) {
        double a2 = mass1 / (mass1 + mass2) * distance;
        double a1 = (mass2 * a2) / mass1;
        return new Pair<>(a1, a2);
    }

    public static double binaryStarOrbitSpeed(double mass1, double mass2, double distance) {
        return Math.sqrt((GRAVITATIONAL_CONSTANT * (mass1 + mass2)) / distance);
    }
}
