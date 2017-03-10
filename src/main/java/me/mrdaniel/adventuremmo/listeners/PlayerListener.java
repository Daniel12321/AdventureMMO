package me.mrdaniel.adventuremmo.listeners;

import java.util.Optional;
import java.util.UUID;

import javax.annotation.Nonnull;

import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.cause.entity.damage.DamageTypes;
import org.spongepowered.api.event.cause.entity.damage.source.DamageSource;
import org.spongepowered.api.event.entity.DamageEntityEvent;
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

	@Listener(order = Order.LATE)
	public void onBlockBreak(final ChangeBlockEvent.Break e, @Root final Player p) {
		if (e.isCancelled()) { return; }

		super.getMMO().getItemDatabase().getData(p.getItemInHand(HandTypes.MAIN_HAND).orElse(null)).ifPresent(hand -> e.getTransactions().forEach(transaction -> super.getMMO().getItemDatabase().getData(transaction.getOriginal().getState().getType()).ifPresent(data -> {
			Optional<UUID> creator = transaction.getOriginal().getCreator();
			if (creator.isPresent()) { p.sendMessage(Text.of("Creator: ", creator.get().toString())); }
			else { p.sendMessage(Text.of("Creator not found!")); }
			if (Lists.newArrayList(hand.getType().getSkills()).contains(data.getSkill())) {
				super.getMMO().getPlayerDatabase().get(p.getUniqueId()).addExp(p, data.getSkill(), data.getExp());
			}
		})));
	}

	@Listener(order = Order.LATE)
	public void onDamage(final DamageEntityEvent e) {
		if (e.isCancelled()) { return; }

		if (e.getTargetEntity() instanceof Player) {
			Player p = (Player) e.getTargetEntity();
			e.getCause().first(DamageSource.class).ifPresent(source -> {
				if (source.getType() == DamageTypes.FALL) {
					super.getMMO().getPlayerDatabase().get(p.getUniqueId()).addExp(p, SkillType.ACROBATICS, (int) (5.0 * e.getOriginalDamage()));
				}
			});
		}
	}

	@Listener(order = Order.LATE)
	public void onLevelUp(final LevelUpEvent e, @First final SkillType type) {
		if (e.isCancelled()) { return; }

		e.getTargetEntity().sendMessage(Text.of(TextColors.DARK_GRAY, "[", TextColors.BLUE, "MMO", TextColors.DARK_GRAY, "] ", TextColors.GOLD, "Your ", type.getName(), " level went up to level ", e.getCause().get("new_level", Integer.class).get(), "!"));

		super.getMMO().getTops().update(type, e.getTargetEntity().getName(), e.getCause().get("new_level", Integer.class).get());
		super.getMMO().getTops().update(null, e.getTargetEntity().getName(), super.getMMO().getPlayerDatabase().get(e.getTargetEntity().getUniqueId()).getLevels());
	}
}