package me.mrdaniel.mmo.commands;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;

import org.spongepowered.api.command.CommandCallable;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import me.mrdaniel.mmo.enums.SkillType;
import me.mrdaniel.mmo.io.top.SkillTop;
import me.mrdaniel.mmo.utils.TopInfo;

public class CommandTop  implements CommandCallable {
	
	public CommandResult process(CommandSource sender, String arguments) throws CommandException {
		
		if (arguments.contains(" ")) { sender.sendMessage(usage); return CommandResult.success(); }
		
		if (arguments.equals("")) { sendTop(sender, SkillTop.getInstance().getTop(null).getTop(), "Total"); return CommandResult.success(); }
		
		SkillType type = SkillType.match(arguments);
		if (type == null) { sendTop(sender, SkillTop.getInstance().getTop(null).getTop(), "Total");  return CommandResult.success();  }
		
		sendTop(sender, SkillTop.getInstance().getTop(type).getTop(), type.name);
		return CommandResult.success();
	}
	private final Text usage = Text.of(TextColors.BLUE, "Usage: /skilltop [stat]");
	private final Text description = Text.of(TextColors.BLUE, "MMO | SkillTop Command");
	private String permission = "";
	
	public Text getUsage(CommandSource sender) { return usage; }
	public Optional<Text> getHelp(CommandSource sender) { return Optional.of(usage); }
	public Optional<Text> getShortDescription(CommandSource sender) { return Optional.of(description); }
	public List<String> getSuggestions(CommandSource sender, String arguments, Location<World> loc) throws CommandException { return getSuggestions(sender, arguments); }
	public List<String> getSuggestions(CommandSource sender, String arguments) throws CommandException {  return CommandCenter.getSkillSuggesions(arguments); }
	public boolean testPermission(CommandSource sender) { return permission.equals("") ? true : sender.hasPermission(permission); }
	
	private void sendTop(CommandSource sender, TreeMap<Integer, TopInfo> top, String title) {
		sender.sendMessage(Text.of(""));
		sender.sendMessage(Text.of(TextColors.RED, "--=== ", TextColors.AQUA, title, " SkillTop", TextColors.RED, " ==---"));
		Set<Integer> keys = top.keySet();
		for (int key : keys) {
			TopInfo TopInfo = top.get(key);
			sender.sendMessage(Text.of(TextColors.RED, String.valueOf(key), ": ", TextColors.AQUA, TopInfo.name, TextColors.GRAY, " - ", TextColors.GREEN, "Level ", TopInfo.level));
		}
	}
}