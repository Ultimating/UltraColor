package me.ultimategamer200.ultracolor.subcommands;

import me.ultimategamer200.ultracolor.settings.AllowedHexesData;
import me.ultimategamer200.ultracolor.settings.Localization;
import me.ultimategamer200.ultracolor.util.UltraColorPermissions;
import me.ultimategamer200.ultracolor.util.UltraColorUtil;
import org.mineacademy.fo.command.SimpleSubCommand;

import java.util.List;

public class RemoveAllowedHexColorCommand extends SimpleSubCommand {
	protected RemoveAllowedHexColorCommand() {
		super("removeallowedhexcolor|rahc");
		setPermission(UltraColorPermissions.Command.REMOVE_ALLOWED_HEX_COLOR);
		setUsage("<hex>");
		setMinArguments(1);
	}

	@Override
	protected void onCommand() {
		final String hex = args[0];

		if (UltraColorUtil.isHexValid(hex)) {
			tellError(Localization.Hex_Colors.INVALID_HEX_COLOR_MESSAGE);
			return;
		}

		final AllowedHexesData.AllowedHex allowedHex = AllowedHexesData.getInstance().findAllowedHex(hex);

		if (allowedHex != null) {
			AllowedHexesData.getInstance().toggleAllowedHex(hex, allowedHex.getType(), allowedHex.getPermission());
			tellSuccess(Localization.Hex_Colors.HEX_COLOR_REMOVED_FROM_WHITELIST.replace("%hex_color%", hex));
		} else
			tellError(Localization.Hex_Colors.HEX_COLOR_NOT_FOUND_ON_WHITELIST);
	}

	@Override
	protected List<String> tabComplete() {
		if (args.length == 1)
			return completeLastWord(AllowedHexesData.getInstance().getAllowedHexStrings());
		return super.tabComplete();
	}
}