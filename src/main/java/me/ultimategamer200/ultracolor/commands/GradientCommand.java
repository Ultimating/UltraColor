package me.ultimategamer200.ultracolor.commands;

import me.ultimategamer200.ultracolor.menu.GradientMenu;
import me.ultimategamer200.ultracolor.settings.Localization;
import me.ultimategamer200.ultracolor.util.UltraColorPermissions;
import org.mineacademy.fo.MinecraftVersion;
import org.mineacademy.fo.command.SimpleCommand;

public class GradientCommand extends SimpleCommand {
	public GradientCommand() {
		super("gradientcolor|gc");
		setPermission(UltraColorPermissions.GRADIENT_COLOR);
	}

	@Override
	protected void onCommand() {
		checkConsole();

		// Is the SERVER running on at least 1.16?
		if (MinecraftVersion.atLeast(MinecraftVersion.V.v1_16))
			new GradientMenu().displayTo(getPlayer());
		else
			tellError(Localization.Gradient_Color_Selection.UNSUPPORTED_VERSION_MESSAGE);
	}
}
