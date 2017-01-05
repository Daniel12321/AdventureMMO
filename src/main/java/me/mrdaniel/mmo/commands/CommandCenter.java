package me.mrdaniel.mmo.commands;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.Tuple;

import com.google.common.collect.Maps;

import me.mrdaniel.mmo.Main;
import me.mrdaniel.mmo.enums.Setting;
import me.mrdaniel.mmo.enums.SkillType;
import me.mrdaniel.mmo.io.players.MMOPlayer;
import me.mrdaniel.mmo.scoreboard.PlayerRunnable;
import me.mrdaniel.mmo.utils.TextUtils;

public class CommandCenter {

	private static CommandCenter instance = null;
	public static CommandCenter getInstance() {
		if (instance == null) { instance = new CommandCenter(); }
		return instance;
	}

	private final HashMap<UUID, PlayerRunnable> repeating;
	private final HashMap<UUID, Tuple<Long, PlayerRunnable>> delays;

	private CommandCenter() {
		this.repeating = Maps.newHashMap();
		this.delays = Maps.newHashMap();

		Task.builder().name("MMO CommandCenter Task").delayTicks(40).intervalTicks(40).execute(() -> {
			for (UUID uuid : this.repeating.keySet()) {
				Optional<Player> player = Main.getInstance().getGame().getServer().getPlayer(uuid);
				if (player.isPresent()) { this.repeating.get(uuid).run(player.get()); }
				else { this.repeating.remove(uuid); }
			}
			for (UUID uuid : this.delays.keySet()) {
				if (System.currentTimeMillis() > this.delays.get(uuid).getFirst()) {
					Main.getInstance().getGame().getServer().getPlayer(uuid).ifPresent(player -> this.delays.get(uuid).getSecond().run(player));
					this.delays.remove(uuid);
				}
			}
		}).submit(Main.getInstance());
	}

	public void removeRepeating(UUID uuid) {
		if (this.repeating.containsKey(uuid)) { this.repeating.remove(uuid); }
	}

	public void sendMain(Player p, MMOPlayer mmop, String name) {

		p.sendMessage(Text.of(""));
		p.sendMessage(Text.of(TextColors.RED, "}--=== ", TextColors.AQUA, name, TextColors.RED, " ==---{"));

		for (SkillType skill : SkillType.values()) {
			p.sendMessage(TextUtils.setCommandClick(Text.of(TextColors.AQUA, skill.getName(), TextColors.GRAY, " - ", TextColors.GREEN, "Level ", mmop.getSkills().getSkill(skill).level), "/skill " + skill.getName().toLowerCase(), Text.of(TextColors.BLUE, "Click to see more")));
		}

		p.sendMessage(Text.of(TextColors.AQUA, "Total", TextColors.GRAY, " - ", TextColors.GREEN, "Level ", mmop.totalLevels()));
	}

	public void sendSkill(Player p, MMOPlayer mmop, SkillType type) {
		if (mmop.getSettings().getSetting(Setting.SCOREBOARD)) {
			BoardMenus.sendSkill(p, mmop, type);
			if (mmop.getSettings().getSetting(Setting.SCOREBOARDPERMANENT)) { this.repeating.put(p.getUniqueId(), player -> BoardMenus.sendSkill(player, mmop, type)); }
			else { this.delays.put(p.getUniqueId(), new Tuple<Long, PlayerRunnable>(System.currentTimeMillis() + (1000 * Main.getInstance().getConfig().SCOREBOARD_ACTIVE_SECONDS), player -> player.setScoreboard(null))); }
		}
		else { ChatMenus.sendSkill(p, mmop, type); }
	}

	public void sendTop(Player p, SkillType type) {
		MMOPlayer mmop = Main.getInstance().getMMOPlayerDatabase().getOrCreatePlayer(p.getUniqueId());
		if (mmop.getSettings().getSetting(Setting.SCOREBOARD)) {
			BoardMenus.sendTop(p, mmop, type);
			if (mmop.getSettings().getSetting(Setting.SCOREBOARDPERMANENT)) { this.repeating.put(p.getUniqueId(), player -> BoardMenus.sendTop(player, mmop, type)); }
			else { this.delays.put(p.getUniqueId(), new Tuple<Long, PlayerRunnable>(System.currentTimeMillis() + (1000 * Main.getInstance().getConfig().SCOREBOARD_ACTIVE_SECONDS), player -> player.setScoreboard(null))); }
		}
		else { ChatMenus.sendTop(p, mmop, type); }
	}

	public void sendAdmin(Player p) {
		p.sendMessage(Text.of(""));
		p.sendMessage(Text.of(TextColors.RED, "}--=== ", TextColors.AQUA, "MMO Admin", TextColors.RED, " ==---{"));
		p.sendMessage(Text.of(TextColors.GREEN, "/mmoadmin set <player> <skill> <level>"));
		p.sendMessage(Text.of(TextColors.GREEN, "/mmoadmin view <player>"));
		p.sendMessage(Text.of(TextColors.GREEN, "/mmoadmin reload"));
	}

	public void sendSettings(Player p) {
		MMOPlayer mmop = Main.getInstance().getMMOPlayerDatabase().getOrCreatePlayer(p.getUniqueId());
		p.sendMessage(Text.of(""));
		p.sendMessage(Text.of(TextColors.RED, "}--=== ", TextColors.AQUA, p.getName(), TextColors.RED, " ==---{"));

		for (Setting s : Setting.values()) {
			if (mmop.getSettings().getSetting(s)) { p.sendMessage(TextUtils.setCommandClick(Text.of(TextColors.AQUA, s.name, TextColors.GRAY, " - ", TextColors.GREEN, "Enabled"), "/settings " + s.name + " false", Text.of(TextColors.RED, "Click to Disable"))); }
			else { p.sendMessage(TextUtils.setCommandClick(Text.of(TextColors.AQUA, s.name, TextColors.GRAY, " - ", TextColors.RED, "Disabled"), "/settings " + s.name + " true", Text.of(TextColors.GREEN, "Click to Enable"))); }
		}
	}
}