package coffee.amo.astromancy.core.registration;

import coffee.amo.astromancy.Astromancy;
import coffee.amo.astromancy.client.renderer.block.ArmillarySphereRenderer;
import coffee.amo.astromancy.client.renderer.block.AstrolabeRenderer;
import coffee.amo.astromancy.client.renderer.block.StarGatewayRenderer;
import coffee.amo.astromancy.common.blockentity.ArmillarySphereBlockEntity;
import coffee.amo.astromancy.common.blockentity.AstrolabeBlockEntity;
import coffee.amo.astromancy.common.blockentity.StarGatewayBlockEntity;
import com.tterrag.registrate.util.entry.BlockEntityEntry;

public class BlockEntityRegistration {

    public static final BlockEntityEntry<ArmillarySphereBlockEntity> ARMILLARY_SPHERE = Astromancy.registrate().<ArmillarySphereBlockEntity>blockEntity("armillary_sphere", ArmillarySphereBlockEntity::new).renderer(() -> ArmillarySphereRenderer::new).validBlock(BlockRegistration.ARMILLARY_SPHERE).register();
    public static final BlockEntityEntry<StarGatewayBlockEntity> STAR_GATEWAY = Astromancy.registrate().<StarGatewayBlockEntity>blockEntity("star_gateway", StarGatewayBlockEntity::new).renderer(() -> StarGatewayRenderer::new).validBlock(BlockRegistration.STAR_GATEWAY).register();
    public static final BlockEntityEntry<AstrolabeBlockEntity> ASTROLABE = Astromancy.registrate().<AstrolabeBlockEntity>blockEntity("astrolabe", AstrolabeBlockEntity::new).renderer(() -> AstrolabeRenderer::new).validBlock(BlockRegistration.ASTROLABE).register();

    public static void register(){

    }
}
