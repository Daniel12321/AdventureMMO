package me.mrdaniel.mmo.commands;

import java.util.List;
import java.util.Optional;

import org.spongepowered.api.command.CommandCallable;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public class CommandSkills implements CommandCallable {
	
	
	public CommandResult process(CommandSource sender, String arguments) throws CommandException {
		
		if (arguments.equals("")) { CommandCenter.sendMainInfo(sender, arguments); }
		else { CommandCenter.sendSkillInfo(sender, arguments); }		
		return CommandResult.success();
	}
	private final Text usage = Text.of(TextColors.BLUE, "Usage: /skills [skill]");
	private final Text description = Text.of(TextColors.BLUE, "MMO | Skills Command");
	private String permission = "";
	
	public Text getUsage(CommandSource sender) { return usage; }
	public Optional<Text> getHelp(CommandSource sender) { return Optional.of(usage); }
	public Optional<Text> getShortDescription(CommandSource sender) { return Optional.of(description); }
	public List<String> getSuggestions(CommandSource sender, String arguments, Location<World> loc) throws CommandException { return getSuggestions(sender, arguments); }
	public List<String> getSuggestions(CommandSource sender, String arguments) throws CommandException { return CommandCenter.getSkillSuggesions(arguments); }
	public boolean testPermission(CommandSource sender) { return permission.equals("") ? true : sender.hasPermission(permission); }
}