package me.ultimategamer200.ultracolor.subcommands;

import me.ultimategamer200.ultracolor.PlayerCache;
import me.ultimategamer200.ultracolor.settings.Localization;
import me.ultimategamer200.ultracolor.util.UltraColorPermissions;
import me.ultimategamer200.ultracolor.util.UltraColorUtil;
import org.bukkit.entity.Player;
import org.mineacademy.fo.command.SimpleSubCommand;
import org.mineacademy.fo.remain.CompChatColor;

import java.util.Arrays;
import java.util.List;

public class SetNameColorCommand extends SimpleSubCommand {
	protected SetNameColorCommand() {
		super("setnamecolor|snc");
		setUsage("<color> [format]");
		setMinArguments(1);
		setDescription("Set your name color and format!");
		setPermission(UltraColorPermissions.Command.SET_NAME_COLOR);
	}

	@Override
	protected void onCommand() {
		checkConsole();
		final String color = args[0];
		final Player player = getPlayer();

		if (!UltraColorUtil.isNameColorEnabled(color)) {
			tellError("The name color you selected is disabled or doesn't exist! Notify an admin if this is an error.");
			return;
		}

		if (!UltraColorUtil.isColorSelectedAbleToBeSet("name", color, player)) {
			tellError(Localization.Other.UNABLE_TO_SELECT_FORMAT_MESSAGE);
			return;
		}

		if (args.length == 1) // if the command entered is just /ucolor setnamecolor <color>
			applyNameColor(color, player);
		if (args.length == 2) { // entered /ucolor setnamecolor <color> <format>
			final String format = args[1];

			if (!UltraColorUtil.isNameFormatEnabled(format)) {
				tellError("The name format you selected is disabled or doesn't exist! Notify an admin if this is an error.");
				return;
			}

			if (!UltraColorUtil.isFormatSelectedAbleToBeSet("name", format, player)) {
				tellError(Localization.Other.UNABLE_TO_SELECT_FORMAT_MESSAGE);
				return;
			}

			applyNameFormat(color, format, player);
		}
	}

	@Override
	protected List<String> tabComplete() {
		final List<String> colors = Arrays.asList("black", "dark_blue", "dark_green", "dark_aqua",
				"dark_red", "dark_purple", "orange", "gray", "dark_gray", "blue", "green", "aqua",
				"red", "light_purple", "yellow", "white", "rainbow", "none", "reset");
		final List<String> formats = Arrays.asList("bold", "underline", "italic", "strikethrough", "magic");

		if (args.length == 1)
			return completeLastWord(colors);
		if (args.length == 2)
			return completeLastWord(formats);

		return null;
	}

	private void applyNameColor(final String color, final Player player) {
		final PlayerCache pCache = PlayerCache.fromPlayer(player);

		if (color.equalsIgnoreCase("black")) {
			UltraColorUtil.applyNameColor(player, CompChatColor.BLACK, null);
			tellSuccess(Localization.Name_Color_Selection_Customization_Black.BLACK_SUCCESS);
		}

		if (color.equalsIgnoreCase("dark_blue")) {
			UltraColorUtil.applyNameColor(player, CompChatColor.DARK_BLUE, null);
			tellSuccess(Localization.Name_Color_Selection_Customization_Dark_Blue.DARK_BLUE_SUCCESS);
		}

		if (color.equalsIgnoreCase("dark_green")) {
			UltraColorUtil.applyNameColor(player, CompChatColor.DARK_GREEN, null);
			tellSuccess(Localization.Name_Color_Selection_Customization_Dark_Green.DARK_GREEN_SUCCESS);
		}

		if (color.equalsIgnoreCase("dark_aqua")) {
			UltraColorUtil.applyNameColor(player, CompChatColor.DARK_AQUA, null);
			tellSuccess(Localization.Name_Color_Selection_Customization_Dark_Aqua.DARK_AQUA_SUCCESS);
		}

		if (color.equalsIgnoreCase("dark_red")) {
			UltraColorUtil.applyNameColor(player, CompChatColor.DARK_RED, null);
			tellSuccess(Localization.Name_Color_Selection_Customization_Dark_Red.DARK_RED_SUCCESS);
		}

		if (color.equalsIgnoreCase("dark_purple")) {
			UltraColorUtil.applyNameColor(player, CompChatColor.DARK_PURPLE, null);
			tellSuccess(Localization.Name_Color_Selection_Customization_Dark_Purple.DARK_PURPLE_SUCCESS);
		}

		if (color.equalsIgnoreCase("orange")) {
			UltraColorUtil.applyNameColor(player, CompChatColor.GOLD, null);
			tellSuccess(Localization.Name_Color_Selection_Customization_Dark_Purple.DARK_PURPLE_SUCCESS);
		}

		if (color.equalsIgnoreCase("gray")) {
			UltraColorUtil.applyNameColor(player, CompChatColor.GRAY, null);
			tellSuccess(Localization.Name_Color_Selection_Customization_Dark_Purple.DARK_PURPLE_SUCCESS);
		}

		if (color.equalsIgnoreCase("dark_gray")) {
			UltraColorUtil.applyNameColor(player, CompChatColor.DARK_GRAY, null);
			tellSuccess(Localization.Name_Color_Selection_Customization_Dark_Purple.DARK_PURPLE_SUCCESS);
		}

		if (color.equalsIgnoreCase("blue")) {
			UltraColorUtil.applyNameColor(player, CompChatColor.BLUE, null);
			tellSuccess(Localization.Name_Color_Selection_Customization_Blue.BLUE_SUCCESS);
		}

		if (color.equalsIgnoreCase("green")) {
			UltraColorUtil.applyNameColor(player, CompChatColor.GREEN, null);
			tellSuccess(Localization.Name_Color_Selection_Customization_Green.GREEN_SUCCESS);
		}

		if (color.equalsIgnoreCase("aqua")) {
			UltraColorUtil.applyNameColor(player, CompChatColor.AQUA, null);
			tellSuccess(Localization.Name_Color_Selection_Customization_Aqua.AQUA_SUCCESS);
		}

		if (color.equalsIgnoreCase("red")) {
			UltraColorUtil.applyNameColor(player, CompChatColor.RED, null);
			tellSuccess(Localization.Name_Color_Selection_Customization_Red.RED_SUCCESS);
		}

		if (color.equalsIgnoreCase("light_purple")) {
			UltraColorUtil.applyNameColor(player, CompChatColor.LIGHT_PURPLE, null);
			tellSuccess(Localization.Name_Color_Selection_Customization_Light_Purple.LIGHT_PURPLE_SUCCESS);
		}

		if (color.equalsIgnoreCase("yellow")) {
			UltraColorUtil.applyNameColor(player, CompChatColor.YELLOW, null);
			tellSuccess(Localization.Name_Color_Selection_Customization_Yellow.YELLOW_SUCCESS);
		}

		if (color.equalsIgnoreCase("white")) {
			UltraColorUtil.applyNameColor(player, CompChatColor.WHITE, null);
			tellSuccess(Localization.Name_Color_Selection_Customization_White.WHITE_SUCCESS);
		}

		if (color.equalsIgnoreCase("rainbow")) {
			pCache.setNameRainbowColors(true);
			pCache.setCustomGradient1(null);
			pCache.setCustomGradient2(null);
			UltraColorUtil.convertNameToRainbow(player, false, "");
			tellSuccess(Localization.Name_Color_Selection_Customization_Rainbow.RAINBOW_SUCCESS.replace("%rainbow_colors%",
					UltraColorUtil.convertStringToRainbow("Rainbow colors", false, "")));
		}

		if (color.equalsIgnoreCase("reset")) {
			pCache.setNameColor(null);
			pCache.setNameRainbowColors(false);
			tellSuccess(Localization.Name_Reset_Button.RESET_SUCCESS);
		}
	}

	private void applyNameFormat(final String color, final String format, final Player player) {
		final PlayerCache pCache = PlayerCache.fromPlayer(player);

		if (color.equalsIgnoreCase("black")) {
			UltraColorUtil.applyNameColor(player, CompChatColor.BLACK, UltraColorUtil.getNameFormatToChatColor(format));
			tellSuccess(Localization.Name_Color_Selection_Customization_Black.BLACK_SUCCESS);
		}

		if (color.equalsIgnoreCase("dark_blue")) {
			UltraColorUtil.applyNameColor(player, CompChatColor.DARK_BLUE, UltraColorUtil.getNameFormatToChatColor(format));
			tellSuccess(Localization.Name_Color_Selection_Customization_Dark_Blue.DARK_BLUE_SUCCESS);
		}

		if (color.equalsIgnoreCase("dark_green")) {
			UltraColorUtil.applyNameColor(player, CompChatColor.DARK_GREEN, UltraColorUtil.getNameFormatToChatColor(format));
			tellSuccess(Localization.Name_Color_Selection_Customization_Dark_Green.DARK_GREEN_SUCCESS);
		}

		if (color.equalsIgnoreCase("dark_aqua")) {
			UltraColorUtil.applyNameColor(player, CompChatColor.DARK_AQUA, UltraColorUtil.getNameFormatToChatColor(format));
			tellSuccess(Localization.Name_Color_Selection_Customization_Dark_Aqua.DARK_AQUA_SUCCESS);
		}

		if (color.equalsIgnoreCase("dark_red")) {
			UltraColorUtil.applyNameColor(player, CompChatColor.DARK_RED, UltraColorUtil.getNameFormatToChatColor(format));
			tellSuccess(Localization.Name_Color_Selection_Customization_Dark_Red.DARK_RED_SUCCESS);
		}

		if (color.equalsIgnoreCase("dark_purple")) {
			UltraColorUtil.applyNameColor(player, CompChatColor.DARK_PURPLE, UltraColorUtil.getNameFormatToChatColor(format));
			tellSuccess(Localization.Name_Color_Selection_Customization_Dark_Purple.DARK_PURPLE_SUCCESS);
		}

		if (color.equalsIgnoreCase("orange")) {
			UltraColorUtil.applyNameColor(player, CompChatColor.GOLD, UltraColorUtil.getNameFormatToChatColor(format));
			tellSuccess(Localization.Name_Color_Selection_Customization_Orange.ORANGE_SUCCESS);
		}

		if (color.equalsIgnoreCase("gray")) {
			UltraColorUtil.applyNameColor(player, CompChatColor.GRAY, UltraColorUtil.getNameFormatToChatColor(format));
			tellSuccess(Localization.Name_Color_Selection_Customization_Gray.GRAY_SUCCESS);
		}

		if (color.equalsIgnoreCase("dark_gray")) {
			UltraColorUtil.applyNameColor(player, CompChatColor.DARK_GRAY, UltraColorUtil.getNameFormatToChatColor(format));
			tellSuccess(Localization.Name_Color_Selection_Customization_Dark_Gray.DARK_GRAY_SUCCESS);
		}

		if (color.equalsIgnoreCase("blue")) {
			UltraColorUtil.applyNameColor(player, CompChatColor.BLUE, UltraColorUtil.getNameFormatToChatColor(format));
			tellSuccess(Localization.Name_Color_Selection_Customization_Blue.BLUE_SUCCESS);
		}

		if (color.equalsIgnoreCase("green")) {
			UltraColorUtil.applyNameColor(player, CompChatColor.GREEN, UltraColorUtil.getNameFormatToChatColor(format));
			tellSuccess(Localization.Name_Color_Selection_Customization_Green.GREEN_SUCCESS);
		}

		if (color.equalsIgnoreCase("aqua")) {
			UltraColorUtil.applyNameColor(player, CompChatColor.AQUA, UltraColorUtil.getNameFormatToChatColor(format));
			tellSuccess(Localization.Name_Color_Selection_Customization_Aqua.AQUA_SUCCESS);
		}

		if (color.equalsIgnoreCase("red")) {
			UltraColorUtil.applyNameColor(player, CompChatColor.RED, UltraColorUtil.getNameFormatToChatColor(format));
			tellSuccess(Localization.Name_Color_Selection_Customization_Red.RED_SUCCESS);
		}

		if (color.equalsIgnoreCase("light_purple")) {
			UltraColorUtil.applyNameColor(player, CompChatColor.LIGHT_PURPLE, UltraColorUtil.getNameFormatToChatColor(format));
			tellSuccess(Localization.Name_Color_Selection_Customization_Light_Purple.LIGHT_PURPLE_SUCCESS);
		}

		if (color.equalsIgnoreCase("yellow")) {
			UltraColorUtil.applyNameColor(player, CompChatColor.YELLOW, UltraColorUtil.getNameFormatToChatColor(format));
			tellSuccess(Localization.Name_Color_Selection_Customization_Yellow.YELLOW_SUCCESS);
		}

		if (color.equalsIgnoreCase("white")) {
			UltraColorUtil.applyNameColor(player, CompChatColor.WHITE, UltraColorUtil.getNameFormatToChatColor(format));
			tellSuccess(Localization.Name_Color_Selection_Customization_White.WHITE_SUCCESS);
		}

		if (color.equalsIgnoreCase("none")) {
			if (format.equalsIgnoreCase("bold"))
				tellSuccess(Localization.Name_Color_Selection_Customization_Bold.BOLD_SUCCESS);
			else if (format.equalsIgnoreCase("italic"))
				tellSuccess(Localization.Name_Color_Selection_Customization_Italic.ITALIC_SUCCESS);
			else if (format.equalsIgnoreCase("underline"))
				tellSuccess(Localization.Name_Color_Selection_Customization_Underline.UNDERLINE_SUCCESS);
			else if (format.equalsIgnoreCase("strikethrough"))
				tellSuccess(Localization.Name_Color_Selection_Customization_Strikethrough.STRIKETHROUGH_SUCCESS);
			else if (format.equalsIgnoreCase("magic"))
				tellSuccess(Localization.Name_Color_Selection_Customization_Underline.UNDERLINE_SUCCESS);
		}

		if (color.equalsIgnoreCase("rainbow")) {
			UltraColorUtil.convertNameToRainbow(player, true, format);
			tellSuccess(Localization.Name_Color_Selection_Customization_Rainbow.RAINBOW_SUCCESS.replace(
					"%rainbow_colors%", UltraColorUtil.convertStringToRainbow("Rainbow colors",
							true, format)));
		}

		if (color.equalsIgnoreCase("reset")) {
			pCache.setNameColor(null);
			pCache.setNameFormat(null);
			pCache.setNameRainbowColors(false);
			tellSuccess(Localization.Name_Reset_Button.RESET_SUCCESS);
		}
	}
}
