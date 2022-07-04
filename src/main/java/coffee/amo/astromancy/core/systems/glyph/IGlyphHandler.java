package coffee.amo.astromancy.core.systems.glyph;

import coffee.amo.astromancy.common.item.GlyphPhial;

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
 * Item: {@link GlyphPhial GlyphPhial}.
 *
 * @author The_seVen_
 */
public interface IGlyphHandler {

    /**
     * Returns the number of GlyphStack storage units ("tanks") available.
     *
     * @return The number of tanks available
     */
    int getTanks();

    /**
     * Returns the GlyphStack in a given tank for comparison purposes.
     * IMPORTANT: DO NOT MODIFY THE RETURNED GlyphSTACK!
     *
     * @param tank Tank to query.
     * @return GlyphStack in a given tank. GlyphStack.EMPTY if the tank is empty.
     */
    @Nonnull
    GlyphStack getGlyphInTank(int tank);

    /**
     * Retrieves the maximum Glyph amount for a given tank.
     *
     * @param tank Tank to query.
     * @return The maximum Glyph amount held by the tank.
     */
    int getTankCapacity(int tank);

    /**
     * This function is a way to determine which Glyph can exist inside a given handler.
     * General purpose tanks will basically always return TRUE for this.
     * Use when the tank is supposed to hold only specific Glyph, like labeled jars.
     *
     * @param tank  Tank to query for validity.
     * @param stack GlyphStack to test with for validity.
     * @return TRUE if the tank can hold the GlyphStack, not considering current state.
     */
    boolean isGlyphValid(int tank, @Nonnull GlyphStack stack);

    /**
     * Fills GlyphStack into internal tanks, distribution is left entirely to the implementation.
     *
     * @param resource GlyphStack representing the Glyph and maximum amount to be filled.
     * @param simulate If SIMULATE, fill will only be simulated.
     * @return Amount of resource that was (or would have been, if simulated) filled.
     */
    int fill(GlyphStack resource, boolean simulate);

    /**
     * Drains GlyphStack out of internal tanks, distribution is left entirely to the implementation.
     *
     * @param resource GlyphStack representing the Glyph and maximum amount of slurry to be drained.
     * @param simulate If SIMULATE, drain will only be simulated.
     * @return GlyphStack representing the Glyph and amount that was (or would have been, if simulated) drained.
     */
    @Nonnull
    GlyphStack drain(GlyphStack resource, boolean simulate);

    /**
     * Drains GlyphStack out of internal tanks, distribution is left entirely to the implementation.
     * <p>
     * This method is not Glyph-sensitive.
     *
     * @param maxDrain Maximum amount of Glyph to drain.
     * @param simulate If SIMULATE, drain will only be simulated.
     * @return GlyphStack representing the Glyph and amount that was (or would have been, if simulated) drained.
     */
    @Nonnull
    GlyphStack drain(int maxDrain, boolean simulate);
}
