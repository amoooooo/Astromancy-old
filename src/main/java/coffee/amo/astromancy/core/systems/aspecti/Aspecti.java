package coffee.amo.astromancy.core.systems.aspecti;

public enum Aspecti {
    CONJUNCTION("!"), // Basic
    OPPOSITION("\""), // Complete
    SQUARE("#"), // Formed, but not complete
    TRINE("$"), // Tool
    SEXTILE("%"), // Versatile
    SEMISEXTILE("&"), // Half, split
    QUINTILE("("), // Compress, condense
    QUINCUNX("'"), // Life, living
    OCTILE(")"), // Combine
    TRIOCTILE("*"), // Power
    DECILE("+"), // Weight
    SOL("-"), // Lumen
    LUNA("."), // Lumen alteration
    MERCURIA("/"), // Metal
    VENUTIO("0"), // Fire
    MARTUS("1"), // Combat
    JUPILUS("2"), // Air
    SATURIS("3"), // Rock, natural
    URANIA("4"), // Mechanical, magical
    NEPTURA("5"), // Water, fluid
    PLUTUS("6"), // Power source
    CHIROS("7"), // Healing, pain
    LILITHIA("8") // Life, death
    ;
    private final String symbol;

    Aspecti(String symbol) {
        this.symbol = symbol;
    }

    public String symbol() {
        return symbol;
    }
}
