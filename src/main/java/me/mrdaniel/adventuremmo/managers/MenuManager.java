package me.mrdaniel.adventuremmo.managers;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import me.mrdaniel.adventuremmo.AdventureMMO;
import me.mrdaniel.adventuremmo.catalogtypes.skills.SkillType;
import me.mrdaniel.adventuremmo.catalogtypes.skills.SkillTypes;
import me.mrdaniel.adventuremmo.data.manipulators.SettingsData;
import me.mrdaniel.adventuremmo.io.PlayerData;
import me.mrdaniel.adventuremmo.utils.MathUtils;
import me.mrdaniel.adventuremmo.utils.TextUtils;

public class MenuManager {

	private final ScoreboardManager scoreboards;

	public MenuManager(@Nonnull final AdventureMMO mmo) {
		this.scoreboards = new ScoreboardManager(mmo);
	}

	public void sendSkillList(@Nonnull final Player p) {
		PlayerData pdata = this.scoreboards.getMMO().getPlayerDatabase().get(p.getUniqueId());
		SettingsData sdata = p.get(SettingsData.class).orElse(new SettingsData());

		if (sdata.getScoreboard()) {
			if (sdata.getScoreboardPermanent()) { this.scoreboards.setRepeating(p, this.getTitle("Skills", true), data -> this.getSkillListLines(data)); }
			else { this.scoreboards.setTemp(p, this.getTitle("Skills", true), this.getSkillListLines(pdata)); }
		}
		else {
			p.sendMessage(this.getTitle("Skills", false));
			p.sendMessage(Text.of(TextColors.AQUA, "Total", TextColors.GRAY, " - ", TextColors.GREEN, "Level ", pdata.getLevels()));
			SkillTypes.VALUES.forEach(skill -> p.sendMessage(Text.builder().append(Text.of(TextColors.AQUA, skill.getName(), TextColors.GRAY, " - ", TextColors.GREEN, "Level ", pdata.getLevel(skill))).onHover(TextActions.showText(Text.of(TextColors.BLUE, "Click for more info."))).onClick(TextActions.runCommand("/mmoskill " + skill.getId())).build()));
			p.sendMessage(Text.EMPTY);
		}
	}

	public void sendSkillInfo(@Nonnull final Player p, @Nonnull final SkillType skill) {
		PlayerData pdata = this.scoreboards.getMMO().getPlayerDatabase().get(p.getUniqueId());
		SettingsData sdata = p.get(SettingsData.class).orElse(new SettingsData());

		if (sdata.getScoreboard()) {
			if (sdata.getScoreboardPermanent()) { this.scoreboards.setRepeating(p, this.getTitle(skill.getName(), true), data -> this.getSkillInfoLines(data, skill)); }
			else { this.scoreboards.setTemp(p, this.getTitle(skill.getName(), true), this.getSkillInfoLines(pdata, skill)); }
		}
		else {
			p.sendMessage(this.getTitle(skill.getName(), false));
			p.sendMessage(Text.of(TextColors.GREEN, "Level: ", pdata.getLevel(skill)));
			p.sendMessage(Text.of(TextColors.GREEN, "EXP: ",  pdata.getExp(skill), " / ", MathUtils.expTillNextLevel(pdata.getLevel(skill))));
			p.sendMessage(Text.EMPTY);
		}
	}

	public void sendSkillTop(@Nonnull final Player p, @Nullable final SkillType type) {
		SettingsData sdata = p.get(SettingsData.class).orElse(new SettingsData());
		String title = (type == null) ? "Total Top" : (type.getName() + " Top");

		if (sdata.getScoreboard()) {
			if (sdata.getScoreboardPermanent()) { this.scoreboards.setRepeating(p, this.getTitle(title, true), data -> this.getSkillTopLines(type)); }
			else { this.scoreboards.setTemp(p, this.getTitle(title, true), this.getSkillTopLines(type)); }
		}
		else {
			p.sendMessage(this.getTitle(title, false));
			this.scoreboards.getMMO().getTops().getTop(type).getTop().forEach((number, player) -> p.sendMessage(Text.of(TextColors.RED, number, ": ", TextColors.AQUA, player.getFirst(), TextColors.GRAY, " - ", TextColors.GREEN, "Level ", player.getSecond())));
			p.sendMessage(Text.EMPTY);
		}
	}

	public void sendSettingsInfo(@Nonnull final Player p) {
		SettingsData sdata = p.get(SettingsData.class).orElse(new SettingsData());

		p.sendMessage(this.getTitle("Settings", false));
		p.sendMessage(Text.builder().append(Text.of(TextColors.AQUA, "Scoreboard: ", TextUtils.getValueText(sdata.getScoreboard()))).onHover(TextActions.showText(TextUtils.getToggleText(sdata.getScoreboard()))).onClick(TextActions.executeCallback(src -> { sdata.setScoreboard(!sdata.getScoreboard()); p.offer(sdata); this.sendSettingsInfo((Player)src); })).build());
		p.sendMessage(Text.builder().append(Text.of(TextColors.AQUA, "Scoreboard Permanent: ", TextUtils.getValueText(sdata.getScoreboardPermanent()))).onHover(TextActions.showText(TextUtils.getToggleText(sdata.getScoreboardPermanent()))).onClick(TextActions.executeCallback(src -> { sdata.setScoreboardPermanent(!sdata.getScoreboardPermanent()); p.offer(sdata); this.sendSettingsInfo((Player)src); })).build());
		p.sendMessage(Text.EMPTY);
	}

	@Nonnull
	private Multimap<Integer, Text> getSkillListLines(@Nonnull final PlayerData data) {
		Multimap<Integer, Text> lines = ArrayListMultimap.create();

		SkillTypes.VALUES.forEach(type -> lines.put(data.getLevel(type), Text.of(TextColors.AQUA, type.getName(), TextColors.GRAY, " - ")));
		lines.put(data.getLevels(), Text.of(TextColors.GREEN, "Total", TextColors.GRAY, " - "));

		return lines;
	}

	@Nonnull
	private Multimap<Integer, Text> getSkillInfoLines(@Nonnull final PlayerData data, @Nonnull final SkillType skill) {
		Multimap<Integer, Text> lines = ArrayListMultimap.create();

		lines.put(2, Text.of(TextColors.GREEN, "Level: ", data.getLevel(skill)));
		lines.put(1, Text.of(TextColors.GREEN, "EXP: ", data.getExp(skill), " / ", MathUtils.expTillNextLevel(data.getLevel(skill))));

		return lines;
	}

	@Nonnull
	private Multimap<Integer, Text> getSkillTopLines(@Nullable final SkillType type) {
		Multimap<Integer, Text> lines = ArrayListMultimap.create();

		this.scoreboards.getMMO().getTops().getTop(type).getTop().forEach((number, player) -> lines.put(player.getSecond(), Text.of(TextColors.AQUA, player.getFirst())));

		return lines;
	}

	@Nonnull
	private Text getTitle(@Nonnull final String txt, final boolean small) {
		return small ? Text.of(TextColors.RED, "-=[ ", TextColors.AQUA, txt, TextColors.RED, " ]=-") : Text.of(TextColors.RED, "----===[ ", TextColors.AQUA, txt, TextColors.RED, " ]===---");
	}

	@Nonnull
	public ScoreboardManager getScoreboardManager() {
		return this.scoreboards;
	}
}