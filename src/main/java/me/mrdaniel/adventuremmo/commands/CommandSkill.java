package me.mrdaniel.adventuremmo.commands;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.adventuremmo.AdventureMMO;
import me.mrdaniel.adventuremmo.data.PlayerData;
import me.mrdaniel.adventuremmo.enums.SkillType;
import me.mrdaniel.adventuremmo.utils.MathUtils;

public class CommandSkill extends PlayerCommand {

	private final AdventureMMO mmo;

	public CommandSkill(@Nonnull final AdventureMMO mmo) {
		this.mmo = mmo;
	}

	@Override
	public void execute(final Player p, final CommandContext args) {
		Optional<SkillType> s = args.getOne("skill");
		if (!s.isPresent()) {
			p.sendMessage(Text.of(TextColors.BLUE, "----===[ ", TextColors.RED, "Skills", TextColors.BLUE, " ]===---"));
		}
		else {
			SkillType skill = s.get();
			PlayerData data = this.mmo.getPlayerDatabase().get(p.getUniqueId());
			int level = data.getLevel(skill);

			p.sendMessage(Text.of(TextColors.BLUE, "----===[ ", TextColors.RED, skill.getName(), " skill", TextColors.BLUE, " ]===----"));
			p.sendMessage(Text.of(TextColors.GOLD, "Level: ", TextColors.RED, level));
			p.sendMessage(Text.of(TextColors.GOLD, "Exp: ", TextColors.RED, data.getExp(skill), " / ", MathUtils.expTillNextLevel(level)));
		}
	}
}