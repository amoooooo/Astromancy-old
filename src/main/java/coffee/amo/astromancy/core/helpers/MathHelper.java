package coffee.amo.astromancy.core.helpers;

import com.mojang.math.Quaternion;

public class MathHelper {
    public static float remap(float value, float from1, float to1, float from2, float to2) {
        return (value - from1) / (to1 - from1) * (to2 - from2) + from2;
    }
}
