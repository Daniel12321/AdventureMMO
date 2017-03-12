package me.mrdaniel.adventuremmo.event;

import javax.annotation.Nonnull;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Cancellable;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.entity.living.humanoid.player.TargetPlayerEvent;
import org.spongepowered.api.event.impl.AbstractEvent;

import me.mrdaniel.adventuremmo.AdventureMMO;
import me.mrdaniel.adventuremmo.catalogtypes.abilities.Ability;
import me.mrdaniel.adventuremmo.catalogtypes.skills.SkillType;
import me.mrdaniel.adventuremmo.catalogtypes.tools.ToolType;

public abstract class AbilityEvent extends AbstractEvent implements TargetPlayerEvent, Cancellable {

	private final Player player;
	private final Cause cause;
	private boolean cancelled;

	public AbilityEvent(@Nonnull final Player player, @Nonnull final Cause cause) {
		this.player = player;
		this.cause = cause;
		this.cancelled = false;
	}

	@Override
	public Player getTargetEntity() {
		return this.player;
	}

	@Override
	public Cause getCause() {
		return this.cause;
	}

	@Override
	public void setCancelled(final boolean cancelled) {
		this.cancelled = cancelled;
	}

	@Override
	public boolean isCancelled() {
		return this.cancelled;
	}

	/*
	 * Launched before an ability matching the tool is found.
	 */
	public static class Pre extends AbilityEvent {

		private final boolean onblock;

		public Pre(@Nonnull final AdventureMMO mmo, @Nonnull final Player player, @Nonnull final ToolType tool, final boolean onblock) {
			super(player, Cause.source(mmo).named("tool", tool).build());

			this.onblock = onblock;
		}

		public boolean isOnBlock() {
			return this.onblock;
		}
	}

	/*
	 * Launched when an ability is found and ready to activate.
	 */
	public static class Activate extends AbilityEvent {

		public Activate(@Nonnull final AdventureMMO mmo, @Nonnull final Player player, @Nonnull final ToolType tool, @Nonnull final SkillType skill, @Nonnull final Ability ability) {
			super(player, Cause.source(mmo).named("tool", tool).named("skill", skill).named("ability", ability).build());
		}
	}
}