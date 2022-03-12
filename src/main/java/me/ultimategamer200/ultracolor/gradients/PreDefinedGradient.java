package me.ultimategamer200.ultracolor.gradients;

import lombok.Getter;

/**
 * A pre-gradient is a pre-made gradient that admins can create for players to use in either chat, name, or both!
 * These gradients only work for those 1.16+
 */
public class PreDefinedGradient {
	/**
	 * The name of the gradient.
	 */
	private final String name;

	/**
	 * The settings file this gradient belongs to.
	 */
	@Getter
	private final PreDefinedGradientSettings settings;

	public PreDefinedGradient(final String name) {
		this.name = name;
		this.settings = createSettings();
	}

	protected PreDefinedGradientSettings createSettings() {
		return new PreDefinedGradientSettings(this);
	}

	public String getName() {
		return name;
	}
}
