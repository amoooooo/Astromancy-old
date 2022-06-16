package coffee.amo.astromancy.core.systems.aspecti;

import com.mojang.datafixers.util.Pair;
import net.minecraft.nbt.CompoundTag;

import java.awt.*;

public enum Aspecti {
    CONJUNCTION("!", new Color(120,120,120,255)), // Basic
    OPPOSITION("\"", new Color(127,252,124,255)), // Complete
    SQUARE("#", Color.YELLOW), // Formed, but not complete
    TRINE("$", Color.CYAN), // Tool
    SEXTILE("%", Color.MAGENTA), // Versatile
    SEMISEXTILE("&", Color.BLUE), // Half, split
    QUINTILE("(", Color.GREEN), // Compress, condense
    QUINCUNX("'", Color.PINK), // Complex
    OCTILE(")", Color.RED), // Combine
    TRIOCTILE("*", Color.ORANGE), // Power
    DECILE("+", new Color(36,16,35,255)), // Value
    SOL(":", new Color(107,5,4,255)), // Lumen
    LUNA(".", new Color(163,50,11,255)), // Lumen alteration
    MERCURIA("/", new Color(213,230,141,255)), // Metal
    VENUTIO("0", new Color(71,160,37,255)), // Fire
    MARTUS("1", new Color(35,32,32,255)), // Combat
    JUPILUS("2", new Color(85,55,57,255)), // Air
    SATURIS("3", new Color(149,94,66,255)), // Rock, natural
    URANIA("4", new Color(156,145,97,255)), // Mechanical, magical
    NEPTURA("5", new Color(116,142,84,255)), // Water, fluid
    PLUTUS("6", new Color(22,48,43,255)), // Power source
    CHIROS("7", new Color(105,72,115,255)), // Healing, pain
    LILITHIA("8", new Color(0x85B79D)), // Life, death
    EMPTY("", new Color(0,0,0,0));
    ;
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

    public static Pair<Aspecti, Integer> fromNbt(CompoundTag ptag){
        CompoundTag tag = ptag.getCompound("aspecti");
        return Pair.of(values()[tag.getInt("aspecti")], tag.getInt("count"));
    }
}
