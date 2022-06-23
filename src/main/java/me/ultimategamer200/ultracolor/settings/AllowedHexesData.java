package me.ultimategamer200.ultracolor.settings;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.collection.SerializedMap;
import org.mineacademy.fo.model.ConfigSerializable;
import org.mineacademy.fo.settings.YamlConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * A settings file class that stores whitelisted hexes that are allowed to be used if only certain hex colors are
 * allowed to be used.
 */
@Getter
public class AllowedHexesData extends YamlConfig {
	/**
	 * The instance of this data file.
	 */
	@Getter
	private static final AllowedHexesData instance = new AllowedHexesData();

	/**
	 * The allowed hexes.
	 */
	private List<AllowedHex> allowedHexes;

	public AllowedHexesData() {
		setHeader(Common.configLine(),
				"This is where whitelisted hexes are stored. This file is useless",
				"if you have Allow_Only_Certain_Hex_Colors set to false in the settings file.",
				Common.configLine());
		loadConfiguration(NO_DEFAULT, "allowed-hexes.yml");
	}

	/**
	 * Loads the settings options to be used in the file.
	 */
	@Override
	protected void onLoad() {
		this.allowedHexes = getList("Allowed_Hexes", AllowedHex.class, this);
	}

	@Override
	protected void onSave() {
		this.set("Allowed_Hexes", allowedHexes);
	}

	/**
	 * Finds the allowed hex by the hex color.
	 */
	public AllowedHex findAllowedHex(final String hex) {
		for (final AllowedHex allowedHex : allowedHexes)
			if (allowedHex.getHex().equalsIgnoreCase(hex)) return allowedHex;
		return null;
	}

	/**
	 * Toggles an allowed hex color.
	 */
	public void toggleAllowedHex(final String hex, final String type, final String permission) {
		final AllowedHex allowedHex = findAllowedHex(hex);
		if (allowedHex != null) allowedHexes.remove(allowedHex);
		else allowedHexes.add(new AllowedHex(this, type, hex, permission));
		save();
	}

	/**
	 * Gets all the allowed hex colors as strings.
	 */
	public List<String> getAllowedHexStrings() {
		return Common.convert(allowedHexes, AllowedHex::getHex);
	}

	/**
	 * Gets all the allowed hex colors, but without their # at the start.
	 */
	public List<String> getAllowedHexStringsWithoutHashTag(final String mode) {
		final List<String> hexStrings = new ArrayList<>();

		for (final AllowedHex hex : allowedHexes) {
			if (hex.getType().equalsIgnoreCase(mode) || hex.getType().equalsIgnoreCase("both")) {
				String hexString = hex.getHex().replace("#", "");
				hexStrings.add(hexString);
			}
		}

		return hexStrings;
	}

	/**
	 * An allowed hex color class instance.
	 */
	@Getter
	@AllArgsConstructor(access = AccessLevel.PRIVATE)
	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	public static final class AllowedHex implements ConfigSerializable {
		/**
		 * The data file this applies to.
		 */
		private final AllowedHexesData data;

		/**
		 * The type this hex is related to. Allowed Types: chat, name, or both.
		 */
		private String type;

		/**
		 * The hex itself.
		 */
		private String hex;

		/**
		 * The permission node this hex requires. Defaults to "None" if this isn't given.
		 */
		private String permission;

		public static AllowedHex deserialize(final SerializedMap map, final AllowedHexesData data) {
			final AllowedHex allowedHex = new AllowedHex(data);

			allowedHex.type = map.getString("Type", "both");
			allowedHex.hex = map.getString("Hex");
			allowedHex.permission = map.getString("Permission", "None");

			return allowedHex;
		}

		/**
		 * Serializes the data.
		 */
		@Override
		public SerializedMap serialize() {
			return SerializedMap.ofArray("Type", type, "Hex", hex, "Permission", permission);
		}
	}
}
