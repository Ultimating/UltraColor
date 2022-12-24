package me.ultimategamer200.ultracolor.subcommands;

import me.ultimategamer200.ultracolor.PlayerCache;
import me.ultimategamer200.ultracolor.settings.Localization;
import me.ultimategamer200.ultracolor.settings.Settings;
import me.ultimategamer200.ultracolor.util.ColorId;
import me.ultimategamer200.ultracolor.util.UltraColorPermissions;
import me.ultimategamer200.ultracolor.util.UltraColorUtil;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.command.SimpleSubCommand;
import org.mineacademy.fo.remain.CompChatColor;
import org.mineacademy.fo.remain.Remain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ForceColorCommand extends SimpleSubCommand {
	protected ForceColorCommand() {
		super("forcecolor|fc");
		setPermission(UltraColorPermissions.Command.FORCE_COLOR);
		setMinArguments(3);
		setUsage("<name|chat> <player> <color> [format]");
	}

	@Override
	protected void onCommand() {
		final String colorType = args[0];
		final OfflinePlayer player = Bukkit.getOfflinePlayer(args[1]);
		final String color = args[2];

		final PlayerCache pCache = PlayerCache.fromOfflinePlayer(player);
		final boolean isHexValid = color.length() == 7 && color.startsWith("#");
		CompChatColor format = null;

		if (args.length >= 4) {
			if (ColorId.FormatId.getFormatIds().contains(args[3]))
				format = UltraColorUtil.getFormatToCompChatColor(args[3]);
			else
				returnInvalidArgs();
		}

		Player onlinePlayer;

		if (ColorId.getColorIds().contains(color) || color.startsWith("#")) {
			if (color.startsWith("#") && Remain.hasHexColors()) {
				if (isHexValid) {
					final CompChatColor hexColor = CompChatColor.of(color);
					applyForcedColor(colorType, hexColor, format, player);
				}
			} else if (color.startsWith("#") && !Remain.hasHexColors()) {
				tellError(Localization.Hex_Colors.HEX_COLOR_UNSUPPORTED_VERSION_MESSAGE);
				returnInvalidArgs();
			} else {
				for (final ColorId colorId : ColorId.values()) {
					if (color.equalsIgnoreCase(colorId.getId())) {
						applyForcedColor(colorType, colorId.getColor(), format, player);
						break;
					}
				}
			}
		} else
			returnInvalidArgs();

		if (colorType.equalsIgnoreCase("chat")) {
			if (color.equalsIgnoreCase(ColorId.RAINBOW.getId()) && Settings.Color_Settings.CHAT_RAINBOW_COLORS) {
				if (args.length >= 4)
					pCache.setChatFormat(format);
				else
					pCache.setChatFormat(null);

				final boolean isFormatSelected = pCache.getChatFormat() != null;
				applyForcedRainbow(player, colorType, isFormatSelected);
			} else if (color.equalsIgnoreCase("none")) {
				applyForcedColor(colorType, null, format, player);
			} else if (color.equalsIgnoreCase("reset")) {
				pCache.setChatColor(null);
				pCache.setChatFormat(null);
				pCache.clearGradients("chat");
				pCache.setChatRainbowColors(false);

				tellSuccess(Localization.Other.ADMIN_RESET_COLOR_COMMAND_SUCCESS_MESSAGE.replace("%player%", Objects.requireNonNull(player.getName())));
			} else if (color.equalsIgnoreCase(ColorId.RAINBOW.getId()) && !Settings.Color_Settings.CHAT_RAINBOW_COLORS)
				tellError("Rainbow colors are disabled in the settings.yml file!");
		} else if (colorType.equalsIgnoreCase("name")) {
			if (color.equalsIgnoreCase(ColorId.RAINBOW.getId()) && Settings.Color_Settings.NAME_RAINBOW_COLORS) {
				applyForcedRainbow(player, colorType, format != null);
			} else if (color.equalsIgnoreCase("none")) {
				applyForcedColor(colorType, null, format, player);
			} else if (color.equalsIgnoreCase("reset")) {
				pCache.setNameColor(null);
				pCache.setNameFormat(null);
				pCache.clearGradients("name");
				pCache.setNameRainbowColors(false);

				if (player.isOnline()) {
					onlinePlayer = (Player) player;
					onlinePlayer.setDisplayName(onlinePlayer.getName());
				}

				tellSuccess(Localization.Other.ADMIN_RESET_COLOR_COMMAND_SUCCESS_MESSAGE.replace("%player%", Objects.requireNonNull(player.getName())));
			} else if (color.equalsIgnoreCase(ColorId.RAINBOW.getId()) && !Settings.Color_Settings.NAME_RAINBOW_COLORS)
				tellError("Rainbow colors are disabled in the settings.yml file!");
		} else
			returnInvalidArgs();
	}

	@Override
	protected List<String> tabComplete() {
		if (args.length == 1)
			return completeLastWord("name", "chat");
		if (args.length == 2)
			return completeLastWordPlayerNames();
		if (args.length == 3)
			return completeLastWord(ColorId.getColorIds());
		if (args.length == 4)
			return completeLastWord(ColorId.FormatId.getFormatIds());

		return new ArrayList<>();
	}

	private void applyForcedColor(final String type, final CompChatColor color, final CompChatColor format, final OfflinePlayer player) {
		final PlayerCache pCache = PlayerCache.fromOfflinePlayer(player);
		String newName = player.getName();

		if (type.equalsIgnoreCase("chat")) {
			pCache.setChatColor(color);
			pCache.setChatFormat(format);
			pCache.setChatRainbowColors(false);
			pCache.clearGradients("chat");

			final String successMessage = Localization.Other.ADMIN_SET_FORCE_CHAT_COLOR_SUCCESS_MESSAGE
					.replace("%new_chat_color%", pCache.getChatColor() + "this")
					.replace("%player%", Objects.requireNonNull(player.getName()));
			Common.runLaterAsync(10, () -> tellSuccess(successMessage));
		} else {
			pCache.setNameColor(color);

			if (format != null)
				pCache.setNameFormat(UltraColorUtil.getNameFormatToChatColor(format.getName()));
			else
				pCache.setNameFormat(null);

			pCache.setNameRainbowColors(false);
			pCache.clearGradients("name");
			Player onlinePlayer;

			if (!pCache.getNickName().equalsIgnoreCase("none")) {
				if (format != null)
					pCache.setColoredNickName(UltraColorUtil.nameAndChatColorToString(color) + UltraColorUtil.nameFormatToString(pCache.getNameFormat())
							+ pCache.getNickName());
				else if (color != null)
					pCache.setColoredNickName(pCache.getNameColor() + pCache.getNickName());
				else
					pCache.setColoredNickName(pCache.getNickName());

				newName = pCache.getColoredNickName();
			} else {
				if (pCache.getNameColor() != null || pCache.getNameFormat() != null) {
					if (pCache.getNameFormat() != null)
						newName = UltraColorUtil.nameAndChatColorToString(pCache.getNameColor()) + UltraColorUtil.nameFormatToString(pCache.getNameFormat())
								+ player.getName();
					else
						newName = UltraColorUtil.nameAndChatColorToString(pCache.getNameColor()) + player.getName();
				}
			}

			if (player.isOnline()) {
				onlinePlayer = (Player) player;
				onlinePlayer.setDisplayName(newName);
			}

			final String successMessage;

			if (color != null) {
				successMessage = Localization.Other.ADMIN_SET_FORCE_NAME_COLOR_SUCCESS_MESSAGE.replace("%new_name_color%",
						pCache.getNameColor() + "this").replace("%player%", Objects.requireNonNull(player.getName()));
				Common.runLaterAsync(10, () -> tellSuccess(successMessage));
			} else {
				successMessage = Localization.Other.ADMIN_SET_FORCE_NAME_COLOR_SUCCESS_MESSAGE.replace("%new_name_color%", "none")
						.replace("%player%", Objects.requireNonNull(player.getName()));
			}

			Common.runLaterAsync(10, () -> tellSuccess(successMessage));

			if (format != null) {
				final String formatSuccessMessage = Localization.Other.ADMIN_SET_FORCE_NAME_FORMAT_SUCCESS_MESSAGE.replace("%new_name_format%",
								pCache.getNameFormat() != null ? pCache.getNameFormat() + "this" : "none")
						.replace("%player%", Objects.requireNonNull(player.getName()));

				Common.runLaterAsync(10, () -> tellSuccess(formatSuccessMessage));
			}
		}
	}

	public void applyForcedRainbow(final OfflinePlayer player, final String type, final boolean isFormatEnabled) {
		final PlayerCache pCache = PlayerCache.fromOfflinePlayer(player);

		if (type.equalsIgnoreCase("name")) {
			Player onlinePlayer;

			if (player.isOnline()) {
				onlinePlayer = (Player) player;
				UltraColorUtil.convertNameToRainbow(onlinePlayer, isFormatEnabled, isFormatEnabled
						? pCache.getNameFormat().name() : "");
			}

			tellSuccess(Localization.Other.ADMIN_SET_FORCE_NAME_COLOR_SUCCESS_MESSAGE.replace("%new_name_color%", UltraColorUtil.convertStringToRainbow(
							"this", isFormatEnabled, isFormatEnabled ? pCache.getNameFormat().name() : ""))
					.replace("%player%", Objects.requireNonNull(player.getName())));
		} else {
			pCache.setChatColor(null);
			pCache.setChatRainbowColors(true);

			tellSuccess(Localization.Other.ADMIN_SET_FORCE_CHAT_COLOR_SUCCESS_MESSAGE.replace("%new_chat_color%",
					UltraColorUtil.convertStringToRainbow("this", isFormatEnabled, isFormatEnabled
							? pCache.getChatFormat().getName() : "")).replace("%player%", Objects.requireNonNull(player.getName())));
		}
	}
}
