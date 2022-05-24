package me.ultimategamer200.ultracolor.menu;

import me.ultimategamer200.ultracolor.gradients.PreDefinedGradientMenu;
import me.ultimategamer200.ultracolor.settings.Localization;
import me.ultimategamer200.ultracolor.settings.Settings;
import me.ultimategamer200.ultracolor.util.UltraColorUtil;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.menu.Menu;
import org.mineacademy.fo.menu.button.Button;
import org.mineacademy.fo.menu.button.ButtonMenu;

import java.util.Collections;

/**
 * The gradient menu brought by /gradientcolor.
 */
public class GradientMenu extends Menu {
	private final Button gradientNameButton;
	private final Button gradientChatButton;
	private final Button fillButton;

	public GradientMenu() {
		setTitle(Localization.Gradient_Color_Selection.MENU_TITLE);
		setInfo(Localization.Gradient_Color_Selection.INFO_MESSAGE);
		setSize(Localization.Gradient_Color_Selection.MAIN_MENU_SIZE);

		gradientChatButton = new ButtonMenu(new PreDefinedGradientMenu.PreDefinedChatGradientsMenu(this),
				UltraColorUtil.makeMenuItem(Settings.Gradient_Color_Menu_Items.CHAT_ITEM, Localization.Gradient_Color_Selection.CHAT_MENU_TITLE,
						Collections.singletonList(Localization.Gradient_Color_Selection.CHAT_MENU_LORE)));

		gradientNameButton = new ButtonMenu(new PreDefinedGradientMenu.PreDefinedNameGradientsMenu(this),
				UltraColorUtil.makeMenuItem(Settings.Gradient_Color_Menu_Items.NAME_ITEM, Localization.Gradient_Color_Selection.NAME_MENU_TITLE,
						Collections.singletonList(Localization.Gradient_Color_Selection.NAME_MENU_LORE)));

		fillButton = Button.makeDummy(UltraColorUtil.makeMenuItem(Settings.Gradient_Color_Menu_Items.FILL_ITEM, " ", Collections.singletonList("")));
	}

	@Override
	protected boolean addInfoButton() {
		return Localization.Gradient_Color_Selection.ALLOW_INFO_BUTTON;
	}

	@Override
	public ItemStack getItemAt(int slot) {
		if (slot == Localization.Gradient_Color_Selection.CHAT_MENU_ITEM_SLOT && Settings.Color_Settings.CHAT_GRADIENT_COLORS)
			return gradientChatButton.getItem();
		if (slot == Localization.Gradient_Color_Selection.NAME_MENU_ITEM_SLOT && Settings.Color_Settings.NAME_GRADIENT_COLORS)
			return gradientNameButton.getItem();
		return fillButton.getItem();
	}
}
