package me.ultimategamer200.ultracolor.commands;

import me.ultimategamer200.ultracolor.menu.ColorSelectionMenu;
import me.ultimategamer200.ultracolor.settings.Localization;
import me.ultimategamer200.ultracolor.util.UltraColorPermissions;
import org.mineacademy.fo.annotation.AutoRegister;
import org.mineacademy.fo.command.SimpleCommand;

/**
 * The @AutoRegister tag allows automatic registration of this command without having to call the command on startup and
 * mentioning it in the plugin.yml file.
 */
@AutoRegister
public final class ChatColorCommand extends SimpleCommand {
	public ChatColorCommand() {
		super("chatcolor|cc");
		setPermission(UltraColorPermissions.CHAT_COLOR);
		setPermissionMessage(Localization.Other.NO_PERMISSION.replace("{permission}", UltraColorPermissions.CHAT_COLOR));
		setDescription("Opens the Chat Color GUI menu!");
	}

	@Override
	protected void onCommand() {
		// Checks if the sender is a console, and if so, deny the command from being run.
		checkConsole();

		new ColorSelectionMenu.ChatColorSelectionMenu().displayTo(getPlayer());
	}
}
