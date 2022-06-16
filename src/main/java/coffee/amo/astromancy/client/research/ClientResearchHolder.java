package coffee.amo.astromancy.client.research;

import java.util.ArrayList;
import java.util.List;

public class ClientResearchHolder {
    public static List<String> research = new ArrayList<>();

    public static void addResearch(String in){
        research.add(in);
    }

    public static List<String> getResearch(){
        return research;
    }

    public boolean hasResearch(String in){
        return research.contains(in);
    }

    public static void removeResearch(String in){
        research.remove(in);
    }

    public void clearResearch(){
        research.clear();
    }

    public void addAllResearch(List<String> in){
        research.addAll(in);
    }

    public String getResearch(int in){
        return research.get(in);
    }
}
