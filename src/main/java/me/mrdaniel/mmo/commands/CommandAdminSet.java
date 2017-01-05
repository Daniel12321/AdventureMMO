package me.mrdaniel.mmo.commands;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.mmo.Main;
import me.mrdaniel.mmo.enums.SkillType;
import me.mrdaniel.mmo.io.players.MMOPlayer;
import me.mrdaniel.mmo.skills.Skill;

public class CommandAdminSet implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource sender, CommandContext args) throws CommandException {

		if (!args.<Player>getOne("other").isPresent()) { sender.sendMessage(Text.of(TextColors.RED, "Player not found")); }
		Player other = args.<Player>getOne("other").get();

		if (!args.<SkillType>getOne("type").isPresent()) { sender.sendMessage(Text.of(TextColors.RED, "Invalid Skill"));}
		SkillType type = args.<SkillType>getOne("type").get();

		if (!args.<Integer>getOne("value").isPresent()) { sender.sendMessage(Text.of(TextColors.RED, "Invalid Value"));}
		int level = args.<Integer>getOne("value").get();

		Skill skill = new Skill(level, 0);
		MMOPlayer mmop = Main.getInstance().getMMOPlayerDatabase().getOrCreatePlayer(other.getUniqueId());
		mmop.getSkills().setSkill(type, skill);
		Main.getInstance().getSkillTop().update(other.getName(), mmop);
		
		sender.sendMessage(Main.getInstance().getConfig().PREFIX.concat(Text.of(TextColors.GREEN, "You set ", other.getName(), "'s ", type.getName(), " level to ", level)));

		return CommandResult.success();
	}
}