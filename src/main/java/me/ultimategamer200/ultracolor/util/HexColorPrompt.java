package me.ultimategamer200.ultracolor.util;

import me.ultimategamer200.ultracolor.PlayerCache;
import me.ultimategamer200.ultracolor.settings.Localization;
import me.ultimategamer200.ultracolor.settings.Settings;
import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.ConversationPrefix;
import org.bukkit.conversations.Prompt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mineacademy.fo.Messenger;
import org.mineacademy.fo.conversation.SimpleConversation;
import org.mineacademy.fo.conversation.SimplePrefix;
import org.mineacademy.fo.conversation.SimplePrompt;
import org.mineacademy.fo.remain.CompChatColor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Executes this prompt if the player is selecting a hex color from the /color menu.
 */
public class HexColorPrompt extends SimpleConversation {
	@Override
	protected Prompt getFirstPrompt() {
		return new HexColorType();
	}

	@Override
	protected ConversationPrefix getPrefix() {
		return new SimplePrefix(Localization.Hex_Colors.HEX_PROMPT_PREFIX);
	}

	enum Selections {
		HEX_COLOR
	}

	public static class HexColorType extends SimplePrompt {
		@Override
		protected String getPrompt(ConversationContext conversationContext) {
			return Localization.Hex_Colors.FIRST_PROMPT.replace("%hex_color_types%", getPartAvailable());
		}

		@Override
		protected boolean isInputValid(ConversationContext context, String input) {
			if (input.equalsIgnoreCase("chat") && Settings.Color_Settings.CHAT_HEX_COLORS)
				return true;
			return input.equalsIgnoreCase("name") && Settings.Color_Settings.NAME_HEX_COLORS;
		}

		@Override
		protected String getFailedValidationText(ConversationContext context, String invalidInput) {
			return "Invalid input. Available: &e" + getPartAvailable();
		}

		private String getPartAvailable() {
			String partAvailable = "chat & name";

			if (Settings.Color_Settings.CHAT_HEX_COLORS && !Settings.Color_Settings.NAME_HEX_COLORS)
				partAvailable = "chat";
			else if (!Settings.Color_Settings.CHAT_HEX_COLORS && Settings.Color_Settings.NAME_HEX_COLORS)
				partAvailable = "name";

			return partAvailable;
		}

		@Nullable
		@Override
		protected Prompt acceptValidatedInput(@NotNull ConversationContext conversationContext, @NotNull String input) {
			return new HexColorSelect(input);
		}
	}

	public static class HexColorSelect extends SimplePrompt {
		private final String type;

		private HexColorSelect(final String type) {
			this.type = type;
		}

		@Override
		protected String getPrompt(ConversationContext conversationContext) {
			return Localization.Hex_Colors.SECOND_PROMPT;
		}

		@Override
		protected boolean isInputValid(ConversationContext context, String input) {
			if (input.startsWith("#") && input.length() == 7)
				return true;
			return input.length() == 6;
		}

		@Override
		protected String getFailedValidationText(ConversationContext context, String invalidInput) {
			return Localization.Hex_Colors.HEX_COLOR_ERROR_MESSAGE;
		}

		@Nullable
		@Override
		protected Prompt acceptValidatedInput(@NotNull ConversationContext conversationContext, @NotNull String input) {
			final String hexColor = input.length() == 6 ? "#" + input : input;
			conversationContext.setSessionData(Selections.HEX_COLOR, hexColor);
			return new FormatSelect(type);
		}
	}

	public static class FormatSelect extends SimplePrompt {
		private final String type;

		private FormatSelect(final String type) {
			this.type = type;
		}

		@Override
		protected String getPrompt(ConversationContext conversationContext) {
			final String options = this.type.equalsIgnoreCase("name") ? getAvailableNameOptions() : getAvailableChatOptions();
			return Localization.Hex_Colors.FORMAT_PROMPT.replace("{format_options}", options);
		}

		@Override
		protected boolean isInputValid(ConversationContext context, String input) {
			return ColorId.FormatId.getFormatIds().contains(input.toLowerCase()) || input.equalsIgnoreCase("none");
		}

		@Override
		protected String getFailedValidationText(ConversationContext context, String invalidInput) {
			return "&cPlease type either &ebold, italic, underline, strikethrough, magic, or none&c.";
		}

		@Nullable
		@Override
		protected Prompt acceptValidatedInput(@NotNull ConversationContext conversationContext, @NotNull String input) {
			final PlayerCache pCache = PlayerCache.fromPlayer(getPlayer(conversationContext));

			pCache.setChatFormat(null);
			pCache.setNameFormat(null);

			final String hex = conversationContext.getSessionData(Selections.HEX_COLOR).toString();
			final CompChatColor hexColor = CompChatColor.of(hex);
			boolean addFormat = false;
			ChatColor format = null;

			if (!input.equalsIgnoreCase("none")) {
				if (type.equalsIgnoreCase("chat"))
					pCache.setChatFormat(UltraColorUtil.getFormatToCompChatColor(input));
				else
					pCache.setNameFormat(UltraColorUtil.getNameFormatToChatColor(input));
				addFormat = true;
				format = UltraColorUtil.getNameFormatToChatColor(input);
			}

			if (type.equalsIgnoreCase("chat")) {
				pCache.setChatColor(hexColor);
				pCache.setChatRainbowColors(false);
				pCache.clearGradients("chat");
			} else {
				pCache.setNameColor(hexColor);
				pCache.setNameRainbowColors(false);
				pCache.clearGradients("name");

				if (!pCache.getColoredNickName().equalsIgnoreCase("none")) {
					if (pCache.getNameFormat() != null)
						pCache.setColoredNickName(UltraColorUtil.nameAndChatColorToString(pCache.getNameColor()) + UltraColorUtil
								.nameFormatToString(pCache.getNameFormat()) + pCache.getNickName());
					else
						pCache.setColoredNickName(UltraColorUtil.nameAndChatColorToString(pCache.getNameColor()) + pCache.getNickName());
				}

				getPlayer(conversationContext).setDisplayName(UltraColorUtil.getPlayerNameInColor(getPlayer(conversationContext)));
			}

			String hexDisplay = hexColor + "this";
			if (addFormat) hexDisplay = hexColor + UltraColorUtil.nameFormatToString(format) + "this";

			Messenger.success(getPlayer(conversationContext), Localization.Hex_Colors.HEX_COLOR_SUCCESS_MESSAGE.replace(
					"%hex_color%", hexDisplay));
			return Prompt.END_OF_CONVERSATION;
		}

		private static String getAvailableChatOptions() {
			final List<String> options = new ArrayList<>();

			if (Settings.Color_Settings_Chat_Formats.BOLD_FORMAT) options.add("bold");
			if (Settings.Color_Settings_Chat_Formats.ITALIC_FORMAT) options.add("italic");
			if (Settings.Color_Settings_Chat_Formats.MAGIC_FORMAT) options.add("magic");
			if (Settings.Color_Settings_Chat_Formats.STRIKETHROUGH_FORMAT) options.add("strikethrough");
			if (Settings.Color_Settings_Chat_Formats.UNDERLINE_FORMAT) options.add("underline");

			return buildOptions(options);
		}

		private static String getAvailableNameOptions() {
			final List<String> options = new ArrayList<>();

			if (Settings.Color_Settings_Name_Formats.BOLD_FORMAT) options.add("bold");
			if (Settings.Color_Settings_Name_Formats.ITALIC_FORMAT) options.add("italic");
			if (Settings.Color_Settings_Name_Formats.MAGIC_FORMAT) options.add("magic");
			if (Settings.Color_Settings_Name_Formats.STRIKETHROUGH_FORMAT) options.add("strikethrough");
			if (Settings.Color_Settings_Name_Formats.UNDERLINE_FORMAT) options.add("underline");

			return buildOptions(options);
		}

		public static String buildOptions(final List<String> options) {
			options.add("none");
			final StringBuilder stringBuilder = new StringBuilder();
			final Iterator<String> iterator = options.iterator();

			while (iterator.hasNext()) {
				final String option = iterator.next();

				if (!iterator.hasNext()) stringBuilder.append(option).append(".");
				else stringBuilder.append(option).append(", ");
			}

			return stringBuilder.toString();
		}
	}
}
