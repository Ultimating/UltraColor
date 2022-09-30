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
import org.mineacademy.fo.ChatUtil;
import org.mineacademy.fo.Messenger;
import org.mineacademy.fo.conversation.SimpleConversation;
import org.mineacademy.fo.conversation.SimplePrefix;
import org.mineacademy.fo.conversation.SimplePrompt;
import org.mineacademy.fo.remain.CompChatColor;

import java.util.ArrayList;
import java.util.List;

public class GradientPrompt extends SimpleConversation {
	@Override
	protected Prompt getFirstPrompt() {
		return new GradientFirstColor();
	}

	@Override
	protected ConversationPrefix getPrefix() {
		return new SimplePrefix(Localization.Gradient_Color_Selection_Custom.GRADIENT_PROMPT_PREFIX);
	}

	enum GradientSelections {
		FIRST_COLOR,
		SECOND_COLOR
	}

	private static class GradientFirstColor extends SimplePrompt {
		@Override
		protected String getPrompt(ConversationContext conversationContext) {
			return Localization.Gradient_Color_Selection_Custom.FIRST_PROMPT;
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
			conversationContext.setSessionData(GradientSelections.FIRST_COLOR, officialInput);
			return new GradientSecondColor();
		}
	}

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

			if (UltraColorUtil.isAtLeastOneFormatEnabled("name")) return new GradientFormat();

			applySelections(getPlayer(conversationContext), conversationContext, "none");
			return Prompt.END_OF_CONVERSATION;
		}
	}

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
			pCache.setNameFormat(UltraColorUtil.getNameFormatToChatColor(formatInput));
		else pCache.setNameFormat(null);

		final CompChatColor firstGradient = CompChatColor.of(conversationContext.getSessionData(GradientPrompt.GradientSelections.FIRST_COLOR).toString());
		final CompChatColor secondGradient = CompChatColor.of(conversationContext.getSessionData(GradientPrompt.GradientSelections.SECOND_COLOR).toString());

		pCache.setNameColor(null);
		pCache.setNameRainbowColors(false);
		pCache.setCustomGradient1(firstGradient);
		pCache.setCustomGradient2(secondGradient);
		String newDisplayName;

		if (pCache.getNameFormat() == null)
			newDisplayName = ChatUtil.generateGradient(player.getName(), pCache.getCustomGradient1(), pCache.getCustomGradient2());
		else {
			newDisplayName = ChatUtil.generateGradient(UltraColorUtil.nameFormatToString(pCache.getNameFormat())
					+ player.getName(), pCache.getCustomGradient1(), pCache.getCustomGradient2());
		}

		player.setDisplayName(newDisplayName);

		if (!pCache.getNickName().equalsIgnoreCase("none")) {
			if (pCache.getNameFormat() != null)
				pCache.setColoredNickName(ChatUtil.generateGradient(UltraColorUtil.nameFormatToString(pCache.getNameFormat())
						+ pCache.getNickName(), pCache.getCustomGradient1(), pCache.getCustomGradient2()));
			else
				pCache.setColoredNickName(ChatUtil.generateGradient(pCache.getNickName(), pCache.getCustomGradient1(), pCache.getCustomGradient2()));
			player.setDisplayName(pCache.getColoredNickName());
		}

		Messenger.success(player, Localization.Gradient_Color_Selection_Custom.SUCCESS_MESSAGE);
	}

	private static String getAvailableOptions() {
		final List<String> options = new ArrayList<>();

		if (Settings.Color_Settings_Name_Formats.BOLD_FORMAT) options.add("bold");
		if (Settings.Color_Settings_Name_Formats.ITALIC_FORMAT) options.add("italic");
		if (Settings.Color_Settings_Name_Formats.MAGIC_FORMAT) options.add("magic");
		if (Settings.Color_Settings_Name_Formats.STRIKETHROUGH_FORMAT) options.add("strikethrough");
		if (Settings.Color_Settings_Name_Formats.UNDERLINE_FORMAT) options.add("underline");

		return HexColorPrompt.FormatSelect.buildOptions(options);
	}
}
