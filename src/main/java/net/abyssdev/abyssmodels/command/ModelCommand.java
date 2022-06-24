package net.abyssdev.abyssmodels.command;

import net.abyssdev.abysslib.command.Command;
import net.abyssdev.abysslib.command.context.CommandContext;
import net.abyssdev.abyssmodels.AbyssModels;
import org.bukkit.entity.Player;

import java.util.Arrays;

public final class ModelCommand extends Command<Player> {

    private final AbyssModels plugin;

    public ModelCommand(final AbyssModels plugin) {
        super("models", "Base AbyssModels command", Arrays.asList(
                "model",
                "abyssmodels",
                "abyssmodel"), Player.class);

        this.plugin = plugin;
    }

    @Override
    public void execute(final CommandContext<Player> context) {
        this.plugin.getCategoryMenu().open(context.getSender());
    }

}