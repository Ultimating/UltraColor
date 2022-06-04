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

import java.util.List;

public class ResetColorCommand extends SimpleSubCommand {
	public ResetColorCommand() {
		super("resetcolor|rc");
		setMinArguments(2);
		setUsage("<chat|name> <player>");
		setPermission(UltraColorPermissions.Command.RESET_COLOR);
		setDescription("Reset a player's color selections!");
	}

	@Override
	protected void onCommand() {
		final String colorToReset = args[0];
		final OfflinePlayer player = Bukkit.getOfflinePlayer(args[1]);
		final PlayerCache pCache = PlayerCache.fromOfflinePlayer(player);
		boolean resetSuccess = false;

		if (colorToReset.equalsIgnoreCase("chat")) {
			if (pCache.getChatColor() != null || pCache.getChatFormat() != null || pCache.getChatCustomGradient1() != null
					|| pCache.getChatCustomGradient2() != null || pCache.isChatRainbowColors()) {
				pCache.setChatColor(null);
				pCache.setChatFormat(null);
				pCache.setChatCustomGradient1(null);
				pCache.setChatCustomGradient2(null);
				pCache.setChatRainbowColors(false);
				resetSuccess = true;
			} else
				tellError(Localization.Other.RESET_COLOR_COMMAND_ERROR_MESSAGE.replace("%player%", player.getName()));
		} else if (colorToReset.equalsIgnoreCase("name")) {
			if (pCache.getNameColor() != null || pCache.getNameFormat() != null || pCache.getCustomGradient1() != null
					|| pCache.getCustomGradient2() != null || pCache.isNameRainbowColors()) {
				pCache.setNameColor(null);
				pCache.setNameFormat(null);
				pCache.setCustomGradient1(null);
				pCache.setCustomGradient2(null);
				pCache.setNameRainbowColors(false);
				resetSuccess = true;
			} else
				tellError(Localization.Other.RESET_COLOR_COMMAND_ERROR_MESSAGE.replace("%player%", player.getName()));
		} else {
			returnInvalidArgs();
			return;
		}

		if (resetSuccess) {
			if (player.isOnline())
				Messenger.info((CommandSender) player, Localization.Other.PLAYER_RESET_COLOR_COMMAND_SUCCESS_MESSAGE.replace("%type%", colorToReset));
			if (!pCache.getNickName().equalsIgnoreCase("none") && colorToReset.equalsIgnoreCase("name")) {
				pCache.setColoredNickName(pCache.getNickName());

				if (player.isOnline()) {
					final Player onlinePlayer = (Player) player;
					onlinePlayer.setDisplayName(pCache.getNickName());
				}
			} else if (pCache.getNickName().equalsIgnoreCase("none") && colorToReset.equalsIgnoreCase("name")) {
				if (player.isOnline()) {
					final Player onlinePlayer = (Player) player;
					onlinePlayer.setDisplayName(player.getName());
				}
			}

			tellSuccess(Localization.Other.ADMIN_RESET_COLOR_COMMAND_SUCCESS_MESSAGE.replace("%player%", player.getName()));
		} else
			tellError(Localization.Other.RESET_COLOR_COMMAND_ERROR_MESSAGE.replace("%player%", player.getName()));
	}

	@Override
	protected List<String> tabComplete() {
		if (args.length == 1) return completeLastWord("chat", "name");
		if (args.length == 2) return completeLastWordPlayerNames();
		return NO_COMPLETE;
	}
}
