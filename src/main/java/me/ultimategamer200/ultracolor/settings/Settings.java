package me.ultimategamer200.ultracolor.settings;

import org.bukkit.event.EventPriority;
import org.mineacademy.fo.remain.CompMaterial;
import org.mineacademy.fo.settings.SimpleSettings;

/**
 * The class that manages the settings.yml. Like the Localization class, each settings section is divided by their own subclass.
 */
public class Settings extends SimpleSettings {

	@Override
	protected int getConfigVersion() {
		return 1;
	}

	public static class Chat_Color_Menu_Items {
		public static String BLACK_ITEM;
		public static String DARK_BLUE_ITEM;
		public static String DARK_GREEN_ITEM;
		public static String DARK_AQUA_ITEM;
		public static String DARK_RED_ITEM;
		public static String DARK_PURPLE_ITEM;
		public static String ORANGE_ITEM;
		public static String GRAY_ITEM;
		public static String DARK_GRAY_ITEM;
		public static String BLUE_ITEM;
		public static String GREEN_ITEM;
		public static String AQUA_ITEM;
		public static String RED_ITEM;
		public static String LIGHT_PURPLE_ITEM;
		public static String YELLOW_ITEM;
		public static String WHITE_ITEM;
		public static String MAGIC_ITEM;
		public static String BOLD_ITEM;
		public static String ITALIC_ITEM;
		public static String UNDERLINE_ITEM;
		public static String STRIKETHROUGH_ITEM;
		public static String FORMAT_RESET_ITEM;
		public static String RAINBOW_ITEM;

		private static void init() {
			pathPrefix("Chat_Color_Items");

			BLACK_ITEM = getString("Black");
			DARK_BLUE_ITEM = getString("Dark_Blue");
			DARK_GREEN_ITEM = getString("Dark_Green");
			DARK_AQUA_ITEM = getString("Dark_Aqua");
			DARK_RED_ITEM = getString("Dark_Red");
			DARK_PURPLE_ITEM = getString("Dark_Purple");
			ORANGE_ITEM = getString("Orange");
			GRAY_ITEM = getString("Gray");
			DARK_GRAY_ITEM = getString("Dark_Gray");
			BLUE_ITEM = getString("Blue");
			GREEN_ITEM = getString("Green");
			AQUA_ITEM = getString("Aqua");
			RED_ITEM = getString("Red");
			LIGHT_PURPLE_ITEM = getString("Light_Purple");
			YELLOW_ITEM = getString("Yellow");
			WHITE_ITEM = getString("White");
			MAGIC_ITEM = getString("Magic_Format");
			BOLD_ITEM = getString("Bold_Format");
			ITALIC_ITEM = getString("Italic_Format");
			UNDERLINE_ITEM = getString("Underline_Format");
			STRIKETHROUGH_ITEM = getString("Strikethrough_Format");
			FORMAT_RESET_ITEM = getString("Reset_Format");
			RAINBOW_ITEM = getString("Rainbow");
		}
	}

	public static class Name_Color_Menu_Items {
		public static String BLACK_ITEM;
		public static String DARK_BLUE_ITEM;
		public static String DARK_GREEN_ITEM;
		public static String DARK_AQUA_ITEM;
		public static String DARK_RED_ITEM;
		public static String DARK_PURPLE_ITEM;
		public static String ORANGE_ITEM;
		public static String GRAY_ITEM;
		public static String DARK_GRAY_ITEM;
		public static String BLUE_ITEM;
		public static String GREEN_ITEM;
		public static String AQUA_ITEM;
		public static String RED_ITEM;
		public static String LIGHT_PURPLE_ITEM;
		public static String YELLOW_ITEM;
		public static String WHITE_ITEM;
		public static String MAGIC_ITEM;
		public static String BOLD_ITEM;
		public static String ITALIC_ITEM;
		public static String UNDERLINE_ITEM;
		public static String STRIKETHROUGH_ITEM;
		public static String FORMAT_RESET_ITEM;
		public static String RAINBOW_ITEM;

		private static void init() {
			pathPrefix("Name_Color_Items");

			BLACK_ITEM = getString("Black");
			DARK_BLUE_ITEM = getString("Dark_Blue");
			DARK_GREEN_ITEM = getString("Dark_Green");
			DARK_AQUA_ITEM = getString("Dark_Aqua");
			DARK_RED_ITEM = getString("Dark_Red");
			DARK_PURPLE_ITEM = getString("Dark_Purple");
			ORANGE_ITEM = getString("Orange");
			GRAY_ITEM = getString("Gray");
			DARK_GRAY_ITEM = getString("Dark_Gray");
			BLUE_ITEM = getString("Blue");
			GREEN_ITEM = getString("Green");
			AQUA_ITEM = getString("Aqua");
			RED_ITEM = getString("Red");
			LIGHT_PURPLE_ITEM = getString("Light_Purple");
			YELLOW_ITEM = getString("Yellow");
			WHITE_ITEM = getString("White");
			MAGIC_ITEM = getString("Magic_Format");
			BOLD_ITEM = getString("Bold_Format");
			ITALIC_ITEM = getString("Italic_Format");
			UNDERLINE_ITEM = getString("Underline_Format");
			STRIKETHROUGH_ITEM = getString("Strikethrough_Format");
			FORMAT_RESET_ITEM = getString("Reset_Format");
			RAINBOW_ITEM = getString("Rainbow");
		}
	}

	public static class Gradient_Color_Menu_Items {
		public static String CHAT_ITEM;
		public static String NAME_ITEM;
		public static String RESET_ITEM;
		public static String CUSTOM_ITEM;
		public static String FILL_ITEM;
		public static CompMaterial RETURN_ITEM;

		private static void init() {
			pathPrefix("Gradient_Color_Items");

			RESET_ITEM = getString("Reset");
			CUSTOM_ITEM = getString("Custom");
			FILL_ITEM = getString("Fill");

			CHAT_ITEM = getString("Chat");
			NAME_ITEM = getString("Name");
			RETURN_ITEM = getMaterial("Return");
		}
	}

	public static class Color_Settings {
		public static Boolean NAME_COLORS;
		public static Boolean CHAT_COLORS;
		public static Boolean NAME_RAINBOW_COLORS;
		public static Boolean CHAT_RAINBOW_COLORS;
		public static Boolean CHAT_HEX_COLORS;
		public static Boolean NAME_HEX_COLORS;
		public static Boolean CHAT_GRADIENT_COLORS;
		public static Boolean NAME_GRADIENT_COLORS;
		public static Boolean ALLOW_ONLY_CERTAIN_HEX_COLORS;
		public static String CHAT_FILL_BUTTON;
		public static String NAME_FILL_BUTTON;
		public static String CHAT_COLOR_BUTTON;
		public static String NAME_COLOR_BUTTON;

		private static void init() {
			pathPrefix("Color_Settings");

			NAME_COLORS = getBoolean("Name_Colors");
			CHAT_COLORS = getBoolean("Chat_Colors");
			NAME_RAINBOW_COLORS = getBoolean("Name_Rainbow_Colors");
			CHAT_RAINBOW_COLORS = getBoolean("Chat_Rainbow_Colors");

			CHAT_HEX_COLORS = getBoolean("Chat_Hex_Colors");
			NAME_HEX_COLORS = getBoolean("Name_Hex_Colors");
			NAME_GRADIENT_COLORS = getBoolean("Name_Gradient_Colors");
			CHAT_GRADIENT_COLORS = getBoolean("Chat_Gradient_Colors");

			CHAT_FILL_BUTTON = getString("Chat_Fill_Button");
			NAME_FILL_BUTTON = getString("Name_Fill_Button");
			ALLOW_ONLY_CERTAIN_HEX_COLORS = getBoolean("Allow_Only_Certain_Hex_Colors");

			CHAT_COLOR_BUTTON = getString("Chat_Color_Button");
			NAME_COLOR_BUTTON = getString("Name_Color_Button");
		}

		public static void setAllowOnlyCertainHexColors(final Boolean allowOnlyCertainHexColors) {
			set("Allow_Only_Certain_Hex_Colors", allowOnlyCertainHexColors);
		}
	}

	public static class Color_Settings_Name_Formats {
		public static Boolean BOLD_FORMAT;
		public static Boolean MAGIC_FORMAT;
		public static Boolean ITALIC_FORMAT;
		public static Boolean UNDERLINE_FORMAT;
		public static Boolean STRIKETHROUGH_FORMAT;

		private static void init() {
			pathPrefix("Color_Settings.Name_Formats");

			BOLD_FORMAT = getBoolean("Bold_Format_Enabled");
			MAGIC_FORMAT = getBoolean("Magic_Format_Enabled");
			ITALIC_FORMAT = getBoolean("Italic_Format_Enabled");
			UNDERLINE_FORMAT = getBoolean("Underline_Format_Enabled");
			STRIKETHROUGH_FORMAT = getBoolean("Strikethrough_Format_Enabled");
		}
	}

	public static class Color_Settings_Chat_Formats {
		public static Boolean BOLD_FORMAT;
		public static Boolean MAGIC_FORMAT;
		public static Boolean ITALIC_FORMAT;
		public static Boolean UNDERLINE_FORMAT;
		public static Boolean STRIKETHROUGH_FORMAT;

		private static void init() {
			pathPrefix("Color_Settings.Chat_Formats");

			BOLD_FORMAT = getBoolean("Bold_Format_Enabled");
			MAGIC_FORMAT = getBoolean("Magic_Format_Enabled");
			ITALIC_FORMAT = getBoolean("Italic_Format_Enabled");
			UNDERLINE_FORMAT = getBoolean("Underline_Format_Enabled");
			STRIKETHROUGH_FORMAT = getBoolean("Strikethrough_Format_Enabled");
		}
	}

	public static class Color_Settings_Chat_Colors {
		public static Boolean BLACK_COLOR_ENABLED;
		public static Boolean DARK_BLUE_COLOR_ENABLED;
		public static Boolean DARK_GREEN_COLOR_ENABLED;
		public static Boolean DARK_AQUA_COLOR_ENABLED;
		public static Boolean DARK_RED_COLOR_ENABLED;
		public static Boolean DARK_PURPLE_COLOR_ENABLED;
		public static Boolean ORANGE_COLOR_ENABLED;
		public static Boolean GRAY_COLOR_ENABLED;
		public static Boolean DARK_GRAY_COLOR_ENABLED;
		public static Boolean BLUE_COLOR_ENABLED;
		public static Boolean GREEN_COLOR_ENABLED;
		public static Boolean AQUA_COLOR_ENABLED;
		public static Boolean RED_COLOR_ENABLED;
		public static Boolean LIGHT_PURPLE_COLOR_ENABLED;
		public static Boolean YELLOW_COLOR_ENABLED;
		public static Boolean WHITE_COLOR_ENABLED;

		private static void init() {
			pathPrefix("Color_Settings.Chat_Color_Settings");

			BLACK_COLOR_ENABLED = getBoolean("Black_Color_Enabled");
			DARK_BLUE_COLOR_ENABLED = getBoolean("Dark_Blue_Color_Enabled");
			DARK_GREEN_COLOR_ENABLED = getBoolean("Dark_Green_Color_Enabled");
			DARK_AQUA_COLOR_ENABLED = getBoolean("Dark_Aqua_Color_Enabled");
			DARK_RED_COLOR_ENABLED = getBoolean("Dark_Red_Color_Enabled");
			DARK_PURPLE_COLOR_ENABLED = getBoolean("Dark_Purple_Color_Enabled");
			ORANGE_COLOR_ENABLED = getBoolean("Orange_Color_Enabled");
			GRAY_COLOR_ENABLED = getBoolean("Gray_Color_Enabled");
			DARK_GRAY_COLOR_ENABLED = getBoolean("Dark_Gray_Color_Enabled");
			BLUE_COLOR_ENABLED = getBoolean("Blue_Color_Enabled");
			GREEN_COLOR_ENABLED = getBoolean("Green_Color_Enabled");
			AQUA_COLOR_ENABLED = getBoolean("Aqua_Color_Enabled");
			RED_COLOR_ENABLED = getBoolean("Red_Color_Enabled");
			LIGHT_PURPLE_COLOR_ENABLED = getBoolean("Light_Purple_Color_Enabled");
			YELLOW_COLOR_ENABLED = getBoolean("Yellow_Color_Enabled");
			WHITE_COLOR_ENABLED = getBoolean("White_Color_Enabled");
		}
	}

	public static class Color_Settings_Name_Colors {
		public static Boolean BLACK_COLOR_ENABLED;
		public static Boolean DARK_BLUE_COLOR_ENABLED;
		public static Boolean DARK_GREEN_COLOR_ENABLED;
		public static Boolean DARK_AQUA_COLOR_ENABLED;
		public static Boolean DARK_RED_COLOR_ENABLED;
		public static Boolean DARK_PURPLE_COLOR_ENABLED;
		public static Boolean ORANGE_COLOR_ENABLED;
		public static Boolean GRAY_COLOR_ENABLED;
		public static Boolean DARK_GRAY_COLOR_ENABLED;
		public static Boolean BLUE_COLOR_ENABLED;
		public static Boolean GREEN_COLOR_ENABLED;
		public static Boolean AQUA_COLOR_ENABLED;
		public static Boolean RED_COLOR_ENABLED;
		public static Boolean LIGHT_PURPLE_COLOR_ENABLED;
		public static Boolean YELLOW_COLOR_ENABLED;
		public static Boolean WHITE_COLOR_ENABLED;

		private static void init() {
			pathPrefix("Color_Settings.Name_Color_Settings");

			BLACK_COLOR_ENABLED = getBoolean("Black_Color_Enabled");
			DARK_BLUE_COLOR_ENABLED = getBoolean("Dark_Blue_Color_Enabled");
			DARK_GREEN_COLOR_ENABLED = getBoolean("Dark_Green_Color_Enabled");
			DARK_AQUA_COLOR_ENABLED = getBoolean("Dark_Aqua_Color_Enabled");
			DARK_RED_COLOR_ENABLED = getBoolean("Dark_Red_Color_Enabled");
			DARK_PURPLE_COLOR_ENABLED = getBoolean("Dark_Purple_Color_Enabled");
			ORANGE_COLOR_ENABLED = getBoolean("Orange_Color_Enabled");
			GRAY_COLOR_ENABLED = getBoolean("Gray_Color_Enabled");
			DARK_GRAY_COLOR_ENABLED = getBoolean("Dark_Gray_Color_Enabled");
			BLUE_COLOR_ENABLED = getBoolean("Blue_Color_Enabled");
			GREEN_COLOR_ENABLED = getBoolean("Green_Color_Enabled");
			AQUA_COLOR_ENABLED = getBoolean("Aqua_Color_Enabled");
			RED_COLOR_ENABLED = getBoolean("Red_Color_Enabled");
			LIGHT_PURPLE_COLOR_ENABLED = getBoolean("Light_Purple_Color_Enabled");
			YELLOW_COLOR_ENABLED = getBoolean("Yellow_Color_Enabled");
			WHITE_COLOR_ENABLED = getBoolean("White_Color_Enabled");
		}
	}

	public static class Database {
		public static String HOST;
		public static Integer PORT;
		public static String DATABASE;
		public static String USER;
		public static String PASS;
		public static Boolean ENABLED;
		public static Integer EXPIRY_DAYS;

		private static void init() {
			pathPrefix("Database");

			ENABLED = getBoolean("Enabled");
			HOST = getString("Host");
			PORT = getInteger("Port");
			DATABASE = getString("Database");
			USER = getString("User");
			PASS = getString("Pass");
			EXPIRY_DAYS = getInteger("Expiry_Days");
		}
	}

	public static class Other {
		public static Boolean BACK_BUTTON_ENABLE;
		public static Boolean NICKNAMES_ENABLE;
		public static Boolean PURGE_NICKNAMES;
		public static Integer NICKNAME_CHARACTER_LIMIT;
		public static String HEX_COLOR_ITEM;
		public static String BACK_BUTTON_ITEM;
		public static String RESET_BUTTON_ITEM;
		public static CompMaterial INFO_ITEM;

		private static void init() {
			pathPrefix("Other");

			BACK_BUTTON_ENABLE = getBoolean("Back_Button_Enable");
			NICKNAMES_ENABLE = getBoolean("Nicknames_Enable");
			PURGE_NICKNAMES = getBoolean("Purge_Nicknames");
			NICKNAME_CHARACTER_LIMIT = getInteger("Nickname_Character_Limit");
			HEX_COLOR_ITEM = getString("Hex_Color_Item");
			BACK_BUTTON_ITEM = getString("Back_Button_Item");
			RESET_BUTTON_ITEM = getString("Reset_Button_Item");
			INFO_ITEM = getMaterial("Info_Button_Item");
		}
	}

	public static class Other_Prefixes {
		public static String SUCCESS_PREFIX;
		public static String ERROR_PREFIX;

		private static void init() {
			pathPrefix("Other_Prefixes");

			SUCCESS_PREFIX = getString("Success_Prefix");
			ERROR_PREFIX = getString("Error_Prefix");
		}
	}

	public static Boolean NOTIFY_UPDATES;
	public static EventPriority CHAT_LISTENER_PRIORITY;

	private static void init() {
		pathPrefix(null);

		NOTIFY_UPDATES = getBoolean("Notify_Updates");
		CHAT_LISTENER_PRIORITY = get("Chat_Listener_Priority", EventPriority.class);
	}
}
