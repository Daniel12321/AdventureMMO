package me.mrdaniel.mmo.commands;

import java.util.ArrayList;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.mmo.enums.Ability;
import me.mrdaniel.mmo.enums.SkillType;
import me.mrdaniel.mmo.io.players.MMOPlayer;
import me.mrdaniel.mmo.skills.Skill;
import me.mrdaniel.mmo.utils.TextUtils;

public class Menus {
	
	public static void sendMainInfo(Player p, String name, MMOPlayer mmop, boolean adminMode) {
		String otherUUID = "";
		if (adminMode) { otherUUID = " " + mmop.getUUID(); }
		p.sendMessage(Text.of(""));
		p.sendMessage(Text.of(TextColors.RED, "--=== ", TextColors.AQUA, name, TextColors.RED, " ==---"));
		p.sendMessage(TextUtils.setCommandClick("&bMining &7- &aLevel " + String.valueOf(mmop.getSkills().getSkill(SkillType.MINING).level), "/mining" + otherUUID, "&9Click to see more"));
		p.sendMessage(TextUtils.setCommandClick("&bWoodcutting &7- &aLevel " + String.valueOf(mmop.getSkills().getSkill(SkillType.WOODCUTTING).level), "/woodcutting" + otherUUID, "&9Click to see more"));
		p.sendMessage(TextUtils.setCommandClick("&bExcavation &7- &aLevel " + String.valueOf(mmop.getSkills().getSkill(SkillType.EXCAVATION).level), "/excavation" + otherUUID, "&9Click to see more"));
		p.sendMessage(TextUtils.setCommandClick("&bFishing &7- &aLevel " + String.valueOf(mmop.getSkills().getSkill(SkillType.FISHING).level), "/fishing" + otherUUID, "&9Click to see more"));
		p.sendMessage(TextUtils.setCommandClick("&bFarming &7- &aLevel " + String.valueOf(mmop.getSkills().getSkill(SkillType.FARMING).level), "/farming" + otherUUID, "&9Click to see more"));
		p.sendMessage(TextUtils.setCommandClick("&bAcrobatics &7- &aLevel " + String.valueOf(mmop.getSkills().getSkill(SkillType.ACROBATICS).level), "/acrobatics" + otherUUID, "&9Click to see more"));
		p.sendMessage(TextUtils.setCommandClick("&bTaming &7- &aLevel " + String.valueOf(mmop.getSkills().getSkill(SkillType.TAMING).level), "/taming" + otherUUID, "&9Click to see more"));
		p.sendMessage(TextUtils.setCommandClick("&bSalvage &7- &aLevel " + String.valueOf(mmop.getSkills().getSkill(SkillType.SALVAGE).level), "/salvage" + otherUUID, "&9Click to see more"));
		p.sendMessage(TextUtils.setCommandClick("&bRepair &7- &aLevel " + String.valueOf(mmop.getSkills().getSkill(SkillType.REPAIR).level), "/repair" + otherUUID, "&9Click to see more"));
		p.sendMessage(Text.of(TextColors.AQUA, "Total", TextColors.GRAY, " - ", TextColors.GREEN, "Level ", mmop.totalLevels()));
	}
	
	public static void sendSkillInfo(Player p, MMOPlayer mmop, SkillType type, Skill skill, ArrayList<Ability> abilitys) {
		int level = skill.level;
		
		p.sendMessage(Text.of(""));
		p.sendMessage(Text.of(TextColors.RED, "--=== ", TextColors.AQUA, type.name, TextColors.RED, " ==---"));
		p.sendMessage(Text.of(TextColors.GREEN, "Level: " + String.valueOf(level)));
		p.sendMessage(Text.of(TextColors.GREEN, "EXP: " + String.valueOf(skill.exp) + "/" + String.valueOf(mmop.getSkills().expTillNextLevel(skill.level))));
		
		for (Ability ability : abilitys) {
			p.sendMessage(Text.of(""));
			p.sendMessage(Text.of(TextColors.RED, "-= ", TextColors.YELLOW, ability.name, TextColors.RED, " =-"));
			p.sendMessage(Text.of(TextColors.DARK_GREEN, ability.desc));
			p.sendMessage(Text.of(TextColors.DARK_GREEN, ability.showState.create(ability.getValue(level))));
		}
	}
	public static void sendAdminInfo(CommandSource p) {
		p.sendMessage(Text.of(""));
		p.sendMessage(Text.of(TextColors.RED, "--=== ", TextColors.AQUA, "MMO Admin", TextColors.RED, " ==---"));
		p.sendMessage(Text.of(TextColors.GREEN, "/mmoadmin set <player> <skill> <level>"));
		p.sendMessage(Text.of(TextColors.GREEN, "/mmoadmin view <player>"));
	}
}