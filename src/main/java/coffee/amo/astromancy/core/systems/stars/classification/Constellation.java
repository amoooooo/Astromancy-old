package coffee.amo.astromancy.core.systems.stars.classification;

import coffee.amo.astromancy.core.systems.stars.Star;

import java.util.ArrayList;
import java.util.List;

public class Constellation {
    // Star names are the quadrant suits + number
    public List<Star> stars;
    public String name;
    public Constellation(String name){
        stars = new ArrayList<>();
        this.name = name;
    }
}
