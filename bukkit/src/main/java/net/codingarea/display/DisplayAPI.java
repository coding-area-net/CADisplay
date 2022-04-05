package net.codingarea.display;

import net.codingarea.display.chat.IChatDisplay;
import net.codingarea.display.chat.StaticChatDisplay;
import net.codingarea.display.tab.CloudPermsTabDisplay;
import net.codingarea.display.tab.ITabDisplay;
import org.jetbrains.annotations.Nullable;

/**
 * @author KxmischesDomi | https://github.com/kxmischesdomi
 * @since 1.0
 */
public class DisplayAPI {

	private static DisplayAPI displayAPI;

	private final CADisplayPlugin plugin;

	DisplayAPI(CADisplayPlugin plugin) {
		this.plugin = plugin;
		displayAPI = this;
	}

	/**
	 * Gets the current chat display instance
	 */
	public IChatDisplay getCurrentChatDisplay() {
		return plugin.getCurrentChatDisplay();
	}

	/**
	 * Sets the current chat back to the default from the config
	 */
	public void resetChatDisplay() {
		plugin.setCurrentChatDisplay(new StaticChatDisplay(plugin.getConfigFormat()));
	}

	public void setCurrentChatDisplay(@Nullable IChatDisplay newChatDisplay) {
		plugin.setCurrentChatDisplay(newChatDisplay);
	}

	/**
	 * Gets the current tab display instance
	 */
	@Nullable
	public ITabDisplay getCurrentTabDisplay() {
		return plugin.getCurrentTabDisplay();
	}

	/**
	 * Sets the current tab back to the default one with cloud permissions
	 */
	public void resetTabDisplay() {
		plugin.setCurrentTabDisplay(new CloudPermsTabDisplay(plugin));
	}

	/**
	 * Sets the current tab display instance and reloads the tablist
	 */
	public void setCurrentTabDisplay(@Nullable ITabDisplay newTabDisplay) {
		plugin.setCurrentTabDisplay(newTabDisplay);
	}

	public static DisplayAPI getDisplayAPI() {
		return displayAPI;
	}

}
