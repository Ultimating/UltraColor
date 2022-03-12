package me.ultimategamer200.ultracolor.subcommands;

import me.ultimategamer200.ultracolor.UltraColorPlugin;
import me.ultimategamer200.ultracolor.settings.AllowedHexesData;
import me.ultimategamer200.ultracolor.settings.Localization;
import me.ultimategamer200.ultracolor.settings.Settings;
import me.ultimategamer200.ultracolor.util.UltraColorPermissions;
import me.ultimategamer200.ultracolor.util.UltraColorUtil;
import org.mineacademy.fo.command.SimpleSubCommand;

import java.util.List;

public class AddAllowedHexColorCommand extends SimpleSubCommand {
	protected AddAllowedHexColorCommand() {
		super("addallowedhexcolor|aahc");
		setPermission(UltraColorPermissions.Command.ADD_ALLOWED_HEX_COLOR);
		setUsage("<type> <hex> [permission]");
		setDescription("Allows a certain hex to be usable!");
		setMinArguments(2);
	}

	@Override
	protected void onCommand() {
		final String type = args[0];
		final String hex = args[1];
		final AllowedHexesData data = AllowedHexesData.getInstance();

		if (!type.equalsIgnoreCase("chat") && !type.equalsIgnoreCase("name")
				&& !type.equalsIgnoreCase("both")) {
			tellError("Invalid type: Available: chat, name, and both");
			return;
		}

		if (!UltraColorUtil.isHexValid(hex))
			tellError(Localization.Hex_Colors.INVALID_HEX_COLOR_MESSAGE);
		else {
			if (data.findAllowedHex(hex) != null)
				tellError(Localization.Hex_Colors.HEX_COLOR_ALREADY_ADDED_TO_WHITELIST);
			else {
				if (args.length < 3)
					data.toggleAllowedHex(hex, type, "None");
				else {
					final String permission = args[2];
					data.toggleAllowedHex(hex, type, permission);
				}
				tellSuccess(Localization.Hex_Colors.HEX_COLOR_ADDED_TO_WHITELIST.replace("%hex_color%", hex));

				if (!Settings.Color_Settings.ALLOW_ONLY_CERTAIN_HEX_COLORS) {
					Settings.Color_Settings.setAllowOnlyCertainHexColors(true);
					UltraColorPlugin.getInstance().reload();
				}
			}
		}
	}

	@Override
	protected List<String> tabComplete() {
		if (args.length == 1)
			return completeLastWord("chat", "name", "both");
		return super.tabComplete();
	}
}