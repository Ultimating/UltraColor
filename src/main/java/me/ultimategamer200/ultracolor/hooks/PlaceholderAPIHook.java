package me.ultimategamer200.ultracolor.hooks;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.ultimategamer200.ultracolor.PlayerCache;
import me.ultimategamer200.ultracolor.util.UltraColorUtil;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.mineacademy.fo.ChatUtil;

/**
 * This class stores all the placeholders for the PlaceholderAPI integration.
 */
public class PlaceholderAPIHook extends PlaceholderExpansion {
	@Override
	public @NotNull String getIdentifier() {
		return "ucolor";
	}

	@Override
	public @NotNull String getAuthor() {
		return "UltimateGamer200";
	}

	@Override
	public @NotNull String getVersion() {
		return "1.0";
	}

	@Override
	public String onRequest(OfflinePlayer player, @NotNull String identifier) {
		if (player == null)
			return "";

		final PlayerCache pCache = PlayerCache.fromOfflinePlayer(player);
		final String format = pCache.getNameFormat() != null ? pCache.getNameFormat().name() : "";

		if (identifier.equals("chat_color")) {
			if (pCache.getChatCustomGradient1() != null && pCache.getChatCustomGradient2() != null)
				return pCache.getChatCustomGradient1() + pCache.getChatCustomGradient2().toString();

			if (pCache.getChatColor() != null || pCache.getChatFormat() != null)
				if (pCache.getChatColor() != null && pCache.getChatFormat() == null)
					return pCache.getChatColor().toString();
				else if (pCache.getChatColor() != null && pCache.getChatFormat() != null)
					return pCache.getChatColor().toString() + pCache.getChatFormat().toString();

			return "";
		}
		if (identifier.equals("name_color")) {
			if (pCache.getCustomGradient1() != null && pCache.getCustomGradient2() != null)
				return pCache.getCustomGradient1().toString() + pCache.getCustomGradient2().toString();

			if (pCache.getNameColor() != null || pCache.getNameFormat() != null) {
				if (pCache.getNameColor() != null && pCache.getNameFormat() == null)
					return pCache.getNameColor().toString();
				else if (pCache.getNameColor() != null && pCache.getNameFormat() != null)
					return pCache.getNameColor().toString() + pCache.getNameFormat().toString();
			}

			return "";
		}

		if (identifier.equals("chat_color_name")) {
			if (pCache.isChatRainbowColors())
				return "Rainbow";

			if (pCache.getChatCustomGradient1() != null && pCache.getChatCustomGradient2() != null) {
				if (pCache.getChatFormat() == null)
					return ChatUtil.generateGradient("this", pCache.getChatCustomGradient1(), pCache.getChatCustomGradient2());
				else
					return ChatUtil.generateGradient(UltraColorUtil.chatFormatToString(pCache.getChatFormat()) + "this",
							pCache.getChatCustomGradient1(), pCache.getChatCustomGradient2());
			}

			if (pCache.getChatColor() == null && pCache.getChatCustomGradient1() == null && pCache.getChatCustomGradient2() == null
					&& pCache.getChatFormat() == null && !pCache.isChatRainbowColors())
				return "None";

			if (pCache.getChatColor() != null) {
				if (pCache.getChatColor().isHex()) {
					if (pCache.getChatFormat() != null)
						return pCache.getChatColor() + UltraColorUtil.chatFormatToString(pCache.getChatFormat()) + "this";
					return pCache.getChatColor() + "this";
				}

				if (pCache.getChatFormat() != null)
					return UltraColorUtil.chatFormatToString(pCache.getChatFormat()) + pCache.getChatColor().getName();
				return pCache.getChatColor().getName();
			} else {
				if (pCache.getChatFormat() != null)
					return pCache.getChatFormat().getName();
			}

			return "";
		}
		if (identifier.equals("name_color_name")) {
			if (pCache.isNameRainbowColors())
				return "Rainbow";

			if (pCache.getCustomGradient1() != null && pCache.getCustomGradient2() != null) {
				if (pCache.getNameFormat() == null)
					return ChatUtil.generateGradient("this", pCache.getCustomGradient1(), pCache.getCustomGradient2());
				else
					return ChatUtil.generateGradient(UltraColorUtil.nameFormatToString(pCache.getNameFormat()) + "this",
							pCache.getCustomGradient1(), pCache.getCustomGradient2());
			}
			if (pCache.getNameColor() == null && pCache.getCustomGradient1() == null && pCache.getCustomGradient2() == null
					&& pCache.getNameFormat() == null && !pCache.isNameRainbowColors())
				return "None";

			if (pCache.getNameColor() != null) {
				if (pCache.getNameColor().isHex()) {
					if (pCache.getNameFormat() != null)
						return pCache.getNameColor() + UltraColorUtil.nameFormatToString(pCache.getNameFormat()) + "this";
					return pCache.getNameColor() + "this";
				}

				if (pCache.getNameFormat() != null)
					return UltraColorUtil.nameFormatToString(pCache.getNameFormat()) + pCache.getNameColor().getName();
				return pCache.getNameColor().getName();
			} else {
				if (pCache.getNameFormat() != null)
					return pCache.getNameFormat().name();
			}

			return "";
		}

		if (identifier.equals("nickname")) {
			if (!pCache.getNickName().equalsIgnoreCase("none"))
				return pCache.getNickName();
			else
				return "None";
		}

		if (identifier.equals("colored_nickname")) {
			if (!pCache.getColoredNickName().equalsIgnoreCase("none"))
				return pCache.getColoredNickName();
			else {
				if (pCache.isNameRainbowColors()) {
					return UltraColorUtil.convertStringToRainbow(player.getName(), pCache.getNameFormat() != null,
							format);
				}

				if (pCache.getCustomGradient1() != null && pCache.getCustomGradient2() != null)
					return ChatUtil.generateGradient(player.getName(), pCache.getCustomGradient1(), pCache.getCustomGradient2());

				if (pCache.getNameColor() != null || pCache.getNameFormat() != null) {
					if (pCache.getNameColor() != null && pCache.getNameFormat() == null)
						return pCache.getNameColor().toString() + player.getName();
					else if (pCache.getNameColor() != null && pCache.getNameFormat() != null)
						return pCache.getNameColor().toString() + pCache.getNameFormat().toString() + player.getName();
				}

				return player.getName();
			}
		}

		return null;
	}
}
