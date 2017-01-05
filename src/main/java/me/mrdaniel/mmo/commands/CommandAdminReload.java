package me.mrdaniel.mmo.commands;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.mmo.Main;

public class CommandAdminReload implements CommandExecutor {
	
	public CommandResult execute(CommandSource sender, CommandContext args) throws CommandException {

		sender.sendMessage(Main.getInstance().getConfig().PREFIX.concat(Text.of(TextColors.GOLD, "Reloading AdventureMMO")));

		Main.getInstance().onReload(null);

		sender.sendMessage(Main.getInstance().getConfig().PREFIX.concat(Text.of(TextColors.GOLD, "AdventureMMO reloaded succesfully")));
		return CommandResult.success();
	}
}