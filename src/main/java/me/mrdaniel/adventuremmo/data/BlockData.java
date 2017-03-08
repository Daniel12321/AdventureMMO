package me.mrdaniel.adventuremmo.data;

import java.util.Optional;

import javax.annotation.Nonnull;

import me.mrdaniel.adventuremmo.enums.SkillType;

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
		return this.skill.getID() + "," + this.exp;
	}

	@Nonnull
	public static Optional<BlockData> deserialize(@Nonnull final String str) {
		String[] s = str.split(",");
		try { return Optional.of(new BlockData(SkillType.of(s[0]).get(), Integer.valueOf(s[1]))); }
		catch (final Exception exc) { return Optional.empty(); }
	}
}