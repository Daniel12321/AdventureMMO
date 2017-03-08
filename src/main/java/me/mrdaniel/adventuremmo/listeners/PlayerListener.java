package me.mrdaniel.adventuremmo.listeners;

import javax.annotation.Nonnull;

import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.filter.cause.Root;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import com.google.common.collect.Lists;

import me.mrdaniel.adventuremmo.AdventureMMO;
import me.mrdaniel.adventuremmo.MMOObject;
import me.mrdaniel.adventuremmo.enums.SkillType;
import me.mrdaniel.adventuremmo.event.LevelUpEvent;

public class PlayerListener extends MMOObject {

	public PlayerListener(@Nonnull final AdventureMMO mmo) {
		super(mmo);
	}

	@Listener(order = Order.EARLY)
	public void onBlockBreak(final ChangeBlockEvent.Break e, @Root final Player p) {
		super.getMMO().getItemDatabase().getData(p.getItemInHand(HandTypes.MAIN_HAND).orElse(null)).ifPresent(hand -> e.getTransactions().forEach(transaction -> super.getMMO().getItemDatabase().getData(transaction.getOriginal().getState().getType()).ifPresent(data -> {
			if (Lists.newArrayList(hand.getType().getSkills()).contains(data.getSkill())) {
				super.getMMO().getPlayerDatabase().get(p.getUniqueId()).addExp(p, data.getSkill(), data.getExp());
			}
		})));
	}

	@Listener(order = Order.LATE)
	public void onLevelUp(final LevelUpEvent e, @First final SkillType type) {
		e.getTargetEntity().sendMessage(Text.of(TextColors.DARK_GRAY, "[", TextColors.DARK_BLUE, "MMO", TextColors.DARK_GRAY, "] ", TextColors.GOLD, "Your ", type.getName(), " level went up to level ", e.getCause().get("new_level", Integer.class).get(), "!"));
	}
}