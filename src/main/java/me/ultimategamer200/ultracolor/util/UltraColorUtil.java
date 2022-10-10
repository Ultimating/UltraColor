package me.ultimategamer200.ultracolor.util;

import lombok.experimental.UtilityClass;
import me.ultimategamer200.ultracolor.PlayerCache;
import me.ultimategamer200.ultracolor.settings.Settings;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.ChatUtil;
import org.mineacademy.fo.menu.model.ItemCreator;
import org.mineacademy.fo.menu.model.SkullCreator;
import org.mineacademy.fo.remain.CompChatColor;
import org.mineacademy.fo.remain.CompMaterial;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Useful utility methods for the plugin.
 */
@UtilityClass
public class UltraColorUtil {
	/**
	 * Takes the chat color identifier provided and returns a color code based on that identifier.
	 *
	 * @param format the name color identifier
	 * @return the chat color format as a string
	 */
	public String nameFormatToString(final ChatColor format) {
		if (format.isFormat()) return "§" + format.getChar();
		return "";
	}

	/**
	 * Takes the chat color identifier provided and returns a color code based on that identifier.
	 *
	 * @param format the name color identifier
	 * @return the chat color format as a string
	 */
	public String chatFormatToString(final CompChatColor format) {
		if (CompChatColor.getDecorations().contains(format)) return "§" + format.getCode();
		return "";
	}

	/**
	 * Takes the name/chat color identifier provided and returns a color code based on that identifier.
	 *
	 * @param nameOrChatColor the name color identifier
	 * @return the name/chat color format as a string
	 */
	public String nameAndChatColorToString(final CompChatColor nameOrChatColor) {
		// This condition takes into account if the color is a hex or gradient.
		// If this is false, the color is a regular color that's neither a hex nor gradient.
		if (nameOrChatColor.toString().contains("§")) return nameOrChatColor.toString();
		return "§" + nameOrChatColor.getCode();
	}

	/**
	 * Converts a player's username to rainbow colors.
	 *
	 * @param player the player to have rainbow colors.
	 */
	public void convertNameToRainbow(final Player player, final boolean formatEnabled, final String format) {
		final String displayName;
		final PlayerCache pCache = PlayerCache.fromPlayer(player);
		pCache.setNameColor(null);

		if (pCache.getCustomGradient1() != null || pCache.getCustomGradient2() != null) {
			pCache.setCustomGradient1(null);
			pCache.setCustomGradient2(null);
		}

		if (!pCache.getNickName().equalsIgnoreCase("None"))
			displayName = convertStringToRainbow(pCache.getNickName(), formatEnabled, format);
		else displayName = convertStringToRainbow(player.getName(), formatEnabled, format);

		player.setDisplayName(displayName);
		pCache.setNameRainbowColors(true);

		if (formatEnabled) pCache.setNameFormat(UltraColorUtil.getNameFormatToChatColor(format));

		if (!pCache.getColoredNickName().equalsIgnoreCase("none"))
			pCache.setColoredNickName(displayName);
	}

	/**
	 * Applies a chat color to a specific player.
	 *
	 * @param player the player to apply the color.
	 * @param color  the color to apply.
	 */
	public void applyChatColor(final OfflinePlayer player, final CompChatColor color) {
		final PlayerCache pCache = PlayerCache.fromOfflinePlayer(player);
		pCache.setChatColor(color);

		if (pCache.isChatRainbowColors()) pCache.setChatRainbowColors(false);

		if (pCache.getCustomGradient1() != null || pCache.getChatCustomGradient2() != null) {
			pCache.setChatCustomGradient1(null);
			pCache.setChatCustomGradient2(null);
		}
	}

	/**
	 * Applies a chat format to a specific player.
	 *
	 * @param player the player to apply the format.
	 * @param format the format to apply.
	 */
	public void applyChatFormat(final OfflinePlayer player, final CompChatColor format) {
		final PlayerCache pCache = PlayerCache.fromOfflinePlayer(player);

		if (pCache.getCustomGradient1() != null || pCache.getChatCustomGradient2() != null)
			applyFormatToGradient(player, "chat", getNameFormatToChatColor(format.getName()));
		else pCache.setChatFormat(format);
	}

	/**
	 * Applies a name color and/or format to a specific player.
	 *
	 * @param player the player to apply the color.
	 * @param color  the color to apply.
	 * @param format the format to apply or null if none.
	 */
	public void applyNameColor(final Player player, final CompChatColor color, final ChatColor format) {
		final PlayerCache pCache = PlayerCache.fromPlayer(player);

		if (color != null) pCache.setNameColor(color);
		if (format != null) pCache.setNameFormat(format);

		if (pCache.getCustomGradient1() != null || pCache.getCustomGradient2() != null) {
			if (pCache.getNameFormat() == null) {
				pCache.setCustomGradient1(null);
				pCache.setCustomGradient2(null);
			} else {
				applyFormatToGradient(player, "name", pCache.getNameFormat());
				return;
			}
		}

		if (pCache.getNickName().equalsIgnoreCase("none")) {
			if (pCache.getNameFormat() != null && pCache.getNameColor() != null)
				player.setDisplayName(nameAndChatColorToString(pCache.getNameColor()) + nameFormatToString(pCache.getNameFormat())
						+ player.getName());
			else if (pCache.getNameFormat() == null && pCache.getNameColor() != null)
				player.setDisplayName(nameAndChatColorToString(pCache.getNameColor()) + player.getName());
			else if (pCache.getNameFormat() != null)
				player.setDisplayName(nameFormatToString(pCache.getNameFormat()) + player.getName());
		} else {
			if (pCache.getNameFormat() != null && pCache.getNameColor() != null) {
				player.setDisplayName(UltraColorUtil.nameAndChatColorToString(pCache.getNameColor())
						+ UltraColorUtil.nameFormatToString(pCache.getNameFormat()) + pCache.getNickName());
				pCache.setColoredNickName(UltraColorUtil.nameAndChatColorToString(pCache.getNameColor())
						+ UltraColorUtil.nameFormatToString(pCache.getNameFormat()) + pCache.getNickName());
			} else if (pCache.getNameFormat() == null && pCache.getNameColor() != null) {
				player.setDisplayName(UltraColorUtil.nameAndChatColorToString(pCache.getNameColor()) + pCache.getNickName());
				pCache.setColoredNickName(UltraColorUtil.nameAndChatColorToString(pCache.getNameColor()) + pCache.getNickName());
			} else if (pCache.getNameFormat() != null) {
				player.setDisplayName(UltraColorUtil.nameFormatToString(pCache.getNameFormat()) + pCache.getNickName());
				pCache.setColoredNickName(UltraColorUtil.nameFormatToString(pCache.getNameFormat()) + pCache.getNickName());
			}
		}
	}

	/**
	 * Applies a specified format to a pre-defined gradient for a player.
	 *
	 * @param player player to apply the format to.
	 * @param type   the pre-gradient to use.
	 * @param format the format to apply.
	 */
	public void applyFormatToGradient(final OfflinePlayer player, final String type, final ChatColor format) {
		final PlayerCache pCache = PlayerCache.fromOfflinePlayer(player);

		if (format.isFormat()) {
			if (type.equalsIgnoreCase("name")) {
				if (pCache.getNickName().equalsIgnoreCase("none")) {
					if (player.isOnline()) {
						final Player onlinePlayer = (Player) player;
						onlinePlayer.setDisplayName(ChatUtil.generateGradient(nameFormatToString(pCache.getNameFormat()) + player.getName(),
								pCache.getCustomGradient1(), pCache.getCustomGradient2()));
					}
				} else {
					if (player.isOnline()) {
						final Player onlinePlayer = (Player) player;
						onlinePlayer.setDisplayName(ChatUtil.generateGradient(nameFormatToString(pCache.getNameFormat())
								+ pCache.getNickName(), pCache.getCustomGradient1(), pCache.getCustomGradient2()));
					}

					pCache.setColoredNickName(ChatUtil.generateGradient(nameFormatToString(pCache.getNameFormat())
							+ pCache.getNickName(), pCache.getCustomGradient1(), pCache.getCustomGradient2()));
				}
			} else
				pCache.setChatFormat(getFormatToCompChatColor(format.name()));
		}
	}

	/**
	 * Converts a string message to rainbow colors.
	 *
	 * @param message the message to have rainbow colors.
	 * @return the rainbow message
	 */
	public String convertStringToRainbow(final String message, final boolean formatEnabled, final String format) {
		StringBuilder result = new StringBuilder();
		int stringSize = message.length();
		int colorCount = 0;

		final CompChatColor[] rainbowColors = {CompChatColor.YELLOW, CompChatColor.GOLD, CompChatColor.RED,
				CompChatColor.GREEN, CompChatColor.BLUE, CompChatColor.LIGHT_PURPLE};
		String formatCode = "";

		if (formatEnabled)
			formatCode = nameFormatToString(getNameFormatToChatColor(format));

		for (int i = 0; i < stringSize; i++) {
			result.append(rainbowColors[colorCount % rainbowColors.length]).append(formatEnabled ? formatCode : "")
					.append(message.charAt(i));
			if (!Character.isWhitespace(message.charAt(i))) colorCount++;
		}

		return result.toString();
	}

	/**
	 * Modifies a color GUI item lore with the color preview for that color.
	 *
	 * @param lore      the lore to modify.
	 * @param color     the color to get the preview of.
	 * @param gradients the gradient to use for the preview if the color is a gradient.
	 * @return
	 */
	public List<String> modifyColorLoreWithPreview(final List<String> lore, final String color, final List<String> gradients) {
		final List<String> modifiedLore = new ArrayList<>();

		for (final String string : lore) {
			if (string.contains("{color_preview}") && !color.equalsIgnoreCase(ColorId.RAINBOW.getId())
					&& !color.equalsIgnoreCase("gradient")) {
				modifiedLore.add(string.replace("{color_preview}", color + "this"));
				continue;
			} else if (string.contains("{color_preview}") && color.equalsIgnoreCase(ColorId.RAINBOW.getId())) {
				modifiedLore.add(string.replace("{color_preview}", convertStringToRainbow(
						"this", false, "")));
				continue;
			} else if (string.contains("{color_preview}") && color.equalsIgnoreCase("gradient")) {
				modifiedLore.add(string.replace("{color_preview}", ChatUtil.generateGradient("this",
						CompChatColor.of(gradients.get(0)), CompChatColor.of(gradients.get(1)))));
				continue;
			}

			modifiedLore.add(string);
		}

		return modifiedLore;
	}

	/**
	 * A convenient method for making a menu item that allows support of custom player heads.
	 *
	 * @param material the material ID as a string
	 * @param name     the name for the item.
	 * @param lore     the lore for the item.
	 * @return an ItemStack of the specified material, name, and lore.
	 */
	public ItemStack makeMenuItem(final String material, final String name, final List<String> lore) {
		if (material.startsWith("PLAYER_HEAD:")) {
			final String textureLink = material.substring(12);
			final ItemStack head = SkullCreator.itemFromUrl(textureLink);
			return ItemCreator.of(head).name(name).lore(lore).make();
		}

		return ItemCreator.of(CompMaterial.valueOf(material), name, lore).make();
	}

	/**
	 * Takes a string of a format and converts it to a CompChatColor.
	 *
	 * @param selectedFormat the format to convert.
	 * @return the CompChatColor of the specified format.
	 */
	public CompChatColor getFormatToCompChatColor(final String selectedFormat) {
		for (final CompChatColor decoration : CompChatColor.getDecorations())
			if (decoration.getName().equalsIgnoreCase(selectedFormat)) return decoration;
		return null;
	}

	/**
	 * Takes a string of a format and converts it to a ChatColor.
	 *
	 * @param selectedFormat the format to convert.
	 * @return the ChatColor of the specified format.
	 */
	public ChatColor getNameFormatToChatColor(final String selectedFormat) {
		for (final ChatColor color : ChatColor.values()) {
			if (!color.isFormat()) continue;
			if (color.name().equalsIgnoreCase(selectedFormat)) return color;
		}

		return null;
	}

	/**
	 * Gets all the nicknames uncolored.
	 */
	public Set<String> getNickNamesUnColored() {
		final Set<String> nickNames = new HashSet<>();

		for (final PlayerCache pCache : PlayerCache.cacheMap.values())
			if (!pCache.getNickName().equalsIgnoreCase("none")) nickNames.add(pCache.getNickName());

		return nickNames;
	}

	/**
	 * Is the specified color able to be selected?
	 */
	public boolean isColorSelectedAbleToBeSet(final String type, final String color, final Player player) {
		String permissionStarter;

		if (type.equalsIgnoreCase("name")) permissionStarter = UltraColorPermissions.NAME_COLOR;
		else permissionStarter = UltraColorPermissions.CHAT_COLOR;

		if (player.hasPermission(permissionStarter + ".*") || color.equalsIgnoreCase("none"))
			return true;

		if (type.equalsIgnoreCase("name")) {
			if (!isNameColorEnabled(color)) return false;
		} else if (type.equalsIgnoreCase("chat"))
			if (!isChatColorEnabled(color)) return false;

		for (final CompChatColor compChatColor : CompChatColor.getColors())
			if (player.hasPermission(permissionStarter + "." + compChatColor.getCode())) return true;

		return player.hasPermission(permissionStarter + ".r");
	}

	/**
	 * Is the specified format able to be selected?
	 */
	public boolean isFormatSelectedAbleToBeSet(final String type, final String format, final Player player) {
		String permissionStarter;

		if (type.equalsIgnoreCase("name")) permissionStarter = UltraColorPermissions.Color.NAME_FORMAT;
		else permissionStarter = UltraColorPermissions.Color.CHAT_FORMAT;

		if (player.hasPermission(permissionStarter + ".*") || format.equalsIgnoreCase("none"))
			return true;

		if (type.equalsIgnoreCase("name")) {
			if (!isNameFormatEnabled(format)) return false;
		} else if (type.equalsIgnoreCase("chat"))
			if (!isChatFormatEnabled(format)) return false;

		return player.hasPermission(permissionStarter + "." + getFormatToCompChatColor(format).getCode());
	}

	/**
	 * Is the specified name color enabled?
	 */
	public boolean isNameColorEnabled(final String color) {
		if (color.equalsIgnoreCase(ColorId.BLACK.getId()))
			return Settings.Color_Settings_Name_Colors.BLACK_COLOR_ENABLED;
		else if (color.equalsIgnoreCase(ColorId.DARK_BLUE.getId()))
			return Settings.Color_Settings_Name_Colors.DARK_BLUE_COLOR_ENABLED;
		else if (color.equalsIgnoreCase(ColorId.DARK_GREEN.getId()))
			return Settings.Color_Settings_Name_Colors.DARK_GREEN_COLOR_ENABLED;
		else if (color.equalsIgnoreCase(ColorId.DARK_AQUA.getId()))
			return Settings.Color_Settings_Name_Colors.DARK_AQUA_COLOR_ENABLED;
		else if (color.equalsIgnoreCase(ColorId.DARK_RED.getId()))
			return Settings.Color_Settings_Name_Colors.DARK_RED_COLOR_ENABLED;
		else if (color.equalsIgnoreCase(ColorId.DARK_PURPLE.getId()))
			return Settings.Color_Settings_Name_Colors.DARK_PURPLE_COLOR_ENABLED;
		else if (color.equalsIgnoreCase(ColorId.ORANGE.getId()))
			return Settings.Color_Settings_Name_Colors.ORANGE_COLOR_ENABLED;
		else if (color.equalsIgnoreCase(ColorId.GRAY.getId()))
			return Settings.Color_Settings_Name_Colors.DARK_GRAY_COLOR_ENABLED;
		else if (color.equalsIgnoreCase(ColorId.DARK_GRAY.getId()))
			return Settings.Color_Settings_Name_Colors.DARK_GRAY_COLOR_ENABLED;
		else if (color.equalsIgnoreCase(ColorId.BLUE.getId()))
			return Settings.Color_Settings_Name_Colors.BLUE_COLOR_ENABLED;
		else if (color.equalsIgnoreCase(ColorId.GREEN.getId()))
			return Settings.Color_Settings_Name_Colors.GREEN_COLOR_ENABLED;
		else if (color.equalsIgnoreCase(ColorId.AQUA.getId()))
			return Settings.Color_Settings_Name_Colors.AQUA_COLOR_ENABLED;
		else if (color.equalsIgnoreCase(ColorId.RED.getId()))
			return Settings.Color_Settings_Name_Colors.RED_COLOR_ENABLED;
		else if (color.equalsIgnoreCase(ColorId.LIGHT_PURPLE.getId()))
			return Settings.Color_Settings_Name_Colors.LIGHT_PURPLE_COLOR_ENABLED;
		else if (color.equalsIgnoreCase(ColorId.YELLOW.getId()))
			return Settings.Color_Settings_Name_Colors.WHITE_COLOR_ENABLED;
		else if (color.equalsIgnoreCase(ColorId.WHITE.getId()))
			return Settings.Color_Settings_Name_Colors.WHITE_COLOR_ENABLED;
		else if (color.equalsIgnoreCase(ColorId.RAINBOW.getId()))
			return Settings.Color_Settings.NAME_RAINBOW_COLORS;
		else
			return color.equalsIgnoreCase("none");
	}

	/**
	 * Is the specified name format enabled?
	 */
	public boolean isNameFormatEnabled(final String format) {
		if (format.equalsIgnoreCase(ColorId.FormatId.BOLD.getId()))
			return Settings.Color_Settings_Name_Formats.BOLD_FORMAT;
		else if (format.equalsIgnoreCase(ColorId.FormatId.ITALIC.getId()))
			return Settings.Color_Settings_Name_Formats.ITALIC_FORMAT;
		else if (format.equalsIgnoreCase(ColorId.FormatId.MAGIC.getId()))
			return Settings.Color_Settings_Name_Formats.MAGIC_FORMAT;
		else if (format.equalsIgnoreCase(ColorId.FormatId.STRIKETHROUGH.getId()))
			return Settings.Color_Settings_Name_Formats.STRIKETHROUGH_FORMAT;
		else if (format.equalsIgnoreCase(ColorId.FormatId.UNDERLINE.getId()))
			return Settings.Color_Settings_Name_Formats.UNDERLINE_FORMAT;
		return false;
	}

	/**
	 * Is the specified chat format enabled?
	 */
	public boolean isChatFormatEnabled(final String format) {
		if (format.equalsIgnoreCase(ColorId.FormatId.BOLD.getId()))
			return Settings.Color_Settings_Chat_Formats.BOLD_FORMAT;
		else if (format.equalsIgnoreCase(ColorId.FormatId.ITALIC.getId()))
			return Settings.Color_Settings_Chat_Formats.ITALIC_FORMAT;
		else if (format.equalsIgnoreCase(ColorId.FormatId.MAGIC.getId()))
			return Settings.Color_Settings_Chat_Formats.MAGIC_FORMAT;
		else if (format.equalsIgnoreCase(ColorId.FormatId.STRIKETHROUGH.getId()))
			return Settings.Color_Settings_Chat_Formats.STRIKETHROUGH_FORMAT;
		else if (format.equalsIgnoreCase(ColorId.FormatId.UNDERLINE.getId()))
			return Settings.Color_Settings_Chat_Formats.UNDERLINE_FORMAT;
		return false;
	}

	/**
	 * Is the specified chat color enabled?
	 */
	public boolean isChatColorEnabled(final String color) {
		if (color.equalsIgnoreCase(ColorId.BLACK.getId()))
			return Settings.Color_Settings_Chat_Colors.BLACK_COLOR_ENABLED;
		else if (color.equalsIgnoreCase(ColorId.DARK_BLUE.getId()))
			return Settings.Color_Settings_Chat_Colors.DARK_BLUE_COLOR_ENABLED;
		else if (color.equalsIgnoreCase(ColorId.DARK_GREEN.getId()))
			return Settings.Color_Settings_Chat_Colors.DARK_GREEN_COLOR_ENABLED;
		else if (color.equalsIgnoreCase(ColorId.DARK_AQUA.getId()))
			return Settings.Color_Settings_Chat_Colors.DARK_AQUA_COLOR_ENABLED;
		else if (color.equalsIgnoreCase(ColorId.DARK_RED.getId()))
			return Settings.Color_Settings_Chat_Colors.DARK_RED_COLOR_ENABLED;
		else if (color.equalsIgnoreCase(ColorId.DARK_PURPLE.getId()))
			return Settings.Color_Settings_Chat_Colors.DARK_PURPLE_COLOR_ENABLED;
		else if (color.equalsIgnoreCase(ColorId.ORANGE.getId()))
			return Settings.Color_Settings_Chat_Colors.ORANGE_COLOR_ENABLED;
		else if (color.equalsIgnoreCase(ColorId.GRAY.getId()))
			return Settings.Color_Settings_Chat_Colors.DARK_GRAY_COLOR_ENABLED;
		else if (color.equalsIgnoreCase(ColorId.DARK_GRAY.getId()))
			return Settings.Color_Settings_Chat_Colors.DARK_GRAY_COLOR_ENABLED;
		else if (color.equalsIgnoreCase(ColorId.BLUE.getId()))
			return Settings.Color_Settings_Chat_Colors.BLUE_COLOR_ENABLED;
		else if (color.equalsIgnoreCase(ColorId.GREEN.getId()))
			return Settings.Color_Settings_Chat_Colors.GREEN_COLOR_ENABLED;
		else if (color.equalsIgnoreCase(ColorId.AQUA.getId()))
			return Settings.Color_Settings_Chat_Colors.AQUA_COLOR_ENABLED;
		else if (color.equalsIgnoreCase(ColorId.RED.getId()))
			return Settings.Color_Settings_Chat_Colors.RED_COLOR_ENABLED;
		else if (color.equalsIgnoreCase(ColorId.LIGHT_PURPLE.getId()))
			return Settings.Color_Settings_Chat_Colors.LIGHT_PURPLE_COLOR_ENABLED;
		else if (color.equalsIgnoreCase(ColorId.YELLOW.getId()))
			return Settings.Color_Settings_Chat_Colors.WHITE_COLOR_ENABLED;
		else if (color.equalsIgnoreCase(ColorId.WHITE.getId()))
			return Settings.Color_Settings_Chat_Colors.WHITE_COLOR_ENABLED;
		else if (color.equalsIgnoreCase(ColorId.RAINBOW.getId()))
			return Settings.Color_Settings.CHAT_RAINBOW_COLORS;
		else
			return color.equalsIgnoreCase("none");
	}

	public boolean isAtLeastOneFormatEnabled(final String formatType) {
		for (final CompChatColor color : CompChatColor.getDecorations()) {
			if (formatType.equalsIgnoreCase("name")) {
				if (isNameFormatEnabled(color.getName())) return true;
			} else if (isChatFormatEnabled(color.getName())) return true;
		}
		return false;
	}

	/**
	 * Is the specified hex valid?
	 */
	public boolean isHexValid(final String hex) {
		return hex.startsWith("#") && hex.length() == 7;
	}

	/**
	 * Checks if a list of hexes are valid.
	 *
	 * @return If one of the hexes is not valid, return false. Otherwise, return true.
	 */
	public boolean areHexesValid(final List<String> hexes) {
		for (final String hex : hexes) if (!isHexValid(hex)) return false;
		return true;
	}

	/**
	 * Gets the specified player's name coloring.
	 */
	public String getPlayerNameInColor(final Player player) {
		final PlayerCache pCache = PlayerCache.fromPlayer(player);

		if (!pCache.getColoredNickName().equalsIgnoreCase("none"))
			return pCache.getColoredNickName();
		else if (!pCache.getNickName().equalsIgnoreCase("none"))
			return pCache.getNickName();

		if (pCache.isNameRainbowColors())
			return convertStringToRainbow(player.getName(), pCache.getNameFormat() != null, pCache.getNameFormat() != null ? pCache.getNameFormat().name() : "");

		if (pCache.getNameColor() != null) {
			if (pCache.getNameFormat() != null)
				return nameAndChatColorToString(pCache.getNameColor()) + nameFormatToString(pCache.getNameFormat()) + player.getName();
			else
				return nameAndChatColorToString(pCache.getNameColor()) + player.getName();
		} else {
			if (pCache.getCustomGradient1() != null && pCache.getCustomGradient2() != null) {
				if (pCache.getNameFormat() != null) {
					return ChatUtil.generateGradient(nameFormatToString(pCache.getNameFormat()) + player.getName(),
							pCache.getCustomGradient1(), pCache.getCustomGradient2());
				}

				return ChatUtil.generateGradient(player.getName(), pCache.getCustomGradient1(), pCache.getCustomGradient2());
			}

			if (pCache.getNameFormat() != null) return nameFormatToString(pCache.getNameFormat()) + player.getName();
		}

		return player.getName();
	}
}
