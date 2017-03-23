package me.mrdaniel.adventuremmo.commands;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;

import me.mrdaniel.adventuremmo.AdventureMMO;
import me.mrdaniel.adventuremmo.MMOObject;

public class CommandReload extends MMOObject implements CommandExecutor {

	public CommandReload(@Nonnull final AdventureMMO mmo) {
		super(mmo);
	}

	@Override
	public CommandResult execute(final CommandSource src, final CommandContext args) throws CommandException {
		super.getMMO().onReload(null);
		super.getMMO().getMessages().sendReload(src);
		return CommandResult.success();
	}
}