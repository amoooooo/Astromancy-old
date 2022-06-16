package coffee.amo.astromancy.core.registration;

import coffee.amo.astromancy.Astromancy;
import coffee.amo.astromancy.client.renderer.block.ArmillarySphereRenderer;
import coffee.amo.astromancy.client.renderer.block.AstrolabeRenderer;
import coffee.amo.astromancy.client.renderer.block.JarRenderer;
import coffee.amo.astromancy.client.renderer.block.StarGatewayRenderer;
import coffee.amo.astromancy.common.block.armillary_sphere.ArmillarySphereCoreBlock;
import coffee.amo.astromancy.common.blockentity.AstrolabeBlockEntity;
import coffee.amo.astromancy.common.blockentity.StarGatewayBlockEntity;
import coffee.amo.astromancy.common.blockentity.armillary_sphere.ArmillarySphereCoreBlockEntity;
import coffee.amo.astromancy.common.blockentity.jar.JarBlockEntity;
import coffee.amo.astromancy.core.systems.multiblock.IAstromancyMultiblockComponent;
import coffee.amo.astromancy.core.systems.multiblock.MultiblockComponentEntity;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.Arrays;

public class BlockEntityRegistration {

    //public static final BlockEntityEntry<ArmillarySphereBlockEntity> ARMILLARY_SPHERE = Astromancy.registrate().<ArmillarySphereBlockEntity>blockEntity("armillary_sphere", ArmillarySphereBlockEntity::new).renderer(() -> ArmillarySphereRenderer::new).validBlock(BlockRegistration.ARMILLARY_SPHERE).register();
    public static final BlockEntityEntry<StarGatewayBlockEntity> STAR_GATEWAY = Astromancy.registrate().<StarGatewayBlockEntity>blockEntity("star_gateway", StarGatewayBlockEntity::new).renderer(() -> StarGatewayRenderer::new).validBlock(BlockRegistration.STAR_GATEWAY).register();
    public static final BlockEntityEntry<AstrolabeBlockEntity> ASTROLABE = Astromancy.registrate().<AstrolabeBlockEntity>blockEntity("astrolabe", AstrolabeBlockEntity::new).renderer(() -> AstrolabeRenderer::new).validBlock(BlockRegistration.ASTROLABE).register();
    public static final BlockEntityEntry<JarBlockEntity> JAR = Astromancy.registrate().<JarBlockEntity>blockEntity("jar", JarBlockEntity::new).renderer(() -> JarRenderer::new).validBlock(BlockRegistration.JAR).register();


    public static DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, Astromancy.MODID);
    public static final RegistryObject<BlockEntityType<MultiblockComponentEntity>> MULTIBLOCK_COMPONENT = BLOCK_ENTITY_TYPES.register("multiblock_component", () -> BlockEntityType.Builder.of(MultiblockComponentEntity::new, getBlocks(IAstromancyMultiblockComponent.class)).build(null));

    public static final RegistryObject<BlockEntityType<ArmillarySphereCoreBlockEntity>> ARMILLARY_SPHERE = BLOCK_ENTITY_TYPES.register("armillary_sphere", () -> BlockEntityType.Builder.of(ArmillarySphereCoreBlockEntity::new, getBlocks(ArmillarySphereCoreBlock.class)).build(null));

    public static void register(){

    }
    public static Block[] getBlocks(Class<?>... blockClasses) {
        IForgeRegistry<Block> blocks = ForgeRegistries.BLOCKS;
        ArrayList<Block> matchingBlocks = new ArrayList<>();
        for (Block block : blocks) {
            if (Arrays.stream(blockClasses).anyMatch(b -> b.isInstance(block))) {
                matchingBlocks.add(block);
            }
        }
        return matchingBlocks.toArray(new Block[0]);
    }

    @Mod.EventBusSubscriber(modid = Astromancy.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientOnly{
        @SubscribeEvent
        public static void registerRenderer(EntityRenderersEvent.RegisterRenderers event){
            event.registerBlockEntityRenderer(ARMILLARY_SPHERE.get(), ArmillarySphereRenderer::new);
        }
    }
}
