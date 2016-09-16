package guiAntMiner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import javax.swing.JOptionPane;

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

public class MyFileReader {
	private String relation;

	private DataInstance[] dataInstancesArray;
	private Attribute[] attributesArray;
	private List<String[]> attributesList;
	private List<int[]> dataInstancesList;

	private boolean fileIsOk;

	public MyFileReader(File file) {
		List<String> attributesNameList = new ArrayList<String>();

		attributesList = new ArrayList<String[]>();

		dataInstancesList = new ArrayList<int[]>();

		try {
			BufferedReader in = new BufferedReader(new FileReader(file));

			for (String s = in.readLine(); s != null; s = in.readLine()) {
				if (s.startsWith("@")) {
					if (s.toUpperCase().contains("RELATION")) {
						relation = s.substring(s.indexOf(" ") + 1);
					} else if (s.toUpperCase().contains("ATTRIBUTE")) {
						s = s.substring(s.indexOf(" ") + 1);
						attributesNameList.add(s.substring(0, s.indexOf("{")).trim().replaceAll("'", ""));

						s = s.substring(s.indexOf("{") + 1, s.indexOf("}"));
						String[] v = s.split(",");
						for (int n = 0; n < v.length; n++)
							v[n] = v[n].trim().replaceAll("'", "");
						attributesList.add(v);
					} else if (s.toUpperCase().contains("DATA")) {
						// convert attributesList to array
						int x = 0;
						attributesArray = new Attribute[attributesList.size()];
						for (ListIterator<String[]> i = attributesList.listIterator(); i.hasNext();) {
							Attribute attribute = new Attribute((String[]) i.next());
							attribute.setAttributeName((String) attributesNameList.get(x));
							attributesArray[x] = attribute;
							x++;
						}
						readData(in);
					}
				}
			}

			dataInstancesArray = new DataInstance[dataInstancesList.size()];
			int n = 0;
			for (ListIterator<int[]> i = dataInstancesList.listIterator(); i.hasNext();) {
				DataInstance dataInstance = new DataInstance((int[]) i.next());
				dataInstancesArray[n++] = dataInstance;
			}
			if (attributesArray == null)
				throw new IOException("File error");
			fileIsOk = true;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"<html><font face=Dialog size=3>Error reading file.<br><br>Make sure the file conforms to the arff format<br>and that it only contains nominal attributes.</font></html>",
					"Error", JOptionPane.ERROR_MESSAGE);
		}

	}

	public boolean fileIsOk() {
		return fileIsOk;
	}

	public int getAttributesNo() {
		return attributesArray.length;
	}

	public int getInstancesNo() {
		return dataInstancesArray.length;
	}

	public Attribute[] getAttributesArray() {
		return attributesArray;
	}

	public DataInstance[] getDataInstancesArray() {
		return dataInstancesArray;
	}

	public String getRelation() {
		return relation;
	}

	/**
	 * Reads instances line by line
	 * 
	 * @param in
	 * @throws IOException
	 */
	private void readData(BufferedReader in) throws IOException {
		for (String s2 = in.readLine(); s2 != null; s2 = in.readLine()) {
			if (!s2.startsWith("%")) {
				String[] v1 = s2.split(",");
				int dataArray[] = new int[attributesList.size()];
				for (int y = 0; y < v1.length; y++) {
					v1[y] = v1[y].trim().replaceAll("'", "");
					dataArray[y] = attributesArray[y].indexOf(v1[y]);
				}
				dataInstancesList.add(dataArray);
			}
		}
	}

}
