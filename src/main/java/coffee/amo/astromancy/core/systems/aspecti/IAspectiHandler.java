package coffee.amo.astromancy.core.systems.aspecti;

import javax.annotation.Nonnull;

/**
 * Implement this interface as a capability which should handle aspecti inventories.
 * <p>
 * Example implementations:
 * <p>
 * Handler ("single-stack"): {@link AspectiStackHandler AspectiStackHandler} (with default Provider for Items)
 * <p>
 * BlockEntity: {@link coffee.amo.astromancy.common.blockentity.jar.JarBlockEntity JarBlockEntity}.
 * <p>
 * Item: {@link coffee.amo.astromancy.common.item.AspectiPhial AspectiPhial}.
 *
 * @author The_seVen_
 */
public interface IAspectiHandler {

    /**
     * Returns the number of AspectiStack storage units ("tanks") available.
     *
     * @return The number of tanks available
     */
    int getTanks();

    /**
     * Returns the AspectiStack in a given tank for comparison purposes.
     * IMPORTANT: DO NOT MODIFY THE RETURNED ASPECTISTACK!
     *
     * @param tank Tank to query.
     * @return AspectiStack in a given tank. AspectiStack.EMPTY if the tank is empty.
     */
    @Nonnull
    AspectiStack getAspectiInTank(int tank);

    /**
     * Retrieves the maximum Aspecti amount for a given tank.
     *
     * @param tank Tank to query.
     * @return The maximum Aspecti amount held by the tank.
     */
    int getTankCapacity(int tank);

    /**
     * This function is a way to determine which aspecti can exist inside a given handler.
     * General purpose tanks will basically always return TRUE for this.
     * Use when the tank is supposed to hold only specific aspecti, like labeled jars.
     *
     * @param tank  Tank to query for validity.
     * @param stack AspectiStack to test with for validity.
     * @return TRUE if the tank can hold the AspectiStack, not considering current state.
     */
    boolean isAspectiValid(int tank, @Nonnull AspectiStack stack);

    /**
     * Fills AspectiStack into internal tanks, distribution is left entirely to the implementation.
     *
     * @param resource AspectiStack representing the Aspecti and maximum amount to be filled.
     * @param simulate If SIMULATE, fill will only be simulated.
     * @return Amount of resource that was (or would have been, if simulated) filled.
     */
    int fill(AspectiStack resource, boolean simulate);

    /**
     * Drains AspectiStack out of internal tanks, distribution is left entirely to the implementation.
     *
     * @param resource AspectiStack representing the Aspecti and maximum amount of slurry to be drained.
     * @param simulate If SIMULATE, drain will only be simulated.
     * @return AspectiStack representing the Aspecti and amount that was (or would have been, if simulated) drained.
     */
    @Nonnull
    AspectiStack drain(AspectiStack resource, boolean simulate);

    /**
     * Drains AspectiStack out of internal tanks, distribution is left entirely to the implementation.
     * <p>
     * This method is not Aspecti-sensitive.
     *
     * @param maxDrain Maximum amount of Aspecti to drain.
     * @param simulate If SIMULATE, drain will only be simulated.
     * @return AspectiStack representing the Aspecti and amount that was (or would have been, if simulated) drained.
     */
    @Nonnull
    AspectiStack drain(int maxDrain, boolean simulate);
}
