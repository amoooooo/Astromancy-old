package coffee.amo.astromancy.core.events;

import coffee.amo.astromancy.Astromancy;
import coffee.amo.astromancy.client.research.ClientResearchHolder;
import coffee.amo.astromancy.common.blockentity.jar.JarBlockEntity;
import coffee.amo.astromancy.common.capability.PlayerResearchProvider;
import coffee.amo.astromancy.core.handlers.CapabilityAspectiHandler;
import coffee.amo.astromancy.core.systems.aspecti.*;
import coffee.amo.astromancy.core.systems.research.IPlayerResearch;
import coffee.amo.astromancy.core.commands.AstromancyCommand;
import coffee.amo.astromancy.core.handlers.AstromancyPacketHandler;
import coffee.amo.astromancy.core.handlers.PlayerResearchHandler;
import coffee.amo.astromancy.core.handlers.SolarEclipseHandler;
import coffee.amo.astromancy.core.packets.ResearchPacket;
import coffee.amo.astromancy.core.packets.StarDataPacket;
import coffee.amo.astromancy.core.util.StarSavedData;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
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
            ClientResearchHolder.research.clear();
            AstromancyPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> se), new StarDataPacket(StarSavedData.get(event.getEntity().getServer()).getConstellationInstances()));
            LazyOptional<IPlayerResearch> optional = event.getPlayer().getCapability(PlayerResearchHandler.RESEARCH_CAPABILITY);
            if(optional.isPresent() && optional.resolve().isPresent()) {
                IPlayerResearch research = optional.resolve().get();
                CompoundTag tag = research.toNBT(new CompoundTag());
                ListTag researchTag = (ListTag) tag.get("research");
                for(int i = 0; i < researchTag.size(); i++) {
                    AstromancyPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> se), new ResearchPacket(researchTag.getString(i), true));
                }
            }
        }
    }

    @SubscribeEvent
    public static void researchCommand(RegisterCommandsEvent event){
        AstromancyCommand.registerSubCommands(event.getDispatcher());
    }

    @SubscribeEvent
    public static void libriObtain(AdvancementEvent event){
        if(event.getAdvancement().getId().equals(Astromancy.astromancy("stella_libri"))){
            if(event.getPlayer() instanceof ServerPlayer se){
                AstromancyPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> se), new ResearchPacket("introduction", false));
                AstromancyPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> se), new ResearchPacket("stellarite", false));
                AstromancyPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> se), new ResearchPacket("tab:introduction", true));
                se.getCapability(PlayerResearchHandler.RESEARCH_CAPABILITY).ifPresent(research -> {
                    research.addResearch(se, "introduction");
                    research.addResearch(se, "tab:introduction");
                    research.addResearch(se, "stellarite");
                });
            }
        }
    }

    @SubscribeEvent
    public static void attachPlayerResearchCapability(AttachCapabilitiesEvent<Entity> event){
        Entity entity = event.getObject();
        if(entity instanceof Player && !(entity instanceof FakePlayer)){
            event.addCapability(Astromancy.astromancy("player_research"), new PlayerResearchProvider());
        }
    }

//    @SubscribeEvent
//    public static void attachBECapabilities(AttachCapabilitiesEvent<BlockEntity> event){
//        if(!(event.getObject() instanceof JarBlockEntity)) return;
//
//        AspectiStackHandler backend = new AspectiStackHandler(256);
//        LazyOptional<IAspectiHandler> optional = LazyOptional.of(() -> backend);
//        Capability<IAspectiHandler> capability = CapabilityAspectiHandler.ASPECTI_HANDLER_CAPABILITY;
//
//        ICapabilityProvider provider = new ICapabilitySerializable<CompoundTag>() {
//
//            @NotNull
//            @Override
//            public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
//                if (cap == capability){
//                    return optional.cast();
//                }
//                return LazyOptional.empty();
//            }
//
//            @Override
//            public CompoundTag serializeNBT() {
//                return backend.writeToNBT(new CompoundTag());
//            }
//
//            @Override
//            public void deserializeNBT(CompoundTag nbt) {
//                backend.readFromNBT(nbt);
//            }
//        };
//
//        event.addCapability(Astromancy.astromancy("aspecti_handler"), provider);
//    }
}
