package me.mrdaniel.adventuremmo.commands;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.adventuremmo.AdventureMMO;
import me.mrdaniel.adventuremmo.data.manipulators.SettingsData;
import me.mrdaniel.adventuremmo.enums.Setting;

public class CommandSetting extends PlayerCommand {

	private final AdventureMMO mmo;

	public CommandSetting(@Nonnull final AdventureMMO mmo) {
		this.mmo = mmo;
	}

	@Override
	public void execute(final Player p, final CommandContext args) throws CommandException {
		Setting setting = args.<Setting>getOne("setting").get();
		boolean value = args.<Boolean>getOne("value").get();

		SettingsData data = p.get(SettingsData.class).orElse(new SettingsData(true, false));
		if (setting == Setting.SCOREBOARD) { data.setScoreboard(value); }
		else if (setting == Setting.SCOREBOARD_PERMANENT) { data.setScoreboardPermanent(value); }
		else throw new CommandException(Text.of(TextColors.RED, "Invalid setting!"));

		p.offer(data);
		this.mmo.getGame().getCommandManager().process(p, "mmoeffects");
	}
}