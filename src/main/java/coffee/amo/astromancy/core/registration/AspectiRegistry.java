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
    public static final RegistryObject<ICompoundType> CONJUNCTION = TYPES.register("conjunction", () -> new AspectiCompoundType(Aspecti.CONJUNCTION, ASPECTI_TYPES).setRegistryName(Astromancy.astromancy("conjunction")));
    public static final RegistryObject<ICompoundType> OPPOSITION = TYPES.register("opposition", () -> new AspectiCompoundType(Aspecti.OPPOSITION, ASPECTI_TYPES).setRegistryName(Astromancy.astromancy("opposition")));
    public static final RegistryObject<ICompoundType> SQUARE = TYPES.register("square", () -> new AspectiCompoundType(Aspecti.SQUARE, ASPECTI_TYPES).setRegistryName(Astromancy.astromancy("square")));
    public static final RegistryObject<ICompoundType> TRINE = TYPES.register("trine", () -> new AspectiCompoundType(Aspecti.TRINE, ASPECTI_TYPES).setRegistryName(Astromancy.astromancy("trine")));
    public static final RegistryObject<ICompoundType> SEXTILE = TYPES.register("sextile", () -> new AspectiCompoundType(Aspecti.SEXTILE, ASPECTI_TYPES).setRegistryName(Astromancy.astromancy("sextile")));
    public static final RegistryObject<ICompoundType> SEMISEXTILE = TYPES.register("semisextile", () -> new AspectiCompoundType(Aspecti.SEMISEXTILE, ASPECTI_TYPES).setRegistryName(Astromancy.astromancy("semisextile")));
    public static final RegistryObject<ICompoundType> QUINTILE = TYPES.register("quintile", () -> new AspectiCompoundType(Aspecti.QUINTILE, ASPECTI_TYPES).setRegistryName(Astromancy.astromancy("quintile")));
    public static final RegistryObject<ICompoundType> QUINCUNX = TYPES.register("quincunx", () -> new AspectiCompoundType(Aspecti.QUINCUNX, ASPECTI_TYPES).setRegistryName(Astromancy.astromancy("quincunx")));
    public static final RegistryObject<ICompoundType> OCTILE = TYPES.register("octile", () -> new AspectiCompoundType(Aspecti.OCTILE, ASPECTI_TYPES).setRegistryName(Astromancy.astromancy("octile")));
    public static final RegistryObject<ICompoundType> TRIOCTILE = TYPES.register("trioctile", () -> new AspectiCompoundType(Aspecti.TRIOCTILE, ASPECTI_TYPES).setRegistryName(Astromancy.astromancy("trioctile")));
    public static final RegistryObject<ICompoundType> DECILE = TYPES.register("decile", () -> new AspectiCompoundType(Aspecti.DECILE, ASPECTI_TYPES).setRegistryName(Astromancy.astromancy("decile")));
    public static final RegistryObject<ICompoundType> SOL = TYPES.register("sol", () -> new AspectiCompoundType(Aspecti.SOL, ASPECTI_TYPES).setRegistryName(Astromancy.astromancy("sol")));
    public static final RegistryObject<ICompoundType> LUNA = TYPES.register("luna", () -> new AspectiCompoundType(Aspecti.LUNA, ASPECTI_TYPES).setRegistryName(Astromancy.astromancy("luna")));
    public static final RegistryObject<ICompoundType> MERCURIA = TYPES.register("mercuria", () -> new AspectiCompoundType(Aspecti.MERCURIA, ASPECTI_TYPES).setRegistryName(Astromancy.astromancy("mercuria")));
    public static final RegistryObject<ICompoundType> VENUTIO = TYPES.register("venutio", () -> new AspectiCompoundType(Aspecti.VENUTIO, ASPECTI_TYPES).setRegistryName(Astromancy.astromancy("venutio")));
    public static final RegistryObject<ICompoundType> MARTUS = TYPES.register("martus", () -> new AspectiCompoundType(Aspecti.MARTUS, ASPECTI_TYPES).setRegistryName(Astromancy.astromancy("martus")));
    public static final RegistryObject<ICompoundType> JUPILUS = TYPES.register("jupilus", () -> new AspectiCompoundType(Aspecti.JUPILUS, ASPECTI_TYPES).setRegistryName(Astromancy.astromancy("jupilus")));
    public static final RegistryObject<ICompoundType> SATURIS = TYPES.register("saturis", () -> new AspectiCompoundType(Aspecti.SATURIS, ASPECTI_TYPES).setRegistryName(Astromancy.astromancy("saturis")));
    public static final RegistryObject<ICompoundType> URANIA = TYPES.register("urania", () -> new AspectiCompoundType(Aspecti.URANIA, ASPECTI_TYPES).setRegistryName(Astromancy.astromancy("urania")));
    public static final RegistryObject<ICompoundType> NEPTURA = TYPES.register("neptura", () -> new AspectiCompoundType(Aspecti.NEPTURA, ASPECTI_TYPES).setRegistryName(Astromancy.astromancy("neptura")));
    public static final RegistryObject<ICompoundType> PLUTUS = TYPES.register("plutus", () -> new AspectiCompoundType(Aspecti.PLUTUS, ASPECTI_TYPES).setRegistryName(Astromancy.astromancy("plutus")));
    public static final RegistryObject<ICompoundType> CHIROS = TYPES.register("chiros", () -> new AspectiCompoundType(Aspecti.CHIROS, ASPECTI_TYPES).setRegistryName(Astromancy.astromancy("chiros")));
    public static final RegistryObject<ICompoundType> LILITHIA = TYPES.register("lilithia", () -> new AspectiCompoundType(Aspecti.LILITHIA, ASPECTI_TYPES).setRegistryName(Astromancy.astromancy("lilithia")));

    public static void register(){
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        TYPES.register(bus);
    }
}
