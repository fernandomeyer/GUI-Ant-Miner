package guiAntMiner;

import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

/**
 * Copyright (C) 2007  Fernando Meyer
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * A full copy of the license is available in gpl.txt and online at
 * http://www.gnu.org/licenses/gpl.txt
 */

/**
 * This code was generated using CloudGarden's Jigloo SWT/Swing GUI Builder,
 * which is free for non-commercial use. If Jigloo is being used commercially
 * (ie, by a corporation, company or business for any purpose whatever) then you
 * should purchase a license for each developer using Jigloo. Please visit
 * www.cloudgarden.com for details. Use of Jigloo implies acceptance of these
 * licensing terms. ************************************* A COMMERCIAL LICENSE
 * HAS NOT BEEN PURCHASED for this machine, so Jigloo or this code cannot be
 * used legally for any corporate or commercial purpose.
 * *************************************
 */
public class AboutGUIAntMinerJDialog extends javax.swing.JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1893243061264575311L;

	{
		// Set Look & Feel
		try {
			javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			// e.printStackTrace();
		}
	}

	private JLabel jLabel1;
	private JLabel jLabel2;
	private JPanel jPanel1;
	private JButton jButton2;
	private JLabel jLabel4;
	private JTextPane jTextPane3;
	private JTextPane jTextPane2;
	private JTextPane jTextPane1;
	private JLabel jLabel3;
	private JPanel jPanel2;
	private JLabel jLabel5;
	private JButton jButton1;
	private JFrame parent;

	/**
	 * Auto-generated main method to display this JDialog
	 */
	/*
	 * public static void main(String[] args) { JFrame frame = new JFrame();
	 * AboutAntMinerJDialog inst = new AboutAntMinerJDialog(frame);
	 * inst.setVisible(true); }
	 */

	public AboutGUIAntMinerJDialog(JDialog frame, JFrame parent) {
		super(frame, "About GUI Ant-Miner", true);
		this.parent = parent;
		initGUI();
	}

	private void initGUI() {
		try {
			GridBagLayout thisLayout = new GridBagLayout();
			thisLayout.columnWeights = new double[] { 0.1 };
			thisLayout.columnWidths = new int[] { 7 };
			thisLayout.rowWeights = new double[] { 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1 };
			thisLayout.rowHeights = new int[] { 25, 10, 20, 8, 180, 11, 52, 2, 7 };
			this.getContentPane().setLayout(thisLayout);
			this.setModal(true);
			this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

			Point p = parent.getLocationOnScreen();
			this.setLocation(new Double(p.getX() + 30).intValue(), new Double(p.getY() + 50).intValue());

			this.getContentPane().setForeground(new java.awt.Color(255, 255, 255));
			this.getContentPane().setBackground(new java.awt.Color(255, 255, 255));
			{
				jLabel2 = new JLabel();
				this.getContentPane().add(jLabel2, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
						GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(0, 11, 0, 0), 0, 0));
				jLabel2.setText("Version 1.3");
			}
			{
				jLabel1 = new JLabel();
				this.getContentPane().add(jLabel1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
						GridBagConstraints.SOUTHWEST, GridBagConstraints.NONE, new Insets(0, 11, 0, 0), 0, 0));
				GridLayout jLabel1Layout = new GridLayout(3, 1);
				jLabel1Layout.setRows(3);
				jLabel1Layout.setVgap(33);
				jLabel1.setLayout(jLabel1Layout);
				jLabel1.setText("GUI Ant-Miner");
				jLabel1.setFont(new java.awt.Font("Dialog", 1, 14));
			}
			{
				jPanel1 = new JPanel();
				FlowLayout jPanel1Layout = new FlowLayout();
				jPanel1Layout.setAlignment(FlowLayout.RIGHT);
				jPanel1Layout.setHgap(15);
				jPanel1Layout.setVgap(9);
				jPanel1.setLayout(jPanel1Layout);
				this.getContentPane().add(jPanel1, new GridBagConstraints(0, 8, 1, 2, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(7, 0, 0, 0), 0, 0));
				{
					jButton1 = new JButton();
					jPanel1.add(jButton1);
					jButton1.setText("Ok");
					jButton1.setPreferredSize(new java.awt.Dimension(65, 23));
					jButton1.setLocation(new java.awt.Point(0, 7));
					jButton1.setBounds(246, 6, 56, 24);
					jButton1.setMaximumSize(new java.awt.Dimension(65, 23));
					jButton1.setMinimumSize(new java.awt.Dimension(65, 23));
					jButton1.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							setVisible(false);
						}
					});
				}
			}
			{
				jLabel5 = new JLabel();
				this.getContentPane().add(jLabel5, new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0,
						GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				jLabel5.setIcon(new ImageIcon(getClass().getClassLoader().getResource("resources/images/udesc_joinville.png")));
				jLabel5.setToolTipText("State University of Santa Catarina, campus Joinville, Brazil.");
			}
			{
				jButton2 = new JButton();
				this.getContentPane().add(jButton2, new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0,
						GridBagConstraints.PAGE_END, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				jButton2.setText(
						"<html><a href=\"http://www.joinville.udesc.br\">http://www.joinville.udesc.br</a></html>");
				jButton2.setBackground(new java.awt.Color(255, 255, 255));
				jButton2.setContentAreaFilled(false);
				jButton2.setBorderPainted(false);
				jButton2.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
				jButton2.setFont(new java.awt.Font("Arial", 0, 11));
				jButton2.setMaximumSize(new java.awt.Dimension(50, 9));
				jButton2.setMinimumSize(new java.awt.Dimension(180, 14));
				jButton2.setToolTipText(
						"Website of the State University of Santa Catarina, campus Joinville, Brazil.");
				jButton2.addMouseListener(new MouseAdapter() {
					public void mouseEntered(MouseEvent evt) {
						setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
					}

					public void mouseExited(MouseEvent evt) {
						setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
					}
				});
				jButton2.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						String[] cmd = new String[4];
						cmd[0] = "cmd.exe";
						cmd[1] = "/C";
						cmd[2] = "start";
						cmd[3] = "http://www.joinville.udesc.br";
						try {
							Runtime.getRuntime().exec(cmd);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				});
			}
			{
				jPanel2 = new JPanel();
				GridBagLayout jPanel2Layout = new GridBagLayout();
				jPanel2Layout.columnWeights = new double[] { 0.1 };
				jPanel2Layout.columnWidths = new int[] { 7 };
				jPanel2Layout.rowWeights = new double[] { 0.1, 0.1, 0.1, 0.1 };
				jPanel2Layout.rowHeights = new int[] { 7, 7, 7, 7 };
				this.getContentPane().add(jPanel2, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 11, 0, 11), 0, 0));

				jPanel2.setMinimumSize(new java.awt.Dimension(530, 10));
				jPanel2.setPreferredSize(new java.awt.Dimension(530, 10));
				jPanel2.setSize(540, 54);
				jPanel2.setBackground(new java.awt.Color(240, 240, 240));
				jPanel2.setLayout(jPanel2Layout);
				{
					jLabel4 = new JLabel();
					jPanel2.add(jLabel4, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
							GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
					jLabel4.setText("Copyright Notice");
					jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11));
				}
				{
					jTextPane1 = new JTextPane();
					jPanel2.add(jTextPane1, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
							GridBagConstraints.BOTH, new Insets(0, 11, 0, 11), 0, 0));
					jTextPane1.setText(
							"This software is freely available for research and teaching. It is not meant for commercial purposes. If you publish any material involving the use of this software, please quote the original paper that describes the algorithm in detail, as suggested below:");
					jTextPane1.setOpaque(false);
					jTextPane1.setEditable(false);
					jTextPane1.setFocusable(false);
					jTextPane1.setLocation(new java.awt.Point(555, 0));
				}
				{
					jTextPane2 = new JTextPane();
					jPanel2.add(jTextPane2, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
							GridBagConstraints.BOTH, new Insets(0, 11, 0, 11), 0, 0));
					jTextPane2.setText(
							"Parpinelli, R.S., Lopes, H.S., Freitas, A.A. \"Data mining with an ant colony optimization algorithm\". IEEE Transactions on Evolutionary Computation, special issue on Ant Colony Algorithms, v. 6, n. 4, p. 321-332, August, 2002.");
					jTextPane2.setOpaque(false);
					jTextPane2.setEditable(false);
					jTextPane2.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
				}
				{
					jTextPane3 = new JTextPane();
					jPanel2.add(jTextPane3, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
							GridBagConstraints.BOTH, new Insets(0, 11, 0, 11), 0, 0));
					jTextPane3.setText(
							"We do not take any responsibility for the results that you obtain with this software, we do not offer any guarantees, nor do we help with the maintenance of this program.");
					jTextPane3.setOpaque(false);
					jTextPane3.setEditable(false);
					jTextPane3.setFocusable(false);
				}
			}
			{
				jLabel3 = new JLabel();
				this.getContentPane().add(jLabel3, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
						GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(0, 11, 0, 0), 0, 0));
				jLabel3.setText("Developed by Fernando Meyer. Initiated under supervision of Rafael Stubs Parpinelli.");
			}
			this.setSize(580, 386);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Auto-generated method for setting the popup menu for a component
	 */
	private void setComponentPopupMenu(final java.awt.Component parent, final javax.swing.JPopupMenu menu) {
		parent.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent e) {
				if (e.isPopupTrigger())
					menu.show(parent, e.getX(), e.getY());
			}

			public void mouseReleased(java.awt.event.MouseEvent e) {
				if (e.isPopupTrigger())
					menu.show(parent, e.getX(), e.getY());
			}
		});
	}

}
