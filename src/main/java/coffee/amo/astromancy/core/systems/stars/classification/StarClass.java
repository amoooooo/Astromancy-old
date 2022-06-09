package coffee.amo.astromancy.core.systems.stars.classification;

public enum StarClass {
    HYPERGIANT("Hypergiant"),
    SUPERGIANT("Supergiant"),
    BRIGHT_GIANT("Bright Giant"),
    GIANT("Giant"),
    SUBGIANT("Subgiant"),
    MAIN_SEQUENCE("Main Sequence"),
    DWARF("Dwarf"),
    SUBDWARF("Subdwarf"),
    WHITE_DWARF("White Dwarf"),
    CRIMSON("Crimson"),
    PURE("Pure"),
    DARK("Dark"),
    EMPTY("Empty"),
    HELL("Hell");
    private String type;
    StarClass(String type) {
        this.type = type;
    }
    public String getType() {
        return type;
    }
}
