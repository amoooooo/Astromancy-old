package coffee.amo.astromancy.core.systems.stars.classification;

public enum Constellations {
    JUSTICE(0, "Justice"),
    THE_HANGED_MAN(1, "The Hanged Man"),
    DEATH(2, "Death"),
    STRENGTH(-1, "Strength"),
    THE_CHARIOT(-2, "The Chariot"),
    JUDGEMENT(-3, "Judgement"),
    THE_DEVIL(0, "The Devil"),
    THE_TOWER(1, "The Tower"),
    THE_STAR(2, "The Star"),
    THE_MOON(-1, "The Moon"),
    THE_SUN(-2, "The Sun"),
    THE_FOOL(0, "The Fool"),
    THE_MAGICIAN(1, "The Magician"),
    THE_HERMIT(-1, "The Hermit"),
    WHEEL_OF_FORTUNE(-2, "Wheel of Fortune"),
    TEMPERANCE(-3, "Temperance"),
    THE_WORLD(4, "The World"),
    THE_EMPRESS(0, "The Empress"),
    THE_EMPEROR(1, "The Emperor"),
    THE_HIEROPHANT(2, "The Hierophant"),
    THE_LOVERS(-1, "The Lovers"),
    THE_HIGH_PRIESTESS(-2, "The High Priestess");

    private final int height;
    private final String name;

    Constellations(int height, String name) {
        this.height = height;
        this.name = name;
    }

    public int getHeight() {
        return height;
    }

    public String getName() {
        return name;
    }
}
