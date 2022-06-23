package me.ultimategamer200.ultracolor.menu;

import me.ultimategamer200.ultracolor.PlayerCache;
import me.ultimategamer200.ultracolor.settings.Localization;
import me.ultimategamer200.ultracolor.settings.Settings;
import me.ultimategamer200.ultracolor.util.HexColorPrompt;
import me.ultimategamer200.ultracolor.util.UltraColorPermissions;
import me.ultimategamer200.ultracolor.util.UltraColorUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.ChatUtil;
import org.mineacademy.fo.Messenger;
import org.mineacademy.fo.menu.Menu;
import org.mineacademy.fo.menu.button.Button;
import org.mineacademy.fo.menu.model.ItemCreator;
import org.mineacademy.fo.remain.CompChatColor;
import org.mineacademy.fo.remain.CompMaterial;
import org.mineacademy.fo.remain.Remain;

/**
 * The main color GUI when using /color.
 */
public class ColorSelectionMenu extends Menu {
	/**
	 * Opens up the chat color menu.
	 */
	private final Button chatColorButton;

	/**
	 * Opens up the name color menu.
	 */
	private final Button nameColorButton;

	/**
	 * A shortcut to the menu brought by /gradientcolor.
	 */
	private final Button gradientButton;

	/**
	 * A shortcut to using /hexcolor.
	 */
	private final Button hexColorButton;

	/**
	 * Fills any empty spots of the menu with this button.
	 */
	private final Button emptyButton;

	public ColorSelectionMenu() {
		setTitle(Localization.Menu_Titles.COLOR_SELECTION_MENU_TITLE);
		setSize(Localization.Main_GUI_Customization.MENU_SIZE);

		chatColorButton = new Button() {
			@Override
			public void onClickedInMenu(Player player, Menu menu, ClickType clickType) {
				if (player.hasPermission(UltraColorPermissions.COLOR) || player.hasPermission(UltraColorPermissions.CHAT_COLOR)) {
					new ChatColorSelectionMenu().displayTo(player);
				} else {
					tellError(Localization.Other.NO_PERMISSION.replace("{permission}", UltraColorPermissions.COLOR));
					player.closeInventory();
				}
			}

			@Override
			public ItemStack getItem() {
				return ItemCreator.of(CompMaterial.valueOf(Settings.Color_Settings.CHAT_COLOR_BUTTON),
						Localization.Main_GUI_Customization_Chat_Color_Selection.CHAT_COLOR_NAME,
						Localization.Main_GUI_Customization_Chat_Color_Selection.CHAT_COLOR_LORE).make();
			}
		};
		nameColorButton = new Button() {
			@Override
			public void onClickedInMenu(Player player, Menu menu, ClickType clickType) {
				if (player.hasPermission(UltraColorPermissions.COLOR) || player.hasPermission(UltraColorPermissions.NAME_COLOR)) {
					new NameColorSelectionMenu().displayTo(player);
				} else {
					tellError(Localization.Other.NO_PERMISSION.replace("{permission}", UltraColorPermissions.COLOR));
					player.closeInventory();
				}
			}

			@Override
			public ItemStack getItem() {
				return ItemCreator.of(CompMaterial.valueOf(Settings.Color_Settings.NAME_COLOR_BUTTON),
						Localization.Main_GUI_Customization_Name_Color_Selection.NAME_COLOR_NAME,
						Localization.Main_GUI_Customization_Name_Color_Selection.NAME_COLOR_LORE).make();
			}
		};

		gradientButton = new Button() {
			@Override
			public void onClickedInMenu(Player player, Menu menu, ClickType clickType) {
				if (player.hasPermission(UltraColorPermissions.GRADIENT_COLOR)) {
					new GradientMenu().displayTo(player);
				} else {
					tellError(Localization.Other.NO_PERMISSION.replace("{permission}", UltraColorPermissions.GRADIENT_COLOR));
					player.closeInventory();
				}
			}

			@Override
			public ItemStack getItem() {
				return ItemCreator.of(CompMaterial.BEACON, Localization.Main_GUI_Customization_Gradient_Selection.MENU_NAME,
						Localization.Main_GUI_Customization_Gradient_Selection.GRADIENT_COLOR_LORE).make();
			}
		};

		hexColorButton = new Button() {
			@Override
			public void onClickedInMenu(Player player, Menu menu, ClickType clickType) {
				if (player.hasPermission(UltraColorPermissions.Command.HEX_COLOR)) {
					new HexColorPrompt().start(player);
				} else {
					tellError(Localization.Other.NO_PERMISSION.replace("{permission}", UltraColorPermissions.Command.HEX_COLOR));
					player.closeInventory();
				}
			}

			@Override
			public ItemStack getItem() {
				return ItemCreator.of(CompMaterial.valueOf(Settings.Other.HEX_COLOR_ITEM), Localization.Main_GUI_Customization_Hex_Selection.MENU_NAME,
						Localization.Main_GUI_Customization_Hex_Selection.HEX_COLOR_LORE).make();
			}
		};

		emptyButton = Button.makeDummy(ItemCreator.of(CompMaterial.valueOf(Settings.Color_Settings.CHAT_FILL_BUTTON), " "));
	}

	@Override
	protected boolean addInfoButton() {
		return Localization.Main_GUI_Customization.ALLOW_INFO_BUTTON;
	}

	@Override
	protected int getInfoButtonPosition() {
		return Localization.Main_GUI_Customization.INFO_BUTTON_SLOT;
	}

	@Override
	public ItemStack getItemAt(int slot) {
		if (slot == Localization.Main_GUI_Customization_Chat_Color_Selection.MENU_SLOT && Settings.Color_Settings.CHAT_COLORS)
			return chatColorButton.getItem();
		if (slot == Localization.Main_GUI_Customization_Name_Color_Selection.MENU_SLOT && Settings.Color_Settings.NAME_COLORS)
			return nameColorButton.getItem();
		if (Remain.hasHexColors()) {
			if (slot == Localization.Main_GUI_Customization_Gradient_Selection.MENU_SLOT)
				if (Settings.Color_Settings.CHAT_GRADIENT_COLORS || Settings.Color_Settings.NAME_GRADIENT_COLORS)
					return gradientButton.getItem();
			if (slot == Localization.Main_GUI_Customization_Hex_Selection.MENU_SLOT)
				if (Settings.Color_Settings.NAME_HEX_COLORS || Settings.Color_Settings.CHAT_HEX_COLORS)
					return hexColorButton.getItem();
		}

		return emptyButton.getItem();
	}

	@Override
	protected String[] getInfo() {
		return new String[]{Localization.Main_GUI_Customization.INFO_MESSAGE};
	}

	public static final class ChatColorSelectionMenu extends Menu {
		private final Button darkAquaButton;
		private final Button blackButton;
		private final Button darkBlueButton;

		private final Button darkGreenButton;
		private final Button darkRedButton;
		private final Button darkPurpleButton;

		private final Button goldButton;
		private final Button grayButton;
		private final Button darkGrayButton;

		private final Button blueButton;
		private final Button greenButton;
		private final Button aquaButton;

		private final Button redButton;
		private final Button lightPurpleButton;
		private final Button yellowButton;

		private final Button whiteButton;
		private final Button magicFormatButton;
		private final Button boldFormatButton;

		private final Button italicFormatButton;
		private final Button underlineFormatButton;
		private final Button strikethroughFormatButton;

		private final Button resetFormatButton;
		private final Button rainbowButton;
		private final Button backButton;

		private final Button resetButton;
		private final Button emptyButton;

		public ChatColorSelectionMenu() {
			setTitle(Localization.Menu_Titles.CHAT_COLOR_SELECTION_MENU_TITLE);
			setSize(Localization.Main_GUI_Customization_Chat_Color_Selection.MENU_SIZE);

			blackButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType clickType) {
					if (player.hasPermission(UltraColorPermissions.Color.CHAT_COLOR.replace("{color}", "0"))
							|| player.hasPermission(UltraColorPermissions.Color.CHAT_COLOR.replace("{color}", "*"))) {
						UltraColorUtil.applyChatColor(player, CompChatColor.BLACK);
						Messenger.success(player, Localization.Chat_Color_Selection_Customization_Black.BLACK_SUCCESS);
					} else
						Messenger.error(player, Localization.Main_GUI_Customization_Chat_Color_Selection.ERROR_MESSAGE);
					player.closeInventory();
				}

				@Override
				public ItemStack getItem() {
					return UltraColorUtil.makeMenuItem(Settings.Chat_Color_Menu_Items.BLACK_ITEM, Localization
							.Chat_Color_Selection_Customization_Black.BLACK_NAME, UltraColorUtil.modifyColorLoreWithPreview(Localization
							.Chat_Color_Selection_Customization_Black.BLACK_LORE, "§0", null));
				}
			};

			darkBlueButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType clickType) {
					if (player.hasPermission(UltraColorPermissions.Color.CHAT_COLOR.replace("{color}", "1"))
							|| player.hasPermission(UltraColorPermissions.Color.CHAT_COLOR.replace("{color}", "*"))) {
						UltraColorUtil.applyChatColor(player, CompChatColor.DARK_BLUE);
						Messenger.success(player, Localization.Chat_Color_Selection_Customization_Dark_Blue.DARK_BLUE_SUCCESS);
					} else
						Messenger.error(player, Localization.Main_GUI_Customization_Chat_Color_Selection.ERROR_MESSAGE);
					player.closeInventory();
				}

				@Override
				public ItemStack getItem() {
					return UltraColorUtil.makeMenuItem(Settings.Chat_Color_Menu_Items.DARK_BLUE_ITEM,
							Localization.Chat_Color_Selection_Customization_Dark_Blue.DARK_BLUE_NAME,
							UltraColorUtil.modifyColorLoreWithPreview(Localization.Chat_Color_Selection_Customization_Dark_Blue.DARK_BLUE_LORE,
									"§1", null));
				}
			};

			darkGreenButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType clickType) {
					if (player.hasPermission(UltraColorPermissions.Color.CHAT_COLOR.replace("{color}", "2"))
							|| player.hasPermission(UltraColorPermissions.Color.CHAT_COLOR.replace("{color}", "*"))) {
						UltraColorUtil.applyChatColor(player, CompChatColor.DARK_GREEN);
						Messenger.success(player, Localization.Chat_Color_Selection_Customization_Dark_Green.DARK_GREEN_SUCCESS);
					} else
						Messenger.error(player, Localization.Main_GUI_Customization_Chat_Color_Selection.ERROR_MESSAGE);
					player.closeInventory();
				}

				@Override
				public ItemStack getItem() {
					return UltraColorUtil.makeMenuItem(Settings.Chat_Color_Menu_Items.DARK_GREEN_ITEM,
							Localization.Chat_Color_Selection_Customization_Dark_Green.DARK_GREEN_NAME,
							UltraColorUtil.modifyColorLoreWithPreview(Localization.Chat_Color_Selection_Customization_Dark_Green.DARK_GREEN_LORE,
									"§2", null));
				}
			};

			darkAquaButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType clickType) {
					if (player.hasPermission(UltraColorPermissions.Color.CHAT_COLOR.replace("{color}", "3"))
							|| player.hasPermission(UltraColorPermissions.Color.CHAT_COLOR.replace("{color}", "*"))) {
						UltraColorUtil.applyChatColor(player, CompChatColor.DARK_AQUA);
						Messenger.success(player, Localization.Chat_Color_Selection_Customization_Dark_Aqua.DARK_AQUA_SUCCESS);
					} else
						Messenger.error(player, Localization.Main_GUI_Customization_Chat_Color_Selection.ERROR_MESSAGE);
					player.closeInventory();
				}

				@Override
				public ItemStack getItem() {
					return UltraColorUtil.makeMenuItem(Settings.Chat_Color_Menu_Items.DARK_AQUA_ITEM,
							Localization.Chat_Color_Selection_Customization_Dark_Aqua.DARK_AQUA_NAME,
							UltraColorUtil.modifyColorLoreWithPreview(Localization.Chat_Color_Selection_Customization_Dark_Aqua.DARK_AQUA_LORE,
									"§3", null));
				}
			};

			darkRedButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType clickType) {
					if (player.hasPermission(UltraColorPermissions.Color.CHAT_COLOR.replace("{color}", "4"))
							|| player.hasPermission(UltraColorPermissions.Color.CHAT_COLOR.replace("{color}", "*"))) {
						UltraColorUtil.applyChatColor(player, CompChatColor.DARK_RED);
						Messenger.success(player, Localization.Chat_Color_Selection_Customization_Dark_Red.DARK_RED_SUCCESS);
					} else
						Messenger.error(player, Localization.Main_GUI_Customization_Chat_Color_Selection.ERROR_MESSAGE);
					player.closeInventory();
				}

				@Override
				public ItemStack getItem() {
					return UltraColorUtil.makeMenuItem(Settings.Chat_Color_Menu_Items.DARK_RED_ITEM,
							Localization.Chat_Color_Selection_Customization_Dark_Red.DARK_RED_NAME,
							UltraColorUtil.modifyColorLoreWithPreview(Localization.Chat_Color_Selection_Customization_Dark_Red.DARK_RED_LORE,
									"§4", null));
				}
			};

			darkPurpleButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType clickType) {
					if (player.hasPermission(UltraColorPermissions.Color.CHAT_COLOR.replace("{color}", "5"))
							|| player.hasPermission(UltraColorPermissions.Color.CHAT_COLOR.replace("{color}", "*"))) {
						UltraColorUtil.applyChatColor(player, CompChatColor.DARK_PURPLE);
						Messenger.success(player, Localization.Chat_Color_Selection_Customization_Dark_Purple.DARK_PURPLE_SUCCESS);
					} else
						Messenger.error(player, Localization.Main_GUI_Customization_Chat_Color_Selection.ERROR_MESSAGE);
					player.closeInventory();
				}

				@Override
				public ItemStack getItem() {
					return UltraColorUtil.makeMenuItem(Settings.Chat_Color_Menu_Items.DARK_PURPLE_ITEM,
							Localization.Chat_Color_Selection_Customization_Dark_Purple.DARK_PURPLE_NAME,
							UltraColorUtil.modifyColorLoreWithPreview(Localization.Chat_Color_Selection_Customization_Dark_Purple.DARK_PURPLE_LORE,
									"§5", null));
				}
			};

			goldButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType clickType) {
					if (player.hasPermission(UltraColorPermissions.Color.CHAT_COLOR.replace("{color}", "6"))
							|| player.hasPermission(UltraColorPermissions.Color.CHAT_COLOR.replace("{color}", "*"))) {
						UltraColorUtil.applyChatColor(player, CompChatColor.GOLD);
						Messenger.success(player, Localization.Chat_Color_Selection_Customization_Orange.ORANGE_SUCCESS);
					} else
						Messenger.error(player, Localization.Main_GUI_Customization_Chat_Color_Selection.ERROR_MESSAGE);
					player.closeInventory();
				}

				@Override
				public ItemStack getItem() {
					return UltraColorUtil.makeMenuItem(Settings.Chat_Color_Menu_Items.ORANGE_ITEM,
							Localization.Chat_Color_Selection_Customization_Orange.ORANGE_NAME,
							UltraColorUtil.modifyColorLoreWithPreview(Localization.Chat_Color_Selection_Customization_Orange.ORANGE_LORE,
									"§6", null));
				}
			};

			grayButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType clickType) {
					if (player.hasPermission(UltraColorPermissions.Color.CHAT_COLOR.replace("{color}", "7"))
							|| player.hasPermission(UltraColorPermissions.Color.CHAT_COLOR.replace("{color}", "*"))) {
						UltraColorUtil.applyChatColor(player, CompChatColor.GRAY);
						Messenger.success(player, Localization.Chat_Color_Selection_Customization_Gray.GRAY_SUCCESS);
					} else
						Messenger.error(player, Localization.Main_GUI_Customization_Chat_Color_Selection.ERROR_MESSAGE);
					player.closeInventory();
				}

				@Override
				public ItemStack getItem() {
					return UltraColorUtil.makeMenuItem(Settings.Chat_Color_Menu_Items.GRAY_ITEM,
							Localization.Chat_Color_Selection_Customization_Gray.GRAY_NAME,
							UltraColorUtil.modifyColorLoreWithPreview(Localization.Chat_Color_Selection_Customization_Gray.GRAY_LORE,
									"§7", null));
				}
			};

			darkGrayButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType clickType) {
					if (player.hasPermission(UltraColorPermissions.Color.CHAT_COLOR.replace("{color}", "8"))
							|| player.hasPermission(UltraColorPermissions.Color.CHAT_COLOR.replace("{color}", "*"))) {
						UltraColorUtil.applyChatColor(player, CompChatColor.DARK_GRAY);
						Messenger.success(player, Localization.Chat_Color_Selection_Customization_Dark_Gray.DARK_GRAY_SUCCESS);
					} else
						Messenger.error(player, Localization.Main_GUI_Customization_Chat_Color_Selection.ERROR_MESSAGE);
					player.closeInventory();
				}

				@Override
				public ItemStack getItem() {
					return UltraColorUtil.makeMenuItem(Settings.Chat_Color_Menu_Items.DARK_GRAY_ITEM,
							Localization.Chat_Color_Selection_Customization_Dark_Gray.DARK_GRAY_NAME,
							UltraColorUtil.modifyColorLoreWithPreview(Localization.Chat_Color_Selection_Customization_Dark_Gray.DARK_GRAY_LORE,
									"§8", null));
				}
			};

			blueButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType clickType) {
					if (player.hasPermission(UltraColorPermissions.Color.CHAT_COLOR.replace("{color}", "9"))
							|| player.hasPermission(UltraColorPermissions.Color.CHAT_COLOR.replace("{color}", "*"))) {
						UltraColorUtil.applyChatColor(player, CompChatColor.BLUE);
						Messenger.success(player, Localization.Chat_Color_Selection_Customization_Blue.BLUE_SUCCESS);
					} else
						Messenger.error(player, Localization.Main_GUI_Customization_Chat_Color_Selection.ERROR_MESSAGE);
					player.closeInventory();
				}

				@Override
				public ItemStack getItem() {
					return UltraColorUtil.makeMenuItem(Settings.Chat_Color_Menu_Items.BLUE_ITEM, Localization.Chat_Color_Selection_Customization_Blue.BLUE_NAME,
							UltraColorUtil.modifyColorLoreWithPreview(Localization.Chat_Color_Selection_Customization_Blue.BLUE_LORE,
									"§9", null));
				}
			};

			greenButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType clickType) {
					if (player.hasPermission(UltraColorPermissions.Color.CHAT_COLOR.replace("{color}", "a"))
							|| player.hasPermission(UltraColorPermissions.Color.CHAT_COLOR.replace("{color}", "*"))) {
						UltraColorUtil.applyChatColor(player, CompChatColor.GREEN);
						Messenger.success(player, Localization.Chat_Color_Selection_Customization_Green.GREEN_SUCCESS);
					} else {
						Messenger.error(player, Localization.Main_GUI_Customization_Chat_Color_Selection.ERROR_MESSAGE);
					}
					player.closeInventory();
				}

				@Override
				public ItemStack getItem() {
					return UltraColorUtil.makeMenuItem(Settings.Chat_Color_Menu_Items.GREEN_ITEM, Localization.Chat_Color_Selection_Customization_Green.GREEN_NAME,
							UltraColorUtil.modifyColorLoreWithPreview(Localization.Chat_Color_Selection_Customization_Green.GREEN_LORE,
									"§a", null));
				}
			};

			aquaButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType clickType) {
					if (player.hasPermission(UltraColorPermissions.Color.CHAT_COLOR.replace("{color}", "b"))
							|| player.hasPermission(UltraColorPermissions.Color.CHAT_COLOR.replace("{color}", "*"))) {
						UltraColorUtil.applyChatColor(player, CompChatColor.AQUA);
						Messenger.success(player, Localization.Chat_Color_Selection_Customization_Aqua.AQUA_SUCCESS);
					} else
						Messenger.error(player, Localization.Main_GUI_Customization_Chat_Color_Selection.ERROR_MESSAGE);
					player.closeInventory();
				}

				@Override
				public ItemStack getItem() {
					return UltraColorUtil.makeMenuItem(Settings.Chat_Color_Menu_Items.AQUA_ITEM, Localization.Chat_Color_Selection_Customization_Aqua.AQUA_NAME,
							UltraColorUtil.modifyColorLoreWithPreview(Localization.Chat_Color_Selection_Customization_Aqua.AQUA_LORE,
									"§b", null));
				}
			};

			redButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType clickType) {
					if (player.hasPermission(UltraColorPermissions.Color.CHAT_COLOR.replace("{color}", "c"))
							|| player.hasPermission(UltraColorPermissions.Color.CHAT_COLOR.replace("{color}", "*"))) {
						UltraColorUtil.applyChatColor(player, CompChatColor.RED);
						Messenger.success(player, Localization.Chat_Color_Selection_Customization_Red.RED_SUCCESS);
					} else
						Messenger.error(player, Localization.Main_GUI_Customization_Chat_Color_Selection.ERROR_MESSAGE);
					player.closeInventory();
				}

				@Override
				public ItemStack getItem() {
					return UltraColorUtil.makeMenuItem(Settings.Chat_Color_Menu_Items.RED_ITEM, Localization.Chat_Color_Selection_Customization_Red.RED_NAME,
							UltraColorUtil.modifyColorLoreWithPreview(Localization.Chat_Color_Selection_Customization_Red.RED_LORE,
									"§c", null));
				}
			};

			lightPurpleButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType clickType) {
					if (player.hasPermission(UltraColorPermissions.Color.CHAT_COLOR.replace("{color}", "d"))
							|| player.hasPermission(UltraColorPermissions.Color.CHAT_COLOR.replace("{color}", "*"))) {
						UltraColorUtil.applyChatColor(player, CompChatColor.LIGHT_PURPLE);
						Messenger.success(player, Localization.Chat_Color_Selection_Customization_Light_Purple.LIGHT_PURPLE_SUCCESS);
					} else {
						Messenger.error(player, Localization.Main_GUI_Customization_Chat_Color_Selection.ERROR_MESSAGE);
					}
					player.closeInventory();
				}

				@Override
				public ItemStack getItem() {
					return UltraColorUtil.makeMenuItem(Settings.Chat_Color_Menu_Items.LIGHT_PURPLE_ITEM,
							Localization.Chat_Color_Selection_Customization_Light_Purple.LIGHT_PURPLE_NAME,
							UltraColorUtil.modifyColorLoreWithPreview(Localization.Chat_Color_Selection_Customization_Light_Purple.LIGHT_PURPLE_LORE,
									"§d", null));
				}
			};

			yellowButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType clickType) {
					if (player.hasPermission(UltraColorPermissions.Color.CHAT_COLOR.replace("{color}", "e"))
							|| player.hasPermission(UltraColorPermissions.Color.CHAT_COLOR.replace("{color}", "*"))) {
						UltraColorUtil.applyChatColor(player, CompChatColor.YELLOW);
						Messenger.success(player, Localization.Chat_Color_Selection_Customization_Yellow.YELLOW_SUCCESS);
					} else
						Messenger.error(player, Localization.Main_GUI_Customization_Chat_Color_Selection.ERROR_MESSAGE);
					player.closeInventory();
				}

				@Override
				public ItemStack getItem() {
					return UltraColorUtil.makeMenuItem(Settings.Chat_Color_Menu_Items.YELLOW_ITEM, Localization.Chat_Color_Selection_Customization_Yellow
							.YELLOW_NAME, UltraColorUtil.modifyColorLoreWithPreview(Localization.Chat_Color_Selection_Customization_Yellow.YELLOW_LORE,
							"§e", null));
				}
			};

			whiteButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType clickType) {
					if (player.hasPermission(UltraColorPermissions.Color.CHAT_COLOR.replace("{color}", "f"))
							|| player.hasPermission(UltraColorPermissions.Color.CHAT_COLOR.replace("{color}", "*"))) {
						UltraColorUtil.applyChatColor(player, CompChatColor.WHITE);
						Messenger.success(player, Localization.Chat_Color_Selection_Customization_White.WHITE_SUCCESS);
					} else
						Messenger.error(player, Localization.Main_GUI_Customization_Chat_Color_Selection.ERROR_MESSAGE);
					player.closeInventory();
				}

				@Override
				public ItemStack getItem() {
					return UltraColorUtil.makeMenuItem(Settings.Chat_Color_Menu_Items.WHITE_ITEM, Localization.Chat_Color_Selection_Customization_White
							.WHITE_NAME, UltraColorUtil.modifyColorLoreWithPreview(Localization.Chat_Color_Selection_Customization_White.WHITE_LORE,
							"§f", null));
				}
			};

			magicFormatButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType clickType) {
					if (player.hasPermission(UltraColorPermissions.Color.CHAT_FORMAT.replace("{format}", "k"))
							|| player.hasPermission(UltraColorPermissions.Color.CHAT_FORMAT.replace("{format}", "*"))) {
						UltraColorUtil.applyChatFormat(player, CompChatColor.MAGIC);
						Messenger.success(player, Localization.Chat_Color_Selection_Customization_Magic.MAGIC_SUCCESS);
					} else
						Messenger.error(player, Localization.Main_GUI_Customization_Chat_Color_Selection.ERROR_MESSAGE);
					player.closeInventory();
				}

				@Override
				public ItemStack getItem() {
					return UltraColorUtil.makeMenuItem(Settings.Chat_Color_Menu_Items.MAGIC_ITEM, Localization.Chat_Color_Selection_Customization_Magic
							.MAGIC_NAME, UltraColorUtil.modifyColorLoreWithPreview(Localization.Chat_Color_Selection_Customization_Magic.MAGIC_LORE,
							"§k", null));
				}
			};

			boldFormatButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType clickType) {
					if (player.hasPermission(UltraColorPermissions.Color.CHAT_FORMAT.replace("{format}", "l"))
							|| player.hasPermission(UltraColorPermissions.Color.CHAT_FORMAT.replace("{format}", "*"))) {
						UltraColorUtil.applyChatFormat(player, CompChatColor.BOLD);
						Messenger.success(player, Localization.Chat_Color_Selection_Customization_Bold.BOLD_SUCCESS);
					} else
						Messenger.error(player, Localization.Main_GUI_Customization_Chat_Color_Selection.ERROR_MESSAGE);
					player.closeInventory();
				}

				@Override
				public ItemStack getItem() {
					return UltraColorUtil.makeMenuItem(Settings.Chat_Color_Menu_Items.BOLD_ITEM, Localization.Chat_Color_Selection_Customization_Bold
							.BOLD_NAME, UltraColorUtil.modifyColorLoreWithPreview(Localization.Chat_Color_Selection_Customization_Bold.BOLD_LORE,
							"§l", null));
				}
			};

			italicFormatButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType clickType) {
					if (player.hasPermission(UltraColorPermissions.Color.CHAT_FORMAT.replace("{format}", "o"))
							|| player.hasPermission(UltraColorPermissions.Color.CHAT_FORMAT.replace("{format}", "*"))) {
						UltraColorUtil.applyChatFormat(player, CompChatColor.ITALIC);
						Messenger.success(player, Localization.Chat_Color_Selection_Customization_Italic.ITALIC_SUCCESS);
					} else
						Messenger.error(player, Localization.Main_GUI_Customization_Chat_Color_Selection.ERROR_MESSAGE);
					player.closeInventory();
				}

				@Override
				public ItemStack getItem() {
					return UltraColorUtil.makeMenuItem(Settings.Chat_Color_Menu_Items.ITALIC_ITEM, Localization.Chat_Color_Selection_Customization_Italic
							.ITALIC_NAME, UltraColorUtil.modifyColorLoreWithPreview(Localization.Chat_Color_Selection_Customization_Italic.ITALIC_LORE,
							"§o", null));
				}
			};

			underlineFormatButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType clickType) {
					if (player.hasPermission(UltraColorPermissions.Color.CHAT_FORMAT.replace("{format}", "n"))
							|| player.hasPermission(UltraColorPermissions.Color.CHAT_FORMAT.replace("{format}", "*"))) {
						UltraColorUtil.applyChatFormat(player, CompChatColor.UNDERLINE);
						Messenger.success(player, Localization.Chat_Color_Selection_Customization_Underline.UNDERLINE_SUCCESS);
					} else
						Messenger.error(player, Localization.Main_GUI_Customization_Chat_Color_Selection.ERROR_MESSAGE);
					player.closeInventory();
				}

				@Override
				public ItemStack getItem() {
					return UltraColorUtil.makeMenuItem(Settings.Chat_Color_Menu_Items.UNDERLINE_ITEM, Localization.Chat_Color_Selection_Customization_Underline
							.UNDERLINE_NAME, UltraColorUtil.modifyColorLoreWithPreview(Localization.Chat_Color_Selection_Customization_Underline.UNDERLINE_LORE,
							"§n", null));
				}
			};

			strikethroughFormatButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType clickType) {
					if (player.hasPermission(UltraColorPermissions.Color.CHAT_FORMAT.replace("{format}", "m"))
							|| player.hasPermission(UltraColorPermissions.Color.CHAT_FORMAT.replace("{format}", "*"))) {
						UltraColorUtil.applyChatFormat(player, CompChatColor.STRIKETHROUGH);
						Messenger.success(player, Localization.Chat_Color_Selection_Customization_Strikethrough.STRIKETHROUGH_SUCCESS);
					} else
						Messenger.error(player, Localization.Main_GUI_Customization_Chat_Color_Selection.ERROR_MESSAGE);
					player.closeInventory();
				}

				@Override
				public ItemStack getItem() {
					return UltraColorUtil.makeMenuItem(Settings.Chat_Color_Menu_Items.STRIKETHROUGH_ITEM,
							Localization.Chat_Color_Selection_Customization_Strikethrough.STRIKETHROUGH_NAME,
							UltraColorUtil.modifyColorLoreWithPreview(Localization.Chat_Color_Selection_Customization_Strikethrough.STRIKETHROUGH_LORE,
									"§m", null));
				}
			};

			resetFormatButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType clickType) {
					final PlayerCache pCache = PlayerCache.fromPlayer(player);

					if (pCache.getChatFormat() != null) {
						pCache.setChatFormat(null);
						Messenger.success(player, Localization.Chat_Format_Reset_Button.RESET_SUCCESS);
					} else
						Messenger.error(player, Localization.Chat_Format_Reset_Button.RESET_ERROR);
					player.closeInventory();
				}

				@Override
				public ItemStack getItem() {
					return UltraColorUtil.makeMenuItem(Settings.Chat_Color_Menu_Items.FORMAT_RESET_ITEM, Localization.Chat_Format_Reset_Button.RESET_NAME,
							Localization.Chat_Format_Reset_Button.RESET_LORE);
				}
			};

			rainbowButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType clickType) {
					final PlayerCache pCache = PlayerCache.fromPlayer(player);

					if (player.hasPermission(UltraColorPermissions.Color.CHAT_COLOR.replace("{color}", "r"))
							|| player.hasPermission(UltraColorPermissions.Color.CHAT_COLOR.replace("{color}", "*"))) {
						pCache.setChatRainbowColors(true);
						pCache.setChatColor(null);

						if (pCache.getCustomGradient1() != null || pCache.getChatCustomGradient2() != null) {
							pCache.setChatCustomGradient1(null);
							pCache.setChatCustomGradient2(null);
						}

						final boolean hasFormat = pCache.getChatFormat() != null;

						Messenger.success(player, Localization.Chat_Color_Selection_Customization_Rainbow.RAINBOW_SUCCESS.replace("%rainbow_colors%",
								UltraColorUtil.convertStringToRainbow("Rainbow colors", hasFormat, hasFormat
										? pCache.getChatFormat().getName() : "")));
					} else
						Messenger.error(player, Localization.Main_GUI_Customization_Chat_Color_Selection.ERROR_MESSAGE);
					player.closeInventory();
				}

				@Override
				public ItemStack getItem() {
					return UltraColorUtil.makeMenuItem(Settings.Chat_Color_Menu_Items.RAINBOW_ITEM, Localization.Chat_Color_Selection_Customization_Rainbow
							.RAINBOW_NAME, UltraColorUtil.modifyColorLoreWithPreview(Localization.Chat_Color_Selection_Customization_Rainbow.RAINBOW_LORE,
							"rainbow", null));
				}
			};

			backButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType clickType) {
					if (player.hasPermission(UltraColorPermissions.COLOR)) {
						new ColorSelectionMenu().displayTo(player);
					} else {
						tellError(Localization.Other.NO_PERMISSION.replace("{permission}", UltraColorPermissions.COLOR));
						player.closeInventory();
					}
				}

				@Override
				public ItemStack getItem() {
					return UltraColorUtil.makeMenuItem(Settings.Other.BACK_BUTTON_ITEM, Localization.Chat_Back_Button.BACK_NAME,
							Localization.Chat_Back_Button.BACK_LORE);
				}
			};

			resetButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType clickType) {
					final PlayerCache pCache = PlayerCache.fromPlayer(player);

					if (pCache.getChatColor() != null || pCache.getChatFormat() != null || pCache.isChatRainbowColors()) {
						pCache.setChatColor(null);
						pCache.setChatFormat(null);

						if (pCache.isChatRainbowColors())
							pCache.setChatRainbowColors(false);
						Messenger.success(player, Localization.Chat_Reset_Button.RESET_SUCCESS);
					} else
						Messenger.error(player, Localization.Chat_Reset_Button.RESET_ERROR);
					player.closeInventory();
				}

				@Override
				public ItemStack getItem() {
					return UltraColorUtil.makeMenuItem(Settings.Other.RESET_BUTTON_ITEM, Localization.Chat_Reset_Button.RESET_NAME,
							Localization.Chat_Reset_Button.RESET_LORE);
				}
			};

			emptyButton = Button.makeDummy(ItemCreator.of(CompMaterial.valueOf(Settings.Color_Settings.CHAT_FILL_BUTTON), " "));
		}

		@Override
		protected boolean addInfoButton() {
			return Localization.Main_GUI_Customization_Chat_Color_Selection.ALLOW_INFO_BUTTON;
		}

		@Override
		public ItemStack getItemAt(int slot) {
			if (slot == Localization.Chat_Color_Selection_Customization_Black.MENU_SLOT
					&& Settings.Color_Settings_Chat_Colors.BLACK_COLOR_ENABLED)
				return blackButton.getItem();
			if (slot == Localization.Chat_Color_Selection_Customization_Dark_Blue.MENU_SLOT
					&& Settings.Color_Settings_Chat_Colors.DARK_BLUE_COLOR_ENABLED)
				return darkBlueButton.getItem();
			if (slot == Localization.Chat_Color_Selection_Customization_Dark_Green.MENU_SLOT
					&& Settings.Color_Settings_Chat_Colors.DARK_GREEN_COLOR_ENABLED)
				return darkGreenButton.getItem();
			if (slot == Localization.Chat_Color_Selection_Customization_Dark_Aqua.MENU_SLOT
					&& Settings.Color_Settings_Chat_Colors.DARK_AQUA_COLOR_ENABLED)
				return darkAquaButton.getItem();
			if (slot == Localization.Chat_Color_Selection_Customization_Dark_Red.MENU_SLOT
					&& Settings.Color_Settings_Chat_Colors.DARK_RED_COLOR_ENABLED)
				return darkRedButton.getItem();
			if (slot == Localization.Chat_Color_Selection_Customization_Dark_Purple.MENU_SLOT
					&& Settings.Color_Settings_Chat_Colors.DARK_PURPLE_COLOR_ENABLED)
				return darkPurpleButton.getItem();
			if (slot == Localization.Chat_Color_Selection_Customization_Orange.MENU_SLOT
					&& Settings.Color_Settings_Chat_Colors.ORANGE_COLOR_ENABLED)
				return goldButton.getItem();
			if (slot == Localization.Chat_Color_Selection_Customization_Gray.MENU_SLOT
					&& Settings.Color_Settings_Chat_Colors.GRAY_COLOR_ENABLED)
				return grayButton.getItem();
			if (slot == Localization.Chat_Color_Selection_Customization_Dark_Gray.MENU_SLOT
					&& Settings.Color_Settings_Chat_Colors.DARK_GRAY_COLOR_ENABLED)
				return darkGrayButton.getItem();
			if (slot == Localization.Chat_Color_Selection_Customization_Blue.MENU_SLOT
					&& Settings.Color_Settings_Chat_Colors.BLUE_COLOR_ENABLED)
				return blueButton.getItem();
			if (slot == Localization.Chat_Color_Selection_Customization_Green.MENU_SLOT
					&& Settings.Color_Settings_Chat_Colors.GREEN_COLOR_ENABLED)
				return greenButton.getItem();
			if (slot == Localization.Chat_Color_Selection_Customization_Aqua.MENU_SLOT
					&& Settings.Color_Settings_Chat_Colors.AQUA_COLOR_ENABLED)
				return aquaButton.getItem();
			if (slot == Localization.Chat_Color_Selection_Customization_Red.MENU_SLOT
					&& Settings.Color_Settings_Chat_Colors.RED_COLOR_ENABLED)
				return redButton.getItem();
			if (slot == Localization.Chat_Color_Selection_Customization_Light_Purple.MENU_SLOT
					&& Settings.Color_Settings_Chat_Colors.LIGHT_PURPLE_COLOR_ENABLED)
				return lightPurpleButton.getItem();
			if (slot == Localization.Chat_Color_Selection_Customization_Yellow.MENU_SLOT
					&& Settings.Color_Settings_Chat_Colors.YELLOW_COLOR_ENABLED)
				return yellowButton.getItem();
			if (slot == Localization.Chat_Color_Selection_Customization_White.MENU_SLOT
					&& Settings.Color_Settings_Chat_Colors.WHITE_COLOR_ENABLED)
				return whiteButton.getItem();
			if (slot == Localization.Chat_Color_Selection_Customization_Magic.MENU_SLOT && Settings.Color_Settings_Chat_Formats.MAGIC_FORMAT)
				return magicFormatButton.getItem();
			if (slot == Localization.Chat_Color_Selection_Customization_Bold.MENU_SLOT && Settings.Color_Settings_Chat_Formats.BOLD_FORMAT)
				return boldFormatButton.getItem();
			if (slot == Localization.Chat_Color_Selection_Customization_Italic.MENU_SLOT && Settings.Color_Settings_Chat_Formats.ITALIC_FORMAT)
				return italicFormatButton.getItem();
			if (slot == Localization.Chat_Color_Selection_Customization_Underline.MENU_SLOT && Settings.Color_Settings_Chat_Formats.UNDERLINE_FORMAT)
				return underlineFormatButton.getItem();
			if (slot == Localization.Chat_Color_Selection_Customization_Strikethrough.MENU_SLOT && Settings.Color_Settings_Chat_Formats.STRIKETHROUGH_FORMAT)
				return strikethroughFormatButton.getItem();
			if (slot == Localization.Chat_Format_Reset_Button.MENU_SLOT)
				return resetFormatButton.getItem();
			if (slot == Localization.Chat_Color_Selection_Customization_Rainbow.MENU_SLOT && Settings.Color_Settings.CHAT_RAINBOW_COLORS)
				return rainbowButton.getItem();
			if (slot == Localization.Chat_Back_Button.MENU_SLOT && Settings.Other.BACK_BUTTON_ENABLE)
				return backButton.getItem();
			if (slot == Localization.Chat_Reset_Button.MENU_SLOT)
				return resetButton.getItem();
			return emptyButton.getItem();
		}

		@Override
		protected String[] getInfo() {
			return new String[]{Localization.Main_GUI_Customization_Chat_Color_Selection.MENU_INFO};
		}
	}

	public static final class NameColorSelectionMenu extends Menu {
		private final Button blackButton;
		private final Button darkBlueButton;
		private final Button darkGreenButton;

		private final Button darkAquaButton;
		private final Button darkRedButton;
		private final Button darkPurpleButton;

		private final Button goldButton;
		private final Button grayButton;
		private final Button darkGrayButton;

		private final Button blueButton;
		private final Button greenButton;
		private final Button aquaButton;

		private final Button redButton;
		private final Button lightPurpleButton;
		private final Button yellowButton;

		private final Button whiteButton;
		private final Button magicFormatButton;
		private final Button boldFormatButton;

		private final Button italicFormatButton;
		private final Button underlineFormatButton;
		private final Button strikethroughFormatButton;

		private final Button resetFormatButton;
		private final Button rainbowButton;
		private final Button backButton;

		private final Button resetButton;
		private final Button emptyButton;

		public NameColorSelectionMenu() {
			setTitle(Localization.Menu_Titles.NAME_COLOR_SELECTION_MENU_TITLE);
			setSize(Localization.Main_GUI_Customization_Name_Color_Selection.MENU_SIZE);

			blackButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType clickType) {
					if (player.hasPermission(UltraColorPermissions.Color.NAME_COLOR.replace("{color}", "0"))
							|| player.hasPermission(UltraColorPermissions.Color.NAME_COLOR.replace("{color}", "*"))) {
						UltraColorUtil.applyNameColor(player, CompChatColor.BLACK, null);
						PlayerCache.fromPlayer(player).setNameRainbowColors(false);
						Messenger.success(player, Localization.Name_Color_Selection_Customization_Black.BLACK_SUCCESS);
					} else
						Messenger.error(player, Localization.Main_GUI_Customization_Name_Color_Selection.ERROR_MESSAGE);
					player.closeInventory();
				}

				@Override
				public ItemStack getItem() {
					return UltraColorUtil.makeMenuItem(Settings.Name_Color_Menu_Items.BLACK_ITEM, Localization.Name_Color_Selection_Customization_Black.BLACK_NAME,
							UltraColorUtil.modifyColorLoreWithPreview(Localization.Name_Color_Selection_Customization_Black.BLACK_LORE, "&0", null));
				}
			};

			darkBlueButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType clickType) {
					if (player.hasPermission(UltraColorPermissions.Color.NAME_COLOR.replace("{color}", "1"))
							|| player.hasPermission(UltraColorPermissions.Color.NAME_COLOR.replace("{color}", "*"))) {
						UltraColorUtil.applyNameColor(player, CompChatColor.DARK_BLUE, null);
						PlayerCache.fromPlayer(player).setNameRainbowColors(false);
						Messenger.success(player, Localization.Name_Color_Selection_Customization_Dark_Blue.DARK_BLUE_SUCCESS);
					} else
						Messenger.error(player, Localization.Main_GUI_Customization_Name_Color_Selection.ERROR_MESSAGE);
					player.closeInventory();
				}

				@Override
				public ItemStack getItem() {
					return UltraColorUtil.makeMenuItem(Settings.Name_Color_Menu_Items.DARK_BLUE_ITEM, Localization.Name_Color_Selection_Customization_Dark_Blue
							.DARK_BLUE_NAME, UltraColorUtil.modifyColorLoreWithPreview(Localization.Name_Color_Selection_Customization_Dark_Blue.DARK_BLUE_LORE,
							"&1", null));
				}
			};

			darkGreenButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType clickType) {
					if (player.hasPermission(UltraColorPermissions.Color.NAME_COLOR.replace("{color}", "2"))
							|| player.hasPermission(UltraColorPermissions.Color.NAME_COLOR.replace("{color}", "*"))) {
						UltraColorUtil.applyNameColor(player, CompChatColor.DARK_GREEN, null);
						PlayerCache.fromPlayer(player).setNameRainbowColors(false);
						Messenger.success(player, Localization.Name_Color_Selection_Customization_Dark_Green.DARK_GREEN_SUCCESS);
					} else
						Messenger.error(player, Localization.Main_GUI_Customization_Name_Color_Selection.ERROR_MESSAGE);
					player.closeInventory();
				}

				@Override
				public ItemStack getItem() {
					return UltraColorUtil.makeMenuItem(Settings.Name_Color_Menu_Items.DARK_GREEN_ITEM, Localization.Name_Color_Selection_Customization_Dark_Green
							.DARK_GREEN_NAME, UltraColorUtil.modifyColorLoreWithPreview(Localization.Name_Color_Selection_Customization_Dark_Green.DARK_GREEN_LORE,
							"&2", null));
				}
			};

			darkAquaButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType clickType) {
					if (player.hasPermission(UltraColorPermissions.Color.NAME_COLOR.replace("{color}", "3"))
							|| player.hasPermission(UltraColorPermissions.Color.NAME_COLOR.replace("{color}", "*"))) {
						UltraColorUtil.applyNameColor(player, CompChatColor.DARK_AQUA, null);
						PlayerCache.fromPlayer(player).setNameRainbowColors(false);
						Messenger.success(player, Localization.Name_Color_Selection_Customization_Dark_Aqua.DARK_AQUA_SUCCESS);
					} else
						Messenger.error(player, Localization.Main_GUI_Customization_Name_Color_Selection.ERROR_MESSAGE);
					player.closeInventory();
				}

				@Override
				public ItemStack getItem() {
					return UltraColorUtil.makeMenuItem(Settings.Name_Color_Menu_Items.DARK_AQUA_ITEM, Localization.Name_Color_Selection_Customization_Dark_Aqua
							.DARK_AQUA_NAME, UltraColorUtil.modifyColorLoreWithPreview(Localization.Name_Color_Selection_Customization_Dark_Aqua.DARK_AQUA_LORE,
							"&3", null));
				}
			};

			darkRedButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType clickType) {
					if (player.hasPermission(UltraColorPermissions.Color.NAME_COLOR.replace("{color}", "4"))
							|| player.hasPermission(UltraColorPermissions.Color.NAME_COLOR.replace("{color}", "*"))) {
						UltraColorUtil.applyNameColor(player, CompChatColor.DARK_RED, null);
						PlayerCache.fromPlayer(player).setNameRainbowColors(false);
						Messenger.success(player, Localization.Name_Color_Selection_Customization_Dark_Red.DARK_RED_SUCCESS);
					} else
						Messenger.error(player, Localization.Main_GUI_Customization_Name_Color_Selection.ERROR_MESSAGE);
					player.closeInventory();
				}

				@Override
				public ItemStack getItem() {
					return UltraColorUtil.makeMenuItem(Settings.Name_Color_Menu_Items.DARK_RED_ITEM, Localization.Name_Color_Selection_Customization_Dark_Red
							.DARK_RED_NAME, UltraColorUtil.modifyColorLoreWithPreview(Localization.Name_Color_Selection_Customization_Dark_Red.DARK_RED_LORE,
							"&4", null));
				}
			};

			darkPurpleButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType clickType) {
					if (player.hasPermission(UltraColorPermissions.Color.NAME_COLOR.replace("{color}", "5"))
							|| player.hasPermission(UltraColorPermissions.Color.NAME_COLOR.replace("{color}", "*"))) {
						UltraColorUtil.applyNameColor(player, CompChatColor.DARK_PURPLE, null);
						PlayerCache.fromPlayer(player).setNameRainbowColors(false);
						Messenger.success(player, Localization.Name_Color_Selection_Customization_Dark_Purple.DARK_PURPLE_SUCCESS);
					} else
						Messenger.error(player, Localization.Main_GUI_Customization_Name_Color_Selection.ERROR_MESSAGE);
					player.closeInventory();
				}

				@Override
				public ItemStack getItem() {
					return UltraColorUtil.makeMenuItem(Settings.Name_Color_Menu_Items.DARK_PURPLE_ITEM,
							Localization.Name_Color_Selection_Customization_Dark_Purple.DARK_PURPLE_NAME,
							UltraColorUtil.modifyColorLoreWithPreview(Localization.Name_Color_Selection_Customization_Dark_Purple.DARK_PURPLE_LORE,
									"&5", null));
				}
			};

			goldButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType clickType) {
					if (player.hasPermission(UltraColorPermissions.Color.NAME_COLOR.replace("{color}", "6"))
							|| player.hasPermission(UltraColorPermissions.Color.NAME_COLOR.replace("{color}", "*"))) {
						UltraColorUtil.applyNameColor(player, CompChatColor.GOLD, null);
						PlayerCache.fromPlayer(player).setNameRainbowColors(false);
						Messenger.success(player, Localization.Name_Color_Selection_Customization_Orange.ORANGE_SUCCESS);
					} else
						Messenger.error(player, Localization.Main_GUI_Customization_Name_Color_Selection.ERROR_MESSAGE);
					player.closeInventory();
				}

				@Override
				public ItemStack getItem() {
					return UltraColorUtil.makeMenuItem(Settings.Name_Color_Menu_Items.ORANGE_ITEM, Localization.Name_Color_Selection_Customization_Orange
							.ORANGE_NAME, UltraColorUtil.modifyColorLoreWithPreview(Localization.Name_Color_Selection_Customization_Orange.ORANGE_LORE,
							"&6", null));
				}
			};

			grayButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType clickType) {
					if (player.hasPermission(UltraColorPermissions.Color.NAME_COLOR.replace("{color}", "7"))
							|| player.hasPermission(UltraColorPermissions.Color.NAME_COLOR.replace("{color}", "*"))) {
						UltraColorUtil.applyNameColor(player, CompChatColor.GRAY, null);
						PlayerCache.fromPlayer(player).setNameRainbowColors(false);
						Messenger.success(player, Localization.Name_Color_Selection_Customization_Gray.GRAY_SUCCESS);
					} else
						Messenger.error(player, Localization.Main_GUI_Customization_Name_Color_Selection.ERROR_MESSAGE);
					player.closeInventory();
				}

				@Override
				public ItemStack getItem() {
					return UltraColorUtil.makeMenuItem(Settings.Name_Color_Menu_Items.GRAY_ITEM, Localization.Name_Color_Selection_Customization_Gray.GRAY_NAME,
							UltraColorUtil.modifyColorLoreWithPreview(Localization.Name_Color_Selection_Customization_Gray.GRAY_LORE, "&7", null));
				}
			};

			darkGrayButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType clickType) {
					if (player.hasPermission(UltraColorPermissions.Color.NAME_COLOR.replace("{color}", "8"))
							|| player.hasPermission(UltraColorPermissions.Color.NAME_COLOR.replace("{color}", "*"))) {
						UltraColorUtil.applyNameColor(player, CompChatColor.DARK_GRAY, null);
						PlayerCache.fromPlayer(player).setNameRainbowColors(false);
						Messenger.success(player, Localization.Name_Color_Selection_Customization_Dark_Gray.DARK_GRAY_SUCCESS);
					} else
						Messenger.error(player, Localization.Main_GUI_Customization_Name_Color_Selection.ERROR_MESSAGE);
					player.closeInventory();
				}

				@Override
				public ItemStack getItem() {
					return UltraColorUtil.makeMenuItem(Settings.Name_Color_Menu_Items.DARK_GRAY_ITEM, Localization.Name_Color_Selection_Customization_Dark_Gray
							.DARK_GRAY_NAME, UltraColorUtil.modifyColorLoreWithPreview(Localization.Name_Color_Selection_Customization_Dark_Gray.DARK_GRAY_LORE,
							"&8", null));
				}
			};

			blueButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType clickType) {
					if (player.hasPermission(UltraColorPermissions.Color.NAME_COLOR.replace("{color}", "9"))
							|| player.hasPermission(UltraColorPermissions.Color.NAME_COLOR.replace("{color}", "*"))) {
						UltraColorUtil.applyNameColor(player, CompChatColor.BLUE, null);
						PlayerCache.fromPlayer(player).setNameRainbowColors(false);
						Messenger.success(player, Localization.Name_Color_Selection_Customization_Blue.BLUE_SUCCESS);
					} else
						Messenger.error(player, Localization.Main_GUI_Customization_Name_Color_Selection.ERROR_MESSAGE);
					player.closeInventory();
				}

				@Override
				public ItemStack getItem() {
					return UltraColorUtil.makeMenuItem(Settings.Name_Color_Menu_Items.BLUE_ITEM, Localization.Name_Color_Selection_Customization_Blue.BLUE_NAME,
							UltraColorUtil.modifyColorLoreWithPreview(Localization.Name_Color_Selection_Customization_Blue.BLUE_LORE, "&9", null));
				}
			};

			greenButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType clickType) {
					if (player.hasPermission(UltraColorPermissions.Color.NAME_COLOR.replace("{color}", "a"))
							|| player.hasPermission(UltraColorPermissions.Color.NAME_COLOR.replace("{color}", "*"))) {
						UltraColorUtil.applyNameColor(player, CompChatColor.GREEN, null);
						PlayerCache.fromPlayer(player).setNameRainbowColors(false);
						Messenger.success(player, Localization.Name_Color_Selection_Customization_Green.GREEN_SUCCESS);
					} else
						Messenger.error(player, Localization.Main_GUI_Customization_Name_Color_Selection.ERROR_MESSAGE);
					player.closeInventory();
				}

				@Override
				public ItemStack getItem() {
					return UltraColorUtil.makeMenuItem(Settings.Name_Color_Menu_Items.GREEN_ITEM,
							Localization.Name_Color_Selection_Customization_Green.GREEN_NAME,
							UltraColorUtil.modifyColorLoreWithPreview(Localization.Name_Color_Selection_Customization_Green.GREEN_LORE,
									"&a", null));
				}
			};

			aquaButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType clickType) {
					if (player.hasPermission(UltraColorPermissions.Color.NAME_COLOR.replace("{color}", "b"))
							|| player.hasPermission(UltraColorPermissions.Color.NAME_COLOR.replace("{color}", "*"))) {
						UltraColorUtil.applyNameColor(player, CompChatColor.AQUA, null);
						PlayerCache.fromPlayer(player).setNameRainbowColors(false);
						Messenger.success(player, Localization.Name_Color_Selection_Customization_Aqua.AQUA_SUCCESS);
					} else
						Messenger.error(player, Localization.Main_GUI_Customization_Name_Color_Selection.ERROR_MESSAGE);
					player.closeInventory();
				}

				@Override
				public ItemStack getItem() {
					return UltraColorUtil.makeMenuItem(Settings.Name_Color_Menu_Items.AQUA_ITEM, Localization.Name_Color_Selection_Customization_Aqua.AQUA_NAME,
							UltraColorUtil.modifyColorLoreWithPreview(Localization.Name_Color_Selection_Customization_Aqua.AQUA_LORE, "&b", null));
				}
			};

			redButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType clickType) {
					if (player.hasPermission(UltraColorPermissions.Color.NAME_COLOR.replace("{color}", "c"))
							|| player.hasPermission(UltraColorPermissions.Color.NAME_COLOR.replace("{color}", "*"))) {
						UltraColorUtil.applyNameColor(player, CompChatColor.RED, null);
						PlayerCache.fromPlayer(player).setNameRainbowColors(false);
						Messenger.success(player, Localization.Name_Color_Selection_Customization_Red.RED_SUCCESS);
					} else
						Messenger.error(player, Localization.Main_GUI_Customization_Name_Color_Selection.ERROR_MESSAGE);
					player.closeInventory();
				}

				@Override
				public ItemStack getItem() {
					return UltraColorUtil.makeMenuItem(Settings.Name_Color_Menu_Items.RED_ITEM, Localization.Name_Color_Selection_Customization_Red.RED_NAME,
							UltraColorUtil.modifyColorLoreWithPreview(Localization.Name_Color_Selection_Customization_Red.RED_LORE, "&c", null));
				}
			};

			lightPurpleButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType clickType) {
					if (player.hasPermission(UltraColorPermissions.Color.NAME_COLOR.replace("{color}", "d"))
							|| player.hasPermission(UltraColorPermissions.Color.NAME_COLOR.replace("{color}", "*"))) {
						UltraColorUtil.applyNameColor(player, CompChatColor.LIGHT_PURPLE, null);
						PlayerCache.fromPlayer(player).setNameRainbowColors(false);
						Messenger.success(player, Localization.Name_Color_Selection_Customization_Light_Purple.LIGHT_PURPLE_SUCCESS);
					} else
						Messenger.error(player, Localization.Main_GUI_Customization_Name_Color_Selection.ERROR_MESSAGE);
					player.closeInventory();
				}

				@Override
				public ItemStack getItem() {
					return UltraColorUtil.makeMenuItem(Settings.Name_Color_Menu_Items.LIGHT_PURPLE_ITEM,
							Localization.Name_Color_Selection_Customization_Light_Purple.LIGHT_PURPLE_NAME,
							UltraColorUtil.modifyColorLoreWithPreview(Localization.Name_Color_Selection_Customization_Light_Purple.LIGHT_PURPLE_LORE,
									"&d", null));
				}
			};

			yellowButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType clickType) {
					if (player.hasPermission(UltraColorPermissions.Color.NAME_COLOR.replace("{color}", "e"))
							|| player.hasPermission(UltraColorPermissions.Color.NAME_COLOR.replace("{color}", "*"))) {
						UltraColorUtil.applyNameColor(player, CompChatColor.YELLOW, null);
						PlayerCache.fromPlayer(player).setNameRainbowColors(false);
						Messenger.success(player, Localization.Name_Color_Selection_Customization_Yellow.YELLOW_SUCCESS);
					} else
						Messenger.error(player, Localization.Main_GUI_Customization_Name_Color_Selection.ERROR_MESSAGE);
					player.closeInventory();
				}

				@Override
				public ItemStack getItem() {
					return UltraColorUtil.makeMenuItem(Settings.Name_Color_Menu_Items.YELLOW_ITEM, Localization.Name_Color_Selection_Customization_Yellow
							.YELLOW_NAME, UltraColorUtil.modifyColorLoreWithPreview(Localization.Name_Color_Selection_Customization_Yellow.YELLOW_LORE,
							"&e", null));
				}
			};

			whiteButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType clickType) {
					if (player.hasPermission(UltraColorPermissions.Color.NAME_COLOR.replace("{color}", "f"))
							|| player.hasPermission(UltraColorPermissions.Color.NAME_COLOR.replace("{color}", "*"))) {
						UltraColorUtil.applyNameColor(player, CompChatColor.WHITE, null);
						PlayerCache.fromPlayer(player).setNameRainbowColors(false);
						Messenger.success(player, Localization.Name_Color_Selection_Customization_White.WHITE_SUCCESS);
					} else
						Messenger.error(player, Localization.Main_GUI_Customization_Name_Color_Selection.ERROR_MESSAGE);
					player.closeInventory();
				}

				@Override
				public ItemStack getItem() {
					return UltraColorUtil.makeMenuItem(Settings.Name_Color_Menu_Items.WHITE_ITEM, Localization.Name_Color_Selection_Customization_White.WHITE_NAME,
							UltraColorUtil.modifyColorLoreWithPreview(Localization.Name_Color_Selection_Customization_White.WHITE_LORE, "&f", null));
				}
			};

			magicFormatButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType clickType) {
					if (player.hasPermission(UltraColorPermissions.Color.NAME_FORMAT.replace("{format}", "k"))
							|| player.hasPermission(UltraColorPermissions.Color.NAME_FORMAT.replace("{format}", "*"))) {
						UltraColorUtil.applyNameColor(player, null, ChatColor.MAGIC);
						final PlayerCache pCache = PlayerCache.fromPlayer(player);

						if (pCache.isNameRainbowColors())
							UltraColorUtil.convertNameToRainbow(player, true, ChatColor.MAGIC.name());

						Messenger.success(player, Localization.Name_Color_Selection_Customization_Magic.MAGIC_SUCCESS);
					} else
						Messenger.error(player, Localization.Main_GUI_Customization_Name_Color_Selection.ERROR_MESSAGE);
					player.closeInventory();
				}

				@Override
				public ItemStack getItem() {
					return UltraColorUtil.makeMenuItem(Settings.Name_Color_Menu_Items.MAGIC_ITEM, Localization.Name_Color_Selection_Customization_Magic.MAGIC_NAME,
							UltraColorUtil.modifyColorLoreWithPreview(Localization.Name_Color_Selection_Customization_Magic.MAGIC_LORE, "&k", null));
				}
			};

			boldFormatButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType clickType) {
					if (player.hasPermission(UltraColorPermissions.Color.NAME_FORMAT.replace("{format}", "l"))
							|| player.hasPermission(UltraColorPermissions.Color.NAME_FORMAT.replace("{format}", "*"))) {
						UltraColorUtil.applyNameColor(player, null, ChatColor.BOLD);
						final PlayerCache pCache = PlayerCache.fromPlayer(player);

						if (pCache.isNameRainbowColors())
							UltraColorUtil.convertNameToRainbow(player, true, ChatColor.BOLD.name());
						Messenger.success(player, Localization.Name_Color_Selection_Customization_Bold.BOLD_SUCCESS);
					} else
						Messenger.error(player, Localization.Main_GUI_Customization_Name_Color_Selection.ERROR_MESSAGE);
					player.closeInventory();
				}

				@Override
				public ItemStack getItem() {
					return UltraColorUtil.makeMenuItem(Settings.Name_Color_Menu_Items.BOLD_ITEM, Localization.Name_Color_Selection_Customization_Bold.BOLD_NAME,
							UltraColorUtil.modifyColorLoreWithPreview(Localization.Name_Color_Selection_Customization_Bold.BOLD_LORE, "&l", null));
				}
			};

			italicFormatButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType clickType) {
					if (player.hasPermission(UltraColorPermissions.Color.NAME_FORMAT.replace("{format}", "o"))
							|| player.hasPermission(UltraColorPermissions.Color.NAME_FORMAT.replace("{format}", "*"))) {
						UltraColorUtil.applyNameColor(player, null, ChatColor.ITALIC);
						final PlayerCache pCache = PlayerCache.fromPlayer(player);

						if (pCache.isNameRainbowColors())
							UltraColorUtil.convertNameToRainbow(player, true, ChatColor.ITALIC.name());

						Messenger.success(player, Localization.Name_Color_Selection_Customization_Italic.ITALIC_SUCCESS);
					} else
						Messenger.error(player, Localization.Main_GUI_Customization_Name_Color_Selection.ERROR_MESSAGE);
					player.closeInventory();
				}

				@Override
				public ItemStack getItem() {
					return UltraColorUtil.makeMenuItem(Settings.Name_Color_Menu_Items.ITALIC_ITEM, Localization.Name_Color_Selection_Customization_Italic
							.ITALIC_NAME, UltraColorUtil.modifyColorLoreWithPreview(Localization.Name_Color_Selection_Customization_Italic.ITALIC_LORE,
							"&o", null));
				}
			};

			underlineFormatButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType clickType) {
					if (player.hasPermission(UltraColorPermissions.Color.NAME_FORMAT.replace("{format}", "n"))
							|| player.hasPermission(UltraColorPermissions.Color.NAME_FORMAT.replace("{format}", "*"))) {
						UltraColorUtil.applyNameColor(player, null, ChatColor.UNDERLINE);
						final PlayerCache pCache = PlayerCache.fromPlayer(player);

						if (pCache.isNameRainbowColors())
							UltraColorUtil.convertNameToRainbow(player, true, ChatColor.UNDERLINE.name());

						Messenger.success(player, Localization.Name_Color_Selection_Customization_Underline.UNDERLINE_SUCCESS);
					} else
						Messenger.error(player, Localization.Main_GUI_Customization_Name_Color_Selection.ERROR_MESSAGE);
					player.closeInventory();
				}

				@Override
				public ItemStack getItem() {
					return UltraColorUtil.makeMenuItem(Settings.Name_Color_Menu_Items.UNDERLINE_ITEM, Localization.Name_Color_Selection_Customization_Underline
							.UNDERLINE_NAME, UltraColorUtil.modifyColorLoreWithPreview(Localization.Name_Color_Selection_Customization_Underline.UNDERLINE_LORE,
							"&n", null));
				}
			};

			strikethroughFormatButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType clickType) {
					if (player.hasPermission(UltraColorPermissions.Color.NAME_FORMAT.replace("{format}", "m"))
							|| player.hasPermission(UltraColorPermissions.Color.NAME_FORMAT.replace("{format}", "*"))) {
						UltraColorUtil.applyNameColor(player, null, ChatColor.STRIKETHROUGH);
						final PlayerCache pCache = PlayerCache.fromPlayer(player);

						if (pCache.isNameRainbowColors())
							UltraColorUtil.convertNameToRainbow(player, true, ChatColor.STRIKETHROUGH.name());

						Messenger.success(player, Localization.Name_Color_Selection_Customization_Strikethrough.STRIKETHROUGH_SUCCESS);
					} else
						Messenger.error(player, Localization.Main_GUI_Customization_Name_Color_Selection.ERROR_MESSAGE);
					player.closeInventory();
				}

				@Override
				public ItemStack getItem() {
					return UltraColorUtil.makeMenuItem(Settings.Name_Color_Menu_Items.STRIKETHROUGH_ITEM,
							Localization.Name_Color_Selection_Customization_Strikethrough.STRIKETHROUGH_NAME,
							UltraColorUtil.modifyColorLoreWithPreview(Localization.Name_Color_Selection_Customization_Strikethrough.STRIKETHROUGH_LORE,
									"&m", null));
				}
			};

			resetFormatButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType clickType) {
					final PlayerCache pCache = PlayerCache.fromPlayer(player);

					if (pCache.getNameFormat() != null) {
						pCache.setNameFormat(null);

						if (!pCache.getColoredNickName().equalsIgnoreCase("none")) {
							if (pCache.isNameRainbowColors())
								pCache.setColoredNickName(UltraColorUtil.convertStringToRainbow(pCache.getNickName(), false, ""));
							if (pCache.getNameColor() != null)
								pCache.setColoredNickName(UltraColorUtil.nameAndChatColorToString(pCache.getNameColor()) + pCache.getNickName());
							else
								pCache.setColoredNickName(pCache.getNickName());

							if (pCache.getCustomGradient1() != null && pCache.getCustomGradient2() != null)
								pCache.setColoredNickName(ChatUtil.generateGradient(pCache.getNickName(), pCache.getCustomGradient1(), pCache.getCustomGradient2()));
						}

						player.setDisplayName(UltraColorUtil.getPlayerNameInColor(player));
						Messenger.success(player, Localization.Name_Format_Reset_Button.RESET_SUCCESS);
					} else
						Messenger.error(player, Localization.Name_Format_Reset_Button.RESET_ERROR);
					player.closeInventory();
				}

				@Override
				public ItemStack getItem() {
					return UltraColorUtil.makeMenuItem(Settings.Name_Color_Menu_Items.FORMAT_RESET_ITEM, Localization.Name_Format_Reset_Button.RESET_NAME,
							Localization.Name_Format_Reset_Button.RESET_LORE);
				}
			};

			rainbowButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType clickType) {
					final PlayerCache pCache = PlayerCache.fromPlayer(player);

					if (player.hasPermission(UltraColorPermissions.Color.NAME_COLOR.replace("{color}", "r"))
							|| player.hasPermission(UltraColorPermissions.Color.NAME_COLOR.replace("{color}", "*"))) {
						pCache.setNameRainbowColors(true);
						pCache.setNameColor(null);

						if (pCache.getCustomGradient1() != null || pCache.getCustomGradient2() != null) {
							pCache.setCustomGradient1(null);
							pCache.setCustomGradient2(null);
						}

						UltraColorUtil.convertNameToRainbow(player, pCache.getNameFormat() != null,
								pCache.getNameFormat() != null ? pCache.getNameFormat().name() : "");

						if (!pCache.getColoredNickName().equalsIgnoreCase("none")) {
							pCache.setColoredNickName(UltraColorUtil.convertStringToRainbow(pCache.getNickName(),
									pCache.getNameFormat() != null, pCache.getNameFormat() != null
											? pCache.getNameFormat().name() : ""));
							player.setDisplayName(PlayerCache.fromPlayer(player).getColoredNickName());
						}

						Messenger.success(player, Localization.Name_Color_Selection_Customization_Rainbow.RAINBOW_SUCCESS.replace("%rainbow_colors%",
								UltraColorUtil.convertStringToRainbow("Rainbow colors", pCache.getNameFormat() != null,
										pCache.getNameFormat() != null ? pCache.getNameFormat().name() : "")));
					} else
						Messenger.error(player, Localization.Main_GUI_Customization_Name_Color_Selection.ERROR_MESSAGE);
					player.closeInventory();
				}

				@Override
				public ItemStack getItem() {
					return UltraColorUtil.makeMenuItem(Settings.Name_Color_Menu_Items.RAINBOW_ITEM, Localization.Name_Color_Selection_Customization_Rainbow
							.RAINBOW_NAME, UltraColorUtil.modifyColorLoreWithPreview(Localization.Name_Color_Selection_Customization_Rainbow.RAINBOW_LORE,
							"rainbow", null));
				}
			};

			backButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType clickType) {
					if (player.hasPermission(UltraColorPermissions.COLOR)) {
						new ColorSelectionMenu().displayTo(player);
					} else {
						tellError(Localization.Other.NO_PERMISSION.replace("{permission}", "ultracolor.gui"));
						player.closeInventory();
					}
				}

				@Override
				public ItemStack getItem() {
					return UltraColorUtil.makeMenuItem(Settings.Other.BACK_BUTTON_ITEM, Localization.Name_Back_Button.BACK_NAME,
							Localization.Name_Back_Button.BACK_LORE);
				}
			};

			resetButton = new Button() {
				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType clickType) {
					final PlayerCache pCache = PlayerCache.fromPlayer(player);

					if (pCache.getNameColor() != null || pCache.getNameFormat() != null || pCache.isNameRainbowColors()) {
						pCache.setNameColor(null);
						pCache.setNameFormat(null);
						player.setDisplayName(player.getName());
						pCache.setNameRainbowColors(false);

						if (pCache.getCustomGradient1() != null || pCache.getCustomGradient2() != null) {
							pCache.setCustomGradient1(null);
							pCache.setCustomGradient2(null);
						}

						if (!pCache.getNickName().equalsIgnoreCase("none")) {
							player.setDisplayName(pCache.getNickName());
							pCache.setColoredNickName(pCache.getNickName());
						}

						Messenger.success(player, Localization.Name_Reset_Button.RESET_SUCCESS);
					} else
						Messenger.error(player, Localization.Name_Reset_Button.RESET_ERROR);
					player.closeInventory();
				}

				@Override
				public ItemStack getItem() {
					return UltraColorUtil.makeMenuItem(Settings.Other.RESET_BUTTON_ITEM, Localization.Name_Reset_Button.RESET_NAME,
							Localization.Name_Reset_Button.RESET_LORE);
				}
			};

			emptyButton = Button.makeDummy(ItemCreator.of(CompMaterial.valueOf(Settings.Color_Settings.NAME_FILL_BUTTON), " "));
		}

		@Override
		protected boolean addInfoButton() {
			return Localization.Main_GUI_Customization_Name_Color_Selection.ALLOW_INFO_BUTTON;
		}

		@Override
		public ItemStack getItemAt(int slot) {
			if (slot == Localization.Name_Color_Selection_Customization_Black.MENU_SLOT
					&& Settings.Color_Settings_Name_Colors.BLACK_COLOR_ENABLED)
				return blackButton.getItem();
			if (slot == Localization.Name_Color_Selection_Customization_Dark_Blue.MENU_SLOT
					&& Settings.Color_Settings_Name_Colors.DARK_BLUE_COLOR_ENABLED)
				return darkBlueButton.getItem();
			if (slot == Localization.Name_Color_Selection_Customization_Dark_Green.MENU_SLOT
					&& Settings.Color_Settings_Name_Colors.DARK_GREEN_COLOR_ENABLED)
				return darkGreenButton.getItem();
			if (slot == Localization.Name_Color_Selection_Customization_Dark_Aqua.MENU_SLOT
					&& Settings.Color_Settings_Name_Colors.DARK_AQUA_COLOR_ENABLED)
				return darkAquaButton.getItem();
			if (slot == Localization.Name_Color_Selection_Customization_Dark_Red.MENU_SLOT
					&& Settings.Color_Settings_Name_Colors.DARK_RED_COLOR_ENABLED)
				return darkRedButton.getItem();
			if (slot == Localization.Name_Color_Selection_Customization_Dark_Purple.MENU_SLOT
					&& Settings.Color_Settings_Name_Colors.DARK_PURPLE_COLOR_ENABLED)
				return darkPurpleButton.getItem();
			if (slot == Localization.Name_Color_Selection_Customization_Orange.MENU_SLOT
					&& Settings.Color_Settings_Name_Colors.ORANGE_COLOR_ENABLED)
				return goldButton.getItem();
			if (slot == Localization.Name_Color_Selection_Customization_Gray.MENU_SLOT
					&& Settings.Color_Settings_Name_Colors.GRAY_COLOR_ENABLED)
				return grayButton.getItem();
			if (slot == Localization.Name_Color_Selection_Customization_Dark_Gray.MENU_SLOT
					&& Settings.Color_Settings_Name_Colors.DARK_GRAY_COLOR_ENABLED)
				return darkGrayButton.getItem();
			if (slot == Localization.Name_Color_Selection_Customization_Blue.MENU_SLOT
					&& Settings.Color_Settings_Name_Colors.BLUE_COLOR_ENABLED)
				return blueButton.getItem();
			if (slot == Localization.Name_Color_Selection_Customization_Green.MENU_SLOT
					&& Settings.Color_Settings_Name_Colors.GREEN_COLOR_ENABLED)
				return greenButton.getItem();
			if (slot == Localization.Name_Color_Selection_Customization_Aqua.MENU_SLOT
					&& Settings.Color_Settings_Name_Colors.AQUA_COLOR_ENABLED)
				return aquaButton.getItem();
			if (slot == Localization.Name_Color_Selection_Customization_Red.MENU_SLOT
					&& Settings.Color_Settings_Name_Colors.RED_COLOR_ENABLED)
				return redButton.getItem();
			if (slot == Localization.Name_Color_Selection_Customization_Light_Purple.MENU_SLOT
					&& Settings.Color_Settings_Name_Colors.LIGHT_PURPLE_COLOR_ENABLED)
				return lightPurpleButton.getItem();
			if (slot == Localization.Name_Color_Selection_Customization_Yellow.MENU_SLOT
					&& Settings.Color_Settings_Name_Colors.YELLOW_COLOR_ENABLED)
				return yellowButton.getItem();
			if (slot == Localization.Name_Color_Selection_Customization_White.MENU_SLOT
					&& Settings.Color_Settings_Name_Colors.WHITE_COLOR_ENABLED)
				return whiteButton.getItem();
			if (slot == Localization.Name_Color_Selection_Customization_Magic.MENU_SLOT && Settings.Color_Settings_Name_Formats.MAGIC_FORMAT)
				return magicFormatButton.getItem();
			if (slot == Localization.Name_Color_Selection_Customization_Bold.MENU_SLOT && Settings.Color_Settings_Name_Formats.BOLD_FORMAT)
				return boldFormatButton.getItem();
			if (slot == Localization.Name_Color_Selection_Customization_Italic.MENU_SLOT && Settings.Color_Settings_Name_Formats.ITALIC_FORMAT)
				return italicFormatButton.getItem();
			if (slot == Localization.Name_Color_Selection_Customization_Underline.MENU_SLOT && Settings.Color_Settings_Name_Formats.UNDERLINE_FORMAT)
				return underlineFormatButton.getItem();
			if (slot == Localization.Name_Color_Selection_Customization_Strikethrough.MENU_SLOT && Settings.Color_Settings_Name_Formats.STRIKETHROUGH_FORMAT)
				return strikethroughFormatButton.getItem();
			if (slot == Localization.Name_Format_Reset_Button.MENU_SLOT)
				return resetFormatButton.getItem();
			if (slot == Localization.Name_Color_Selection_Customization_Rainbow.MENU_SLOT && Settings.Color_Settings.NAME_RAINBOW_COLORS)
				return rainbowButton.getItem();
			if (slot == Localization.Name_Back_Button.MENU_SLOT && Settings.Other.BACK_BUTTON_ENABLE)
				return backButton.getItem();
			if (slot == Localization.Name_Reset_Button.MENU_SLOT)
				return resetButton.getItem();
			return emptyButton.getItem();
		}

		@Override
		protected String[] getInfo() {
			return new String[]{Localization.Main_GUI_Customization_Name_Color_Selection.MENU_INFO};
		}
	}
}
