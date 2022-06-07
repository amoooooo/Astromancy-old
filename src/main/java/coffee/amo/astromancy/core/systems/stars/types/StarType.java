package coffee.amo.astromancy.core.systems.stars.types;

import coffee.amo.astromancy.core.systems.stars.AbstractStar;
import com.mojang.datafixers.util.Pair;

public class StarType {
    public AbstractStar star;
    public Pair<AbstractStar, AbstractStar> pair;
    public StarType(AbstractStar star) {
        this.star = star;
    }

    public AbstractStar getStar() {
        return star;
    }

    //toString
    public String toString() {
        return ("StarType: " + star.type.toString() + " " + star.name);
    }
}
