//package coffee.amo.astromancy.common.item;
//
//import coffee.amo.astromancy.Astromancy;
//import coffee.amo.astromancy.core.registration.AttributeRegistry;
//import com.google.common.collect.LinkedHashMultimap;
//import com.google.common.collect.Multimap;
//import net.minecraft.nbt.CompoundTag;
//import net.minecraft.world.effect.MobEffectInstance;
//import net.minecraft.world.effect.MobEffects;
//import net.minecraft.world.entity.LivingEntity;
//import net.minecraft.world.entity.ai.attributes.Attribute;
//import net.minecraft.world.entity.ai.attributes.AttributeModifier;
//import net.minecraft.world.item.Item;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.item.Wearable;
//import net.minecraftforge.common.capabilities.ICapabilityProvider;
//import org.jetbrains.annotations.Nullable;
//import top.theillusivec4.curios.api.CuriosApi;
//import top.theillusivec4.curios.api.SlotContext;
//import top.theillusivec4.curios.api.type.capability.ICurio;
//import top.theillusivec4.curios.common.capability.CurioItemCapability;
//
//import java.util.UUID;
//
//public class TestRing extends Item implements Wearable {
//    public TestRing(Properties p_41383_) {
//        super(p_41383_);
//    }
//
//    @Override
//    public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
//        return CurioItemCapability.createProvider(new ICurio() {
//            @Override
//            public ItemStack getStack() {
//                return stack;
//            }
//
//            @Override
//            public void curioTick(SlotContext slotContext) {
//            }
//
//            @Override
//            public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid) {
//                Multimap<Attribute, AttributeModifier> atts = LinkedHashMultimap.create();
//                atts.put(AttributeRegistry.ASTRAL_RESIST.get(),
//                        new AttributeModifier(uuid, Astromancy.MODID + ":astral_resist", 0.25, AttributeModifier.Operation.ADDITION));
//                CuriosApi.getCuriosHelper().addSlotModifier(atts, "ring", uuid, 1, AttributeModifier.Operation.ADDITION);
//                return atts;
//            }
//        });
//    }
//}
