package coffee.amo.astromancy.core.events;

import net.minecraftforge.eventbus.api.Event;

public class SetupAstromancyBookTabsEvent extends Event {
    public SetupAstromancyBookTabsEvent(){

    }

    @Override
    public boolean isCancelable() {
        return false;
    }
}
