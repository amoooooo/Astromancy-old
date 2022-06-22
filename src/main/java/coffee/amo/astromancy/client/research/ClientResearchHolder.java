package coffee.amo.astromancy.client.research;

import coffee.amo.astromancy.core.systems.research.ResearchObject;
import coffee.amo.astromancy.core.systems.research.ResearchTabObject;

import java.util.ArrayList;
import java.util.List;

public class ClientResearchHolder {
    public static List<ResearchObject> research = new ArrayList<>();
    public static List<ResearchTabObject> tabs = new ArrayList<>();

    public static void addResearch(ResearchObject in){
        research.add(in);
    }

    public static List<ResearchObject> getResearch(){
        return research;
    }

    public boolean hasResearch(String in){
        return research.contains(in);
    }


    public static void rmeoveResearch(ResearchObject in){
        research.remove(in);
    }

    public void clearResearch(){
        research.clear();
    }

    public void addAllResearch(List<ResearchObject> in){
        research.addAll(in);
    }

    public ResearchObject getResearch(int in){
        return research.get(in);
    }

    public static boolean containsIdentifier(String in) {
        for (ResearchObject r : research) {
            if (r.identifier.equals(in)) {
                return true;
            }
        }
        return false;
    }

    public static ResearchObject getFromName(String in){
        for(ResearchObject r : research){
            if(r.identifier.equals(in)){
                return r;
            }
        }
        return null;
    }

    public static void addTab(ResearchTabObject in){
        tabs.add(in);
    }

    public static List<ResearchTabObject> getTabs(){
        return tabs;
    }

    public static void removeTab(String in){
        tabs.remove(in);
    }

    public static void clearTabs(){
        tabs.clear();
    }

    public static void addAllTabs(List<ResearchTabObject> in){
        tabs.addAll(in);
    }

    public static ResearchTabObject getTab(int in){
        return tabs.get(in);
    }

    public static boolean containsTab(String in) {
        for (ResearchTabObject r : tabs) {
            if (r.identifier.equals(in)) {
                return true;
            }
        }
        return false;
    }
}
