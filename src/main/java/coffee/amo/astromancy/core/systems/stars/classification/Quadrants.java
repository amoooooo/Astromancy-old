package coffee.amo.astromancy.core.systems.stars.classification;

import coffee.amo.astromancy.core.systems.stars.Star;

public enum Quadrants {
    CUPS(Constellations.THE_FOOL, Constellations.THE_MAGICIAN, Constellations.THE_HERMIT, Constellations.WHEEL_OF_FORTUNE, Constellations.TEMPERANCE, Constellations.THE_WORLD),
    WANDS(Constellations.THE_EMPEROR, Constellations.THE_HIEROPHANT, Constellations.THE_LOVERS, Constellations.THE_HIGH_PRIESTESS, Constellations.THE_EMPRESS),
    SWORDS(Constellations.THE_DEVIL, Constellations.THE_TOWER, Constellations.THE_STAR, Constellations.THE_MOON, Constellations.THE_SUN),
    PENTACLES(Constellations.JUSTICE, Constellations.THE_HANGED_MAN, Constellations.DEATH, Constellations.STRENGTH, Constellations.THE_CHARIOT, Constellations.JUDGEMENT);

    private final Constellations[] constellations;

    Quadrants(Constellations... constellations) {
        this.constellations = constellations;
    }

    public Constellations[] getConstellations() {
        return constellations;
    }

    public static Quadrants getQuadrant(Constellations constellation) {
        for (Quadrants quadrant : Quadrants.values()) {
            for (Constellations c : quadrant.getConstellations()) {
                if (c == constellation) {
                    return quadrant;
                }
            }
        }
        return null;
    }
}
