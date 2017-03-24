package me.mrdaniel.adventuremmo.io.items;

import java.util.Optional;

import javax.annotation.Nonnull;

import me.mrdaniel.adventuremmo.catalogtypes.skills.SkillType;
import me.mrdaniel.adventuremmo.catalogtypes.skills.SkillTypes;

public class BlockData {

	private final SkillType skill;
	private final int exp;

	public BlockData(@Nonnull final SkillType skill, final int exp) {
		this.skill = skill;
		this.exp = exp;
	}

	@Nonnull
	public SkillType getSkill() {
		return this.skill;
	}

	public int getExp() {
		return this.exp;
	}

	@Nonnull
	public String serialize() {
		return this.skill.getId() + "," + this.exp;
	}

	@Nonnull
	public static Optional<BlockData> deserialize(@Nonnull final String str) {
		String[] s = str.split(",");
		try { return Optional.of(new BlockData(SkillTypes.of(s[0]).get(), Integer.valueOf(s[1]))); }
		catch (final Exception exc) { return Optional.empty(); }
	}
}