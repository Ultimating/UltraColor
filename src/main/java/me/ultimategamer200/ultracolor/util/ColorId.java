package me.ultimategamer200.ultracolor.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.ChatColor;
import org.mineacademy.fo.ChatUtil;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.remain.CompChatColor;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Getter
public enum ColorId {
	BLACK("black", CompChatColor.BLACK),
	DARK_BLUE("dark_blue", CompChatColor.DARK_BLUE),
	DARK_GREEN("dark_green", CompChatColor.DARK_GREEN),
	DARK_AQUA("dark_aqua", CompChatColor.DARK_AQUA),
	DARK_RED("dark_red", CompChatColor.DARK_RED),
	DARK_PURPLE("dark_purple", CompChatColor.DARK_PURPLE),
	ORANGE("orange", CompChatColor.GOLD),
	GRAY("gray", CompChatColor.GRAY),
	DARK_GRAY("dark_gray", CompChatColor.DARK_GRAY),
	BLUE("blue", CompChatColor.BLUE),
	GREEN("green", CompChatColor.GREEN),
	AQUA("aqua", CompChatColor.AQUA),
	RED("red", CompChatColor.RED),
	LIGHT_PURPLE("light_purple", CompChatColor.LIGHT_PURPLE),
	YELLOW("yellow", CompChatColor.YELLOW),
	WHITE("white", CompChatColor.WHITE),
	RAINBOW("rainbow", null);

	private final String id;
	private final CompChatColor color;

	public static String bountifyCompChatColor(final CompChatColor color) {
		if (color.getName().contains("_"))
			return ChatUtil.capitalizeFully(color.getName().replace("_", " "));

		return ChatUtil.capitalizeFirst(color.getName());
	}

	public static String bountifyChatColor(final ChatColor color) {
		if (color.name().contains("_"))
			return ChatUtil.capitalizeFully(color.name().toLowerCase().replace("_", " "));

		return ChatUtil.capitalizeFirst(color.name().toLowerCase());
	}

	public static List<String> getColorIds() {
		final List<String> colorIds = Common.convert(ColorId.values(), ColorId::getId);
		colorIds.addAll(Arrays.asList("none", "reset"));

		return colorIds;
	}

	@RequiredArgsConstructor
	@Getter
	public enum FormatId {
		BOLD("bold"),
		ITALIC("italic"),
		UNDERLINE("underline"),
		STRIKETHROUGH("strikethrough"),
		MAGIC("magic");

		private final String id;

		public static List<String> getFormatIds() {
			return Common.convert(FormatId.values(), FormatId::getId);
		}
	}
}
