package me.ultimategamer200.ultracolor.gradients;

import lombok.Getter;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.Valid;
import org.mineacademy.fo.remain.CompMaterial;
import org.mineacademy.fo.settings.YamlConfig;

import java.util.List;

/**
 * The settings file class for the pre-gradient.
 */
@Getter
public class PreDefinedGradientSettings extends YamlConfig {
	@Getter
	private final PreDefinedGradient gradient;

	/**
	 * The type of gradient (chat or name).
	 */
	private String type;

	/**
	 * What two hex colors does this gradient have?
	 */
	private List<String> hexColors;

	/**
	 * What permission does this gradient require?
	 */
	private String permission;

	/**
	 * The name the gradient will display in gradient menu.
	 */
	private String displayName;

	/**
	 * The item the gradient will display in the gradient menu.
	 */
	private CompMaterial menuItem;

	/**
	 * The lore the menu item will display.
	 */
	private List<String> menuLore;

	/**
	 * The message displayed on successful gradient selection.
	 */
	private String successMessage;

	/**
	 * The message displayed on failed gradient selection.
	 */
	private String errorMessage;

	public PreDefinedGradientSettings(final PreDefinedGradient gradient) {
		this.gradient = gradient;
		loadConfiguration(NO_DEFAULT, "pre-defined-gradients/" + gradient.getName() + ".yml");
		setHeader(Common.configLine(),
				"Welcome to the pre-defined gradient settings for " + gradient.getName() + " gradient!",
				Common.configLine());
	}

	/**
	 * Loads the settings options to be used in the file.
	 */
	@Override
	protected void onLoadFinish() {
		this.type = getString("Type", "both");
		this.hexColors = getStringList("Hex_Colors");
		this.permission = getString("Permission", "ultracolor.gradients." + gradient.getName());
		this.displayName = getString("Menu_Display_Name", gradient.getName() + " Gradient");
		this.menuItem = getMaterial("Menu_Item");
		this.menuLore = getStringList("Menu_Lore");
		this.successMessage = getString("Success_Message");
		this.errorMessage = getString("Error_Message");
	}

	public void setType(final String type) {
		Valid.checkBoolean(type.equalsIgnoreCase("chat") || type.equalsIgnoreCase("name")
						|| type.equalsIgnoreCase("both"),
				"The gradient type must be chat, name, or both, not " + type + "!");
		this.type = type;
		save("Type", type);
	}

	public void toggleHexColor(final String hexColor) {
		if (this.hexColors.contains(hexColor)) {
			this.hexColors.remove(hexColor);
			save("Hex_Colors", this.hexColors);
			return;
		}

		this.hexColors.add(hexColor);
		save("Hex_Colors", this.hexColors);
	}

	public void setPermission(final String permission) {
		this.permission = permission;
		save("Permission", permission);
	}

	public void setDisplayName(final String displayName) {
		this.displayName = displayName;
		save("Menu_Display_Name", displayName);
	}

	public void setMenuItem(final CompMaterial menuItem) {
		this.menuItem = menuItem;
		save("Menu_Item", menuItem);
	}

	public void setMenuLore(final List<String> menuLore) {
		this.menuLore = menuLore;
		save("Menu_Lore", menuLore);
	}

	public void setSuccessMessage(final String successMessage) {
		this.successMessage = successMessage;
		save("Success_Message", successMessage);
	}

	public void setErrorMessage(final String errorMessage) {
		this.errorMessage = errorMessage;
		save("Error_Message", errorMessage);
	}
}
