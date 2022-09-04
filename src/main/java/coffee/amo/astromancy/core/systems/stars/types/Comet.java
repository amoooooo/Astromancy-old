package coffee.amo.astromancy.core.systems.stars.types;

import java.util.UUID;

public class Comet extends AstralObject {
    public Comet(String name) {
        super(name);
    }

    public Comet(String name, UUID uuid, int size) {
        super(name, uuid, size);
    }

    public Comet() {
        super();
    }
}
