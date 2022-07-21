package coffee.amo.astromancy.core.systems.stars.classification;

public class Constellation {
    // Star names are the quadrant suits + number
    public String name;
    public int height;
    public Constellation(String name, int height) {
        this.name = name;
        this.height = height;
    }
}
