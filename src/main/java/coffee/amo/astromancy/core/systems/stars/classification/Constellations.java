package coffee.amo.astromancy.core.systems.stars.classification;

import coffee.amo.astromancy.Astromancy;
import net.minecraft.resources.ResourceLocation;

public enum Constellations {
    JUSTICE(0, "Justice", Astromancy.astromancy("textures/environment/constellations/justice.png")),//
    THE_HANGED_MAN(1, "The Hanged Man", Astromancy.astromancy("textures/environment/constellations/the_hanged_man.png")),//
    DEATH(2, "Death", Astromancy.astromancy("textures/environment/constellations/death.png")),//
    STRENGTH(-1, "Strength", Astromancy.astromancy("textures/environment/constellations/strength.png")),//
    THE_CHARIOT(-2, "The Chariot", Astromancy.astromancy("textures/environment/constellations/the_chariot.png")),//
    JUDGEMENT(-3, "Judgement", Astromancy.astromancy("textures/environment/constellations/judgement.png")), //
    THE_DEVIL(0, "The Devil", Astromancy.astromancy("textures/environment/constellations/the_devil.png")), //
    THE_TOWER(1, "The Tower", Astromancy.astromancy("textures/environment/constellations/the_tower.png")), //
    THE_STAR(2, "The Star", Astromancy.astromancy("textures/environment/constellations/the_star.png")), //
    THE_MOON(-1, "The Moon", Astromancy.astromancy("textures/environment/constellations/the_moon.png")), //
    THE_SUN(-2, "The Sun", Astromancy.astromancy("textures/environment/constellations/the_sun.png")), //
    THE_FOOL(0, "The Fool", Astromancy.astromancy("textures/environment/constellations/the_fool.png")), //
    THE_MAGICIAN(1, "The Magician", Astromancy.astromancy("textures/environment/constellations/the_magician.png")), //
    THE_HERMIT(-1, "The Hermit", Astromancy.astromancy("textures/environment/constellations/the_hermit.png")), //
    WHEEL_OF_FORTUNE(-2, "Wheel of Fortune", Astromancy.astromancy("textures/environment/constellations/wheel_of_fortune.png")), //
    TEMPERANCE(-3, "Temperance", Astromancy.astromancy("textures/environment/constellations/temperance.png")),//
    THE_WORLD(4, "The World", Astromancy.astromancy("textures/environment/constellations/the_world.png")), //
    THE_EMPRESS(0, "The Empress", Astromancy.astromancy("textures/environment/constellations/the_empress.png")), //
    THE_EMPEROR(1, "The Emperor", Astromancy.astromancy("textures/environment/constellations/the_emperor.png")), //
    THE_HIEROPHANT(2, "The Hierophant", Astromancy.astromancy("textures/environment/constellations/the_hierophant.png")), //
    THE_LOVERS(-1, "The Lovers", Astromancy.astromancy("textures/environment/constellations/the_lovers.png")), //
    THE_HIGH_PRIESTESS(-2, "The High Priestess", Astromancy.astromancy("textures/environment/constellations/the_high_priestess.png"));

    private final int height;
    private final String name;
    private final ResourceLocation icon;

    Constellations(int height, String name, ResourceLocation icon) {
        this.height = height;
        this.name = name;
        this.icon = icon;
    }

    public int getHeight() {
        return height;
    }

    public String getName() {
        return name;
    }

    public ResourceLocation getIcon() {
        return icon;
    }
}
