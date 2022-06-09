package coffee.amo.astromancy.core.packets;

import coffee.amo.astromancy.core.systems.aspecti.Aspecti;
import net.minecraft.core.BlockPos;

import java.util.Map;

public class ArmillarySpherePacket {

    public final BlockPos pos;
    public final Map<Aspecti, Integer> aspecti;
    public final int size;

    public ArmillarySpherePacket(BlockPos pos, Map<Aspecti, Integer> aspecti) {
        this.pos = pos;
        this.aspecti = aspecti;
        this.size = aspecti.size();
    }
}
