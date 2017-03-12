package me.mrdaniel.adventuremmo.catalogtypes.abilities;

import javax.annotation.Nonnull;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class ActiveAbility extends Ability {

	private final ActiveAbilityActions actions;

	public ActiveAbility(@Nonnull final String name, @Nonnull final String id, @Nonnull final ActiveAbilityActions actions) {
		super(name, id);

		this.actions = actions;
	}

	public int getSeconds(final int level) {
		return (int) Math.min(this.getInitial() + (this.getIncrement()*level), this.getCap());
	}

	@Nonnull
	public ActiveAbilityActions getActions() {
		return this.actions;
	}

	@Override
	public Text getValueLine(final int level) {
		return Text.of(TextColors.YELLOW, "Duration: ", this.getSeconds(level), "s");
	}

//	public void activate(@Nonnull final AdventureMMO mmo, @Nonnull final Player p, final int level) {
//		mmo.getMessages().sendAbilityActivate(p, super.getName());
//		this.actions.activate(p);
//
//		final int seconds = this.getSeconds(level);
//
//		MMOData data = p.get(MMOData.class).orElse(new MMOData());
//		data.setAbility(super.getId(), System.currentTimeMillis() + (seconds * 1000));
//		p.offer(data);
//
//		Task.builder().delayTicks(20 * seconds).execute(() -> {
//			mmo.getMessages().sendAbilityEnd(p, super.getName());
//			this.actions.deactivate(p);
//		}).submit(mmo);
//	}
}