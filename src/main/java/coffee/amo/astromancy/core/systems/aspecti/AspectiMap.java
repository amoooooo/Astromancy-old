package coffee.amo.astromancy.core.systems.aspecti;

import coffee.amo.astromancy.aequivaleo.compound.AspectiCompoundType;
import net.minecraft.Util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AspectiMap {

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

}
