package coffee.amo.astromancy.core.systems.stars.types;

import coffee.amo.astromancy.core.systems.stars.AbstractStar;
import com.mojang.datafixers.util.Pair;

public class BinaryStar extends StarType{
    public Pair<AbstractStar, AbstractStar> pair;
    public AbstractStar star1;
    public AbstractStar star2;

    public BinaryStar(AbstractStar star1, AbstractStar star2) {
        super(star1);
        this.pair = new Pair<>(star1, star2);
        this.star1 = star1;
        this.star2 = star2;
    }

    @Override
    public AbstractStar getStar() {
        return pair.getFirst();
    }
}
