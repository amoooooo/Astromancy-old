package coffee.amo.astromancy.core.packets;

public class SolarEclipsePacket {
    public boolean bool;
    public boolean initialized = true;

    public SolarEclipsePacket(boolean bool){
        this.bool = bool;
        initialized = true;
    }

    public SolarEclipsePacket(){
        initialized = false;
    }
}
