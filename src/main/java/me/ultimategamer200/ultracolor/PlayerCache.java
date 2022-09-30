package me.ultimategamer200.ultracolor;

import lombok.Getter;
import me.ultimategamer200.ultracolor.mysql.UltraColorDatabase;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.mineacademy.fo.Common;
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
	private PlayerCache(final UUID uuid, final String playerName, final boolean loadData) {
		// This will prepend this cache with the players unique id just like you use pathPrefix in the Settings class.
		this.setPathPrefix(uuid.toString());

		this.uuid = uuid;
		this.playerName = playerName;

		if (loadData) this.loadConfiguration(NO_DEFAULT, FoConstants.File.DATA);
	}

	@Override
	protected void onLoad() {
		Common.log("Loading data");
		this.playerName = getString("Player_Name");
		this.chatColor = get(UltraColorDatabase.DataField.CHAT_COLOR.getIdentifier(), CompChatColor.class);
		this.nameColor = get(UltraColorDatabase.DataField.NAME_COLOR.getIdentifier(), CompChatColor.class);
		this.nameFormat = get(UltraColorDatabase.DataField.NAME_FORMAT.getIdentifier(), ChatColor.class);
		this.chatFormat = get(UltraColorDatabase.DataField.CHAT_FORMAT.getIdentifier(), CompChatColor.class);
		this.customGradient1 = get(UltraColorDatabase.DataField.FIRST_NAME_GRADIENT_HEX.getIdentifier(), CompChatColor.class);
		this.customGradient2 = get(UltraColorDatabase.DataField.SECOND_NAME_GRADIENT_HEX.getIdentifier(), CompChatColor.class);
		this.chatCustomGradient1 = get(UltraColorDatabase.DataField.FIRST_CHAT_GRADIENT_HEX.getIdentifier(), CompChatColor.class);
		this.chatCustomGradient2 = get(UltraColorDatabase.DataField.SECOND_CHAT_GRADIENT_HEX.getIdentifier(), CompChatColor.class);
		this.nickName = getString(UltraColorDatabase.DataField.NICKNAME.getIdentifier(), "none");
		this.coloredNickName = getString(UltraColorDatabase.DataField.COLORED_NICKNAME.getIdentifier(), "none");
		this.chatRainbowColors = getBoolean(UltraColorDatabase.DataField.CHAT_RAINBOW_COLORS.getIdentifier(), false);
		this.nameRainbowColors = getBoolean(UltraColorDatabase.DataField.NAME_RAINBOW_COLORS.getIdentifier(), false);

		Common.log("Colored Nickname: " + this.coloredNickName);
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
		return fromUUID(player.getUniqueId(), player.getName());
	}

	/**
	 * Gets the cache of any player.
	 */
	public static PlayerCache fromOfflinePlayer(final OfflinePlayer player) {
		return fromUUID(player.getUniqueId(), player.getName());
	}

	/**
	 * Gets the cache by UUID (Not recommended calling this method unless it's necessary!)
	 */
	public static PlayerCache fromUUID(final UUID uuid, final String playerName) {
		PlayerCache pCache = cacheMap.get(uuid);

		if (pCache == null) {
			pCache = new PlayerCache(uuid, playerName, false);
			cacheMap.put(uuid, pCache);
		}

		return pCache;
	}

	/**
	 * Similar to above method, only this time, you can choose to load data when new player cache is formed.
	 */
	public static PlayerCache fromUUID(final UUID uuid, final String playerName, final boolean loadData) {
		return new PlayerCache(uuid, playerName, loadData);
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
		this.set(UltraColorDatabase.DataField.FIRST_NAME_GRADIENT_HEX.getIdentifier(), this.customGradient1);
		this.set(UltraColorDatabase.DataField.SECOND_NAME_GRADIENT_HEX.getIdentifier(), this.customGradient2);
		this.set(UltraColorDatabase.DataField.FIRST_CHAT_GRADIENT_HEX.getIdentifier(), this.chatCustomGradient1);
		this.set(UltraColorDatabase.DataField.SECOND_CHAT_GRADIENT_HEX.getIdentifier(), this.chatCustomGradient2);
		this.set(UltraColorDatabase.DataField.NICKNAME.getIdentifier(), this.nickName);
		this.set(UltraColorDatabase.DataField.COLORED_NICKNAME.getIdentifier(), this.coloredNickName);
		this.set(UltraColorDatabase.DataField.CHAT_RAINBOW_COLORS.getIdentifier(), this.chatRainbowColors);
		this.set(UltraColorDatabase.DataField.NAME_RAINBOW_COLORS.getIdentifier(), this.nameRainbowColors);
	}
}
