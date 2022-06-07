package coffee.amo.astromancy.core.systems.stars;

import coffee.amo.astromancy.core.systems.stars.classification.*;
import coffee.amo.astromancy.core.systems.stars.types.BinaryStar;
import coffee.amo.astromancy.core.systems.stars.types.SimpleStar;
import coffee.amo.astromancy.core.systems.stars.types.StarType;

import java.util.List;
import java.util.Random;

public class StarUtils {
    public static final List<AbstractStar> STAR_CLASSES = List.of(
            new BrightGiant(),
            new Crimson(),
            new Dark(),
            new Pure(),
            new Null(),
            new Crimson(),
            new Giant(),
            new Hell(),
            new HyperGiant(),
            new SuperGiant(),
            new WhiteDwarf(),
            new MainSequence(),
            new SubDwarf(),
            new SubGiant()
    );
    public static StarType generateRandomStar() {
        // generate a random star type
        StarType starType = new Random().nextInt(2) == 0 ? new BinaryStar(
                STAR_CLASSES.get(new Random().nextInt(STAR_CLASSES.size())),
                STAR_CLASSES.get(new Random().nextInt(STAR_CLASSES.size()))
        ) : new SimpleStar(
                STAR_CLASSES.get(new Random().nextInt(STAR_CLASSES.size()))
        );
        if(starType == StarTypes.SIMPLE_STAR){
            starType.star.name = "fgk";
            starType.star.classification = "AAA";
            starType.star.constellation = "BBB";
            starType.star.luminosityClass = "CCC";
            starType.star.luminosity = "DDD";
            starType.star.mass = new Random().nextInt(100);
            starType.star.strength = new Random().nextInt(100);
        } else if(starType instanceof BinaryStar){
            ((BinaryStar) starType).star1.name = "fgk";
            ((BinaryStar) starType).star1.classification = "AAA";
            ((BinaryStar) starType).star1.constellation = "BBB";
            ((BinaryStar) starType).star1.luminosityClass = "CCC";
            ((BinaryStar) starType).star1.luminosity = "DDD";
            ((BinaryStar) starType).star1.mass = new Random().nextInt(100);
            ((BinaryStar) starType).star1.strength = new Random().nextInt(100);
            ((BinaryStar) starType).star2.name = "rhhr";
            ((BinaryStar) starType).star2.classification = "bu5 ";
            ((BinaryStar) starType).star2.constellation = " sh";
            ((BinaryStar) starType).star2.luminosityClass = "s ye5";
            ((BinaryStar) starType).star2.luminosity = " ys5e";
            ((BinaryStar) starType).star2.mass = new Random().nextInt(100);
            ((BinaryStar) starType).star2.strength = new Random().nextInt(100);
        }

        return starType;
    }
}
