package me.mrdaniel.adventuremmo.utils;

import java.util.Map;

import javax.annotation.Nonnull;

import com.google.common.collect.Maps;

import me.mrdaniel.adventuremmo.enums.Setting;
import me.mrdaniel.adventuremmo.enums.SkillType;

public class ChoiceMaps {

	private final Map<String, SkillType> skills;
	private final Map<String, Setting> settings;

	public ChoiceMaps() {
		this.skills = Maps.newHashMap();
		this.settings = Maps.newHashMap();

		for (SkillType type : SkillType.values()) { skills.put(type.getID(), type); }
		for (Setting s : Setting.values()) { this.settings.put(s.getID(), s); }
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