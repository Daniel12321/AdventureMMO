package me.mrdaniel.adventuremmo.io;

import java.util.Optional;
import java.util.UUID;

import javax.annotation.Nonnull;

import org.spongepowered.api.entity.living.player.Player;

import me.mrdaniel.adventuremmo.AdventureMMO;
import me.mrdaniel.adventuremmo.catalogtypes.skills.SkillType;
import me.mrdaniel.adventuremmo.event.LevelUpEvent;
import me.mrdaniel.adventuremmo.utils.MathUtils;

public interface PlayerDatabase {

	PlayerData get(UUID uuid);
	Optional<PlayerData> getOffline(UUID uuid);

	void load(UUID uuid);
	void unload(UUID uuid);

	default PlayerData addExp(@Nonnull final AdventureMMO mmo, @Nonnull final Player p, @Nonnull final SkillType skill, final int exp) {
		PlayerData pdata = this.get(p.getUniqueId());

		int current_level = pdata.getLevel(skill);
		int current_exp = pdata.getExp(skill);
		int new_exp = current_exp + exp;
		int exp_till_next_level = MathUtils.expTillNextLevel(current_level);

		if (new_exp >= exp_till_next_level) {
			if (!mmo.getGame().getEventManager().post(new LevelUpEvent(mmo, p, skill, current_level, current_level + 1))) {
				pdata.setLevel(skill, current_level + 1);
				new_exp -= exp_till_next_level;
			}
		}
		pdata.setExp(skill, new_exp);
		return pdata;
	}
}