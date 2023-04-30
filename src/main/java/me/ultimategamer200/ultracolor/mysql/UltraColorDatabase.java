package me.ultimategamer200.ultracolor.mysql;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.ultimategamer200.ultracolor.PlayerCache;
import me.ultimategamer200.ultracolor.settings.Settings;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.collection.SerializedMap;
import org.mineacademy.fo.database.SimpleFlatDatabase;
import org.mineacademy.fo.remain.CompChatColor;
import org.mineacademy.fo.remain.Remain;

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
		final CompChatColor chatColor = map.get(DataField.CHAT_COLOR.getIdentifier(), CompChatColor.class);
		final CompChatColor chatFormat = map.get(DataField.CHAT_FORMAT.getIdentifier(), CompChatColor.class);
		final CompChatColor nameColor = map.get(DataField.NAME_COLOR.getIdentifier(), CompChatColor.class);
		final ChatColor nameFormat = map.get(DataField.NAME_FORMAT.getIdentifier(), ChatColor.class);

		final CompChatColor firstCustomGradient = map.get(DataField.FIRST_NAME_GRADIENT_HEX.getIdentifier(), CompChatColor.class);
		final CompChatColor secondCustomGradient = map.get(DataField.SECOND_NAME_GRADIENT_HEX.getIdentifier(), CompChatColor.class);
		final CompChatColor firstChatCustomGradient = map.get(DataField.FIRST_CHAT_GRADIENT_HEX.getIdentifier(), CompChatColor.class);
		final CompChatColor secondChatCustomGradient = map.get(DataField.SECOND_CHAT_GRADIENT_HEX.getIdentifier(), CompChatColor.class);

		final String nickName = map.getString(DataField.NICKNAME.getIdentifier(), "none");
		final String coloredNickname = map.getString(DataField.COLORED_NICKNAME.getIdentifier(), "none");
		final Boolean chatRainbowColors = map.getBoolean(DataField.CHAT_RAINBOW_COLORS.getIdentifier(), false);
		final Boolean nameRainbowColors = map.getBoolean(DataField.NAME_RAINBOW_COLORS.getIdentifier(), false);

		if (chatColor != null) data.setChatColor(chatColor);
		if (chatFormat != null) data.setChatFormat(chatFormat);
		if (nameColor != null) data.setNameColor(nameColor);
		if (nameFormat != null) data.setNameFormat(nameFormat);

		if (firstCustomGradient != null) data.setCustomGradientOne(firstCustomGradient);
		if (secondCustomGradient != null) data.setCustomGradientTwo(secondCustomGradient);

		if (firstChatCustomGradient != null) data.setChatCustomGradientOne(firstChatCustomGradient);
		if (secondChatCustomGradient != null) data.setChatCustomGradientTwo(secondChatCustomGradient);

		data.setChatRainbowColors(chatRainbowColors);
		data.setNameRainbowColors(nameRainbowColors);
		data.setNickName(nickName);
		data.setColoredNickName(coloredNickname);
		Common.log("Database data loaded.");
	}

	/**
	 * Communicates to save the player data to the database.
	 */
	@Override
	protected SerializedMap onSave(PlayerCache data) {
		final SerializedMap map = new SerializedMap();

		if (data.getChatColor() != null) map.put(DataField.CHAT_COLOR.getIdentifier(), data.getChatColor());
		if (data.getNameColor() != null) map.put(DataField.NAME_COLOR.getIdentifier(), data.getNameColor());
		if (data.getChatFormat() != null) map.put(DataField.CHAT_FORMAT.getIdentifier(), data.getChatFormat());
		if (data.getNameFormat() != null) map.put(DataField.NAME_FORMAT.getIdentifier(), data.getNameFormat());

		if (data.getCustomGradientOne() != null)
			map.put(DataField.FIRST_NAME_GRADIENT_HEX.getIdentifier(), data.getCustomGradientOne());
		if (data.getCustomGradientTwo() != null)
			map.put(DataField.SECOND_NAME_GRADIENT_HEX.getIdentifier(), data.getCustomGradientTwo());
		if (data.getChatCustomGradientOne() != null)
			map.put(DataField.FIRST_CHAT_GRADIENT_HEX.getIdentifier(), data.getChatCustomGradientOne());
		if (data.getChatCustomGradientTwo() != null)
			map.put(DataField.SECOND_CHAT_GRADIENT_HEX.getIdentifier(), data.getChatCustomGradientTwo());

		map.put(DataField.CHAT_RAINBOW_COLORS.getIdentifier(), data.isChatRainbowColors());
		map.put(DataField.NAME_RAINBOW_COLORS.getIdentifier(), data.isNameRainbowColors());
		map.put(DataField.NICKNAME.getIdentifier(), data.getNickName());
		map.put(DataField.COLORED_NICKNAME.getIdentifier(), data.getColoredNickName());
		return map;
	}

	/**
	 * Is the player uuid specified stored?
	 */
	public boolean isPlayerStored(final UUID uuid) throws SQLException {
		if (uuid == null)
			throw new NullPointerException("uuid is marked non-null but is null");
		else {
			ResultSet resultSet = selectUser(uuid);
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
		if (!isPlayerStored(uuid)) return null;

		ResultSet resultSet = selectUser(uuid);
		resultSet.next();
		return Remain.getOfflinePlayerByUUID(UUID.fromString(resultSet.getString("UUID")));
	}

	/**
	 * Gets the player's nickname if stored. Otherwise, "None" would be returned.
	 */
	public String getStoredNick(final OfflinePlayer player) throws SQLException {
		final UUID uuid = player.getUniqueId();
		if (getStoredPlayerByUUID(uuid) == null) return "";

		ResultSet resultSet = selectUser(uuid);
		String dataRaw = resultSet.next() ? resultSet.getString("Data") : "{}";
		SerializedMap data = SerializedMap.fromJson(dataRaw);

		return data.getString(DataField.COLORED_NICKNAME.getIdentifier(), "none");
	}

	public ResultSet selectUser(final UUID uuid) {
		return this.query("SELECT * FROM {table} WHERE UUID= '" + uuid + "'");
	}

	/**
	 * How long in days should we wait to purge a player's data since they were last online?
	 */
	@Override
	protected int getExpirationDays() {
		return Settings.Database.EXPIRY_DAYS;
	}

	@RequiredArgsConstructor
	public enum DataField {
		CHAT_COLOR("Chat_Color", CompChatColor.class),
		NAME_COLOR("Name_Color", CompChatColor.class),
		CHAT_FORMAT("Chat_Format", CompChatColor.class),
		NAME_FORMAT("Name_Format", CompChatColor.class),
		FIRST_NAME_GRADIENT_HEX("First_Custom_Gradient", CompChatColor.class),
		SECOND_NAME_GRADIENT_HEX("Second_Custom_Gradient", CompChatColor.class),
		FIRST_CHAT_GRADIENT_HEX("Chat_First_Custom_Gradient", CompChatColor.class),
		SECOND_CHAT_GRADIENT_HEX("Chat_Second_Custom_Gradient", CompChatColor.class),
		CHAT_RAINBOW_COLORS("Chat_Rainbow_Colors", Boolean.class),
		NAME_RAINBOW_COLORS("Name_Rainbow_Colors", Boolean.class),
		NICKNAME("Nickname", String.class),
		COLORED_NICKNAME("Colored_Nickname", String.class);

		@Getter
		private final String identifier;

		@Getter
		private final Class<?> dataFieldClass;
	}
}
