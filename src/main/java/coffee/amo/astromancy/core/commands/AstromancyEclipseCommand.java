package coffee.amo.astromancy.core.commands;

import coffee.amo.astromancy.core.util.StarSavedData;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public class AstromancyEclipseCommand implements Command<CommandSourceStack> {
    private static final AstromancyEclipseCommand CMD = new AstromancyEclipseCommand();

    public static ArgumentBuilder<CommandSourceStack, ?> register(CommandDispatcher<CommandSourceStack> dispatcher){
        LiteralArgumentBuilder<CommandSourceStack> builder = Commands.literal("eclipse").executes(CMD);
        builder.then(Commands.argument("days", IntegerArgumentType.integer()).requires(command -> command.hasPermission(0)).executes(AstromancyEclipseCommand::execute));
        return builder;
    }

    public static int execute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        StarSavedData.get(context.getSource().getLevel().getServer()).setDaysTilEclipse(IntegerArgumentType.getInteger(context, "days"));
        return 1;
    }

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return 0;
    }
}
