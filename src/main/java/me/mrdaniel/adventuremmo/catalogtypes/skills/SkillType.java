package me.mrdaniel.adventuremmo.catalogtypes.skills;

import java.util.List;
import java.util.function.BiFunction;

import javax.annotation.Nonnull;

import org.spongepowered.api.CatalogType;
import org.spongepowered.api.util.annotation.CatalogedBy;

import com.google.common.collect.Lists;

import me.mrdaniel.adventuremmo.AdventureMMO;
import me.mrdaniel.adventuremmo.catalogtypes.abilities.Ability;
import me.mrdaniel.adventuremmo.io.Config;

@CatalogedBy(SkillTypes.class)
public class SkillType implements CatalogType {

	private final String name;
	private final String id;
	private final BiFunction<AdventureMMO, Config, Object> listener;
	private final List<Ability> abilities;

	SkillType(@Nonnull final String name, @Nonnull final String id, @Nonnull final BiFunction<AdventureMMO, Config, Object> listener, @Nonnull final Ability... abilities) {
		this.name = name;
		this.id = id;
		this.listener = listener;
		this.abilities = Lists.newArrayList(abilities);
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
	public BiFunction<AdventureMMO, Config, Object> getListener() {
		return this.listener;
	}

	@Nonnull
	public List<Ability> getAbilities() {
		return this.abilities;
	}
}