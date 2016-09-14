package guiAntMiner;

import java.util.ArrayList;

/**
 * Copyright (C) 2006 Fernando Meyer
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * A full copy of the license is available in gpl.txt and online at
 * http://www.gnu.org/licenses/gpl.txt
 */

class MyList extends ArrayList implements Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4353246525577643694L;

	public Object clone() {
		return super.clone();
	}
}
