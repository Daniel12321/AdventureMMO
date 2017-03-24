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
import me.mrdaniel.adventuremmo.io.playerdata.PlayerData;

public class CommandView extends MMOObject implements CommandExecutor {

	public CommandView(@Nonnull final AdventureMMO mmo) {
		super(mmo);
	}

	@Override
	public CommandResult execute(final CommandSource src, final CommandContext args) throws CommandException {
		User user = args.<User>getOne("user").get();
		PlayerData data = super.getMMO().getPlayerDatabase().getOffline(user.getUniqueId()).orElseThrow(() -> new CommandException(Text.of(TextColors.RED, "Invalid User!")));

		super.getMMO().getMenus().sendAdminView(src, data, user.getName());
		return CommandResult.success();
	}
}