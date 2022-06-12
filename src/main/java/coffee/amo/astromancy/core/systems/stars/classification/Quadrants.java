package coffee.amo.astromancy.core.systems.stars.classification;

import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;

public class Quadrants {
    public static Quadrant PENTACLES = new Quadrant(Direction.EAST, "Pentacles").addConstellations(
            Constellations.JUSTICE,
            Constellations.THE_HANGED_MAN,
            Constellations.DEATH,
            Constellations.STRENGTH,
            Constellations.THE_CHARIOT,
            Constellations.JUDGEMENT
    );
    public static Quadrant SWORDS = new Quadrant(Direction.WEST, "Swords").addConstellations(
            Constellations.THE_DEVIL,
            Constellations.THE_TOWER,
            Constellations.THE_STAR,
            Constellations.THE_MOON,
            Constellations.THE_SUN
    );
    public static Quadrant WANDS = new Quadrant(Direction.NORTH, "Wands").addConstellations(
            Constellations.THE_EMPRESS,
            Constellations.THE_EMPEROR,
            Constellations.THE_HIEROPHANT,
            Constellations.THE_LOVERS,
            Constellations.THE_HIGH_PRIESTESS
    );
    public static Quadrant STARS = new Quadrant(Direction.SOUTH, "Stars").addConstellations(
            Constellations.THE_FOOL,
            Constellations.THE_MAGICIAN,
            Constellations.THE_HERMIT,
            Constellations.WHEEL_OF_FORTUNE,
            Constellations.TEMPERANCE,
            Constellations.THE_WORLD
    );

    public static final Quadrant[] QUADRANTS = new Quadrant[]{
            PENTACLES,
            SWORDS,
            WANDS,
            STARS
    };

    public static Quadrant findQuadrantFromConstellation(Constellation constellation){
        for(Quadrant quadrant : QUADRANTS){
            if(quadrant.constellations.contains(constellation)){
                return quadrant;
            }
        }
        return null;
    }

    public static Quadrant findQuadrantFromName(String name){
        for(Quadrant quadrant : QUADRANTS){
            if(quadrant.name.equals(name)){
                return quadrant;
            }
        }
        return null;
    }
    public static Constellation randomConstellationInQuadrant(Quadrant quadrant, Level level){
        return quadrant.constellations.get(level.random.nextInt(quadrant.constellations.size()));
    }
}
