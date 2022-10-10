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
import org.mineacademy.fo.ChatUtil;
import org.mineacademy.fo.Messenger;
import org.mineacademy.fo.MinecraftVersion;
import org.mineacademy.fo.command.SimpleSubCommand;
import org.mineacademy.fo.remain.CompChatColor;

import java.util.List;

public class ForceGradientCommand extends SimpleSubCommand {
	protected ForceGradientCommand() {
		super("forcegradient|fg");
		setPermission(UltraColorPermissions.Command.FORCE_GRADIENT);
		setUsage("<name|chat> <player> <color1> <color2> [format]");
		setMinArguments(4);
	}

	@Override
	protected void onCommand() {
		final String gradientType = args[0];
		final String playerName = args[1];
		OfflinePlayer player = Bukkit.getPlayer(playerName);

		final String firstColor = args[2];
		final String secondColor = args[3];

		if (MinecraftVersion.atLeast(MinecraftVersion.V.v1_16)) {
			if (player != null) {
				final boolean areColorsValid = firstColor.length() == 7 && secondColor.length() == 7
						&& firstColor.startsWith("#") && secondColor.startsWith("#");

				if (areColorsValid) {
					final CompChatColor finishedFirstColor = CompChatColor.of(firstColor);
					final CompChatColor finishedSecondColor = CompChatColor.of(secondColor);

					if (gradientType.equalsIgnoreCase("name") || gradientType.equalsIgnoreCase("chat"))
						applyForcedGradient(player, gradientType, finishedFirstColor, finishedSecondColor);
					else
						returnInvalidArgs();
				} else
					tellError(Localization.Gradient_Color_Selection.INVALID_GRADIENT_MESSAGE);
			}
		} else
			tellError(Localization.Gradient_Color_Selection.UNSUPPORTED_VERSION_MESSAGE);
	}

	@Override
	protected List<String> tabComplete() {
		if (args.length == 1)
			return completeLastWord("name", "chat");
		if (args.length == 2)
			return completeLastWordPlayerNames();
		if (args.length == 5)
			return completeLastWord(ColorId.FormatId.getFormatIds());

		return null;
	}

	private void applyForcedGradient(final OfflinePlayer player, final String type, final CompChatColor hex1, final CompChatColor hex2) {
		final PlayerCache pCache = PlayerCache.fromOfflinePlayer(player);

		if (type.equalsIgnoreCase("name")) {
			if (!Settings.Color_Settings.NAME_GRADIENT_COLORS) {
				tellError(Localization.Gradient_Color_Selection.DISABLED_MESSAGE.replace("%type%", "Name"));
				return;
			}

			pCache.setNameColor(null);
			pCache.setNameRainbowColors(false);
			pCache.setCustomGradient1(hex1);
			pCache.setCustomGradient2(hex2);
		} else {
			if (!Settings.Color_Settings.CHAT_GRADIENT_COLORS) {
				tellError(Localization.Gradient_Color_Selection.DISABLED_MESSAGE.replace("%type%", "Chat"));
				return;
			}

			pCache.setChatColor(null);
			pCache.setChatFormat(null);
			pCache.setChatRainbowColors(false);
			pCache.setChatCustomGradient1(hex1);
			pCache.setChatCustomGradient2(hex2);
		}

		if (args.length < 5) {
			if (type.equalsIgnoreCase("name"))
				pCache.setNameFormat(null);
			else
				pCache.setChatFormat(null);
		} else {
			final String format = args[4];

			if (format.equalsIgnoreCase("none")) {
				tellInfo("If you wish to not have a gradient with a format, you can ignore the format parameter.");

				if (type.equalsIgnoreCase("name"))
					pCache.setNameFormat(null);
				else
					pCache.setChatFormat(null);
			}

			if (ColorId.FormatId.getFormatIds().contains(format)) {
				if (type.equalsIgnoreCase("name"))
					pCache.setNameFormat(UltraColorUtil.getNameFormatToChatColor(format));
				else
					pCache.setChatFormat(UltraColorUtil.getFormatToCompChatColor(format));
			}
		}

		if (!pCache.getNickName().equalsIgnoreCase("none") && type.equalsIgnoreCase("name")) {
			if (args.length >= 5)
				pCache.setColoredNickName(ChatUtil.generateGradient(UltraColorUtil.nameFormatToString(pCache.getNameFormat())
						+ pCache.getNickName(), pCache.getCustomGradient1(), pCache.getCustomGradient2()));
			else
				pCache.setColoredNickName(ChatUtil.generateGradient(pCache.getNickName(), pCache.getCustomGradient1(), pCache.getCustomGradient2()));
		}

		if (player.isOnline()) {
			final Player onlinePlayer = (Player) player;

			if (type.equalsIgnoreCase("name")) {
				String message;

				if (pCache.getNameFormat() == null)
					message = onlinePlayer.getName();
				else
					message = UltraColorUtil.getNameFormatToChatColor(args[4]) + onlinePlayer.getName();

				onlinePlayer.setDisplayName(ChatUtil.generateGradient(message, hex1, hex2));

				if (!pCache.getColoredNickName().equalsIgnoreCase("none"))
					onlinePlayer.setDisplayName(pCache.getColoredNickName());
			}

			Messenger.info(onlinePlayer, Localization.Other.ADMIN_SET_GRADIENT_COLOR_SUCCESS_MESSAGE.replace("%new_gradient%",
							ChatUtil.generateGradient(pCache.getNameFormat() != null ? pCache.getNameFormat() + "this" : "this", hex1, hex2))
					.replace("%gradient_type%", type));
		}

		tellSuccess(Localization.Gradient_Color_Selection_Custom.SUCCESS_MESSAGE);
	}
}
