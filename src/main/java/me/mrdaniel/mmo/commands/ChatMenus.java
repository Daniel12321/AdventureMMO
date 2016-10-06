package me.mrdaniel.mmo.commands;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeMap;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.mmo.enums.Ability;
import me.mrdaniel.mmo.enums.Setting;
import me.mrdaniel.mmo.enums.SkillType;
import me.mrdaniel.mmo.io.players.MMOPlayer;
import me.mrdaniel.mmo.skills.Skill;
import me.mrdaniel.mmo.utils.TextUtils;
import me.mrdaniel.mmo.utils.TopInfo;

public class ChatMenus {
	
	public static void sendMainInfo(Player p, String name, MMOPlayer mmop, boolean adminMode) {
		String otherUUID = "";
		if (adminMode) { otherUUID = " " + mmop.getUUID(); }
		p.sendMessage(Text.of(""));
		p.sendMessage(Text.of(TextColors.RED, "}--=== ", TextColors.AQUA, name, TextColors.RED, " ==---{"));
		p.sendMessage(TextUtils.setCommandClick("&bMining &7- &aLevel " + String.valueOf(mmop.getSkills().getSkill(SkillType.MINING).level), "/skills mining" + otherUUID, "&9Click to see more"));
		p.sendMessage(TextUtils.setCommandClick("&bWoodcutting &7- &aLevel " + String.valueOf(mmop.getSkills().getSkill(SkillType.WOODCUTTING).level), "/skills woodcutting" + otherUUID, "&9Click to see more"));
		p.sendMessage(TextUtils.setCommandClick("&bExcavation &7- &aLevel " + String.valueOf(mmop.getSkills().getSkill(SkillType.EXCAVATION).level), "/skills excavation" + otherUUID, "&9Click to see more"));
		p.sendMessage(TextUtils.setCommandClick("&bFishing &7- &aLevel " + String.valueOf(mmop.getSkills().getSkill(SkillType.FISHING).level), "/skills fishing" + otherUUID, "&9Click to see more"));
		p.sendMessage(TextUtils.setCommandClick("&bFarming &7- &aLevel " + String.valueOf(mmop.getSkills().getSkill(SkillType.FARMING).level), "/skills farming" + otherUUID, "&9Click to see more"));
		p.sendMessage(TextUtils.setCommandClick("&bAcrobatics &7- &aLevel " + String.valueOf(mmop.getSkills().getSkill(SkillType.ACROBATICS).level), "/skills acrobatics" + otherUUID, "&9Click to see more"));
		p.sendMessage(TextUtils.setCommandClick("&bTaming &7- &aLevel " + String.valueOf(mmop.getSkills().getSkill(SkillType.TAMING).level), "/skills taming" + otherUUID, "&9Click to see more"));
		p.sendMessage(TextUtils.setCommandClick("&bSalvage &7- &aLevel " + String.valueOf(mmop.getSkills().getSkill(SkillType.SALVAGE).level), "/skills salvage" + otherUUID, "&9Click to see more"));
		p.sendMessage(TextUtils.setCommandClick("&bRepair &7- &aLevel " + String.valueOf(mmop.getSkills().getSkill(SkillType.REPAIR).level), "/skills repair" + otherUUID, "&9Click to see more"));
		p.sendMessage(Text.of(TextColors.AQUA, "Total", TextColors.GRAY, " - ", TextColors.GREEN, "Level ", mmop.totalLevels()));
	}
	public static void sendSkillInfo(Player p, MMOPlayer mmop, SkillType type, Skill skill, ArrayList<Ability> abilitys) {
		int level = skill.level;
		
		p.sendMessage(Text.of(""));
		p.sendMessage(Text.of(TextColors.RED, "}--=== ", TextColors.AQUA, type.name, TextColors.RED, " ==---{"));
		p.sendMessage(Text.of(TextColors.GREEN, "Level: " + String.valueOf(level)));
		p.sendMessage(Text.of(TextColors.GREEN, "EXP: " + String.valueOf(skill.exp) + "/" + String.valueOf(mmop.getSkills().expTillNextLevel(skill.level))));
		
		for (Ability ability : abilitys) {
			p.sendMessage(Text.of(""));
			p.sendMessage(Text.of(TextColors.RED, "}-= ", TextColors.YELLOW, ability.name, TextColors.RED, " =-{"));
			p.sendMessage(Text.of(TextColors.DARK_GREEN, ability.desc));
			p.sendMessage(Text.of(TextColors.DARK_GREEN, ability.showState.create(ability.getValue(level))));
		}
	}
	public static void sendAdminInfo(CommandSource p) {
		p.sendMessage(Text.of(""));
		p.sendMessage(Text.of(TextColors.RED, "}--=== ", TextColors.AQUA, "MMO Admin", TextColors.RED, " ==---{"));
		p.sendMessage(Text.of(TextColors.GREEN, "/mmoadmin set <player> <skill> <level>"));
		p.sendMessage(Text.of(TextColors.GREEN, "/mmoadmin view <player>"));
	}
	public static void sendSettingsInfo(Player p, MMOPlayer mmop) {
		p.sendMessage(Text.of(""));
		p.sendMessage(Text.of(TextColors.RED, "}--=== ", TextColors.AQUA, p.getName(), TextColors.RED, " ==---{"));

		if (mmop.getSettings().getSetting(Setting.SOUNDS)) { p.sendMessage(TextUtils.setCommandClick("&bSounds &7- &aEnabled", "/settings sounds false", "&cClick to Disable")); }
		else { p.sendMessage(TextUtils.setCommandClick("&bSounds &7- &cDisabled", "/setting sounds true", "&aClick to Enable")); }
		
		if (mmop.getSettings().getSetting(Setting.EFFECTS)) { p.sendMessage(TextUtils.setCommandClick("&bEffects &7- &aEnabled", "/settings effects false", "&cClick to Disable")); }
		else { p.sendMessage(TextUtils.setCommandClick("&bEffects &7- &cDisabled", "/setting effects true", "&aClick to Enable")); }
		
		if (mmop.getSettings().getSetting(Setting.SCOREBOARD)) { p.sendMessage(TextUtils.setCommandClick("&bScoreboard &7- &aEnabled", "/settings scoreboard false", "&cClick to Disable")); }
		else { p.sendMessage(TextUtils.setCommandClick("&bScoreboard &7- &cDisabled", "/setting scoreboard true", "&aClick to Enable")); }
	}
	public static void sendTop(Player p, TreeMap<Integer, TopInfo> top, String title) {
		p.sendMessage(Text.of(""));
		p.sendMessage(Text.of(TextColors.RED, "}--=== ", TextColors.AQUA, title, " SkillTop", TextColors.RED, " ==---{"));
		Set<Integer> keys = top.keySet();
		for (int key : keys) {
			TopInfo TopInfo = top.get(key);
			p.sendMessage(Text.of(TextColors.RED, String.valueOf(key), ": ", TextColors.AQUA, TopInfo.name, TextColors.GRAY, " - ", TextColors.GREEN, "Level ", TopInfo.level));
		}
	}
}