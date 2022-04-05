package net.codingarea.display;

import net.codingarea.display.chat.ChatListener;
import net.codingarea.display.chat.IChatDisplay;
import net.codingarea.display.chat.StaticChatDisplay;
import net.codingarea.display.tab.CloudPermsTabDisplay;
import net.codingarea.display.tab.ITabDisplay;
import net.codingarea.display.tab.TabUpdateListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

/**
 * @author KxmischesDomi | https://github.com/kxmischesdomi
 * @since 1.0
 */
public class CADisplayPlugin extends JavaPlugin {

	private ITabDisplay currentTabDisplay;
	private IChatDisplay currentChatDisplay;
	private String configFormat;

	@Override
	public void onLoad() {
		new DisplayAPI(this);
	}

	@Override
	public void onEnable() {
		this.getConfig().options().copyDefaults(true);
		this.saveConfig();

		this.configFormat = getConfig().getString("format");

		if (configFormat == null) {
			getLogger().severe("Chat Format set in config is null!");
		}

		this.currentTabDisplay = new CloudPermsTabDisplay(this);
		this.currentChatDisplay = new StaticChatDisplay(configFormat);

		Bukkit.getPluginManager().registerEvents(new TabUpdateListener(this), this);
		Bukkit.getPluginManager().registerEvents(new ChatListener(), this);
	}

	public String getConfigFormat() {
		return configFormat;
	}

	@Nullable
	public IChatDisplay getCurrentChatDisplay() {
		return currentChatDisplay;
	}

	public void setCurrentChatDisplay(@Nullable IChatDisplay currentChatDisplay) {
		this.currentChatDisplay = currentChatDisplay;
	}

	@Nullable
	public ITabDisplay getCurrentTabDisplay() {
		return currentTabDisplay;
	}

	public void setCurrentTabDisplay(@Nullable ITabDisplay currentTabDisplay) {
		if (this.currentTabDisplay != null && this.currentTabDisplay != currentTabDisplay) this.currentTabDisplay.onDeactivate();
		this.currentTabDisplay = currentTabDisplay;
		if (currentTabDisplay != null) Bukkit.getOnlinePlayers().forEach(currentTabDisplay::onUpdate);
	}

}
