package coffee.amo.astromancy.core.systems.research;

import coffee.amo.astromancy.Astromancy;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ResearchTabObject extends ResearchTabType{
    public final String identifier;
    public List<ResearchObject> children = new ArrayList<>();
    public ItemStack icon = ItemStack.EMPTY;
    public ResourceLocation backgroundLocation;
    public List<ResourceLocation> backgroundLocations;
    public int x;
    public int y;
    public Color tabColor;

    public ResearchTabObject(String identifier, int x, int y, ResourceLocation backgroundLocation, ItemStack stack) {
        super(Astromancy.astromancy(identifier));
        this.identifier = identifier;
        this.x = x;
        this.y = y;
        this.backgroundLocation = backgroundLocation;
        this.icon = stack;
    }

    public ResearchTabObject(String identifier, int x, int y, List<ResourceLocation> backgroundLocations, ItemStack stack) {
        super(Astromancy.astromancy(identifier));
        this.identifier = identifier;
        this.x = x;
        this.y = y;
        this.backgroundLocations = backgroundLocations;
        this.icon = stack;
    }

    public ResearchTabObject addChild(ResearchObject child){
        children.add(child);
        return this;
    }

    public ResearchTabObject addChildren(ResearchObject... children){
        this.children.addAll(Arrays.asList(children));
        return this;
    }

    public List<ResearchObject> getChildren(){
        return children;
    }

    public String getIdentifier(){
        return identifier;
    }

    public ItemStack getIcon(){
        return icon;
    }

    public ResearchTabObject setColor(Color color){
        tabColor = color;
        return this;
    }

    public void setIcon(ItemStack icon){
        this.icon = icon;
    }

    @Override
    public ResourceLocation getTabName() {
        return Astromancy.astromancy(identifier);
    }
}
