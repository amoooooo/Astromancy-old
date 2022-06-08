package coffee.amo.astromancy.core.systems.stars.classification;

import net.minecraft.core.Direction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Quadrant {
    public Direction direction;
    public String name;
    public List<Constellation> constellations = new ArrayList<>();
    public Quadrant(Direction direction, String name){
        this.direction = direction;
        this.name = name;
    }

    public void addConstellation(Constellation constellation){
        constellations.add(constellation);
    }

    public Quadrant addConstellations(Constellation... constellations){
        this.constellations.addAll(Arrays.asList(constellations));
        return this;
    }

    public String getName(){
        return name;
    }
}
