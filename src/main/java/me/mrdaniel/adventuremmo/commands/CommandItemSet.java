package me.mrdaniel.adventuremmo.commands;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.adventuremmo.AdventureMMO;
import me.mrdaniel.adventuremmo.catalogtypes.tools.ToolType;

public class CommandItemSet extends PlayerCommand {

	private final AdventureMMO mmo;

	public CommandItemSet(@Nonnull final AdventureMMO mmo) {
		this.mmo = mmo;
	}

	@Override
	public void execute(final Player p, final CommandContext args) throws CommandException {
		Optional<ItemStack> hand = p.getItemInHand(HandTypes.MAIN_HAND);
		if (!hand.isPresent()) { p.sendMessage(Text.of(TextColors.RED, "You must be holding an item to use this command")); return; }

		ItemType item = hand.get().getItem();
		ToolType tool = args.<ToolType>getOne("tooltype").get();

		this.mmo.getItemDatabase().set(item, tool);
		this.mmo.getMessages().sendItemSet(p, item, tool);
	}
}