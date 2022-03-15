package me.ultimategamer200.ultracolor.gradients;

import me.ultimategamer200.ultracolor.PlayerCache;
import me.ultimategamer200.ultracolor.settings.Localization;
import me.ultimategamer200.ultracolor.settings.Settings;
import me.ultimategamer200.ultracolor.util.ChatGradientPrompt;
import me.ultimategamer200.ultracolor.util.GradientPrompt;
import me.ultimategamer200.ultracolor.util.UltraColorPermissions;
import me.ultimategamer200.ultracolor.util.UltraColorUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.ChatUtil;
import org.mineacademy.fo.Messenger;
import org.mineacademy.fo.menu.Menu;
import org.mineacademy.fo.menu.MenuPagged;
import org.mineacademy.fo.menu.button.Button;
import org.mineacademy.fo.menu.model.ItemCreator;
import org.mineacademy.fo.remain.CompChatColor;

import java.util.ArrayList;
import java.util.List;

/**
 * This menu displays the pre-gradients made.
 */
public class PreDefinedGradientMenu extends Menu {
	/**
	 * The pre-defined gradients made for player name display.
	 */
	public static class PreDefinedNameGradientsMenu extends MenuPagged<PreDefinedGradient> {
		private final Button customNameGradientButton;
		private final Button resetButton;

		// Main Menu Configuration
		public PreDefinedNameGradientsMenu(final Menu parent) {
			super(parent, addPreDefinedGradients("name"));
			setTitle(Localization.Gradient_Color_Selection.NAME_MENU_TITLE);
			setSize(Localization.Gradient_Color_Selection_Name.NAME_MENU_SIZE);

			customNameGradientButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType clickType) {
					if (player.hasPermission(UltraColorPermissions.Color.NAME_GRADIENTS.replace("{gradient-color}", "custom"))
							|| player.hasPermission(UltraColorPermissions.Color.NAME_GRADIENTS.replace("{gradient-color}", "*")))
						new GradientPrompt().start(player);
					else
						Messenger.error(player, Localization.Gradient_Color_Selection.ERROR_MESSAGE);
				}

				// Gets the item to display in the menu for this button.
				@Override
				public ItemStack getItem() {
					return ItemCreator.of(Settings.Gradient_Color_Menu_Items.CUSTOM_ITEM,
							Localization.Gradient_Color_Selection_Custom.CUSTOM_NAME,
							Localization.Gradient_Color_Selection_Custom.CUSTOM_LORE).build().make();
				}
			};

			resetButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType clickType) {
					final PlayerCache pCache = PlayerCache.fromPlayer(player);

					if (pCache.getCustomGradient1() != null || pCache.getCustomGradient2() != null) {
						pCache.setCustomGradient1(null);
						pCache.setCustomGradient2(null);

						if (!pCache.getNickName().equalsIgnoreCase("none")) {
							if (pCache.getNameFormat() != null)
								player.setDisplayName(UltraColorUtil.nameFormatToString(pCache.getNameFormat()) + pCache.getNickName());
							else
								player.setDisplayName(pCache.getNickName());
							pCache.setColoredNickName(player.getDisplayName());
						} else {
							if (pCache.getNameFormat() != null)
								player.setDisplayName(UltraColorUtil.nameFormatToString(pCache.getNameFormat()) + player.getName());
							else
								player.setDisplayName(player.getName());
							pCache.setColoredNickName("none");
						}

						Messenger.success(player, Localization.Gradient_Color_Selection_Reset.SUCCESS_MESSAGE);
					} else
						Messenger.error(player, Localization.Gradient_Color_Selection_Reset.ERROR_MESSAGE);

					player.closeInventory();
				}

				@Override
				public ItemStack getItem() {
					return ItemCreator.of(Settings.Gradient_Color_Menu_Items.RESET_ITEM,
							Localization.Gradient_Color_Selection_Reset.RESET_NAME,
							Localization.Gradient_Color_Selection_Reset.RESET_LORE).build().make();
				}
			};
		}

		/**
		 * Converts each pre-defined gradient into an itemstack to be clicked on.
		 */
		@Override
		protected ItemStack convertToItemStack(PreDefinedGradient gradient) {
			final PreDefinedGradientSettings settings = gradient.getSettings();
			return ItemCreator.of(settings.getMenuItem(), settings.getDisplayName(), UltraColorUtil
					.modifyColorLoreWithPreview(settings.getMenuLore(), "gradient", settings.getHexColors())).build().make();
		}

		/**
		 * Ran when a pre-gradient is clicked on.
		 */
		@Override
		protected void onPageClick(Player player, PreDefinedGradient gradient, ClickType clickType) {
			final PreDefinedGradientSettings settings = gradient.getSettings();
			final PlayerCache pCache = PlayerCache.fromPlayer(player);

			if (player.hasPermission(settings.getPermission()) || player.hasPermission(UltraColorPermissions.Color.NAME_GRADIENTS
					.replace("{gradient-color}", "*"))) {
				final String hex1 = settings.getHexColors().get(0);
				final String hex2 = settings.getHexColors().get(1);
				pCache.setCustomGradient1(CompChatColor.of(hex1));
				pCache.setCustomGradient2(CompChatColor.of(hex2));
				pCache.setNameColor(null);
				String newDisplayName;

				if (pCache.getNameFormat() == null)
					newDisplayName = ChatUtil.generateGradient(player.getName(), CompChatColor.of(hex1), CompChatColor.of(hex2));
				else
					newDisplayName = ChatUtil.generateGradient(UltraColorUtil.nameFormatToString(pCache.getNameFormat())
							+ player.getName(), CompChatColor.of(hex1), CompChatColor.of(hex2));

				if (!pCache.getNickName().equalsIgnoreCase("none")) {
					if (pCache.getNameFormat() != null)
						newDisplayName = ChatUtil.generateGradient(UltraColorUtil.nameFormatToString(pCache.getNameFormat())
								+ pCache.getNickName(), CompChatColor.of(hex1), CompChatColor.of(hex2));
					else
						newDisplayName = ChatUtil.generateGradient(pCache.getNickName(), CompChatColor.of(hex1), CompChatColor.of(hex2));
					pCache.setColoredNickName(newDisplayName);
				}

				player.setDisplayName(newDisplayName);
				tellSuccess(settings.getSuccessMessage());
			} else
				tellError(settings.getErrorMessage());
			player.closeInventory();
		}

		/**
		 * Should we add the return button?
		 */
		@Override
		protected boolean addReturnButton() {
			return Localization.Gradient_Color_Selection_Return.ENABLED;
		}

		/**
		 * Puts the menu items in their correct configured spots.
		 */
		@Override
		public ItemStack getItemAt(int slot) {
			if (slot == Localization.Gradient_Color_Selection_Name.NAME_CUSTOM_GRADIENT_SLOT)
				return customNameGradientButton.getItem();
			if (slot == Localization.Gradient_Color_Selection_Name.NAME_RESET_GRADIENT_SLOT)
				return resetButton.getItem();
			if (slot == Localization.Gradient_Color_Selection_Name.NEXT_PAGE_SLOT)
				return formNextButton().getItem();
			if (slot == Localization.Gradient_Color_Selection_Name.PREVIOUS_PAGE_SLOT)
				return formPreviousButton().getItem();
			return super.getItemAt(slot);
		}

		@Override
		protected int getReturnButtonPosition() {
			return Localization.Gradient_Color_Selection_Return.MENU_SLOT;
		}
	}

	/**
	 * The pre-defined gradients made for player chat.
	 */
	public static class PreDefinedChatGradientsMenu extends MenuPagged<PreDefinedGradient> {
		private final Button customChatGradientButton;
		private final Button resetButton;

		// Allows a parent menu to return to when clicking the return button.
		public PreDefinedChatGradientsMenu(final Menu parent) {
			super(parent, addPreDefinedGradients("chat"));
			setTitle(Localization.Gradient_Color_Selection.CHAT_MENU_TITLE);
			setSize(Localization.Gradient_Color_Selection_Chat.CHAT_MENU_SIZE);

			customChatGradientButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType clickType) {
					if (player.hasPermission(UltraColorPermissions.Color.CHAT_GRADIENTS.replace("{gradient-color}", "custom"))
							|| player.hasPermission(UltraColorPermissions.Color.CHAT_GRADIENTS.replace("{gradient-color}", "*")))
						new ChatGradientPrompt().start(player);
					else
						Messenger.error(player, Localization.Gradient_Color_Selection.ERROR_MESSAGE);
				}

				@Override
				public ItemStack getItem() {
					return ItemCreator.of(Settings.Gradient_Color_Menu_Items.CUSTOM_ITEM,
							Localization.Gradient_Color_Selection_Custom.CUSTOM_NAME,
							Localization.Gradient_Color_Selection_Custom.CUSTOM_LORE).build().make();
				}
			};

			resetButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType clickType) {
					final PlayerCache pCache = PlayerCache.fromPlayer(player);

					if (pCache.getChatCustomGradient1() != null || pCache.getChatCustomGradient2() != null) {
						pCache.setChatCustomGradient1(null);
						pCache.setChatCustomGradient2(null);
						Messenger.success(player, Localization.Gradient_Color_Selection_Reset.SUCCESS_MESSAGE);
					} else
						Messenger.error(player, Localization.Gradient_Color_Selection_Reset.ERROR_MESSAGE);

					player.closeInventory();
				}

				@Override
				public ItemStack getItem() {
					return ItemCreator.of(Settings.Gradient_Color_Menu_Items.RESET_ITEM,
							Localization.Gradient_Color_Selection_Reset.RESET_NAME,
							Localization.Gradient_Color_Selection_Reset.RESET_LORE).build().make();
				}
			};
		}

		@Override
		protected ItemStack convertToItemStack(PreDefinedGradient gradient) {
			final PreDefinedGradientSettings settings = gradient.getSettings();
			return ItemCreator.of(settings.getMenuItem(), settings.getDisplayName(), UltraColorUtil
					.modifyColorLoreWithPreview(settings.getMenuLore(), "gradient", settings.getHexColors())).build().make();
		}

		@Override
		protected void onPageClick(Player player, PreDefinedGradient gradient, ClickType clickType) {
			final PreDefinedGradientSettings settings = gradient.getSettings();
			final PlayerCache pCache = PlayerCache.fromPlayer(player);

			if (player.hasPermission(settings.getPermission()) || player.hasPermission(UltraColorPermissions.Color.CHAT_GRADIENTS
					.replace("{gradient-color}", "*"))) {
				final String hex1 = settings.getHexColors().get(0);
				final String hex2 = settings.getHexColors().get(1);
				pCache.setChatCustomGradient1(CompChatColor.of(hex1));
				pCache.setChatCustomGradient2(CompChatColor.of(hex2));
				pCache.setChatColor(null);
				tellSuccess(settings.getSuccessMessage());
			} else
				tellError(settings.getErrorMessage());
			player.closeInventory();
		}

		@Override
		protected boolean addReturnButton() {
			return Localization.Gradient_Color_Selection_Return.ENABLED;
		}

		@Override
		public ItemStack getItemAt(int slot) {
			if (slot == Localization.Gradient_Color_Selection_Chat.CHAT_CUSTOM_GRADIENT_SLOT)
				return customChatGradientButton.getItem();
			if (slot == Localization.Gradient_Color_Selection_Chat.CHAT_RESET_GRADIENT_SLOT)
				return resetButton.getItem();
			if (slot == Localization.Gradient_Color_Selection_Chat.NEXT_PAGE_SLOT)
				return formNextButton().getItem();
			if (slot == Localization.Gradient_Color_Selection_Chat.PREVIOUS_PAGE_SLOT)
				return formPreviousButton().getItem();
			return super.getItemAt(slot);
		}

		@Override
		protected int getReturnButtonPosition() {
			return Localization.Gradient_Color_Selection_Return.MENU_SLOT;
		}
	}

	/**
	 * Adds all the pre-gradients of the specified type or "both"
	 */
	private static List<PreDefinedGradient> addPreDefinedGradients(final String type) {
		final List<PreDefinedGradient> gradients = new ArrayList<>();

		for (final PreDefinedGradient gradient : PreDefinedGradientManager.getLoadedPreDefinedGradients()) {
			if (gradient.getSettings().getType().equalsIgnoreCase(type) || gradient.getSettings().getType()
					.equalsIgnoreCase("both")) {
				if (canGradientDisplayInMenu(gradient))
					gradients.add(gradient);
			}
		}

		return gradients;
	}

	/**
	 * Can this gradient be displayed in the menu?
	 */
	private static boolean canGradientDisplayInMenu(final PreDefinedGradient gradient) {
		final PreDefinedGradientSettings settings = gradient.getSettings();

		return settings.getHexColors().size() == 2 && settings.getMenuItem() != null && settings.getMenuLore() != null
				&& UltraColorUtil.areHexesValid(settings.getHexColors());
	}
}
