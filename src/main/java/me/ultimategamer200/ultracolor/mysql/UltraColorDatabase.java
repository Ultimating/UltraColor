package me.ultimategamer200.ultracolor.mysql;

import lombok.Getter;
import me.ultimategamer200.ultracolor.PlayerCache;
import me.ultimategamer200.ultracolor.settings.Settings;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.collection.SerializedMap;
import org.mineacademy.fo.database.SimpleFlatDatabase;
import org.mineacademy.fo.remain.CompChatColor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * The MySQL database instance class.
 */
public final class UltraColorDatabase extends SimpleFlatDatabase<PlayerCache> {
	/**
	 * Gets the database instance.
	 */
	@Getter
	private static final UltraColorDatabase instance = new UltraColorDatabase();

	private UltraColorDatabase() {
		addVariable("table", "UltraColor");
	}

	/**
	 * Communicates to the server to load data from the database and save it to its UltraColor data.db file.
	 */
	@Override
	protected void onLoad(SerializedMap map, PlayerCache data) {
		final CompChatColor chatColor = map.get("Chat_Color", CompChatColor.class);
		final CompChatColor chatFormat = map.get("Chat_Format", CompChatColor.class);
		final CompChatColor nameColor = map.get("Name_Color", CompChatColor.class);
		final ChatColor nameFormat = map.get("Name_Format", ChatColor.class);

		final CompChatColor firstCustomGradient = map.get("First_Custom_Gradient", CompChatColor.class);
		final CompChatColor secondCustomGradient = map.get("Second_Custom_Gradient", CompChatColor.class);
		final CompChatColor firstChatCustomGradient = map.get("Chat_First_Custom_Gradient", CompChatColor.class);
		final CompChatColor secondChatCustomGradient = map.get("Chat_Second_Custom_Gradient", CompChatColor.class);

		final String nickName = map.getString("Nickname", "none");
		final String coloredNickname = map.getString("Colored_Nickname", "none");
		final Boolean chatRainbowColors = map.getBoolean("Chat_Rainbow_Colors", false);
		final Boolean nameRainbowColors = map.getBoolean("Name_Rainbow_Colors", false);

		if (chatColor != null) Common.runLater(() -> data.setChatColor(chatColor));
		if (chatFormat != null) Common.runLater(() -> data.setChatFormat(chatFormat));
		if (nameColor != null) Common.runLater(() -> data.setNameColor(nameColor));
		if (nameFormat != null) Common.runLater(() -> data.setNameFormat(nameFormat));

		if (firstCustomGradient != null) Common.runLater(() -> data.setCustomGradient1(firstCustomGradient));
		if (secondCustomGradient != null) Common.runLater(() -> data.setCustomGradient2(secondCustomGradient));

		if (firstChatCustomGradient != null)
			Common.runLater(() -> data.setChatCustomGradient1(firstChatCustomGradient));
		if (secondChatCustomGradient != null)
			Common.runLater(() -> data.setChatCustomGradient2(secondChatCustomGradient));

		Common.runLater(() -> {
			data.setChatRainbowColors(chatRainbowColors);
			data.setNameRainbowColors(nameRainbowColors);
			data.setNickName(nickName);
			data.setColoredNickName(coloredNickname);
		});
	}

	/**
	 * Communicates to save the player data to the database.
	 */
	@Override
	protected SerializedMap onSave(PlayerCache data) {
		final SerializedMap map = new SerializedMap();

		if (data.getChatColor() != null)
			map.put("Chat_Color", data.getChatColor());
		if (data.getNameColor() != null)
			map.put("Name_Color", data.getNameColor());
		if (data.getChatFormat() != null)
			map.put("Chat_Format", data.getChatFormat());
		if (data.getNameFormat() != null)
			map.put("Name_Format", data.getNameFormat());

		if (data.getCustomGradient1() != null)
			map.put("First_Custom_Gradient", data.getCustomGradient1());
		if (data.getCustomGradient2() != null)
			map.put("Second_Custom_Gradient", data.getCustomGradient2());
		if (data.getChatCustomGradient1() != null)
			map.put("Chat_First_Custom_Gradient", data.getChatCustomGradient1());
		if (data.getChatCustomGradient2() != null)
			map.put("Chat_Second_Custom_Gradient", data.getChatCustomGradient2());

		map.put("Chat_Rainbow_Colors", data.isChatRainbowColors());
		map.put("Name_Rainbow_Colors", data.isNameRainbowColors());
		map.put("Nickname", data.getNickName());
		map.put("Colored_Nickname", data.getColoredNickName());
		return map;
	}

	/**
	 * Is the player uuid specified stored?
	 *
	 * @param uuid
	 * @return
	 * @throws SQLException
	 */
	public boolean isStored(final UUID uuid) throws SQLException {
		if (uuid == null)
			throw new NullPointerException("uuid is marked non-null but is null");
		else {
			ResultSet resultSet = this.query("SELECT * FROM {table} WHERE UUID= '" + uuid + "'");
			if (resultSet == null)
				return false;
			else if (resultSet.next())
				return resultSet.getString("UUID") != null;
			else
				return false;
		}
	}

	/**
	 * Gets the stored player in the database by their UUID.
	 *
	 * @param uuid
	 * @return An offline player instance in case the player is offline or null if they can't be found.
	 * @throws SQLException
	 */
	public OfflinePlayer getStoredPlayerByUUID(final UUID uuid) throws SQLException {
		if (!isStored(uuid))
			return null;

		ResultSet resultSet = this.query("SELECT * FROM {table} WHERE UUID= '" + uuid + "'");
		resultSet.next();
		return Bukkit.getOfflinePlayer(UUID.fromString(resultSet.getString("UUID")));
	}

	/**
	 * Gets the player's nickname if stored. Otherwise, "None" would be returned.
	 *
	 * @param player
	 * @return
	 * @throws SQLException
	 */
	public String getStoredNick(final OfflinePlayer player) throws SQLException {
		if (getStoredPlayerByUUID(player.getUniqueId()) == null)
			return "";

		ResultSet resultSet = this.query("SELECT * FROM {table} WHERE UUID= '" + player.getUniqueId() + "'");
		String dataRaw = resultSet.next() ? resultSet.getString("Data") : "{}";
		SerializedMap data = SerializedMap.fromJson(dataRaw);

		return data.getString("Colored_Nickname", "none");
	}

	/**
	 * How long in days should we wait to purge a player's data since they were last online?
	 */
	@Override
	protected int getExpirationDays() {
		return Settings.Database.EXPIRY_DAYS;
	}
}
