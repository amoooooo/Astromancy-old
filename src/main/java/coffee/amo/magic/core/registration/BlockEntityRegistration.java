package coffee.amo.magic.core.registration;

import coffee.amo.magic.Magic;
import coffee.amo.magic.client.renderer.block.ArmillarySphereRenderer;
import coffee.amo.magic.common.blockentity.ArmillarySphereBlockEntity;
import com.tterrag.registrate.util.entry.BlockEntityEntry;

public class BlockEntityRegistration {

    public static final BlockEntityEntry<ArmillarySphereBlockEntity> ARMILLARY_SPHERE = Magic.registrate().<ArmillarySphereBlockEntity>blockEntity("armillary_sphere", ArmillarySphereBlockEntity::new).renderer(() -> ArmillarySphereRenderer::new).validBlock(BlockRegistration.ARMILLARY_SPHERE).register();

    public static void register(){

    }
}
