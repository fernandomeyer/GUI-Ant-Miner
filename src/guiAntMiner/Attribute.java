package guiAntMiner;

/**
 * Copyright (C) 2007 Fernando Meyer
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

public class Attribute {
	private String attributeName;
	private String[] typesArray;
	private int[] intTypesArray;

	public Attribute(String[] typesArray) {
		this.typesArray = typesArray;
		initializeIntTypesArray();
	}

	private void initializeIntTypesArray() {
		intTypesArray = new int[typesArray.length];
		for (int x = 0; x < typesArray.length; x++) {
			intTypesArray[x] = x;
		}
	}

	public void setAttributeName(String name) {
		attributeName = name;
	}

	public String getAttributeName() {
		return attributeName;
	}

	public String[] getTypes() {
		return typesArray;
	}

	public int[] getIntTypesArray() {
		return intTypesArray;
	}

	public int indexOf(String value) {
		for (int x = 0; x < typesArray.length; x++) {
			if (value.compareTo(typesArray[x]) == 0) {
				return x;
			}
		}
		return -1;
	}
}
