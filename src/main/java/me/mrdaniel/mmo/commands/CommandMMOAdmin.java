package me.mrdaniel.mmo.commands;

import java.util.ArrayList;
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

import me.mrdaniel.mmo.Main;
import me.mrdaniel.mmo.enums.SkillType;
import me.mrdaniel.mmo.io.Config;
import me.mrdaniel.mmo.io.players.MMOPlayer;
import me.mrdaniel.mmo.io.players.MMOPlayerDatabase;
import me.mrdaniel.mmo.io.top.SkillTop;
import me.mrdaniel.mmo.skills.Skill;
import me.mrdaniel.mmo.utils.Permissions;
import me.mrdaniel.mmo.utils.TextUtils;

public class CommandMMOAdmin implements CommandCallable {
	
	public CommandResult process(CommandSource sender, String arguments) throws CommandException {
		
		if (!(sender instanceof Player)) { return CommandResult.success(); }
		Player p = (Player) sender;
		if (!p.hasPermission(Permissions.MMO_ADMIN())) { p.sendMessage(Text.of(TextColors.RED, "You don't have permission to use this command")); return CommandResult.success(); }
		
		String[] args = arguments.split(" ");
		if (arguments.equals("")) { ChatMenus.sendAdminInfo(p); return CommandResult.success(); }
		
		else if (args[0].equalsIgnoreCase("set")) {
			if (!p.hasPermission(Permissions.MMO_ADMIN_SET())) { p.sendMessage(Text.of(TextColors.RED, "You don't have permission to use this command")); return CommandResult.success(); }
			if (args.length != 4) { p.sendMessage(Text.of(TextColors.GREEN, "/mmoadmin set <player> <skill> <level>")); return CommandResult.success(); }
			
			Optional<Player> otherOpt = Main.getInstance().getGame().getServer().getPlayer(args[1]);
			if (!otherOpt.isPresent()) { p.sendMessage(Text.of(TextColors.RED, "Player not found")); return CommandResult.success(); }
			Player other = otherOpt.get();
			
			SkillType type = SkillType.match(args[2]);
			if (type == null) { p.sendMessage(Text.of(TextColors.RED, "Invalid skill")); return CommandResult.success(); }
			
			int level = 0;
			try { level = Integer.valueOf(args[3]); }
			catch(NumberFormatException exc) { p.sendMessage(Text.of(TextColors.RED, "Level must be a number")); return CommandResult.success(); }
			
			Skill skill = new Skill(level, 0);
			MMOPlayer mmop = MMOPlayerDatabase.getInstance().getOrCreatePlayer(other.getUniqueId().toString());
			mmop.getSkills().setSkill(type, skill);
			SkillTop.getInstance().update(other.getName(), mmop);
			
			p.sendMessage(Config.getInstance().PREFIX.concat(Text.of(TextColors.GREEN, "You set ", other.getName(), "'s ", args[2], " level to ", level)));
		}
		
		else if (args[0].equalsIgnoreCase("view")) {
			if (!p.hasPermission(Permissions.MMO_ADMIN_VIEW_OTHERS())) { p.sendMessage(Text.of(TextColors.RED, "You don't have permission to use this command")); return CommandResult.success(); }
			if (args.length != 2) { p.sendMessage(Text.of(TextColors.GREEN, "/mmoadmin view <player>")); return CommandResult.success(); }
			
			Optional<Player> otherOpt = Main.getInstance().getGame().getServer().getPlayer(args[1]);
			if (!otherOpt.isPresent()) { p.sendMessage(Text.of(TextColors.RED, "Player not found")); return CommandResult.success(); }
			Player other = otherOpt.get();
			
			MMOPlayer mmop = MMOPlayerDatabase.getInstance().getOrCreatePlayer(other.getUniqueId().toString());
			ChatMenus.sendMainInfo(p, other.getName(), mmop, true);
			
		}
		else { ChatMenus.sendAdminInfo(p); return CommandResult.success(); }
		return CommandResult.success();
	}
	private final Text usage = Text.of(TextColors.BLUE, "Usage: /mmoadmin");
	private final Text description = Text.of(TextColors.BLUE, "MMO | Admin Command");
	private final List<String> suggestions = new ArrayList<String>();
	
	public Text getUsage(CommandSource sender) { return usage; }
	public Optional<Text> getHelp(CommandSource sender) { return Optional.of(usage); }
	public Optional<Text> getShortDescription(CommandSource sender) { return Optional.of(description); }
	public boolean testPermission(CommandSource sender) { return true; }
	public List<String> getSuggestions(CommandSource sender, String arguments, Location<World> loc) throws CommandException { return getSuggestions(sender, arguments); }
	public List<String> getSuggestions(CommandSource sender, String arguments) throws CommandException {
		String[] args = arguments.split(" ");
		if (args[0].equalsIgnoreCase("set")) {
			if (args.length > 1 || (args.length == 1 && arguments.endsWith(" "))) {
				if (args.length > 2 || (args.length == 2 && arguments.endsWith(" "))) {
					if (args.length > 3 || (args.length == 3 && arguments.endsWith(" "))) { return suggestions; }
					else if (args.length == 2) { return TextUtils.getSkillsSuggestions(""); } 
					else { return TextUtils.getSkillsSuggestions(args[2]); }
				}
				else if (args.length == 1) { return TextUtils.getPlayerSuggestions(""); } 
				else { return TextUtils.getPlayerSuggestions(args[1]); }
			}
		}
		else if (args[0].equalsIgnoreCase("view")) {
			if (args.length > 1 || (args.length == 1 && arguments.endsWith(" "))) {
				if (args.length > 2) { return suggestions; }
				else if (args.length == 1) { return TextUtils.getPlayerSuggestions(""); } 
				else { return TextUtils.getPlayerSuggestions(args[1]); }
			}
		}
		else {
			List<String> s = new ArrayList<String>();
			s.add("set"); s.add("view");
			return s;
		}
		return suggestions;
	}
}