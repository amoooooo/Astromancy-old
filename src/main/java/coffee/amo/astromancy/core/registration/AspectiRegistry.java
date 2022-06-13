package coffee.amo.astromancy.core.registration;

import coffee.amo.astromancy.Astromancy;
import coffee.amo.astromancy.aequivaleo.compound.AspectiCompoundType;
import coffee.amo.astromancy.aequivaleo.compound.AspectiCompoundTypeGroup;
import coffee.amo.astromancy.core.systems.aspecti.Aspecti;
import com.ldtteam.aequivaleo.api.compound.type.ICompoundType;
import com.ldtteam.aequivaleo.api.compound.type.group.ICompoundTypeGroup;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class AspectiRegistry {
    private static final ResourceLocation AEQ_COMPOUND_TYPES_LOC = new ResourceLocation("aequivaleo", "compound_type");
    private static final ResourceLocation AEQ_COMPOUND_TYPE_GROUP_LOC = new ResourceLocation("aequivaleo", "compound_type_group");
    public static final DeferredRegister<ICompoundType> TYPES = DeferredRegister.create(AEQ_COMPOUND_TYPES_LOC, Astromancy.MODID);
    public static final DeferredRegister<ICompoundTypeGroup> TYPE_GROUPS = DeferredRegister.create(AEQ_COMPOUND_TYPE_GROUP_LOC, Astromancy.MODID);
    public static final RegistryObject<AspectiCompoundTypeGroup> ASPECTI_TYPES = TYPE_GROUPS.register("aspecti_types", AspectiCompoundTypeGroup::new);
    public static final RegistryObject<ICompoundType> CONJUNCTION = TYPES.register("conjunction", () -> new AspectiCompoundType(Aspecti.CONJUNCTION, ASPECTI_TYPES));
    public static final RegistryObject<ICompoundType> OPPOSITION = TYPES.register("opposition", () -> new AspectiCompoundType(Aspecti.OPPOSITION, ASPECTI_TYPES));
    public static final RegistryObject<ICompoundType> SQUARE = TYPES.register("square", () -> new AspectiCompoundType(Aspecti.SQUARE, ASPECTI_TYPES));
    public static final RegistryObject<ICompoundType> TRINE = TYPES.register("trine", () -> new AspectiCompoundType(Aspecti.TRINE, ASPECTI_TYPES));
    public static final RegistryObject<ICompoundType> SEXTILE = TYPES.register("sextile", () -> new AspectiCompoundType(Aspecti.SEXTILE, ASPECTI_TYPES));
    public static final RegistryObject<ICompoundType> SEMISEXTILE = TYPES.register("semisextile", () -> new AspectiCompoundType(Aspecti.SEMISEXTILE, ASPECTI_TYPES));
    public static final RegistryObject<ICompoundType> QUINTILE = TYPES.register("quintile", () -> new AspectiCompoundType(Aspecti.QUINTILE, ASPECTI_TYPES));
    public static final RegistryObject<ICompoundType> QUINCUNX = TYPES.register("quincunx", () -> new AspectiCompoundType(Aspecti.QUINCUNX, ASPECTI_TYPES));
    public static final RegistryObject<ICompoundType> OCTILE = TYPES.register("octile", () -> new AspectiCompoundType(Aspecti.OCTILE, ASPECTI_TYPES));
    public static final RegistryObject<ICompoundType> TRIOCTILE = TYPES.register("trioctile", () -> new AspectiCompoundType(Aspecti.TRIOCTILE, ASPECTI_TYPES));
    public static final RegistryObject<ICompoundType> DECILE = TYPES.register("decile", () -> new AspectiCompoundType(Aspecti.DECILE, ASPECTI_TYPES));
    public static final RegistryObject<ICompoundType> SOL = TYPES.register("sol", () -> new AspectiCompoundType(Aspecti.SOL, ASPECTI_TYPES));
    public static final RegistryObject<ICompoundType> LUNA = TYPES.register("luna", () -> new AspectiCompoundType(Aspecti.LUNA, ASPECTI_TYPES));
    public static final RegistryObject<ICompoundType> MERCURIA = TYPES.register("jupiter", () -> new AspectiCompoundType(Aspecti.MERCURIA, ASPECTI_TYPES));
    public static final RegistryObject<ICompoundType> VENUTIO = TYPES.register("venus", () -> new AspectiCompoundType(Aspecti.VENUTIO, ASPECTI_TYPES));
    public static final RegistryObject<ICompoundType> MARTUS = TYPES.register("mars", () -> new AspectiCompoundType(Aspecti.MARTUS, ASPECTI_TYPES));
    public static final RegistryObject<ICompoundType> JUPILUS = TYPES.register("jupiter", () -> new AspectiCompoundType(Aspecti.JUPILUS, ASPECTI_TYPES));
    public static final RegistryObject<ICompoundType> SATURIS = TYPES.register("saturnus", () -> new AspectiCompoundType(Aspecti.SATURIS, ASPECTI_TYPES));
    public static final RegistryObject<ICompoundType> URANIA = TYPES.register("uranus", () -> new AspectiCompoundType(Aspecti.URANIA, ASPECTI_TYPES));
    public static final RegistryObject<ICompoundType> NEPTURA = TYPES.register("neptunus", () -> new AspectiCompoundType(Aspecti.NEPTURA, ASPECTI_TYPES));
    public static final RegistryObject<ICompoundType> PLUTUS = TYPES.register("pluto", () -> new AspectiCompoundType(Aspecti.PLUTUS, ASPECTI_TYPES));
    public static final RegistryObject<ICompoundType> CHIROS = TYPES.register("argus", () -> new AspectiCompoundType(Aspecti.CHIROS, ASPECTI_TYPES));
    public static final RegistryObject<ICompoundType> LILITHIA = TYPES.register("aquarius", () -> new AspectiCompoundType(Aspecti.LILITHIA, ASPECTI_TYPES));

    public static void register(){
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        TYPES.register(bus);
    }
}
