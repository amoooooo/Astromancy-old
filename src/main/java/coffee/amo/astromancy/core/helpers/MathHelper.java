package coffee.amo.astromancy.core.helpers;

public class MathHelper {
    public static float remap(float value, float from1, float to1, float from2, float to2) {
        return (value - from1) / (to1 - from1) * (to2 - from2) + from2;
    }

    public static float normalize(float val, float min, float max){
        return 1-((val - min) / (max - min));
    }

    public static float ease(float x, Easing eType){
        return eType.ease(x);
    }

    public enum Easing implements IEasing {
        easeInOutBack {
            public float ease(float x) {
                float c1 = 1.70158F;
                float c2 = c1 * 1.525F;
                return (float) (x < 0.5 ? (Math.pow(2 * x, 2) * ((c2 + 1) * 2 * x - c2)) / 2 : (Math.pow(2 * x - 2, 2) * ((c2 + 1) * (x * 2 - 2) + c2) + 2) / 2);
            }
        },
        easeOutCubic{
            public float ease(float x) {
                return (float) ((float) 1 - Math.pow(1 - x, 3));
            }
        }
    }

    public interface IEasing {
        float ease(float x);
    }
}
