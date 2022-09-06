package coffee.amo.astromancy.core.util;

import coffee.amo.astromancy.core.helpers.RomanNumeralHelper;
import coffee.amo.astromancy.core.helpers.StringHelper;
import coffee.amo.astromancy.core.systems.stars.systems.Galaxy;
import coffee.amo.astromancy.core.systems.stars.systems.StarSystem;
import coffee.amo.astromancy.core.systems.stars.systems.Supercluster;
import coffee.amo.astromancy.core.systems.stars.types.AstralObject;
import coffee.amo.astromancy.core.systems.stars.types.Moon;
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
                for (int k = 0; k < 6; k++) {
                    StarSystem starSystem = new StarSystem();
                    starSystem.setPosition(new Vec3(k, k, k));
                    Planet[] planets = new Planet[12];
                    for (int l = 0; l < rand.nextInt(7, 11); l++) {
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
                        planets[l].setHasClouds(rand.nextBoolean());
                        planets[l].setHasRings(rand.nextBoolean());
                        planets[l].setHasOcean(rand.nextBoolean());
                        planets[l].setHasAtmosphere(rand.nextBoolean());
                        planets[l].setHasLand(!planets[l].hasOcean() || rand.nextBoolean());
                        planets[l].setMoonCount(rand.nextInt(0, 4));
                        planets[l].setHasMoon(planets[l].getMoonCount() > 0);
                        for(int m = 0; m < planets[l].getMoonCount(); m++){
                            Moon moon = new Moon();
                            moon.setPosition(new Vec3(m, m, m));
                            moon.setAxisTilt(rand.nextFloat(0, 90));
                            moon.setMass(rand.nextInt(3, 15) * 100);
                            moon.setOrbitAngle(rand.nextFloat(0, 90));
                            planets[l].getMoons().add(moon);
                        }
                        planets[l].setName(generatePlanetName(planets[l]));
                    }
                    starSystem.setPlanets(planets);
                    Star[] stars = new Star[2];
                    for (int s = 0; s < rand.nextInt(1,3); s++) {
                        stars[s] = new Star(rand.nextInt(2400,35000));
                        stars[s].setName("Unnamed Star " + s);
                    }
                    starSystem.setStars(stars);
                    for(int a = 0; a < rand.nextInt(10, 27); a++){
                        AstralObject object = new AstralObject();
                        object.setPosition(new Vec3(a, a, a));
                        object.setName("");
                        object.setSize(rand.nextInt(1, 30));
                        object.setAxisTilt(rand.nextFloat(0, 90));
                        starSystem.addSolarSystemObject(object);
                    }
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

    public static String generatePlanetName(Planet planet){
        Random rand = new Random();
        String[] base = {"Agamemnon", "Agenor", "Aigisthos", "Alkmene", "Amazonen", "Amphitryon", "Andromeda", "Arachne", "Ariadne",
                "Atalanta", "Bellerophon", "Daidalos", "Danaiden", "Deukalion", "Dioskuren", "Elektra", "Europa", "Eurystheus", "Hekabe",
                "Hektor", "Helena", "Hero", "Iason", "Ikaros", "Ixion", "Kassandra", "Laokoon", "Leander", "Melampous", "Menelaos", "Minos",
                "Nestor", "Odysseus", "Oknos", "Orestes", "Orpheus", "Paris", "Pasiphae", "Penelope", "Perseus", "Priamos", "Pyrrha", "Sisyphos",
                "Tantalos", "Telemachos", "Theseus", "Acheloos", "Aletheia", "Anemoi", "Asklepios", "Charon", "Hekate", "Hesperiden", "Horen",
                "Hypnos", "Iris", "Moiren", "Musen", "Morpheus", "Musen", "Nymphen", "Oneiroi", "Pan", "Plutos", "Prometheus", "Thanatos", "Tyche", "Zelos"};
        String[] greekLetters = {"Alpha", "Beta", "Gamma", "Delta", "Epsilon", "Zeta", "Eta", "Theta", "Iota", "Kappa", "Lambda", "Mu", "Nu", "Xi", "Omicron", "Pi", "Rho", "Sigma", "Tau", "Upsilon", "Phi", "Chi", "Psi", "Omega"};
        String toRoman = RomanNumeralHelper.toRoman(planet.getMass()/100);
        String name = base[rand.nextInt(base.length)] + " " + greekLetters[rand.nextInt(greekLetters.length)] + " " + toRoman;
        return name;
    }
}
