package me.mrdaniel.mmo.commands;

import java.util.ArrayList;
import java.util.List;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.mmo.enums.AbilityEnum;
import me.mrdaniel.mmo.enums.PassiveEnum;
import me.mrdaniel.mmo.enums.SkillType;
import me.mrdaniel.mmo.io.players.MMOPlayer;
import me.mrdaniel.mmo.io.players.MMOPlayerDatabase;
import me.mrdaniel.mmo.utils.Permissions;
import me.mrdaniel.mmo.utils.TextUtils;

public class CommandCenter {
		
	public static void sendMainInfo(CommandSource sender, String arguments) {
		
		if (!(sender instanceof Player)) { sender.sendMessage(Text.of(TextColors.RED, "This command is for players only")); return; }
		Player p = (Player) sender;
		
		MMOPlayer mmop = MMOPlayerDatabase.getInstance().getOrCreate(p.getUniqueId().toString());
		
		Menus.sendMainInfo(p, p.getName(), mmop, false);
	}
	
	public static void sendSkillInfo(CommandSource sender, String arguments) {
		
		if (!(sender instanceof Player)) { sender.sendMessage(Text.of(TextColors.RED, "This command is for players only")); return; }
		Player p = (Player) sender;
		
		String[] args = arguments.split(" ");
		if (args.length > 2) { p.sendMessage(Text.of(TextColors.BLUE, "Usage: /skills [skill]")); return; }
		
		MMOPlayer mmop = null;
		if (args.length == 1) { mmop = MMOPlayerDatabase.getInstance().getOrCreate(p.getUniqueId().toString()); }
		else if ((args.length == 2) && (p.hasPermission(Permissions.MMO_ADMIN_VIEW_OTHERS()))) { mmop = MMOPlayerDatabase.getInstance().getOrCreate(args[1]); }
		if (mmop == null) { p.sendMessage(Text.of(TextColors.RED, "You don't have permission to view others skills")); return; }
		
		if (args[0].equalsIgnoreCase("mining")) { Menus.sendSkillInfo(p, mmop, SkillType.MINING, mmop.getSkills().getSkill(SkillType.MINING), AbilityEnum.SUPER_BREAKER, PassiveEnum.MINING_DOUBLEDROP); }
		else if (args[0].equalsIgnoreCase("woodcutting")) { Menus.sendSkillInfo(p, mmop, SkillType.WOODCUTTING, mmop.getSkills().getSkill(SkillType.WOODCUTTING), AbilityEnum.TREE_VELLER, PassiveEnum.WOODCUTTING_DOUBLEDROP); }
		else if (args[0].equalsIgnoreCase("excavation")) { Menus.sendSkillInfo(p, mmop, SkillType.EXCAVATION, mmop.getSkills().getSkill(SkillType.EXCAVATION), AbilityEnum.GIGA_DRILL_BREAKER, PassiveEnum.EXCAVATION_DOUBLEDROP, PassiveEnum.TREASURE_HUNT); }
		else if (args[0].equalsIgnoreCase("fishing")) { Menus.sendSkillInfo(p, mmop, SkillType.FISHING, mmop.getSkills().getSkill(SkillType.FISHING), null, PassiveEnum.WATER_TREASURE); }
		else if (args[0].equalsIgnoreCase("farming")) { Menus.sendSkillInfo(p, mmop, SkillType.FARMING, mmop.getSkills().getSkill(SkillType.FARMING), AbilityEnum.GREEN_TERRA, PassiveEnum.FARMING_DOUBLEDROP); }
		else if (args[0].equalsIgnoreCase("acrobatics")) { Menus.sendSkillInfo(p, mmop, SkillType.ACROBATICS, mmop.getSkills().getSkill(SkillType.ACROBATICS), null, PassiveEnum.ROLL, PassiveEnum.DODGE); }
//		else if (args[0].equalsIgnoreCase("witchcraft")) { Menus.sendSkillInfo(p, mmop, SkillType.WITCHCRAFT, mmop.getSkills().getSkill(SkillType.WITCHCRAFT), null); }
		else if (args[0].equalsIgnoreCase("taming")) { Menus.sendSkillInfo(p, mmop, SkillType.TAMING, mmop.getSkills().getSkill(SkillType.TAMING), null); }
		else if (args[0].equalsIgnoreCase("salvage")) { Menus.sendSkillInfo(p, mmop, SkillType.SALVAGE, mmop.getSkills().getSkill(SkillType.SALVAGE), null); }
		else { p.sendMessage(Text.of(TextColors.RED, "Invalid Skill Type")); }
		return;
	}
	public static void sendAdminInfo(CommandSource sender) {
		Menus.sendAdminInfo(sender);
	}
	public static List<String> getSkillSuggesions(String arguments) {
		String[] args = arguments.split(" ");
		if (arguments.equals("") || arguments.equals(" ")) { return TextUtils.getSkillsSuggestions(""); }
		else if (args.length == 1) { return TextUtils.getSkillsSuggestions(args[0]); }
		return suggestions;
	}
	private final static List<String> suggestions = new ArrayList<String>();
}