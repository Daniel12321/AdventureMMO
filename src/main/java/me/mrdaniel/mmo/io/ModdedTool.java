package me.mrdaniel.mmo.io;

import javax.annotation.Nonnull;

import me.mrdaniel.mmo.enums.ToolType;

public class ModdedTool {
	
	@Nonnull public final String id;
	@Nonnull public final ToolType type;
	
	public ModdedTool(@Nonnull final String id, @Nonnull final ToolType type) {
		this.id = id;
		this.type = type;
	}
}