package net.codingarea.display.bukkit.chat;

import org.bukkit.entity.Player;

/**
 * @author KxmischesDomi | https://github.com/kxmischesdomi
 * @since 1.0
 */
public interface IChatDisplay {

	/**
	 * @return if the chat format is the same for all and shouldn't be replaced by sending each player a separate message
	 */
	boolean broadcastChat();

	String getFormat(Player viewer, Player sender);

}
