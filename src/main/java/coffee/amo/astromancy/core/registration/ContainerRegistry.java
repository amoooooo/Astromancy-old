package coffee.amo.astromancy.core.registration;

import coffee.amo.astromancy.Astromancy;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = Astromancy.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ContainerRegistry {
    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, Astromancy.MODID);

//    public static final RegistryObject<MenuType<ResearchTableContainer>> RESEARCH_TABLE = CONTAINERS.register("research_table", () -> IForgeMenuType.create((int id, Inventory inv, FriendlyByteBuf extraData) -> { return new ResearchTableContainer(id, inv, extraData.readItem()); }));

    @SubscribeEvent
    public static void bindContainerRenderers(FMLClientSetupEvent event){
        DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
//            MenuScreens.register(ContainerRegistry.RESEARCH_TABLE.get(), ResearchTableScreen::new);
        });
    }
}
