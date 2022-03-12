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
		public static CompMaterial BLACK_ITEM;
		public static CompMaterial DARK_BLUE_ITEM;
		public static CompMaterial DARK_GREEN_ITEM;
		public static CompMaterial DARK_AQUA_ITEM;
		public static CompMaterial DARK_RED_ITEM;
		public static CompMaterial DARK_PURPLE_ITEM;
		public static CompMaterial ORANGE_ITEM;
		public static CompMaterial GRAY_ITEM;
		public static CompMaterial DARK_GRAY_ITEM;
		public static CompMaterial BLUE_ITEM;
		public static CompMaterial GREEN_ITEM;
		public static CompMaterial AQUA_ITEM;
		public static CompMaterial RED_ITEM;
		public static CompMaterial LIGHT_PURPLE_ITEM;
		public static CompMaterial YELLOW_ITEM;
		public static CompMaterial WHITE_ITEM;
		public static CompMaterial MAGIC_ITEM;
		public static CompMaterial BOLD_ITEM;
		public static CompMaterial ITALIC_ITEM;
		public static CompMaterial UNDERLINE_ITEM;
		public static CompMaterial STRIKETHROUGH_ITEM;
		public static CompMaterial FORMAT_RESET_ITEM;

		private static void init() {
			pathPrefix("Chat_Color_Items");

			BLACK_ITEM = getMaterial("Black");
			DARK_BLUE_ITEM = getMaterial("Dark_Blue");
			DARK_GREEN_ITEM = getMaterial("Dark_Green");
			DARK_AQUA_ITEM = getMaterial("Dark_Aqua");
			DARK_RED_ITEM = getMaterial("Dark_Red");
			DARK_PURPLE_ITEM = getMaterial("Dark_Purple");
			ORANGE_ITEM = getMaterial("Orange");
			GRAY_ITEM = getMaterial("Gray");
			DARK_GRAY_ITEM = getMaterial("Dark_Gray");
			BLUE_ITEM = getMaterial("Blue");
			GREEN_ITEM = getMaterial("Green");
			AQUA_ITEM = getMaterial("Aqua");
			RED_ITEM = getMaterial("Red");
			LIGHT_PURPLE_ITEM = getMaterial("Light_Purple");
			YELLOW_ITEM = getMaterial("Yellow");
			WHITE_ITEM = getMaterial("White");
			MAGIC_ITEM = getMaterial("Magic_Format");
			BOLD_ITEM = getMaterial("Bold_Format");
			ITALIC_ITEM = getMaterial("Italic_Format");
			UNDERLINE_ITEM = getMaterial("Underline_Format");
			STRIKETHROUGH_ITEM = getMaterial("Strikethrough_Format");
			FORMAT_RESET_ITEM = getMaterial("Reset_Format");
		}
	}

	public static class Name_Color_Menu_Items {
		public static CompMaterial BLACK_ITEM;
		public static CompMaterial DARK_BLUE_ITEM;
		public static CompMaterial DARK_GREEN_ITEM;
		public static CompMaterial DARK_AQUA_ITEM;
		public static CompMaterial DARK_RED_ITEM;
		public static CompMaterial DARK_PURPLE_ITEM;
		public static CompMaterial ORANGE_ITEM;
		public static CompMaterial GRAY_ITEM;
		public static CompMaterial DARK_GRAY_ITEM;
		public static CompMaterial BLUE_ITEM;
		public static CompMaterial GREEN_ITEM;
		public static CompMaterial AQUA_ITEM;
		public static CompMaterial RED_ITEM;
		public static CompMaterial LIGHT_PURPLE_ITEM;
		public static CompMaterial YELLOW_ITEM;
		public static CompMaterial WHITE_ITEM;
		public static CompMaterial MAGIC_ITEM;
		public static CompMaterial BOLD_ITEM;
		public static CompMaterial ITALIC_ITEM;
		public static CompMaterial UNDERLINE_ITEM;
		public static CompMaterial STRIKETHROUGH_ITEM;
		public static CompMaterial FORMAT_RESET_ITEM;

		private static void init() {
			pathPrefix("Name_Color_Items");

			BLACK_ITEM = getMaterial("Black");
			DARK_BLUE_ITEM = getMaterial("Dark_Blue");
			DARK_GREEN_ITEM = getMaterial("Dark_Green");
			DARK_AQUA_ITEM = getMaterial("Dark_Aqua");
			DARK_RED_ITEM = getMaterial("Dark_Red");
			DARK_PURPLE_ITEM = getMaterial("Dark_Purple");
			ORANGE_ITEM = getMaterial("Orange");
			GRAY_ITEM = getMaterial("Gray");
			DARK_GRAY_ITEM = getMaterial("Dark_Gray");
			BLUE_ITEM = getMaterial("Blue");
			GREEN_ITEM = getMaterial("Green");
			AQUA_ITEM = getMaterial("Aqua");
			RED_ITEM = getMaterial("Red");
			LIGHT_PURPLE_ITEM = getMaterial("Light_Purple");
			YELLOW_ITEM = getMaterial("Yellow");
			WHITE_ITEM = getMaterial("White");
			MAGIC_ITEM = getMaterial("Magic_Format");
			BOLD_ITEM = getMaterial("Bold_Format");
			ITALIC_ITEM = getMaterial("Italic_Format");
			UNDERLINE_ITEM = getMaterial("Underline_Format");
			STRIKETHROUGH_ITEM = getMaterial("Strikethrough_Format");
			FORMAT_RESET_ITEM = getMaterial("Reset_Format");
		}
	}

	public static class Gradient_Color_Menu_Items {
		public static CompMaterial CHAT_ITEM;
		public static CompMaterial NAME_ITEM;
		public static CompMaterial RESET_ITEM;
		public static CompMaterial CUSTOM_ITEM;
		public static CompMaterial FILL_ITEM;
		public static CompMaterial RETURN_ITEM;

		private static void init() {
			pathPrefix("Gradient_Color_Items");

			RESET_ITEM = getMaterial("Reset");
			CUSTOM_ITEM = getMaterial("Custom");
			FILL_ITEM = getMaterial("Fill");

			CHAT_ITEM = getMaterial("Chat");
			NAME_ITEM = getMaterial("Name");
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
		public static CompMaterial CHAT_FILL_BUTTON;
		public static CompMaterial NAME_FILL_BUTTON;
		public static CompMaterial CHAT_COLOR_BUTTON;
		public static CompMaterial NAME_COLOR_BUTTON;

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

			CHAT_FILL_BUTTON = getMaterial("Chat_Fill_Button");
			NAME_FILL_BUTTON = getMaterial("Name_Fill_Button");
			ALLOW_ONLY_CERTAIN_HEX_COLORS = getBoolean("Allow_Only_Certain_Hex_Colors");

			CHAT_COLOR_BUTTON = getMaterial("Chat_Color_Button");
			NAME_COLOR_BUTTON = getMaterial("Name_Color_Button");
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
		public static CompMaterial HEX_COLOR_ITEM;
		public static CompMaterial BACK_BUTTON_ITEM;
		public static CompMaterial RESET_BUTTON_ITEM;
		public static CompMaterial INFO_ITEM;

		private static void init() {
			pathPrefix("Other");

			BACK_BUTTON_ENABLE = getBoolean("Back_Button_Enable");
			NICKNAMES_ENABLE = getBoolean("Nicknames_Enable");
			PURGE_NICKNAMES = getBoolean("Purge_Nicknames");
			NICKNAME_CHARACTER_LIMIT = getInteger("Nickname_Character_Limit");
			HEX_COLOR_ITEM = getMaterial("Hex_Color_Item");
			BACK_BUTTON_ITEM = getMaterial("Back_Button_Item");
			RESET_BUTTON_ITEM = getMaterial("Reset_Button_Item");
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
