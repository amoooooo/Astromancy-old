package coffee.amo.astromancy.core.systems.lumen;

import coffee.amo.astromancy.common.item.GlyphPhial;
import coffee.amo.astromancy.core.systems.glyph.GlyphStackHandler;

import javax.annotation.Nonnull;

/**
 * Implement this interface as a capability which should handle Glyph inventories.
 * <p>
 * Example implementations:
 * <p>
 * Handler ("single-stack"): {@link GlyphStackHandler GlyphStackHandler} (with default Provider for Items)
 * <p>
 * BlockEntity: {@link coffee.amo.astromancy.common.blockentity.jar.JarBlockEntity JarBlockEntity}.
 * <p>
 * Item: {@link }.
 *
 * @author amo
 */
public interface ILumenHandler {

    int getTanks();

    @Nonnull
    LumenStack getLumenInTank(int tank);

    int getTankCapacity(int tank);

    boolean isLumenValid(int tank, @Nonnull LumenStack stack);

    int fill(LumenStack resource, boolean simulate);

    @Nonnull
    LumenStack drain(LumenStack resource, boolean simulate);

    @Nonnull
    LumenStack drain(int maxDrain, boolean simulate);
}
