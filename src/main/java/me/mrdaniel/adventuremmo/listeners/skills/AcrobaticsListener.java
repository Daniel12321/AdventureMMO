package me.mrdaniel.adventuremmo.listeners.skills;

import javax.annotation.Nonnull;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.cause.entity.damage.DamageTypes;
import org.spongepowered.api.event.cause.entity.damage.source.DamageSource;
import org.spongepowered.api.event.entity.DamageEntityEvent;
import org.spongepowered.api.event.filter.IsCancelled;
import org.spongepowered.api.util.Tristate;

import me.mrdaniel.adventuremmo.AdventureMMO;
import me.mrdaniel.adventuremmo.MMOObject;
import me.mrdaniel.adventuremmo.catalogtypes.abilities.Abilities;
import me.mrdaniel.adventuremmo.catalogtypes.skills.SkillTypes;
import me.mrdaniel.adventuremmo.io.playerdata.PlayerData;

public class AcrobaticsListener extends MMOObject {

	private final double exp_multiplier;

	public AcrobaticsListener(@Nonnull final AdventureMMO mmo, final double exp_multiplier) {
		super(mmo);

		this.exp_multiplier = exp_multiplier;
	}

	@Listener(order = Order.LATE)
	@IsCancelled(value = Tristate.FALSE)
	public void onDamange(final DamageEntityEvent e) {
		if (e.getTargetEntity() instanceof Player) {
			Player p = (Player) e.getTargetEntity();

			boolean fall = e.getCause().first(DamageSource.class).map(source -> source.getType() == DamageTypes.FALL)
					.orElse(false);
			PlayerData data = fall
					? super.getMMO().getPlayerDatabase().addExp(super.getMMO(), p, SkillTypes.ACROBATICS,
							(int) (this.exp_multiplier * p.get(Keys.FALL_DISTANCE).orElse(4F)))
					: super.getMMO().getPlayerDatabase().get(p.getUniqueId());
			int level = data.getLevel(SkillTypes.ACROBATICS);

			if (fall && Abilities.ROLL.getChance(level)) {
				e.setCancelled(true);
				super.getMMO().getMessages().sendRoll(p);
			} else if (Abilities.DODGE.getChance(level)) {
				e.setCancelled(true);
				super.getMMO().getMessages().sendDodge(p);
			}
		}
	}
}