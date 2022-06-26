package coffee.amo.astromancy.client.research;

import coffee.amo.astromancy.client.screen.stellalibri.BookEntry;
import coffee.amo.astromancy.client.screen.stellalibri.objects.EntryObject;
import coffee.amo.astromancy.client.screen.stellalibri.objects.ImportantEntryObject;
import coffee.amo.astromancy.client.screen.stellalibri.pages.ResearchPageRegistry;
import coffee.amo.astromancy.core.systems.research.ResearchObject;
import coffee.amo.astromancy.core.systems.research.ResearchProgress;
import coffee.amo.astromancy.core.systems.research.ResearchType;
import coffee.amo.astromancy.core.systems.research.ResearchTypeRegistry;

import java.util.ArrayList;
import java.util.List;

public class ClientResearchHolder {
    public static List<ResearchObject> research = new ArrayList<>();

    public static void addResearch(ResearchObject in){
        research.add(in);
    }

    public static void addResearch(String in){
        List<ResearchType> researchObjects = ResearchTypeRegistry.RESEARCH_TYPES.get().getValues().stream().toList();
        for (ResearchType type : researchObjects) {
            ResearchObject object = (ResearchObject) type;
            if (object.identifier.equals(in)) {
                object.locked = ResearchProgress.IN_PROGRESS;
                research.add(object);
                return;
            }
        }
    }

    public static void completeResearch(String in){
        List<ResearchType> researchObjects = ResearchTypeRegistry.RESEARCH_TYPES.get().getValues().stream().toList();
        for (ResearchType type : researchObjects) {
            ResearchObject object = (ResearchObject) type;
            if (object.identifier.equals(in)) {
                object.locked = ResearchProgress.COMPLETED;
                return;
            }
        }
    }

    public static List<ResearchObject> getResearch(){
        return research;
    }

    public boolean hasResearch(ResearchObject in){
        return research.contains(in);
    }

    public static void removeResearch(ResearchObject in){
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

    public ResearchObject getResearch(String in){
        for(ResearchObject r : research){
            if(r.getIdentifier().equals(in)){
                return r;
            }
        }
        return null;
    }

    public static boolean isResearchCompleted(String in){
        for(ResearchObject r : research){
            if(r.getIdentifier().equals(in) && r.locked.equals(ResearchProgress.COMPLETED)){
                return true;
            }
        }
        return false;
    }

    public static boolean contains(String in){
        for(ResearchObject r : research){
            if(r.getIdentifier().equals(in)){
                return true;
            }
        }
        return false;
    }

    public boolean contains(ResearchObject in){
        return research.contains(in);
    }

}
