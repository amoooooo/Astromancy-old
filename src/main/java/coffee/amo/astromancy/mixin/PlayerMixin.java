package coffee.amo.astromancy.mixin;

import coffee.amo.astromancy.core.registration.ItemRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public class PlayerMixin {

    @Inject(method = "isScoping", at = @At("HEAD"), cancellable = true)
    public void isScoping(CallbackInfoReturnable<Boolean> cir) {
        LivingEntity player = (LivingEntity) (Object) this;
        cir.setReturnValue(player.isUsingItem() && (player.getUseItem().is(Items.SPYGLASS) || player.getUseItem().is(ItemRegistry.SKYWATCHERS_LOOKING_GLASS.get())));
    }
}
