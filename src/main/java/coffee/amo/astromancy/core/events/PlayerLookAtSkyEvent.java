package coffee.amo.astromancy.core.events;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.Event;

public class PlayerLookAtSkyEvent extends TickEvent.PlayerTickEvent {
    public PlayerLookAtSkyEvent(Phase phase, Player player){
        super(phase, player);
    }

    @Override
    public boolean isCancelable() {
        return false;
    }
}
