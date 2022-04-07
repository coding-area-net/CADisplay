package net.codingarea.display.bukkit.tab;

import de.dytanic.cloudnet.driver.CloudNetDriver;
import de.dytanic.cloudnet.driver.event.EventListener;
import de.dytanic.cloudnet.driver.event.events.permission.PermissionUpdateGroupEvent;
import de.dytanic.cloudnet.driver.event.events.permission.PermissionUpdateUserEvent;
import de.dytanic.cloudnet.driver.permission.IPermissionUser;
import net.codingarea.display.bukkit.DisplayAPI;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.function.Consumer;

/**
 * @author KxmischesDomi | https://github.com/kxmischesdomi
 * @since 1.0
 */
public class TabUpdateListener implements Listener {

	private void executeIfSet(Consumer<ITabDisplay> consumer) {
		ITabDisplay currentTabDisplay = DisplayAPI.getDisplayAPI().getCurrentTabDisplay();
		if (currentTabDisplay != null) {
			consumer.accept(currentTabDisplay);
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void handle(PlayerJoinEvent event) {
		executeIfSet(iTabDisplay -> iTabDisplay.onUpdate(event.getPlayer()));
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void handle(PlayerQuitEvent event) {
		executeIfSet(iTabDisplay -> iTabDisplay.onUpdate(event.getPlayer()));
	}

	@EventListener
	public void handle(PermissionUpdateUserEvent event) {
		executeIfSet(iTabDisplay ->
				Bukkit.getOnlinePlayers().stream()
						.filter(player -> player.getUniqueId().equals(event.getPermissionUser().getUniqueId()))
						.findFirst()
						.ifPresent(iTabDisplay::onUpdate)
		);
	}

	@EventListener
	public void handle(PermissionUpdateGroupEvent event) {
		executeIfSet(iTabDisplay ->
				Bukkit.getOnlinePlayers().forEach(player -> {
					IPermissionUser permissionUser = CloudNetDriver.getInstance().getPermissionManagement().getUser(player.getUniqueId());
					if (permissionUser != null && permissionUser.inGroup(event.getPermissionGroup().getName())) {
						iTabDisplay.onUpdate(player);
					}
				})
		);
	}

}
