package me.mrdaniel.adventuremmo.utils;

import java.util.Map;

import javax.annotation.Nonnull;

import com.google.common.collect.Maps;

import me.mrdaniel.adventuremmo.catalogtypes.settings.Setting;
import me.mrdaniel.adventuremmo.catalogtypes.settings.Settings;
import me.mrdaniel.adventuremmo.catalogtypes.skills.SkillType;
import me.mrdaniel.adventuremmo.catalogtypes.skills.SkillTypes;
import me.mrdaniel.adventuremmo.catalogtypes.tools.ToolType;
import me.mrdaniel.adventuremmo.catalogtypes.tools.ToolTypes;

public class ChoiceMaps {

	private final Map<String, SkillType> skills;
	private final Map<String, ToolType> tools;
	private final Map<String, Setting> settings;

	public ChoiceMaps() {
		this.skills = Maps.newHashMap();
		this.tools = Maps.newHashMap();
		this.settings = Maps.newHashMap();

		SkillTypes.VALUES.forEach(type -> this.skills.put(type.getId(), type));
		ToolTypes.VALUES.forEach(tool -> this.tools.put(tool.getId(), tool));
		Settings.VALUES.forEach(setting -> this.settings.put(setting.getId(), setting));
	}

	@Nonnull
	public Map<String, SkillType> getSkills() {
		return this.skills;
	}

	@Nonnull
	public Map<String, ToolType> getTools() {
		return this.tools;
	}

	@Nonnull
	public Map<String, Setting> getSettings() {
		return this.settings;
	}
}