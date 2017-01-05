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

public class CommandAdminView implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource sender, CommandContext args) throws CommandException {
		if (!(sender instanceof Player)) { sender.sendMessage(Text.of(TextColors.RED, "This command is for players only")); return CommandResult.success(); }
		Player p = (Player) sender;

		if (!args.<Player>getOne("other").isPresent()) { sender.sendMessage(Text.of(TextColors.RED, "Player not found")); }
		Player other = args.<Player>getOne("other").get();
		MMOPlayer mmop = Main.getInstance().getMMOPlayerDatabase().getOrCreatePlayer(other.getUniqueId());

		if (args.<SkillType>getOne("type").isPresent()) { CommandCenter.getInstance().sendSkill(p, mmop, args.<SkillType>getOne("type").get()); }
		else { CommandCenter.getInstance().sendMain(p, mmop, other.getName()); }

		return CommandResult.success();
	}
}