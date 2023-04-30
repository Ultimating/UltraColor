package me.ultimategamer200.ultracolor.subcommands;

import me.ultimategamer200.ultracolor.PlayerCache;
import me.ultimategamer200.ultracolor.settings.Localization;
import me.ultimategamer200.ultracolor.util.ColorId;
import me.ultimategamer200.ultracolor.util.UltraColorPermissions;
import me.ultimategamer200.ultracolor.util.UltraColorUtil;
import org.bukkit.OfflinePlayer;
import org.mineacademy.fo.ChatUtil;
import org.mineacademy.fo.command.SimpleSubCommand;
import org.mineacademy.fo.model.Replacer;

import java.util.List;

public class SetChatColorCommand extends SimpleSubCommand {
	protected SetChatColorCommand() {
		super("setchatcolor|scc");
		setUsage("<color> [format]");
		setMinArguments(1);
		setDescription("Set your chat color and format!");
		setPermission(UltraColorPermissions.Command.SET_CHAT_COLOR);
	}

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
		if (args.length == 1) return completeLastWord(ColorId.getColorIds());
		if (args.length == 2) return completeLastWord(ColorId.FormatId.getFormatIds());
		return super.tabComplete();
	}

	private void applyChatColor(final String color, final OfflinePlayer player) {
		final PlayerCache pCache = PlayerCache.fromOfflinePlayer(player);
		ColorId foundColorId = ColorId.RAINBOW;
		pCache.setChatFormat(null);

		if (!color.equalsIgnoreCase(ColorId.RAINBOW.getId()) && !color.equalsIgnoreCase("reset")) {
			for (final ColorId colorId : ColorId.values()) {
				if (color.equalsIgnoreCase(colorId.getId())) {
					UltraColorUtil.applyChatColor(player, colorId.getColor());
					foundColorId = colorId;
					break;
				}
			}

			tellSuccess(Replacer.replaceArray(Localization.Main_GUI_Customization_Chat_Color_Selection.SUCCESS_MESSAGE,
					"color", ColorId.bountifyCompChatColor(foundColorId.getColor()), "type", "color"));
		} else if (color.equalsIgnoreCase(ColorId.RAINBOW.getId())) {
			pCache.setChatRainbowColors(true);
			pCache.clearGradients("chat");

			tellSuccess(Replacer.replaceArray(Localization.Main_GUI_Customization_Chat_Color_Selection.SUCCESS_MESSAGE,
					"color", ChatUtil.capitalizeFirst(color), "type", "color"));
		} else {
			pCache.setChatColor(null);
			pCache.setChatRainbowColors(false);
			tellSuccess(Localization.Chat_Reset_Button.RESET_SUCCESS);
		}
	}

	private void applyChatFormat(final String color, final String format, final OfflinePlayer player) {
		final PlayerCache pCache = PlayerCache.fromOfflinePlayer(player);
		ColorId foundColorId = ColorId.RAINBOW;
		pCache.setChatFormat(UltraColorUtil.getFormatToCompChatColor(format));

		if (!color.equalsIgnoreCase("none") && !color.equalsIgnoreCase(ColorId.RAINBOW.getId())
				&& !color.equalsIgnoreCase("reset")) {
			for (final ColorId colorId : ColorId.values()) {
				if (color.equalsIgnoreCase(colorId.getId())) {
					foundColorId = colorId;
					UltraColorUtil.applyChatFormat(player, pCache.getChatFormat());
					break;
				}
			}

			tellSuccess(Replacer.replaceArray(Localization.Main_GUI_Customization_Chat_Color_Selection.SUCCESS_MESSAGE,
					"color", UltraColorUtil.nameAndChatColorToString(foundColorId.getColor())
							+ ColorId.bountifyChatColor(UltraColorUtil.getNameFormatToChatColor(format)), "type", "format"));
		} else if (color.equalsIgnoreCase("none")) {
			tellSuccess("Format set to none!");
		} else if (color.equalsIgnoreCase(ColorId.RAINBOW.getId())) {
			pCache.setChatRainbowColors(true);
			tellSuccess(Replacer.replaceArray(Localization.Main_GUI_Customization_Chat_Color_Selection.SUCCESS_MESSAGE,
					"color", UltraColorUtil.convertStringToRainbow(ColorId.bountifyChatColor(UltraColorUtil.getNameFormatToChatColor(format)),
							true, format), "type", "format"));
		} else {
			pCache.setChatColor(null);
			pCache.setChatFormat(null);
			pCache.setChatRainbowColors(false);
			tellSuccess(Localization.Chat_Reset_Button.RESET_SUCCESS);
		}
	}
}
