package me.mrdaniel.adventuremmo.data;

import java.util.Optional;
import java.util.UUID;

public interface PlayerDatabase {

	PlayerData get(UUID uuid);
	Optional<PlayerData> getOffline(UUID uuid);

	void load(UUID uuid);
	void unload(UUID uuid);
}