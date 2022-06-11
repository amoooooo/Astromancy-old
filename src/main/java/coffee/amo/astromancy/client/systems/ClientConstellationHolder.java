package coffee.amo.astromancy.client.systems;

import coffee.amo.astromancy.core.systems.stars.classification.Quadrant;

import java.util.ArrayList;
import java.util.List;

public class ClientConstellationHolder {
    public static List<Quadrant> quadrants = new ArrayList<>();

    public static void addConstellation(Quadrant quadrant){
        quadrants.add(quadrant);
    }

    public static List<Quadrant> getQuadrants(){
        return quadrants;
    }
}
