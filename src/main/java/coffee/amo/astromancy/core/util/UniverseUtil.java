package coffee.amo.astromancy.core.util;

import coffee.amo.astromancy.core.systems.stars.systems.Galaxy;
import coffee.amo.astromancy.core.systems.stars.systems.StarSystem;
import coffee.amo.astromancy.core.systems.stars.systems.Supercluster;
import coffee.amo.astromancy.core.systems.stars.types.AstralObject;
import coffee.amo.astromancy.core.systems.stars.types.Planet;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class UniverseUtil {
    public static List<Supercluster> generateSuperclusters(){
        List<Supercluster> clusters = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            Supercluster cluster = new Supercluster();
            cluster.setPosition(new Vec3(i, i, i));
            List<Galaxy> galaxies = new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                Galaxy galaxy = new Galaxy();
                galaxy.setPosition(new Vec3(j, j, j));
                List<StarSystem> starSystems = new ArrayList<>();
                for (int k = 0; k < 10; k++) {
                    StarSystem starSystem = new StarSystem();
                    starSystem.setPosition(new Vec3(k, k, k));
                    Planet[] planets = new Planet[10];
                    for (int l = 0; l < 10; l++) {
                        planets[l] = new Planet();
                        planets[l].setPosition(new Vec3(l, l, l));
                    }
                    starSystem.setPlanets(planets);
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
}
