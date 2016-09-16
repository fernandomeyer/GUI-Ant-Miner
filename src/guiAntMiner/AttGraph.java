package guiAntMiner;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.Random;

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

class AttGraph extends Canvas {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int statsPerAttribute[][][];
	private int attribute;
	private Color[] colors; 

	public AttGraph() {
		
	}

	public int[][][] getStatsPerAttribute() {
		return statsPerAttribute;
	}

	public void setStatsPerAttribute(int statsPerAttribute[][][]) {
		this.statsPerAttribute = statsPerAttribute;
		initRandomColors();
	}

	public int getAttribute() {
		return attribute;
	}

	public void setAttribute(int attribute) {
		this.attribute = attribute;
	}

	// This method is called whenever content needs to be painted
	public void paint(Graphics g) {
		// Retrieve the graphics context; this object is used to paint shapes
		Graphics2D g2d = (Graphics2D) g;
		int width = getSize().width - 1;
		int height = getSize().height - 1;
		int x = 0;
		int y = 0;
		g.setClip(x, y, width + 1, height + 1);
		
		if (statsPerAttribute != null) {
			g2d.setPaint(Color.white);
			g2d.fill(new Rectangle2D.Double(x, y, width, height));
			drawDist(g2d, width, height);
		}
	}
	
	public void drawDist(Graphics2D g2d, int width, int height) {
		int numTypes = statsPerAttribute[attribute].length;
		int lastAtt = statsPerAttribute.length - 1;
		int numTypesOfLastAtt = statsPerAttribute[lastAtt].length;
		
		double columnTotalWidth = (double) width / numTypes;
		double column1_3 = columnTotalWidth * .1;
		double column2 = columnTotalWidth * .8;
		double posStartColumn = column1_3;
		
		int max = 0;
		for (int i = 0; i < numTypes; i++) {
			int sum = 0;
			for (int j = 0; j < numTypesOfLastAtt; j++) {
				sum += statsPerAttribute[attribute][i][j];
			}
			if (sum > max) {
				max = sum;
			}
		}
		double heightUnit = (double) (height * .9) / max;
		
		for (int i = 0; i < numTypes; i++) {
			int sum = 0;
			int sumTypeHeight = 0;
			for (int j = 0; j < numTypesOfLastAtt; j++) {
				sum += statsPerAttribute[attribute][i][j];
				g2d.setPaint(colors[j]);
				int typeHeight = (int) (heightUnit * statsPerAttribute[attribute][i][j]);
				sumTypeHeight += typeHeight;
				g2d.fillRect((int) posStartColumn, height-sumTypeHeight, (int) column2, typeHeight);
			}
			g2d.setPaint(Color.black);
			g2d.drawString(String.valueOf(sum), (int) posStartColumn, height-sumTypeHeight-1);
			posStartColumn += columnTotalWidth;
		}
	}
	
	private void initRandomColors() {
		Random random = new Random();
		colors = new Color[200];
		for (int i = 0; i < 200; i++) {
			colors[i] = new Color(random.nextInt(0xFFFFFF));
		}
	}
}
