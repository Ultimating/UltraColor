package me.ultimategamer200.ultracolor;

import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.mineacademy.fo.collection.SerializedMap;
import org.mineacademy.fo.collection.expiringmap.ExpiringMap;
import org.mineacademy.fo.constants.FoConstants;
import org.mineacademy.fo.remain.CompChatColor;
import org.mineacademy.fo.settings.YamlSectionConfig;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * This class stores player data inside the plugin's data.db file.
 */
@Getter
public final class PlayerCache extends YamlSectionConfig {
	/**
	 * A special map that works with the data.db file, but never changes the file at all. It's an ExpiringMap to keep it fresh and clean.
	 */
	public static final ExpiringMap<UUID, PlayerCache> cacheMap = ExpiringMap.builder().expiration(30, TimeUnit.MINUTES).build();

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
	private CompChatColor customGradient1;

	/**
	 * The second hex color of a player's name gradient.
	 */
	private CompChatColor customGradient2;

	/**
	 * The first hex color of a player's chat gradient.
	 */
	private CompChatColor chatCustomGradient1;

	/**
	 * The second hex color of a player's chat gradient.
	 */
	private CompChatColor chatCustomGradient2;

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
		super(uuid.toString());

		this.uuid = uuid;
		loadConfiguration(NO_DEFAULT, FoConstants.File.DATA);
	}

	@Override
	protected void onLoadFinish() {
		this.playerName = getString("Player_Name");
		this.chatColor = get("Chat_Color", CompChatColor.class);
		this.nameColor = get("Name_Color", CompChatColor.class);
		this.nameFormat = get("Name_Format", ChatColor.class);
		this.chatFormat = get("Chat_Format", CompChatColor.class);
		this.customGradient1 = get("First_Custom_Gradient", CompChatColor.class);
		this.customGradient2 = get("Second_Custom_Gradient", CompChatColor.class);
		this.chatCustomGradient1 = get("Chat_First_Custom_Gradient", CompChatColor.class);
		this.chatCustomGradient2 = get("Chat_Second_Custom_Gradient", CompChatColor.class);
		this.nickName = getString("Nickname", "none");
		this.coloredNickName = getString("Colored_Nickname", "none");
		this.chatRainbowColors = getBoolean("Chat_Rainbow_Colors", false);
		this.nameRainbowColors = getBoolean("Name_Rainbow_Colors", false);
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
		this.save("Player_Name", playerName);
	}

	public void setChatColor(final CompChatColor chatColor) {
		this.chatColor = chatColor;
		this.save("Chat_Color", chatColor);
	}

	public void setNameColor(final CompChatColor nameColor) {
		this.nameColor = nameColor;
		this.save("Name_Color", nameColor);
	}

	public void setNameFormat(final ChatColor nameFormat) {
		this.nameFormat = nameFormat;
		this.save("Name_Format", nameFormat);
	}

	public void setChatFormat(final CompChatColor chatFormat) {
		this.chatFormat = chatFormat;
		this.save("Chat_Format", chatFormat);
	}

	public CompChatColor setCustomGradient1(final CompChatColor gradients) {
		this.customGradient1 = gradients;

		this.save("First_Custom_Gradient", gradients);
		return gradients;
	}

	public CompChatColor setCustomGradient2(final CompChatColor gradients) {
		this.customGradient2 = gradients;

		this.save("Second_Custom_Gradient", gradients);
		return gradients;
	}

	public void setChatCustomGradient1(final CompChatColor gradients) {
		this.chatCustomGradient1 = gradients;
		this.save("Chat_First_Custom_Gradient", gradients);
	}

	public void setChatCustomGradient2(final CompChatColor gradients) {
		this.chatCustomGradient2 = gradients;
		this.save("Chat_Second_Custom_Gradient", gradients);
	}

	public void setNickName(final String nickName) {
		this.nickName = nickName;
		this.save("Nickname", nickName);
	}

	public void setColoredNickName(final String coloredNickName) {
		this.coloredNickName = coloredNickName;
		this.save("Colored_Nickname", coloredNickName);
	}

	public void setChatRainbowColors(boolean chatRainbowColors) {
		this.chatRainbowColors = chatRainbowColors;
		this.save("Chat_Rainbow_Colors", chatRainbowColors);
	}

	public void setNameRainbowColors(boolean nameRainbowColors) {
		this.nameRainbowColors = nameRainbowColors;
		this.save("Name_Rainbow_Colors", nameRainbowColors);
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

	/**
	 * Serializes all player data to save them easily.
	 */
	@Override
	public SerializedMap serialize() {
		return SerializedMap.ofArray(
				"Player_Name", this.playerName,
				"Chat_Color", this.chatColor,
				"Name_Color", this.nameColor,
				"Name_Format", this.nameFormat,
				"Chat_Format", this.chatFormat,
				"First_Custom_Gradient", this.customGradient1,
				"Second_Custom_Gradient", this.customGradient2,
				"Chat_First_Custom_Gradient", this.chatCustomGradient1,
				"Chat_Second_Custom_Gradient", this.chatCustomGradient2,
				"Nickname", this.nickName,
				"Colored_Nickname", this.coloredNickName,
				"Chat_Rainbow_Colors", this.chatRainbowColors,
				"Name_Rainbow_Colors", this.nameRainbowColors);
	}
}
