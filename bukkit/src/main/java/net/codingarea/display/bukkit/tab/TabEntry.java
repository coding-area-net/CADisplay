package net.codingarea.display.bukkit.tab;

import de.dytanic.cloudnet.driver.CloudNetDriver;
import de.dytanic.cloudnet.driver.permission.IPermissionGroup;
import de.dytanic.cloudnet.driver.permission.IPermissionUser;

import java.util.UUID;

import org.jetbrains.annotations.Nullable;

/**
 * Represents an entry in the tablist with all attributes
 *
 * @author KxmischesDomi | https://github.com/kxmischesdomi
 * @since 1.0
 */
public final class TabEntry {

	private String name;
	private String prefix;
	private String display;
	private String color;
	private String suffix;
	private int sortId;

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

	public String getName() {
		return name;
	}

	public String getPrefix() {
		return prefix;
	}

	public String getDisplay() {
		return display;
	}

	public String getColor() {
		return color;
	}

	public String getSuffix() {
		return suffix;
	}

	public int getSortId() {
		return sortId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public void setSortId(int sortId) {
		this.sortId = sortId;
	}

}
