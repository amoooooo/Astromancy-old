package coffee.amo.astromancy.core.systems.stars;

import coffee.amo.astromancy.core.helpers.RomanNumeralHelper;
import coffee.amo.astromancy.core.systems.stars.classification.*;
import coffee.amo.astromancy.core.util.StarSavedData;
import com.mojang.datafixers.util.Pair;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Random;

public class StarUtils {
    private static final List<String> luminosityClasses = List.of("I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X", "XI", "XII", "XIII", "XIV", "XV", "XVI", "XVII", "XVIII", "XIX", "XX", "XXI", "XXII", "XXIII", "XXIV", "XXV", "XXVI", "XXVII", "XXVIII", "XXIX", "XXX", "XXXI", "XXXII", "XXXIII", "XXXIV", "XXXV", "XXXVI", "XXXVII", "XXXVIII", "XXXIX", "XL", "XLI", "XLII", "XLIII", "XLIV", "XLV", "XLVI", "XLVII", "XLVIII", "XLIX", "L", "LI", "LII", "LIII", "LIV", "LV", "LVI", "LVII", "LVIII", "LIX", "LX", "LXI", "LXII", "LXIII", "LXIV", "LXV", "LXVI", "LXVII", "LXVIII", "LXIX", "LXX", "LXXI", "LXXII", "LXXIII", "LXXIV", "LXXV", "LXXVI", "LXXVII", "LXXVIII", "LXXIX", "LXXX", "LXXXI", "LXXXII", "LXXXIII", "LXXXIV", "LXXXV", "LXXXVI", "LXXXVII", "LXXXVIII", "LXXXIX", "XC", "XCI", "XCII", "XCIII", "XCIV", "XCV", "XCVI", "XCVII", "XCVIII", "XCIX", "C", "CI", "CII", "CIII", "CIV", "CV", "CVI", "CVII", "CVIII", "CIX", "CX", "CXI", "CXII", "CXIII", "CXIV", "CXV", "CX");

//    public static Star generateRandomStar(Level level) {
//        // generate and return random star
//        int randomInt = level.random.nextInt(StarClass.values().length);
//        Star star = new Star(StarClass.values()[randomInt]);
//        star.constellation = generateConstellation(level);
//        star.luminosity = luminosityClasses.get(level.random.nextInt(luminosityClasses.size()));
//        star.name = generateName(generateQuadrant(level), generateQuadrant(level), level) + " " + star.luminosity;
//        star.mass = level.random.nextInt(100) + 1;
//        star.strength = level.random.nextInt(100) + 1;
//        if (StarSavedData.get().containsStarWithName(star.name)) {
//            return generateRandomStar(level);
//        }
//        return star;
//    }

    public static Star generateStar(Level level){
        Star star = new Star(StarClass.values()[level.random.nextInt(StarClass.values().length)]);
        star.setQuadrants(generateQuadrant(level), generateQuadrant(level));
        star.setConstellation(generateConstellation(star.getQuadrants().getFirst(), level));
        int X = level.random.nextInt(9)+1;
        int Y = level.random.nextInt(9)+1;
        Constellations.findByName(star.getConstellation().name).starsByQuadrant[X][Y] = star;
        star.setQuadrantCoordinates(new Pair<>(X, Y));
        star.setLuminosity(level.random.nextInt(3999));
        star.setName(star.getConstellation().name  + " " + RomanNumeralHelper.toRoman(star.getLuminosity()) + " [" + X + " of " + star.getQuadrants().getFirst().name + ", " + Y + " of " + star.getQuadrants().getSecond().name + "]");
        star.setMass( level.random.nextInt(100) + 1);
        star.setStrength(level.random.nextInt(100) + 1);
        return star;
    }

    public static Star findStarByArcana(int x, int y, Constellation constellation){
        return Constellations.findByName(constellation.name).starsByQuadrant[x][y];
    }

    public static String generateName(Quadrant quadrant, Quadrant quadrant2, Level level){
        return level.random.nextInt(11) + " of " + quadrant.getName() + ", " + level.random.nextInt(11) + " of " + quadrant2.getName();
    }

    public static Quadrant generateQuadrant(Level level){
        return level.random.nextInt(2) == 0 ? (level.random.nextInt(2) == 0 ? Quadrants.STARS : Quadrants.PENTACLES) : (level.random.nextInt(2) == 0 ? Quadrants.SWORDS : Quadrants.WANDS);
    }

    public static Constellation generateConstellation(Quadrant quadrant, Level level){
        return Quadrants.randomConstellationInQuadrant(quadrant, level);
    }
}
