package coffee.amo.astromancy.core.helpers;

public class StringHelper {
    public static String capitalize(String str) {
        if (str.length() == 0) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
