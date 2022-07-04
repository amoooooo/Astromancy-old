package coffee.amo.astromancy;

import coffee.amo.astromancy.core.handlers.AstromancyPacketHandler;
import coffee.amo.astromancy.core.handlers.CapabilityGlyphHandler;
import coffee.amo.astromancy.core.registration.*;
import coffee.amo.astromancy.core.util.StarSavedData;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.SlotTypePreset;

import java.util.Arrays;
import java.util.stream.Collectors;

import static coffee.amo.astromancy.core.registration.AttributeRegistry.ATTRIBUTES;
import static coffee.amo.astromancy.core.registration.BlockEntityRegistration.BLOCK_ENTITY_TYPES;
import static coffee.amo.astromancy.core.registration.BlockRegistration.BLOCKS;
import static coffee.amo.astromancy.core.registration.ItemRegistry.ITEMS;
import static coffee.amo.astromancy.core.registration.RecipeRegistry.RECIPE_SERIALIZERS;
import static coffee.amo.astromancy.core.registration.SoundRegistry.SOUNDS;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("astromancy")
public class Astromancy {
    public static final String MODID = "astromancy";

    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();

    public Astromancy() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        modBus.addListener(this::registerCapabilities);
        BlockRegistration.register();
        GlyphRegistry.register();
        ResearchRegistry.register();
        TabRegistry.register();
        AstromancyPacketHandler.init();
        BLOCKS.register(modBus);
        ITEMS.register(modBus);
        ATTRIBUTES.register(modBus);
        SOUNDS.register(modBus);
        RECIPE_SERIALIZERS.register(modBus);
        BLOCK_ENTITY_TYPES.register(modBus);
        // Register the enqueueIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        MinecraftForge.EVENT_BUS.addListener(this::attachDataStorage);
    }

    public void registerCapabilities(RegisterCapabilitiesEvent event){
        CapabilityGlyphHandler.register(event);
    }

    private void setup(final FMLCommonSetupEvent event) {
        // some preinit code
        ResearchRegistry.doSetup();
        TabRegistry.doSetup();
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {
        // some example code to dispatch IMC to another mod
        InterModComms.sendTo("astromancy", "helloworld", () -> {
            LOGGER.info("Hello world from the MDK");
            return "Hello world";
        });
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE,
                () -> Arrays.stream(SlotTypePreset.values()).map(preset -> preset.getMessageBuilder().build()).collect(Collectors.toList()));
    }

    private void processIMC(final InterModProcessEvent event) {
        // some example code to receive and process InterModComms from other mods
        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m -> m.messageSupplier().get()).
                collect(Collectors.toList()));
    }

    private void attachDataStorage(final ServerStartedEvent event) {
        //Attach the data as soon as we can, not sure if it is required
        StarSavedData.get(event.getServer());
    }

    public static ResourceLocation astromancy(String path){
        return new ResourceLocation(MODID, path);
    }
}
