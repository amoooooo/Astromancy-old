package coffee.amo.astromancy.core.events;

import coffee.amo.astromancy.core.handlers.SolarEclipseHandler;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "astromancy")
public class AstromancyLevelEvents {
    public static boolean setForDay = false;
    public static float pity = 0;
    @SubscribeEvent
    public static void checkSolarEclipse(TickEvent.WorldTickEvent event) {
        if (event.world instanceof ServerLevel se) {
            long time = event.world.getDayTime() % 24000;
            boolean day = time == 22500;
            if(day && !setForDay){
                setForDay = true;
                pity += 10;
            }
            float chance = event.world.random.nextInt(51) + pity;
            if (day && chance == 50) {
                System.out.println("Solar Eclipse!");
                SolarEclipseHandler.setEnabled(se, true);
                pity = 0;
            } else if (time >= 13500 && time < 22500){
                SolarEclipseHandler.setEnabled(se, false);
            }
        }
    }
}
