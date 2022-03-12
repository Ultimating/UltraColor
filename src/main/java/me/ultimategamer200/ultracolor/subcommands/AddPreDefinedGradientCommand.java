package me.ultimategamer200.ultracolor.subcommands;

import me.ultimategamer200.ultracolor.gradients.PreDefinedGradientManager;
import me.ultimategamer200.ultracolor.settings.Localization;
import me.ultimategamer200.ultracolor.settings.Settings;
import me.ultimategamer200.ultracolor.util.UltraColorPermissions;
import me.ultimategamer200.ultracolor.util.UltraColorUtil;
import org.mineacademy.fo.Valid;
import org.mineacademy.fo.command.SimpleSubCommand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddPreDefinedGradientCommand extends SimpleSubCommand {
	protected AddPreDefinedGradientCommand() {
		super("addpredefinedgradient|apdg");
		setPermission(UltraColorPermissions.Command.ADD_PRE_DEFINED_GRADIENT);
		setUsage("<name> <type> <hex1> <hex2>");
		setMinArguments(4);
		setDescription("Creates a pre-defined gradient for players to select!");
	}

	@Override
	protected void onCommand() {
		final String name = args[0];
		final String type = args[1];
		final String hex1 = args[2];
		final String hex2 = args[3];

		if (type.equalsIgnoreCase("name") && !Settings.Color_Settings.NAME_GRADIENT_COLORS) {
			tellError(Localization.Gradient_Color_Selection.DISABLED_MESSAGE.replace("%type%", "Name"));
			return;
		} else if (type.equalsIgnoreCase("chat") && !Settings.Color_Settings.CHAT_GRADIENT_COLORS) {
			tellError(Localization.Gradient_Color_Selection.DISABLED_MESSAGE.replace("%type%", "Chat"));
			return;
		} else if (type.equalsIgnoreCase("both")) {
			if (!Settings.Color_Settings.CHAT_GRADIENT_COLORS || !Settings.Color_Settings.NAME_GRADIENT_COLORS) {
				tellError("Detected chat or name gradient types are disabled in settings file! Both are required to be enabled to use the 'both' gradient type.");
				return;
			}
		}

		Valid.checkBoolean(PreDefinedGradientManager.findPreDefinedGradientByName(name) == null,
				name + " gradient already exists!");

		if (UltraColorUtil.areHexesValid(Arrays.asList(hex1, hex2)) && type.equalsIgnoreCase("chat")
				|| type.equalsIgnoreCase("name") || type.equalsIgnoreCase("both")) {
			PreDefinedGradientManager.addPreDefinedGradient(name, type, Arrays.asList(hex1, hex2));
			tellSuccess(name + " gradient has been added &asuccessfully!");
		} else if (!UltraColorUtil.areHexesValid(Arrays.asList(hex1, hex2)))
			tellError("One or both of your hexes are invalid! Be sure they contain a # and follow a 6 digit code afterwards!");
		else
			tellError("Invalid gradient type: Available: chat, name, both");
	}

	@Override
	protected List<String> tabComplete() {
		if (args.length == 1)
			return PreDefinedGradientManager.getPreDefinedGradientNames();
		if (args.length == 2)
			return Arrays.asList("chat", "name", "both");
		return new ArrayList<>();
	}
}
