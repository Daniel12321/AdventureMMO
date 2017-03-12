package me.mrdaniel.adventuremmo.listeners;

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

import com.flowpowered.math.vector.Vector3d;

import me.mrdaniel.adventuremmo.AdventureMMO;
import me.mrdaniel.adventuremmo.MMOObject;
import me.mrdaniel.adventuremmo.catalogtypes.abilities.Ability;
import me.mrdaniel.adventuremmo.catalogtypes.skills.SkillType;
import me.mrdaniel.adventuremmo.data.ToolData;
import me.mrdaniel.adventuremmo.data.manipulators.MMOData;
import me.mrdaniel.adventuremmo.event.AbilityEvent;
import me.mrdaniel.adventuremmo.event.BreakBlockEvent;
import me.mrdaniel.adventuremmo.event.LevelUpEvent;
import me.mrdaniel.adventuremmo.event.PlayerTargetEntityEvent;
import me.mrdaniel.adventuremmo.utils.MathUtils;

public class AbilitiesListener extends MMOObject {

	public AbilitiesListener(@Nonnull final AdventureMMO mmo) {
		super(mmo);
	}

	@Listener(order = Order.LATE)
	public void onBlockBreak(final ChangeBlockEvent.Break e, @Root final Player p) {
		if (e.isCancelled()) { return; }

		ToolData handdata = super.getMMO().getItemDatabase().getData(p.getItemInHand(HandTypes.MAIN_HAND).orElse(null)).orElse(null);

		e.getTransactions().forEach(trans -> {
			super.getMMO().getItemDatabase().getData(trans.getOriginal().getState().getType()).ifPresent(blockdata -> {
				super.getGame().getEventManager().post(new BreakBlockEvent(super.getMMO(), p, blockdata, handdata));
			});
		});
	}

	@Listener(order = Order.LATE)
	public void onDamage(final DamageEntityEvent e, @First final EntityDamageSource source) {
		if (e.isCancelled()) { return; }

		if (source.getSource() instanceof Player) {
			Player damager = (Player) source.getSource();
			if (e.willCauseDeath()) { super.getMMO().getItemDatabase().getData(damager.getItemInHand(HandTypes.MAIN_HAND).orElse(null)).ifPresent(handdata -> super.getGame().getEventManager().post(new PlayerTargetEntityEvent.Kill(super.getMMO(), damager, e.getTargetEntity(), handdata.getType()))); }
			else { super.getMMO().getItemDatabase().getData(damager.getItemInHand(HandTypes.MAIN_HAND).orElse(null)).ifPresent(handdata -> super.getGame().getEventManager().post(new PlayerTargetEntityEvent.Damage(super.getMMO(), damager, e.getTargetEntity(), handdata.getType()))); }
		}
	}

	@Listener(order = Order.LATE)
	public void onBlockClick(final InteractBlockEvent.Secondary.MainHand e, @Root final Player p) {
		if (e.isCancelled()) { return; }

		if (p.get(Keys.IS_SNEAKING).orElse(false)) {
			super.getMMO().getItemDatabase().getData(p.getItemInHand(HandTypes.MAIN_HAND).orElse(null)).ifPresent(handdata -> {
				super.getGame().getEventManager().post(new AbilityEvent.Pre(super.getMMO(), p, handdata.getType(), e.getTargetBlock().getState().getType() != BlockTypes.AIR));
			});
		}
	}

	@Listener(order = Order.LATE)
	public void onAbility(final AbilityEvent.Activate e, @First final Ability ability) {
		if (e.isCancelled()) { return; }

		MMOData data = e.getTargetEntity().get(MMOData.class).orElse(new MMOData());
		if (data.isDelayActive(ability.getId())) {
			super.getMMO().getMessages().sendAbilityRecharge(e.getTargetEntity(), MathUtils.secondsTillTime(data.getDelay(ability.getId())));
			e.setCancelled(true);
		}
		else {
			data.setDelay(ability.getId(), System.currentTimeMillis() + (super.getMMO().getConfig().getAbilityRechargeSeconds()*1000));
			e.getTargetEntity().offer(data);
		}
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