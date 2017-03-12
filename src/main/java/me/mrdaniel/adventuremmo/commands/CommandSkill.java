package me.mrdaniel.adventuremmo.commands;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;

import me.mrdaniel.adventuremmo.AdventureMMO;
import me.mrdaniel.adventuremmo.catalogtypes.skills.SkillType;

public class CommandSkill extends PlayerCommand {

	private final AdventureMMO mmo;
	private final SkillType skill;

	public CommandSkill(@Nonnull final AdventureMMO mmo, @Nonnull final SkillType skill) {
		this.mmo = mmo;
		this.skill = skill;
	}

	@Override
	public void execute(final Player p, final CommandContext args) throws CommandException {
		this.mmo.getMenus().sendSkillInfo(p, this.skill);
	}
}