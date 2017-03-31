package me.mrdaniel.adventuremmo.listeners.skills;

import javax.annotation.Nonnull;

import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.cause.entity.damage.DamageTypes;
import org.spongepowered.api.event.cause.entity.damage.source.DamageSource;
import org.spongepowered.api.util.Tristate;

import com.flowpowered.math.vector.Vector3d;

import me.mrdaniel.adventuremmo.AdventureMMO;
import me.mrdaniel.adventuremmo.catalogtypes.abilities.Abilities;
import me.mrdaniel.adventuremmo.catalogtypes.skills.SkillTypes;
import me.mrdaniel.adventuremmo.catalogtypes.tools.ToolTypes;
import me.mrdaniel.adventuremmo.data.manipulators.MMOData;
import me.mrdaniel.adventuremmo.event.PlayerDamageEntityEvent;
import me.mrdaniel.adventuremmo.io.playerdata.PlayerData;
import me.mrdaniel.adventuremmo.utils.ItemUtils;

public class AxesListener extends ActiveAbilityListener  {

	private final int damage_exp;
	private final int kill_exp;

	public AxesListener(@Nonnull final AdventureMMO mmo, final int damage_exp, final int kill_exp) {
		super(mmo, Abilities.SLAUGHTER, SkillTypes.AXES, ToolTypes.AXE, Tristate.FALSE);

		this.damage_exp = damage_exp;
		this.kill_exp = kill_exp;
	}

	@Listener
	public void onTarget(final PlayerDamageEntityEvent e) {
		if (e.getTool() != null && e.getTool() == super.tool) {
			Entity target = e.getEntity();
			PlayerData pdata = super.getMMO().getPlayerDatabase().addExp(super.getMMO(), e.getPlayer(), super.skill, e.isDeath() ? this.kill_exp : this.damage_exp);

			if (e.isDeath() && Abilities.DECAPITATE.getChance(pdata.getLevel(super.skill))) {
				if (target instanceof Player) { ItemUtils.drop(target.getLocation(), ItemUtils.getPlayerHead((Player)target).createSnapshot()); }
				else { ItemUtils.getHead(target.getType()).ifPresent(item -> ItemUtils.drop(target.getLocation(), item.createSnapshot())); }
			}
			else if (e.getPlayer().get(MMOData.class).orElse(new MMOData()).isAbilityActive(super.ability.getId())) {
				final Vector3d pos = target.getLocation().getPosition();
				target.getNearbyEntities(ent -> ent.getLocation().getPosition().distance(pos) < 2.0 && !ent.equals(e.getPlayer())).forEach(ent -> {
					ent.damage(e.getDamage(), DamageSource.builder().type(DamageTypes.CUSTOM).build(), e.getCause());
				});
			}
		}
	}
}