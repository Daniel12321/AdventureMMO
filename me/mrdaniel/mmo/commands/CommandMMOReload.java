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

import me.mrdaniel.mmo.io.Config;
import me.mrdaniel.mmo.io.ModdedBlocks;
import me.mrdaniel.mmo.io.players.MMOPlayerDatabase;
import me.mrdaniel.mmo.io.top.SkillTop;
import me.mrdaniel.mmo.utils.Permissions;

public class CommandMMOReload implements CommandCallable {
	
	public CommandResult process(CommandSource sender, String arguments) throws CommandException {
		
		if (!(sender.hasPermission(Permissions.MMO_RELOAD()))) { sender.sendMessage(Config.PREFIX().concat(Text.of(TextColors.RED, "You don't have permission for this command"))); return CommandResult.success(); }
		
		Config.setup();
		MMOPlayerDatabase.getInstance().writeAll();
		MMOPlayerDatabase.getInstance().players.clear();
		MMOPlayerDatabase.getInstance().setup();
		SkillTop.getInstance().setup();
		ModdedBlocks.setup();
		
		sender.sendMessage(Config.PREFIX().concat(Text.of(TextColors.GOLD, "SpongeMMO reloaded succesfully")));
		
		return CommandResult.success();
	}
	private final Text usage = Text.of(TextColors.BLUE, "Usage: /mmoreload");
	private final Text description = Text.of(TextColors.BLUE, "MMO | Reload Command");
	private List<String> suggestions = new ArrayList<String>();
	private String permission = "";
	
	public Text getUsage(CommandSource sender) { return usage; }
	public Optional<Text> getHelp(CommandSource sender) { return Optional.of(usage); }
	public Optional<Text> getShortDescription(CommandSource sender) { return Optional.of(description); }
	public List<String> getSuggestions(CommandSource sender, String arguments) throws CommandException { return suggestions; }
	public boolean testPermission(CommandSource sender) { return permission.equals("") ? true : sender.hasPermission(permission); }
}