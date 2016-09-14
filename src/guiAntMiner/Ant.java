package guiAntMiner;

import java.util.List;

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

public class Ant implements Cloneable {
	private int[] rulesArray;
	private MyList instancesIndexList;
	private int ruleConsequent;
	private double ruleQuality;
	private byte[] memory;

	public Ant(int memorySize) {
		rulesArray = new int[memorySize];
		for (int x = 0; x < memorySize; x++)
			rulesArray[x] = -1;
		instancesIndexList = new MyList();
		memory = new byte[memorySize];
	}

	public int[] getRulesArray() {
		return rulesArray;
	}

	public List getInstancesIndexList() {
		return instancesIndexList;
	}

	public void setInstancesIndexList(MyList list) {
		instancesIndexList = list;
	}

	public void setRuleConsequent(int i) {
		ruleConsequent = i;
	}

	public int getRuleConsequent() {
		return ruleConsequent;
	}

	public void setRulesArray(int[] rulesArray) {
		this.rulesArray = rulesArray;
	}

	public void clearRulesArray() {
		for (int x = 0; x < rulesArray.length; x++)
			rulesArray[x] = -1;
	}

	public byte[] getMemory() {
		return memory;
	}

	public void setMemory(byte[] memory) {
		this.memory = memory;
	}

	public void setRuleQuality(double d) {
		ruleQuality = d;
	}

	public double getRuleQuality() {
		return ruleQuality;
	}

	public boolean hasRules() {
		for (int x = 0; x < rulesArray.length; x++) {
			if (rulesArray[x] != -1) {
				return true;
			}
		}
		return false;
	}

	public Object clone() throws CloneNotSupportedException {
		Ant antClone = new Ant(this.memory.length);
		antClone.setRulesArray((int[]) this.rulesArray.clone());
		antClone.setInstancesIndexList((MyList) this.instancesIndexList.clone());
		antClone.setRuleConsequent(this.ruleConsequent);
		antClone.setRuleQuality(this.ruleQuality);
		antClone.setMemory(this.memory);
		return antClone;
	}
}
