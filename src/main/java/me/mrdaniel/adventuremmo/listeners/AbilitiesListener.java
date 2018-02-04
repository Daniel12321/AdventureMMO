package me.mrdaniel.adventuremmo.listeners;

import java.util.Map;
import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.effect.particle.ParticleEffect;
import org.spongepowered.api.effect.particle.ParticleTypes;
import org.spongepowered.api.effect.sound.SoundTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.event.cause.entity.damage.source.EntityDamageSource;
import org.spongepowered.api.event.entity.DamageEntityEvent;
import org.spongepowered.api.event.filter.IsCancelled;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.filter.cause.Root;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.util.Tristate;

import com.flowpowered.math.vector.Vector3d;
import com.google.common.collect.Maps;

import me.mrdaniel.adventuremmo.AdventureMMO;
import me.mrdaniel.adventuremmo.MMOObject;
import me.mrdaniel.adventuremmo.catalogtypes.abilities.Abilities;
import me.mrdaniel.adventuremmo.catalogtypes.abilities.Ability;
import me.mrdaniel.adventuremmo.catalogtypes.abilities.ActiveAbility;
import me.mrdaniel.adventuremmo.catalogtypes.skills.SkillType;
import me.mrdaniel.adventuremmo.catalogtypes.tools.ToolType;
import me.mrdaniel.adventuremmo.data.manipulators.MMOData;
import me.mrdaniel.adventuremmo.event.AbilityEvent;
import me.mrdaniel.adventuremmo.event.BreakBlockEvent;
import me.mrdaniel.adventuremmo.event.LevelUpEvent;
import me.mrdaniel.adventuremmo.event.PlayerDamageEntityEvent;
import me.mrdaniel.adventuremmo.io.Config;
import me.mrdaniel.adventuremmo.io.items.ToolData;
import me.mrdaniel.adventuremmo.utils.MathUtils;

public class AbilitiesListener extends MMOObject {

	private final Map<Ability, Integer> ability_recharge;

	public AbilitiesListener(@Nonnull final AdventureMMO mmo, @Nonnull final Config config) {
		super(mmo);

		this.ability_recharge = Maps.newHashMap();
		Abilities.VALUES.stream().filter(ability -> ability instanceof ActiveAbility)
				.forEach(ability -> this.ability_recharge.put(ability,
						config.getNode("abilities", ability.getId(), "recharge_seconds").getInt(300)));
	}

	@Listener(order = Order.LATE)
	@IsCancelled(value = Tristate.FALSE)
	public void onBlockBreak(final ChangeBlockEvent.Break e, @Root final Player p) {
		Optional<ToolData> handdata = super.getMMO().getItemDatabase()
				.getData(p.getItemInHand(HandTypes.MAIN_HAND).orElse(null));

		e.getTransactions().forEach(trans -> {
			super.getMMO().getItemDatabase().getData(trans.getOriginal().getState().getType()).ifPresent(blockdata -> {
				if (!trans.getOriginal().getCreator().isPresent())
					super.getGame().getEventManager()
							.post(new BreakBlockEvent(super.getMMO(), p,
									trans.getOriginal().getLocation().orElse(p.getLocation()), blockdata,
									handdata.map(data -> data.getType()).orElse(null)));
			});
		});
	}

	@Listener(order = Order.LATE)
	@IsCancelled(value = Tristate.FALSE)
	public void onDamage(final DamageEntityEvent e, @First final EntityDamageSource source) {
		if (source.getSource() instanceof Player) {
			Player p = (Player) source.getSource();
			super.getMMO().getItemDatabase().getData(p.getItemInHand(HandTypes.MAIN_HAND).orElse(null)).ifPresent(
					handdata -> super.getGame().getEventManager().post(new PlayerDamageEntityEvent(super.getMMO(), p,
							e.getTargetEntity(), handdata.getType(), e.getFinalDamage(), e.willCauseDeath())));
		}
	}

	@Listener(order = Order.LATE)
	@IsCancelled(value = Tristate.FALSE)
	public void onBlockClick(final InteractBlockEvent.Secondary.MainHand e, @Root final Player p) {
		if (!p.get(Keys.IS_SNEAKING).orElse(false)) {
			return;
		}

		Optional<ToolData> handdata = super.getMMO().getItemDatabase()
				.getData(p.getItemInHand(HandTypes.MAIN_HAND).orElse(null));
		if (!handdata.isPresent()) {
			return;
		}
		ToolType tool = handdata.get().getType();

		AbilityEvent ae = new AbilityEvent(super.getMMO(), p, tool,
				e.getTargetBlock().getState().getType() != BlockTypes.AIR);
		super.getGame().getEventManager().post(ae);
		if (ae.isCancelled() || ae.getAbility() == null || ae.getSkill() == null) {
			return;
		}
		ActiveAbility ability = ae.getAbility();
		SkillType skill = ae.getSkill();

		MMOData data = p.get(MMOData.class).orElse(new MMOData());
		if (data.isDelayActive(ability.getId())) {
			super.getMMO().getMessages().sendAbilityRecharge(p,
					MathUtils.secondsTillTime(data.getDelay(ability.getId())));
			return;
		}

		final double seconds = ability
				.getSeconds(super.getMMO().getPlayerDatabase().get(p.getUniqueId()).getLevel(skill));
		data.setDelay(ability.getId(), System.currentTimeMillis() + (this.ability_recharge.get(ability) * 1000));
		data.setAbility(ability.getId(), System.currentTimeMillis() + (long) (seconds * 1000));
		p.offer(data);

		ability.getActions().activate(super.getMMO(), p);

		super.getMMO().getMessages().sendAbilityActivate(p, ability);
		p.getWorld().spawnParticles(ParticleEffect.builder().type(ParticleTypes.FIREWORKS_SPARK).quantity(50)
				.offset(new Vector3d(1.2, 1.2, 1.2)).build(), p.getLocation().getPosition().add(0.0, 1.0, 0.0));
		p.getWorld().playSound(SoundTypes.ENTITY_FIREWORK_LAUNCH, p.getLocation().getPosition().add(0.0, 1.0, 0.0),
				1.0);

		Task.builder().delayTicks((int) (20 * seconds)).execute(() -> {
			ability.getActions().deactivate(super.getMMO(), p);

			super.getMMO().getMessages().sendAbilityEnd(p, ability);
			p.getWorld().spawnParticles(ParticleEffect.builder().type(ParticleTypes.LAVA).quantity(20)
					.offset(new Vector3d(1.2, 1.2, 1.2)).build(), p.getLocation().getPosition().add(0.0, 1.0, 0.0));
			p.getWorld().playSound(SoundTypes.BLOCK_LAVA_EXTINGUISH, p.getLocation().getPosition().add(0.0, 1.0, 0.0),
					1.0);
		}).submit(super.getMMO());
	}

	@Listener(order = Order.LATE)
	@IsCancelled(value = Tristate.FALSE)
	public void onLevelUp(final LevelUpEvent e) {
		super.getMMO().getMessages().sendLevelUp(e.getPlayer(), e.getSkill(), e.getNewLevel());

		super.getMMO().getTops().update(e.getPlayer().getName(), e.getSkill(), e.getNewLevel());
		super.getMMO().getTops().update(e.getPlayer().getName(), null,
				super.getMMO().getPlayerDatabase().get(e.getPlayer().getUniqueId()).getLevels());

		e.getPlayer().getWorld().spawnParticles(
				ParticleEffect.builder().type(ParticleTypes.HAPPY_VILLAGER).quantity(50)
						.offset(new Vector3d(1.2, 1.2, 1.2)).build(),
				e.getPlayer().getLocation().getPosition().add(0.0, 1.0, 0.0));
	}
}