package guiAntMiner;

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

public class DataInstance implements Cloneable {
	private int[] valuesArray;
	private int crossValidationGroup;

	public DataInstance(int[] valueArray) {
		this.valuesArray = valueArray;
		crossValidationGroup = -1;
	}

	public int[] getValues() {
		return valuesArray;
	}

	public void setValues(int[] p) {
		valuesArray = p;
	}

	public void setCrossValidationGroup(int group) {
		crossValidationGroup = group;
	}

	public int getCrossValidationGroup() {
		return crossValidationGroup;
	}

	public int getClassValue() {
		return valuesArray[valuesArray.length - 1];
	}

	public Object clone() throws CloneNotSupportedException {
		DataInstance myClone = (DataInstance) super.clone(); // shallow copy of
																// object fields
		return myClone;
	}
}
