package coffee.amo.astromancy.datagen;

import coffee.amo.astromancy.Astromancy;
import coffee.amo.astromancy.core.registration.AspectiRegistry;
import com.ldtteam.aequivaleo.api.compound.CompoundInstance;
import com.ldtteam.aequivaleo.api.compound.information.datagen.ForcedInformationProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.Arrays;
import java.util.LinkedHashSet;

public class AequivaleoInformationProvider extends ForcedInformationProvider {
    AequivaleoInformationProvider(final DataGenerator dataGenerator) {
        super(Astromancy.MODID, dataGenerator);
    }

    @Override
    public void calculateDataToSave() {
        saveNatural();
        saveAquatic();
        saveRocks();
        saveDirt();
        saveMostFoods(trine(1), opposition(1), quincunx(1));
        saveMisc();
        saveNether();
        saveEnd();

        saveData(Items.WATER_BUCKET, trine(4));
        saveData(Items.LAVA_BUCKET, trine(4));

        saveData(Items.NETHER_STAR, opposition(8), trioctile(8), decile(8));
        saveData(Items.HEART_OF_THE_SEA, opposition(8), trioctile(8), decile(8));
    }

    private void saveMisc(){
        saveData(Items.GLASS, square(1));
        saveData(Items.CHARCOAL, square(1));
        saveData(Items.BRICK, square(1));
        saveData(Items.POISONOUS_POTATO, square(1));
        saveData(Items.CLAY_BALL, square(1));
        saveData(Items.FEATHER, square(1));
        saveData(Items.BROWN_MUSHROOM_BLOCK, square(1));
        saveData(Items.RED_MUSHROOM_BLOCK, square(1));
        saveData(Items.COBWEB, square(1));
        saveData(Items.PUMPKIN, square(1));
        saveData(Items.CARVED_PUMPKIN, square(1));
        saveData(Items.BELL, square(1));
        saveData(Items.GUNPOWDER, square(1));
        saveData(Items.HONEYCOMB, square(1));
        saveData(Items.HONEY_BLOCK, square(1));
        saveData(Items.ICE, square(1));
        saveData(Items.MAGMA_BLOCK, square(1));
        saveData(Items.OBSIDIAN, square(1));
        saveData(Items.PHANTOM_MEMBRANE, square(1));
        saveData(Items.RABBIT_FOOT, square(1));
        saveData(Items.ROTTEN_FLESH, square(1));
        saveData(Items.SLIME_BALL, square(1));
        saveData(Items.SNOWBALL, square(1));
        saveData(Items.SPIDER_EYE, square(1));
    }

    private void saveEnd(){
        saveData(Items.CHORUS_FLOWER, square(1), quincunx(1), trioctile(1), decile(1));
        saveData(Items.CHORUS_FRUIT, square(1), quincunx(1), trioctile(1), decile(1));
        saveData(Items.POPPED_CHORUS_FRUIT, square(1), quincunx(1), trioctile(1), decile(1));
        saveData(Items.END_ROD, square(1), quincunx(1), trioctile(1), decile(1));
        saveData(Items.END_STONE, square(1), quincunx(1), trioctile(1), decile(1));
        saveData(Items.ENDER_PEARL, square(1), quincunx(1), trioctile(1), decile(1));
        saveData(Items.SHULKER_SHELL, square(1), quincunx(1), trioctile(1), decile(1));
    }

    private void saveNether(){
        saveData(Items.NETHER_WART, conjunction(2), sextile(2), quincunx(1), trioctile(1));
        saveData(Items.WARPED_WART_BLOCK, conjunction(2), sextile(2), quincunx(1), octile(1));
        saveData(Items.NETHERRACK, conjunction(1), trioctile(1));
        saveData(Items.CRIMSON_NYLIUM, conjunction(1), trioctile(1));
        saveData(Items.WARPED_NYLIUM, conjunction(1), trioctile(1));
        saveData(Items.CRYING_OBSIDIAN, square(1), trioctile(1), decile(8));
        saveData(Items.GHAST_TEAR, conjunction(2), sextile(2), quincunx(1), decile(6));
        saveData(Items.GLOWSTONE_DUST, conjunction(2), sextile(2), trioctile(2), decile(4));
        saveData(Items.SHROOMLIGHT, conjunction(2), sextile(2), quincunx(1), decile(4));
        saveData(Items.SOUL_SAND, conjunction(2), sextile(2), trioctile(1), decile(4));
        saveData(Items.SOUL_SOIL, conjunction(2), sextile(2), trioctile(1), decile(4));
        saveData(Items.BASALT, conjunction(2), sextile(2), trioctile(1), decile(4));
        saveData(Items.BLAZE_ROD, conjunction(2), sextile(2), trioctile(1), decile(4));
    }

    private void saveRocks(){
        saveData(Items.COAL_ORE, conjunction(2), sextile(1), trioctile(4), quintile(2));
        saveData(Items.DEEPSLATE_COAL_ORE, conjunction(2), sextile(1), trioctile(4), quintile(2));
        saveData(Items.DEEPSLATE_IRON_ORE, conjunction(2), sextile(1), trioctile(4), quintile(2));
        saveData(Items.DEEPSLATE_GOLD_ORE, conjunction(2), sextile(1), trioctile(4), quintile(2));
        saveData(Items.DEEPSLATE_REDSTONE_ORE, conjunction(2), sextile(1), trioctile(4), quintile(2));
        saveData(Items.DEEPSLATE_LAPIS_ORE, conjunction(2), sextile(1), trioctile(4), quintile(2));
        saveData(Items.DEEPSLATE_DIAMOND_ORE, conjunction(2), sextile(1), trioctile(4), quintile(2));
        saveData(Items.DEEPSLATE_EMERALD_ORE, conjunction(2), sextile(1), trioctile(4), quintile(2));
        saveData(Items.IRON_ORE, conjunction(2), sextile(2), trioctile(4), quintile(2));
        saveData(Items.GOLD_ORE, conjunction(2), sextile(2), trioctile(8), quintile(2));
        saveData(Items.DIAMOND_ORE, conjunction(2), sextile(2), trioctile(12), quintile(2));
        saveData(Items.EMERALD_ORE, conjunction(2), sextile(2), trioctile(8), quintile(2));
        saveData(Items.LAPIS_ORE, conjunction(2), sextile(2), trioctile(6), quintile(2));
        saveData(Items.REDSTONE_ORE, conjunction(2), sextile(2), trioctile(8), quintile(2));
        saveData(Items.NETHER_QUARTZ_ORE, conjunction(2), sextile(2), trioctile(6), quintile(2));
        saveData(Items.NETHER_GOLD_ORE, conjunction(2), sextile(2), trioctile(8), quintile(2));
        saveData(Items.GILDED_BLACKSTONE, conjunction(2), sextile(2), trioctile(8), quintile(2));
        saveData(Items.COAL, conjunction(4), sextile(4), trioctile(2), decile(1));
        saveData(Items.IRON_INGOT, conjunction(4), sextile(4), trioctile(2), decile(2));
        saveData(Items.GOLD_INGOT, conjunction(4), sextile(4), trioctile(2), decile(4));
        saveData(Items.DIAMOND, conjunction(4), sextile(4), trioctile(2), decile(8));
        saveData(Items.EMERALD, conjunction(4), sextile(4), trioctile(2), decile(6));
        saveData(Items.LAPIS_LAZULI, conjunction(4), sextile(4), trioctile(2), decile(6));
        saveData(Items.REDSTONE, conjunction(4), sextile(4), trioctile(2), decile(6));
        saveData(Items.QUARTZ, conjunction(4), sextile(4), trioctile(2), decile(6));
        saveData(Items.ANCIENT_DEBRIS, conjunction(1), sextile(4), trioctile(16), quintile(4));
        saveData(Items.NETHERITE_SCRAP, conjunction(1), sextile(2), trioctile(8), quintile(4));
    }

    private void saveDirt(){
        saveTerrestrialBlocks(conjunction(1), sextile(1));
        saveData(Items.MOSSY_COBBLESTONE, conjunction(1), quincunx(1), sextile(1));
        saveData(Items.PODZOL, opposition(1), quincunx(1), quintile(1));
        saveData(Items.GRASS_BLOCK, conjunction(1), sextile(1), quincunx(1));
        saveData(Items.MYCELIUM, trioctile(2), quincunx(1), opposition(1));
    }
    
    private void saveAquatic(){
        saveCoral(opposition(1), trioctile(1));
        saveCoralBlocks(opposition(1), trioctile(2), quintile(1));

        saveData(Items.PRISMARINE_CRYSTALS, conjunction(2), trioctile(1), decile(1), sextile(1));
        saveData(Items.PRISMARINE_SHARD, conjunction(4), trioctile(2), decile(1), sextile(1));
        saveData(Items.SCUTE, trine(2), square(1), decile(1));
        saveData(Items.SEA_PICKLE, quincunx(1), decile(1), octile(1));
        saveData(Items.SEAGRASS, quincunx(1), decile(1), octile(1));
        saveData(Items.TURTLE_EGG, quincunx(1), decile(2), octile(1), square(1));
        saveData(Items.INK_SAC, trine(1), decile(2), octile(1), square(1));
        saveData(Items.GLOW_INK_SAC, trine(1), decile(2), octile(1), square(1), trioctile(2));
        saveData(Items.WET_SPONGE, trine(1), quintile(2), sextile(2));
        saveData(Items.NAUTILUS_SHELL, trine(1), decile(2), octile(1), square(1), trioctile(2));
        saveMostFish(quincunx(4), decile(1), octile(1), square(1), trioctile(2));
    }

    private void saveNatural(){
        saveLeavesAndPlants(conjunction(1));
        saveTallPlants(opposition(1), quintile(1));
        saveLogsAndSaplings(conjunction(1), sextile(4), quintile(2));
        saveFlowersAndDye(square(1), sextile(1), semisextile(1));
        saveDoubleFlowers(square(1), sextile(1), semisextile(1));
        saveData(Items.BAMBOO, square(1), sextile(1), semisextile(1));
        saveData(Items.BONE, conjunction(1), sextile(1), semisextile(1));
        saveData(Items.STRING, conjunction(1), sextile(2), semisextile(1));

    }

    private void saveMostFish(CompoundInstance... instances) {
        saveData(Items.SALMON, instances);
        saveData(Items.COD, instances);
        saveData(Items.PUFFERFISH, instances);
        saveData(Items.TROPICAL_FISH, instances);
    }

    private void saveMostFoods(CompoundInstance... instances) {
        saveData(Items.APPLE, instances);
        saveData(Items.BEEF, instances);
        saveData(Items.BEETROOT_SEEDS, instances);
        saveData(Items.CRIMSON_FUNGUS, instances);
        saveData(Items.EGG, instances);
        saveData(Items.CARROT, instances);
        saveData(Items.CHICKEN, instances);
        saveData(Items.MELON_SLICE, instances);
        saveData(Items.MUTTON, instances);
        saveData(Items.RABBIT, instances);
        saveData(Items.SUGAR_CANE, instances);
        saveData(Items.WARPED_FUNGUS, instances);
        saveData(Items.WHEAT, instances);
        saveData(Items.WHEAT_SEEDS, instances);
        saveData(Items.MILK_BUCKET, instances);
        saveData(Items.PORKCHOP, instances);
        saveData(Items.POTATO, instances);
        saveData(Items.RABBIT_HIDE, instances);
        saveData(Items.RED_MUSHROOM, instances);
        saveData(Items.BROWN_MUSHROOM, instances);
    }

    private void saveTerrestrialBlocks(CompoundInstance... earth) {
        saveData(Items.BLACKSTONE, earth);
        saveData(Items.COBBLESTONE, earth);
        saveData(Items.DIRT, earth);
        saveData(Items.FLINT, earth);
        saveData(Items.GRAVEL, earth);
        saveData(Items.RED_SAND, earth);
        saveData(Items.SAND, earth);
    }

    private void saveTallPlants(CompoundInstance... instances) {

        saveData(Items.LARGE_FERN, instances);
        saveData(Items.TALL_GRASS, instances);
    }

    private void saveCoral(CompoundInstance... instances) {
        saveData(Items.BRAIN_CORAL, instances);
        saveData(Items.BUBBLE_CORAL, instances);
        saveData(Items.DEAD_BRAIN_CORAL, instances);
        saveData(Items.DEAD_BUBBLE_CORAL, instances);
        saveData(Items.DEAD_FIRE_CORAL, instances);
        saveData(Items.DEAD_HORN_CORAL, instances);
        saveData(Items.DEAD_TUBE_CORAL, instances);
        saveData(Items.FIRE_CORAL, instances);
        saveData(Items.HORN_CORAL, instances);
        saveData(Items.TUBE_CORAL, instances);
        saveData(Items.BRAIN_CORAL_FAN, instances);
        saveData(Items.BUBBLE_CORAL_FAN, instances);
        saveData(Items.DEAD_BRAIN_CORAL_FAN, instances);
        saveData(Items.DEAD_BUBBLE_CORAL_FAN, instances);
        saveData(Items.DEAD_FIRE_CORAL_FAN, instances);
        saveData(Items.DEAD_HORN_CORAL_FAN, instances);
        saveData(Items.DEAD_TUBE_CORAL_FAN, instances);
        saveData(Items.FIRE_CORAL_FAN, instances);
        saveData(Items.HORN_CORAL_FAN, instances);
        saveData(Items.TUBE_CORAL_FAN, instances);
    }

    private void saveCoralBlocks(CompoundInstance... instances) {
        saveData(Items.BRAIN_CORAL_BLOCK, instances);
        saveData(Items.BUBBLE_CORAL_BLOCK, instances);
        saveData(Items.DEAD_BRAIN_CORAL_BLOCK, instances);
        saveData(Items.DEAD_BUBBLE_CORAL_BLOCK, instances);
        saveData(Items.DEAD_FIRE_CORAL_BLOCK, instances);
        saveData(Items.DEAD_HORN_CORAL_BLOCK, instances);
        saveData(Items.DEAD_TUBE_CORAL_BLOCK, instances);
        saveData(Items.FIRE_CORAL_BLOCK, instances);
        saveData(Items.HORN_CORAL_BLOCK, instances);
        saveData(Items.TUBE_CORAL_BLOCK, instances);
    }

    private void saveDoubleFlowers(CompoundInstance... instances) {
        saveData(Items.LILAC, instances);
        saveData(Items.PEONY, instances);
        saveData(Items.ROSE_BUSH, instances);
        saveData(Items.SUNFLOWER, instances);
    }

    private void saveFlowersAndDye(CompoundInstance... instances) {

        saveData(Items.ALLIUM, instances);
        saveData(Items.AZURE_BLUET, instances);
        saveData(Items.BLUE_ORCHID, instances);
        saveData(Items.COCOA_BEANS, instances);
        saveData(Items.CORNFLOWER, instances);
        saveData(Items.DANDELION, instances);
        saveData(Items.LILY_OF_THE_VALLEY, instances);
        saveData(Items.ORANGE_TULIP, instances);
        saveData(Items.OXEYE_DAISY, instances);
        saveData(Items.PINK_TULIP, instances);
        saveData(Items.POPPY, instances);
        saveData(Items.RED_TULIP, instances);
        saveData(Items.WHITE_TULIP, instances);
        saveData(Items.BLACK_DYE, instances);
        saveData(Items.BLUE_DYE, instances);
        saveData(Items.BROWN_DYE, instances);
        saveData(Items.CYAN_DYE, instances);
        saveData(Items.GRAY_DYE, instances);
        saveData(Items.GREEN_DYE, instances);
        saveData(Items.LIGHT_BLUE_DYE, instances);
        saveData(Items.LIGHT_GRAY_DYE, instances);
        saveData(Items.LIME_DYE, instances);
        saveData(Items.MAGENTA_DYE, instances);
        saveData(Items.ORANGE_DYE, instances);
        saveData(Items.PINK_DYE, instances);
        saveData(Items.PURPLE_DYE, instances);
        saveData(Items.RED_DYE, instances);
        saveData(Items.WHITE_DYE, instances);
        saveData(Items.YELLOW_DYE, instances);

        // weird ones, not flowers, but still dyes
        saveData(Items.BEETROOT, instances);
        saveData(Items.CACTUS, instances);

        // weirder still, making wool equal to dyes
        saveData(Items.WHITE_WOOL, instances);
        saveData(Items.RED_WOOL, instances);
        saveData(Items.ORANGE_WOOL, instances);
        saveData(Items.YELLOW_WOOL, instances);
        saveData(Items.GREEN_WOOL, instances);
        saveData(Items.BLUE_WOOL, instances);
        saveData(Items.PURPLE_WOOL, instances);
        saveData(Items.PINK_WOOL, instances);
        saveData(Items.LIME_WOOL, instances);
        saveData(Items.CYAN_WOOL, instances);
        saveData(Items.GRAY_WOOL, instances);
        saveData(Items.LIGHT_GRAY_WOOL, instances);
        saveData(Items.BLACK_WOOL, instances);
        saveData(Items.BROWN_WOOL, instances);
        saveData(Items.LIGHT_BLUE_WOOL, instances);
        saveData(Items.MAGENTA_WOOL, instances);
    }

    private void saveLogsAndSaplings(CompoundInstance... instances) {
        saveData(Items.ACACIA_LOG, instances);
        saveData(Items.BIRCH_LOG, instances);
        saveData(Items.DARK_OAK_LOG, instances);
        saveData(Items.JUNGLE_LOG, instances);
        saveData(Items.OAK_LOG, instances);
        saveData(Items.SPRUCE_LOG, instances);
        saveData(Items.CRIMSON_STEM, instances);
        saveData(Items.WARPED_STEM, instances);
        saveData(Items.STRIPPED_ACACIA_LOG, instances);
        saveData(Items.STRIPPED_BIRCH_LOG, instances);
        saveData(Items.STRIPPED_DARK_OAK_LOG, instances);
        saveData(Items.STRIPPED_JUNGLE_LOG, instances);
        saveData(Items.STRIPPED_OAK_LOG, instances);
        saveData(Items.STRIPPED_SPRUCE_LOG, instances);
        saveData(Items.STRIPPED_CRIMSON_STEM, instances);
        saveData(Items.STRIPPED_WARPED_STEM, instances);
        saveData(Items.ACACIA_SAPLING, instances);
        saveData(Items.BIRCH_SAPLING, instances);
        saveData(Items.DARK_OAK_SAPLING, instances);
        saveData(Items.JUNGLE_SAPLING, instances);
        saveData(Items.OAK_SAPLING, instances);
        saveData(Items.SPRUCE_SAPLING, instances);
    }

    private void saveLeavesAndPlants(CompoundInstance... instances){
        saveData(Items.ACACIA_LEAVES, instances);
        saveData(Items.BIRCH_LEAVES, instances);
        saveData(Items.DARK_OAK_LEAVES, instances);
        saveData(Items.JUNGLE_LEAVES, instances);
        saveData(Items.OAK_LEAVES, instances);
        saveData(Items.SPRUCE_LEAVES, instances);

        saveData(Items.DEAD_BUSH, instances);
        saveData(Items.FERN, instances);
        saveData(Items.GRASS, instances);
        saveData(Items.CRIMSON_ROOTS, instances);
        saveData(Items.WARPED_ROOTS, instances);
        saveData(Items.NETHER_SPROUTS, instances);

        saveData(Items.KELP, instances);
        saveData(Items.LILY_PAD, instances);

        saveData(Items.SWEET_BERRIES, instances);
        saveData(Items.TWISTING_VINES, instances);
        saveData(Items.VINE, instances);
        saveData(Items.WEEPING_VINES, instances);
        saveData(Items.MUSHROOM_STEM, instances);
    }

    private void saveData(Item item, CompoundInstance... instances){
        saveData(newLinkedHashSet(item, new ItemStack(item)), instances);
    }

    private LinkedHashSet<Object> newLinkedHashSet(final Object... internal){
        return new LinkedHashSet<>(Arrays.asList(internal));
    }

    private void saveData(LinkedHashSet<Object> items, CompoundInstance... instances){
        save(specFor(items).withCompounds(instances));
    }

    private static CompoundInstance conjunction(double d) { return new CompoundInstance(AspectiRegistry.CONJUNCTION.get(), d); }
    private static CompoundInstance opposition(double d) { return new CompoundInstance(AspectiRegistry.OPPOSITION.get(), d); }
    private static CompoundInstance square(double d) { return new CompoundInstance(AspectiRegistry.SQUARE.get(), d); }
    private static CompoundInstance trine(double d) { return new CompoundInstance(AspectiRegistry.TRINE.get(), d); }
    private static CompoundInstance sextile(double d) { return new CompoundInstance(AspectiRegistry.SEXTILE.get(), d); }
    private static CompoundInstance semisextile(double d) { return new CompoundInstance(AspectiRegistry.SEMISEXTILE.get(), d); }
    private static CompoundInstance quintile(double d) { return new CompoundInstance(AspectiRegistry.QUINTILE.get(), d); }
    private static CompoundInstance quincunx(double d) { return new CompoundInstance(AspectiRegistry.QUINCUNX.get(), d); }
    private static CompoundInstance octile(double d) { return new CompoundInstance(AspectiRegistry.OCTILE.get(), d); }
    private static CompoundInstance trioctile(double d) { return new CompoundInstance(AspectiRegistry.TRIOCTILE.get(), d); }
    private static CompoundInstance decile(double d) { return new CompoundInstance(AspectiRegistry.DECILE.get(), d); }
}
