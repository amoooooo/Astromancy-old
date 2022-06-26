package coffee.amo.astromancy.core.events;

import coffee.amo.astromancy.Astromancy;
import coffee.amo.astromancy.client.research.ClientResearchHolder;
import coffee.amo.astromancy.common.blockentity.jar.JarBlockEntity;
import coffee.amo.astromancy.common.capability.PlayerResearchProvider;
import coffee.amo.astromancy.core.commands.AstromancyCommand;
import coffee.amo.astromancy.core.handlers.AstromancyPacketHandler;
import coffee.amo.astromancy.core.handlers.CapabilityAspectiHandler;
import coffee.amo.astromancy.core.handlers.PlayerResearchHandler;
import coffee.amo.astromancy.core.handlers.SolarEclipseHandler;
import coffee.amo.astromancy.core.packets.ResearchClearPacket;
import coffee.amo.astromancy.core.packets.ResearchPacket;
import coffee.amo.astromancy.core.packets.StarDataPacket;
import coffee.amo.astromancy.core.systems.aspecti.IAspectiHandler;
import coffee.amo.astromancy.core.systems.research.ResearchObject;
import coffee.amo.astromancy.core.systems.research.ResearchProgress;
import coffee.amo.astromancy.core.systems.research.ResearchTypeRegistry;
import coffee.amo.astromancy.core.util.StarSavedData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.SpyglassItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.AdvancementEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@Mod.EventBusSubscriber(modid = "astromancy")
public class AstromancyLevelEvents {
    public static boolean setForDay = false;
    public static float pity = 0;

    @SubscribeEvent
    public static void checkSolarEclipse(TickEvent.WorldTickEvent event) {
        if (event.world instanceof ServerLevel se) {
            long time = event.world.getDayTime() % 24000;
            boolean day = time == 22500;
            if (day && !setForDay) {
                setForDay = true;
                pity += 10;
            }
            float chance = event.world.random.nextInt(51) + pity;
            if (day && chance == 50) {
                System.out.println("Solar Eclipse!");
                SolarEclipseHandler.setEnabled(se, true);
                pity = 0;
            } else if (time >= 13500 && time < 22500) {
                SolarEclipseHandler.setEnabled(se, false);
            }
        }
    }

    @SubscribeEvent
    public static void sendStars(PlayerEvent.PlayerLoggedInEvent event) {
        if(event.getPlayer() instanceof LocalPlayer){
            ClientResearchHolder.research.clear();
        }
        if (event.getEntity() instanceof ServerPlayer se) {
            AstromancyPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> se), new StarDataPacket(StarSavedData.get(event.getEntity().getServer()).getConstellationInstances()));
            ClientResearchHolder.research.clear();
            AstromancyPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> se), new ResearchClearPacket());
            event.getPlayer().getCapability(PlayerResearchHandler.RESEARCH_CAPABILITY).ifPresent(research -> {
                CompoundTag tag = research.toNBT(new CompoundTag());
                ListTag researchTag = (ListTag) tag.get("research");
                if (!researchTag.isEmpty()) {
                    researchTag.forEach(r -> {
                        ResearchObject ro = ResearchObject.fromNBT((CompoundTag) r);
                        Astromancy.LOGGER.info("Sending " + se.getName() + " research: " + ro.getResearchName());
                        AstromancyPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> se), new ResearchPacket(ro.identifier, true, false, ro.locked.ordinal()));
                    });
                }
            });
        }
    }

    @SubscribeEvent
    public static void researchCommand(RegisterCommandsEvent event) {
        AstromancyCommand.registerSubCommands(event.getDispatcher());
    }

    @SubscribeEvent
    public static void libriObtain(AdvancementEvent event) {
        if (event.getAdvancement().getId().equals(Astromancy.astromancy("stella_libri"))) {
            if (event.getPlayer() instanceof ServerPlayer se) {
                se.getCapability(PlayerResearchHandler.RESEARCH_CAPABILITY, null).ifPresent(research -> {
                    ResearchTypeRegistry.RESEARCH_TYPES.get().getValues().forEach(s -> {
                        ResearchObject object = (ResearchObject) s;
                        if (object.identifier.equals("introduction")) {
                            research.completeResearch(se, object);
                            AstromancyPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> se), new ResearchPacket(object.identifier, false, true, ResearchProgress.COMPLETED.ordinal()));
                        }
                    });
                });
            }
        }
    }

    @SubscribeEvent
    public static void attachPlayerResearchCapability(AttachCapabilitiesEvent<Entity> event) {
        Entity entity = event.getObject();
        if (entity instanceof Player && !(entity instanceof FakePlayer)) {
            event.addCapability(Astromancy.astromancy("player_research"), new PlayerResearchProvider());
        }
    }

    @SubscribeEvent
    public static void playerTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        if (player.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof SpyglassItem) {
            if (player.getUseItem().getItem() instanceof SpyglassItem) {
                if (player.getXRot() < -30 && !event.player.level.isDay() && event.player.level.dimension().equals(Level.OVERWORLD)) {
//                    MinecraftForge.EVENT_BUS.post(new PlayerLookAtSkyEvent(event.phase, event.player));
                    if(!player.level.isClientSide){
                        player.getCapability(PlayerResearchHandler.RESEARCH_CAPABILITY, null).ifPresent(research -> {
                            if(research.contains(player, "stargazing")){return;}
                            ResearchTypeRegistry.RESEARCH_TYPES.get().getValues().forEach(s -> {
                                ResearchObject object = (ResearchObject) s;
                                if(object.identifier.equals("stargazing")){
                                    research.addResearch(player, object);
                                    AstromancyPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) player), new ResearchPacket(object.identifier, false, false, ResearchProgress.IN_PROGRESS.ordinal()));
                                }
                            });
                        });
                    }
                }
            }
        }
    }
}
