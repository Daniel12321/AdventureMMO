package me.mrdaniel.adventuremmo.listeners.skills;

import javax.annotation.Nonnull;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.EntityArchetype;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.projectile.arrow.Arrow;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.cause.entity.damage.source.EntityDamageSource;
import org.spongepowered.api.event.entity.DamageEntityEvent;
import org.spongepowered.api.event.entity.projectile.LaunchProjectileEvent;
import org.spongepowered.api.event.filter.IsCancelled;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.util.Tristate;

import com.flowpowered.math.vector.Vector3d;

import me.mrdaniel.adventuremmo.AdventureMMO;
import me.mrdaniel.adventuremmo.MMOObject;
import me.mrdaniel.adventuremmo.catalogtypes.abilities.Abilities;
import me.mrdaniel.adventuremmo.catalogtypes.skills.SkillTypes;
import me.mrdaniel.adventuremmo.io.PlayerData;
import me.mrdaniel.adventuremmo.utils.ServerUtils;

public class ArcheryListener extends MMOObject  {

	private final int damage_exp;
	private final int kill_exp;

	public ArcheryListener(@Nonnull final AdventureMMO mmo, final int damage_exp, final int kill_exp) {
		super(mmo);

		this.damage_exp = damage_exp;
		this.kill_exp = kill_exp;
	}

	@Listener(order = Order.LATE)
	@IsCancelled(value = Tristate.FALSE)
	public void onDamage(final DamageEntityEvent e, @First final EntityDamageSource source) {
		if (source.getSource() instanceof Arrow) {
			Arrow arrow = (Arrow) source.getSource();
			if (arrow.getShooter() instanceof Player) {
				super.getMMO().getPlayerDatabase().addExp(super.getMMO(), (Player)arrow.getShooter(), SkillTypes.ARCHERY, e.willCauseDeath() ? this.kill_exp : this.damage_exp);
			}
		}
	}

	@Listener(order = Order.LATE)
	@IsCancelled(value = Tristate.FALSE)
	public void onArrowFire(final LaunchProjectileEvent e) {
		if (e.getTargetEntity() instanceof Arrow && e.getTargetEntity().getShooter() instanceof Player) {
			Player p = (Player) e.getTargetEntity().getShooter();
			PlayerData data = super.getMMO().getPlayerDatabase().get(p.getUniqueId());

			if (Abilities.ARROW_RAIN.getChance(data.getLevel(SkillTypes.ARCHERY))) {
				e.getTargetEntity().offer(Keys.FIRE_TICKS, 1000);
				EntityArchetype a = e.getTargetEntity().createArchetype();
				Vector3d v = e.getTargetEntity().getVelocity();
				for (int i = 0; i < 9; i++) {
					a.offer(Keys.VELOCITY, v.add(0.05 - (Math.random()*0.1), 0.05 - (Math.random()*0.1), 0.05 - (Math.random()*0.1)));
					a.apply(e.getTargetEntity().getLocation(), ServerUtils.getSpawnCause(e.getTargetEntity()));
				}
			}
		}
	}
}