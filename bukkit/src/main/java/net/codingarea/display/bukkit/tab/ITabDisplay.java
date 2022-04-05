package net.codingarea.display.bukkit.tab;

import org.bukkit.entity.Player;

/**
 * @author KxmischesDomi | https://github.com/kxmischesdomi
 * @since 1.0
 */
public interface ITabDisplay {

	void onUpdate(Player player);

	void onDeactivate();

}