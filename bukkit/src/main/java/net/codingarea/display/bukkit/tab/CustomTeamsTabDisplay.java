package net.codingarea.display.bukkit.tab;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.function.Function;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Tablist with custom attributes saved within {@link TabEntry} that is the same for every viewer.
 *
 * @author KxmischesDomi | https://github.com/kxmischesdomi
 * @since 1.0
 */
public class CustomTeamsTabDisplay implements ITabDisplay {

	private final Function<Player, TabEntry> propertiesFunction;

	public CustomTeamsTabDisplay(Function<Player, TabEntry> propertiesFunction) {
		this.propertiesFunction = propertiesFunction;
	}

	@Override
	public void onUpdate(@NotNull Player player) {
		for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
			Scoreboard scoreboard = onlinePlayer.getScoreboard();
			Team team = scoreboard.getEntryTeam(player.getName());
			if (team == null) {
				continue;
			}
			team.removeEntry(player.getName());
			if (team.getEntries().isEmpty()) {
				team.unregister();
			}
		}

		TabEntry properties = propertiesFunction.apply(player);
		player.setDisplayName(ChatColor.translateAlternateColorCodes('&', properties.getDisplay() + player.getName()));

		Bukkit.getOnlinePlayers().forEach(player1 -> {
			TabEntry properties1 = propertiesFunction.apply(player1);

			Bukkit.getOnlinePlayers().forEach(player2 -> {
				Team team = createTeam(properties1, player2.getScoreboard());
				team.addEntry(player1.getName());
			});

		});
	}

	@Override
	public void onDeactivate() {
		Bukkit.getOnlinePlayers().forEach(player -> {
			player.setDisplayName((player.getGameMode() == GameMode.SPECTATOR ? "§7" : "") + player.getName());
			new ArrayList<>(player.getScoreboard().getTeams()).forEach(Team::unregister);
		});
	}

	protected Team createTeam(TabEntry properties, Scoreboard scoreboard) {
		String teamName = getTeamName(properties);
		Team team = scoreboard.getTeam(teamName);

		if (team == null) {
			team = scoreboard.registerNewTeam(teamName);
		}

		team.setPrefix(properties.getPrefix() == null ? "" : ChatColor.translateAlternateColorCodes('&', properties.getPrefix()));
		team.setSuffix(properties.getSuffix() == null ? "" : ChatColor.translateAlternateColorCodes('&', properties.getSuffix()));
		setColor(team, properties.getColor(), properties.getPrefix());

		return team;
	}

	protected void setColor(Team team, @Nullable String color, @Nullable String prefix) {

		try {
			Method method = team.getClass().getDeclaredMethod("setColor", ChatColor.class);
			method.setAccessible(true);

			if (color != null && !color.isEmpty()) {
				ChatColor chatColor = ChatColor.getByChar(color.replaceAll("&", "").replaceAll("§", ""));
				if (chatColor != null) {
					method.invoke(team, chatColor);
				}
			} else if (prefix != null && !prefix.isEmpty()) {
				color = ChatColor.getLastColors(prefix.replace('&', '§'));
				if (color.isEmpty()) {
					return;
				}
				ChatColor chatColor = ChatColor.getByChar(color.replaceAll("&", "").replaceAll("§", ""));
				if (chatColor != null) {
					method.invoke(team, chatColor);
				}
			}
		} catch (NoSuchMethodException ignored) {
		} catch (IllegalAccessException | InvocationTargetException exception) {
			exception.printStackTrace();
		}
	}

	private String getTeamName(TabEntry properties) {
		return properties.getSortId() + properties.getName();
	}

}