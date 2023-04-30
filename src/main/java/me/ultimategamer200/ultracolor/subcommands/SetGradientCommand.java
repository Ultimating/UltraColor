package me.ultimategamer200.ultracolor.subcommands;

import me.ultimategamer200.ultracolor.PlayerCache;
import me.ultimategamer200.ultracolor.gradients.PreDefinedGradient;
import me.ultimategamer200.ultracolor.gradients.PreDefinedGradientManager;
import me.ultimategamer200.ultracolor.settings.Localization;
import me.ultimategamer200.ultracolor.settings.Settings;
import me.ultimategamer200.ultracolor.util.*;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.mineacademy.fo.ChatUtil;
import org.mineacademy.fo.Messenger;
import org.mineacademy.fo.Valid;
import org.mineacademy.fo.command.SimpleSubCommand;
import org.mineacademy.fo.model.Replacer;
import org.mineacademy.fo.remain.CompChatColor;

import java.util.ArrayList;
import java.util.List;

public class SetGradientCommand extends SimpleSubCommand {
	final String INVALID_FORMAT_MESSAGE = "Invalid format.";

	protected SetGradientCommand() {
		super("setgradient|sg");
		setUsage("<chat|name> <color> [format]");
		setMinArguments(2);
		setDescription("Set your gradient color!");
		setPermission(UltraColorPermissions.Command.SET_GRADIENT);
	}

	@Override
	protected void onCommand() {
		final Player player = getPlayer();
		final PlayerCache pCache = PlayerCache.fromPlayer(player);
		final List<String> colors = PreDefinedGradientManager.getPreDefinedGradientNames();
		final String color = args[1];

		if (args[0].equalsIgnoreCase("chat") && Settings.Color_Settings.CHAT_GRADIENT_COLORS) {
			if (colors.contains(color)) {
				final PreDefinedGradient gradient = PreDefinedGradientManager.findPreDefinedGradientByName(color);
				Valid.checkNotNull(gradient, color + " Gradient does not exist!");

				if (gradient.getSettings().getType().equalsIgnoreCase("chat")
						|| gradient.getSettings().getType().equalsIgnoreCase("both")) {
					if (UltraColorUtil.areHexesValid(gradient.getSettings().getHexColors())) {
						if (player.hasPermission(gradient.getSettings().getPermission())
								|| player.hasPermission("ultracolor.chatgradient.*")) {
							pCache.setChatCustomGradientOne(CompChatColor.of(gradient.getSettings().getHexColors().get(0)));
							pCache.setChatCustomGradientTwo(CompChatColor.of(gradient.getSettings().getHexColors().get(1)));
							pCache.setChatColor(null);

							if (args.length == 3) {
								if (ColorId.FormatId.getFormatIds().contains(args[2])) {
									if (!UltraColorUtil.isFormatSelectedAbleToBeSet("chat", args[2], getPlayer())) {
										tellError(Localization.Other.UNABLE_TO_SELECT_FORMAT_MESSAGE);
										return;
									}

									pCache.setChatFormat(UltraColorUtil.getFormatToCompChatColor(args[2]));
									tellSuccess(Replacer.replaceArray(Localization.Main_GUI_Customization_Chat_Color_Selection.SUCCESS_MESSAGE,
											"color", ColorId.bountifyChatColor(UltraColorUtil.getNameFormatToChatColor(args[2])), "type", "format"));
								} else
									tellError(INVALID_FORMAT_MESSAGE);
							} else
								pCache.setChatFormat(null);

							tellSuccess(gradient.getSettings().getSuccessMessage());
						} else
							tellError(gradient.getSettings().getErrorMessage());
					} else
						tellError("Hexes for this gradient are invalid! Notify an admin!");
				}
			} else if (color.equalsIgnoreCase("custom") || color.equalsIgnoreCase("reset")) {
				if (color.equalsIgnoreCase("custom")) {
					if (player.hasPermission("ultracolor.chatgradient.custom"))
						new ChatGradientPrompt().start(player);
					else
						tellError(Localization.Gradient_Color_Selection.ERROR_MESSAGE);
				}

				if (color.equalsIgnoreCase("reset")) {
					pCache.clearGradients("chat");
					Messenger.success(player, Localization.Gradient_Color_Selection_Reset.SUCCESS_MESSAGE);
				}
			}
		} else if (!Settings.Color_Settings.CHAT_GRADIENT_COLORS && args[0].equalsIgnoreCase("chat"))
			tellError(Localization.Gradient_Color_Selection.DISABLED_MESSAGE.replace("%type%", "Chat"));

		if (args[0].equalsIgnoreCase("name") && Settings.Color_Settings.NAME_GRADIENT_COLORS) {
			String newDisplayName = player.getName();
			if (colors.contains(color)) {
				final PreDefinedGradient gradient = PreDefinedGradientManager.findPreDefinedGradientByName(color);
				Valid.checkNotNull(gradient, color + " Gradient does not exist!");

				if (gradient.getSettings().getType().equalsIgnoreCase("name")
						|| gradient.getSettings().getType().equalsIgnoreCase("both")) {
					if (UltraColorUtil.areHexesValid(gradient.getSettings().getHexColors())) {
						if (player.hasPermission(gradient.getSettings().getPermission())
								|| player.hasPermission("ultracolor.namegradient.*")) {
							if (args.length == 3) {
								if (ColorId.FormatId.getFormatIds().contains(args[2])) {
									if (!UltraColorUtil.isFormatSelectedAbleToBeSet("name", args[2], getPlayer())) {
										tellError(Localization.Other.UNABLE_TO_SELECT_FORMAT_MESSAGE);
										return;
									}

									final ChatColor format = UltraColorUtil.getNameFormatToChatColor(args[2]);
									pCache.setNameFormat(format);

									tellSuccess(Replacer.replaceArray(Localization.Main_GUI_Customization_Name_Color_Selection.SUCCESS_MESSAGE,
											"color", ColorId.bountifyChatColor(format), "type", "format"));
								} else
									tellError(INVALID_FORMAT_MESSAGE);
							} else
								pCache.setNameFormat(null);

							pCache.setCustomGradientOne(CompChatColor.of(gradient.getSettings().getHexColors().get(0)));
							pCache.setCustomGradientTwo(CompChatColor.of(gradient.getSettings().getHexColors().get(1)));
							pCache.setNameColor(null);

							if (!pCache.getNickName().equalsIgnoreCase("none")) {
								if (pCache.getNameFormat() != null)
									newDisplayName = ChatUtil.generateGradient(UltraColorUtil.nameFormatToString(pCache.getNameFormat())
											+ pCache.getNickName(), pCache.getCustomGradientOne(), pCache.getCustomGradientTwo());
								else
									newDisplayName = ChatUtil.generateGradient(pCache.getNickName(), pCache.getCustomGradientOne(), pCache.getCustomGradientTwo());

								pCache.setColoredNickName(newDisplayName);
							} else {
								if (pCache.getNameFormat() != null)
									newDisplayName = ChatUtil.generateGradient(UltraColorUtil.nameFormatToString(pCache.getNameFormat())
											+ player.getName(), pCache.getCustomGradientOne(), pCache.getCustomGradientTwo());
								else
									newDisplayName = ChatUtil.generateGradient(player.getName(), pCache.getCustomGradientOne(), pCache.getCustomGradientTwo());
							}

							player.setDisplayName(newDisplayName);
							tellSuccess(gradient.getSettings().getSuccessMessage());
						} else
							tellError(gradient.getSettings().getErrorMessage());
					} else
						tellError("Hexes for this gradient are invalid! Notify an admin!");
				}
			} else if (color.equalsIgnoreCase("custom") || color.equalsIgnoreCase("reset")) {
				if (color.equalsIgnoreCase("custom")) {
					if (player.hasPermission("ultracolor.gradient.custom"))
						new GradientPrompt().start(player);
					else
						tellError(Localization.Gradient_Color_Selection.ERROR_MESSAGE);
				}

				if (color.equalsIgnoreCase("reset")) {
					pCache.clearGradients("name");

					if (!pCache.getNickName().equalsIgnoreCase("none"))
						newDisplayName = pCache.getNickName();

					player.setDisplayName(newDisplayName);
					Messenger.success(player, Localization.Gradient_Color_Selection_Reset.SUCCESS_MESSAGE);
				}
			}
		} else if (!Settings.Color_Settings.NAME_GRADIENT_COLORS && args[0].equalsIgnoreCase("name"))
			tellError(Localization.Gradient_Color_Selection.DISABLED_MESSAGE.replace("%type%", "Name"));
	}

	@Override
	protected List<String> tabComplete() {
		final List<String> colors = new ArrayList<>(PreDefinedGradientManager.getPreDefinedGradientNames());
		colors.add("custom");
		colors.add("reset");

		if (args.length == 1) return completeLastWord("chat", "name");
		if (args.length == 2) return completeLastWord(colors);

		return null;
	}
}
