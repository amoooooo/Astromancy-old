package coffee.amo.astromancy.core.registration;

import coffee.amo.astromancy.Astromancy;
import coffee.amo.astromancy.client.renderer.block.*;
import coffee.amo.astromancy.common.block.armillary_sphere.ArmillarySphereCoreBlock;
import coffee.amo.astromancy.common.blockentity.AstrolabeBlockEntity;
import coffee.amo.astromancy.common.blockentity.CrucibleBlockEntity;
import coffee.amo.astromancy.common.blockentity.StarGatewayBlockEntity;
import coffee.amo.astromancy.common.blockentity.armillary_sphere.ArmillarySphereCoreBlockEntity;
import coffee.amo.astromancy.common.blockentity.jar.JarBlockEntity;
import coffee.amo.astromancy.common.blockentity.mortar.MortarBlockEntity;
import coffee.amo.astromancy.core.systems.multiblock.IAstromancyMultiblockComponent;
import coffee.amo.astromancy.core.systems.multiblock.MultiblockComponentEntity;
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



    public static DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Astromancy.MODID);
    public static final RegistryObject<BlockEntityType<MultiblockComponentEntity>> MULTIBLOCK_COMPONENT = BLOCK_ENTITY_TYPES.register("multiblock_component", () -> BlockEntityType.Builder.of(MultiblockComponentEntity::new, getBlocks(IAstromancyMultiblockComponent.class)).build(null));

    public static final RegistryObject<BlockEntityType<ArmillarySphereCoreBlockEntity>> ARMILLARY_SPHERE = BLOCK_ENTITY_TYPES.register("armillary_sphere", () -> BlockEntityType.Builder.of(ArmillarySphereCoreBlockEntity::new, getBlocks(ArmillarySphereCoreBlock.class)).build(null));
    public static final RegistryObject<BlockEntityType<StarGatewayBlockEntity>> STAR_GATEWAY = BLOCK_ENTITY_TYPES.register("star_gateway", () -> BlockEntityType.Builder.of(StarGatewayBlockEntity::new, BlockRegistration.STAR_GATEWAY.get()).build(null));
    public static final RegistryObject<BlockEntityType<JarBlockEntity>> JAR = BLOCK_ENTITY_TYPES.register("jar", () -> BlockEntityType.Builder.of(JarBlockEntity::new, BlockRegistration.JAR.get()).build(null));
    public static final RegistryObject<BlockEntityType<AstrolabeBlockEntity>> ASTROLABE = BLOCK_ENTITY_TYPES.register("astrolabe", () -> BlockEntityType.Builder.of(AstrolabeBlockEntity::new, BlockRegistration.ASTROLABE.get()).build(null));
    public static final RegistryObject<BlockEntityType<CrucibleBlockEntity>> CRUCIBLE = BLOCK_ENTITY_TYPES.register("crucible", () -> BlockEntityType.Builder.of(CrucibleBlockEntity::new, BlockRegistration.CRUCIBLE.get()).build(null));
    public static final RegistryObject<BlockEntityType<MortarBlockEntity>> MORTAR = BLOCK_ENTITY_TYPES.register("mortar", () -> BlockEntityType.Builder.of(MortarBlockEntity::new, BlockRegistration.MORTAR.get()).build(null));
//    public static final RegistryObject<BlockEntityType<ResearchTableCoreBlockEntity>> RESEARCH_TABLE = BLOCK_ENTITY_TYPES.register("research_table", () -> BlockEntityType.Builder.of(ResearchTableCoreBlockEntity::new, BlockRegistration.RESEARCH_TABLE.get()).build(null));

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
            event.registerBlockEntityRenderer(STAR_GATEWAY.get(), StarGatewayRenderer::new);
            event.registerBlockEntityRenderer(JAR.get(), JarRenderer::new);
            event.registerBlockEntityRenderer(ASTROLABE.get(), AstrolabeRenderer::new);
            event.registerBlockEntityRenderer(MORTAR.get(), MortarRenderer::new);
        }
    }
}
