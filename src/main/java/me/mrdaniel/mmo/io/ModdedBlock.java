package me.mrdaniel.mmo.io;

import javax.annotation.Nonnull;

import me.mrdaniel.mmo.enums.SkillType;

public class ModdedBlock {
	
	@Nonnull public final String id;
	@Nonnull public final SkillType type;
	public final int exp;
	
	public ModdedBlock(@Nonnull final String id, @Nonnull final SkillType type, final int exp) {
		this.id = id;
		this.type = type;
		this.exp = exp;
	}
}