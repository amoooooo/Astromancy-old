package coffee.amo.astromancy.core.systems.stars.systems;

import coffee.amo.astromancy.core.systems.stars.classification.constellation.ConstellationInstance;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtIo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Universe {
    private List<Supercluster> superclusters;
    private List<ConstellationInstance> constellations = new ArrayList<>();

    public Universe(List<Supercluster> superclusters) {
        this.superclusters = superclusters;
    }

    public Universe() {
        this.superclusters = new ArrayList<>();
    }

    public List<Supercluster> getSuperclusters() {
        return superclusters;
    }

    public void setSuperclusters(List<Supercluster> superclusters) {
        this.superclusters = superclusters;
    }

    public List<ConstellationInstance> getConstellations() {
        return constellations;
    }

    public void setConstellations(List<ConstellationInstance> constellations) {
        this.constellations = constellations;
    }

    public static String generateUniverseName(){
        return "Universe";
    }

    public static void printLog(Universe universe) throws IOException {
        File log = new File("The Universe.nbt");
        NbtIo.write(universe.toNbt(), log);
    }

    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append("Universe: ");
        for(Supercluster supercluster : superclusters){
            builder.append(supercluster.toString());
        }
        for(ConstellationInstance constellation : constellations){
            builder.append(constellation.toString());
        }
        return builder.toString();
    }

    public CompoundTag toNbt(){
        CompoundTag tag = new CompoundTag();
        ListTag superclustersTag = new ListTag();
        for (Supercluster supercluster : superclusters) {
            superclustersTag.add(supercluster.toNbt());
        }
        tag.put("superclusters", superclustersTag);
        ListTag constellationsTag = new ListTag();
        for (ConstellationInstance constellation : constellations) {
            constellationsTag.add(constellation.toNbt());
        }
        tag.put("constellations", constellationsTag);
        return tag;
    }

    public static Universe fromNbt(CompoundTag tag){
        Universe universe = new Universe();
        ListTag superclustersTag = tag.getList("superclusters", 10);
        for (int i = 0; i < superclustersTag.size(); i++) {
            CompoundTag superclusterTag = superclustersTag.getCompound(i);
            universe.getSuperclusters().add(Supercluster.fromNbt(superclusterTag));
        }
        ListTag constellationsTag = tag.getList("constellations", 10);
        for (int i = 0; i < constellationsTag.size(); i++) {
            CompoundTag constellationTag = constellationsTag.getCompound(i);
            universe.getConstellations().add(ConstellationInstance.fromNbt(constellationTag));
        }
        return universe;
    }
}
