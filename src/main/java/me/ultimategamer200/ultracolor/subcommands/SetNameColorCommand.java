package me.ultimategamer200.ultracolor.subcommands;

import me.ultimategamer200.ultracolor.PlayerCache;
import me.ultimategamer200.ultracolor.settings.Localization;
import me.ultimategamer200.ultracolor.util.ColorId;
import me.ultimategamer200.ultracolor.util.UltraColorPermissions;
import me.ultimategamer200.ultracolor.util.UltraColorUtil;
import org.bukkit.entity.Player;
import org.mineacademy.fo.ChatUtil;
import org.mineacademy.fo.command.SimpleSubCommand;
import org.mineacademy.fo.model.Replacer;

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
		if (args.length == 1) return completeLastWord(ColorId.getColorIds());
		if (args.length == 2) return completeLastWord(ColorId.FormatId.getFormatIds());

		return NO_COMPLETE;
	}

	private void applyNameColor(final String color, final Player player) {
		final PlayerCache pCache = PlayerCache.fromPlayer(player);
		ColorId foundColorId = ColorId.RAINBOW;

		if (!color.equalsIgnoreCase(ColorId.RAINBOW.getId()) && !color.equalsIgnoreCase("reset")) {
			for (final ColorId colorId : ColorId.values()) {
				if (color.equalsIgnoreCase(colorId.getId())) {
					UltraColorUtil.applyNameColor(player, colorId.getColor(), null);
					foundColorId = colorId;
					break;
				}
			}

			tellSuccess(Replacer.replaceArray(Localization.Main_GUI_Customization_Name_Color_Selection.SUCCESS_MESSAGE,
					"color", ColorId.bountifyCompChatColor(foundColorId.getColor()), "type", "color"));
		} else if (color.equalsIgnoreCase(ColorId.RAINBOW.getId())) {
			UltraColorUtil.convertNameToRainbow(player, false, "");
			tellSuccess(Replacer.replaceArray(Localization.Main_GUI_Customization_Name_Color_Selection.SUCCESS_MESSAGE,
					"color", ChatUtil.capitalizeFirst(color), "type", "color"));
		} else {
			pCache.setNameColor(null);
			pCache.setNameRainbowColors(false);
			tellSuccess(Localization.Name_Reset_Button.RESET_SUCCESS);
		}
	}

	private void applyNameFormat(final String color, final String format, final Player player) {
		final PlayerCache pCache = PlayerCache.fromPlayer(player);
		ColorId foundColor = ColorId.RAINBOW;

		if (!color.equalsIgnoreCase("none") && !color.equalsIgnoreCase(ColorId.RAINBOW.getId())
				&& !color.equalsIgnoreCase("reset")) {
			for (final ColorId colorId : ColorId.values()) {
				if (color.equalsIgnoreCase(colorId.getId())) {
					foundColor = colorId;
					UltraColorUtil.applyNameColor(player, colorId.getColor(), UltraColorUtil.getNameFormatToChatColor(format));
					break;
				}
			}

			tellSuccess(Replacer.replaceArray(Localization.Main_GUI_Customization_Name_Color_Selection.SUCCESS_MESSAGE,
					"color", UltraColorUtil.nameAndChatColorToString(foundColor.getColor())
							+ ColorId.bountifyChatColor(UltraColorUtil.getNameFormatToChatColor(format)), "type", "format"));
		} else if (color.equalsIgnoreCase("none")) {
			tellSuccess(Replacer.replaceArray(Localization.Main_GUI_Customization_Name_Color_Selection.SUCCESS_MESSAGE,
					"color", ChatUtil.capitalizeFirst(color), "type", "format"));
		} else if (color.equalsIgnoreCase(ColorId.RAINBOW.getId())) {
			UltraColorUtil.convertNameToRainbow(player, true, format);
			tellSuccess(Replacer.replaceArray(Localization.Main_GUI_Customization_Name_Color_Selection.SUCCESS_MESSAGE,
					"color", UltraColorUtil.convertStringToRainbow(ColorId.bountifyChatColor(UltraColorUtil.getNameFormatToChatColor(format)),
							true, format), "type", "format"));
		} else {
			pCache.setNameColor(null);
			pCache.setNameFormat(null);
			pCache.setNameRainbowColors(false);
			tellSuccess(Localization.Name_Reset_Button.RESET_SUCCESS);
		}
	}
}
