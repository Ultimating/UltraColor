package me.ultimategamer200.ultracolor.commands;

import me.ultimategamer200.ultracolor.PlayerCache;
import me.ultimategamer200.ultracolor.settings.AllowedHexesData;
import me.ultimategamer200.ultracolor.settings.Localization;
import me.ultimategamer200.ultracolor.settings.Settings;
import me.ultimategamer200.ultracolor.util.UltraColorPermissions;
import me.ultimategamer200.ultracolor.util.UltraColorUtil;
import org.bukkit.entity.Player;
import org.mineacademy.fo.Messenger;
import org.mineacademy.fo.command.SimpleCommand;
import org.mineacademy.fo.remain.CompChatColor;
import org.mineacademy.fo.remain.Remain;

import java.util.ArrayList;
import java.util.Arrays;
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

	final List<String> formats = Arrays.asList("bold", "underline", "italic", "strikethrough", "magic");

	@Override
	protected void onCommand() {
		checkConsole();
		final String hexCode = args[1];
		final Player player = getPlayer();
		final PlayerCache pCache = PlayerCache.fromPlayer(player);
		final String hexColor = hexCode.length() == 6 ? "#" + hexCode : hexCode;
		final AllowedHexesData data = AllowedHexesData.getInstance();

		if (hexCode.length() == 6 || hexCode.length() == 7 && Remain.hasHexColors()) {
			if (args[0].equals("chat") && Settings.Color_Settings.CHAT_HEX_COLORS) {
				if (Settings.Color_Settings.ALLOW_ONLY_CERTAIN_HEX_COLORS) {
					if (data.findAllowedHex(hexColor) != null) {
						final String permission = data.findAllowedHex(hexColor).getPermission();
						final String type = data.findAllowedHex(hexColor).getType();

						if (type.equalsIgnoreCase("chat") || type.equalsIgnoreCase("both")) {
							if (player.hasPermission("ultracolor.bypass.hexlimits") || permission.equalsIgnoreCase("None")
									|| player.hasPermission(permission)) {
								setHexColor(args[0], hexColor, pCache);

								if (args.length < 3)
									Messenger.success(player, Localization.Hex_Colors.HEX_COLOR_SUCCESS_MESSAGE.replace(
											"%hex_color%", pCache.getChatColor() + "this"));
								else {
									final String format = args[2];
									pCache.setChatFormat(UltraColorUtil.getFormatToCompChatColor(format));
									Messenger.success(player, Localization.Hex_Colors.HEX_COLOR_SUCCESS_MESSAGE.replace(
											"%hex_color%", pCache.getChatColor() + UltraColorUtil.chatFormatToString
													(pCache.getChatFormat()) + "this"));
								}
							} else
								tellError(Localization.Other.NO_PERMISSION.replace("{permission}", permission));
						}
					} else if (data.getAllowedHexes().isEmpty())
						tellError(Localization.Hex_Colors.HEX_COLOR_WHITELIST_EMPTY);
					else
						tellError(Localization.Hex_Colors.HEX_COLOR_NOT_ALLOWED_MESSAGE);
				} else {
					setHexColor(args[0], hexColor, pCache);

					if (args.length < 3)
						Messenger.success(player, Localization.Hex_Colors.HEX_COLOR_SUCCESS_MESSAGE.replace(
								"%hex_color%", pCache.getChatColor() + "this"));
					else {
						final String format = args[2];
						pCache.setChatFormat(UltraColorUtil.getFormatToCompChatColor(format));
						Messenger.success(player, Localization.Hex_Colors.HEX_COLOR_SUCCESS_MESSAGE.replace("%hex_color%",
								pCache.getChatColor() + UltraColorUtil.chatFormatToString(pCache.getChatFormat()) + "this"));
					}
				}

				return;
			} else if (args[0].equalsIgnoreCase("chat") && !Settings.Color_Settings.CHAT_HEX_COLORS) {
				tellError(Localization.Hex_Colors.DISABLED_TYPE_MESSAGE);
				return;
			}

			if (args[0].equals("name") && Settings.Color_Settings.NAME_HEX_COLORS) {
				if (Settings.Color_Settings.ALLOW_ONLY_CERTAIN_HEX_COLORS) {
					if (data.findAllowedHex(hexColor) != null) {
						final String permission = data.findAllowedHex(hexColor).getPermission();
						final String type = data.findAllowedHex(hexColor).getType();

						if (type.equalsIgnoreCase("name") || type.equalsIgnoreCase("both")) {
							if (player.hasPermission("ultracolor.bypass.hexlimits") || permission.equalsIgnoreCase("None")
									|| player.hasPermission(permission)) {
								setHexColor(args[0], hexColor, pCache);

								if (args.length < 3) {
									if (!pCache.getNickName().equalsIgnoreCase("none"))
										player.setDisplayName(UltraColorUtil.nameAndChatColorToString(pCache.getNameColor()) + pCache.getNickName());
									else
										player.setDisplayName(UltraColorUtil.nameAndChatColorToString(pCache.getNameColor()) + player.getName());

									pCache.setColoredNickName(player.getDisplayName());
									Messenger.success(player, Localization.Hex_Colors.HEX_COLOR_SUCCESS_MESSAGE.replace(
											"%hex_color%", pCache.getNameColor() + "this"));
								} else {
									final String format = args[2];
									pCache.setNameFormat(UltraColorUtil.getNameFormatToChatColor(format));

									if (!pCache.getNickName().equalsIgnoreCase("none"))
										player.setDisplayName(UltraColorUtil.nameAndChatColorToString(pCache.getNameColor())
												+ UltraColorUtil.nameFormatToString(pCache.getNameFormat()) + pCache.getNickName());
									else
										player.setDisplayName(UltraColorUtil.nameAndChatColorToString(pCache.getNameColor())
												+ UltraColorUtil.nameFormatToString(pCache.getNameFormat()) + player.getName());

									pCache.setColoredNickName(player.getDisplayName());
									Messenger.success(player, Localization.Hex_Colors.HEX_COLOR_SUCCESS_MESSAGE.replace("%hex_color%",
											pCache.getNameColor() + UltraColorUtil.nameFormatToString(pCache.getNameFormat()) + "this"));
								}
							} else
								tellError(Localization.Other.NO_PERMISSION.replace("{permission}", permission));
						}
					} else if (data.getAllowedHexes().isEmpty())
						tellError(Localization.Hex_Colors.HEX_COLOR_WHITELIST_EMPTY);
					else
						tellError(Localization.Hex_Colors.HEX_COLOR_NOT_ALLOWED_MESSAGE);
				} else {
					setHexColor(args[0], hexColor, pCache);

					if (args.length < 3) {
						if (!pCache.getNickName().equalsIgnoreCase("none"))
							player.setDisplayName(UltraColorUtil.nameAndChatColorToString(pCache.getNameColor()) + pCache.getNickName());
						else
							player.setDisplayName(UltraColorUtil.nameAndChatColorToString(pCache.getNameColor()) + player.getName());

						pCache.setColoredNickName(player.getDisplayName());
						Messenger.success(player, Localization.Hex_Colors.HEX_COLOR_SUCCESS_MESSAGE.replace(
								"%hex_color%", pCache.getNameColor() + "this"));
					} else {
						final String format = args[2];
						pCache.setNameFormat(UltraColorUtil.getNameFormatToChatColor(format));

						if (!pCache.getNickName().equalsIgnoreCase("none"))
							player.setDisplayName(UltraColorUtil.nameAndChatColorToString(pCache.getNameColor())
									+ UltraColorUtil.nameFormatToString(pCache.getNameFormat()) + pCache.getNickName());
						else
							player.setDisplayName(UltraColorUtil.nameAndChatColorToString(pCache.getNameColor())
									+ UltraColorUtil.nameFormatToString(pCache.getNameFormat()) + player.getName());

						pCache.setColoredNickName(player.getDisplayName());
						Messenger.success(player, Localization.Hex_Colors.HEX_COLOR_SUCCESS_MESSAGE.replace("%hex_color%",
								pCache.getNameColor() + UltraColorUtil.nameFormatToString(pCache.getNameFormat()) + "this"));
					}
				}

				return;
			} else if (args[0].equalsIgnoreCase("name") && !Settings.Color_Settings.NAME_HEX_COLORS) {
				tellError(Localization.Hex_Colors.DISABLED_TYPE_MESSAGE);
				return;
			}
		}

		if (!Remain.hasHexColors())
			Messenger.error(player, Localization.Hex_Colors.HEX_COLOR_UNSUPPORTED_VERSION_MESSAGE);
		if (hexCode.length() != 6 && Remain.hasHexColors())
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
			return completeLastWord(formats);

		return new ArrayList<>();
	}

	private void setHexColor(final String type, final String hexColor, final PlayerCache pCache) {
		if (type.equalsIgnoreCase("name")) {
			pCache.setNameColor(CompChatColor.of(hexColor));
			pCache.setNameFormat(null);
			pCache.setCustomGradient1(null);
			pCache.setCustomGradient2(null);
			pCache.setNameRainbowColors(false);
		} else {
			pCache.setChatColor(CompChatColor.of(hexColor));
			pCache.setChatFormat(null);
			pCache.setChatCustomGradient1(null);
			pCache.setChatCustomGradient2(null);
			pCache.setChatRainbowColors(false);
		}
	}
}
