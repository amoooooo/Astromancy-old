package coffee.amo.astromancy.core.events;

import net.minecraftforge.eventbus.api.Event;

public class SetupAstromancyBookEntriesEvent extends Event {
    public SetupAstromancyBookEntriesEvent(){

    }

    @Override
    public boolean isCancelable() {
        return false;
    }
}
