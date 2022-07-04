package coffee.amo.astromancy.core.systems.damage;

import net.minecraft.world.damagesource.DamageSource;

public class AstromancyDamageSource extends DamageSource {
    private boolean bypassAstral;
    private boolean bypassPure;
    private boolean bypassHell;
    private boolean bypassNull;
    private boolean bypassDenatured;
    private boolean bypassCrimson;
    public AstromancyDamageSource(String p_19333_) {
        super(p_19333_);
    }

    public AstromancyDamageSource bypassAstral() {
        this.bypassAstral = true;
        return this;
    }

    public AstromancyDamageSource bypassPure() {
        this.bypassPure = true;
        return this;
    }

    public AstromancyDamageSource bypassHell() {
        this.bypassHell = true;
        return this;
    }

    public AstromancyDamageSource bypassNull() {
        this.bypassNull = true;
        return this;
    }

    public AstromancyDamageSource bypassDenatured() {
        this.bypassDenatured = true;
        return this;
    }

    public AstromancyDamageSource bypassCrimson() {
        this.bypassCrimson = true;
        return this;
    }

    public boolean isBypassesAstral() {
        return this.bypassAstral;
    }

    public boolean isBypassesPure() {
        return this.bypassPure;
    }

    public boolean isBypassesHell() {
        return this.bypassHell;
    }

    public boolean isBypassesNull() {
        return this.bypassNull;
    }

    public boolean isBypassesDenatured() {
        return this.bypassDenatured;
    }

    public boolean isBypassesCrimson() {
        return this.bypassCrimson;
    }

}
