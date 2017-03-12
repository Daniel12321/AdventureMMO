package me.mrdaniel.adventuremmo.managers;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

import javax.annotation.Nonnull;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.scoreboard.critieria.Criteria;
import org.spongepowered.api.scoreboard.displayslot.DisplaySlots;
import org.spongepowered.api.scoreboard.objective.Objective;
import org.spongepowered.api.text.Text;

import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;

import me.mrdaniel.adventuremmo.AdventureMMO;
import me.mrdaniel.adventuremmo.MMOObject;
import me.mrdaniel.adventuremmo.data.manipulators.SettingsData;
import me.mrdaniel.adventuremmo.io.PlayerData;

public class ScoreboardManager extends MMOObject {

	private final Map<UUID, Task> boards;

	public ScoreboardManager(@Nonnull final AdventureMMO mmo) {
		super(mmo);

		this.boards = Maps.newHashMap();
	}

	public void setTemp(@Nonnull final Player p, @Nonnull final Text title, @Nonnull final Multimap<Integer, Text> lines) {
		this.unload(p);

		this.set(p, title, lines);

		this.boards.put(p.getUniqueId(), Task.builder().delayTicks(300).execute(() -> this.unload(p)).submit(super.getMMO()));
	}

	public void setRepeating(@Nonnull final Player p, @Nonnull final Text title, @Nonnull final Function<PlayerData, Multimap<Integer, Text>> function) {
		this.unload(p);

		this.boards.put(p.getUniqueId(), Task.builder().delayTicks(0).intervalTicks(100).execute(t -> {
			if (!p.get(SettingsData.class).orElse(new SettingsData()).getScoreboardPermanent()) { this.unload(p); }
			else { this.set(p, title, function.apply(super.getMMO().getPlayerDatabase().get(p.getUniqueId()))); }
		}).submit(super.getMMO()));
	}

	private void set(@Nonnull final Player p, @Nonnull final Text title, @Nonnull final Multimap<Integer, Text> lines) {
		String name = "MMO_" + (p.getName().length() > 12 ? p.getName().substring(0, 12) : p.getName());

		p.getScoreboard().getObjective(name).ifPresent(p.getScoreboard()::removeObjective);

		Objective obj = Objective.builder().name(name).criterion(Criteria.DUMMY).displayName(title).build();
		lines.asMap().forEach((line, txts) -> txts.forEach(txt -> obj.getOrCreateScore(txt).setScore(line)));

		p.getScoreboard().addObjective(obj);
		p.getScoreboard().updateDisplaySlot(obj, DisplaySlots.SIDEBAR);
	}

	public void unload(@Nonnull final Player p) {
		Optional.ofNullable(this.boards.get(p.getUniqueId())).ifPresent(t -> {
			t.cancel();
			this.boards.remove(p.getUniqueId());
		});

		p.getScoreboard().clearSlot(DisplaySlots.SIDEBAR);
	}
}