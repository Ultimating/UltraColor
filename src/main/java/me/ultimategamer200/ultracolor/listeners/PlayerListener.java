package me.ultimategamer200.ultracolor.listeners;

import me.ultimategamer200.ultracolor.PlayerCache;
import me.ultimategamer200.ultracolor.settings.Settings;
import me.ultimategamer200.ultracolor.util.UltraColorPermissions;
import me.ultimategamer200.ultracolor.util.UltraColorUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.server.ServerCommandEvent;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.annotation.AutoRegister;
import org.mineacademy.fo.model.SpigotUpdater;

@AutoRegister
public final class PlayerListener implements Listener {
	@EventHandler
	public void onJoin(final PlayerJoinEvent event) {
		final Player player = event.getPlayer();
		final SpigotUpdater updater = new SpigotUpdater(85332);
		final PlayerCache pCache = PlayerCache.fromUUID(player.getUniqueId());

		// Saves the player name for the player, so it's easy to tell what data section belongs to whom.
		if (pCache.getPlayerName() == null)
			pCache.setPlayerName(player.getName());
		else {
			if (!pCache.getPlayerName().equalsIgnoreCase(player.getName()))
				pCache.setPlayerName(player.getName());
		}

		if (Settings.NOTIFY_UPDATES && updater.isNewVersionAvailable())
			if (player.isOp() || player.hasPermission(UltraColorPermissions.NOTIFY_UPDATE))
				Common.tell(player, updater.getNotifyMessage());

		if (!Settings.Database.ENABLED)
			player.setDisplayName(UltraColorUtil.getPlayerNameInColor(player));
		else {
			// Runs this loading later to allow player connecting to finish.
			Common.runLater(25, () -> player.setDisplayName(UltraColorUtil.getPlayerNameInColor(player)));
		}

		// Purge the player's nickname if nicknames are disabled, purging is enabled, and if they have one.
		if (!Settings.Other.NICKNAMES_ENABLE && !pCache.getNickName().equalsIgnoreCase("none")) {
			if (Settings.Other.PURGE_NICKNAMES) {
				pCache.setNickName("none");
				pCache.setColoredNickName("none");
				player.setDisplayName(UltraColorUtil.getPlayerNameInColor(player));
			}
		}
	}

	@EventHandler
	public void onCommandSend(final PlayerCommandPreprocessEvent event) {
		if (Settings.Other.NICKNAMES_ENABLE)
			runReplacement(event);
	}

	/**
	 * Command sending for non-players (eg. console)
	 */
	@EventHandler
	public void onConsoleCommandSend(final ServerCommandEvent event) {
		if (Settings.Other.NICKNAMES_ENABLE)
			runReplacement(event);
	}

	/**
	 * Runs the nickname replacement for console commands.
	 */
	private void runReplacement(final ServerCommandEvent event) {
		if (!event.getCommand().startsWith("/realname") && !event.getCommand().startsWith("/nickname")
				&& !event.getCommand().startsWith("/nick") && !event.getCommand().startsWith("/ver"))
			event.setCommand(replaceNickWithPlayerInCommand(event.getCommand()));
	}

	/**
	 * Runs the nickname replacement for player commands.
	 */
	private void runReplacement(final PlayerCommandPreprocessEvent event) {
		if (!event.getMessage().startsWith("/realname") && !event.getMessage().startsWith("/nickname")
				&& !event.getMessage().startsWith("/nick") && !event.getMessage().startsWith("/ver"))
			event.setMessage(replaceNickWithPlayerInCommand(event.getMessage()));
	}

	/**
	 * Finds the needed player to replace the nickname with.
	 */
	private PlayerCache findNeededCacheForCommandReplacement(final String command) {
		PlayerCache cache = null;

		for (final PlayerCache pCache : PlayerCache.cacheMap.values()) {
			if (pCache.getNickName().equalsIgnoreCase("none"))
				continue;

			if (command.contains(pCache.getNickName()) && !command.contains(pCache.getPlayerName())) {
				cache = pCache;
				break;
			} else if (command.contains(pCache.getPlayerName())) {
				cache = pCache;
				break;
			}
		}

		return cache;
	}

	/**
	 * Modifies the command with substituting the player name for their nickname.
	 */
	private String replaceNickWithPlayerInCommand(final String command) {
		final PlayerCache pCache = findNeededCacheForCommandReplacement(command);

		if (pCache != null) {
			final boolean commandNeedReplacement = command.contains(pCache.getNickName()) && !command.contains(pCache.getPlayerName());

			if (commandNeedReplacement)
				return command.replace(pCache.getNickName(), pCache.getPlayerName());
		}
		return command;
	}
}
