package coffee.amo.astromancy.core.systems.damage;

import net.minecraft.world.damagesource.DamageSource;

public class AstromancyDamageSources {
    public static final DamageSource ASTRAL = new AstromancyDamageSource("astral").bypassArmor().bypassEnchantments().bypassMagic();
    public static final DamageSource PURE = new AstromancyDamageSource("pure").bypassHell().bypassCrimson().bypassNull().bypassArmor().bypassEnchantments().bypassMagic();
    public static final DamageSource HELL = new AstromancyDamageSource("hell").bypassPure().bypassDenatured().bypassNull().bypassArmor().bypassEnchantments().bypassMagic();
    public static final DamageSource NULL = new AstromancyDamageSource("null").bypassPure().bypassHell().bypassCrimson().bypassDenatured().bypassArmor().bypassEnchantments().bypassMagic();
    public static final DamageSource DENATURED = new AstromancyDamageSource("denatured").bypassHell().bypassNull().bypassArmor().bypassEnchantments().bypassMagic();
    public static final DamageSource EXOTIC = new AstromancyDamageSource("crimson").bypassHell().bypassNull().bypassDenatured().bypassArmor().bypassEnchantments().bypassMagic();
}
