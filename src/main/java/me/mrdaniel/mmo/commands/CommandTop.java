package me.mrdaniel.mmo.commands;

import java.util.List;
import java.util.Optional;
import java.util.TreeMap;

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
import me.mrdaniel.mmo.enums.SkillType;
import me.mrdaniel.mmo.io.players.MMOPlayer;
import me.mrdaniel.mmo.io.players.MMOPlayerDatabase;
import me.mrdaniel.mmo.io.top.SkillTop;
import me.mrdaniel.mmo.utils.TopInfo;

public class CommandTop  implements CommandCallable {
	
	public CommandResult process(CommandSource sender, String arguments) throws CommandException {
		
		if (!(sender instanceof Player)) { sender.sendMessage(Text.of(TextColors.RED, "This command is for players only")); return CommandResult.success(); }
		Player p = (Player) sender;
		
		if (arguments.contains(" ")) { sender.sendMessage(usage); return CommandResult.success(); }
		
		MMOPlayer mmop = MMOPlayerDatabase.getInstance().getOrCreatePlayer(p.getUniqueId());
		
		if (arguments.equals("")) { sendTop(p, SkillTop.getInstance().getTop(null).getTop(), "Total", mmop, arguments); return CommandResult.success(); }
		
		SkillType type = SkillType.match(arguments);
		if (type == null) { sendTop(p, SkillTop.getInstance().getTop(null).getTop(), "Total", mmop, arguments);  return CommandResult.success();  }
		
		sendTop(p, SkillTop.getInstance().getTop(type).getTop(), type.name, mmop, arguments);
		return CommandResult.success();
	}
	private final Text usage = Text.of(TextColors.BLUE, "Usage: /skilltop [stat]");
	private final Text description = Text.of(TextColors.BLUE, "MMO | SkillTop Command");
	
	public Text getUsage(CommandSource sender) { return usage; }
	public Optional<Text> getHelp(CommandSource sender) { return Optional.of(usage); }
	public Optional<Text> getShortDescription(CommandSource sender) { return Optional.of(description); }
	public List<String> getSuggestions(CommandSource sender, String arguments, Location<World> loc) throws CommandException { return getSuggestions(sender, arguments); }
	public List<String> getSuggestions(CommandSource sender, String arguments) throws CommandException {  return CommandCenter.getSkillSuggesions(arguments); }
	public boolean testPermission(CommandSource sender) { return true; }
	private void sendTop(Player p, TreeMap<Integer, TopInfo> top, String title, MMOPlayer mmop, String arguments) {
		if (mmop.getSettings().getSetting(Setting.SCOREBOARD)) { BoardMenus.sendTop(p, mmop, top, title, "skilltop " + arguments); }
		else { ChatMenus.sendTop(p, top, title); }
	}
}