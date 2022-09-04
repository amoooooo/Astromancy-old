package coffee.amo.astromancy.core.systems.stars.types;

import net.minecraft.world.phys.Vec3;

import java.util.UUID;

public class Asteroid extends AstralObject {

    public Asteroid(String name) {
        super(name);
    }

    public Asteroid(String name, UUID uuid, int size) {
        super(name, uuid, size);
    }

    public Asteroid() {
        super();
    }
}
