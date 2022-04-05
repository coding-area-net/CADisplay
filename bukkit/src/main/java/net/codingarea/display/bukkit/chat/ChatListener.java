package net.codingarea.display.bukkit.chat;

import de.dytanic.cloudnet.driver.CloudNetDriver;
import de.dytanic.cloudnet.driver.permission.IPermissionGroup;
import de.dytanic.cloudnet.driver.permission.IPermissionUser;
import net.codingarea.display.bukkit.DisplayAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

	@EventHandler(priority = EventPriority.HIGH)
	public void handleChat(AsyncPlayerChatEvent event) {

		Player player = event.getPlayer();
		IPermissionUser user = CloudNetDriver.getInstance().getPermissionManagement().getUser(player.getUniqueId());

		if (user == null) {
			return;
		}

		String message = event.getMessage().replace("%", "%%");
		if (player.hasPermission("cloudnet.chat.color")) {
			message = ChatColor.translateAlternateColorCodes('&', message);
		}

		if (ChatColor.stripColor(message).trim().isEmpty()) {
			event.setCancelled(true);
			return;
		}

		IChatDisplay display = DisplayAPI.getDisplayAPI().getCurrentChatDisplay();

		if (display == null) {
			return;
		}
		IPermissionGroup group = CloudNetDriver.getInstance().getPermissionManagement().getHighestPermissionGroup(user);

		if (!display.broadcastChat()) {
			event.setCancelled(true);

			for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
				onlinePlayer.sendMessage(replacePlaceholders(display.getFormat(onlinePlayer, player), player, group));
			}

		} else {
			event.setFormat(replacePlaceholders(display.getFormat(player, player), player, group).replace("%message%", message));
		}

	}

	private String replacePlaceholders(String s, Player player, IPermissionGroup group) {
		String format = s
				.replace("%name%", player.getName())
				.replace("%uniqueId%", player.getUniqueId().toString());

		if (group != null) {
			format = ChatColor.translateAlternateColorCodes('&',
					format
							.replace("%group%", group.getName())
							.replace("%display%", group.getDisplay())
							.replace("%prefix%", group.getPrefix())
							.replace("%suffix%", group.getSuffix())
							.replace("%color%", group.getColor())
			);
		} else {
			format = ChatColor.translateAlternateColorCodes('&',
					format
							.replace("%group%", "")
							.replace("%display%", "")
							.replace("%prefix%", "")
							.replace("%suffix%", "")
							.replace("%color%", "")
			);
		}
		return format;
	}

}