package coffee.amo.astromancy.core.packets;

import coffee.amo.astromancy.core.systems.stars.classification.Constellation;
import coffee.amo.astromancy.core.systems.stars.classification.Quadrant;

import java.util.List;

public class StarDataPacket {
    public final List<Constellation> constellations;

    public StarDataPacket(List<Constellation> constellations) {
        this.constellations = constellations;
    }
}
