package net.codingarea.display.chat;

import org.bukkit.entity.Player;

import java.util.function.BiFunction;

/**
 * @author KxmischesDomi | https://github.com/kxmischesdomi
 * @since 1.0
 */
public class ViewerSpecificChatDisplay implements IChatDisplay {

	private final BiFunction<Player, Player, String> formatFunction;

	/**
	 * @param formatFunction first player is viewer second is the sender
	 */
	public ViewerSpecificChatDisplay(BiFunction<Player, Player, String> formatFunction) {
		this.formatFunction = formatFunction;
	}

	@Override
	public boolean broadcastChat() {
		return false;
	}

	@Override
	public String getFormat(Player viewer, Player sender) {
		return formatFunction.apply(viewer, sender);
	}

}
