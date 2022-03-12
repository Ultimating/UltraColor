package me.ultimategamer200.ultracolor.subcommands;

import me.ultimategamer200.ultracolor.PlayerCache;
import me.ultimategamer200.ultracolor.settings.Localization;
import me.ultimategamer200.ultracolor.util.UltraColorPermissions;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.mineacademy.fo.Messenger;
import org.mineacademy.fo.command.SimpleSubCommand;

public class ResetColorCommand extends SimpleSubCommand {
	public ResetColorCommand() {
		super("resetcolor|rc");
		setMinArguments(1);
		setUsage("<player>");
		setPermission(UltraColorPermissions.Command.RESET_COLOR);
		setDescription("Reset a player's color selections!");
	}

	@Override
	protected void onCommand() {
		final OfflinePlayer player = Bukkit.getOfflinePlayer(args[0]);
		final PlayerCache pCache = PlayerCache.fromOfflinePlayer(player);

		if (pCache.getChatColor() != null || pCache.getNameColor() != null || pCache.getChatFormat() != null
				|| pCache.getNameFormat() != null || pCache.getCustomGradient1() != null || pCache.getCustomGradient2() != null
				|| pCache.getChatCustomGradient1() != null || pCache.getChatCustomGradient2() != null) {
			if (pCache.getNameColor() != null || pCache.isChatRainbowColors() || pCache.isNameRainbowColors()
					|| pCache.getCustomGradient1() != null || pCache.getCustomGradient2() != null) {
				if (player.isOnline()) {
					final Player onlinePlayer = (Player) player;
					checkNotNull(onlinePlayer, "Online player cannot be null!");
					onlinePlayer.setDisplayName(player.getName());
					Messenger.info((CommandSender) player, Localization.Other.PLAYER_RESET_COLOR_COMMAND_SUCCESS_MESSAGE);
				}
			}

			pCache.setChatColor(null);
			pCache.setNameColor(null);
			pCache.setNameFormat(null);
			pCache.setChatFormat(null);
			pCache.setCustomGradient1(null);
			pCache.setCustomGradient2(null);
			pCache.setChatCustomGradient1(null);
			pCache.setChatCustomGradient2(null);
			pCache.setChatRainbowColors(false);
			pCache.setNameRainbowColors(false);

			if (!pCache.getNickName().equalsIgnoreCase("none")) {
				pCache.setColoredNickName(pCache.getNickName());

				if (player.isOnline()) {
					final Player onlinePlayer = (Player) player;
					onlinePlayer.setDisplayName(pCache.getNickName());
				}
			}

			tellSuccess(Localization.Other.ADMIN_RESET_COLOR_COMMAND_SUCCESS_MESSAGE.replace("%player%", player.getName()));
		} else
			tellError(Localization.Other.RESET_COLOR_COMMAND_ERROR_MESSAGE.replace("%player%", player.getName()));
	}
}
