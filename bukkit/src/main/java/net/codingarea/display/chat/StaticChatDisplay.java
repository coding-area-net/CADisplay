package net.codingarea.display.chat;

import org.bukkit.entity.Player;

/**
 * @author KxmischesDomi | https://github.com/kxmischesdomi
 * @since 1.0
 */
public class StaticChatDisplay implements IChatDisplay {

	private final String format;

	public StaticChatDisplay(String format) {
		this.format = format != null ? format : "";
	}

	@Override
	public boolean broadcastChat() {
		return true;
	}

	@Override
	public String getFormat(Player viewer, Player sender) {
		return format;
	}

}
