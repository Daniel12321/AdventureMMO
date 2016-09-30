package me.mrdaniel.mmo.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.spongepowered.api.command.CommandCallable;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.mmo.commands.CommandCenter;

public class CommandShell implements CommandCallable {
	
	private String skillName;
	
	public CommandShell(String skillName) {
		this.skillName = skillName;
	}

	public CommandResult process(CommandSource sender, String arguments) throws CommandException {
		CommandCenter.sendSkillInfo(sender, skillName + " " + arguments);
		return CommandResult.success();
	}
	private final Text usage = Text.of(TextColors.BLUE, "Usage: /skills [skill]");
	private final Text description = Text.of(TextColors.BLUE, "MMO | Skills Command");
	private List<String> suggestions = new ArrayList<String>();
	private String permission = "";
	
	public Text getUsage(CommandSource sender) { return usage; }
	public Optional<Text> getHelp(CommandSource sender) { return Optional.of(usage); }
	public Optional<Text> getShortDescription(CommandSource sender) { return Optional.of(description); }
	public List<String> getSuggestions(CommandSource sender, String arguments) throws CommandException { return suggestions; }
	public boolean testPermission(CommandSource sender) { return permission.equals("") ? true : sender.hasPermission(permission); }
}