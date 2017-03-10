package me.mrdaniel.adventuremmo.data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.annotation.Nonnull;

import org.spongepowered.api.entity.living.player.Player;

import me.mrdaniel.adventuremmo.AdventureMMO;
import me.mrdaniel.adventuremmo.MMOObject;
import me.mrdaniel.adventuremmo.enums.SkillType;
import me.mrdaniel.adventuremmo.event.LevelUpEvent;
import me.mrdaniel.adventuremmo.utils.MathUtils;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;

public class HoconPlayerData extends MMOObject implements PlayerData {

	private final ConfigurationLoader<CommentedConfigurationNode> loader;
	private final CommentedConfigurationNode node;

	public HoconPlayerData(@Nonnull final AdventureMMO mmo, @Nonnull final Path path) {
		super(mmo);

		this.loader = HoconConfigurationLoader.builder().setPath(path).build();

		if (!Files.exists(path)) {
			try { Files.createFile(path); }
			catch (final IOException exc) { super.getLogger().error("Failed to create playerdata file: {}", exc); }
		}
		this.node = this.load();
	}

	private CommentedConfigurationNode load() {
		try { return this.loader.load(); }
		catch (final IOException exc) { super.getLogger().error("Failed to load playerdata file: {}", exc); return this.loader.createEmptyNode(); }
	}

	private void save() {
		try { this.loader.save(this.node); }
		catch (final IOException exc) { super.getLogger().error("Failed to save playerdata file: {}", exc); }
	}

	public int getLevels() { return this.node.getChildrenMap().values().stream().mapToInt(node -> node.getNode("level").getInt()).sum(); }
	public int getLevel(@Nonnull final SkillType skill) { return this.node.getNode(skill.getID(), "level").getInt(); }
	public void setLevel(@Nonnull final SkillType skill, final int level) { node.getNode(skill.getID(), "level").setValue(level); this.save(); }
	public void addLevel(@Nonnull final SkillType skill, final int level) { this.setLevel(skill, this.getLevel(skill) + level); }
	public int getExp(@Nonnull final SkillType skill) { return this.node.getNode(skill.getID(), "exp").getInt(); }
	public void setExp(@Nonnull final SkillType skill, final int exp) { this.node.getNode(skill.getID(), "exp").setValue(exp); this.save(); }

	public void addExp(@Nonnull final Player p, @Nonnull final SkillType skill, final int exp) {
		int current_level = this.getLevel(skill);
		int current_exp = this.getExp(skill);
		int new_exp = current_exp + exp;
		int exp_till_next_level = MathUtils.expTillNextLevel(current_level);
		if (new_exp >= exp_till_next_level) {
			LevelUpEvent e = new LevelUpEvent(p, super.getContainer(), skill, current_level, current_level + 1);
			super.getGame().getEventManager().post(e);
			if (!e.isCancelled()) {
				this.setLevel(skill, current_level + 1);
				new_exp -= exp_till_next_level;
			}
		}
		this.setExp(skill, new_exp);
	}
}