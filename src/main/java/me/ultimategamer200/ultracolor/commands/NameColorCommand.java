package me.ultimategamer200.ultracolor.commands;

import me.ultimategamer200.ultracolor.menu.ColorSelectionMenu;
import me.ultimategamer200.ultracolor.settings.Localization;
import me.ultimategamer200.ultracolor.util.UltraColorPermissions;
import org.bukkit.entity.Player;
import org.mineacademy.fo.annotation.AutoRegister;
import org.mineacademy.fo.command.SimpleCommand;

@AutoRegister
public final class NameColorCommand extends SimpleCommand {
	public NameColorCommand() {
		super("namecolor|nc");
		setPermission(UltraColorPermissions.NAME_COLOR);
		setPermissionMessage(Localization.Other.NO_PERMISSION.replace("{permission}", UltraColorPermissions.NAME_COLOR));
		setDescription("Opens the name color selection GUI.");
	}

	@Override
	protected void onCommand() {
		checkConsole();

		final Player player = getPlayer();
		new ColorSelectionMenu.NameColorSelectionMenu().displayTo(player);
	}
}
