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
import me.mrdaniel.mmo.enums.Setting;
import me.mrdaniel.mmo.io.players.MMOPlayer;

public class CommandSettings implements CommandExecutor {
	
	public CommandResult execute(CommandSource sender, CommandContext args) throws CommandException {
		if (!(sender instanceof Player)) { sender.sendMessage(Text.of(TextColors.RED, "This command is for players only")); return CommandResult.success(); }
		Player p = (Player) sender;

		if (!args.<Setting>getOne("setting").isPresent() || !args.<Boolean>getOne("value").isPresent()) { CommandCenter.getInstance().sendSettings(p); return CommandResult.success(); }

		Setting setting = args.<Setting>getOne("setting").get();
		boolean value = args.<Boolean>getOne("value").get();
		if (Main.getInstance().getConfig().FORCEDSETTINGS.containsKey(setting)) { return CommandResult.success(); }

		MMOPlayer mmop = Main.getInstance().getMMOPlayerDatabase().getOrCreatePlayer(p.getUniqueId());
		mmop.getSettings().setSetting(setting, value);
		if (setting == Setting.SCOREBOARDPERMANENT) { CommandCenter.getInstance().removeRepeating(p.getUniqueId()); }
		CommandCenter.getInstance().sendSettings(p);

		return CommandResult.success();
	}
}