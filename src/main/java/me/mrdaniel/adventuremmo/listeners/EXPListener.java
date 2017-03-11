package me.mrdaniel.adventuremmo.listeners;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.cause.entity.damage.DamageTypes;
import org.spongepowered.api.event.cause.entity.damage.source.DamageSource;
import org.spongepowered.api.event.entity.DamageEntityEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.filter.cause.Root;
import org.spongepowered.api.text.chat.ChatTypes;

import me.mrdaniel.adventuremmo.AdventureMMO;
import me.mrdaniel.adventuremmo.MMOObject;
import me.mrdaniel.adventuremmo.catalogtypes.skills.SkillType;
import me.mrdaniel.adventuremmo.catalogtypes.skills.SkillTypes;
import me.mrdaniel.adventuremmo.data.ToolData;
import me.mrdaniel.adventuremmo.event.LevelUpEvent;

public class EXPListener extends MMOObject {

	public EXPListener(@Nonnull final AdventureMMO mmo) {
		super(mmo);
	}

	@Listener(order = Order.LATE)
	public void onBlockBreak(final ChangeBlockEvent.Break e, @Root final Player p) {
		if (e.isCancelled()) { return; }

		Optional<ToolData> handdata = super.getMMO().getItemDatabase().getData(p.getItemInHand(HandTypes.MAIN_HAND).orElse(null));
		e.getTransactions().forEach(trans -> super.getMMO().getItemDatabase().getData(trans.getOriginal().getState().getType()).ifPresent(blockdata -> {
			if (!trans.getOriginal().getCreator().isPresent()) {
				if ((!blockdata.getTool().isPresent()) || (blockdata.getTool().isPresent() && handdata.isPresent() && blockdata.getTool().get() == handdata.get().getType())) {
					super.getMMO().getPlayerDatabase().get(p.getUniqueId()).addExp(p, blockdata.getSkill(), blockdata.getExp());
				}
			}
		}));

//		super.getMMO().getItemDatabase().getData(p.getItemInHand(HandTypes.MAIN_HAND).orElse(null)).ifPresent(hand -> e.getTransactions().forEach(transaction -> super.getMMO().getItemDatabase().getData(transaction.getOriginal().getState().getType()).ifPresent(data -> {
//			Optional<UUID> creator = transaction.getOriginal().getCreator();
//			if (creator.isPresent()) { p.sendMessage(Text.of("Creator: ", creator.get().toString())); }
//			else { p.sendMessage(Text.of("Creator not found!")); }
//
//			if (data.getSkill().getTool().isPresent() && data.getSkill().getTool().get() == hand.getType()) {
//				super.getMMO().getPlayerDatabase().get(p.getUniqueId()).addExp(p, data.getSkill(), data.getExp());
//			}
//		})));
	}

	@Listener(order = Order.LATE)
	public void onDamage(final DamageEntityEvent e) {
		if (e.isCancelled()) { return; }

		if (e.getTargetEntity() instanceof Player) {
			Player p = (Player) e.getTargetEntity();
			e.getCause().first(DamageSource.class).ifPresent(source -> {
				if (source.getType() == DamageTypes.FALL) {
					super.getMMO().getPlayerDatabase().get(p.getUniqueId()).addExp(p, SkillTypes.ACROBATICS, (int) (5.0 * e.getOriginalDamage()));
				}
			});
		}
	}

	@Listener(order = Order.LATE)
	public void onLevelUp(final LevelUpEvent e, @First final SkillType type) {
		if (e.isCancelled()) { return; }

		e.getTargetEntity().sendMessage(ChatTypes.ACTION_BAR, super.getMMO().getConfig().getLevelUpText(type.getName(), e.getCause().get("new_level", Integer.class).get()));

		super.getMMO().getTops().update(type, e.getTargetEntity().getName(), e.getCause().get("new_level", Integer.class).get());
		super.getMMO().getTops().update(null, e.getTargetEntity().getName(), super.getMMO().getPlayerDatabase().get(e.getTargetEntity().getUniqueId()).getLevels());

//		e.getTargetEntity().getWorld().spawnParticles(ParticleEffect.builder().type(ParticleTypes.HAPPY_VILLAGER).quantity(50).offset(new Vector3d(0.8, 0.8, 0.8)).build(), e.getTargetEntity().getLocation().getPosition().add(0,  1.0, 0));
	}
}