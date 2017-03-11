package me.mrdaniel.adventuremmo.listeners;

import javax.annotation.Nonnull;

import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.event.cause.entity.damage.DamageTypes;
import org.spongepowered.api.event.cause.entity.damage.source.DamageSource;
import org.spongepowered.api.event.entity.DamageEntityEvent;
import org.spongepowered.api.event.filter.cause.Root;
import org.spongepowered.api.text.chat.ChatTypes;

import me.mrdaniel.adventuremmo.AdventureMMO;
import me.mrdaniel.adventuremmo.MMOObject;
import me.mrdaniel.adventuremmo.catalogtypes.abilities.Abilities;
import me.mrdaniel.adventuremmo.catalogtypes.skills.SkillTypes;
import me.mrdaniel.adventuremmo.data.manipulators.MMOData;
import me.mrdaniel.adventuremmo.io.PlayerData;
import me.mrdaniel.adventuremmo.utils.MathUtils;

public class AbilityListener extends MMOObject {

	public AbilityListener(@Nonnull final AdventureMMO mmo) {
		super(mmo);
	}

//	@Listener public void onItemClick(final InteractItemEvent.Secondary.MainHand e, @Root final Player p) {}

	@Listener
	public void onBlockClick(final InteractBlockEvent.Secondary.MainHand e, @Root final Player p) {
		if (p.get(Keys.IS_SNEAKING).orElse(false)) {
			super.getMMO().getItemDatabase().getData(p.getItemInHand(HandTypes.MAIN_HAND).orElse(null)).ifPresent(handdata -> {
				SkillTypes.of(handdata.getType(), e.getTargetBlock().getState().getType() != BlockTypes.AIR).ifPresent(skill -> skill.getActiveAbility().ifPresent(ability -> {
					MMOData data = p.get(MMOData.class).orElse(new MMOData());
					if (data.isDelayActive(ability.getId())) {
						p.sendMessage(ChatTypes.ACTION_BAR, super.getMMO().getConfig().getAbilityRechargingText(MathUtils.secondsTillTime(data.getDelay(ability.getId()))));
						return;
					}
					p.sendMessage(ChatTypes.ACTION_BAR, super.getMMO().getConfig().getAbilityActivateText(ability.getName()));
					ability.activate(p, super.getMMO().getPlayerDatabase().get(p.getUniqueId()).getLevel(skill));
					data.setDelay(ability.getId(), System.currentTimeMillis() + (super.getMMO().getConfig().getAbilityRechargeSeconds()*1000));
					p.offer(data);
				}));
			});
		}
	}

	@Listener(order = Order.EARLY)
	public void onDamange(final DamageEntityEvent e) {
		if (e.getTargetEntity() instanceof Player) {
			Player p = (Player) e.getTargetEntity();
			PlayerData data = super.getMMO().getPlayerDatabase().get(p.getUniqueId());

			if (Abilities.DODGE.getChance(data.getLevel(SkillTypes.ACROBATICS))) {
				e.setCancelled(true);
				p.sendMessage(ChatTypes.ACTION_BAR, super.getMMO().getConfig().getDodgeText());
			}

			e.getCause().first(DamageSource.class).ifPresent(source -> {
				if (source.getType() == DamageTypes.FALL && Abilities.ROLL.getChance(data.getLevel(SkillTypes.ACROBATICS))) {
					e.setCancelled(true);
					p.sendMessage(ChatTypes.ACTION_BAR, super.getMMO().getConfig().getRollText());
				}
			});
		}
	}
}