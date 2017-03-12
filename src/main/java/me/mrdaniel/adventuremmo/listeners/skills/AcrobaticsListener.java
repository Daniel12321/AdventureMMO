package me.mrdaniel.adventuremmo.listeners.skills;

import javax.annotation.Nonnull;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.cause.entity.damage.DamageTypes;
import org.spongepowered.api.event.cause.entity.damage.source.DamageSource;
import org.spongepowered.api.event.entity.DamageEntityEvent;

import me.mrdaniel.adventuremmo.AdventureMMO;
import me.mrdaniel.adventuremmo.MMOObject;
import me.mrdaniel.adventuremmo.catalogtypes.abilities.Abilities;
import me.mrdaniel.adventuremmo.catalogtypes.skills.SkillTypes;
import me.mrdaniel.adventuremmo.io.PlayerData;

public class AcrobaticsListener extends MMOObject {

	private final double exp_multiplier;

	public AcrobaticsListener(@Nonnull final AdventureMMO mmo, final double exp_multiplier) {
		super(mmo);

		this.exp_multiplier = exp_multiplier;
	}

	@Listener(order = Order.LATE)
	public void onDamange(final DamageEntityEvent e) {
		if (e.isCancelled()) { return; }

		if (e.getTargetEntity() instanceof Player) {
			Player p = (Player) e.getTargetEntity();
			PlayerData data = super.getMMO().getPlayerDatabase().get(p.getUniqueId());

			if (Abilities.DODGE.getChance(data.getLevel(SkillTypes.ACROBATICS))) {
				e.setCancelled(true);
				super.getMMO().getMessages().sendDodge(p);
				return;
			}

			e.getCause().first(DamageSource.class).ifPresent(source -> {
				if (source.getType() == DamageTypes.FALL) {
					if (Abilities.ROLL.getChance(data.getLevel(SkillTypes.ACROBATICS))) {
						e.setCancelled(true);
						super.getMMO().getMessages().sendRoll(p);
					}
					super.getMMO().getPlayerDatabase().get(p.getUniqueId()).addExp(p, SkillTypes.ACROBATICS, (int) (this.exp_multiplier * e.getOriginalDamage()));
				}
			});
		}
	}
}