package coffee.amo.astromancy.core.systems.aspecti;

import coffee.amo.astromancy.aequivaleo.compound.AspectiCompoundType;
import net.minecraft.Util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AspectiMap {
    public static final Map<Aspecti, String> map = Util.make(() -> {
                Map<Aspecti, String> map = new HashMap<>();
                map.put(Aspecti.CONJUNCTION, "!");
                map.put(Aspecti.OPPOSITION, "\"");
                map.put(Aspecti.SQUARE, "#");
                map.put(Aspecti.TRINE, "$");
                map.put(Aspecti.SEXTILE, "%");
                map.put(Aspecti.SEMISEXTILE, "&");
                map.put(Aspecti.QUINTILE, "'");
                map.put(Aspecti.QUINCUNX, "(");
                map.put(Aspecti.OCTILE, ")");
                map.put(Aspecti.TRIOCTILE, "*");
                map.put(Aspecti.DECILE, "+");
                return map;
            }
    );

    // map of ints to Aspecti
    public static final Map<Integer, Aspecti> intMap = Util.make(() -> {
        Map<Integer, Aspecti> map = new HashMap<>();
        map.put(0, Aspecti.CONJUNCTION);
        map.put(1, Aspecti.OPPOSITION);
        map.put(2, Aspecti.SQUARE);
        map.put(3, Aspecti.TRINE);
        map.put(4, Aspecti.SEXTILE);
        map.put(5, Aspecti.SEMISEXTILE);
        map.put(6, Aspecti.QUINTILE);
        map.put(7, Aspecti.QUINCUNX);
        map.put(8, Aspecti.OCTILE);
        map.put(9, Aspecti.TRIOCTILE);
        map.put(10, Aspecti.DECILE);
        return map;
    });

    public static final Map<Integer, String> small = Util.make(() -> {
        Map<Integer, String> map = new HashMap<>();
        map.put(0, "\u2080");
        map.put(1, "\u2081");
        map.put(2, "\u2082");
        map.put(3, "\u2083");
        map.put(4, "\u2084");
        map.put(5, "\u2085");
        map.put(6, "\u2086");
        map.put(7, "\u2087");
        map.put(8, "\u2088");
        map.put(9, "\u2089");
        return map;
    });

    public static String convertDoubleToSmallString(int number){
        List<Character> list = Integer.toString(number).chars().mapToObj(c -> (char) c).toList();
        StringBuilder result = new StringBuilder();
        for(Character c : list){
            if(c == '.'){
                result.append("\u002E");
            } else {
                result.append(small.get(Integer.parseInt(c.toString())));
            }
        }
        return result.toString();
    }

    public static String getAspectiSymbol(Aspecti aspecti){
        return map.get(aspecti);
    }
}
