package net.codingarea.display.bukkit.tab;

import de.dytanic.cloudnet.driver.CloudNetDriver;
import de.dytanic.cloudnet.driver.permission.IPermissionGroup;
import de.dytanic.cloudnet.driver.permission.IPermissionUser;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;

/**
 * Represents an entry in the tablist with all attributes
 *
 * @author KxmischesDomi | https://github.com/kxmischesdomi
 * @since 1.0
 */
@Builder
@Getter
@Setter
public final class TabEntry {

	private String name;
	private String prefix = "";
	private String display = "";
	private String color;
	private String suffix = "";
	private int sortId = 0;

	public TabEntry(String name, @Nullable String prefix, @Nullable String display,
					@Nullable String color, @Nullable String suffix, int sortId) {
		this.name = name;
		this.prefix = prefix;
		this.display = display;
		this.color = color;
		this.suffix = suffix;
		this.sortId = sortId;
	}

	public TabEntry(UUID uuid) {
		IPermissionUser user = CloudNetDriver.getInstance().getPermissionManagement().getUser(uuid);
		if (user == null) {
			return;
		}
		IPermissionGroup group = CloudNetDriver.getInstance().getPermissionManagement()
				.getHighestPermissionGroup(user);

		this.name = group.getName();
		this.prefix = group.getPrefix();
		this.display = group.getDisplay();
		this.color = group.getColor();
		this.suffix = group.getSuffix();
		this.sortId = group.getSortId();
	}

}
