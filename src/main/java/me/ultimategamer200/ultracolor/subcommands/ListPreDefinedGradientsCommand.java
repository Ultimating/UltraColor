package me.ultimategamer200.ultracolor.subcommands;

import me.ultimategamer200.ultracolor.gradients.PreDefinedGradientManager;
import me.ultimategamer200.ultracolor.util.UltraColorPermissions;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.command.SimpleSubCommand;

public class ListPreDefinedGradientsCommand extends SimpleSubCommand {
	protected ListPreDefinedGradientsCommand() {
		super("listpredefinedgradients|lpdg");
		setPermission(UltraColorPermissions.Command.LIST_PRE_DEFINED_GRADIENTS);
		setDescription("Lists all loaded pre-defined gradients!");
	}

	@Override
	protected void onCommand() {
		tellInfo(Common.format("Available: %s", PreDefinedGradientManager.getPreDefinedGradientNames()));
	}
}