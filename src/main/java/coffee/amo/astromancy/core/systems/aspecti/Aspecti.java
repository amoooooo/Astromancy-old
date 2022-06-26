package coffee.amo.astromancy.core.systems.aspecti;

import coffee.amo.astromancy.core.util.AstroKeys;
import com.mojang.datafixers.util.Pair;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import java.awt.*;

public enum Aspecti {
    EMPTY("", new Color(0,0,0,0)),
    CONJUNCTION("a", new Color(120,120,120,255)), // Basic
    OPPOSITION("b", new Color(127,252,124,255)), // Complete
    SQUARE("c", Color.YELLOW), // Formed, but not complete
    TRINE("d", Color.CYAN), // Tool
    SEXTILE("e", Color.MAGENTA), // Versatile
    SEMISEXTILE("f", Color.BLUE), // Half, split
    QUINTILE("g", Color.GREEN), // Compress, condense
    QUINCUNX("h", Color.PINK), // Complex
    OCTILE("i", Color.RED), // Combine
    TRIOCTILE("j", Color.ORANGE), // Power
    DECILE("k", new Color(36,16,35,255)), // Value
    SOL("l", new Color(107,5,4,255)), // Lumen
    LUNA("m", new Color(163,50,11,255)), // Lumen alteration
    MERCURIA("n", new Color(213,230,141,255)), // Metal
    VENUTIO("o", new Color(71,160,37,255)), // Fire
    MARTUS("p", new Color(35,32,32,255)), // Combat
    JUPILUS("q", new Color(85,55,57,255)), // Air
    SATURIS("r", new Color(149,94,66,255)), // Rock, natural
    URANIA("s", new Color(156,145,97,255)), // Mechanical, magical
    NEPTURA("t", new Color(116,142,84,255)), // Water, fluid
    PLUTUS("u", new Color(22,48,43,255)), // Power source
    CHIROS("v", new Color(105,72,115,255)), // Healing, pain
    LILITHIA("w", new Color(0x85B79D)); // Life, death

    private final String symbol;
    private final Color color;

    Aspecti(String symbol, Color color) {
        this.symbol = symbol;
        this.color = color;
    }

    public String symbol() {
        return symbol;
    }

    public Color color() {
        return color;
    }

    public static Aspecti get(int type) {
        return values()[type];
    }

    public static Aspecti fromItemStack(ItemStack stack) {
        if(stack.getOrCreateTag().contains(AstroKeys.KEY_ASPECTI_TAG))
            return get(stack.getOrCreateTag()
                    .getCompound(AstroKeys.KEY_ASPECTI_TAG)
                    .getCompound(AstroKeys.KEY_ASPECTI_STACK)
                    .getInt(AstroKeys.KEY_ASPECTI_TYPE));
        return EMPTY;
    }
}
