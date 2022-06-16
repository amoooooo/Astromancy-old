package coffee.amo.astromancy.core.systems.stars.classification;


import java.awt.*;

public enum StarColors {
    O(new Color(40, 145, 250, 255)),
    B(new Color(144,178,245,255)),
    A(new Color(174, 184, 245, 255)),
    F(new Color(245,221,159, 255)),
    G(new Color(245, 210, 122, 255)),
    K(new Color(245, 175, 122, 255)),
    M(new Color(242, 142, 102, 255)),
    CRIMSON(new Color(242, 53, 85, 255)),
    PURE(new Color(197, 242, 172, 255)),
    DARK(new Color(41, 25, 79, 255)),
    EMPTY(new Color(1,1,115, 255)),
    HELL(new Color(227, 38, 0, 255)),
    NORMAL(new Color(255,255,255, 255));
    ;

    private Color color;
    StarColors(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
