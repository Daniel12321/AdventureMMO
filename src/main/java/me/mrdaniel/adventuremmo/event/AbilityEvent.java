package me.mrdaniel.adventuremmo.event;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Cancellable;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.impl.AbstractEvent;

import me.mrdaniel.adventuremmo.AdventureMMO;
import me.mrdaniel.adventuremmo.catalogtypes.abilities.ActiveAbility;
import me.mrdaniel.adventuremmo.catalogtypes.skills.SkillType;
import me.mrdaniel.adventuremmo.catalogtypes.tools.ToolType;

public class AbilityEvent extends AbstractEvent implements Cancellable {

	private final Player player;
	private final Cause cause;
	private final ToolType tool;
	private final boolean onblock;
	private boolean cancelled;

	@Nullable private ActiveAbility ability;
	@Nullable private SkillType skill;

	public AbilityEvent(@Nonnull final AdventureMMO mmo, @Nonnull final Player player, @Nonnull final ToolType tool, final boolean onblock) {
		this.player = player;
		this.cause = Cause.source(mmo.getContainer()).named("tool", tool).build();
		this.tool = tool;
		this.onblock = onblock;
		this.cancelled = false;

		this.ability = null;
		this.skill = null;
	}

	@Nonnull
	public Player getPlayer() {
		return this.player;
	}

	@Nonnull
	public ToolType getTool() {
		return this.tool;
	}

	public boolean isOnBlock() {
		return this.onblock;
	}

	@Nullable 
	public ActiveAbility getAbility() {
		return this.ability;
	}

	public void setAbility(@Nullable final ActiveAbility ability) {
		this.ability = ability;
	}

	@Nullable
	public SkillType getSkill() {
		return this.skill;
	}

	public void setSkill(@Nullable final SkillType skill) {
		this.skill = skill;
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
}
//	/*
//	 * Launched before an ability matching the tool is found.
//	 */
//	public static class Pre extends AbilityEvent {
//
//		private final boolean onblock;
//
//		public Pre(@Nonnull final AdventureMMO mmo, @Nonnull final Player player, @Nonnull final ToolType tool, final boolean onblock) {
//			super(player, Cause.source(mmo).named("tool", tool).build());
//
//			this.onblock = onblock;
//		}
//
//		public boolean isOnBlock() {
//			return this.onblock;
//		}
//	}
//
//	/*
//	 * Launched when an ability is found and ready to activate.
//	 */
//	public static class Activate extends AbilityEvent {
//
//		public Activate(@Nonnull final AdventureMMO mmo, @Nonnull final Player player, @Nonnull final ToolType tool, @Nonnull final SkillType skill, @Nonnull final ActiveAbility ability) {
//			super(player, Cause.source(mmo).named("tool", tool).named("skill", skill).named("ability", ability).build());
//		}
//	}
//}