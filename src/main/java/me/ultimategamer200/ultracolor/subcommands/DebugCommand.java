package me.ultimategamer200.ultracolor.subcommands;

import lombok.Setter;
import org.bukkit.Bukkit;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.FileUtil;
import org.mineacademy.fo.MinecraftVersion;
import org.mineacademy.fo.TimeUtil;
import org.mineacademy.fo.command.SimpleSubCommand;
import org.mineacademy.fo.plugin.SimplePlugin;
import org.mineacademy.fo.remain.Remain;
import org.mineacademy.fo.settings.SimpleLocalization;
import org.mineacademy.fo.settings.YamlConfig;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class DebugCommand extends SimpleSubCommand {
	/**
	 * Set the custom debug lines you would like to add to the debug file
	 */
	@Setter
	private static Consumer<List<String>> debugLines;

	public DebugCommand() {
		super("debug");
		setDescription("ZIP your UltraColor folder for reporting bugs or providing more information about your setup.");
		setPermission("ultracolor.command.debug");
	}

	@Override
	protected void onCommand() {
		tell(SimpleLocalization.Commands.DEBUG_PREPARING);

		final File debugFolder = FileUtil.getFile("debug");
		final List<File> files = listFilesRecursively(SimplePlugin.getData(), new ArrayList<>());

		// Clean up the old folder if exists
		FileUtil.deleteRecursivelly(debugFolder);

		// Collect general debug information first
		writeDebugInformation();

		// Copy all plugin files
		copyFilesToDebug(files);

		// Zip the folder
		zipAndRemoveFolder(debugFolder);

		tell(SimpleLocalization.Commands.DEBUG_SUCCESS.replace("{amount}", String.valueOf(files.size())));
	}

	/*
	 * Write our own debug information
	 */
	private void writeDebugInformation() {
		final List<String> lines = Common.toList(Common.consoleLine(),
				" Debug log generated " + TimeUtil.getFormattedDate(),
				Common.consoleLine(),
				"Plugin: " + SimplePlugin.getInstance().getDescription().getFullName(),
				"Server Version: " + Bukkit.getName() + " " + MinecraftVersion.getServerVersion(),
				"Java: " + System.getProperty("java.version") + " (" + System.getProperty("java.specification.vendor") + "/" + System.getProperty("java.vm.vendor") + ")",
				"OS: " + System.getProperty("os.name") + " " + System.getProperty("os.version"),
				"Online mode: " + Bukkit.getOnlineMode(),
				"Players Online: " + Remain.getOnlinePlayers().size(),
				"Plugins: " + Common.join(Bukkit.getPluginManager().getPlugins(), ", ", plugin -> plugin.getDescription().getFullName()));

		if (debugLines != null) debugLines.accept(lines);
		FileUtil.write("debug/general.txt", lines);
	}

	/*
	 * Copy the given files into debug/ folder
	 */
	private void copyFilesToDebug(List<File> files) {
		for (final File file : files) {
			try {
				// Get the path in our folder
				final String path = file.getPath().replace("\\", "/").replace("plugins/" + SimplePlugin.getNamed(), "");

				// Create a copy file
				final File copy = FileUtil.createIfNotExists("debug/" + path);

				// Strip sensitive keys from .YML files
				if (file.getName().endsWith(".yml")) {
					final YamlConfig config = YamlConfig.fromFile(file);
					final YamlConfig copyConfig = YamlConfig.fromFile(copy);

					for (final String key : config.getKeys(true))
						if (!key.contains("Database")) copyConfig.set(key, config.getObject(key));

					copyConfig.save(copy);
				} else
					Files.copy(file.toPath(), copy.toPath(), StandardCopyOption.REPLACE_EXISTING);

			} catch (final Exception ex) {
				ex.printStackTrace();

				returnTell(SimpleLocalization.Commands.DEBUG_COPY_FAIL.replace("{file}", file.getName()));
			}
		}
	}

	/*
	 * Zips the given folder and removes it afterwards
	 */
	private void zipAndRemoveFolder(File folder) {
		try {
			final String path = folder.getPath();

			FileUtil.zip(path, path);
			FileUtil.deleteRecursivelly(folder);

		} catch (final IOException ex) {
			ex.printStackTrace();

			returnTell(SimpleLocalization.Commands.DEBUG_ZIP_FAIL);
		}
	}

	/*
	 * Load the list of files available to ZIP
	 */
	private List<File> listFilesRecursively(File folder, List<File> files) {
		for (final File file : folder.listFiles()) {
			if (file.isDirectory()) {
				// Ignore log directory and ignore the debug directory itself
				if (!file.getName().equals("logs") && !file.getName().equals("debug"))
					listFilesRecursively(file, files);

			} else {
				// Ignore the debug zip file itself
				if (!file.getName().equals("debug.zip") && !file.getName().equals("mysql.yml"))
					files.add(file);
			}
		}

		return files;
	}

	/**
	 * @see org.mineacademy.fo.command.SimpleCommand#tabComplete()
	 */
	@Override
	protected List<String> tabComplete() {
		return NO_COMPLETE;
	}
}
