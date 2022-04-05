package net.codingarea.display.bukkit.chat;

import org.bukkit.entity.Player;

import java.util.function.Function;

/**
 * @author KxmischesDomi | https://github.com/kxmischesdomi
 * @since 1.0
 */
public class DynamicChatDisplay implements IChatDisplay {

	private final Function<Player, String> formatFunction;

	public DynamicChatDisplay(Function<Player, String> formatFunction) {
		this.formatFunction = formatFunction;
	}

	@Override
	public boolean broadcastChat() {
		return true;
	}

	@Override
	public String getFormat(Player viewer, Player sender) {
		return formatFunction.apply(sender);
	}

}
