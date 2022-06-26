package coffee.amo.astromancy.core.commands;

import coffee.amo.astromancy.core.handlers.AstromancyPacketHandler;
import coffee.amo.astromancy.core.packets.ResearchPacket;
import coffee.amo.astromancy.core.systems.research.ResearchProgress;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.PacketDistributor;

public class AstromancyResearchCommand implements Command<CommandSourceStack> {
    private static final AstromancyResearchCommand CMD = new AstromancyResearchCommand();

    public static ArgumentBuilder<CommandSourceStack, ?> register(CommandDispatcher<CommandSourceStack> dispatcher) {
        LiteralArgumentBuilder<CommandSourceStack> builder = Commands.literal("research").executes(CMD);
        builder.then(Commands.argument("research", StringArgumentType.greedyString()).requires(command -> command.hasPermission(0)).executes(AstromancyResearchCommand::sendPacket));
        return builder;
    }

    public static int sendPacket(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        AstromancyPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(()->(ServerPlayer) context.getSource().getEntity()), new ResearchPacket(StringArgumentType.getString(context, "research"), false, true, ResearchProgress.COMPLETED.ordinal()));
        return 1;
    }

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        AstromancyCommand.feedback(context.getSource(), "Research", "astromancy.research.success", AstromancyCommand.CommandFeedbackType.INFO);
        return 1;
    }
}
