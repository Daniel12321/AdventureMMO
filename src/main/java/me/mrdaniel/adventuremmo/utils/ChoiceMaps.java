package me.mrdaniel.adventuremmo.utils;

import java.util.Map;

import javax.annotation.Nonnull;

import com.google.common.collect.Maps;

import me.mrdaniel.adventuremmo.catalogtypes.settings.Setting;
import me.mrdaniel.adventuremmo.catalogtypes.settings.Settings;
import me.mrdaniel.adventuremmo.catalogtypes.skills.SkillType;
import me.mrdaniel.adventuremmo.catalogtypes.skills.SkillTypes;

public class ChoiceMaps {

	private final Map<String, SkillType> skills;
	private final Map<String, Setting> settings;

	public ChoiceMaps() {
		this.skills = Maps.newHashMap();
		this.settings = Maps.newHashMap();

		SkillTypes.getAll().forEach(type -> this.skills.put(type.getId(), type));
		Settings.getAll().forEach(setting -> this.settings.put(setting.getId(), setting));
	}

	@Nonnull
	public Map<String, SkillType> getSkills() {
		return this.skills;
	}

	@Nonnull
	public Map<String, Setting> getSettings() {
		return this.settings;
	}
}