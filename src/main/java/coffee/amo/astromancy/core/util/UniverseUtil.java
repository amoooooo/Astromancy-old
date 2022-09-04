package coffee.amo.astromancy.core.util;

import coffee.amo.astromancy.core.systems.stars.systems.Galaxy;
import coffee.amo.astromancy.core.systems.stars.systems.StarSystem;
import coffee.amo.astromancy.core.systems.stars.systems.Supercluster;
import coffee.amo.astromancy.core.systems.stars.types.AstralObject;
import coffee.amo.astromancy.core.systems.stars.types.Planet;
import coffee.amo.astromancy.core.systems.stars.types.Star;
import net.minecraft.world.phys.Vec3;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UniverseUtil {
    public static List<Supercluster> generateSuperclusters(){
        Random rand = new Random();
        List<Supercluster> clusters = new ArrayList<>();
        for(int i = 0; i < 5; i++){
            Supercluster cluster = new Supercluster();
            cluster.setPosition(new Vec3(i, i, i));
            List<Galaxy> galaxies = new ArrayList<>();
            for (int j = 0; j < 5; j++) {
                Galaxy galaxy = new Galaxy();
                galaxy.setPosition(new Vec3(j, j, j));
                List<StarSystem> starSystems = new ArrayList<>();
                for (int k = 0; k < 5; k++) {
                    StarSystem starSystem = new StarSystem();
                    starSystem.setPosition(new Vec3(k, k, k));
                    Planet[] planets = new Planet[9];
                    for (int l = 0; l < rand.nextInt(4, 8); l++) {
                        planets[l] = new Planet();
                        planets[l].setPosition(new Vec3(l, l, l));
                        planets[l].setLandColor(generateColor());
                        planets[l].setSkyColor(generateColor());
                        planets[l].setOceanColor(generateColor());
                        planets[l].setAxisTilt(rand.nextFloat(0, 66));
                        planets[l].setName("Unnamed Planet " + l);
                        planets[l].setMass(rand.nextInt(10) * 1000);
                        planets[l].setOrbitAngle(rand.nextFloat(0, 90));
                        planets[l].setRingCount(rand.nextInt(0, 2));
                    }
                    starSystem.setPlanets(planets);
                    Star[] stars = new Star[2];
                    for (int l = 0; l < rand.nextInt(1,3); l++) {
                        stars[l] = new Star(rand.nextInt(2400,35000));
                        stars[l].setName("Unnamed Star " + l);
                    }
                    starSystem.setStars(stars);
                    starSystems.add(starSystem);
                }
                galaxy.setStarSystems(starSystems);
                galaxies.add(galaxy);
            }
            cluster.setGalaxies(galaxies);
            clusters.add(cluster);
        }
        return clusters;
    }

    public static Color generateColor(){
        Random rand = new Random();
        double r = rand.nextFloat() / 2f + 0.5;
        double g = rand.nextFloat() / 2f + 0.5;
        double b = rand.nextFloat() / 2f + 0.5;
        return new Color((float) r, (float) g, (float) b);
    }
}
