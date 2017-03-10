package me.mrdaniel.adventuremmo.commands;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;

import me.mrdaniel.adventuremmo.AdventureMMO;
import me.mrdaniel.adventuremmo.enums.SkillType;

public class CommandSkill extends PlayerCommand {

	private final AdventureMMO mmo;

	public CommandSkill(@Nonnull final AdventureMMO mmo) {
		this.mmo = mmo;
	}

	@Override
	public void execute(final Player p, final CommandContext args) {
		Optional<SkillType> skill = args.getOne("skill");

		if (!skill.isPresent()) { this.mmo.getMenus().sendSkillList(p); }
		else { this.mmo.getMenus().sendSkillInfo(p, skill.get()); }
	}
}