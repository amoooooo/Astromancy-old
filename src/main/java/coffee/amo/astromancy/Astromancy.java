package coffee.amo.astromancy;

import coffee.amo.astromancy.common.item.AspectiPhial;
import coffee.amo.astromancy.common.item.StellaLibri;
import coffee.amo.astromancy.core.handlers.AstromancyPacketHandler;
import coffee.amo.astromancy.core.handlers.CapabilityAspectiHandler;
import coffee.amo.astromancy.core.registration.AspectiRegistry;
import coffee.amo.astromancy.core.registration.BlockRegistration;
import coffee.amo.astromancy.core.registration.ItemRegistry;
import coffee.amo.astromancy.core.systems.aspecti.Aspecti;
import coffee.amo.astromancy.core.util.StarSavedData;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.stream.Collectors;

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

    public static CreativeModeTab PHIALS = new CreativeModeTab(MODID + ".phialTab") {
        @Override
        public ItemStack makeIcon() {
            return ItemRegistry.ASPECTI_PHIAL.get().getDefaultInstance();
        }
    };

    public Astromancy() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::client);
        CapabilityAspectiHandler.init();
        BlockRegistration.register();
        AspectiRegistry.register();
        ItemRegistry.register();
        AstromancyPacketHandler.init();
        BLOCKS.register(modBus);
        ITEMS.register(modBus);
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

    private void setup(final FMLCommonSetupEvent event) {
        // some preinit code
    }

    private void client(final FMLClientSetupEvent event) {
        // do something that can only be done on the client
        ItemProperties.register(ItemRegistry.STELLA_LIBRI.get(), astromancy("book_open"), (pStack, pLevel, pEntity, pSeed) -> {
            if(pStack.getItem() instanceof StellaLibri sl){
                return pStack.getOrCreateTag().getInt("openness");
            }
            return 0;
        });
        ItemProperties.register(ItemRegistry.ASPECTI_PHIAL.get(), astromancy("phial_filled"), (pStack, pLevel, pEntity, pSeed) -> {
            if(pStack.getItem() instanceof AspectiPhial && Aspecti.fromItemStack(pStack) != Aspecti.EMPTY){
                return 1;
            }
            return 0;
        });
        ItemProperties.register(ItemRegistry.JAR.get(), astromancy("jar_fill"), (pStack, pLevel, pEntity, pSeed) -> {
            if(pStack.getItem().equals(ItemRegistry.JAR.get()) && pStack.hasTag()){
                return ((CompoundTag)pStack.getTag().get("BlockEntityTag")).getInt("count") / 256f;
            }
            return 0;
        });
        ItemBlockRenderTypes.setRenderLayer(BlockRegistration.JAR.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockRegistration.ARMILLARY_SPHERE_COMPONENT.get(), RenderType.cutout());
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {
        // some example code to dispatch IMC to another mod
        InterModComms.sendTo("astromancy", "helloworld", () -> {
            LOGGER.info("Hello world from the MDK");
            return "Hello world";
        });
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
