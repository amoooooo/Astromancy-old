package coffee.amo.astromancy.core.systems.stars.types;

import java.util.UUID;

public class Meteor extends AstralObject {
    public Meteor(String name) {
        super(name);
    }

    public Meteor(String name, UUID uuid, int size) {
        super(name, uuid, size);
    }

    public Meteor() {
        super();
    }
}
