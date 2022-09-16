package me.ultimategamer200.ultracolor.listeners;

import me.ultimategamer200.ultracolor.PlayerCache;
import me.ultimategamer200.ultracolor.mysql.UltraColorDatabase;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.mineacademy.fo.Common;

import java.sql.SQLException;

/**
 * This listener only runs if the MySQL database feature is enabled.
 */
public class DatabaseListener implements Listener {
	/**
	 * Saves player data to the database when leaving.
	 */
	@EventHandler
	public void onQuit(final PlayerQuitEvent event) {
		final Player player = event.getPlayer();
		final PlayerCache cache = PlayerCache.fromPlayer(player);

		Common.runLaterAsync(() -> {
			try {
				if (UltraColorDatabase.getInstance().isUpdateForPlayerNeeded(player.getUniqueId()))
					UltraColorDatabase.getInstance().save(player.getName(), player.getUniqueId(), cache);
			} catch (SQLException exception) {
				exception.printStackTrace();
			}
		});
	}
}
