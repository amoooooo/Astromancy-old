package coffee.amo.astromancy.core.systems.stars.types;

import java.util.UUID;

public class AstralObject {
    private UUID uuid;
    private String name;

    public AstralObject(String name) {
        this.name = name;
        this.uuid = UUID.randomUUID();
    }

    public AstralObject(String name, UUID uuid) {
        this.name = name;
        this.uuid = uuid;
    }

    public AstralObject() {
        this.name = generateAstralObjectName();
        this.uuid = UUID.randomUUID();
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public static String generateAstralObjectName(){
        return "Astral Object";
    }
}
