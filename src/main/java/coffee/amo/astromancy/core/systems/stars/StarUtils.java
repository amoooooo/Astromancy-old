package coffee.amo.astromancy.core.systems.stars;

import coffee.amo.astromancy.core.systems.stars.classification.*;
import coffee.amo.astromancy.core.util.StarSavedData;

import java.util.List;
import java.util.Random;

public class StarUtils {
    private static final List<String> luminosityClasses = List.of("I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X", "XI", "XII", "XIII", "XIV", "XV", "XVI", "XVII", "XVIII", "XIX", "XX", "XXI", "XXII", "XXIII", "XXIV", "XXV", "XXVI", "XXVII", "XXVIII", "XXIX", "XXX", "XXXI", "XXXII", "XXXIII", "XXXIV", "XXXV", "XXXVI", "XXXVII", "XXXVIII", "XXXIX", "XL", "XLI", "XLII", "XLIII", "XLIV", "XLV", "XLVI", "XLVII", "XLVIII", "XLIX", "L", "LI", "LII", "LIII", "LIV", "LV", "LVI", "LVII", "LVIII", "LIX", "LX", "LXI", "LXII", "LXIII", "LXIV", "LXV", "LXVI", "LXVII", "LXVIII", "LXIX", "LXX", "LXXI", "LXXII", "LXXIII", "LXXIV", "LXXV", "LXXVI", "LXXVII", "LXXVIII", "LXXIX", "LXXX", "LXXXI", "LXXXII", "LXXXIII", "LXXXIV", "LXXXV", "LXXXVI", "LXXXVII", "LXXXVIII", "LXXXIX", "XC", "XCI", "XCII", "XCIII", "XCIV", "XCV", "XCVI", "XCVII", "XCVIII", "XCIX", "C", "CI", "CII", "CIII", "CIV", "CV", "CVI", "CVII", "CVIII", "CIX", "CX", "CXI", "CXII", "CXIII", "CXIV", "CXV", "CX");

    public static Star generateRandomStar() {
        // generate and return random star
        Random random = new Random();
        int randomInt = random.nextInt(StarClass.values().length);
        Star star = new Star(StarClass.values()[randomInt]);
        star.constellation = Constellations.constellations.get(random.nextInt(Constellations.constellations.size()));
        star.luminosity = luminosityClasses.get(random.nextInt(luminosityClasses.size()));
        star.name = generateName(generateQuadrant(), generateQuadrant()) + " " + star.luminosity;
        star.mass = random.nextInt(100) + 1;
        star.strength = random.nextInt(100) + 1;
        if (StarSavedData.get().containsStarWithName(star.name)) {
            return generateRandomStar();
        }
        return star;
    }

    public static String generateName(Quadrant quadrant, Quadrant quadrant2){
        Random random = new Random();
        Random random2 = new Random();
        return random.nextInt((10-1)+1)+1 + " of " + quadrant.getName() + ", " + random2.nextInt((10-1)+1)+1 + " of " + quadrant2.getName();
    }

    public static Quadrant generateQuadrant(){
        Random random = new Random();
        return random.nextInt(2) == 0 ? (random.nextInt(2) == 0 ? Quadrants.STARS : Quadrants.PENTACLES) : (random.nextInt(2) == 0 ? Quadrants.SWORDS : Quadrants.WANDS);
    }
}
