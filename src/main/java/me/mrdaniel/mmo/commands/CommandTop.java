package me.mrdaniel.mmo.commands;

import java.util.Optional;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.mmo.enums.SkillType;

public class CommandTop implements CommandExecutor {

	public CommandResult execute(final CommandSource sender, final CommandContext args) throws CommandException {
		if (!(sender instanceof Player)) { sender.sendMessage(Text.of(TextColors.RED, "This command is for players only.")); return CommandResult.success(); }
		Player p = (Player) sender;

		Optional<String> t = args.<String>getOne("type");
		SkillType type = null;
		if (t.isPresent()) { type = SkillType.of(t.get()).orElse(null); }

		CommandCenter.getInstance().sendTop(p, type);
		return CommandResult.success();
	}
}