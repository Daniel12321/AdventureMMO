package me.mrdaniel.mmo.io;

import me.mrdaniel.mmo.enums.ToolType;

public class ModdedTool {
	
	public String id;
	public ToolType type;
	
	public ModdedTool(String id, ToolType type) {
		this.id = id;
		this.type = type;
	}
}