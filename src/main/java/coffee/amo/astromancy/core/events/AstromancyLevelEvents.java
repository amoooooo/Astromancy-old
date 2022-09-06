package coffee.amo.astromancy.core.events;

import coffee.amo.astromancy.Astromancy;
import coffee.amo.astromancy.client.research.ClientResearchHolder;
import coffee.amo.astromancy.common.capability.PlayerResearchProvider;
import coffee.amo.astromancy.core.commands.AstromancyCommand;
import coffee.amo.astromancy.core.handlers.AstromancyPacketHandler;
import coffee.amo.astromancy.core.handlers.PlayerResearchHandler;
import coffee.amo.astromancy.core.packets.ResearchClearPacket;
import coffee.amo.astromancy.core.packets.ClientboundResearchPacket;
import coffee.amo.astromancy.core.packets.StarDataPacket;
import coffee.amo.astromancy.core.packets.UniverseNBTPacket;
import coffee.amo.astromancy.core.registration.AttributeRegistry;
import coffee.amo.astromancy.core.registration.ResearchRegistry;
import coffee.amo.astromancy.core.systems.damage.AstromancyDamageSource;
import coffee.amo.astromancy.core.systems.research.ResearchObject;
import coffee.amo.astromancy.core.systems.research.ResearchProgress;
import coffee.amo.astromancy.core.systems.research.ResearchTypeRegistry;
import coffee.amo.astromancy.core.util.StarSavedData;
import net.minecraft.advancements.Advancement;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.SpyglassItem;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.player.AdvancementEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;

@Mod.EventBusSubscriber(modid = "astromancy")
public class AstromancyLevelEvents {
    public static long cometTick;
    public static boolean cometChanged;

    @SubscribeEvent
    public static void checkSolarEclipse(TickEvent.LevelTickEvent event) {
        if(event.level.getServer() == null) return;
        if (event.level instanceof ServerLevel se) {
            MinecraftServer s = se.getServer();
            int day = ((int) se.getGameTime() / 24000);
            StarSavedData data = StarSavedData.get(s);
            if(data.lastDay != day && data.isEclipseEnabled()){
                data.setEclipseEnabled(false);
                data.genDaysTilEclipse(s.overworld());
            }
            if(data.day != day){
                data.day = day;
                data.dirtyEclipse = false;
            }
            if(data.lastDay + data.getDaysTilEclipse() != day && !data.dirtyEclipse){
                data.setDaysTilEclipse(data.getDaysTilEclipse() - 1);
                data.dirtyEclipse = true;
                event.level.getServer().sendSystemMessage(Component.literal("Day " + day + ", " + data.getDaysTilEclipse() + " days til eclipse."));
            }
            if(!data.isEclipseEnabled() && data.getDaysTilEclipse() <= 0) {
                data.setEclipseEnabled(true);
                data.lastDay = day;
            }
            if(data.getDaysTilEclipse() <= -1) {
                data.setDaysTilEclipse(0);
            }
        }
    }

    @SubscribeEvent
    public static void sendStars(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof LocalPlayer) {
            ClientResearchHolder.research.clear();
        }
        if (event.getEntity() instanceof ServerPlayer se) {
            AstromancyPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> se), new StarDataPacket(StarSavedData.get(event.getEntity().getServer()).getConstellationInstances()));
            for(int i = 0; i < StarSavedData.get(event.getEntity().getServer()).getUniverse().getSuperclusters().size(); i++){
                AstromancyPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> se), new UniverseNBTPacket(StarSavedData.get(event.getEntity().getServer()).getUniverse().getSuperclusters().get(i).toNbt(), i));
            }
            ClientResearchHolder.research.clear();
            AstromancyPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> se), new ResearchClearPacket());
            event.getEntity().getCapability(PlayerResearchHandler.RESEARCH_CAPABILITY).ifPresent(research -> {
                CompoundTag tag = research.toNBT(new CompoundTag());
                ListTag researchTag = (ListTag) tag.get("research");
                if (!researchTag.isEmpty()) {
                    researchTag.forEach(r -> {
                        ResearchObject ro = ResearchObject.fromNBT((CompoundTag) r);
                        Astromancy.LOGGER.info("Sending " + se.getName() + " research: " + ro.getResearchName());
                        AstromancyPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> se), new ClientboundResearchPacket(ro.identifier, true, false, ro.locked.ordinal()));
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
            if (event.getEntity() instanceof ServerPlayer se) {
                se.getCapability(PlayerResearchHandler.RESEARCH_CAPABILITY, null).ifPresent(research -> {
                    ResearchTypeRegistry.RESEARCH_TYPES.get().getValues().forEach(s -> {
                        ResearchObject object = (ResearchObject) s;
                        if (object.identifier.equals("introduction") || object.identifier.equals("glyph")) {
                            research.completeResearch(se, object);
                            AstromancyPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> se), new ClientboundResearchPacket(object.identifier, false, true, ResearchProgress.COMPLETED.ordinal()));
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
                    if (!player.level.isClientSide) {
                        player.getCapability(PlayerResearchHandler.RESEARCH_CAPABILITY, null).ifPresent(research -> {
                            if (research.contains(player, "stargazing")) {
                                return;
                            }
                            ResearchTypeRegistry.RESEARCH_TYPES.get().getValues().forEach(s -> {
                                ResearchObject object = (ResearchObject) s;
                                if (object.identifier.equals("stargazing")) {
                                    research.addResearch(player, object);
                                    AstromancyPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) player), new ClientboundResearchPacket(object.identifier, false, false, ResearchProgress.IN_PROGRESS.ordinal()));
                                }
                            });
                        });
                    }
                }
            }
        }
        if(!player.level.isClientSide){
            if(player.getPersistentData().contains("fleeting_test_start")){
                if(player.getPersistentData().getLong("fleeting_test_start") + 120 == event.player.level.getGameTime()){
                    player.getCapability(PlayerResearchHandler.RESEARCH_CAPABILITY).ifPresent(research -> {
                        if(research.contains(player, "fleeting_test")){
                            research.removeResearch(player, ResearchRegistry.FLEETING_TEST.get());
                            player.getPersistentData().remove("fleeting_test_start");
                        }
                    });
                }
            }
        }
    }



    @SubscribeEvent
    public static void damageEvents(LivingDamageEvent event) {
        if (event.getEntity() instanceof Player p) {
            if(event.getSource() instanceof AstromancyDamageSource ds){
                if(!ds.isBypassesAstral()){
                    event.setAmount((float) (event.getAmount() * (1 - p.getAttributeValue(AttributeRegistry.ASTRAL_RESIST.get()))));
                }
            }
        }
    }

    @SubscribeEvent
    public static void wakeUp(PlayerWakeUpEvent event){
        if(!event.getEntity().level.isClientSide){
            ServerPlayer player = (ServerPlayer) event.getEntity();
            player.getCapability(PlayerResearchHandler.RESEARCH_CAPABILITY).ifPresent(research -> {
                Advancement adv = player.getLevel().getServer().getServerResources().managers().getAdvancements().getAdvancement(Astromancy.astromancy("stellarite"));
                if(research.contains(player, "introduction") && player.getAdvancements().getOrStartProgress(adv).isDone()){
                    research.addResearch(player, ResearchRegistry.FLEETING_TEST.get());
                    long startTime = player.getLevel().getGameTime();
                    player.getPersistentData().putLong("fleeting_test_start", startTime);
                }
            });
        }
    }
}
