package me.mrdaniel.mmo.io.top;

import java.nio.file.Path;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Singleton;

import com.google.common.collect.Maps;

import me.mrdaniel.mmo.enums.SkillType;
import me.mrdaniel.mmo.io.players.MMOPlayer;
import me.mrdaniel.mmo.skills.SkillSet;

@Singleton
public class SkillTop {

	@Nonnull private final Path topDir;

	@Nonnull private final Top total;
	@Nonnull private final Map<SkillType, Top> tops;

	public SkillTop(@Nonnull final Path directory) {
		this.topDir = directory;

		this.total = new Top(directory.resolve("totaltop.conf"));
		this.tops = Maps.newHashMap();
		for (SkillType type : SkillType.values()) { this.tops.put(type, new Top(directory.resolve(type.getName().toLowerCase() + "top.conf"))); }
	}

	public void update(@Nonnull final String name, @Nonnull final MMOPlayer mmop) {
		SkillSet skills = mmop.getSkills();
		
		this.total.update(name, mmop.totalLevels());
		this.tops.forEach((type, top) -> top.update(name, skills.getSkill(type).level));
	}

	public Top getTop(@Nullable final SkillType type) {
		if (type == null) { return this.total; }
		return this.tops.get(type);
	}
}