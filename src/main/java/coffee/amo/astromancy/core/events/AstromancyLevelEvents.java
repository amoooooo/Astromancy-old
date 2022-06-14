package coffee.amo.astromancy.core.events;

import coffee.amo.astromancy.core.handlers.AstromancyPacketHandler;
import coffee.amo.astromancy.core.handlers.SolarEclipseHandler;
import coffee.amo.astromancy.core.packets.StarDataPacket;
import coffee.amo.astromancy.core.util.StarSavedData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;

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

    @SubscribeEvent
    public static void sendStars(PlayerEvent.PlayerLoggedInEvent event) {
        if(event.getEntity() instanceof ServerPlayer se) {
            AstromancyPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> se), new StarDataPacket(StarSavedData.get(event.getEntity().getServer()).getConstellationInstances()));
        }
    }
}
