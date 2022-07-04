package coffee.amo.astromancy.core.registration;

import coffee.amo.astromancy.Astromancy;
import coffee.amo.astromancy.aequivaleo.compound.GlyphCompoundType;
import coffee.amo.astromancy.aequivaleo.compound.GlyphCompoundTypeGroup;
import coffee.amo.astromancy.core.systems.glyph.Glyph;
import com.ldtteam.aequivaleo.api.compound.type.ICompoundType;
import com.ldtteam.aequivaleo.api.compound.type.group.ICompoundTypeGroup;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class GlyphRegistry {
    private static final ResourceLocation AEQ_COMPOUND_TYPES_LOC = new ResourceLocation("aequivaleo", "compound_type");
    private static final ResourceLocation AEQ_COMPOUND_TYPE_GROUP_LOC = new ResourceLocation("aequivaleo", "compound_type_group");
    public static final DeferredRegister<ICompoundType> TYPES = DeferredRegister.create(AEQ_COMPOUND_TYPES_LOC, Astromancy.MODID);
    public static final DeferredRegister<ICompoundTypeGroup> TYPE_GROUPS = DeferredRegister.create(AEQ_COMPOUND_TYPE_GROUP_LOC, Astromancy.MODID);
    public static final RegistryObject<GlyphCompoundTypeGroup> GLYPH_TYPES = TYPE_GROUPS.register("glyph_types", GlyphCompoundTypeGroup::new);
    public static final RegistryObject<ICompoundType> CONJUNCTION = TYPES.register("conjunction", () -> new GlyphCompoundType(Glyph.CONJUNCTION, GLYPH_TYPES).setRegistryName(Astromancy.astromancy("conjunction")));
    public static final RegistryObject<ICompoundType> OPPOSITION = TYPES.register("opposition", () -> new GlyphCompoundType(Glyph.OPPOSITION, GLYPH_TYPES).setRegistryName(Astromancy.astromancy("opposition")));
    public static final RegistryObject<ICompoundType> SQUARE = TYPES.register("square", () -> new GlyphCompoundType(Glyph.SQUARE, GLYPH_TYPES).setRegistryName(Astromancy.astromancy("square")));
    public static final RegistryObject<ICompoundType> TRINE = TYPES.register("trine", () -> new GlyphCompoundType(Glyph.TRINE, GLYPH_TYPES).setRegistryName(Astromancy.astromancy("trine")));
    public static final RegistryObject<ICompoundType> SEXTILE = TYPES.register("sextile", () -> new GlyphCompoundType(Glyph.SEXTILE, GLYPH_TYPES).setRegistryName(Astromancy.astromancy("sextile")));
    public static final RegistryObject<ICompoundType> SEMISEXTILE = TYPES.register("semisextile", () -> new GlyphCompoundType(Glyph.SEMISEXTILE, GLYPH_TYPES).setRegistryName(Astromancy.astromancy("semisextile")));
    public static final RegistryObject<ICompoundType> QUINTILE = TYPES.register("quintile", () -> new GlyphCompoundType(Glyph.QUINTILE, GLYPH_TYPES).setRegistryName(Astromancy.astromancy("quintile")));
    public static final RegistryObject<ICompoundType> QUINCUNX = TYPES.register("quincunx", () -> new GlyphCompoundType(Glyph.QUINCUNX, GLYPH_TYPES).setRegistryName(Astromancy.astromancy("quincunx")));
    public static final RegistryObject<ICompoundType> OCTILE = TYPES.register("octile", () -> new GlyphCompoundType(Glyph.OCTILE, GLYPH_TYPES).setRegistryName(Astromancy.astromancy("octile")));
    public static final RegistryObject<ICompoundType> TRIOCTILE = TYPES.register("trioctile", () -> new GlyphCompoundType(Glyph.TRIOCTILE, GLYPH_TYPES).setRegistryName(Astromancy.astromancy("trioctile")));
    public static final RegistryObject<ICompoundType> DECILE = TYPES.register("decile", () -> new GlyphCompoundType(Glyph.DECILE, GLYPH_TYPES).setRegistryName(Astromancy.astromancy("decile")));
    public static final RegistryObject<ICompoundType> SOL = TYPES.register("sol", () -> new GlyphCompoundType(Glyph.SOL, GLYPH_TYPES).setRegistryName(Astromancy.astromancy("sol")));
    public static final RegistryObject<ICompoundType> LUNA = TYPES.register("luna", () -> new GlyphCompoundType(Glyph.LUNA, GLYPH_TYPES).setRegistryName(Astromancy.astromancy("luna")));
    public static final RegistryObject<ICompoundType> MERCURIA = TYPES.register("mercuria", () -> new GlyphCompoundType(Glyph.MERCURIA, GLYPH_TYPES).setRegistryName(Astromancy.astromancy("mercuria")));
    public static final RegistryObject<ICompoundType> VENUTIO = TYPES.register("venutio", () -> new GlyphCompoundType(Glyph.VENUTIO, GLYPH_TYPES).setRegistryName(Astromancy.astromancy("venutio")));
    public static final RegistryObject<ICompoundType> MARTUS = TYPES.register("martus", () -> new GlyphCompoundType(Glyph.MARTUS, GLYPH_TYPES).setRegistryName(Astromancy.astromancy("martus")));
    public static final RegistryObject<ICompoundType> JUPILUS = TYPES.register("jupilus", () -> new GlyphCompoundType(Glyph.JUPILUS, GLYPH_TYPES).setRegistryName(Astromancy.astromancy("jupilus")));
    public static final RegistryObject<ICompoundType> SATURIS = TYPES.register("saturis", () -> new GlyphCompoundType(Glyph.SATURIS, GLYPH_TYPES).setRegistryName(Astromancy.astromancy("saturis")));
    public static final RegistryObject<ICompoundType> URANIA = TYPES.register("urania", () -> new GlyphCompoundType(Glyph.URANIA, GLYPH_TYPES).setRegistryName(Astromancy.astromancy("urania")));
    public static final RegistryObject<ICompoundType> NEPTURA = TYPES.register("neptura", () -> new GlyphCompoundType(Glyph.NEPTURA, GLYPH_TYPES).setRegistryName(Astromancy.astromancy("neptura")));
    public static final RegistryObject<ICompoundType> PLUTUS = TYPES.register("plutus", () -> new GlyphCompoundType(Glyph.PLUTUS, GLYPH_TYPES).setRegistryName(Astromancy.astromancy("plutus")));
    public static final RegistryObject<ICompoundType> CHIROS = TYPES.register("chiros", () -> new GlyphCompoundType(Glyph.CHIROS, GLYPH_TYPES).setRegistryName(Astromancy.astromancy("chiros")));
    public static final RegistryObject<ICompoundType> LILITHIA = TYPES.register("lilithia", () -> new GlyphCompoundType(Glyph.LILITHIA, GLYPH_TYPES).setRegistryName(Astromancy.astromancy("lilithia")));

    public static void register(){
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        TYPES.register(bus);
    }
}
