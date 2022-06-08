package coffee.amo.astromancy.core.systems.stars.classification;

import java.util.List;

public class Constellations {
    public static final Constellation THE_FOOL = new Constellation("The Fool");
    public static final Constellation THE_MAGICIAN = new Constellation("The Magician");
    public static final Constellation THE_HIGH_PRIESTESS = new Constellation("The High Priestess");
    public static final Constellation THE_EMPRESS = new Constellation("The Empress");
    public static final Constellation THE_EMPEROR = new Constellation("The Emperor");
    public static final Constellation THE_HIEROPHANT = new Constellation("The Hierophant");
    public static final Constellation THE_LOVERS = new Constellation("The Lovers");
    public static final Constellation THE_CHARIOT = new Constellation("The Chariot");
    public static final Constellation JUSTICE = new Constellation("Justice");
    public static final Constellation THE_HERMIT = new Constellation("The Hermit");
    public static final Constellation WHEEL_OF_FORTUNE = new Constellation("Wheel of Fortune");
    public static final Constellation STRENGTH = new Constellation("Strength");
    public static final Constellation THE_HANGED_MAN = new Constellation("The Hanged Man");
    public static final Constellation DEATH = new Constellation("Death");
    public static final Constellation TEMPERANCE = new Constellation("Temperance");
    public static final Constellation THE_DEVIL = new Constellation("The Devil");
    public static final Constellation THE_TOWER = new Constellation("The Tower");
    public static final Constellation THE_STAR = new Constellation("The Star");
    public static final Constellation THE_MOON = new Constellation("The Moon");
    public static final Constellation THE_SUN = new Constellation("The Sun");
    public static final Constellation JUDGEMENT = new Constellation("Judgement");
    public static final Constellation THE_WORLD = new Constellation("The World");
    public static final List<Constellation> constellations = List.of(
            THE_FOOL,
            THE_MAGICIAN,
            THE_HIGH_PRIESTESS,
            THE_EMPRESS,
            THE_EMPEROR,
            THE_HIEROPHANT,
            THE_LOVERS,
            THE_CHARIOT,
            JUSTICE,
            THE_HERMIT,
            WHEEL_OF_FORTUNE,
            STRENGTH,
            THE_HANGED_MAN,
            DEATH,
            TEMPERANCE,
            THE_DEVIL,
            THE_TOWER,
            THE_STAR,
            THE_MOON,
            THE_SUN,
            JUDGEMENT,
            THE_WORLD
    );

    public static Constellation findByName(String name) {
        for (Constellation constellation : constellations) {
            if (constellation.name.equals(name)) {
                return constellation;
            }
        }
        return null;
    }
}
