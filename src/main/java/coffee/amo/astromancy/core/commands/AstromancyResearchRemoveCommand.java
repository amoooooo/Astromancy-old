package coffee.amo.astromancy.core.commands;

import coffee.amo.astromancy.core.handlers.AstromancyPacketHandler;
import coffee.amo.astromancy.core.packets.ClientboundResearchRemovePacket;
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

public class AstromancyResearchRemoveCommand implements Command<CommandSourceStack> {
    private static final AstromancyResearchRemoveCommand CMD = new AstromancyResearchRemoveCommand();

    public static ArgumentBuilder<CommandSourceStack, ?> register(CommandDispatcher<CommandSourceStack> dispatcher) {
        LiteralArgumentBuilder<CommandSourceStack> builder = Commands.literal("remove").executes(CMD);
        builder.then(Commands.argument("remove", StringArgumentType.greedyString()).requires(command -> command.hasPermission(0)).executes(AstromancyResearchRemoveCommand::sendPacket));
        return builder;
    }

    public static int sendPacket(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        AstromancyPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(()->(ServerPlayer) context.getSource().getEntity()), new ClientboundResearchRemovePacket(StringArgumentType.getString(context, "remove")));
        return 1;
    }

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        AstromancyCommand.feedback(context.getSource(), "Research", "astromancy.research.success", AstromancyCommand.CommandFeedbackType.INFO);
        return 1;
    }
}
