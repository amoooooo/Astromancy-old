package coffee.amo.astromancy.client.systems;

import coffee.amo.astromancy.core.systems.stars.Star;
import coffee.amo.astromancy.core.systems.stars.classification.constellation.ConstellationInstance;
import coffee.amo.astromancy.core.systems.stars.classification.constellation.Constellations;
import coffee.amo.astromancy.core.systems.stars.systems.Supercluster;
import coffee.amo.astromancy.core.systems.stars.systems.Universe;

import java.util.ArrayList;
import java.util.List;

public class ClientUniverseHolder {
    public static Universe universe = new Universe();
    public static List<ConstellationInstance> constellationInstances = new ArrayList<>();

    public static void setConstellationInstances(List<ConstellationInstance> constellationInstances) {
        ClientUniverseHolder.constellationInstances = constellationInstances;
    }

    public static Universe getUniverse(){
        return universe;
    }

    public static void setUniverse(Universe universe){
        ClientUniverseHolder.universe = universe;
    }

    public static void addSupercluster(Supercluster supercluster, int index){
        universe.superclusters.add(index, supercluster);
    }

    public static List<ConstellationInstance> getConstellationInstances() {
        return constellationInstances;
    }

    public static Star getStar(int x, int y, Constellations constel) {
        for (ConstellationInstance constellationInstance : constellationInstances) {
            if (constellationInstance.getConstellation() == constel) {
                return constellationInstance.getStar(x, y);
            }
        }
        return null;
    }

    public static ConstellationInstance findConstellationFromStar(Star star) {
        for (ConstellationInstance constellationInstance : constellationInstances) {
            if (constellationInstance.getStar(star) != null) {
                return constellationInstance;
            }
        }
        return null;
    }
}
