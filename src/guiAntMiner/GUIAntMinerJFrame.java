package guiAntMiner;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Event;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.text.DecimalFormat;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

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
public class GUIAntMinerJFrame extends javax.swing.JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	{
		// Set Look & Feel
		try {
			javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
		}
	}

	private JMenuBar jMenuBar1;
	private JMenu jMenu1;
	private JMenuItem jMenuItem1;
	private JLabel jLabelNumberOfInstances;
	private JTable jTable1;
	private JTable jTable2;
	private TableModel jTable1Model;
	private TableModel jTable2Model;
	private JLabel jLabel8;
	private JLabel jLabel4;
	private JLabel jLabel14;
	private JLabel jLabel12;
	private JLabel jLabel6;
	private JTextField jTextField5;
	private JTextField jTextField4;
	private JTextField jTextField3;
	private JTextField jTextField2;
	private JPanel jPanel9;
	private JTextField jTextField1;
	private JLabel jLabel13;
	private JPanel jPanel7;
	private JButton jButton1;
	private JLabel jLabelDistinct;
	private JLabel jLabel11;
	private JPanel jPanel4;
	private JLabel jLabel7;
	private JLabel jLabel10;
	private JLabel jLabel9;
	private JScrollPane jScrollPane3;
	private JPanel jPanel6;
	private JPanel jPanel5;
	private JPanel jPanel3;
	private JTabbedPane jTabbedPane1;
	private JScrollPane jScrollPane1;
	private JLabel jLabelNumberOfAttributes;
	private JLabel jLabel5;
	private JLabel jLabel3;
	private JLabel jLabel2;
	private JLabel jLabel1;
	private JPanel jPanel1;
	private JPanel jPanel2;
	private JMenuItem jMenuItem3;
	private JMenu jMenu2;
	private JMenuItem jMenuItem2;
	private JSeparator jSeparator1;
	private MyFileReader myFileReader;
	private AttGraph canvas1;
	private JTextField jTextField6;
	private JLabel jLabel17;
	private JButton jButton2;
	private JProgressBar jProgressBar1;
	private JPanel jPanel8;
	private JCheckBox jCheckBox3;
	private JCheckBox jCheckBox2;
	private JLabel jLabel15;
	private JCheckBox jCheckBox1;
	private JTextArea jTextArea1;
	private JScrollPane jScrollPane2;
	private boolean isClassifying;
	private CrossValidation cv;
	private Attribute[] attributesArray;
	private DataInstance[] dataInstancesArray;
	private int statsPerAttribute[][][];

	/**
	 * Auto-generated main method to display this JFrame
	 */
	public static void main(String[] args) {
		GUIAntMinerJFrame inst = new GUIAntMinerJFrame();
		inst.setVisible(true);

	}

	public GUIAntMinerJFrame() {
		super();
		try {
			javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			javax.swing.SwingUtilities.updateComponentTreeUI(this);
		} catch (Exception e) {
		}
		initGUI();
	}

	private void initGUI() {
		try {
			{
				jMenuBar1 = new JMenuBar();
				setJMenuBar(jMenuBar1);
				jMenuBar1.setPreferredSize(new java.awt.Dimension(392, 26));
				{
					jMenu1 = new JMenu();
					jMenuBar1.add(jMenu1);
					jMenu1.setText("File");
					jMenu1.setDisplayedMnemonicIndex(0);
					jMenu1.setMnemonic(java.awt.event.KeyEvent.VK_F);
					{
						jMenuItem1 = new JMenuItem();
						jMenu1.add(jMenuItem1);
						jMenuItem1.setText("Open...");
						jMenuItem1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, Event.CTRL_MASK));
						jMenuItem1.setDisplayedMnemonicIndex(0);
						jMenuItem1.setMnemonic(java.awt.event.KeyEvent.VK_O);
						jMenuItem1.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								final JFileChooser fc = new JFileChooser();
								fc.setCurrentDirectory(new File(System.getProperty("user.dir")));
								fc.addChoosableFileFilter(new ArffFilter());
								int returnVal = fc.showOpenDialog(GUIAntMinerJFrame.this);
								if (returnVal == 0) {
									myFileReader = new MyFileReader(fc.getSelectedFile());
									if (myFileReader.fileIsOk()) {
										attributesArray = myFileReader.getAttributesArray();
										dataInstancesArray = myFileReader.getDataInstancesArray();
										initializeStatsPerAttribute();

										setLabel2(myFileReader.getRelation());
										jLabelNumberOfAttributes
												.setText(String.valueOf(myFileReader.getAttributesNo()));
										jLabelNumberOfInstances.setText(String.valueOf(myFileReader.getInstancesNo()));
										setTableAtt1(myFileReader.getAttributesArray());
										jTabbedPane1.setEnabledAt(1, true);
										canvas1.setStatsPerAttribute(statsPerAttribute);
									}
								}
							}
						});
					}
					{
						jSeparator1 = new JSeparator();
						jMenu1.add(jSeparator1);
					}
					{
						jMenuItem2 = new JMenuItem();
						jMenu1.add(jMenuItem2);
						jMenuItem2.setText("Exit");
						jMenuItem2.setDisplayedMnemonicIndex(1);
						jMenuItem2.setMnemonic(java.awt.event.KeyEvent.VK_X);
						jMenuItem2.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								System.exit(0);
							}
						});
					}
				}
				{
					jMenu2 = new JMenu();
					jMenuBar1.add(jMenu2);
					jMenu2.setText("Help");
					jMenu2.setDisplayedMnemonicIndex(0);
					jMenu2.setMnemonic(java.awt.event.KeyEvent.VK_H);
					{
						jMenuItem3 = new JMenuItem();
						jMenu2.add(jMenuItem3);
						jMenuItem3.setText("About GUI Ant-Miner");
						jMenuItem3.setDisplayedMnemonicIndex(0);
						jMenuItem3.setMnemonic(java.awt.event.KeyEvent.VK_A);
						jMenuItem3.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								JDialog dialog = new JDialog(GUIAntMinerJFrame.this, true);
								AboutGUIAntMinerJDialog inst = new AboutGUIAntMinerJDialog(dialog,
										GUIAntMinerJFrame.this);
								inst.setVisible(true);
							}
						});
					}
				}
			}
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			BorderLayout thisLayout = new BorderLayout();
			this.getContentPane().setLayout(thisLayout);
			this.setTitle("GUI Ant-Miner");
			{
				jTabbedPane1 = new JTabbedPane();
				this.getContentPane().add(jTabbedPane1, BorderLayout.CENTER);
				jTabbedPane1.setPreferredSize(new java.awt.Dimension(792, 542));
				{
					jPanel2 = new JPanel();
					GridBagLayout jPanel2Layout = new GridBagLayout();
					jPanel2Layout.columnWeights = new double[] { 1.0, 1.0 };
					jPanel2Layout.columnWidths = new int[] { 400, 420 };
					jPanel2Layout.rowWeights = new double[] { 1.0 };
					jPanel2Layout.rowHeights = new int[] {};
					jTabbedPane1.addTab("Preprocess", null, jPanel2, null);
					// jPanel2.setLayout(jPanel2Layout);
					jPanel2.setPreferredSize(new java.awt.Dimension(787, 514));
					jPanel2.setLayout(jPanel2Layout);
					jPanel2.setMinimumSize(new java.awt.Dimension(400, 400));
					{
						jScrollPane1 = new JScrollPane();
						jPanel2.add(jScrollPane1, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.NORTH,
								GridBagConstraints.BOTH, new Insets(72, 5, 5, 2), 0, 0));
						jScrollPane1.setBounds(7, 80, 385, 200);
						jScrollPane1.setBorder(BorderFactory.createTitledBorder("Attributes"));
						jScrollPane1.setSize(364, 200);
						jScrollPane1.setMinimumSize(new java.awt.Dimension(23, 240));
						jScrollPane1.setPreferredSize(new java.awt.Dimension(385, 250));

					}
					{
						jPanel1 = new JPanel();
						jPanel2.add(jPanel1, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.NORTH,
								GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 2), 0, 0));
						GridBagLayout jPanel1Layout = new GridBagLayout();
						jPanel1Layout.columnWeights = new double[] { 0.1, 0.1, 0.1, 0.1, 0.1, 0.1 };
						jPanel1Layout.columnWidths = new int[] { 30, 66, 120, 63, 100, 20 };
						jPanel1Layout.rowHeights = new int[] { 18, 18 };
						jPanel1Layout.rowWeights = new double[] { 0.1, 0.1 };
						jPanel1.setLayout(jPanel1Layout);

						jPanel1.setBounds(7, 8, 385, 72);
						jPanel1.setBorder(BorderFactory.createTitledBorder("Relation"));
						jPanel1.setSize(320, 59);
						{
							jLabel1 = new JLabel();
							jPanel1.add(jLabel1, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
									GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
							jLabel1.setText("Relation: ");
							jLabel1.setHorizontalTextPosition(SwingConstants.RIGHT);
							jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
							jLabel1.setFont(new java.awt.Font("Dialog", 0, 12));
						}
						{
							jLabel2 = new JLabel();
							jPanel1.add(jLabel2, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
									GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
							jLabel2.setFont(new java.awt.Font("Dialog", 0, 12));
							jLabel2.setName("                   ");
							jLabel2.setText("                ");
						}
						{
							jLabel3 = new JLabel();
							jPanel1.add(jLabel3, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
									GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
							jLabel3.setText("Instances: ");
							jLabel3.setLayout(null);
							jLabel3.setFont(new java.awt.Font("Dialog", 0, 12));
						}
						{
							jLabelNumberOfInstances = new JLabel();
							jPanel1.add(jLabelNumberOfInstances, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
									GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
							jLabelNumberOfInstances.setFont(new java.awt.Font("Dialog", 0, 12));
							jLabelNumberOfInstances.setText(" ");
						}
						{
							jLabel5 = new JLabel();
							jPanel1.add(jLabel5, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
									GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
							jLabel5.setText("Attributes: ");
							jLabel5.setBounds(0, 0, 41, 16);
							jLabel5.setFont(new java.awt.Font("Dialog", 0, 12));
						}
						{
							jLabelNumberOfAttributes = new JLabel();
							jPanel1.add(jLabelNumberOfAttributes, new GridBagConstraints(4, 1, 1, 1, 0.0, 0.0,
									GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
							jLabelNumberOfAttributes.setBounds(0, 0, 41, 16);
							jLabelNumberOfAttributes.setFont(new java.awt.Font("Dialog", 0, 12));
							jLabelNumberOfAttributes.setText("  ");
						}

					}
					{
						jPanel5 = new JPanel();
						BoxLayout jPanel5Layout = new BoxLayout(jPanel5, javax.swing.BoxLayout.Y_AXIS);
						jPanel2.add(jPanel5, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0, GridBagConstraints.NORTH,
								GridBagConstraints.HORIZONTAL, new Insets(5, 2, 0, 5), 0, 0));
						jPanel5.setBounds(396, 8, 385, 250);
						jPanel5.setLayout(jPanel5Layout);
						jPanel5.setBorder(BorderFactory.createTitledBorder("Selected Attribute"));
						jPanel5.setMinimumSize(new java.awt.Dimension(300, 250));
						jPanel5.setSize(318, 250);
						{
							jPanel6 = new JPanel();
							GridBagLayout jPanel6Layout = new GridBagLayout();
							jPanel6Layout.columnWeights = new double[] { 0.1, 0.1, 0.1, 0.1, 0.1, 0.1 };
							jPanel6Layout.columnWidths = new int[] { 30, 52, 120, 92, 58, 25 };
							jPanel6Layout.rowWeights = new double[] { 1.0, 1.0, 0.1 };
							jPanel6Layout.rowHeights = new int[] { 18, 22, 4 };
							jPanel5.add(jPanel6);
							jPanel6.setLayout(jPanel6Layout);
							jPanel6.setBounds(6, 19, 369, 41);
							jPanel6.setPreferredSize(new java.awt.Dimension(376, 35));
							jPanel6.setMinimumSize(new java.awt.Dimension(10, 35));
							{
								jLabel8 = new JLabel();
								jPanel6.add(jLabel8,
										new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
												GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
								jLabel8.setFont(new java.awt.Font("Dialog", 0, 12));
								jLabel8.setText(" ");
							}
							{
								jLabel9 = new JLabel();
								jPanel6.add(jLabel9,
										new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
												GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
								jLabel9.setText("Missing: ");
								jLabel9.setFont(new java.awt.Font("Dialog", 0, 12));
								jLabel9.setHorizontalAlignment(SwingConstants.RIGHT);
								jLabel9.setHorizontalTextPosition(SwingConstants.RIGHT);
							}
							{
								jLabel10 = new JLabel();
								jPanel6.add(jLabel10,
										new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
												GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
								jLabel10.setFont(new java.awt.Font("Dialog", 0, 12));
								jLabel10.setText(" ");
							}
							{
								jLabel11 = new JLabel();
								jPanel6.add(jLabel11,
										new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
												GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
								jLabel11.setText("Distinct Values: ");
								jLabel11.setFont(new java.awt.Font("Dialog", 0, 12));
							}
							{
								jLabelDistinct = new JLabel();
								jPanel6.add(jLabelDistinct,
										new GridBagConstraints(4, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
												GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
								jLabelDistinct.setFont(new java.awt.Font("Dialog", 0, 12));
								jLabelDistinct.setText(" ");
							}
							{
								jLabel7 = new JLabel();
								jPanel6.add(jLabel7,
										new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
												GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
								jLabel7.setText("Name: ");
								jLabel7.setFont(new java.awt.Font("Dialog", 0, 12));
								jLabel7.setBounds(0, 0, 45, 16);
							}
						}
						{
							jScrollPane3 = new JScrollPane();
							jPanel5.add(jScrollPane3);
							jScrollPane3.setBounds(5, 63, 374, 181);
							jScrollPane3.setPreferredSize(new java.awt.Dimension(368, 181));
						}
					}
					{
						jPanel4 = new JPanel();
						BorderLayout jPanel4Layout = new BorderLayout();
						jPanel4.setLayout(jPanel4Layout);
						jPanel2.add(jPanel4, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0, GridBagConstraints.NORTH,
								GridBagConstraints.BOTH, new Insets(263, 4, 5, 7), 0, 0));
						jPanel4.setBounds(398, 264, 381, 225);
						jPanel4.setBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED));
						jPanel4.setMinimumSize(new java.awt.Dimension(10, 210));
						jPanel4.setSize(337, 200);
						jPanel4.setPreferredSize(new java.awt.Dimension(381, 210));
					}
				}
				{
					jPanel3 = new JPanel();
					jTabbedPane1.addTab("Classify", null, jPanel3, null);
					GridBagLayout jPanel3Layout = new GridBagLayout();
					jPanel3Layout.columnWeights = new double[] { 0.0, 1.0 };
					jPanel3Layout.columnWidths = new int[] { 150, 500 };
					jPanel3Layout.rowWeights = new double[] { 1.0 };
					jPanel3Layout.rowHeights = new int[] { 7 };
					jPanel3.setLayout(jPanel3Layout);
					{
						jButton1 = new JButton();
						jPanel3.add(jButton1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
								GridBagConstraints.NONE, new Insets(292, 10, 0, 0), 0, 0));
						jButton1.setText("Start");
						jButton1.setBounds(19, 234, 99, 24);
						jButton1.setToolTipText("Start classification. This operation may take a few minutes.");
						jButton1.setSize(75, 23);
						jButton1.setMinimumSize(new java.awt.Dimension(75, 23));
						jButton1.setMaximumSize(new java.awt.Dimension(75, 23));
						jButton1.setPreferredSize(new java.awt.Dimension(75, 23));
						jButton1.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								setIsClassifying(true);
								cv = new CrossValidation(GUIAntMinerJFrame.this);

								cv.setAttributesArray(attributesArray);
								cv.setDataInstancesArray(dataInstancesArray);
								cv.setNumAnts(Integer.parseInt(jTextField1.getText()));
								cv.setFolds(Integer.parseInt(jTextField2.getText()));
								cv.setMinCasesRule(Integer.parseInt(jTextField3.getText()));
								cv.setConvergenceTest(Integer.parseInt(jTextField5.getText()));
								cv.setNumIterations(Integer.parseInt(jTextField6.getText()));
								cv.setMaxUncoveredCases(Integer.parseInt(jTextField4.getText()));

								cv.start();
							}
						});
					}
					{
						jPanel7 = new JPanel();
						GridBagLayout jPanel7Layout = new GridBagLayout();
						jPanel7Layout.columnWeights = new double[] { 0.0, 0.0, 0.0 };
						jPanel7Layout.columnWidths = new int[] { 20, 135, 60 };
						jPanel7Layout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0 };
						jPanel7Layout.rowHeights = new int[] { 22, 22, 22, 22, 22 };
						jPanel3.add(jPanel7, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.NORTH,
								GridBagConstraints.HORIZONTAL, new Insets(60, 5, 0, 2), 0, 0));
						jPanel7.setBounds(7, 58, 260, 141);
						jPanel7.setLayout(jPanel7Layout);
						jPanel7.setBorder(BorderFactory.createTitledBorder("Other Parameters"));
						jPanel7.setMinimumSize(new java.awt.Dimension(10, 150));
						jPanel7.setPreferredSize(new java.awt.Dimension(10, 150));
						jPanel7.setSize(261, 150);
						{
							jLabel13 = new JLabel();
							jPanel7.add(jLabel13, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
									GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
							jLabel13.setText("Number of Ants:");
							jLabel13.setFont(new java.awt.Font("Dialog", 0, 12));
						}
						{
							jTextField1 = new JTextField();
							jPanel7.add(jTextField1, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
									GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
							jTextField1.setText("5");
							jTextField1.setPreferredSize(new java.awt.Dimension(40, 20));
						}
						{
							jTextField3 = new JTextField();
							jPanel7.add(jTextField3, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
									GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
							jTextField3.setText("5");
							jTextField3.setPreferredSize(new java.awt.Dimension(40, 20));
						}
						{
							jTextField4 = new JTextField();
							jPanel7.add(jTextField4, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0,
									GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
							jTextField4.setText("10");
							jTextField4.setPreferredSize(new java.awt.Dimension(40, 20));
						}
						{
							jTextField5 = new JTextField();
							jPanel7.add(jTextField5, new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0,
									GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
							jTextField5.setText("10");
							jTextField5.setPreferredSize(new java.awt.Dimension(40, 20));
						}
						{
							jLabel6 = new JLabel();
							jPanel7.add(jLabel6, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
									GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
							jLabel6.setText("Rules for Convergence:");
							jLabel6.setFont(new java.awt.Font("Dialog", 0, 12));
						}
						{
							jLabel12 = new JLabel();
							jPanel7.add(jLabel12, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
									GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
							jLabel12.setText("Min. Cases per Rule:");
							jLabel12.setFont(new java.awt.Font("Dialog", 0, 12));
						}
						{
							jLabel14 = new JLabel();
							jPanel7.add(jLabel14, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
									GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
							jLabel14.setText("Max. uncovered Cases:");
							jLabel14.setFont(new java.awt.Font("Dialog", 0, 12));
						}
						{
							jLabel15 = new JLabel();
							jPanel7.add(jLabel15, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
									GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
							jLabel15.setText("Number of Iterations:");
							jLabel15.setFont(new java.awt.Font("Dialog", 0, 12));
						}
						{
							jTextField6 = new JTextField();
							jPanel7.add(jTextField6, new GridBagConstraints(2, 4, 1, 1, 0.0, 0.0,
									GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
							jTextField6.setPreferredSize(new java.awt.Dimension(40, 20));
							jTextField6.setText("100");
						}
					}
					{
						jPanel9 = new JPanel();
						jPanel3.add(jPanel9, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.NORTH,
								GridBagConstraints.HORIZONTAL, new Insets(5, 5, 0, 2), 0, 0));
						GridBagLayout jPanel9Layout = new GridBagLayout();
						jPanel9Layout.columnWidths = new int[] { 20, 135, 60 };
						jPanel9Layout.columnWeights = new double[] { 0.0, 0.0, 0.0 };
						jPanel9Layout.rowHeights = new int[] { 22 };
						jPanel9Layout.rowWeights = new double[] { 0.1 };
						jPanel9.setBorder(BorderFactory.createTitledBorder("Cross Validation"));
						jPanel9.setLayout(jPanel9Layout);
						jPanel9.setBounds(7, 8, 260, 50);
						{
							jLabel4 = new JLabel();
							jPanel9.add(jLabel4, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
									GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
							jLabel4.setText("Folds: ");
							jLabel4.setFont(new java.awt.Font("Dialog", 0, 12));
						}
						{
							jTextField2 = new JTextField();
							jPanel9.add(jTextField2, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
									GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
							jTextField2.setText("10");
							jTextField2.setPreferredSize(new java.awt.Dimension(40, 20));
						}
					}
					{
						jScrollPane2 = new JScrollPane();
						jPanel3.add(jScrollPane2, new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0,
								GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 2, 0, 3), 0, 0));
						jScrollPane2.setBounds(271, 8, 509, 501);
						jScrollPane2.setBorder(BorderFactory.createTitledBorder("Output"));
						{
							jTextArea1 = new JTextArea();
							jScrollPane2.setViewportView(jTextArea1);
							jTextArea1.setBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED));
							jTextArea1.setEditable(false);
						}
					}
					{
						jCheckBox1 = new JCheckBox();
						jPanel3.add(jCheckBox1,
								new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
										GridBagConstraints.NONE, new Insets(212, 10, 0, 0), 0, 0));
						jCheckBox1.setText("Clear output screen");
						jCheckBox1.setBounds(17, 201, 133, 30);
						jCheckBox1.setFont(new java.awt.Font("Dialog", 0, 12));
						jCheckBox1.setSelected(true);
						jCheckBox1
								.setToolTipText("Clear the output screen the next time a classification task is run.");
					}
					{
						jCheckBox2 = new JCheckBox();
						jPanel3.add(jCheckBox2,
								new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
										GridBagConstraints.NONE, new Insets(235, 10, 0, 0), 0, 0));
						jCheckBox2.setText("Print test set");
						jCheckBox2.setFont(new java.awt.Font("Dialog", 0, 12));
					}
					{
						jCheckBox3 = new JCheckBox();
						jPanel3.add(jCheckBox3,
								new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
										GridBagConstraints.NONE, new Insets(258, 10, 0, 0), 0, 0));
						jCheckBox3.setText("Print training set");
						jCheckBox3.setFont(new java.awt.Font("Dialog", 0, 12));
					}
					{
						jButton2 = new JButton();
						jPanel3.add(jButton2, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
								GridBagConstraints.NONE, new Insets(292, 95, 0, 0), 0, 0));
						jButton2.setText("Cancel");
						jButton2.setToolTipText("Cancel task.");
						jButton2.setPreferredSize(new java.awt.Dimension(75, 23));
						jButton2.setMaximumSize(new java.awt.Dimension(75, 23));
						jButton2.setMinimumSize(new java.awt.Dimension(75, 23));
						jButton2.setEnabled(false);
						jButton2.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								cv.stop();
							}
						});
						jButton2.addMouseListener(new MouseAdapter() {
							public void mouseExited(MouseEvent evt) {
								if (isClassifying)
									jTabbedPane1.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
							}

							public void mouseEntered(MouseEvent evt) {
								jTabbedPane1.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
							}
						});
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		pack();
		this.setSize(800, 600);
		setGraph();

		jTabbedPane1.setEnabledAt(1, false);
		jTabbedPane1.setBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED));
		{
			jPanel8 = new JPanel();
			GridBagLayout jPanel8Layout = new GridBagLayout();
			jPanel8Layout.columnWeights = new double[] { 0.1, 0.1 };
			jPanel8Layout.columnWidths = new int[] { 7, 7 };
			jPanel8Layout.rowWeights = new double[] { 0.1 };
			jPanel8Layout.rowHeights = new int[] { 7 };
			jPanel8.setLayout(jPanel8Layout);
			this.getContentPane().add(jPanel8, BorderLayout.SOUTH);
			jPanel8.setBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED));
			{
				jProgressBar1 = new JProgressBar();
				jPanel8.add(jProgressBar1, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST,
						GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				jProgressBar1.setPreferredSize(new java.awt.Dimension(140, 18));
			}
			{
				jLabel17 = new JLabel();
				jPanel8.add(jLabel17, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
						GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
				jLabel17.setText("Idle");
			}
		}

	}

	public void setLabel2(String label) {
		jLabel2.setText(label);
	}

	public JLabel getJLabel2() {
		return jLabel2;
	}

	public boolean getJCheckBox1IsSelected() {
		return jCheckBox1.isSelected();
	}

	public boolean getJCheckBox2IsSelected() {
		return jCheckBox2.isSelected();
	}

	public boolean getJCheckBox3IsSelected() {
		return jCheckBox3.isSelected();
	}

	public String getjTextField2Value() {
		return jTextField2.getText();
	}

	public JTextField getJTextField1() {
		return jTextField1;
	}

	public JTextField getJTextField3() {
		return jTextField3;
	}

	public JTextField getJTextField4() {
		return jTextField4;
	}

	public JTextField getJTextField5() {
		return jTextField5;
	}

	public JTextField getJTextField6() {
		return jTextField6;
	}

	public JTextArea getJTextArea1() {
		return jTextArea1;
	}

	public JProgressBar getJProgressBar1() {
		return jProgressBar1;
	}

	public void setTableAtt1(Attribute[] attributesArray) {
		String numberAndName[][] = new String[attributesArray.length][2];
		for (int n = 0; n < attributesArray.length; n++) {
			numberAndName[n][0] = new Integer(n + 1).toString();
			numberAndName[n][1] = attributesArray[n].getAttributeName();
		}

		jTable1Model = new MyTableModel(numberAndName, new String[] { "#", "Name" });
		jTable1 = new JTable();
		jScrollPane1.setViewportView(jTable1);
		jTable1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jTable1.setModel(jTable1Model);

		ListSelectionModel rowSM = jTable1.getSelectionModel();
		rowSM.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				// Ignore extra messages.
				if (e.getValueIsAdjusting())
					return;
				ListSelectionModel lsm = (ListSelectionModel) e.getSource();
				if (lsm.isSelectionEmpty()) {
					// no rows are selected
				} else {
					int selectedRow = lsm.getMinSelectionIndex();
					setTableAtt2(selectedRow, statsPerAttribute[selectedRow]);
					jLabel8.setText(ar()[selectedRow].getAttributeName());
					canvas1.setAttribute(selectedRow);
					canvas1.repaint();

					DecimalFormat myFormatter = new DecimalFormat("###.#");
					jLabel10.setText(String.valueOf(calculateMissing(selectedRow)) + " (" + myFormatter.format(
							((float) calculateMissing(selectedRow) / myFileReader.getDataInstancesArray().length) * 100)
							+ "%)");

					jLabelDistinct.setText(String.valueOf(jTable2.getModel().getRowCount()));
				}
			}
		});
		rowSM.setSelectionInterval(0, 0);
	}

	private Attribute[] ar() {
		return this.attributesArray;
	}

	public void setTableAtt2(int attributeIndex, int[][] typesOccurences) {
		String labelAndCount[][] = new String[typesOccurences.length][2];
		for (int n = 0; n < typesOccurences.length; n++) {
			int sum = 0;
			for (int i = 0; i < typesOccurences[n].length; i++) {
				sum += typesOccurences[n][i];
			}
			labelAndCount[n][0] = attributesArray[attributeIndex].getTypes()[n];
			labelAndCount[n][1] = new Integer(sum).toString();
		}
		jTable2Model = new MyTableModel(labelAndCount, new String[] { "Attribute Value", "Quantity of Instances" });
		jTable2 = new JTable();
		jScrollPane3.setViewportView(jTable2);
		jTable2.setModel(jTable2Model);
	}

	public void setGraph() {
		canvas1 = new AttGraph();
		jPanel4.add(canvas1, BorderLayout.CENTER);

		// canvas1.setSize(347, 220);
		canvas1.setBounds(2, 2, 377, 221);
	}

	public void setInitialState() {
		jTabbedPane1.setEnabledAt(1, false);
		jTabbedPane1.setSelectedIndex(0);
		if (jTable1 != null) {
			jTable1.removeAll();
			jTable1.setVisible(false);
			jTable1Model = null;
		}
		if (jTable2 != null) {
			jTable2.removeAll();
			jTable2.setVisible(false);
			jTable2Model = null;
		}
		jLabel2.setText("");
		jLabelNumberOfInstances.setText("");
		jLabelNumberOfAttributes.setText("");
		jLabel8.setText("");
		jLabel10.setText("");
		jLabelDistinct.setText("");
	}

	public void setIsClassifying(boolean b) {
		if (b) {
			jLabel17.setText("Classifying " + jLabel2.getText() + "...");
			isClassifying = true;
			jMenuItem1.setEnabled(false);
			jButton1.setEnabled(false);
			jButton2.setEnabled(true);
			jTabbedPane1.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		} else {
			jLabel17.setText("Idle");
			isClassifying = false;
			jMenuItem1.setEnabled(true);
			jButton1.setEnabled(true);
			jButton2.setEnabled(false);
			jTabbedPane1.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
	}

	public MyFileReader getMyFileReader() {
		return myFileReader;
	}

	/**
	 * Auto-generated method for setting the popup menu for a component
	 */
	@SuppressWarnings("unused")
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

	public JLabel getJLabel17() {
		return jLabel17;
	}

	class MyTableModel extends DefaultTableModel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public MyTableModel(Object[][] data, Object[] columnNames) {
			setDataVector(data, columnNames);
		}

		public boolean isCellEditable(int row, int column) {
			return false;
		}
	}

	/**
	 * @param attributePos
	 * @return
	 */
	private int calculateMissing(int attributePos) {
		int count = 0;
		for (int x = 0; x < dataInstancesArray.length; x++) {
			if (dataInstancesArray[x].getValues()[attributePos] == -1)
				count++;
		}
		return count;
	}

	/**
	 * Determines the number of occurrences of the attribute values in the
	 * dataset
	 * 
	 * @param attributePos
	 * @return
	 */
	private void initializeStatsPerAttribute() {
		int attArrayLength = attributesArray.length;
		int lastAtt = attArrayLength - 1;
		int numTypesOfLastAtt = (attributesArray[lastAtt]).getIntTypesArray().length;
		statsPerAttribute = new int[attArrayLength][][];
		
		for (int attribute = 0; attribute < attArrayLength; attribute++) {
			int numTypes = (attributesArray[attribute]).getIntTypesArray().length;
			statsPerAttribute[attribute] = new int[numTypes][];
			for (int type = 0; type < numTypes; type++) {
				statsPerAttribute[attribute][type] = new int[numTypesOfLastAtt];
			}
		}
		for (int i = 0; i < dataInstancesArray.length; i++) {
			int[] value = dataInstancesArray[i].getValues();
			for (int attribute = 0; attribute < attArrayLength; attribute++) {
				if (value[attribute] != -1 && value[lastAtt] != -1) {
					statsPerAttribute[attribute][value[attribute]][value[lastAtt]]++;
				}
			}
		}
	}

}
