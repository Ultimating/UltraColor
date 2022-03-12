package me.ultimategamer200.ultracolor.util;

import org.mineacademy.fo.annotation.Permission;
import org.mineacademy.fo.annotation.PermissionGroup;
import org.mineacademy.fo.constants.FoPermissions;

/**
 * Storage of permissions within the plugin. Useful to not type in the same permission strings over and over again.
 */
public final class UltraColorPermissions extends FoPermissions {
	// Non sub-commands
	@Permission("Opens the main color selection GUI.")
	public static final String COLOR = "ultracolor.gui";

	@Permission("Opens the chat color selection GUI.")
	public static final String CHAT_COLOR = "ultracolor.chatcolor";

	@Permission("Opens the name color selection GUI.")
	public static final String NAME_COLOR = "ultracolor.namecolor";

	@Permission("Opens the gradient selection GUI.")
	public static final String GRADIENT_COLOR = "ultracolor.command.gradientcolor";

	@PermissionGroup("Execute plugin commands.")
	public static final class Command {
		@Permission("Set your hex color.")
		public static final String HEX_COLOR = "ultracolor.command.hexcolor";

		@Permission("Set your nickname.")
		public static final String NICKNAME = "ultracolor.command.nickname";

		@Permission("Access to set nicknames of other players.")
		public static final String NICKNAME_OTHERS = "ultracolor.nickname.others";

		@Permission("See the real username of a nicked player.")
		public static final String REAL_NAME = "ultracolor.command.realname";

		// Sub-commands
		@Permission("Adds a whitelisted hex color for players to use.")
		public static final String ADD_ALLOWED_HEX_COLOR = "ultracolor.command.addhexcolor";

		@Permission("Adds a predefined gradient for players to use if they wish.")
		public static final String ADD_PRE_DEFINED_GRADIENT = "ultracolor.command.addpredefinedgradient";

		@Permission("Force a regular or hex color on a player. Hex colors require versions 1.16+!")
		public static final String FORCE_COLOR = "ultracolor.admin.forcecolor";

		@Permission("Force a gradient on a player. Requires versions 1.16+!")
		public static final String FORCE_GRADIENT = "ultracolor.admin.forcegradient";

		@Permission("Lists all pre-defined gradients made.")
		public static final String LIST_PRE_DEFINED_GRADIENTS = "ultracolor.command.listpregradients";

		@Permission("Reloads the plugin.")
		public static final String RELOAD = "ultracolor.admin.reload";

		@Permission("Removes a whitelisted hex color.")
		public static final String REMOVE_ALLOWED_HEX_COLOR = "ultracolor.command.removehexcolor";

		@Permission("Removes a pre-defined gradient.")
		public static final String REMOVE_PRE_DEFINED_GRADIENT = "ultracolor.command.removepredefinedgradient";

		@Permission("Resets a player's colors.")
		public static final String RESET_COLOR = "ultracolor.command.resetcolor";

		@Permission("Set your chat color by command.")
		public static final String SET_CHAT_COLOR = "ultracolor.command.chatcolor";

		@Permission("Set your gradient by command.")
		public static final String SET_GRADIENT = "ultracolor.command.setgradient";

		@Permission("Set your name color by command.")
		public static final String SET_NAME_COLOR = "ultracolor.command.namecolor";

		@Permission("List all nicknames stored by either current server or all servers connected to MySQL database.")
		public static final String LIST_NICKNAMES = "ultracolor.command.listnicknames";
	}

	@PermissionGroup("Use certain chat and/or name colors/gradients.")
	public static final class Color {
		@Permission("Give access to a specific chat color. Example: ultracolor.chat.2 gives access to dark green chat color). You can also use ultracolor.chat.r to give access to rainbow chat color or ultracolor.chat.* for all chat colors.")
		public static final String CHAT_COLOR = "ultracolor.chat.{color}";

		@Permission("Give access to a specific name color. You can also use ultracolor.name.r to give access to rainbow chat color or ultracolor.name.* for all name colors.")
		public static final String NAME_COLOR = "ultracolor.name.{color}";

		@Permission("Give access to a specific chat format.")
		public static final String CHAT_FORMAT = "ultracolor.chatformat.{format}";

		@Permission("Give access to a specific name format.")
		public static final String NAME_FORMAT = "ultracolor.nameformat.{format}";

		@Permission("Gives access to gradient name colors. You can also give ultracolor.gradient.* for all name gradients!")
		public static final String NAME_GRADIENTS = "ultracolor.gradient.{gradient-color}";

		@Permission("Gives access to gradient chat colors. You can also give ultracolor.chatgradient.* for all chat gradients!")
		public static final String CHAT_GRADIENTS = "ultracolor.chatgradient.{gradient-color}";
	}
}
