package me.ultimategamer200.ultracolor.subcommands;

import me.ultimategamer200.ultracolor.gradients.PreDefinedGradientManager;
import me.ultimategamer200.ultracolor.util.UltraColorPermissions;
import org.mineacademy.fo.Valid;
import org.mineacademy.fo.command.SimpleSubCommand;

import java.util.ArrayList;
import java.util.List;

public class RemovePreDefinedGradientCommand extends SimpleSubCommand {
	protected RemovePreDefinedGradientCommand() {
		super("removepredefinedgradient|rpg");
		setPermission(UltraColorPermissions.Command.REMOVE_PRE_DEFINED_GRADIENT);
		setUsage("<name>");
		setMinArguments(1);
		setDescription("Removes a loaded pre-defined gradient!");
	}

	@Override
	protected void onCommand() {
		final String name = args[0];

		Valid.checkBoolean(PreDefinedGradientManager.findPreDefinedGradientByName(name) != null,
				name + " gradient doesn't exist!");
		PreDefinedGradientManager.removePreDefinedGradient(PreDefinedGradientManager.findPreDefinedGradientByName(name));
		tellSuccess(name + " Gradient &cremoved &asuccessfully&7!");
	}

	@Override
	protected List<String> tabComplete() {
		if (args.length == 1)
			return PreDefinedGradientManager.getPreDefinedGradientNames();
		return new ArrayList<>();
	}
}