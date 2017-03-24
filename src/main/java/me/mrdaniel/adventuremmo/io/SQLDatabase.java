package me.mrdaniel.adventuremmo.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.util.Tuple;

import com.google.common.collect.Maps;

import me.mrdaniel.adventuremmo.AdventureMMO;
import me.mrdaniel.adventuremmo.catalogtypes.skills.SkillType;
import me.mrdaniel.adventuremmo.io.items.BlockData;
import me.mrdaniel.adventuremmo.io.items.ItemDatabase;
import me.mrdaniel.adventuremmo.io.items.ToolData;
import me.mrdaniel.adventuremmo.io.playerdata.PlayerData;
import me.mrdaniel.adventuremmo.io.playerdata.PlayerDatabase;
import me.mrdaniel.adventuremmo.io.playerdata.SQLPlayerData;
import me.mrdaniel.adventuremmo.io.tops.TopDatabase;

public class SQLDatabase implements PlayerDatabase, ItemDatabase, TopDatabase {

	private final Map<UUID, SQLPlayerData> players;

	public SQLDatabase(@Nonnull final AdventureMMO mmo, @Nonnull final Path path) {
		if (!Files.exists(path)) {
			try { mmo.getContainer().getAsset("storage.db").get().copyToFile(path); }
			catch (final IOException exc) { mmo.getLogger().error("Failed to create database file from asset: {}", exc); }
		}

		this.players = new ConcurrentHashMap<UUID, SQLPlayerData>();
	}

	// TopDatabase
	@Override
	public void update(@Nonnull final String player, @Nullable final SkillType skill, final int level) {
		;
	}

	@Override
	@Nonnull
	public Map<Integer, Tuple<String, Integer>> getTop(@Nullable final SkillType skill) {
		Map<Integer, Tuple<String, Integer>> top = Maps.newHashMap();

		return top;
	}

	// ItemDatabase
	@Override
	@Nonnull
	public Optional<BlockData> getData(@Nonnull final BlockType type) {
		return Optional.empty();
	}

	@Override
	@Nonnull
	public Optional<ToolData> getData(@Nonnull final ItemType type) {
		return Optional.empty();
	}

	@Override
	@Nonnull
	public Optional<ToolData> getData(@Nullable final ItemStack item) {
		return Optional.empty();
	}

	// PlayerDatabase
	@Override
	public synchronized void load(@Nonnull final UUID uuid) {
		this.players.put(uuid, new SQLPlayerData());
	}

	@Override
	public synchronized void unload(@Nonnull final UUID uuid) {
		this.players.remove(uuid);
	}

	@Override
	@Nonnull
	public synchronized PlayerData get(@Nonnull final UUID uuid) {
		return this.players.get(uuid);
	}

	@Override
	@Nonnull
	public Optional<PlayerData> getOffline(@Nonnull final UUID uuid) {
		return Optional.empty();
	}
}