package coffee.amo.astromancy.client.systems;

import coffee.amo.astromancy.core.systems.stars.Star;
import coffee.amo.astromancy.core.systems.stars.classification.ConstellationInstance;
import coffee.amo.astromancy.core.systems.stars.classification.Constellations;

import java.util.ArrayList;
import java.util.List;

public class ClientConstellationHolder {
    public static List<ConstellationInstance> constellationInstances = new ArrayList<>();

    public static void setConstellationInstances(List<ConstellationInstance> constellationInstances) {
        ClientConstellationHolder.constellationInstances = constellationInstances;
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
