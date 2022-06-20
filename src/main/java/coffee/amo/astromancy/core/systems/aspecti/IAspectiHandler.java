package coffee.amo.astromancy.core.systems.aspecti;

import coffee.amo.astromancy.core.systems.aspecti.Aspecti;
import coffee.amo.astromancy.core.systems.aspecti.AspectiStack;

import javax.annotation.Nonnull;

public interface IAspectiHandler {
    enum AspectiAction{
        EXECUTE, SIMULATE;

        public boolean execute(){
            return this == EXECUTE;
        }

        public boolean simulate(){
            return this == SIMULATE;
        }
    }

    int getTanks();

    @Nonnull
    AspectiStack getAspectiInTank(int tank);

    int getTankCapacity(int tank);

    boolean isAspectiValid(int tank, @Nonnull Aspecti aspecti);

    int fill(AspectiStack resource, AspectiAction action);

    @Nonnull
    AspectiStack drain(AspectiStack resource, AspectiAction action);

    @Nonnull
    AspectiStack drain(int maxDrain, AspectiAction action);
}
