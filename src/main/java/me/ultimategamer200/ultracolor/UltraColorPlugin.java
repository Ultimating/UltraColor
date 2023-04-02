package me.ultimategamer200.ultracolor;

import com.Zrips.CMI.CMI;
import com.earth2me.essentials.Essentials;
import lombok.SneakyThrows;
import me.ultimategamer200.ultracolor.commands.GradientCommand;
import me.ultimategamer200.ultracolor.commands.HexColorCommand;
import me.ultimategamer200.ultracolor.commands.NicknameCommand;
import me.ultimategamer200.ultracolor.commands.RealNameCommand;
import me.ultimategamer200.ultracolor.gradients.PreDefinedGradientManager;
import me.ultimategamer200.ultracolor.hooks.PlaceholderAPIHook;
import me.ultimategamer200.ultracolor.listeners.DatabaseListener;
import me.ultimategamer200.ultracolor.mysql.UltraColorDatabase;
import me.ultimategamer200.ultracolor.settings.AllowedHexesData;
import me.ultimategamer200.ultracolor.settings.Localization;
import me.ultimategamer200.ultracolor.settings.Settings;
import me.ultimategamer200.ultracolor.util.Filter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.FileUtil;
import org.mineacademy.fo.Messenger;
import org.mineacademy.fo.MinecraftVersion;
import org.mineacademy.fo.debug.LagCatcher;
import org.mineacademy.fo.menu.button.Button;
import org.mineacademy.fo.menu.button.ButtonReturnBack;
import org.mineacademy.fo.model.HookManager;
import org.mineacademy.fo.model.SpigotUpdater;
import org.mineacademy.fo.plugin.SimplePlugin;
import org.mineacademy.fo.remain.Remain;

import java.io.File;

/**
 * The main plugin instance. This class extends SimplePlugin rather than JavaPlugin because we use the Foundation library!
 * This library allows developers to cut time spent on coding by at least a 10th, saving time on making products everyone can
 * enjoy!
 */
public class UltraColorPlugin extends SimplePlugin {
	@SneakyThrows
	@Override
	protected void onPluginStart() {
		// Injects our custom filter to the console.
		Filter.inject();
		// The log prefix for the plugin to use in console.
		Common.setLogPrefix("[UltraColor]");

		// If the server has a Java 15, but doesn't have NashornPlus and is older than 1.16, disable the plugin for safe keeping.
		if (Remain.getJavaVersion() >= 15 && !Common.doesPluginExist("NashornPlus")) {
			if (!MinecraftVersion.atLeast(MinecraftVersion.V.v1_16)) {
				Common.log("Detected server using Java 15+ without NashornPlus plugin!",
						"Please install NashornPlus plugin from bitbucket.org/kangarko/nashornplus/downloads/",
						"Disabling plugin to be on the safe side!");
				getPluginLoader().disablePlugin(this);
				return;
			}
		}

		if (!Bukkit.getVersion().contains("Paper") && !Bukkit.getVersion().equals("Spigot")) {
			Common.logFramed("WARNING: You are using software that isn't Spigot/Paper! If you experience issues, please test on Spigot/Paper first.",
					"Otherwise, you will not get support.");
		}

		// Database Registration
		if (Settings.Database.ENABLED) {
			LagCatcher.start("mysql");
			final String databaseURL = "jdbc:mysql://{host}/{database}?autoReconnect=true&useSSL=false";
			UltraColorDatabase.getInstance().connect(databaseURL.replace("{host}", Settings.Database.HOST + ":" + Settings.Database.PORT)
					.replace("{database}", Settings.Database.DATABASE), Settings.Database.USER, Settings.Database.PASS);
			LagCatcher.end("mysql", 0, "Connection to MySQL established. Took {time} ms.");
			registerEvents(new DatabaseListener());
		}

		Common.log(Common.consoleLineSmooth());
		Common.log(" _   _ _ _             _____       _            \n" +
				"| | | | | |           /  __ \\     | |           \n" +
				"| | | | | |_ _ __ __ _| /  \\/ ___ | | ___  _ __ \n" +
				"| | | | | __| '__/ _` | |    / _ \\| |/ _ \\| '__|\n" +
				"| |_| | | |_| | | (_| | \\__/\\ (_) | | (_) | |   \n" +
				" \\___/|_|\\__|_|  \\__,_|\\____/\\___/|_|\\___/|_| ");
		Common.log("Plugin loaded successfully!");

		// Registers optional plugin commands.
		registerOptionalCommands();

		// Uses a prefix when using Common.tell() methods.
		Common.setTellPrefix(Settings.PLUGIN_PREFIX);

		// Checks if change-displayname is false in Essentials and/or CMI.
		if (HookManager.isEssentialsLoaded() || HookManager.isCMILoaded()) {
			if (HookManager.isEssentialsLoaded()) {
				final File essentialsXConfig = new File(Essentials.getPlugin(Essentials.class).getDataFolder() + File.separator + "config.yml");
				final YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(essentialsXConfig);
				final boolean changeDisplayName = yamlConfiguration.getBoolean("change-displayname");

				if (changeDisplayName && Settings.Color_Settings.NAME_COLORS) {
					Common.log("Detected change-displayname set to 'true' in EssentialsX config.yml file!",
							"Please set this option to 'false' if you want name coloring functionality!");
				}
			} else {
				final File cmiConfig = new File(CMI.getPlugin(CMI.class).getDataFolder() + File.separator + "config.yml");
				final YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(cmiConfig);
				final boolean changeDisplayName = yamlConfiguration.getBoolean("DisplayName.Change");

				if (changeDisplayName && Settings.Color_Settings.NAME_COLORS) {
					Common.log("Detected DisplayName.Change option set to 'true' in CMI config.yml file!",
							"Please set this option to 'false' if you want name coloring functionality!");
				}
			}
		}
		
		Common.log(Common.consoleLineSmooth());
	}

	/**
	 * Commands that require a settings option to be enabled and/or a certain Minecraft version or newer are registered
	 * here instead of using @AutoRegister to make code optimized.
	 */
	private void registerOptionalCommands() {
		if (Settings.Other.NICKNAMES_ENABLE) {
			registerCommand(new NicknameCommand());
			registerCommand(new RealNameCommand());
		}

		if (Settings.Color_Settings.CHAT_HEX_COLORS || Settings.Color_Settings.NAME_HEX_COLORS)
			if (Remain.hasHexColors()) registerCommand(new HexColorCommand());
		if (Settings.Color_Settings.CHAT_GRADIENT_COLORS || Settings.Color_Settings.NAME_GRADIENT_COLORS)
			if (Remain.hasHexColors()) registerCommand(new GradientCommand());
	}

	// This method is called when you start the plugin AND when you reload it
	@Override
	protected void onReloadablesStart() {
		Messenger.setSuccessPrefix(Settings.Other_Prefixes.SUCCESS_PREFIX);
		Messenger.setErrorPrefix(Settings.Other_Prefixes.ERROR_PREFIX);

		// Loads pre-defined gradients if gradients are enabled and server is 1.16+
		if (Settings.Color_Settings.CHAT_GRADIENT_COLORS || Settings.Color_Settings.NAME_GRADIENT_COLORS)
			if (Remain.hasHexColors()) PreDefinedGradientManager.loadPreDefinedGradients();

		if (Localization.Main_GUI_Customization_Chat_Color_Selection.ALLOW_INFO_BUTTON || Localization.Main_GUI_Customization_Name_Color_Selection.ALLOW_INFO_BUTTON
				|| Localization.Gradient_Color_Selection.ALLOW_INFO_BUTTON || Localization.Main_GUI_Customization.ALLOW_INFO_BUTTON) {
			Button.setInfoButtonMaterial(Settings.Other.INFO_ITEM);
			Button.setInfoButtonTitle(Localization.Menu_Titles.MENU_INFORMATION_TITLE);
		}

		if (Localization.Gradient_Color_Selection_Return.ENABLED) {
			ButtonReturnBack.setMaterial(Settings.Gradient_Color_Menu_Items.RETURN_ITEM);
			ButtonReturnBack.setTitle(Localization.Gradient_Color_Selection_Return.CUSTOM_NAME);
			ButtonReturnBack.setLore(Localization.Gradient_Color_Selection_Return.CUSTOM_LORE);
		}

		if (HookManager.isPlaceholderAPILoaded()) {
			new PlaceholderAPIHook().register();
			Common.log("Placeholders loaded!");
		}

		if (Settings.Color_Settings.ALLOW_ONLY_CERTAIN_HEX_COLORS) {
			final File file = FileUtil.getFile("allowed-hexes.yml");

			if (!file.exists()) {
				new AllowedHexesData();
				Common.log("Whitelisted Hexes are enabled!");
			}
		}

		// Save the data.db file
		DataFile.getInstance().save();

		// Clear the cache in the plugin so that we load it fresh for only players that are online right now,
		// saving memory
		PlayerCache.clearAllData();
	}

	/**
	 * Gets the UltraColor Spigot resource to check updates for by its ID number.
	 */
	@Override
	public SpigotUpdater getUpdateCheck() {
		if (Settings.NOTIFY_UPDATES) return new SpigotUpdater(85332);
		return null;
	}

	@Override
	public int getMetricsPluginId() {
		return 9266;
	}

	/**
	 * What year was the plugin first released?
	 */
	@Override
	public int getFoundedYear() {
		return 2020; // 10.30.2020
	}

	/**
	 * What is the oldest version this plugin will accept?
	 */
	@Override
	public MinecraftVersion.V getMinimumVersion() {
		return MinecraftVersion.V.v1_8;
	}

	/**
	 * What is the newest version this plugin will accept?
	 */
	@Override
	public MinecraftVersion.V getMaximumVersion() {
		return MinecraftVersion.V.v1_19;
	}
}
