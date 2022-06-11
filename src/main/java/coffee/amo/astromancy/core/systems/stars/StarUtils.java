package coffee.amo.astromancy.core.systems.stars;

import coffee.amo.astromancy.core.helpers.RomanNumeralHelper;
import coffee.amo.astromancy.core.systems.stars.classification.*;
import coffee.amo.astromancy.core.util.StarSavedData;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Direction;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Random;

public class StarUtils {
    private static final List<String> luminosityClasses = List.of("I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X", "XI", "XII", "XIII", "XIV", "XV", "XVI", "XVII", "XVIII", "XIX", "XX", "XXI", "XXII", "XXIII", "XXIV", "XXV", "XXVI", "XXVII", "XXVIII", "XXIX", "XXX", "XXXI", "XXXII", "XXXIII", "XXXIV", "XXXV", "XXXVI", "XXXVII", "XXXVIII", "XXXIX", "XL", "XLI", "XLII", "XLIII", "XLIV", "XLV", "XLVI", "XLVII", "XLVIII", "XLIX", "L", "LI", "LII", "LIII", "LIV", "LV", "LVI", "LVII", "LVIII", "LIX", "LX", "LXI", "LXII", "LXIII", "LXIV", "LXV", "LXVI", "LXVII", "LXVIII", "LXIX", "LXX", "LXXI", "LXXII", "LXXIII", "LXXIV", "LXXV", "LXXVI", "LXXVII", "LXXVIII", "LXXIX", "LXXX", "LXXXI", "LXXXII", "LXXXIII", "LXXXIV", "LXXXV", "LXXXVI", "LXXXVII", "LXXXVIII", "LXXXIX", "XC", "XCI", "XCII", "XCIII", "XCIV", "XCV", "XCVI", "XCVII", "XCVIII", "XCIX", "C", "CI", "CII", "CIII", "CIV", "CV", "CVI", "CVII", "CVIII", "CIX", "CX", "CXI", "CXII", "CXIII", "CXIV", "CXV", "CX");
    private static final WeightedRandomList<StarClass> starClasses = WeightedRandomList.create(
            StarClass.HYPERGIANT,
            StarClass.SUPERGIANT,
            StarClass.BRIGHT_GIANT,
            StarClass.GIANT,
            StarClass.SUBGIANT,
            StarClass.MAIN_SEQUENCE,
            StarClass.DWARF,
            StarClass.SUBDWARF,
            StarClass.WHITE_DWARF,
            StarClass.CRIMSON,
            StarClass.PURE,
            StarClass.DARK,
            StarClass.EMPTY,
            StarClass.HELL
    );

    public static Star generateStar(Level level){
        Star star = new Star(starClasses.getRandom(level.random).isPresent() ? starClasses.getRandom(level.random).get() : StarClass.MAIN_SEQUENCE);
        star.setQuadrants(generateQuadrant(level), generateQuadrant(level));
        star.setConstellation(generateConstellation(star.getQuadrants().getFirst(), level));
        int X = level.random.nextInt(9)+1;
        int Y = level.random.nextInt(9)+1;
        Constellations.findByName(star.getConstellation().name).starsByQuadrant[X][Y] = star;
        star.setQuadrantCoordinates(new Pair<>(X, Y));
        star.setLuminosity((int) (level.random.nextInt(101) * star.getType().getMassMultiplier()));
        star.setName(star.getConstellation().name  + " " + RomanNumeralHelper.toRoman(star.getLuminosity()) + " [" + X + " of " + star.getQuadrants().getFirst().name + ", " + Y + " of " + star.getQuadrants().getSecond().name + "]");
        star.setMass( level.random.nextInt(101) * star.getType().getMassMultiplier());
        star.setStrength(level.random.nextInt(101)* star.getType().getMassMultiplier());
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

    public static Vec3 generatePosition(Vec3 center, Star star){
        Direction offsetDirection_x = star.getQuadrants().getFirst().direction;
        Direction offsetDirection_z = star.getQuadrants().getSecond().direction;
        int offset_x = star.getQuadrantCoordinates().getFirst();
        int offset_z = star.getQuadrantCoordinates().getSecond();
        double x = center.x + offsetDirection_x.getStepX() * (offset_x/10.0f);
        double z = center.z + offsetDirection_z.getStepZ() * (offset_z/10.0f);
        double y = center.y + (1 * (star.getConstellation().getHeight() / 10.0f));
        return new Vec3(x, y, z);
    }
}
