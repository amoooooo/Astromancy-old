package coffee.amo.astromancy.core.systems.research;

import coffee.amo.astromancy.Astromancy;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.*;

public class ResearchObject extends ResearchType {
    public final String identifier;
    public List<ResearchObject> children = new ArrayList<>();
    public List<ResearchObject> unlocks = new ArrayList<>();
    public ResearchProgress locked = ResearchProgress.UNAVAILABLE;
    public ItemStack icon = ItemStack.EMPTY;
    public List<ItemStack> itemRequirements = new ArrayList<>();
    public float x;
    public float y;
    public String type;


    public ResearchObject(String identifier, float x, float y, String type) {
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

    public ResearchObject addItemRequirement(ItemStack item){
        itemRequirements.add(item);
        return this;
    }

    public List<ResearchObject> getChildren(){
        return children;
    }

    public String getIdentifier(){
        return identifier;
    }

    public ResearchObject addUnlock(ResearchObject research){
        unlocks.add(research);
        return this;
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
        tag.putFloat("x", x);
        tag.putFloat("y", y);
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
        ListTag requirementsTag = new ListTag();
        if(itemRequirements.size() > 0){
            for(ItemStack item : itemRequirements){
                CompoundTag itemTag = new CompoundTag();
                item.save(itemTag);
                requirementsTag.add(itemTag);
            }
        }
        tag.put("requirements", requirementsTag);
        return tag;
    }

    //fromNbt
    public static ResearchObject fromNBT(CompoundTag tag) {
        ResearchObject research = new ResearchObject(tag.getString("identifier"), tag.getFloat("x"), tag.getFloat("y"), tag.getString("type"));
        research.icon = ItemStack.of(tag.getCompound("icon"));
        research.locked = ResearchProgress.values()[tag.getInt("progress")];
        ListTag childrenTag = tag.getList("children", Tag.TAG_LIST);
        for (int i = 0; i < childrenTag.size(); i++) {
            CompoundTag childTag = childrenTag.getCompound(i);
            research.children.add(fromNBT(childTag));
        }
        ListTag requirementsTag = tag.getList("requirements", Tag.TAG_LIST);
        for (int i = 0; i < requirementsTag.size(); i++) {
            CompoundTag requirementTag = requirementsTag.getCompound(i);
            research.itemRequirements.add(ItemStack.of(requirementTag));
        }
        return research;
    }
}
