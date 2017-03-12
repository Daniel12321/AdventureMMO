package me.mrdaniel.adventuremmo.catalogtypes.abilities;

import javax.annotation.Nonnull;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.scheduler.Task;

import me.mrdaniel.adventuremmo.AdventureMMO;
import me.mrdaniel.adventuremmo.data.manipulators.MMOData;

public abstract class ActiveAbility extends Ability {

	public ActiveAbility(@Nonnull final String name, @Nonnull final String id, final double initial, final double increment) {
		super(name, id, initial, increment);
	}

	public int getSeconds(final int level) {
		return (int) (this.getInitial() + (this.getIncrement()*level));
	}

	public void activate(@Nonnull final AdventureMMO mmo, @Nonnull final Player p, final int level) {
		mmo.getMessages().sendAbilityActivate(p, super.getName());

		final int seconds = this.getSeconds(level);

		MMOData data = p.get(MMOData.class).orElse(new MMOData());
		data.setAbility(super.getId(), System.currentTimeMillis() + (seconds * 1000));
		p.offer(data);

		Task.builder().delayTicks(20 * seconds).execute(() -> {
			mmo.getMessages().sendAbilityEnd(p, super.getName());
			this.deactivate(p);
		}).submit(mmo);
	}

	protected abstract void activate(Player p);
	protected abstract void deactivate(Player p);
}