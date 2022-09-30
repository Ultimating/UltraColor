package me.ultimategamer200.ultracolor;

import lombok.Getter;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.constants.FoConstants;
import org.mineacademy.fo.remain.Remain;
import org.mineacademy.fo.settings.YamlConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * A helper class for the data.db file.
 */
public final class DataFile extends YamlConfig {
	@Getter
	private static final DataFile instance = new DataFile();

	private DataFile() {
		loadConfiguration(NO_DEFAULT, FoConstants.File.DATA);
	}

	// --------------------------------------------------------------------------------------------------------------
	// Helpers
	// --------------------------------------------------------------------------------------------------------------

	/**
	 * Gets all the player data sections.
	 */
	public List<PlayerCache> getCaches(final boolean loadData) {
		final long now = System.currentTimeMillis();
		final Set<String> uuids = getUUIDS();
		final List<PlayerCache> caches = new ArrayList<>();
		int loadedAmount = 0;

		for (final String uuid : uuids) {
			final String playerName = this.getString(uuid + ".Player_Name", Remain.getOfflinePlayerByUUID(UUID.fromString(uuid)).getName());

			if (playerName != null) {
				final PlayerCache pCache = PlayerCache.fromUUID(UUID.fromString(uuid), playerName, loadData);

				caches.add(pCache);
				loadedAmount++;
			}
		}

		Common.log("Loading " + loadedAmount + " caches took " + (System.currentTimeMillis() - now) + "ms");
		return caches;
	}

	public Set<String> getUUIDS() {
		final DataFile config = getInstance();
		final Set<String> uuids = config.getKeys(false);

		// Removes "Metadata" as it isn't a UUID.
		uuids.remove("Metadata");
		return uuids;
	}
}
