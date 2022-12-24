package me.ultimategamer200.ultracolor.commands;

import me.ultimategamer200.ultracolor.PlayerCache;
import me.ultimategamer200.ultracolor.settings.Localization;
import me.ultimategamer200.ultracolor.settings.Settings;
import me.ultimategamer200.ultracolor.util.UltraColorPermissions;
import me.ultimategamer200.ultracolor.util.UltraColorUtil;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.mineacademy.fo.ChatUtil;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.command.SimpleCommand;

import java.util.List;
import java.util.Objects;

public class NicknameCommand extends SimpleCommand {
	public NicknameCommand() {
		super("nickname|nick");
		setPermission(UltraColorPermissions.Command.NICKNAME);
		setPermissionMessage(Localization.Other.NO_PERMISSION.replace("{permission}", UltraColorPermissions.Command.NICKNAME));
		setUsage("<nick> [player]");
		setMinArguments(1);
	}

	@Override
	protected void onCommand() {
		final String nickName = args[0];
		PlayerCache pCache;
		String playerName;

		if (UltraColorUtil.getNickNamesUnColored().contains(nickName)) {
			tellError(Localization.Nicknames.NICKNAME_ALREADY_TAKEN_MESSAGE);
			return;
		} else if (nickName.equalsIgnoreCase("none")) {
			tellError("You cannot put 'none' as your nickname.");
			return;
		} else if (nickName.length() > Settings.Other.NICKNAME_CHARACTER_LIMIT) {
			tellError("The nickname entered is over &f" + Common.plural(Settings.Other.NICKNAME_CHARACTER_LIMIT, "character") + "!");
			return;
		}

		if (args.length < 2) {
			checkConsole();
			pCache = PlayerCache.fromPlayer(getPlayer());
			playerName = getPlayer().getName();
		} else {
			final OfflinePlayer player = Bukkit.getOfflinePlayer(args[1]);
			pCache = PlayerCache.fromOfflinePlayer(player);
			playerName = player.getName();

			if (!(getSender() instanceof ConsoleCommandSender)) {
				if (!getSender().hasPermission(UltraColorPermissions.Command.NICKNAME_OTHERS)) {
					tellError(Localization.Other.NO_PERMISSION.replace("{permission}", "ultracolor.nickname.others"));
					return;
				}
			}

			if (pCache.getNickName().equalsIgnoreCase("none") && nickName.equalsIgnoreCase("reset")) {
				tellError("&e" + player.getName() + " &cdoes not have a nickname!");
				return;
			}
		}

		if (nickName.equalsIgnoreCase("reset")) {
			pCache.setNickName("none");
			pCache.setColoredNickName("none");

			if (args.length < 2) {
				getPlayer().setDisplayName(getPlayer().getName());

				if (pCache.getNameColor() != null || pCache.getNameFormat() != null) {
					if (pCache.getNameColor() != null && pCache.getNameFormat() == null)
						getPlayer().setDisplayName(UltraColorUtil.nameAndChatColorToString(pCache.getNameColor()) + getPlayer().getName());
					else if (pCache.getNameColor() != null)
						getPlayer().setDisplayName(UltraColorUtil.nameAndChatColorToString(pCache.getNameColor())
								+ UltraColorUtil.nameFormatToString(pCache.getNameFormat()) + getPlayer().getName());
					else
						getPlayer().setDisplayName(UltraColorUtil.nameFormatToString(pCache.getNameFormat()) + getPlayer().getName());
				}

				if (pCache.isNameRainbowColors()) {
					if (pCache.getNameFormat() == null)
						getPlayer().setDisplayName(UltraColorUtil.convertStringToRainbow(getPlayer().getName(), false, ""));
					else
						getPlayer().setDisplayName(UltraColorUtil.convertStringToRainbow(getPlayer().getName(), true, pCache.getNameFormat().name()));
				}

				if (pCache.getCustomGradientOne() != null && pCache.getCustomGradientTwo() != null) {
					if (pCache.getNameFormat() != null)
						getPlayer().setDisplayName(ChatUtil.generateGradient(UltraColorUtil.nameFormatToString(pCache.getNameFormat()) + getPlayer().getName(),
								pCache.getCustomGradientOne(), pCache.getCustomGradientTwo()));
					else
						getPlayer().setDisplayName(ChatUtil.generateGradient(getPlayer().getName(), pCache.getCustomGradientOne(), pCache.getCustomGradientTwo()));
				}
			} else {
				final OfflinePlayer player = Bukkit.getOfflinePlayer(args[1]);
				final Player player1 = (Player) player;

				if (player.isOnline())
					player1.setDisplayName(player.getName());

				if (pCache.getNameColor() != null || pCache.getNameFormat() != null) {
					if (player.isOnline()) {
						if (pCache.getNameColor() != null && pCache.getNameFormat() == null)
							player1.setDisplayName(UltraColorUtil.nameAndChatColorToString(pCache.getNameColor()) + player1.getName());
						else if (pCache.getNameColor() != null)
							player1.setDisplayName(UltraColorUtil.nameAndChatColorToString(pCache.getNameColor())
									+ UltraColorUtil.nameFormatToString(pCache.getNameFormat()) + player1.getName());
						else if (pCache.getNameFormat() != null)
							player1.setDisplayName(UltraColorUtil.nameFormatToString(pCache.getNameFormat()) + player1.getName());
					}
				}

				if (pCache.isNameRainbowColors()) {
					if (player.isOnline()) {
						if (pCache.getNameFormat() == null)
							player1.setDisplayName(UltraColorUtil.convertStringToRainbow(player1.getName(), false, ""));
						else
							player1.setDisplayName(UltraColorUtil.convertStringToRainbow(player1.getName(), true, pCache.getNameFormat().name()));
					}
				}

				if (pCache.getCustomGradientOne() != null && pCache.getCustomGradientTwo() != null) {
					if (player.isOnline()) {
						if (pCache.getNameFormat() != null)
							player1.setDisplayName(ChatUtil.generateGradient(UltraColorUtil.nameFormatToString(pCache.getNameFormat()) + player1.getName(),
									pCache.getCustomGradientOne(), pCache.getCustomGradientTwo()));
						else
							player1.setDisplayName(ChatUtil.generateGradient(player1.getName(), pCache.getCustomGradientOne(), pCache.getCustomGradientTwo()));
					}
				}
			}
		} else {
			pCache.setNickName(nickName);
			boolean colorNickSet = false;

			if (pCache.isNameRainbowColors()) {
				if (pCache.getNameFormat() == null)
					pCache.setColoredNickName(UltraColorUtil.convertStringToRainbow(nickName, false, ""));
				else
					pCache.setColoredNickName(UltraColorUtil.convertStringToRainbow(nickName, true, pCache.getNameFormat().name()));
				colorNickSet = true;
			}

			if (pCache.getCustomGradientOne() != null && pCache.getCustomGradientTwo() != null && !colorNickSet) {
				if (pCache.getNameFormat() != null)
					pCache.setColoredNickName(ChatUtil.generateGradient(UltraColorUtil.nameFormatToString(pCache.getNameFormat()) + nickName,
							pCache.getCustomGradientOne(), pCache.getCustomGradientTwo()));
				else
					pCache.setColoredNickName(ChatUtil.generateGradient(nickName, pCache.getCustomGradientOne(), pCache.getCustomGradientTwo()));
				colorNickSet = true;
			}

			if (pCache.getNameColor() != null && !colorNickSet) {
				if (pCache.getNameFormat() != null)
					pCache.setColoredNickName(UltraColorUtil.nameAndChatColorToString(pCache.getNameColor())
							+ UltraColorUtil.nameFormatToString(pCache.getNameFormat()) + nickName);
				else
					pCache.setColoredNickName(UltraColorUtil.nameAndChatColorToString(pCache.getNameColor()) + nickName);
				colorNickSet = true;
			} else if (pCache.getNameFormat() != null && !colorNickSet) {
				pCache.setColoredNickName(UltraColorUtil.nameFormatToString(pCache.getNameFormat()) + nickName);
				colorNickSet = true;
			}

			if (!colorNickSet)
				pCache.setColoredNickName(nickName);

			if (args.length < 2) {
				getPlayer().setDisplayName(pCache.getColoredNickName());
			} else {
				if (Objects.requireNonNull(Bukkit.getOfflinePlayer(args[1])).isOnline()) {
					final Player onlinePlayer = Bukkit.getPlayer(args[1]);
					Objects.requireNonNull(onlinePlayer).setDisplayName(pCache.getColoredNickName());
				}
			}
		}

		tellSuccess(Localization.Nicknames.NICKNAME_COMMAND_SUCCESSFUL_MESSAGE.replace("%nick%", pCache.getColoredNickName())
				.replace("%player%", playerName));
	}

	@Override
	protected List<String> tabComplete() {
		if (args.length == 1)
			return completeLastWord("<nick>", "reset");
		if (args.length == 2)
			return completeLastWordPlayerNames();
		return super.tabComplete();
	}
}
