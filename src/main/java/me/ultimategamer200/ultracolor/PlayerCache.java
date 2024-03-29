package me.ultimategamer200.ultracolor;

import lombok.Getter;
import me.ultimategamer200.ultracolor.mysql.UltraColorDatabase;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.mineacademy.fo.collection.expiringmap.ExpiringMap;
import org.mineacademy.fo.constants.FoConstants;
import org.mineacademy.fo.remain.CompChatColor;
import org.mineacademy.fo.settings.YamlConfig;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * This class stores player data inside the plugin's data.db file.
 */
@Getter
public final class PlayerCache extends YamlConfig {
	/**
	 * A special map that works with the data.db file, but never changes the file at all. It's an ExpiringMap to keep it fresh and clean.
	 */
	public static final ExpiringMap<UUID, PlayerCache> cacheMap = ExpiringMap.builder().variableExpiration()
			.expiration(30, TimeUnit.MINUTES).build();

	/**
	 * The UUID of the player this data belongs to.
	 */
	private final UUID uuid;

	/**
	 * The name of the player.
	 */
	private String playerName;

	/**
	 * The chat color the player selected.
	 */
	private CompChatColor chatColor;

	/**
	 * The name format the player selected.
	 */
	private ChatColor nameFormat;

	/**
	 * The chat format the player selected.
	 */
	private CompChatColor chatFormat;

	/**
	 * The name color the player selected.
	 */
	private CompChatColor nameColor;

	/**
	 * The first hex color of a player's name gradient.
	 */
	private CompChatColor customGradientOne;

	/**
	 * The second hex color of a player's name gradient.
	 */
	private CompChatColor customGradientTwo;

	/**
	 * The first hex color of a player's chat gradient.
	 */
	private CompChatColor chatCustomGradientOne;

	/**
	 * The second hex color of a player's chat gradient.
	 */
	private CompChatColor chatCustomGradientTwo;

	/**
	 * The player's nickname with no color.
	 */
	private String nickName;

	/**
	 * The colored nickname of the player.
	 */
	private String coloredNickName;

	/**
	 * Does this player have chat rainbow colors selected?
	 */
	private boolean chatRainbowColors;

	/**
	 * Does this player have name rainbow colors selected?
	 */
	private boolean nameRainbowColors;

	// Creates a new data section for the player if they don't exist.
	private PlayerCache(final UUID uuid) {
		// This will prepend this cache with the players unique id just like you use pathPrefix in the Settings class.
		this.setPathPrefix(uuid.toString());

		this.uuid = uuid;
		loadConfiguration(NO_DEFAULT, FoConstants.File.DATA);
	}

	@Override
	protected void onLoad() {
		this.playerName = getString("Player_Name");
		this.chatColor = get(UltraColorDatabase.DataField.CHAT_COLOR.getIdentifier(), CompChatColor.class);
		this.nameColor = get(UltraColorDatabase.DataField.NAME_COLOR.getIdentifier(), CompChatColor.class);
		this.nameFormat = get(UltraColorDatabase.DataField.NAME_FORMAT.getIdentifier(), ChatColor.class);
		this.chatFormat = get(UltraColorDatabase.DataField.CHAT_FORMAT.getIdentifier(), CompChatColor.class);
		this.customGradientOne = get(UltraColorDatabase.DataField.FIRST_NAME_GRADIENT_HEX.getIdentifier(), CompChatColor.class);
		this.customGradientTwo = get(UltraColorDatabase.DataField.SECOND_NAME_GRADIENT_HEX.getIdentifier(), CompChatColor.class);
		this.chatCustomGradientOne = get(UltraColorDatabase.DataField.FIRST_CHAT_GRADIENT_HEX.getIdentifier(), CompChatColor.class);
		this.chatCustomGradientTwo = get(UltraColorDatabase.DataField.SECOND_CHAT_GRADIENT_HEX.getIdentifier(), CompChatColor.class);
		this.nickName = getString(UltraColorDatabase.DataField.NICKNAME.getIdentifier(), "none");
		this.coloredNickName = getString(UltraColorDatabase.DataField.COLORED_NICKNAME.getIdentifier(), "none");
		this.chatRainbowColors = getBoolean(UltraColorDatabase.DataField.CHAT_RAINBOW_COLORS.getIdentifier(), false);
		this.nameRainbowColors = getBoolean(UltraColorDatabase.DataField.NAME_RAINBOW_COLORS.getIdentifier(), false);
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
		this.save();
	}

	public void setChatColor(final CompChatColor chatColor) {
		this.chatColor = chatColor;
		this.save();
	}

	public void setNameColor(final CompChatColor nameColor) {
		this.nameColor = nameColor;
		this.save();
	}

	public void setNameFormat(final ChatColor nameFormat) {
		this.nameFormat = nameFormat;
		this.save();
	}

	public void setChatFormat(final CompChatColor chatFormat) {
		this.chatFormat = chatFormat;
		this.save();
	}

	public void setCustomGradientOne(final CompChatColor gradients) {
		this.customGradientOne = gradients;
		this.save();
	}

	public void setCustomGradientTwo(final CompChatColor gradients) {
		this.customGradientTwo = gradients;
		this.save();
	}

	public void setChatCustomGradientOne(final CompChatColor gradients) {
		this.chatCustomGradientOne = gradients;
		this.save();
	}

	public void setChatCustomGradientTwo(final CompChatColor gradients) {
		this.chatCustomGradientTwo = gradients;
		this.save();
	}

	public void clearGradients(final String type) {
		if (type.equalsIgnoreCase("chat")) {
			this.chatCustomGradientOne = null;
			this.chatCustomGradientTwo = null;
		} else {
			this.customGradientOne = null;
			this.customGradientTwo = null;
		}

		this.save();
	}

	public void setNickName(final String nickName) {
		this.nickName = nickName;
		this.save();
	}

	public void setColoredNickName(final String coloredNickName) {
		this.coloredNickName = coloredNickName;
		this.save();
	}

	public void setChatRainbowColors(boolean chatRainbowColors) {
		this.chatRainbowColors = chatRainbowColors;
		this.save();
	}

	public void setNameRainbowColors(boolean nameRainbowColors) {
		this.nameRainbowColors = nameRainbowColors;
		this.save();
	}

	// --------------------------------------------------------------------------------------------------------------
	// Static methods below
	// --------------------------------------------------------------------------------------------------------------

	/**
	 * Gets the cache by online player instance.
	 */
	public static PlayerCache fromPlayer(final Player player) {
		return fromUUID(player.getUniqueId());
	}

	/**
	 * Gets the cache of any player.
	 */
	public static PlayerCache fromOfflinePlayer(final OfflinePlayer player) {
		return fromUUID(player.getUniqueId());
	}

	/**
	 * Gets the cache by UUID (Not recommended calling this method unless it's necessary!)
	 */
	public static PlayerCache fromUUID(final UUID uuid) {
		synchronized (cacheMap) {
			PlayerCache pCache = cacheMap.get(uuid);

			if (pCache == null) {
				pCache = new PlayerCache(uuid);
				cacheMap.put(uuid, pCache);
			}

			return pCache;
		}
	}

	// Clears all the cache map data.
	public static void clearAllData() {
		cacheMap.clear();
	}

	@Override
	protected void onSave() {
		this.set("Player_Name", this.playerName);
		this.set(UltraColorDatabase.DataField.CHAT_COLOR.getIdentifier(), this.chatColor);
		this.set(UltraColorDatabase.DataField.NAME_COLOR.getIdentifier(), this.nameColor);
		this.set(UltraColorDatabase.DataField.NAME_FORMAT.getIdentifier(), this.nameFormat);
		this.set(UltraColorDatabase.DataField.CHAT_FORMAT.getIdentifier(), this.chatFormat);
		this.set(UltraColorDatabase.DataField.FIRST_NAME_GRADIENT_HEX.getIdentifier(), this.customGradientOne);
		this.set(UltraColorDatabase.DataField.SECOND_NAME_GRADIENT_HEX.getIdentifier(), this.customGradientTwo);
		this.set(UltraColorDatabase.DataField.FIRST_CHAT_GRADIENT_HEX.getIdentifier(), this.chatCustomGradientOne);
		this.set(UltraColorDatabase.DataField.SECOND_CHAT_GRADIENT_HEX.getIdentifier(), this.chatCustomGradientTwo);
		this.set(UltraColorDatabase.DataField.NICKNAME.getIdentifier(), this.nickName);
		this.set(UltraColorDatabase.DataField.COLORED_NICKNAME.getIdentifier(), this.coloredNickName);
		this.set(UltraColorDatabase.DataField.CHAT_RAINBOW_COLORS.getIdentifier(), this.chatRainbowColors);
		this.set(UltraColorDatabase.DataField.NAME_RAINBOW_COLORS.getIdentifier(), this.nameRainbowColors);
	}
}
