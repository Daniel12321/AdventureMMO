package me.mrdaniel.adventuremmo.io.playerdata;

import java.util.Optional;
import java.util.UUID;

import javax.annotation.Nonnull;

import org.spongepowered.api.entity.living.player.Player;

import me.mrdaniel.adventuremmo.AdventureMMO;
import me.mrdaniel.adventuremmo.catalogtypes.skills.SkillType;
import me.mrdaniel.adventuremmo.event.LevelUpEvent;
import me.mrdaniel.adventuremmo.utils.MathUtils;

public interface PlayerDatabase {

	void load(UUID uuid);
	void unload(UUID uuid);

	PlayerData get(UUID uuid);
	Optional<PlayerData> getOffline(UUID uuid);

	default PlayerData addExp(@Nonnull final AdventureMMO mmo, @Nonnull final Player p, @Nonnull final SkillType skill, final int exp) {
		PlayerData data = this.get(p.getUniqueId());

		int current_level = data.getLevel(skill);
		int current_exp = data.getExp(skill);
		int new_exp = current_exp + exp;
		int exp_till_next_level = MathUtils.expTillNextLevel(current_level);

		if (new_exp >= exp_till_next_level) {
			if (!mmo.getGame().getEventManager().post(new LevelUpEvent(mmo, p, skill, current_level, current_level + 1))) {
				data.setLevel(skill, current_level + 1);
				new_exp -= exp_till_next_level;
			}
		}
		data.setExp(skill, new_exp);
		return data;
	}

	default PlayerData addLevel(@Nonnull final AdventureMMO mmo, @Nonnull final Player p, @Nonnull final SkillType skill, final int level) {
		PlayerData data = this.get(p.getUniqueId());

		int current_level = data.getLevel(skill);
		int new_level = current_level + level;
		if (!mmo.getGame().getEventManager().post(new LevelUpEvent(mmo, p, skill, current_level, new_level))) {
			data.setLevel(skill, new_level);
		}
		return data;
	}
}