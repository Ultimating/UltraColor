package me.ultimategamer200.ultracolor.subcommands;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.ultimategamer200.ultracolor.settings.Settings;
import me.ultimategamer200.ultracolor.util.UltraColorPermissions;
import org.mineacademy.fo.annotation.AutoRegister;
import org.mineacademy.fo.collection.SerializedMap;
import org.mineacademy.fo.command.PermsCommand;
import org.mineacademy.fo.command.SimpleCommandGroup;
import org.mineacademy.fo.remain.Remain;

/**
 * The main command group that registers all subcommands with the /ucolor command.
 */
@AutoRegister
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UltraColorCommandGroup extends SimpleCommandGroup {
	@Getter
	private static final UltraColorCommandGroup instance = new UltraColorCommandGroup();

	@Override
	protected void registerSubcommands() {
		registerSubcommand(new ResetColorCommand());
		registerSubcommand(new SetNameColorCommand());
		registerSubcommand(new SetChatColorCommand());
		registerSubcommand(new ForceColorCommand());
		registerSubcommand(new DebugCommand());

		PermsCommand permsCommand = new PermsCommand(UltraColorPermissions.class, SerializedMap.of("label", Settings.MAIN_COMMAND_ALIASES.get(0)));
		permsCommand.setPermission("ultracolor.command.permissions");
		registerSubcommand(permsCommand);

		org.mineacademy.fo.command.ReloadCommand reloadCommand = new org.mineacademy.fo.command.ReloadCommand();
		reloadCommand.setPermission(UltraColorPermissions.Command.RELOAD);
		registerSubcommand(reloadCommand);
		if (Settings.Other.NICKNAMES_ENABLE) registerSubcommand(new ListNicknamesCommand());

		if (Remain.hasHexColors()) {
			if (Settings.Color_Settings.NAME_GRADIENT_COLORS || Settings.Color_Settings.CHAT_GRADIENT_COLORS) {
				registerSubcommand(new SetGradientCommand());
				registerSubcommand(new ForceGradientCommand());
				registerSubcommand(new AddPreDefinedGradientCommand());
				registerSubcommand(new RemovePreDefinedGradientCommand());
				registerSubcommand(new ListPreDefinedGradientsCommand());
			}

			registerSubcommand(new AddAllowedHexColorCommand());
			registerSubcommand(new RemoveAllowedHexColorCommand());
		}
	}

	@Override
	protected String getCredits() {
		return "Visit the UltraColor Spigot page here: https://www.spigotmc.org/resources/ultracolor.85332/";
	}
}
