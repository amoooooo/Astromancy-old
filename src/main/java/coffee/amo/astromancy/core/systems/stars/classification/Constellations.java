package coffee.amo.astromancy.core.systems.stars.classification;

import coffee.amo.astromancy.Astromancy;
import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceLocation;

public enum Constellations {
    JUSTICE(0, "Justice", Astromancy.astromancy("textures/environment/constellations/justice.png"), new Pair[]{Pair.of(5f, 1f), Pair.of(-10f, 6f), Pair.of(3f,-8f), Pair.of(5f,2f)}),//
    THE_HANGED_MAN(1, "The Hanged Man", Astromancy.astromancy("textures/environment/constellations/the_hanged_man.png"), new Pair[]{Pair.of(6f, -3f), Pair.of(10f, -7f), Pair.of(8f,-8f), Pair.of(5f,2f), Pair.of(1f,2f)}),//
    DEATH(2, "Death", Astromancy.astromancy("textures/environment/constellations/death.png"), new Pair[]{Pair.of(1f, 5f), Pair.of(12f, -10f), Pair.of(4f,-6f), Pair.of(10f, -7f), Pair.of(8f,-8f)}),//
    STRENGTH(-1, "Strength", Astromancy.astromancy("textures/environment/constellations/strength.png"), new Pair[]{Pair.of(-5f, -1f), Pair.of(-12f, -7f), Pair.of(-4f,-6f)}),//
    THE_CHARIOT(-2, "The Chariot", Astromancy.astromancy("textures/environment/constellations/the_chariot.png"), new Pair[]{Pair.of(-6f, 3f), Pair.of(-10f, 7f), Pair.of(-8f,-8f)}),//
    JUDGEMENT(-3, "Judgement", Astromancy.astromancy("textures/environment/constellations/judgement.png"), new Pair[]{Pair.of(-1f, -5f), Pair.of(-12f, 10f), Pair.of(-4f,-6f), Pair.of(-6f, 3f), Pair.of(-10f, 7f)}), //
    THE_DEVIL(0, "The Devil", Astromancy.astromancy("textures/environment/constellations/the_devil.png"), new Pair[]{Pair.of(5f, 1f), Pair.of(-10f, 6f), Pair.of(3f,-8f)}), //
    THE_TOWER(1, "The Tower", Astromancy.astromancy("textures/environment/constellations/the_tower.png"), new Pair[]{Pair.of(6f, -3f), Pair.of(10f, -7f), Pair.of(8f,-8f)}), //
    THE_STAR(2, "The Star", Astromancy.astromancy("textures/environment/constellations/the_star.png"), new Pair[]{Pair.of(1f, 5f), Pair.of(12f, -10f), Pair.of(4f,-6f)}), //
    THE_MOON(-1, "The Moon", Astromancy.astromancy("textures/environment/constellations/the_moon.png"), new Pair[]{Pair.of(-5f, -1f), Pair.of(-12f, -7f), Pair.of(-4f,-6f)}), //
    THE_SUN(-2, "The Sun", Astromancy.astromancy("textures/environment/constellations/the_sun.png"), new Pair[]{Pair.of(-6f, 3f), Pair.of(-10f, 7f), Pair.of(-8f,-8f)}), //
    THE_FOOL(0, "The Fool", Astromancy.astromancy("textures/environment/constellations/the_fool.png"), new Pair[]{Pair.of(-1f, -5f), Pair.of(-12f, 10f), Pair.of(-4f,-6f)}), //
    THE_MAGICIAN(1, "The Magician", Astromancy.astromancy("textures/environment/constellations/the_magician.png"), new Pair[]{Pair.of(6f, -3f), Pair.of(10f, -7f), Pair.of(8f,-8f)}), //
    THE_HERMIT(-1, "The Hermit", Astromancy.astromancy("textures/environment/constellations/the_hermit.png"), new Pair[]{Pair.of(-1f, -5f), Pair.of(-12f, 10f), Pair.of(-4f,-6f)}), //
    WHEEL_OF_FORTUNE(-2, "Wheel of Fortune", Astromancy.astromancy("textures/environment/constellations/wheel_of_fortune.png"), new Pair[]{Pair.of(5f, 1f), Pair.of(-10f, 6f), Pair.of(3f,-8f)}), //
    TEMPERANCE(-3, "Temperance", Astromancy.astromancy("textures/environment/constellations/temperance.png"), new Pair[]{Pair.of(-5f, -1f), Pair.of(-12f, -7f), Pair.of(-4f,-6f)}),//
    THE_WORLD(4, "The World", Astromancy.astromancy("textures/environment/constellations/the_world.png"), new Pair[]{Pair.of(-1f, -5f), Pair.of(-12f, 10f), Pair.of(-4f,-6f)}), //
    THE_EMPRESS(0, "The Empress", Astromancy.astromancy("textures/environment/constellations/the_empress.png"), new Pair[]{Pair.of(-1f, -5f), Pair.of(-12f, 10f), Pair.of(-4f,-6f)}), //
    THE_EMPEROR(1, "The Emperor", Astromancy.astromancy("textures/environment/constellations/the_emperor.png"), new Pair[]{Pair.of(1f, 5f), Pair.of(12f, -10f), Pair.of(4f,-6f)}), //
    THE_HIEROPHANT(2, "The Hierophant", Astromancy.astromancy("textures/environment/constellations/the_hierophant.png"), new Pair[]{Pair.of(1f, 5f), Pair.of(12f, -10f), Pair.of(4f,-6f)}), //
    THE_LOVERS(-1, "The Lovers", Astromancy.astromancy("textures/environment/constellations/the_lovers.png"), new Pair[]{Pair.of(6f, -3f), Pair.of(10f, -7f), Pair.of(8f,-8f)}), //
    THE_HIGH_PRIESTESS(-2, "The High Priestess", Astromancy.astromancy("textures/environment/constellations/the_high_priestess.png"), new Pair[]{Pair.of(5f, 1f), Pair.of(-10f, 6f), Pair.of(3f,-8f)});

    private final int height;
    private final String name;
    private final ResourceLocation icon;
    private final Pair<Float, Float>[] points;

    Constellations(int height, String name, ResourceLocation icon, Pair<Float, Float>[] points) {
        this.height = height;
        this.name = name;
        this.icon = icon;
        this.points = points;
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

    public Pair<Float, Float>[] getPoints() {
        return points;
    }
}
