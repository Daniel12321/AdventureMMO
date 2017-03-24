package me.mrdaniel.adventuremmo.commands;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.adventuremmo.AdventureMMO;
import me.mrdaniel.adventuremmo.MMOObject;
import me.mrdaniel.adventuremmo.catalogtypes.skills.SkillType;
import me.mrdaniel.adventuremmo.io.playerdata.PlayerData;

public class CommandSet extends MMOObject implements CommandExecutor {

	public CommandSet(@Nonnull final AdventureMMO mmo) {
		super(mmo);
	}

	@Override
	public CommandResult execute(final CommandSource src, final CommandContext args) throws CommandException {
		User user = args.<User>getOne("user").get();
		SkillType skill = args.<SkillType>getOne("skill").get();
		int level = args.<Integer>getOne("level").get();
		int exp = args.<Integer>getOne("exp").orElse(0);

		PlayerData data = user.getPlayer().isPresent() ? super.getMMO().getPlayerDatabase().get(user.getUniqueId()) : super.getMMO().getPlayerDatabase().getOffline(user.getUniqueId()).orElseThrow(() -> new CommandException(Text.of(TextColors.RED, "Invalid User!")));
		data.setLevel(skill, level);
		data.setExp(skill, exp);

		super.getMMO().getMessages().sendSet(src, user.getName(), skill, level);
		return CommandResult.success();
	}
}