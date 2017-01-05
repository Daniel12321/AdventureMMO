package me.mrdaniel.mmo.commands;

import java.util.Set;
import java.util.TreeMap;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.Tuple;

import me.mrdaniel.mmo.Main;
import me.mrdaniel.mmo.enums.Ability;
import me.mrdaniel.mmo.enums.SkillType;
import me.mrdaniel.mmo.io.players.MMOPlayer;
import me.mrdaniel.mmo.skills.Skill;

public class ChatMenus {

	public static void sendSkill(Player p, MMOPlayer mmop, SkillType type) {
		Skill skill = mmop.getSkills().getSkill(type);

		p.sendMessage(Text.of(""));
		p.sendMessage(Text.of(TextColors.RED, "}--=== ", TextColors.AQUA, type.getName(), TextColors.RED, " ==---{"));
		p.sendMessage(Text.of(TextColors.GREEN, "Level: " + String.valueOf(skill.level)));
		p.sendMessage(Text.of(TextColors.GREEN, "EXP: " + String.valueOf(skill.exp) + "/" + String.valueOf(mmop.getSkills().expTillNextLevel(skill.level))));

		for (Ability ability : Ability.getAll(type)) {
			p.sendMessage(Text.of(""));
			p.sendMessage(Text.of(TextColors.RED, "}-= ", TextColors.YELLOW, ability.getName(), TextColors.RED, " =-{"));
			p.sendMessage(Text.of(TextColors.DARK_GREEN, ability.getDesc()));
			p.sendMessage(ability.getShowState().create(TextColors.DARK_GREEN, Main.getInstance().getValueStore().getAbility(ability).getSecond().getValue(skill.level)));
		}
	}

	public static void sendTop(Player p, MMOPlayer mmop, SkillType type) {
		TreeMap<Integer, Tuple<String, Integer>> top = Main.getInstance().getSkillTop().getTop(type).getTop();

		p.sendMessage(Text.of(""));
		p.sendMessage(Text.of(TextColors.RED, "}--=== ", TextColors.AQUA, type.getName(), " SkillTop", TextColors.RED, " ==---{"));
		Set<Integer> keys = top.keySet();
		for (int key : keys) {
			p.sendMessage(Text.of(TextColors.RED, String.valueOf(key), ": ", TextColors.AQUA, top.get(key).getFirst(), TextColors.GRAY, " - ", TextColors.GREEN, "Level ", top.get(key).getSecond()));
		}
	}
}