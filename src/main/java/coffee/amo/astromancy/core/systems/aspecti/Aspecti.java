package coffee.amo.astromancy.core.systems.aspecti;

public enum Aspecti {
    CONJUNCTION("!"), // Basic
    OPPOSITION("\""), // Complete
    SQUARE("#"), // Formed, but not complete
    TRINE("$"), // Tool
    SEXTILE("%"), // Versatile
    SEMISEXTILE("&"), // Half, split
    QUINTILE("'"), // Compress, condense
    QUINCUNX("("), // Life, living
    OCTILE(")"), // Combine
    TRIOCTILE("*"), // Power
    DECILE("+") // Weight
    ;
    private final String symbol;

    Aspecti(String symbol) {
        this.symbol = symbol;
    }

    public String symbol() {
        return symbol;
    }
}
