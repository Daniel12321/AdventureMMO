package me.mrdaniel.mmo.commands;

import java.util.List;
import java.util.Optional;

import org.spongepowered.api.command.CommandCallable;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import me.mrdaniel.mmo.enums.Setting;
import me.mrdaniel.mmo.io.players.MMOPlayer;
import me.mrdaniel.mmo.io.players.MMOPlayerDatabase;

public class CommandSettings  implements CommandCallable {
	
	public CommandResult process(CommandSource sender, String arguments) throws CommandException {
		
		if (!(sender instanceof Player)) { sender.sendMessage(Text.of(TextColors.RED, "This command is for players only")); return CommandResult.success(); }
		Player p = (Player) sender;
		MMOPlayer mmop = MMOPlayerDatabase.getInstance().getOrCreatePlayer(p.getUniqueId());
		
		if (arguments.equals("")) { ChatMenus.sendSettingsInfo(p, mmop); return CommandResult.success(); }
		String[] args = arguments.split(" ");
		if (args.length == 1) { ChatMenus.sendSettingsInfo(p, mmop); return CommandResult.success(); }
		
		try {
			boolean value = Boolean.valueOf(args[1]);
			mmop.getSettings().setSetting(Setting.match(args[0]), value);
			ChatMenus.sendSettingsInfo(p, mmop);
			return CommandResult.success();
		}
		catch (Exception exc) { ChatMenus.sendSettingsInfo(p, mmop); return CommandResult.success(); }
	}
	private final Text usage = Text.of(TextColors.BLUE, "Usage: /settings <setting> <value>");
	private final Text description = Text.of(TextColors.BLUE, "MMO | Settings Command");
	
	public Text getUsage(CommandSource sender) { return usage; }
	public Optional<Text> getHelp(CommandSource sender) { return Optional.of(usage); }
	public Optional<Text> getShortDescription(CommandSource sender) { return Optional.of(description); }
	public List<String> getSuggestions(CommandSource sender, String arguments, Location<World> loc) throws CommandException { return getSuggestions(sender, arguments); }
	public List<String> getSuggestions(CommandSource sender, String arguments) throws CommandException {  return CommandCenter.getSkillSuggesions(arguments); }
	public boolean testPermission(CommandSource sender) { return true; }
}