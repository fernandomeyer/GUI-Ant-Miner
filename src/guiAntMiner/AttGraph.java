package guiAntMiner;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

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

	public AttGraph() {

	}

	// This method is called whenever the contents needs to be painted
	public void paint(Graphics g) {
		// Retrieve the graphics context; this object is used to paint shapes
		Graphics2D g2d = (Graphics2D) g;
		int width = getSize().width - 1;
		int height = getSize().height - 1;
		int x = 0;
		int y = 0;
		g.setClip(x, y, width + 1, height + 1);

		g2d.setPaint(Color.white);
		g2d.fill(new Rectangle2D.Double(x, y, width, height));

		// g2d.setColor(Color.white);

		// Draw an oval that fills the window

		g2d.setPaint(Color.black);
		// g2d.drawOval(x, y, width, height);
	}
}
