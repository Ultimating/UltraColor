package me.ultimategamer200.ultracolor.listeners;

import me.ultimategamer200.ultracolor.PlayerCache;
import me.ultimategamer200.ultracolor.mysql.UltraColorDatabase;
import me.ultimategamer200.ultracolor.settings.Settings;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * This listener only runs if the MySQL database feature is enabled.
 */
public class DatabaseListener implements Listener {
	/**
	 * Load player data after joining.
	 */
	@EventHandler(priority = EventPriority.MONITOR)
	public void onJoin(final PlayerJoinEvent event) {
		final Player player = event.getPlayer();

		if (Settings.Database.ENABLED) {
			if (UltraColorDatabase.getInstance().isLoaded()) {
				UltraColorDatabase.getInstance().load(player.getUniqueId(), PlayerCache.fromUUID(player.getUniqueId(),
						player.getName(), true));
			}
		}
	}

	/**
	 * Saves player data to the database when leaving.
	 */
	@EventHandler
	public void onQuit(final PlayerQuitEvent event) {
		final Player player = event.getPlayer();
		final PlayerCache cache = PlayerCache.fromPlayer(player);
		UltraColorDatabase.getInstance().save(player.getName(), player.getUniqueId(), cache);
	}
}
