package me.ultimategamer200.ultracolor.gradients;

import lombok.Getter;
import lombok.experimental.UtilityClass;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.FileUtil;
import org.mineacademy.fo.Valid;
import org.mineacademy.fo.remain.CompMaterial;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class manages pre-gradients in a variety of ways.
 */
@UtilityClass
public class PreDefinedGradientManager {
	@Getter
	private final List<PreDefinedGradient> loadedPreDefinedGradients = new ArrayList<>();

	/**
	 * Loads the pre-defined gradients made on server startup and plugin reload.
	 */
	public void loadPreDefinedGradients() {
		loadedPreDefinedGradients.clear();

		for (final File file : FileUtil.getFiles("pre-defined-gradients", "yml")) {
			loadedPreDefinedGradients.add(new PreDefinedGradient(FileUtil.getFileName(file)));
			Common.log("[+] Loaded " + FileUtil.getFileName(file) + " gradient successfully!");
		}
	}

	/**
	 * Adds a pre-defined gradient for players to use.
	 *
	 * @param gradient the gradient name
	 * @param type     the type of gradient (chat, name, or both)
	 * @param hexes    the two hex colors involved.
	 */
	public void addPreDefinedGradient(final String gradient, final String type, final List<String> hexes) {
		Valid.checkBoolean(findPreDefinedGradientByName(gradient) == null,
				"Cannot find " + gradient + " gradient!");
		loadedPreDefinedGradients.add(new PreDefinedGradient(gradient));

		final PreDefinedGradient madeGradient = PreDefinedGradientManager.findPreDefinedGradientByName(gradient);
		madeGradient.getSettings().setDisplayName(gradient + " Gradient");
		madeGradient.getSettings().setType(type);
		madeGradient.getSettings().setPermission("ultracolor.gradient." + gradient.toLowerCase());
		madeGradient.getSettings().setMenuItem(CompMaterial.PAPER);
		madeGradient.getSettings().setMenuLore(Arrays.asList("------------------------------------",
				"Click to select this cool gradient! Wooo!",
				"Color Preview: {color_preview}",
				"------------------------------------"));
		madeGradient.getSettings().setSuccessMessage("Selected " + gradient + " Gradient successfully!");
		madeGradient.getSettings().setErrorMessage("Insufficient permission!");

		for (final String hex : hexes)
			madeGradient.getSettings().toggleHexColor(hex);

		Common.log("[+] Loaded " + madeGradient.getName() + " gradient successfully!");
	}

	/**
	 * Does similarly as the above method, but only removing pre-gradients.
	 *
	 * @param preDefinedGradient the gradient to remove
	 */
	public void removePreDefinedGradient(final PreDefinedGradient preDefinedGradient) {
		final List<PreDefinedGradient> gradients = getLoadedPreDefinedGradients();
		Valid.checkBoolean(gradients.contains(preDefinedGradient), "Cannot find " + preDefinedGradient.getName() + " gradient!");
		loadedPreDefinedGradients.remove(preDefinedGradient);
		preDefinedGradient.getSettings().delete();
		Common.log("[-] Unloaded " + preDefinedGradient.getName() + " successfully!");
	}

	/**
	 * Finds a pre-defined gradient instance by its name.
	 *
	 * @param name the name of the gradient to find.
	 */
	public PreDefinedGradient findPreDefinedGradientByName(final String name) {
		for (final PreDefinedGradient gradient : getLoadedPreDefinedGradients())
			if (gradient.getName().equalsIgnoreCase(name))
				return gradient;
		return null;
	}

	/**
	 * Gets all the names of the pre-gradients.
	 */
	public List<String> getPreDefinedGradientNames() {
		return Common.convert(getLoadedPreDefinedGradients(), PreDefinedGradient::getName);
	}
}
