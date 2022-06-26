package coffee.amo.astromancy.core.registration;

import coffee.amo.astromancy.core.systems.worldevent.LevelEventType;

import java.util.HashMap;

public class LevelEventTypeRegistry {

    public static HashMap<String, LevelEventType> EVENT_TYPES = new HashMap<>();

    public static LevelEventType registerEventType(LevelEventType eventType){
        EVENT_TYPES.put(eventType.id, eventType);
        return eventType;
    }
}
