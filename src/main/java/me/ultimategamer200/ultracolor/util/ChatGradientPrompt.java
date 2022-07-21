package me.ultimategamer200.ultracolor.util;

import me.ultimategamer200.ultracolor.PlayerCache;
import me.ultimategamer200.ultracolor.settings.Localization;
import me.ultimategamer200.ultracolor.settings.Settings;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.ConversationPrefix;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;
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
 * This prompt executes when a player selects their own chat gradient.
 */
public class ChatGradientPrompt extends SimpleConversation {
	@Override
	protected Prompt getFirstPrompt() {
		return new GradientFirstColor();
	}

	@Override
	protected ConversationPrefix getPrefix() {
		return new SimplePrefix(Localization.Gradient_Color_Selection_Custom.GRADIENT_PROMPT_PREFIX);
	}

	// An enum that stores the player prompt responses.
	enum GradientSelections {
		FIRST_COLOR,
		SECOND_COLOR,
		FORMAT
	}

	/**
	 * Asks the player's first hex color to be used.
	 */
	private static class GradientFirstColor extends SimplePrompt {
		@Override
		protected String getPrompt(ConversationContext conversationContext) {
			return Localization.Gradient_Color_Selection_Custom.FIRST_PROMPT;
		}

		@Override
		protected boolean isInputValid(ConversationContext context, String input) {
			if (input.length() == 6)
				return true;
			return input.length() == 7 && input.startsWith("#");
		}

		@Override
		protected String getFailedValidationText(ConversationContext context, String invalidInput) {
			return Localization.Hex_Colors.HEX_COLOR_ERROR_MESSAGE;
		}

		@Override
		protected @Nullable Prompt acceptValidatedInput(@NotNull ConversationContext conversationContext, @NotNull String input) {
			final String officialInput = input.length() == 6 ? "#" + input : input;
			conversationContext.setSessionData(GradientSelections.FIRST_COLOR, officialInput);
			return new GradientSecondColor();
		}
	}

	/**
	 * Asks the player's second hex color to be used.
	 */
	private static class GradientSecondColor extends SimplePrompt {
		@Override
		protected String getPrompt(ConversationContext conversationContext) {
			return Localization.Gradient_Color_Selection_Custom.SECOND_PROMPT;
		}

		@Override
		protected boolean isInputValid(ConversationContext context, String input) {
			if (input.length() == 6) return true;
			return input.length() == 7 && input.startsWith("#");
		}

		@Override
		protected String getFailedValidationText(ConversationContext context, String invalidInput) {
			return Localization.Hex_Colors.HEX_COLOR_ERROR_MESSAGE;
		}

		@Override
		protected @Nullable Prompt acceptValidatedInput(@NotNull ConversationContext conversationContext, @NotNull String input) {
			final String officialInput = input.length() == 6 ? "#" + input : input;
			conversationContext.setSessionData(GradientSelections.SECOND_COLOR, officialInput);
			if (UltraColorUtil.isAtLeastOneFormatEnabled("chat")) return new GradientFormat();

			applySelections(getPlayer(conversationContext), conversationContext, "none");
			return Prompt.END_OF_CONVERSATION;
		}
	}

	/**
	 * Asks the player wants a format to be used in the gradient.
	 */
	private static class GradientFormat extends SimplePrompt {
		@Override
		protected String getPrompt(ConversationContext conversationContext) {
			return Localization.Gradient_Color_Selection_Custom.FORMAT_PROMPT.replace("{format_options}",
					getAvailableOptions());
		}

		@Override
		protected boolean isInputValid(ConversationContext context, String input) {
			return input.equalsIgnoreCase("bold") || input.equalsIgnoreCase("italic")
					|| input.equalsIgnoreCase("strikethrough") || input.equalsIgnoreCase("magic")
					|| input.equalsIgnoreCase("underline") || input.equalsIgnoreCase("none");
		}

		@Override
		protected String getFailedValidationText(ConversationContext context, String invalidInput) {
			return "&cPlease type either &ebold, italic, underline, strikethrough, magic, or none&c.";
		}

		@Nullable
		@Override
		protected Prompt acceptValidatedInput(@NotNull ConversationContext conversationContext, @NotNull String input) {
			applySelections(getPlayer(conversationContext), conversationContext, input);
			return Prompt.END_OF_CONVERSATION;
		}
	}

	private static void applySelections(final Player player, final ConversationContext conversationContext, final String formatInput) {
		final PlayerCache pCache = PlayerCache.fromPlayer(player);

		if (!formatInput.equalsIgnoreCase("none"))
			pCache.setChatFormat(UltraColorUtil.getFormatToCompChatColor(formatInput));
		else pCache.setChatFormat(null);

		pCache.setChatColor(null);
		pCache.setChatRainbowColors(false);
		pCache.setChatCustomGradient1(CompChatColor.of(conversationContext.getSessionData(GradientSelections.FIRST_COLOR).toString()));
		pCache.setChatCustomGradient2(CompChatColor.of(conversationContext.getSessionData(GradientSelections.SECOND_COLOR).toString()));

		Messenger.success(player, Localization.Gradient_Color_Selection_Custom.SUCCESS_MESSAGE);
	}

	private static String getAvailableOptions() {
		final List<String> options = new ArrayList<>();

		if (Settings.Color_Settings_Chat_Formats.BOLD_FORMAT) options.add("bold");
		if (Settings.Color_Settings_Chat_Formats.ITALIC_FORMAT) options.add("italic");
		if (Settings.Color_Settings_Chat_Formats.MAGIC_FORMAT) options.add("magic");
		if (Settings.Color_Settings_Chat_Formats.STRIKETHROUGH_FORMAT) options.add("strikethrough");
		if (Settings.Color_Settings_Chat_Formats.UNDERLINE_FORMAT) options.add("underline");

		options.add("none");
		final StringBuilder stringBuilder = new StringBuilder();
		final Iterator<String> iterator = options.iterator();

		while (iterator.hasNext()) {
			final String option = iterator.next();

			if (!iterator.hasNext()) stringBuilder.append(option + ".");
			else stringBuilder.append(option + ", ");
		}

		return stringBuilder.toString();
	}
}
