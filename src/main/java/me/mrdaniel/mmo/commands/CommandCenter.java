package me.mrdaniel.mmo.commands;

import java.util.ArrayList;
import java.util.List;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.mmo.enums.Ability;
import me.mrdaniel.mmo.enums.Setting;
import me.mrdaniel.mmo.enums.SkillType;
import me.mrdaniel.mmo.io.Config;
import me.mrdaniel.mmo.io.players.MMOPlayer;
import me.mrdaniel.mmo.io.players.MMOPlayerDatabase;
import me.mrdaniel.mmo.skills.Skill;
import me.mrdaniel.mmo.utils.Permissions;
import me.mrdaniel.mmo.utils.TextUtils;

public class CommandCenter {
		
	public static void sendMainInfo(CommandSource sender, String arguments) {
		
		if (!(sender instanceof Player)) { sender.sendMessage(Text.of(TextColors.RED, "This command is for players only")); return; }
		Player p = (Player) sender;
		
		MMOPlayer mmop = MMOPlayerDatabase.getInstance().getOrCreatePlayer(p.getUniqueId().toString());
		
		if (mmop.getSettings().getSetting(Setting.SCOREBOARD)) { BoardMenus.sendMainInfo(p, p.getName(), mmop); }
		else { ChatMenus.sendMainInfo(p, p.getName(), mmop, false); }
	}
	
	public static void sendSkillInfo(CommandSource sender, String arguments) {
		
		if (!(sender instanceof Player)) { sender.sendMessage(Text.of(TextColors.RED, "This command is for players only")); return; }
		Player p = (Player) sender;
		
		String[] args = arguments.split(" ");
		if (args.length > 2) { p.sendMessage(Text.of(TextColors.BLUE, "Usage: /skills [skill]")); return; }
		
		MMOPlayer mmop = null;
		if (args.length == 1) { mmop = MMOPlayerDatabase.getInstance().getOrCreatePlayer(p.getUniqueId().toString()); }
		else if ((args.length == 2) && (p.hasPermission(Permissions.MMO_ADMIN_VIEW_OTHERS()))) { mmop = MMOPlayerDatabase.getInstance().getOrCreatePlayer(args[1]); }
		if (mmop == null) { p.sendMessage(Text.of(TextColors.RED, "You don't have permission to view others skills")); return; }
		
		SkillType type = SkillType.match(args[0]);
		if (type == null) { p.sendMessage(Config.getInstance().PREFIX.concat(Text.of(TextColors.RED, "Invalid Skill Type"))); return; }
		Skill skill = mmop.getSkills().getSkill(type);
		ArrayList<Ability> abilities = new ArrayList<Ability>();
		for (Ability ability : Ability.values()) { if (ability.skillType.equals(type)) { abilities.add(ability); } }
		if (mmop.getSettings().getSetting(Setting.SCOREBOARD)) { BoardMenus.sendSkillInfo(p, mmop, type, skill, abilities); }
		else { ChatMenus.sendSkillInfo(p, mmop, type, skill, abilities); }
		return;
	}
	public static void sendAdminInfo(CommandSource sender) {
		ChatMenus.sendAdminInfo(sender);
	}
	public static List<String> getSkillSuggesions(String arguments) {
		String[] args = arguments.split(" ");
		if (arguments.equals("") || arguments.equals(" ")) { return TextUtils.getSkillsSuggestions(""); }
		else if (args.length == 1) { return TextUtils.getSkillsSuggestions(args[0]); }
		return suggestions;
	}
	private final static List<String> suggestions = new ArrayList<String>();
}