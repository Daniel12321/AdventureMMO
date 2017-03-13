package me.mrdaniel.adventuremmo.listeners.skills;

import java.util.function.Consumer;

import javax.annotation.Nonnull;

import org.spongepowered.api.effect.particle.ParticleEffect;
import org.spongepowered.api.effect.particle.ParticleOptions;
import org.spongepowered.api.effect.particle.ParticleTypes;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.cause.entity.damage.source.DamageSource;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.util.Color;
import org.spongepowered.api.util.Tristate;

import com.flowpowered.math.vector.Vector3d;

import me.mrdaniel.adventuremmo.AdventureMMO;
import me.mrdaniel.adventuremmo.catalogtypes.abilities.Abilities;
import me.mrdaniel.adventuremmo.catalogtypes.skills.SkillTypes;
import me.mrdaniel.adventuremmo.catalogtypes.tools.ToolType;
import me.mrdaniel.adventuremmo.catalogtypes.tools.ToolTypes;
import me.mrdaniel.adventuremmo.data.manipulators.MMOData;
import me.mrdaniel.adventuremmo.event.PlayerTargetEntityEvent;
import me.mrdaniel.adventuremmo.io.PlayerData;
import me.mrdaniel.adventuremmo.utils.ItemUtils;

public class SwordsListener extends ActiveAbilityListener  {

	private final int damage_exp;
	private final int kill_exp;

	public SwordsListener(@Nonnull final AdventureMMO mmo, final int damage_exp, final int kill_exp) {
		super(mmo, Abilities.BLOODSHED, SkillTypes.SWORDS, ToolTypes.SWORD, Tristate.UNDEFINED);

		this.damage_exp = damage_exp;
		this.kill_exp = kill_exp;
	}

	@Listener
	public void onTarget(final PlayerTargetEntityEvent e, @First final ToolType tool) {
		if (tool == ToolTypes.SWORD) {
			PlayerData pdata = super.getMMO().getPlayerDatabase().get(e.getPlayer().getUniqueId());
			Entity target = e.getOriginalEvent().getTargetEntity();
			if (e.getOriginalEvent().willCauseDeath()) {
				pdata.addExp(e.getPlayer(), super.skill, this.kill_exp);
				if (Abilities.DECAPITATE.getChance(pdata.getLevel(super.skill))) {
					if (target instanceof Player) { ItemUtils.drop(target.getLocation(), ItemUtils.getPlayerHead((Player)target).createSnapshot()); }
					else { ItemUtils.getHead(target.getType()).ifPresent(item -> ItemUtils.drop(target.getLocation(), item.createSnapshot())); }
				}
			}
			else {
				pdata.addExp(e.getPlayer(), super.skill, this.damage_exp);
				if (e.getPlayer().get(MMOData.class).orElse(new MMOData()).isAbilityActive(super.ability.getId())) {
					Task.builder().delayTicks(15).intervalTicks(15).execute(new Consumer<Task>() {
						final DamageSource source = e.getOriginalEvent().getCause().first(DamageSource.class).get();
						int i = 8;
						@Override public void accept(@Nonnull final Task t) {
							if (i-- > 0) {
								target.damage(1, source);
								target.getWorld().spawnParticles(ParticleEffect.builder().type(ParticleTypes.REDSTONE_DUST).option(ParticleOptions.COLOR, Color.RED).option(ParticleOptions.VELOCITY, new Vector3d(0, -0.1, 0)).offset(new Vector3d(0.5, 0.5, 0.5)).quantity(50).build(), target.getLocation().getPosition());
							}
							else { t.cancel(); }
						}
					}).submit(super.getMMO());
				}
			}
		}
	}
}