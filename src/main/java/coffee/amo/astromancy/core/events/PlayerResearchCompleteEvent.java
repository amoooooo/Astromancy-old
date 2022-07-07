package coffee.amo.astromancy.core.events;

import coffee.amo.astromancy.core.systems.research.ResearchObject;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.Event;

public class PlayerResearchCompleteEvent extends Event {
    public final Player player;
    public final ResearchObject research;
    public PlayerResearchCompleteEvent(Player player, ResearchObject researchId) {
        this.player = player;
        this.research = researchId;
    }

    @Override
    public boolean isCancelable() {
        return false;
    }
}
