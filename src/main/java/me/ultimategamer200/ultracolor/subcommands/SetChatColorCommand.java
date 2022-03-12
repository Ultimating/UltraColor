package me.ultimategamer200.ultracolor.subcommands;

import me.ultimategamer200.ultracolor.PlayerCache;
import me.ultimategamer200.ultracolor.settings.Localization;
import me.ultimategamer200.ultracolor.settings.Settings;
import me.ultimategamer200.ultracolor.util.UltraColorPermissions;
import me.ultimategamer200.ultracolor.util.UltraColorUtil;
import org.bukkit.OfflinePlayer;
import org.mineacademy.fo.command.SimpleSubCommand;
import org.mineacademy.fo.remain.CompChatColor;

import java.util.Arrays;
import java.util.List;

public class SetChatColorCommand extends SimpleSubCommand {
	protected SetChatColorCommand() {
		super("setchatcolor|scc");
		setUsage("<color> [format]");
		setMinArguments(1);
		setDescription("Set your chat color and format!");
		setPermission(UltraColorPermissions.Command.SET_CHAT_COLOR);
	}

	final List<String> colors = Arrays.asList("black", "dark_blue", "dark_green", "dark_aqua",
			"dark_red", "dark_purple", "orange", "gray", "dark_gray", "blue", "green", "aqua",
			"red", "light_purple", "yellow", "white", "rainbow", "none", "reset");
	final List<String> formats = Arrays.asList("bold", "underline", "italic", "strikethrough", "magic");

	@Override
	protected void onCommand() {
		checkConsole();
		final String color = args[0];

		if (!UltraColorUtil.isChatColorEnabled(color)) {
			tellError("The chat color you selected is disabled or doesn't exist! Notify an admin if this is an error.");
			return;
		}

		if (!UltraColorUtil.isColorSelectedAbleToBeSet("chat", color, getPlayer())) {
			tellError(Localization.Other.UNABLE_TO_SELECT_FORMAT_MESSAGE);
			return;
		}

		if (args.length == 1) // if the command entered is just /ucolor setchatcolor <color>
			applyChatColor(color, getPlayer());
		if (args.length == 2) { // entered /ucolor setchatcolor <color> <format>
			final String format = args[1];

			if (!UltraColorUtil.isChatFormatEnabled(format)) {
				tellError("The chat format you selected is disabled or doesn't exist! Notify an admin if this is an error.");
				return;
			}

			if (!UltraColorUtil.isFormatSelectedAbleToBeSet("chat", format, getPlayer())) {
				tellError(Localization.Other.UNABLE_TO_SELECT_FORMAT_MESSAGE);
				return;
			}

			applyChatFormat(color, format, getPlayer());
		}
	}

	@Override
	protected List<String> tabComplete() {
		if (args.length == 1)
			return completeLastWord(colors);
		if (args.length == 2)
			return completeLastWord(formats);
		return super.tabComplete();
	}

	private void applyChatColor(final String color, final OfflinePlayer player) {
		final PlayerCache pCache = PlayerCache.fromOfflinePlayer(player);
		pCache.setChatFormat(null);

		if (color.equalsIgnoreCase("black")) {
			UltraColorUtil.applyChatColor(player, CompChatColor.BLACK);
			tellSuccess(Localization.Chat_Color_Selection_Customization_Black.BLACK_SUCCESS);
		}

		if (color.equalsIgnoreCase("dark_blue")) {
			UltraColorUtil.applyChatColor(player, CompChatColor.DARK_BLUE);
			tellSuccess(Localization.Chat_Color_Selection_Customization_Dark_Blue.DARK_BLUE_SUCCESS);
		}

		if (color.equalsIgnoreCase("dark_green")) {
			UltraColorUtil.applyChatColor(player, CompChatColor.DARK_GREEN);
			tellSuccess(Localization.Chat_Color_Selection_Customization_Dark_Green.DARK_GREEN_SUCCESS);
		}

		if (color.equalsIgnoreCase("dark_aqua")) {
			UltraColorUtil.applyChatColor(player, CompChatColor.DARK_AQUA);
			tellSuccess(Localization.Chat_Color_Selection_Customization_Dark_Aqua.DARK_AQUA_SUCCESS);
		}

		if (color.equalsIgnoreCase("dark_red")) {
			UltraColorUtil.applyChatColor(player, CompChatColor.DARK_RED);
			tellSuccess(Localization.Chat_Color_Selection_Customization_Dark_Red.DARK_RED_SUCCESS);
		}

		if (color.equalsIgnoreCase("dark_purple")) {
			UltraColorUtil.applyChatColor(player, CompChatColor.DARK_PURPLE);
			tellSuccess(Localization.Chat_Color_Selection_Customization_Dark_Purple.DARK_PURPLE_SUCCESS);
		}

		if (color.equalsIgnoreCase("orange")) {
			UltraColorUtil.applyChatColor(player, CompChatColor.GOLD);
			tellSuccess(Localization.Chat_Color_Selection_Customization_Orange.ORANGE_SUCCESS);
		}

		if (color.equalsIgnoreCase("gray")) {
			UltraColorUtil.applyChatColor(player, CompChatColor.GRAY);
			tellSuccess(Localization.Chat_Color_Selection_Customization_Gray.GRAY_SUCCESS);
		}

		if (color.equalsIgnoreCase("dark_gray")) {
			UltraColorUtil.applyChatColor(player, CompChatColor.DARK_GRAY);
			tellSuccess(Localization.Chat_Color_Selection_Customization_Dark_Gray.DARK_GRAY_SUCCESS);
		}

		if (color.equalsIgnoreCase("blue")) {
			UltraColorUtil.applyChatColor(player, CompChatColor.BLUE);
			tellSuccess(Localization.Chat_Color_Selection_Customization_Blue.BLUE_SUCCESS);
		}

		if (color.equalsIgnoreCase("green")) {
			UltraColorUtil.applyChatColor(player, CompChatColor.GREEN);
			tellSuccess(Localization.Chat_Color_Selection_Customization_Green.GREEN_SUCCESS);
		}

		if (color.equalsIgnoreCase("aqua")) {
			UltraColorUtil.applyChatColor(player, CompChatColor.AQUA);
			tellSuccess(Localization.Chat_Color_Selection_Customization_Aqua.AQUA_SUCCESS);
		}

		if (color.equalsIgnoreCase("red")) {
			UltraColorUtil.applyChatColor(player, CompChatColor.RED);
			tellSuccess(Localization.Chat_Color_Selection_Customization_Red.RED_SUCCESS);
		}

		if (color.equalsIgnoreCase("light_purple")) {
			UltraColorUtil.applyChatColor(player, CompChatColor.LIGHT_PURPLE);
			tellSuccess(Localization.Chat_Color_Selection_Customization_Light_Purple.LIGHT_PURPLE_SUCCESS);
		}

		if (color.equalsIgnoreCase("yellow")) {
			UltraColorUtil.applyChatColor(player, CompChatColor.YELLOW);
			tellSuccess(Localization.Chat_Color_Selection_Customization_Yellow.YELLOW_SUCCESS);
		}

		if (color.equalsIgnoreCase("white")) {
			UltraColorUtil.applyChatColor(player, CompChatColor.WHITE);
			tellSuccess(Localization.Chat_Color_Selection_Customization_White.WHITE_SUCCESS);
		}

		if (color.equalsIgnoreCase("rainbow")) {
			pCache.setChatRainbowColors(true);
			pCache.setChatCustomGradient1(null);
			pCache.setChatCustomGradient2(null);

			tellSuccess(Localization.Chat_Color_Selection_Customization_Rainbow.RAINBOW_SUCCESS.replace("%rainbow_colors%",
					UltraColorUtil.convertStringToRainbow("Rainbow colors", false, "")));
		}

		if (color.equalsIgnoreCase("reset")) {
			pCache.setChatColor(null);
			pCache.setChatRainbowColors(false);
			tellSuccess(Localization.Chat_Reset_Button.RESET_SUCCESS);
		}
	}

	private void applyChatFormat(final String color, final String format, final OfflinePlayer player) {
		final PlayerCache pCache = PlayerCache.fromOfflinePlayer(player);
		pCache.setChatFormat(UltraColorUtil.getFormatToCompChatColor(format));

		if (color.equalsIgnoreCase("black")) {
			UltraColorUtil.applyChatColor(player, CompChatColor.BLACK);
			tellSuccess(Localization.Chat_Color_Selection_Customization_Black.BLACK_SUCCESS);
		}

		if (color.equalsIgnoreCase("dark_blue")) {
			UltraColorUtil.applyChatColor(player, CompChatColor.DARK_BLUE);
			tellSuccess(Localization.Chat_Color_Selection_Customization_Dark_Blue.DARK_BLUE_SUCCESS);
		}

		if (color.equalsIgnoreCase("dark_green") && Settings.Color_Settings_Chat_Colors.DARK_GREEN_COLOR_ENABLED) {
			UltraColorUtil.applyChatColor(player, CompChatColor.DARK_GREEN);
			tellSuccess(Localization.Chat_Color_Selection_Customization_Dark_Green.DARK_GREEN_SUCCESS);
		}

		if (color.equalsIgnoreCase("dark_aqua")) {
			UltraColorUtil.applyChatColor(player, CompChatColor.DARK_AQUA);
			tellSuccess(Localization.Chat_Color_Selection_Customization_Dark_Aqua.DARK_AQUA_SUCCESS);
		}

		if (color.equalsIgnoreCase("dark_red")) {
			UltraColorUtil.applyChatColor(player, CompChatColor.DARK_RED);
			tellSuccess(Localization.Chat_Color_Selection_Customization_Dark_Red.DARK_RED_SUCCESS);
		}

		if (color.equalsIgnoreCase("dark_purple")) {
			UltraColorUtil.applyChatColor(player, CompChatColor.DARK_PURPLE);
			tellSuccess(Localization.Chat_Color_Selection_Customization_Dark_Purple.DARK_PURPLE_SUCCESS);
		}

		if (color.equalsIgnoreCase("orange")) {
			UltraColorUtil.applyChatColor(player, CompChatColor.GOLD);
			tellSuccess(Localization.Chat_Color_Selection_Customization_Orange.ORANGE_SUCCESS);
		}

		if (color.equalsIgnoreCase("gray")) {
			UltraColorUtil.applyChatColor(player, CompChatColor.GRAY);
			tellSuccess(Localization.Chat_Color_Selection_Customization_Gray.GRAY_SUCCESS);
		}

		if (color.equalsIgnoreCase("dark_gray")) {
			UltraColorUtil.applyChatColor(player, CompChatColor.DARK_GRAY);
			tellSuccess(Localization.Chat_Color_Selection_Customization_Dark_Gray.DARK_GRAY_SUCCESS);
		}

		if (color.equalsIgnoreCase("blue")) {
			UltraColorUtil.applyChatColor(player, CompChatColor.BLUE);
			tellSuccess(Localization.Chat_Color_Selection_Customization_Blue.BLUE_SUCCESS);
		}

		if (color.equalsIgnoreCase("green")) {
			UltraColorUtil.applyChatColor(player, CompChatColor.GREEN);
			tellSuccess(Localization.Chat_Color_Selection_Customization_Green.GREEN_SUCCESS);
		}

		if (color.equalsIgnoreCase("aqua")) {
			UltraColorUtil.applyChatColor(player, CompChatColor.AQUA);
			tellSuccess(Localization.Chat_Color_Selection_Customization_Aqua.AQUA_SUCCESS);
		}

		if (color.equalsIgnoreCase("red")) {
			UltraColorUtil.applyChatColor(player, CompChatColor.RED);
			tellSuccess(Localization.Chat_Color_Selection_Customization_Red.RED_SUCCESS);
		}

		if (color.equalsIgnoreCase("light_purple")) {
			UltraColorUtil.applyChatColor(player, CompChatColor.LIGHT_PURPLE);
			tellSuccess(Localization.Chat_Color_Selection_Customization_Light_Purple.LIGHT_PURPLE_SUCCESS);
		}

		if (color.equalsIgnoreCase("yellow")) {
			UltraColorUtil.applyChatColor(player, CompChatColor.YELLOW);
			tellSuccess(Localization.Chat_Color_Selection_Customization_Yellow.YELLOW_SUCCESS);
		}

		if (color.equalsIgnoreCase("white")) {
			UltraColorUtil.applyChatColor(player, CompChatColor.WHITE);
			tellSuccess(Localization.Chat_Color_Selection_Customization_White.WHITE_SUCCESS);
		}

		if (color.equalsIgnoreCase("none")) {
			if (format.equalsIgnoreCase("bold")) {
				tellSuccess(Localization.Chat_Color_Selection_Customization_Bold.BOLD_SUCCESS);
			} else if (format.equalsIgnoreCase("italic")) {
				tellSuccess(Localization.Chat_Color_Selection_Customization_Italic.ITALIC_SUCCESS);
			} else if (format.equalsIgnoreCase("underline")) {
				tellSuccess(Localization.Chat_Color_Selection_Customization_Underline.UNDERLINE_SUCCESS);
			} else if (format.equalsIgnoreCase("strikethrough")) {
				tellSuccess(Localization.Chat_Color_Selection_Customization_Strikethrough.STRIKETHROUGH_SUCCESS);
			} else if (format.equalsIgnoreCase("magic"))
				tellSuccess(Localization.Chat_Color_Selection_Customization_Magic.MAGIC_SUCCESS);
		}

		if (color.equalsIgnoreCase("rainbow")) {
			pCache.setChatRainbowColors(true);
			tellSuccess(Localization.Chat_Color_Selection_Customization_Rainbow.RAINBOW_SUCCESS.replace(
					"%rainbow_colors%", UltraColorUtil.convertStringToRainbow("Rainbow colors",
							true, format)));
		}

		if (color.equalsIgnoreCase("reset")) {
			pCache.setChatColor(null);
			pCache.setChatFormat(null);
			pCache.setChatRainbowColors(false);
			tellSuccess(Localization.Chat_Reset_Button.RESET_SUCCESS);
		}
	}
}
