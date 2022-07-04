package coffee.amo.astromancy.core.systems.glyph;

import coffee.amo.astromancy.core.util.AstromancyKeys;
import net.minecraft.world.item.ItemStack;

import java.awt.*;

public enum Glyph {
    EMPTY("", new Color(0,0,0,0)),
    CONJUNCTION("a", new Color(120,120,120,255)), // Basic
    OPPOSITION("b", new Color(127,252,124,255)), // Complete
    SQUARE("c", Color.YELLOW), // Formed, but not complete
    SEXTILE("d", Color.MAGENTA), // Versatile
    TRINE("e", Color.CYAN), // Tool
    SEMISEXTILE("f", Color.BLUE), // Half, split
    QUINCUNX("g", Color.PINK), // Complex
    QUINTILE("h", Color.GREEN), // Compress, condense
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

    Glyph(String symbol, Color color) {
        this.symbol = symbol;
        this.color = color;
    }

    public String symbol() {
        return symbol;
    }

    public Color color() {
        return color;
    }

    public static Glyph get(int type) {
        return values()[type];
    }

    public static Glyph fromItemStack(ItemStack stack) {
        if(stack.getOrCreateTag().contains(AstromancyKeys.KEY_GLYPH_TAG))
            return get(stack.getOrCreateTag()
                    .getCompound(AstromancyKeys.KEY_GLYPH_TAG)
                    .getCompound(AstromancyKeys.KEY_GLYPH_STACK)
                    .getInt(AstromancyKeys.KEY_GLYPH_TYPE));
        return EMPTY;
    }
}
