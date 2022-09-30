package me.ultimategamer200.ultracolor;

import lombok.Getter;
import org.mineacademy.fo.constants.FoConstants;
import org.mineacademy.fo.settings.YamlConfig;

/**
 * A helper class for the data.db file.
 */
public final class DataFile extends YamlConfig {
	@Getter
	private static final DataFile instance = new DataFile();

	private DataFile() {
		loadConfiguration(NO_DEFAULT, FoConstants.File.DATA);
	}
}
