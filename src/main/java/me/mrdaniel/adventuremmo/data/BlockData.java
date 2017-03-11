package me.mrdaniel.adventuremmo.data;

import java.util.Optional;

import javax.annotation.Nonnull;

import me.mrdaniel.adventuremmo.catalogtypes.skills.SkillType;
import me.mrdaniel.adventuremmo.catalogtypes.skills.SkillTypes;
import me.mrdaniel.adventuremmo.catalogtypes.tools.ToolType;
import me.mrdaniel.adventuremmo.catalogtypes.tools.ToolTypes;

public class BlockData {

	private final SkillType skill;
	private final int exp;
	private final Optional<ToolType> tool;

	public BlockData(@Nonnull final SkillType skill, final int exp, @Nonnull final Optional<ToolType> tool) {
		this.skill = skill;
		this.exp = exp;
		this.tool = tool;
	}

	@Nonnull
	public SkillType getSkill() {
		return this.skill;
	}

	public int getExp() {
		return this.exp;
	}

	@Nonnull
	public Optional<ToolType> getTool() {
		return this.tool;
	}

	@Nonnull
	public String serialize() {
		return this.skill.getId() + "," + this.exp + (this.tool.isPresent() ? ("," + this.tool.get().getId()) : "");
	}

	@Nonnull
	public static Optional<BlockData> deserialize(@Nonnull final String str) {
		String[] s = str.split(",");
		try {
			if (s.length == 3) { return Optional.of(new BlockData(SkillTypes.of(s[0]).get(), Integer.valueOf(s[1]), ToolTypes.of(s[2]))); }
			else { return Optional.of(new BlockData(SkillTypes.of(s[0]).get(), Integer.valueOf(s[1]), Optional.empty())); }
		}
		catch (final Exception exc) { return Optional.empty(); }
	}
}