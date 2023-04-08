package me.ultimategamer200.ultracolor.commands;

import me.ultimategamer200.ultracolor.PlayerCache;
import me.ultimategamer200.ultracolor.settings.AllowedHexesData;
import me.ultimategamer200.ultracolor.settings.Localization;
import me.ultimategamer200.ultracolor.settings.Settings;
import me.ultimategamer200.ultracolor.util.ColorId;
import me.ultimategamer200.ultracolor.util.UltraColorPermissions;
import me.ultimategamer200.ultracolor.util.UltraColorUtil;
import org.bukkit.entity.Player;
import org.mineacademy.fo.Messenger;
import org.mineacademy.fo.command.SimpleCommand;
import org.mineacademy.fo.remain.CompChatColor;
import org.mineacademy.fo.remain.Remain;

import java.util.List;

public class HexColorCommand extends SimpleCommand {
	public HexColorCommand() {
		super("hexcolor|hc");
		setMinArguments(2);
		setUsage("<chat|name> <hexcode> [format]");
		setPermission(UltraColorPermissions.Command.HEX_COLOR);
		setPermissionMessage(Localization.Other.NO_PERMISSION.replace("{permission}", UltraColorPermissions.Command.HEX_COLOR));
		setDescription("Set your hex color.");
	}

	@Override
	protected void onCommand() {
		checkConsole();
		final String hexCode = args[1];
		final Player player = getPlayer();
		final String hexColor = hexCode.length() == 6 ? "#" + hexCode : hexCode;

		if (hexCode.length() == 6 || hexCode.length() == 7 && Remain.hasHexColors())
			setColors(player, hexColor);

		if (!Remain.hasHexColors())
			Messenger.error(player, Localization.Hex_Colors.HEX_COLOR_UNSUPPORTED_VERSION_MESSAGE);
		if (!UltraColorUtil.isHexValid(hexColor) && Remain.hasHexColors())
			Messenger.error(player, Localization.Hex_Colors.HEX_COLOR_ERROR_MESSAGE);
	}

	@Override
	protected List<String> tabComplete() {
		if (args.length == 1)
			return completeLastWord("chat", "name");
		if (args.length == 2 && !AllowedHexesData.getInstance().getAllowedHexes().isEmpty()
				&& Settings.Color_Settings.ALLOW_ONLY_CERTAIN_HEX_COLORS) {
			if (args[0].equalsIgnoreCase("chat"))
				return completeLastWord(AllowedHexesData.getInstance().getAllowedHexStringsWithoutHashTag("chat"));
			else
				return completeLastWord(AllowedHexesData.getInstance().getAllowedHexStringsWithoutHashTag("name"));
		}

		if (args.length == 3)
			return completeLastWord(ColorId.FormatId.getFormatIds());

		return NO_COMPLETE;
	}

	private void setColors(final Player player, final String hexColor) {
		final AllowedHexesData data = AllowedHexesData.getInstance();
		final PlayerCache pCache = PlayerCache.fromPlayer(player);
		final String enteredType = args[0];
		boolean hexTypeEnabled = Settings.Color_Settings.CHAT_HEX_COLORS;

		if (enteredType.equalsIgnoreCase("name"))
			hexTypeEnabled = Settings.Color_Settings.NAME_HEX_COLORS;

		if (hexTypeEnabled) {
			if (Settings.Color_Settings.ALLOW_ONLY_CERTAIN_HEX_COLORS) {
				if (data.findAllowedHex(hexColor) != null) {
					final String permission = data.findAllowedHex(hexColor).getPermission();
					final String type = data.findAllowedHex(hexColor).getType();

					if (type.equalsIgnoreCase(enteredType) || type.equalsIgnoreCase("both")) {
						if (player.hasPermission("ultracolor.bypass.hexlimits") || permission.equalsIgnoreCase("None")
								|| player.hasPermission(permission)) {
							setHexColor(enteredType, hexColor, pCache);
							setFormat(player, pCache, enteredType);
						} else
							tellError(Localization.Other.NO_PERMISSION.replace("{permission}", permission));
					}
				} else if (data.getAllowedHexes().isEmpty())
					tellError(Localization.Hex_Colors.HEX_COLOR_WHITELIST_EMPTY);
				else
					tellError(Localization.Hex_Colors.HEX_COLOR_NOT_ALLOWED_MESSAGE);
			} else {
				setHexColor(args[0], hexColor, pCache);
				setFormat(player, pCache, args[0]);
			}
		} else
			tellError(Localization.Hex_Colors.DISABLED_TYPE_MESSAGE);
	}

	private void setHexColor(final String type, final String hexColor, final PlayerCache pCache) {
		if (type.equalsIgnoreCase("name")) {
			pCache.setNameColor(CompChatColor.of(hexColor));
			pCache.setNameFormat(null);
			pCache.setNameRainbowColors(false);
		} else {
			pCache.setChatColor(CompChatColor.of(hexColor));
			pCache.setChatFormat(null);
			pCache.setChatRainbowColors(false);
		}

		pCache.clearGradients(type);
	}

	private void setFormat(final Player player, final PlayerCache pCache, final String type) {
		final String successMessage;

		if (args.length < 3) {
			if (type.equalsIgnoreCase("name")) {
				successMessage = Localization.Hex_Colors.HEX_COLOR_SUCCESS_MESSAGE.replace("%hex_color%",
						pCache.getNameColor() + "this");

				if (!pCache.getNickName().equalsIgnoreCase("none"))
					player.setDisplayName(UltraColorUtil.nameAndChatColorToString(pCache.getNameColor()) + pCache.getNickName());
				else
					player.setDisplayName(UltraColorUtil.nameAndChatColorToString(pCache.getNameColor()) + player.getName());

				pCache.setColoredNickName(player.getDisplayName());
			} else {
				successMessage = Localization.Hex_Colors.HEX_COLOR_SUCCESS_MESSAGE.replace("%hex_color%",
						pCache.getChatColor() + "this");
			}
		} else {
			final String format = args[2];

			if (!UltraColorUtil.isFormatSelectedAbleToBeSet(type, format, player)) {
				tellError(Localization.Other.UNABLE_TO_SELECT_FORMAT_MESSAGE);
				return;
			}

			if (type.equalsIgnoreCase("name")) {
				pCache.setNameFormat(UltraColorUtil.getNameFormatToChatColor(format));

				if (!pCache.getNickName().equalsIgnoreCase("none"))
					player.setDisplayName(UltraColorUtil.nameAndChatColorToString(pCache.getNameColor())
							+ UltraColorUtil.nameFormatToString(pCache.getNameFormat()) + pCache.getNickName());
				else
					player.setDisplayName(UltraColorUtil.nameAndChatColorToString(pCache.getNameColor())
							+ UltraColorUtil.nameFormatToString(pCache.getNameFormat()) + player.getName());

				pCache.setColoredNickName(player.getDisplayName());
				successMessage = Localization.Hex_Colors.HEX_COLOR_SUCCESS_MESSAGE.replace("%hex_color%",
						UltraColorUtil.nameFormatToString(pCache.getNameFormat()) + pCache.getNameColor() + "this");
			} else {
				pCache.setChatFormat(UltraColorUtil.getFormatToCompChatColor(format));
				successMessage = Localization.Hex_Colors.HEX_COLOR_SUCCESS_MESSAGE.replace("%hex_color%",
						UltraColorUtil.chatFormatToString(pCache.getChatFormat()) + pCache.getChatFormat().getName());
			}
		}

		Messenger.success(player, successMessage);
	}
}
