package me.ultimategamer200.ultracolor.commands;

import me.ultimategamer200.ultracolor.PlayerCache;
import me.ultimategamer200.ultracolor.settings.Localization;
import me.ultimategamer200.ultracolor.util.UltraColorPermissions;
import me.ultimategamer200.ultracolor.util.UltraColorUtil;
import org.mineacademy.fo.command.SimpleCommand;

import java.util.List;

public class RealNameCommand extends SimpleCommand {
	public RealNameCommand() {
		super("realname|rn");
		setPermission(UltraColorPermissions.Command.REAL_NAME);
		setPermissionMessage(Localization.Other.NO_PERMISSION.replace("{permission}", UltraColorPermissions.Command.REAL_NAME));
		setUsage("<nick>");
		setMinArguments(1);
	}

	@Override
	protected void onCommand() {
		final String nick = args[0];
		boolean hasNickNameBeenFound = false;

		for (final PlayerCache pCache : PlayerCache.cacheMap.values()) {
			final String playerName = pCache.getPlayerName();

			if (pCache.getNickName().equalsIgnoreCase("none"))
				continue;

			if (pCache.getNickName().equalsIgnoreCase(nick)) {
				tellInfo(Localization.Nicknames.REAL_NAME_MESSAGE.replace("%nick%", pCache.getColoredNickName())
						.replace("%player%", playerName));
				hasNickNameBeenFound = true;
				break;
			}
		}

		if (!hasNickNameBeenFound)
			tellError(Localization.Nicknames.NICKNAME_NOT_BE_FOUND_MESSAGE.replace("%nick%", nick));
	}

	@Override
	protected List<String> tabComplete() {
		if (args.length == 1)
			return completeLastWord(UltraColorUtil.getNickNamesUnColored());
		return super.tabComplete();
	}
}