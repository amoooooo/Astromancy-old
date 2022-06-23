package coffee.amo.astromancy.core.systems.research;

import coffee.amo.astromancy.Astromancy;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ResearchObject extends ResearchType {
    public final String identifier;
    public List<ResearchObject> children = new ArrayList<>();
    public ResearchProgress locked = ResearchProgress.LOCKED;
    public ItemStack icon = ItemStack.EMPTY;
    public int x;
    public int y;
    public String type;


    public ResearchObject(String identifier, int x, int y, String type) {
        super(Astromancy.astromancy(identifier));
        this.identifier = identifier;
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public ResearchObject addChild(ResearchObject child){
        children.add(child);
        return this;
    }

    public ResearchObject addChildren(ResearchObject... children){
        this.children.addAll(Arrays.asList(children));
        return this;
    }

    public List<ResearchObject> getChildren(){
        return children;
    }

    public String getIdentifier(){
        return identifier;
    }

    @Override
    public ResourceLocation getResearchName() {
        return Astromancy.astromancy(identifier);
    }

    public ResearchObject setIcon(ItemStack icon){
        this.icon = icon;
        return this;
    }

    public ItemStack getIcon(){
        return icon;
    }

    //toNbt
    public CompoundTag toNBT(CompoundTag tag) {
        tag.putString("identifier", identifier);
        tag.putString("type", type);
        tag.putInt("x", x);
        tag.putInt("y", y);
        CompoundTag iconTag = new CompoundTag();
        icon.save(iconTag);
        tag.put("icon", iconTag);
        tag.putInt("progress", locked.ordinal());
        ListTag childrenTag = new ListTag();
        if(children.size() > 0){
            for(ResearchObject child : children){
                childrenTag.add(child.toNBT(new CompoundTag()));
            }
        }
        tag.put("children", childrenTag);
        ListTag parentsTag = new ListTag();
        tag.put("parents", parentsTag);
        return tag;
    }

    //fromNbt
    public static ResearchObject fromNBT(CompoundTag tag) {
        ResearchObject research = new ResearchObject(tag.getString("identifier"), tag.getInt("x"), tag.getInt("y"), tag.getString("type"));
        research.icon = ItemStack.of(tag.getCompound("icon"));
        research.locked = ResearchProgress.values()[tag.getInt("progress")];
        ListTag childrenTag = tag.getList("children", Tag.TAG_LIST);
        for (int i = 0; i < childrenTag.size(); i++) {
            CompoundTag childTag = childrenTag.getCompound(i);
            research.children.add(fromNBT(childTag));
        }
        return research;
    }
}
