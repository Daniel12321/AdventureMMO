package me.mrdaniel.adventuremmo.catalogtypes.skills;

import java.util.Optional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.spongepowered.api.CatalogType;
import org.spongepowered.api.util.annotation.CatalogedBy;

import me.mrdaniel.adventuremmo.catalogtypes.abilities.ActiveAbility;
import me.mrdaniel.adventuremmo.catalogtypes.tools.ToolType;

@CatalogedBy(SkillTypes.class)
public class SkillType implements CatalogType {

	private final String name;
	private final String id;
	private final Optional<ActiveAbility> active_ability;
	private final Optional<ToolType> tool;

	SkillType(@Nonnull final String name, @Nonnull final String id, @Nullable final ActiveAbility active_ability, @Nullable final ToolType tool) {
		this.name = name;
		this.id = id;
		this.active_ability = Optional.ofNullable(active_ability);
		this.tool = Optional.ofNullable(tool);
	}

	@Override
	@Nonnull
	public String getName() {
		return this.name;
	}

	@Override
	@Nonnull
	public String getId() {
		return this.id;
	}

	@Nonnull
	public Optional<ActiveAbility> getActiveAbility() {
		return this.active_ability;
	}

	@Nonnull
	public Optional<ToolType> getTool() {
		return this.tool;
	}
}