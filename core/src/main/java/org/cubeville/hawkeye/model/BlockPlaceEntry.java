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

package org.cubeville.hawkeye.model;

import org.cubeville.hawkeye.DefaultActions;
import org.cubeville.hawkeye.block.BlockState;
import org.cubeville.hawkeye.location.Location;

public class BlockPlaceEntry extends AbstractBlockEntry {

	public BlockPlaceEntry(DatabaseEntry entry) {
		super(DefaultActions.BLOCK_PLACE, entry);
	}

	public BlockPlaceEntry(String player, Location location, BlockState old, BlockState placed) {
		super(DefaultActions.BLOCK_PLACE, player, location, old, placed);
	}

	@Override
	public String getOutput() {
		return super.format(getNewBlockState());
	}

}
