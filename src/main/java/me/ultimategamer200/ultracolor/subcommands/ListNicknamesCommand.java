package me.ultimategamer200.ultracolor.subcommands;

import lombok.SneakyThrows;
import me.ultimategamer200.ultracolor.PlayerCache;
import me.ultimategamer200.ultracolor.mysql.UltraColorDatabase;
import me.ultimategamer200.ultracolor.settings.Settings;
import me.ultimategamer200.ultracolor.util.UltraColorPermissions;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.mineacademy.fo.ChatUtil;
import org.mineacademy.fo.command.SimpleSubCommand;
import org.mineacademy.fo.model.ChatPaginator;
import org.mineacademy.fo.model.SimpleComponent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListNicknamesCommand extends SimpleSubCommand {
	public ListNicknamesCommand() {
		super("listnicknames|lnn");
		setPermission(UltraColorPermissions.Command.LIST_NICKNAMES);
		setDescription("Lists all the nicknames stored by either current server or all servers connected to MySQL database.");
		setUsage("<local|global>");
		setMinArguments(1);
	}

	@SneakyThrows
	@Override
	protected void onCommand() {
		final String mode = args[0];
		final Map<String, String> realNamesAndNicks = new HashMap<>();
		final List<PlayerCache> pCaches = new ArrayList<>(PlayerCache.cacheMap.values());

		if (mode.equalsIgnoreCase("local")) {
			for (final PlayerCache pCache : pCaches) {
				if (pCache.getColoredNickName().equalsIgnoreCase("none")) continue;

				final OfflinePlayer player = Bukkit.getOfflinePlayer(pCache.getPlayerName());
				realNamesAndNicks.put(player.getName(), pCache.getColoredNickName());
			}

			displayNicknames(realNamesAndNicks);
		} else if (mode.equalsIgnoreCase("global")) {
			if (!Settings.Database.ENABLED || !UltraColorDatabase.getInstance().isLoaded()) {
				tellError("Seeing nicknames of all servers requires a MySQL database connection! Check the database settings in settings.yml.");
				return;
			}

			for (final PlayerCache pCache : pCaches) {
				if (pCache.getColoredNickName().equalsIgnoreCase("none"))
					continue;

				final OfflinePlayer player = Bukkit.getOfflinePlayer(pCache.getPlayerName());

				if (!UltraColorDatabase.getInstance().isStored(pCache.getUuid()))
					realNamesAndNicks.put(player.getName(), pCache.getColoredNickName());
				else
					realNamesAndNicks.put(player.getName(), UltraColorDatabase.getInstance().getStoredNick(player));
			}

			displayNicknames(realNamesAndNicks);
		} else
			tellError("The list nickname mode needs to either be &elocal (all nicknames for your current server) &cor &eglobal (all nicknames for servers connected to MySQL database)&c.");
	}

	@Override
	protected List<String> tabComplete() {
		if (args.length == 1)
			return completeLastWord("local", "global");
		return NO_COMPLETE;
	}

	private void displayNicknames(final Map<String, String> realNamesAndNicks) {
		final List<SimpleComponent> textToAdd = new ArrayList<>();

		for (final String player : realNamesAndNicks.keySet()) {
			final OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(player);
			textToAdd.add(SimpleComponent.of(realNamesAndNicks.get(player) + " &c-> &r" + offlinePlayer.getName()));
		}

		new ChatPaginator(ChatPaginator.FOUNDATION_HEIGHT).setHeader(ChatUtil.center("&d* &e&lNICKNAMES (" + args[0].toUpperCase()
				+ ")" + " &b*")).setPages(textToAdd).send(getSender());
	}
}