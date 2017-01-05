package me.mrdaniel.mmo.commands;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class CommandAdmin implements CommandExecutor {
	
	public CommandResult execute(CommandSource sender, CommandContext args) throws CommandException {
		
		if (!(sender instanceof Player)) { sender.sendMessage(Text.of(TextColors.RED, "This command is for players only")); return CommandResult.success(); }
		Player p = (Player) sender;

		CommandCenter.getInstance().sendAdmin(p);
		return CommandResult.success();
	}
}