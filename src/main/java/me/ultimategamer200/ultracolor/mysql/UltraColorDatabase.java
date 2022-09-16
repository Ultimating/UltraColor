package me.ultimategamer200.ultracolor.mysql;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
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

	private final String tableName = "UltraColor";

	private UltraColorDatabase() {
		addVariable("table", this.tableName);
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

		if (data.getChatColor() != null) map.put(DataField.CHAT_COLOR.getIdentifier(), data.getChatColor());
		if (data.getNameColor() != null) map.put(DataField.NAME_COLOR.getIdentifier(), data.getNameColor());
		if (data.getChatFormat() != null) map.put(DataField.CHAT_FORMAT.getIdentifier(), data.getChatFormat());
		if (data.getNameFormat() != null) map.put(DataField.NAME_FORMAT.getIdentifier(), data.getNameFormat());

		if (data.getCustomGradient1() != null)
			map.put(DataField.FIRST_NAME_GRADIENT_HEX.getIdentifier(), data.getCustomGradient1());
		if (data.getCustomGradient2() != null)
			map.put(DataField.SECOND_NAME_GRADIENT_HEX.getIdentifier(), data.getCustomGradient2());
		if (data.getChatCustomGradient1() != null)
			map.put(DataField.FIRST_CHAT_GRADIENT_HEX.getIdentifier(), data.getChatCustomGradient1());
		if (data.getChatCustomGradient2() != null)
			map.put(DataField.SECOND_CHAT_GRADIENT_HEX.getIdentifier(), data.getChatCustomGradient2());

		map.put(DataField.CHAT_RAINBOW_COLORS.getIdentifier(), data.isChatRainbowColors());
		map.put(DataField.NAME_RAINBOW_COLORS.getIdentifier(), data.isNameRainbowColors());
		map.put(DataField.NICKNAME.getIdentifier(), data.getNickName());
		map.put(DataField.COLORED_NICKNAME.getIdentifier(), data.getColoredNickName());
		return map;
	}

	/**
	 * Is the player uuid specified stored?
	 *
	 * @param uuid
	 * @return
	 * @throws SQLException
	 */
	public boolean isPlayerStored(final UUID uuid) throws SQLException {
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

	public boolean isUpdateForPlayerNeeded(final UUID uuid) throws SQLException {
		if (!isPlayerStored(uuid)) return true;

		final PlayerCache pCache = PlayerCache.fromUUID(uuid);
		SerializedMap data = this.onSave(pCache);

		// Iterate through the data fields of the DB.
		for (final DataField dataField : DataField.values()) {
			final String identifier = dataField.getIdentifier();
			final Class<?> dataFieldClass = dataField.getDataFieldClass();

			Common.log("Identifier: " + identifier);
			Common.log("Class: " + dataFieldClass);

			final Object storedValue = DataField.getDataValueStored(identifier, dataFieldClass, pCache.saveToMap());
			Common.log("Stored Value: " + storedValue);

			final Object valueInDatabase = DataField.getDataValueStored(identifier, dataFieldClass, data);
			Common.log("Value In DB: " + valueInDatabase);

			if (!storedValue.equals(valueInDatabase)) {
				Common.log("Can update.");
				return true;
			}
		}

		return false;
	}

	/**
	 * Gets the stored player in the database by their UUID.
	 *
	 * @param uuid
	 * @return An offline player instance in case the player is offline or null if they can't be found.
	 * @throws SQLException
	 */
	public OfflinePlayer getStoredPlayerByUUID(final UUID uuid) throws SQLException {
		if (!isPlayerStored(uuid))
			return null;

		ResultSet resultSet = getResultSetOfUUID(uuid);
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

		return data.getString(DataField.COLORED_NICKNAME.getIdentifier(), "none");
	}

	public ResultSet getResultSetOfUUID(final UUID uuid) throws SQLException {
		ResultSet resultSet = this.query("SELECT * FROM {table} WHERE UUID= '" + uuid + "'");
		resultSet.next();
		return resultSet;
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

		public static Object getDataValueStored(final String identifier, final Class<?> dataFieldClass, final SerializedMap dataMap) {
			final String className = dataFieldClass.getName();

			if (className.equalsIgnoreCase(CompChatColor.class.getName())) {
				return dataMap.get(identifier, CompChatColor.class).getName();
			} else if (className.equalsIgnoreCase(String.class.getName()))
				return dataMap.getString(identifier);
			else if (className.equalsIgnoreCase(Boolean.class.getName()))
				return dataMap.getBoolean(identifier);

			Common.throwError(new NullPointerException(), "Invalid data field class " + className + " specified.",
					"Data field: " + identifier);
			return null;
		}
	}
}
