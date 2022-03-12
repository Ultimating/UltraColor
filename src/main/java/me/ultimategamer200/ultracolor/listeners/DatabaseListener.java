package me.ultimategamer200.ultracolor.listeners;

import me.ultimategamer200.ultracolor.PlayerCache;
import me.ultimategamer200.ultracolor.mysql.UltraColorDatabase;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.mineacademy.fo.Common;

import java.util.UUID;

/**
 * This listener only runs if the MySQL database feature is enabled.
 */
public class DatabaseListener implements Listener {
	/**
	 * Loads data for the player from the database if login is allowed.
	 */
	@EventHandler(priority = EventPriority.MONITOR)
	public void onLogin(final AsyncPlayerPreLoginEvent event) {
		final UUID uuid = event.getUniqueId();

		if (event.getLoginResult() == AsyncPlayerPreLoginEvent.Result.ALLOWED) {
			final PlayerCache cache = PlayerCache.fromUUID(uuid);
			Common.runLaterAsync(25, () -> UltraColorDatabase.getInstance().load(uuid, cache));
		}
	}

	/**
	 * Saves player data to the database when leaving.
	 */
	@EventHandler
	public void onQuit(final PlayerQuitEvent event) {
		final Player player = event.getPlayer();
		final PlayerCache cache = PlayerCache.fromPlayer(player);

		Common.runLaterAsync(() -> UltraColorDatabase.getInstance().save(player.getName(), player.getUniqueId(), cache));
	}
}
