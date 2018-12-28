package MyFrame;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import Robot.Play;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import GIS.Box;
import GIS.Fruit;
import GIS.Ghost;
import GIS.Packman;
import GIS.Player;
import GIS.Types;
import Geom.MyCoords;
import Geom.Point3D;
import MyFrame.Game;

public class GUI extends JFrame {
	BufferedImage backgroundImage;
	JMenuItem menuItem;
	JMenuBar menuBar;
	Color colorsArr[];	
	public static double ratioWidth;
	public static double ratioHeight;
	Dimension dimensionSize = new Dimension();
	myPanel p = new myPanel();

	public GUI() {
		try {
			backgroundImage = ImageIO.read(new File("Ariel1.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}

		startGUI();
		setVisible(true);
		setTitle("Welcome");
		setJMenuBar(menuBar);
		setSize(backgroundImage.getWidth(), backgroundImage.getHeight());
		add(p,BorderLayout.CENTER);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addComponentListener(new ComponentAdapter() { // detects window changing
			public void componentResized(ComponentEvent e) {
				dimensionSize = e.getComponent().getSize();
				ratioHeight = dimensionSize.getHeight() / backgroundImage.getHeight();
				ratioWidth = dimensionSize.getWidth() / backgroundImage.getWidth();
			}
		});

		colorsArr = new Color[13];
		colorsArr[0] = Color.red;
		colorsArr[1] = Color.blue;
		colorsArr[2] = Color.yellow;
		colorsArr[3] = Color.green;
		colorsArr[4] = Color.gray;
		colorsArr[5] = Color.ORANGE;
		colorsArr[6] = Color.pink;
		colorsArr[7] = Color.white;
		colorsArr[8] = Color.CYAN;
		colorsArr[9] = Color.MAGENTA;
		colorsArr[10] = Color.LIGHT_GRAY;
		colorsArr[11] = Color.darkGray;
		colorsArr[12] = Color.BLACK;

	}

	private void startGUI() {
		MenuBar menuBar = new MenuBar();

		// defining "Menu" menu titles
		Menu menu = new Menu("Menu");
		MenuItem item1 = new MenuItem("Load File");
		MenuItem item2 = new MenuItem("Run");
		MenuItem item3 = new MenuItem("Clear");

		// defining "insert" menu titles
		Menu menu2 = new Menu("Insert");
		MenuItem item4 = new MenuItem("Add Player");
		MenuItem item5 = new MenuItem("Relocate Player");

		menuBar.add(menu);
		menu.add(item1);
		menu.add(item2);
		menu.add(item3);

		menuBar.add(menu2);
		menu2.add(item4);
		menu2.add(item5);

		menu.setFont(new Font("Courier New", Font.ITALIC, 12));
		menu2.setFont(new Font("Courier New", Font.ITALIC, 12));

		this.setMenuBar(menuBar);

		// Load File button Listener
		item1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//loadFile(); 
				p.loadFile(GUI.this); //need to debug at the panel
				p.fileLoaded=true;

			}
		});
		// run activated
		item2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				p.startPlay(GUI.this);
			}
		});

		// Clear Method from the "Menu" menu
		item3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				p.clear();
				
			}
		});
		
		//insert player button
		item4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(p.fileLoaded==false) {
					p.popUp("Please load a file before inserting your player", "Alert");
					return;
				}
				p.addPlayer = true;
			}
		});
		//relocate player
		item5.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!p.playerExist) return ;// if the player yet be located return witour doing nothing
				p.playerExist = false;
				p.addPlayer = true;
			}
		});

	}

	public void loadFile() {
		p.loadFile(this);
	}

	public static void main(String[] args) {
		new GUI();
	}

}
