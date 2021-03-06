/*
 * HawkEye Redux
 * Copyright (C) 2012-2013 Cubeville <http://www.cubeville.org> and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package org.cubeville.hawkeye.command;

/**
 * Exception thrown when someone does not use a command correctly
 */
public class CommandUsageException extends CommandException {

	private static final long serialVersionUID = 3043821094302311575L;

	private final String usage;

	public CommandUsageException(String message) {
		super(message);
		usage = null;
	}

	public CommandUsageException(String message, String usage) {
		super(message);
		this.usage = usage;
	}

	public String getUsage() {
		return usage;
	}

}
