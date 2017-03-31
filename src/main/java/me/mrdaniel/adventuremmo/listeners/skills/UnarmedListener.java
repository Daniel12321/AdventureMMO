package me.mrdaniel.adventuremmo.listeners.skills;

import javax.annotation.Nonnull;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.ArmorEquipable;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.util.Tristate;

import me.mrdaniel.adventuremmo.AdventureMMO;
import me.mrdaniel.adventuremmo.catalogtypes.abilities.Abilities;
import me.mrdaniel.adventuremmo.catalogtypes.skills.SkillTypes;
import me.mrdaniel.adventuremmo.catalogtypes.tools.ToolTypes;
import me.mrdaniel.adventuremmo.data.manipulators.MMOData;
import me.mrdaniel.adventuremmo.event.PlayerDamageEntityEvent;
import me.mrdaniel.adventuremmo.io.playerdata.PlayerData;
import me.mrdaniel.adventuremmo.utils.ItemUtils;

public class UnarmedListener extends ActiveAbilityListener  {

	private final int damage_exp;
	private final int kill_exp;

	public UnarmedListener(@Nonnull final AdventureMMO mmo, final int damage_exp, final int kill_exp) {
		super(mmo, Abilities.SAITAMA_PUNCH, SkillTypes.UNARMED, ToolTypes.HAND, Tristate.UNDEFINED);

		this.damage_exp = damage_exp;
		this.kill_exp = kill_exp;
	}

	@Listener
	public void onTarget(final PlayerDamageEntityEvent e) {
		if (e.getTool() != null && e.getTool() == super.tool) {
			PlayerData pdata = super.getMMO().getPlayerDatabase().addExp(super.getMMO(), e.getPlayer(), super.skill, e.isDeath() ? this.kill_exp : this.damage_exp);
			Entity target = e.getEntity();
			if (!e.isDeath()) {
				if (Abilities.DISARM.getChance(pdata.getLevel(super.skill)) && target instanceof ArmorEquipable) {
					ArmorEquipable ae = (ArmorEquipable) target;
					ae.getItemInHand(HandTypes.MAIN_HAND).ifPresent(item -> {
						ae.setItemInHand(HandTypes.MAIN_HAND, null);
						ItemUtils.drop(target.getLocation(), item.createSnapshot()).offer(Keys.PICKUP_DELAY, 30);
						super.getMMO().getMessages().sendDisarm(e.getPlayer());
					});
				}
				if (e.getPlayer().get(MMOData.class).orElse(new MMOData()).isAbilityActive(super.ability.getId())) {
					Task.builder().delayTicks(0).execute(() -> target.setVelocity(target.getVelocity().mul(6.0, 3.0, 6.0))).submit(super.getMMO());
				}
			}
		}
	}
}