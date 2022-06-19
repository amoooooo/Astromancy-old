package coffee.amo.astromancy.core.registration;

import coffee.amo.astromancy.Astromancy;
import coffee.amo.astromancy.common.block.Astrolabe;
import coffee.amo.astromancy.common.block.Crucible;
import coffee.amo.astromancy.common.block.StarGateway;
import coffee.amo.astromancy.common.block.armillary_sphere.ArmillarySphereComponentBlock;
import coffee.amo.astromancy.common.block.armillary_sphere.ArmillarySphereCoreBlock;
import coffee.amo.astromancy.common.block.jar.JarBlock;
import coffee.amo.astromancy.common.block.mortar.MortarBlock;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static coffee.amo.astromancy.core.registration.BlockPropertyRegistration.JAR_PROPERTIES;
import static coffee.amo.astromancy.core.registration.BlockPropertyRegistration.MAGIC_PROPERTIES;

public class BlockRegistration {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Astromancy.MODID);

//    public static final BlockEntry<ArmillarySphere<ArmillarySphereBlockEntity>> ARMILLARY_SPHERE = setupSimpleBlock("armillary_sphere",
//            (p) -> new ArmillarySphere<>(p).<ArmillarySphere<ArmillarySphereBlockEntity>>setBlockEntity(BlockEntityRegistration.ARMILLARY_SPHERE), MAGIC_PROPERTIES()).register();
    public static final RegistryObject<Block> STAR_GATEWAY = BLOCKS.register("star_gateway", () -> new StarGateway<>(MAGIC_PROPERTIES()).setBlockEntity(BlockEntityRegistration.STAR_GATEWAY));
    public static final RegistryObject<Block> ASTROLABE = BLOCKS.register("astrolabe", () -> new Astrolabe<>(MAGIC_PROPERTIES()).setBlockEntity(BlockEntityRegistration.ASTROLABE));
    public static final RegistryObject<Block> JAR = BLOCKS.register("jar", () -> new JarBlock<>(JAR_PROPERTIES()).setBlockEntity(BlockEntityRegistration.JAR));
    public static final RegistryObject<Block> ARMILLARY_SPHERE = BLOCKS.register("armillary_sphere", () -> new ArmillarySphereCoreBlock<>(MAGIC_PROPERTIES().noOcclusion()).setBlockEntity(BlockEntityRegistration.ARMILLARY_SPHERE));
    public static final RegistryObject<Block> ARMILLARY_SPHERE_COMPONENT = BLOCKS.register("armillary_sphere_component", () -> new ArmillarySphereComponentBlock(MAGIC_PROPERTIES().noOcclusion().lootFrom(ARMILLARY_SPHERE)));
    public static final RegistryObject<Block> CRUCIBLE = BLOCKS.register("crucible", () -> new Crucible<>(MAGIC_PROPERTIES().noOcclusion()).setBlockEntity(BlockEntityRegistration.CRUCIBLE));
    public static final RegistryObject<Block> STELLARITE = BLOCKS.register("stellarite", () -> new Block(BlockPropertyRegistration.STELLARITE()));
    public static final RegistryObject<Block> MORTAR = BLOCKS.register("mortar", () -> new MortarBlock<>(MAGIC_PROPERTIES().noOcclusion().instabreak()).setBlockEntity(BlockEntityRegistration.MORTAR));
    public static void register(){}
}
