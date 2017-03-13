package me.mrdaniel.adventuremmo.listeners;

import java.util.Map;
import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.effect.particle.ParticleEffect;
import org.spongepowered.api.effect.particle.ParticleTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.event.cause.entity.damage.source.EntityDamageSource;
import org.spongepowered.api.event.entity.DamageEntityEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.filter.cause.Root;
import org.spongepowered.api.scheduler.Task;

import com.flowpowered.math.vector.Vector3d;
import com.google.common.collect.Maps;

import me.mrdaniel.adventuremmo.AdventureMMO;
import me.mrdaniel.adventuremmo.MMOObject;
import me.mrdaniel.adventuremmo.catalogtypes.abilities.Abilities;
import me.mrdaniel.adventuremmo.catalogtypes.abilities.Ability;
import me.mrdaniel.adventuremmo.catalogtypes.abilities.ActiveAbility;
import me.mrdaniel.adventuremmo.catalogtypes.skills.SkillType;
import me.mrdaniel.adventuremmo.catalogtypes.tools.ToolType;
import me.mrdaniel.adventuremmo.data.ToolData;
import me.mrdaniel.adventuremmo.data.manipulators.MMOData;
import me.mrdaniel.adventuremmo.event.AbilityEvent;
import me.mrdaniel.adventuremmo.event.BreakBlockEvent;
import me.mrdaniel.adventuremmo.event.LevelUpEvent;
import me.mrdaniel.adventuremmo.event.PlayerTargetEntityEvent;
import me.mrdaniel.adventuremmo.io.Config;
import me.mrdaniel.adventuremmo.utils.MathUtils;

public class AbilitiesListener extends MMOObject {

	private final Map<Ability, Integer> ability_recharge;

	public AbilitiesListener(@Nonnull final AdventureMMO mmo, @Nonnull final Config config) {
		super(mmo);

		this.ability_recharge = Maps.newHashMap();
		Abilities.VALUES.stream().filter(ability -> ability instanceof ActiveAbility).forEach(ability -> this.ability_recharge.put(ability, config.getNode("abilities", ability.getId(), "recharge_seconds").getInt()));
	}

	@Listener(order = Order.LATE)
	public void onBlockBreak(final ChangeBlockEvent.Break e, @Root final Player p) {
		if (e.isCancelled()) { return; }

		Optional<ToolData> handdata = super.getMMO().getItemDatabase().getData(p.getItemInHand(HandTypes.MAIN_HAND).orElse(null));

		e.getTransactions().forEach(trans -> {
			super.getMMO().getItemDatabase().getData(trans.getOriginal().getState().getType()).ifPresent(blockdata -> {
				if (!trans.getOriginal().getCreator().isPresent()) super.getGame().getEventManager().post(new BreakBlockEvent(super.getMMO(), p, trans.getOriginal().getLocation().orElse(p.getLocation()), blockdata, handdata.map(data -> data.getType()).orElse(null)));
			});
		});
	}

	@Listener(order = Order.LATE)
	public void onDamage(final DamageEntityEvent e, @First final EntityDamageSource source) {
		if (e.isCancelled()) { return; }

		if (source.getSource() instanceof Player) {
			Player damager = (Player) source.getSource();
			super.getMMO().getItemDatabase().getData(damager.getItemInHand(HandTypes.MAIN_HAND).orElse(null)).ifPresent(handdata -> super.getGame().getEventManager().post(new PlayerTargetEntityEvent(super.getMMO(), damager, e, handdata.getType())));
		}
	}

	@Listener(order = Order.LATE)
	public void onBlockClick(final InteractBlockEvent.Secondary.MainHand e, @Root final Player p) {
		if (e.isCancelled()) { return; }
		if (!p.get(Keys.IS_SNEAKING).orElse(false)) { return; }

		Optional<ToolData> handdata = super.getMMO().getItemDatabase().getData(p.getItemInHand(HandTypes.MAIN_HAND).orElse(null));
		if (!handdata.isPresent()) { return; }
		ToolType tool = handdata.get().getType();

		AbilityEvent ae = new AbilityEvent(super.getMMO(), p, tool, e.getTargetBlock().getState().getType() != BlockTypes.AIR);
		super.getGame().getEventManager().post(ae);
		if (ae.isCancelled() || ae.getAbility() == null || ae.getSkill() == null) { return; }
		ActiveAbility ability = ae.getAbility();
		SkillType skill = ae.getSkill();

		MMOData data = p.get(MMOData.class).orElse(new MMOData());
		if (data.isDelayActive(ability.getId())) { super.getMMO().getMessages().sendAbilityRecharge(p, MathUtils.secondsTillTime(data.getDelay(ability.getId()))); return; }

		final double seconds = ability.getSeconds(super.getMMO().getPlayerDatabase().get(p.getUniqueId()).getLevel(skill));
		data.setDelay(ability.getId(), System.currentTimeMillis() + (this.ability_recharge.get(ability) * 1000));
		data.setAbility(ability.getId(), System.currentTimeMillis() + (long) (seconds * 1000));
		p.offer(data);

		super.getMMO().getMessages().sendAbilityActivate(p, ability.getName());
		ability.getActions().activate(p);

		Task.builder().delayTicks((int) (20 * seconds)).execute(() -> {
			super.getMMO().getMessages().sendAbilityEnd(p, ability.getName());
			ability.getActions().deactivate(p);
		}).submit(super.getMMO());
	}

	@Listener(order = Order.LATE)
	public void onLevelUp(final LevelUpEvent e, @First final SkillType skill) {
		if (e.isCancelled()) { return; }

		super.getMMO().getMessages().sendLevelUp(e.getTargetEntity(), skill.getName(), e.getCause().get("new_level", Integer.class).get());

		super.getMMO().getTops().update(skill, e.getTargetEntity().getName(), e.getCause().get("new_level", Integer.class).get());
		super.getMMO().getTops().update(null, e.getTargetEntity().getName(), super.getMMO().getPlayerDatabase().get(e.getTargetEntity().getUniqueId()).getLevels());

		e.getTargetEntity().getWorld().spawnParticles(ParticleEffect.builder().type(ParticleTypes.HAPPY_VILLAGER).quantity(50).offset(new Vector3d(0.8, 0.8, 0.8)).build(), e.getTargetEntity().getLocation().getPosition().add(0,  1.0, 0));
	}
}