package coffee.amo.astromancy.core.systems.research;

import coffee.amo.astromancy.Astromancy;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ResearchObject extends ResearchType {
    public final String identifier;
    public List<ResearchObject> children = new ArrayList<>();
    public List<ResearchObject> parents = new ArrayList<>();
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
        child.parents.add(this);
        return this;
    }

    public ResearchObject addChildren(ResearchObject... children){
        this.children.addAll(Arrays.asList(children));
        for(ResearchObject child : children){
            child.parents.add(this);
        }
        return this;
    }

    public ResearchObject setParent(List<ResearchObject> parent){
        this.parents = parent;
        return this;
    }

    public List<ResearchObject> getParent(){
        return parents;
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
}
