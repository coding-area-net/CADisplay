package net.codingarea.display.bukkit.tab;

import de.dytanic.cloudnet.ext.cloudperms.bukkit.BukkitCloudNetCloudPermissionsPlugin;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Team;
import org.jetbrains.annotations.NotNull;

/**
 * Default Cloud Permissions Tablist
 *
 * @author KxmischesDomi | https://github.com/kxmischesdomi
 * @since 1.0
 */
public final class CloudPermsTabDisplay implements ITabDisplay {

	private final JavaPlugin plugin;

	public CloudPermsTabDisplay(JavaPlugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public void onUpdate(@NotNull Player player) {
		Bukkit.getScheduler().runTask(plugin, () -> BukkitCloudNetCloudPermissionsPlugin.getInstance().updateNameTags(player));
	}

	@Override
	public void onDeactivate() {
		Bukkit.getOnlinePlayers().forEach(player -> {
			player.setDisplayName((player.getGameMode() == GameMode.SPECTATOR ? "§7" : "") + player.getName());
			new ArrayList<>(player.getScoreboard().getTeams()).forEach(Team::unregister);
		});
	}

}
