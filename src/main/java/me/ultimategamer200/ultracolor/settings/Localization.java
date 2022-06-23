package me.ultimategamer200.ultracolor.settings;

import org.mineacademy.fo.settings.SimpleLocalization;

import java.util.List;

/**
 * The class that manages a player's message file. Each section is divided by their own subclass with options as variables.
 */
public final class Localization extends SimpleLocalization {
	/**
	 * Probably best to leave this as is unless a 2.0 version of the plugin is to come out.
	 */
	@Override
	protected int getConfigVersion() {
		return 1;
	}

	public static class Menu_Titles {
		public static String COLOR_SELECTION_MENU_TITLE;
		public static String CHAT_COLOR_SELECTION_MENU_TITLE;
		public static String NAME_COLOR_SELECTION_MENU_TITLE;
		public static String MENU_INFORMATION_TITLE;

		private static void init() {
			setPathPrefix("Menu_Titles");

			COLOR_SELECTION_MENU_TITLE = getString("Color_Selection");
			CHAT_COLOR_SELECTION_MENU_TITLE = getString("Chat_Color_Selection");
			NAME_COLOR_SELECTION_MENU_TITLE = getString("Name_Color_Selection");
			MENU_INFORMATION_TITLE = getString("Menu_Information");
		}
	}

	public static class Main_GUI_Customization {
		public static String INFO_MESSAGE;
		public static Boolean ALLOW_INFO_BUTTON;
		public static Integer MENU_SIZE;
		public static Integer INFO_BUTTON_SLOT;

		private static void init() {
			setPathPrefix("Main_GUI_Customization");
			INFO_MESSAGE = getString("Info_Message");
			ALLOW_INFO_BUTTON = getBoolean("Info_Button_Enabled");
			MENU_SIZE = getInteger("Size");
			INFO_BUTTON_SLOT = getInteger("Info_Button_Slot");
		}
	}

	public static class Main_GUI_Customization_Chat_Color_Selection {
		public static String CHAT_COLOR_NAME;
		public static List<String> CHAT_COLOR_LORE;
		public static Integer MENU_SIZE;
		public static Integer MENU_SLOT;
		public static String MENU_INFO;
		public static String ERROR_MESSAGE;
		public static Boolean ALLOW_INFO_BUTTON;

		private static void init() {
			setPathPrefix("Main_GUI_Customization.Chat_Color_Selection");
			CHAT_COLOR_NAME = getString("Name");
			CHAT_COLOR_LORE = getStringList("Lore");
			MENU_SIZE = getInteger("Size");
			MENU_SLOT = getInteger("Slot");
			MENU_INFO = getString("Info_Message");
			ERROR_MESSAGE = getString("Error_Message");
			ALLOW_INFO_BUTTON = getBoolean("Info_Button_Enabled");
		}
	}

	public static class Main_GUI_Customization_Name_Color_Selection {
		public static String NAME_COLOR_NAME;
		public static List<String> NAME_COLOR_LORE;
		public static Integer MENU_SIZE;
		public static Integer MENU_SLOT;
		public static String MENU_INFO;
		public static String ERROR_MESSAGE;
		public static Boolean ALLOW_INFO_BUTTON;

		private static void init() {
			setPathPrefix("Main_GUI_Customization.Name_Color_Selection");
			NAME_COLOR_NAME = getString("Name");
			NAME_COLOR_LORE = getStringList("Lore");
			MENU_SIZE = getInteger("Size");
			MENU_SLOT = getInteger("Slot");
			MENU_INFO = getString("Info_Message");
			ERROR_MESSAGE = getString("Error_Message");
			ALLOW_INFO_BUTTON = getBoolean("Info_Button_Enabled");
		}
	}

	public static class Main_GUI_Customization_Gradient_Selection {
		public static String MENU_NAME;
		public static List<String> GRADIENT_COLOR_LORE;
		public static Integer MENU_SLOT;

		private static void init() {
			setPathPrefix("Main_GUI_Customization.Gradient_Color_Selection");

			MENU_NAME = getString("Name");
			GRADIENT_COLOR_LORE = getStringList("Lore");
			MENU_SLOT = getInteger("Slot");
		}
	}

	public static class Main_GUI_Customization_Hex_Selection {
		public static String MENU_NAME;
		public static List<String> HEX_COLOR_LORE;
		public static Integer MENU_SLOT;

		private static void init() {
			setPathPrefix("Main_GUI_Customization.Hex_Color_Selection");

			MENU_NAME = getString("Name");
			HEX_COLOR_LORE = getStringList("Lore");
			MENU_SLOT = getInteger("Slot");
		}
	}

	public static class Commands {
		private static void init() {
			setPathPrefix("Commands");
		}
	}

	public static class Chat_Color_Selection_Customization_Black {
		public static String BLACK_NAME;
		public static List<String> BLACK_LORE;
		public static String BLACK_SUCCESS;
		public static Integer MENU_SLOT;

		private static void init() {
			setPathPrefix("Chat_Color_Selection_Customization.Black");
			BLACK_NAME = getString("Name");
			BLACK_LORE = getStringList("Lore");
			MENU_SLOT = getInteger("Slot");
			BLACK_SUCCESS = getString("Success_Message");
		}
	}

	public static class Chat_Color_Selection_Customization_Dark_Blue {
		public static String DARK_BLUE_NAME;
		public static List<String> DARK_BLUE_LORE;
		public static String DARK_BLUE_SUCCESS;
		public static Integer MENU_SLOT;

		private static void init() {
			setPathPrefix("Chat_Color_Selection_Customization.Dark_Blue");
			DARK_BLUE_NAME = getString("Name");
			DARK_BLUE_LORE = getStringList("Lore");
			MENU_SLOT = getInteger("Slot");
			DARK_BLUE_SUCCESS = getString("Success_Message");
		}
	}

	public static class Chat_Color_Selection_Customization_Dark_Green {
		public static String DARK_GREEN_NAME;
		public static List<String> DARK_GREEN_LORE;
		public static String DARK_GREEN_SUCCESS;
		public static Integer MENU_SLOT;

		private static void init() {
			setPathPrefix("Chat_Color_Selection_Customization.Dark_Green");
			DARK_GREEN_NAME = getString("Name");
			DARK_GREEN_LORE = getStringList("Lore");
			MENU_SLOT = getInteger("Slot");
			DARK_GREEN_SUCCESS = getString("Success_Message");
		}
	}

	public static class Chat_Color_Selection_Customization_Dark_Aqua {
		public static String DARK_AQUA_NAME;
		public static List<String> DARK_AQUA_LORE;
		public static String DARK_AQUA_SUCCESS;
		public static Integer MENU_SLOT;

		private static void init() {
			setPathPrefix("Chat_Color_Selection_Customization.Dark_Aqua");
			DARK_AQUA_NAME = getString("Name");
			DARK_AQUA_LORE = getStringList("Lore");
			MENU_SLOT = getInteger("Slot");
			DARK_AQUA_SUCCESS = getString("Success_Message");
		}
	}

	public static class Chat_Color_Selection_Customization_Dark_Red {
		public static String DARK_RED_NAME;
		public static List<String> DARK_RED_LORE;
		public static String DARK_RED_SUCCESS;
		public static Integer MENU_SLOT;

		private static void init() {
			setPathPrefix("Chat_Color_Selection_Customization.Dark_Red");
			DARK_RED_NAME = getString("Name");
			DARK_RED_LORE = getStringList("Lore");
			MENU_SLOT = getInteger("Slot");
			DARK_RED_SUCCESS = getString("Success_Message");
		}
	}

	public static class Chat_Color_Selection_Customization_Dark_Purple {
		public static String DARK_PURPLE_NAME;
		public static List<String> DARK_PURPLE_LORE;
		public static String DARK_PURPLE_SUCCESS;
		public static Integer MENU_SLOT;

		private static void init() {
			setPathPrefix("Chat_Color_Selection_Customization.Dark_Purple");
			DARK_PURPLE_NAME = getString("Name");
			DARK_PURPLE_LORE = getStringList("Lore");
			MENU_SLOT = getInteger("Slot");
			DARK_PURPLE_SUCCESS = getString("Success_Message");
		}
	}

	public static class Chat_Color_Selection_Customization_Orange {
		public static String ORANGE_NAME;
		public static List<String> ORANGE_LORE;
		public static String ORANGE_SUCCESS;
		public static Integer MENU_SLOT;

		private static void init() {
			setPathPrefix("Chat_Color_Selection_Customization.Orange");
			ORANGE_NAME = getString("Name");
			ORANGE_LORE = getStringList("Lore");
			MENU_SLOT = getInteger("Slot");
			ORANGE_SUCCESS = getString("Success_Message");
		}
	}

	public static class Chat_Color_Selection_Customization_Gray {
		public static String GRAY_NAME;
		public static List<String> GRAY_LORE;
		public static String GRAY_SUCCESS;
		public static Integer MENU_SLOT;

		private static void init() {
			setPathPrefix("Chat_Color_Selection_Customization.Gray");
			GRAY_NAME = getString("Name");
			GRAY_LORE = getStringList("Lore");
			MENU_SLOT = getInteger("Slot");
			GRAY_SUCCESS = getString("Success_Message");
		}
	}

	public static class Chat_Color_Selection_Customization_Dark_Gray {
		public static String DARK_GRAY_NAME;
		public static List<String> DARK_GRAY_LORE;
		public static String DARK_GRAY_SUCCESS;
		public static Integer MENU_SLOT;

		private static void init() {
			setPathPrefix("Chat_Color_Selection_Customization.Dark_Gray");
			DARK_GRAY_NAME = getString("Name");
			DARK_GRAY_LORE = getStringList("Lore");
			MENU_SLOT = getInteger("Slot");
			DARK_GRAY_SUCCESS = getString("Success_Message");
		}
	}

	public static class Chat_Color_Selection_Customization_Blue {
		public static String BLUE_NAME;
		public static List<String> BLUE_LORE;
		public static String BLUE_SUCCESS;
		public static Integer MENU_SLOT;

		private static void init() {
			setPathPrefix("Chat_Color_Selection_Customization.Blue");
			BLUE_NAME = getString("Name");
			BLUE_LORE = getStringList("Lore");
			MENU_SLOT = getInteger("Slot");
			BLUE_SUCCESS = getString("Success_Message");
		}
	}

	public static class Chat_Color_Selection_Customization_Green {
		public static String GREEN_NAME;
		public static List<String> GREEN_LORE;
		public static String GREEN_SUCCESS;
		public static Integer MENU_SLOT;

		private static void init() {
			setPathPrefix("Chat_Color_Selection_Customization.Green");
			GREEN_NAME = getString("Name");
			GREEN_LORE = getStringList("Lore");
			MENU_SLOT = getInteger("Slot");
			GREEN_SUCCESS = getString("Success_Message");
		}
	}

	public static class Chat_Color_Selection_Customization_Aqua {
		public static String AQUA_NAME;
		public static List<String> AQUA_LORE;
		public static String AQUA_SUCCESS;
		public static Integer MENU_SLOT;

		private static void init() {
			setPathPrefix("Chat_Color_Selection_Customization.Aqua");
			AQUA_NAME = getString("Name");
			AQUA_LORE = getStringList("Lore");
			MENU_SLOT = getInteger("Slot");
			AQUA_SUCCESS = getString("Success_Message");
		}
	}

	public static class Chat_Color_Selection_Customization_Red {
		public static String RED_NAME;
		public static List<String> RED_LORE;
		public static String RED_SUCCESS;
		public static Integer MENU_SLOT;

		private static void init() {
			setPathPrefix("Chat_Color_Selection_Customization.Red");
			RED_NAME = getString("Name");
			RED_LORE = getStringList("Lore");
			MENU_SLOT = getInteger("Slot");
			RED_SUCCESS = getString("Success_Message");
		}
	}

	public static class Chat_Color_Selection_Customization_Light_Purple {
		public static String LIGHT_PURPLE_NAME;
		public static List<String> LIGHT_PURPLE_LORE;
		public static String LIGHT_PURPLE_SUCCESS;
		public static Integer MENU_SLOT;

		private static void init() {
			setPathPrefix("Chat_Color_Selection_Customization.Light_Purple");
			LIGHT_PURPLE_NAME = getString("Name");
			LIGHT_PURPLE_LORE = getStringList("Lore");
			MENU_SLOT = getInteger("Slot");
			LIGHT_PURPLE_SUCCESS = getString("Success_Message");
		}
	}

	public static class Chat_Color_Selection_Customization_Yellow {
		public static String YELLOW_NAME;
		public static List<String> YELLOW_LORE;
		public static String YELLOW_SUCCESS;
		public static Integer MENU_SLOT;

		private static void init() {
			setPathPrefix("Chat_Color_Selection_Customization.Yellow");
			YELLOW_NAME = getString("Name");
			YELLOW_LORE = getStringList("Lore");
			MENU_SLOT = getInteger("Slot");
			YELLOW_SUCCESS = getString("Success_Message");
		}
	}

	public static class Chat_Color_Selection_Customization_White {
		public static String WHITE_NAME;
		public static List<String> WHITE_LORE;
		public static String WHITE_SUCCESS;
		public static Integer MENU_SLOT;

		private static void init() {
			setPathPrefix("Chat_Color_Selection_Customization.White");
			WHITE_NAME = getString("Name");
			WHITE_LORE = getStringList("Lore");
			MENU_SLOT = getInteger("Slot");
			WHITE_SUCCESS = getString("Success_Message");
		}
	}

	public static class Chat_Color_Selection_Customization_Magic {
		public static String MAGIC_NAME;
		public static List<String> MAGIC_LORE;
		public static String MAGIC_SUCCESS;
		public static Integer MENU_SLOT;

		private static void init() {
			setPathPrefix("Chat_Color_Selection_Customization.Magic");
			MAGIC_NAME = getString("Name");
			MAGIC_LORE = getStringList("Lore");
			MENU_SLOT = getInteger("Slot");
			MAGIC_SUCCESS = getString("Success_Message");
		}
	}

	public static class Chat_Color_Selection_Customization_Bold {
		public static String BOLD_NAME;
		public static List<String> BOLD_LORE;
		public static String BOLD_SUCCESS;
		public static Integer MENU_SLOT;

		private static void init() {
			setPathPrefix("Chat_Color_Selection_Customization.Bold");
			BOLD_NAME = getString("Name");
			BOLD_LORE = getStringList("Lore");
			MENU_SLOT = getInteger("Slot");
			BOLD_SUCCESS = getString("Success_Message");
		}
	}

	public static class Chat_Color_Selection_Customization_Italic {
		public static String ITALIC_NAME;
		public static List<String> ITALIC_LORE;
		public static String ITALIC_SUCCESS;
		public static Integer MENU_SLOT;

		private static void init() {
			setPathPrefix("Chat_Color_Selection_Customization.Italic");
			ITALIC_NAME = getString("Name");
			ITALIC_LORE = getStringList("Lore");
			MENU_SLOT = getInteger("Slot");
			ITALIC_SUCCESS = getString("Success_Message");
		}
	}

	public static class Chat_Color_Selection_Customization_Underline {
		public static String UNDERLINE_NAME;
		public static List<String> UNDERLINE_LORE;
		public static String UNDERLINE_SUCCESS;
		public static Integer MENU_SLOT;

		private static void init() {
			setPathPrefix("Chat_Color_Selection_Customization.Underline");
			UNDERLINE_NAME = getString("Name");
			UNDERLINE_LORE = getStringList("Lore");
			MENU_SLOT = getInteger("Slot");
			UNDERLINE_SUCCESS = getString("Success_Message");
		}
	}

	public static class Chat_Color_Selection_Customization_Strikethrough {
		public static String STRIKETHROUGH_NAME;
		public static List<String> STRIKETHROUGH_LORE;
		public static String STRIKETHROUGH_SUCCESS;
		public static Integer MENU_SLOT;

		private static void init() {
			setPathPrefix("Chat_Color_Selection_Customization.Strikethrough");
			STRIKETHROUGH_NAME = getString("Name");
			STRIKETHROUGH_LORE = getStringList("Lore");
			MENU_SLOT = getInteger("Slot");
			STRIKETHROUGH_SUCCESS = getString("Success_Message");
		}
	}

	public static class Chat_Color_Selection_Customization_Rainbow {
		public static String RAINBOW_NAME;
		public static List<String> RAINBOW_LORE;
		public static String RAINBOW_SUCCESS;
		public static Integer MENU_SLOT;

		private static void init() {
			setPathPrefix("Chat_Color_Selection_Customization.Rainbow");
			RAINBOW_NAME = getString("Name");
			RAINBOW_LORE = getStringList("Lore");
			MENU_SLOT = getInteger("Slot");
			RAINBOW_SUCCESS = getString("Success_Message");
		}
	}

	public static class Chat_Back_Button {
		public static String BACK_NAME;
		public static List<String> BACK_LORE;
		public static Integer MENU_SLOT;

		private static void init() {
			setPathPrefix("Chat_Color_Selection_Customization.Back_Button");

			BACK_NAME = getString("Name");
			BACK_LORE = getStringList("Lore");
			MENU_SLOT = getInteger("Slot");
		}
	}

	public static class Chat_Reset_Button {
		public static String RESET_NAME;
		public static List<String> RESET_LORE;
		public static String RESET_SUCCESS;
		public static String RESET_ERROR;
		public static Integer MENU_SLOT;

		private static void init() {
			setPathPrefix("Chat_Color_Selection_Customization.Reset_Button");

			RESET_NAME = getString("Name");
			RESET_LORE = getStringList("Lore");
			MENU_SLOT = getInteger("Slot");
			RESET_SUCCESS = getString("Success_Message");
			RESET_ERROR = getString("Error_Message");
		}
	}

	public static class Chat_Format_Reset_Button {
		public static String RESET_NAME;
		public static List<String> RESET_LORE;
		public static String RESET_SUCCESS;
		public static String RESET_ERROR;
		public static Integer MENU_SLOT;

		private static void init() {
			setPathPrefix("Chat_Color_Selection_Customization.Reset_Format_Button");

			RESET_NAME = getString("Name");
			RESET_LORE = getStringList("Lore");
			MENU_SLOT = getInteger("Slot");
			RESET_SUCCESS = getString("Success_Message");
			RESET_ERROR = getString("Error_Message");
		}
	}

	public static class Name_Color_Selection_Customization_Black {
		public static String BLACK_NAME;
		public static List<String> BLACK_LORE;
		public static String BLACK_SUCCESS;
		public static Integer MENU_SLOT;

		private static void init() {
			setPathPrefix("Name_Color_Selection_Customization.Black");
			BLACK_NAME = getString("Name");
			BLACK_LORE = getStringList("Lore");
			MENU_SLOT = getInteger("Slot");
			BLACK_SUCCESS = getString("Success_Message");
		}
	}

	public static class Name_Color_Selection_Customization_Dark_Blue {
		public static String DARK_BLUE_NAME;
		public static List<String> DARK_BLUE_LORE;
		public static String DARK_BLUE_SUCCESS;
		public static Integer MENU_SLOT;

		private static void init() {
			setPathPrefix("Name_Color_Selection_Customization.Dark_Blue");
			DARK_BLUE_NAME = getString("Name");
			DARK_BLUE_LORE = getStringList("Lore");
			MENU_SLOT = getInteger("Slot");
			DARK_BLUE_SUCCESS = getString("Success_Message");
		}
	}

	public static class Name_Color_Selection_Customization_Dark_Green {
		public static String DARK_GREEN_NAME;
		public static List<String> DARK_GREEN_LORE;
		public static String DARK_GREEN_SUCCESS;
		public static Integer MENU_SLOT;

		private static void init() {
			setPathPrefix("Name_Color_Selection_Customization.Dark_Green");
			DARK_GREEN_NAME = getString("Name");
			DARK_GREEN_LORE = getStringList("Lore");
			MENU_SLOT = getInteger("Slot");
			DARK_GREEN_SUCCESS = getString("Success_Message");
		}
	}

	public static class Name_Color_Selection_Customization_Dark_Aqua {
		public static String DARK_AQUA_NAME;
		public static List<String> DARK_AQUA_LORE;
		public static String DARK_AQUA_SUCCESS;
		public static Integer MENU_SLOT;

		private static void init() {
			setPathPrefix("Name_Color_Selection_Customization.Dark_Aqua");
			DARK_AQUA_NAME = getString("Name");
			DARK_AQUA_LORE = getStringList("Lore");
			MENU_SLOT = getInteger("Slot");
			DARK_AQUA_SUCCESS = getString("Success_Message");
		}
	}

	public static class Name_Color_Selection_Customization_Dark_Red {
		public static String DARK_RED_NAME;
		public static List<String> DARK_RED_LORE;
		public static String DARK_RED_SUCCESS;
		public static Integer MENU_SLOT;

		private static void init() {
			setPathPrefix("Name_Color_Selection_Customization.Dark_Red");
			DARK_RED_NAME = getString("Name");
			DARK_RED_LORE = getStringList("Lore");
			MENU_SLOT = getInteger("Slot");
			DARK_RED_SUCCESS = getString("Success_Message");
		}
	}

	public static class Name_Color_Selection_Customization_Dark_Purple {
		public static String DARK_PURPLE_NAME;
		public static List<String> DARK_PURPLE_LORE;
		public static String DARK_PURPLE_SUCCESS;
		public static Integer MENU_SLOT;

		private static void init() {
			setPathPrefix("Name_Color_Selection_Customization.Dark_Purple");
			DARK_PURPLE_NAME = getString("Name");
			DARK_PURPLE_LORE = getStringList("Lore");
			MENU_SLOT = getInteger("Slot");
			DARK_PURPLE_SUCCESS = getString("Success_Message");
		}
	}

	public static class Name_Color_Selection_Customization_Orange {
		public static String ORANGE_NAME;
		public static List<String> ORANGE_LORE;
		public static String ORANGE_SUCCESS;
		public static Integer MENU_SLOT;

		private static void init() {
			setPathPrefix("Name_Color_Selection_Customization.Orange");
			ORANGE_NAME = getString("Name");
			ORANGE_LORE = getStringList("Lore");
			MENU_SLOT = getInteger("Slot");
			ORANGE_SUCCESS = getString("Success_Message");
		}
	}

	public static class Name_Color_Selection_Customization_Gray {
		public static String GRAY_NAME;
		public static List<String> GRAY_LORE;
		public static String GRAY_SUCCESS;
		public static Integer MENU_SLOT;

		private static void init() {
			setPathPrefix("Name_Color_Selection_Customization.Gray");
			GRAY_NAME = getString("Name");
			GRAY_LORE = getStringList("Lore");
			MENU_SLOT = getInteger("Slot");
			GRAY_SUCCESS = getString("Success_Message");
		}
	}

	public static class Name_Color_Selection_Customization_Dark_Gray {
		public static String DARK_GRAY_NAME;
		public static List<String> DARK_GRAY_LORE;
		public static String DARK_GRAY_SUCCESS;
		public static Integer MENU_SLOT;

		private static void init() {
			setPathPrefix("Name_Color_Selection_Customization.Dark_Gray");
			DARK_GRAY_NAME = getString("Name");
			DARK_GRAY_LORE = getStringList("Lore");
			MENU_SLOT = getInteger("Slot");
			DARK_GRAY_SUCCESS = getString("Success_Message");
		}
	}

	public static class Name_Color_Selection_Customization_Blue {
		public static String BLUE_NAME;
		public static List<String> BLUE_LORE;
		public static String BLUE_SUCCESS;
		public static Integer MENU_SLOT;

		private static void init() {
			setPathPrefix("Name_Color_Selection_Customization.Blue");
			BLUE_NAME = getString("Name");
			BLUE_LORE = getStringList("Lore");
			MENU_SLOT = getInteger("Slot");
			BLUE_SUCCESS = getString("Success_Message");
		}
	}

	public static class Name_Color_Selection_Customization_Green {
		public static String GREEN_NAME;
		public static List<String> GREEN_LORE;
		public static String GREEN_SUCCESS;
		public static Integer MENU_SLOT;

		private static void init() {
			setPathPrefix("Name_Color_Selection_Customization.Green");
			GREEN_NAME = getString("Name");
			GREEN_LORE = getStringList("Lore");
			MENU_SLOT = getInteger("Slot");
			GREEN_SUCCESS = getString("Success_Message");
		}
	}

	public static class Name_Color_Selection_Customization_Aqua {
		public static String AQUA_NAME;
		public static List<String> AQUA_LORE;
		public static String AQUA_SUCCESS;
		public static Integer MENU_SLOT;

		private static void init() {
			setPathPrefix("Name_Color_Selection_Customization.Aqua");
			AQUA_NAME = getString("Name");
			AQUA_LORE = getStringList("Lore");
			MENU_SLOT = getInteger("Slot");
			AQUA_SUCCESS = getString("Success_Message");
		}
	}

	public static class Name_Color_Selection_Customization_Red {
		public static String RED_NAME;
		public static List<String> RED_LORE;
		public static String RED_SUCCESS;
		public static Integer MENU_SLOT;

		private static void init() {
			setPathPrefix("Name_Color_Selection_Customization.Red");
			RED_NAME = getString("Name");
			RED_LORE = getStringList("Lore");
			MENU_SLOT = getInteger("Slot");
			RED_SUCCESS = getString("Success_Message");
		}
	}

	public static class Name_Color_Selection_Customization_Light_Purple {
		public static String LIGHT_PURPLE_NAME;
		public static List<String> LIGHT_PURPLE_LORE;
		public static String LIGHT_PURPLE_SUCCESS;
		public static Integer MENU_SLOT;

		private static void init() {
			setPathPrefix("Name_Color_Selection_Customization.Light_Purple");
			LIGHT_PURPLE_NAME = getString("Name");
			LIGHT_PURPLE_LORE = getStringList("Lore");
			MENU_SLOT = getInteger("Slot");
			LIGHT_PURPLE_SUCCESS = getString("Success_Message");
		}
	}

	public static class Name_Color_Selection_Customization_Yellow {
		public static String YELLOW_NAME;
		public static List<String> YELLOW_LORE;
		public static String YELLOW_SUCCESS;
		public static Integer MENU_SLOT;

		private static void init() {
			setPathPrefix("Name_Color_Selection_Customization.Yellow");
			YELLOW_NAME = getString("Name");
			YELLOW_LORE = getStringList("Lore");
			MENU_SLOT = getInteger("Slot");
			YELLOW_SUCCESS = getString("Success_Message");
		}
	}

	public static class Name_Color_Selection_Customization_White {
		public static String WHITE_NAME;
		public static List<String> WHITE_LORE;
		public static String WHITE_SUCCESS;
		public static Integer MENU_SLOT;

		private static void init() {
			setPathPrefix("Name_Color_Selection_Customization.White");
			WHITE_NAME = getString("Name");
			WHITE_LORE = getStringList("Lore");
			MENU_SLOT = getInteger("Slot");
			WHITE_SUCCESS = getString("Success_Message");
		}
	}

	public static class Name_Color_Selection_Customization_Magic {
		public static String MAGIC_NAME;
		public static List<String> MAGIC_LORE;
		public static String MAGIC_SUCCESS;
		public static Integer MENU_SLOT;

		private static void init() {
			setPathPrefix("Name_Color_Selection_Customization.Magic");
			MAGIC_NAME = getString("Name");
			MAGIC_LORE = getStringList("Lore");
			MENU_SLOT = getInteger("Slot");
			MAGIC_SUCCESS = getString("Success_Message");
		}
	}

	public static class Name_Color_Selection_Customization_Bold {
		public static String BOLD_NAME;
		public static List<String> BOLD_LORE;
		public static String BOLD_SUCCESS;
		public static Integer MENU_SLOT;

		private static void init() {
			setPathPrefix("Name_Color_Selection_Customization.Bold");
			BOLD_NAME = getString("Name");
			BOLD_LORE = getStringList("Lore");
			MENU_SLOT = getInteger("Slot");
			BOLD_SUCCESS = getString("Success_Message");
		}
	}

	public static class Name_Color_Selection_Customization_Italic {
		public static String ITALIC_NAME;
		public static List<String> ITALIC_LORE;
		public static String ITALIC_SUCCESS;
		public static Integer MENU_SLOT;

		private static void init() {
			setPathPrefix("Name_Color_Selection_Customization.Italic");
			ITALIC_NAME = getString("Name");
			ITALIC_LORE = getStringList("Lore");
			MENU_SLOT = getInteger("Slot");
			ITALIC_SUCCESS = getString("Success_Message");
		}
	}

	public static class Name_Color_Selection_Customization_Underline {
		public static String UNDERLINE_NAME;
		public static List<String> UNDERLINE_LORE;
		public static String UNDERLINE_SUCCESS;
		public static Integer MENU_SLOT;

		private static void init() {
			setPathPrefix("Name_Color_Selection_Customization.Underline");
			UNDERLINE_NAME = getString("Name");
			UNDERLINE_LORE = getStringList("Lore");
			MENU_SLOT = getInteger("Slot");
			UNDERLINE_SUCCESS = getString("Success_Message");
		}
	}

	public static class Name_Color_Selection_Customization_Strikethrough {
		public static String STRIKETHROUGH_NAME;
		public static List<String> STRIKETHROUGH_LORE;
		public static String STRIKETHROUGH_SUCCESS;
		public static Integer MENU_SLOT;

		private static void init() {
			setPathPrefix("Name_Color_Selection_Customization.Strikethrough");
			STRIKETHROUGH_NAME = getString("Name");
			STRIKETHROUGH_LORE = getStringList("Lore");
			MENU_SLOT = getInteger("Slot");
			STRIKETHROUGH_SUCCESS = getString("Success_Message");
		}
	}

	public static class Name_Color_Selection_Customization_Rainbow {
		public static String RAINBOW_NAME;
		public static List<String> RAINBOW_LORE;
		public static String RAINBOW_SUCCESS;
		public static Integer MENU_SLOT;

		private static void init() {
			setPathPrefix("Name_Color_Selection_Customization.Rainbow");
			RAINBOW_NAME = getString("Name");
			RAINBOW_LORE = getStringList("Lore");
			MENU_SLOT = getInteger("Slot");
			RAINBOW_SUCCESS = getString("Success_Message");
		}
	}

	public static class Name_Back_Button {
		public static String BACK_NAME;
		public static List<String> BACK_LORE;
		public static Integer MENU_SLOT;

		private static void init() {
			setPathPrefix("Name_Color_Selection_Customization.Back_Button");

			BACK_NAME = getString("Name");
			BACK_LORE = getStringList("Lore");
			MENU_SLOT = getInteger("Slot");
		}
	}

	public static class Name_Reset_Button {
		public static String RESET_NAME;
		public static List<String> RESET_LORE;
		public static String RESET_SUCCESS;
		public static String RESET_ERROR;
		public static Integer MENU_SLOT;

		private static void init() {
			setPathPrefix("Name_Color_Selection_Customization.Reset_Button");

			RESET_NAME = getString("Name");
			RESET_LORE = getStringList("Lore");
			RESET_SUCCESS = getString("Success_Message");
			RESET_ERROR = getString("Error_Message");
			MENU_SLOT = getInteger("Slot");
		}
	}

	public static class Name_Format_Reset_Button {
		public static String RESET_NAME;
		public static List<String> RESET_LORE;
		public static String RESET_SUCCESS;
		public static String RESET_ERROR;
		public static Integer MENU_SLOT;

		private static void init() {
			setPathPrefix("Name_Color_Selection_Customization.Reset_Format_Button");

			RESET_NAME = getString("Name");
			RESET_LORE = getStringList("Lore");
			MENU_SLOT = getInteger("Slot");
			RESET_SUCCESS = getString("Success_Message");
			RESET_ERROR = getString("Error_Message");
		}
	}

	public static class Gradient_Color_Selection {
		public static String MENU_TITLE;
		public static String NAME_MENU_TITLE;
		public static Integer NAME_MENU_ITEM_SLOT;
		public static String CHAT_MENU_TITLE;
		public static Integer CHAT_MENU_ITEM_SLOT;
		public static String CHAT_MENU_LORE;
		public static String NAME_MENU_LORE;
		public static String INFO_MESSAGE;
		public static String ERROR_MESSAGE;
		public static Integer MAIN_MENU_SIZE;
		public static String UNSUPPORTED_VERSION_MESSAGE;
		public static String INVALID_GRADIENT_MESSAGE;
		public static String DISABLED_MESSAGE;
		public static Boolean ALLOW_INFO_BUTTON;

		private static void init() {
			setPathPrefix("Gradient_Color_Selection_Customization");

			MAIN_MENU_SIZE = getInteger("Main_Menu_Size");
			MENU_TITLE = getString("Menu_Title");
			NAME_MENU_TITLE = getString("Name_Menu_Title");
			NAME_MENU_ITEM_SLOT = getInteger("Name_Menu_Item_Slot");
			CHAT_MENU_TITLE = getString("Chat_Menu_Title");
			CHAT_MENU_ITEM_SLOT = getInteger("Chat_Menu_Item_Slot");
			CHAT_MENU_LORE = getString("Chat_Menu_Lore");
			NAME_MENU_LORE = getString("Name_Menu_Lore");
			INFO_MESSAGE = getString("Info_Message");
			ERROR_MESSAGE = getString("Error_Message");
			UNSUPPORTED_VERSION_MESSAGE = getString("Unsupported_Version_Message");
			INVALID_GRADIENT_MESSAGE = getString("Invalid_Gradient_Message");

			DISABLED_MESSAGE = getString("Disabled_Message");
			ALLOW_INFO_BUTTON = getBoolean("Info_Button_Enabled");
		}
	}

	public static class Gradient_Color_Selection_Chat {
		public static Integer CHAT_CUSTOM_GRADIENT_SLOT;
		public static Integer CHAT_RESET_GRADIENT_SLOT;
		public static Integer CHAT_MENU_SIZE;
		public static Integer NEXT_PAGE_SLOT;
		public static Integer PREVIOUS_PAGE_SLOT;

		private static void init() {
			setPathPrefix("Gradient_Color_Selection_Customization.Chat");

			CHAT_CUSTOM_GRADIENT_SLOT = getInteger("Chat_Custom_Gradient_Slot");
			CHAT_RESET_GRADIENT_SLOT = getInteger("Chat_Reset_Gradient_Slot");
			CHAT_MENU_SIZE = getInteger("Chat_Gradient_Menu_Size");
			NEXT_PAGE_SLOT = getInteger("Chat_Gradient_Next_Page_Slot");
			PREVIOUS_PAGE_SLOT = getInteger("Chat_Gradient_Previous_Page_Slot");
		}
	}

	public static class Gradient_Color_Selection_Name {
		public static Integer NAME_CUSTOM_GRADIENT_SLOT;
		public static Integer NAME_RESET_GRADIENT_SLOT;
		public static Integer NAME_MENU_SIZE;
		public static Integer NEXT_PAGE_SLOT;
		public static Integer PREVIOUS_PAGE_SLOT;

		private static void init() {
			setPathPrefix("Gradient_Color_Selection_Customization.Name");

			NAME_CUSTOM_GRADIENT_SLOT = getInteger("Name_Custom_Gradient_Slot");
			NAME_RESET_GRADIENT_SLOT = getInteger("Name_Reset_Gradient_Slot");
			NAME_MENU_SIZE = getInteger("Name_Gradient_Menu_Size");
			NEXT_PAGE_SLOT = getInteger("Name_Gradient_Next_Page_Slot");
			PREVIOUS_PAGE_SLOT = getInteger("Name_Gradient_Previous_Page_Slot");
		}
	}

	public static class Gradient_Color_Selection_Reset {
		public static String RESET_NAME;
		public static List<String> RESET_LORE;
		public static String SUCCESS_MESSAGE;
		public static String ERROR_MESSAGE;

		private static void init() {
			setPathPrefix("Gradient_Color_Selection_Customization.Reset_Gradient");

			RESET_NAME = getString("Name");
			RESET_LORE = getStringList("Lore");
			SUCCESS_MESSAGE = getString("Success_Message");
			ERROR_MESSAGE = getString("Error_Message");
		}
	}

	public static class Gradient_Color_Selection_Custom {
		public static String CUSTOM_NAME;
		public static List<String> CUSTOM_LORE;
		public static String SUCCESS_MESSAGE;
		public static String GRADIENT_PROMPT_PREFIX;
		public static String FIRST_PROMPT;
		public static String SECOND_PROMPT;
		public static String FORMAT_PROMPT;

		private static void init() {
			setPathPrefix("Gradient_Color_Selection_Customization.Custom_Gradient");

			CUSTOM_NAME = getString("Name");
			CUSTOM_LORE = getStringList("Lore");
			SUCCESS_MESSAGE = getString("Success_Message");
			FIRST_PROMPT = getString("First_Color_Prompt");
			SECOND_PROMPT = getString("Second_Color_Prompt");
			FORMAT_PROMPT = getString("Format_Prompt");
			GRADIENT_PROMPT_PREFIX = getString("Prompt_Prefix");
		}
	}

	public static class Gradient_Color_Selection_Return {
		public static Boolean ENABLED;
		public static String CUSTOM_NAME;
		public static List<String> CUSTOM_LORE;
		public static Integer MENU_SLOT;

		private static void init() {
			setPathPrefix("Gradient_Color_Selection_Customization.Return");

			ENABLED = getBoolean("Enabled");
			CUSTOM_NAME = getString("Name");
			CUSTOM_LORE = getStringList("Lore");
			MENU_SLOT = getInteger("Slot");
		}
	}

	public static class Nicknames {
		public static String NICKNAME_ALREADY_TAKEN_MESSAGE;
		public static String NICKNAME_COMMAND_SUCCESSFUL_MESSAGE;
		public static String REAL_NAME_MESSAGE;
		public static String NICKNAME_NOT_BE_FOUND_MESSAGE;

		private static void init() {
			setPathPrefix("Nicknames");

			NICKNAME_ALREADY_TAKEN_MESSAGE = getString("Nickname_Already_Taken_Message");
			NICKNAME_COMMAND_SUCCESSFUL_MESSAGE = getString("Nickname_Command_Success_Message");
			REAL_NAME_MESSAGE = getString("Real_Name_Message");
			NICKNAME_NOT_BE_FOUND_MESSAGE = getString("Nickname_Not_Found_Message");
		}
	}

	public static class Hex_Colors {
		public static String HEX_PROMPT_PREFIX;
		public static String FIRST_PROMPT;
		public static String SECOND_PROMPT;

		public static String FORMAT_PROMPT;
		public static String HEX_COLOR_UNSUPPORTED_VERSION_MESSAGE;
		public static String HEX_COLOR_SUCCESS_MESSAGE;

		public static String DISABLED_TYPE_MESSAGE;
		public static String HEX_COLOR_ERROR_MESSAGE;
		public static String HEX_COLOR_NOT_ALLOWED_MESSAGE;

		public static String HEX_COLOR_WHITELIST_EMPTY;
		public static String INVALID_HEX_COLOR_MESSAGE;
		public static String HEX_COLOR_REMOVED_FROM_WHITELIST;

		public static String HEX_COLOR_ADDED_TO_WHITELIST;
		public static String HEX_COLOR_NOT_FOUND_ON_WHITELIST;
		public static String HEX_COLOR_ALREADY_ADDED_TO_WHITELIST;

		private static void init() {
			setPathPrefix("Hex_Colors");

			HEX_PROMPT_PREFIX = getString("Prompt_Prefix");
			FIRST_PROMPT = getString("First_Prompt");
			SECOND_PROMPT = getString("Second_Prompt");

			FORMAT_PROMPT = getString("Format_Prompt");
			HEX_COLOR_UNSUPPORTED_VERSION_MESSAGE = getString("Hex_Color_Unsupported_Message");
			HEX_COLOR_SUCCESS_MESSAGE = getString("Hex_Color_Success_Message");

			DISABLED_TYPE_MESSAGE = getString("Disabled_Type_Message");
			HEX_COLOR_ERROR_MESSAGE = getString("Hex_Color_Error_Message");
			HEX_COLOR_NOT_ALLOWED_MESSAGE = getString("Hex_Color_Not_Allowed_Message");

			HEX_COLOR_WHITELIST_EMPTY = getString("Hex_Color_Whitelist_Empty");
			INVALID_HEX_COLOR_MESSAGE = getString("Invalid_Hex_Color_Message");
			HEX_COLOR_REMOVED_FROM_WHITELIST = getString("Hex_Color_Removed_From_Whitelist");

			HEX_COLOR_ADDED_TO_WHITELIST = getString("Hex_Color_Added_To_Whitelist");
			HEX_COLOR_NOT_FOUND_ON_WHITELIST = getString("Hex_Color_Not_Found_On_Whitelist");
			HEX_COLOR_ALREADY_ADDED_TO_WHITELIST = getString("Hex_Color_Already_Added_To_Whitelist");
		}
	}

	public static class Other {
		public static String NO_PERMISSION;
		public static String ADMIN_SET_GRADIENT_COLOR_SUCCESS_MESSAGE;
		public static String ADMIN_RESET_COLOR_COMMAND_SUCCESS_MESSAGE;
		public static String ADMIN_SET_FORCE_CHAT_COLOR_SUCCESS_MESSAGE;
		public static String ADMIN_SET_FORCE_NAME_COLOR_SUCCESS_MESSAGE;
		public static String ADMIN_SET_FORCE_CHAT_FORMAT_SUCCESS_MESSAGE;
		public static String ADMIN_SET_FORCE_NAME_FORMAT_SUCCESS_MESSAGE;
		public static String PLAYER_RESET_COLOR_COMMAND_SUCCESS_MESSAGE;
		public static String RESET_COLOR_COMMAND_ERROR_MESSAGE;
		public static String UNABLE_TO_SELECT_FORMAT_MESSAGE;

		private static void init() {
			setPathPrefix("Other");

			NO_PERMISSION = getString("No_Permission");
			ADMIN_SET_GRADIENT_COLOR_SUCCESS_MESSAGE = getString("Admin_Set_Gradient_Color_Success");
			ADMIN_RESET_COLOR_COMMAND_SUCCESS_MESSAGE = getString("Admin_Reset_Color_Command_Success");
			ADMIN_SET_FORCE_CHAT_COLOR_SUCCESS_MESSAGE = getString("Admin_Force_Chat_Color_Success");
			ADMIN_SET_FORCE_NAME_COLOR_SUCCESS_MESSAGE = getString("Admin_Force_Name_Color_Success");
			ADMIN_SET_FORCE_CHAT_FORMAT_SUCCESS_MESSAGE = getString("Admin_Force_Chat_Format_Success");
			ADMIN_SET_FORCE_NAME_FORMAT_SUCCESS_MESSAGE = getString("Admin_Force_Name_Format_Success");
			PLAYER_RESET_COLOR_COMMAND_SUCCESS_MESSAGE = getString("Player_Reset_Color_Command_Success");
			RESET_COLOR_COMMAND_ERROR_MESSAGE = getString("Reset_Color_Command_Error");
			UNABLE_TO_SELECT_FORMAT_MESSAGE = getString("Unable_To_Select_Format");
		}
	}
}
