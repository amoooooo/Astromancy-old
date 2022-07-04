package coffee.amo.astromancy;

import coffee.amo.astromancy.client.screen.stellalibri.pages.ResearchPageRegistry;
import coffee.amo.astromancy.common.item.JarItem;
import coffee.amo.astromancy.common.item.StellaLibri;
import coffee.amo.astromancy.core.registration.BlockRegistration;
import coffee.amo.astromancy.core.registration.ItemRegistry;
import coffee.amo.astromancy.core.systems.glyph.Glyph;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import static coffee.amo.astromancy.Astromancy.astromancy;

@Mod.EventBusSubscriber(modid = Astromancy.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class AstromancyClient {
    @SubscribeEvent
    public static void client(final FMLClientSetupEvent event) {
        // do something that can only be done on the client
        ItemProperties.register(ItemRegistry.STELLA_LIBRI.get(), astromancy("book_open"), (pStack, pLevel, pEntity, pSeed) -> {
            if(pStack.getItem() instanceof StellaLibri sl){
                return pStack.getOrCreateTag().getInt("openness");
            }
            return 0;
        });
        ItemProperties.register(ItemRegistry.GLYPH_PHIAL.get(), astromancy("phial_filled"), (pStack, pLevel, pEntity, pSeed) -> {
            if(Glyph.fromItemStack(pStack) != Glyph.EMPTY){
                return 1;
            }
            return 0;
        });
        ItemProperties.register(ItemRegistry.JAR.get(), astromancy("jar_fill"), (pStack, pLevel, pEntity, pSeed) -> {
            if(Glyph.fromItemStack(pStack) != Glyph.EMPTY){
                if(pStack.getItem() instanceof JarItem){
                    return ((CompoundTag)((CompoundTag)((CompoundTag)pStack.getTag().get("BlockEntityTag")).get("Glyph")).get("GlyphStack")).getInt("Amount") / 256.0f;
                }
            }
            return 0;
        });
        ItemBlockRenderTypes.setRenderLayer(BlockRegistration.JAR.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(BlockRegistration.ARMILLARY_SPHERE_COMPONENT.get(), RenderType.cutout());
        ResearchPageRegistry.register();
    }
}
