package coffee.amo.astromancy.client.systems;

import coffee.amo.astromancy.core.systems.stars.classification.ConstellationInstance;

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
}
