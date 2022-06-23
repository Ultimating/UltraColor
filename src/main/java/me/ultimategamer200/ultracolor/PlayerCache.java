package me.ultimategamer200.ultracolor;

import lombok.Getter;
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
		this.setPathPrefix(uuid.toString());

		this.uuid = uuid;
		loadConfiguration(NO_DEFAULT, FoConstants.File.DATA);
	}

	@Override
	protected void onLoad() {
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

	public CompChatColor setCustomGradient1(final CompChatColor gradients) {
		this.customGradient1 = gradients;

		this.save();
		return gradients;
	}

	public CompChatColor setCustomGradient2(final CompChatColor gradients) {
		this.customGradient2 = gradients;

		this.save();
		return gradients;
	}

	public void setChatCustomGradient1(final CompChatColor gradients) {
		this.chatCustomGradient1 = gradients;
		this.save();
	}

	public void setChatCustomGradient2(final CompChatColor gradients) {
		this.chatCustomGradient2 = gradients;
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
		this.set("Chat_Color", this.chatColor);
		this.set("Name_Color", this.nameColor);
		this.set("Name_Format", this.nameFormat);
		this.set("Chat_Format", this.chatFormat);
		this.set("First_Custom_Gradient", this.customGradient1);
		this.set("Second_Custom_Gradient", this.customGradient2);
		this.set("Chat_First_Custom_Gradient", this.chatCustomGradient1);
		this.set("Chat_Second_Custom_Gradient", this.chatCustomGradient2);
		this.set("Nickname", this.nickName);
		this.set("Colored_Nickname", this.coloredNickName);
		this.set("Chat_Rainbow_Colors", this.chatRainbowColors);
		this.set("Name_Rainbow_Colors", this.nameRainbowColors);
	}
}
