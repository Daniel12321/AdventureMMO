package me.mrdaniel.adventuremmo.listeners.skills;

import javax.annotation.Nonnull;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.ArmorEquipable;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.util.Tristate;

import me.mrdaniel.adventuremmo.AdventureMMO;
import me.mrdaniel.adventuremmo.catalogtypes.abilities.Abilities;
import me.mrdaniel.adventuremmo.catalogtypes.skills.SkillTypes;
import me.mrdaniel.adventuremmo.catalogtypes.tools.ToolType;
import me.mrdaniel.adventuremmo.catalogtypes.tools.ToolTypes;
import me.mrdaniel.adventuremmo.data.manipulators.MMOData;
import me.mrdaniel.adventuremmo.event.PlayerTargetEntityEvent;
import me.mrdaniel.adventuremmo.io.PlayerData;
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
	public void onTarget(final PlayerTargetEntityEvent e, @First final ToolType tool) {
		if (tool == ToolTypes.HAND) {
			PlayerData pdata = super.getMMO().getPlayerDatabase().get(e.getPlayer().getUniqueId());
			Entity target = e.getOriginalEvent().getTargetEntity();
			if (e.getOriginalEvent().willCauseDeath()) { pdata.addExp(e.getPlayer(), super.skill, this.kill_exp); }
			else {
				pdata.addExp(e.getPlayer(), super.skill, this.damage_exp);
				if (Abilities.DISARM.getChance(pdata.getLevel(super.skill)) && target instanceof ArmorEquipable) {
					((ArmorEquipable)target).getItemInHand(HandTypes.MAIN_HAND).ifPresent(item -> {
						((ArmorEquipable)target).setItemInHand(HandTypes.MAIN_HAND, null);
						Entity ent = ItemUtils.drop(target.getLocation(), item.createSnapshot());
						ent.offer(Keys.PICKUP_DELAY, 30);
					});
				}
				if (e.getPlayer().get(MMOData.class).orElse(new MMOData()).isAbilityActive(super.ability.getId())) {
					Task.builder().delayTicks(0).execute(() -> {
						target.setVelocity(target.getVelocity().mul(6.0, 3.0, 6.0));
					}).submit(super.getMMO());
				}
			}
		}
	}
}