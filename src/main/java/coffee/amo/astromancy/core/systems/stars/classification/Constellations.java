package coffee.amo.astromancy.core.systems.stars.classification;

import coffee.amo.astromancy.core.systems.stars.Star;

import java.util.List;
import java.util.Objects;

public class Constellations {
    public static Constellation THE_FOOL = new Constellation("The Fool", 0);
    public static Constellation THE_MAGICIAN = new Constellation("The Magician", 1);
    public static Constellation THE_HIGH_PRIESTESS = new Constellation("The High Priestess", -2);
    public static Constellation THE_EMPRESS = new Constellation("The Empress", 0);
    public static Constellation THE_EMPEROR = new Constellation("The Emperor", 1);
    public static Constellation THE_HIEROPHANT = new Constellation("The Hierophant", 2);
    public static Constellation THE_LOVERS = new Constellation("The Lovers", -1);
    public static Constellation THE_CHARIOT = new Constellation("The Chariot", -2);
    public static Constellation JUSTICE = new Constellation("Justice", 0);
    public static Constellation THE_HERMIT = new Constellation("The Hermit", -1);
    public static Constellation WHEEL_OF_FORTUNE = new Constellation("Wheel of Fortune", -2);
    public static Constellation STRENGTH = new Constellation("Strength", -1);
    public static Constellation THE_HANGED_MAN = new Constellation("The Hanged Man", 1);
    public static Constellation DEATH = new Constellation("Death", 2);
    public static Constellation TEMPERANCE = new Constellation("Temperance", -3);
    public static Constellation THE_DEVIL = new Constellation("The Devil", 0);
    public static Constellation THE_TOWER = new Constellation("The Tower", 1);
    public static Constellation THE_STAR = new Constellation("The Star", 2);
    public static Constellation THE_MOON = new Constellation("The Moon", -1);
    public static Constellation THE_SUN = new Constellation("The Sun", -2);
    public static Constellation JUDGEMENT = new Constellation("Judgement", -3);
    public static Constellation THE_WORLD = new Constellation("The World", 4);
    public static List<Constellation> constellations = List.of(
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

    public static Constellation register(String name, int height){
        Constellation constellation = new Constellation(name, height);
        constellations.add(constellation);
        return constellation;
    }

    public static Constellation findByName(String name) {
        for (Constellation constellation : constellations) {
            if (constellation.name.equals(name)) {
                return constellation;
            }
        }
        return null;
    }

    public static Constellation findByStar(Star star) {
        for (Constellation constellation : constellations) {
            if (constellation.containsStar(star)) {
                return constellation;
            }
        }
        return null;
    }

    public static void setConstellation(Constellation constellation){
        for(int i = 0; i < constellations.size(); i++){
            if(constellations.get(i).name.equals(constellation.name)){
                constellations.set(i, constellation);
            }
        }
    }
}
