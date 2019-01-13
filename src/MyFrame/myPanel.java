package MyFrame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Graphics;
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

import javax.imageio.ImageIO;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import GIS.Box;
import GIS.Fruit;
import GIS.Ghost;
import GIS.Packman;
import GIS.Player;
import GIS.Types;
import Geom.MyCoords;
import Geom.Point3D;
import Robot.Play;

public class myPanel extends JPanel implements MouseListener {
	BufferedImage backgroundImage, packmanImage, fruitImage, ghostImage, barrierImage, playerImage;
	JMenuItem menuItem;
	JMenuBar menuBar;
	ArrayList<Types> types;
	Color colorsArr[];
	Map map = new Map();
	Game game =new Game();
	Iterator<Packman> itPac = game.packmans.iterator();
	Iterator<Fruit> itFru = game.fruits.iterator();
	Iterator<Ghost> itGhost = game.ghosts.iterator();
	Iterator<Box> itBox = game.boxes.iterator();
	Dimension dimensionSize = new Dimension();
	MyCoords mc = new MyCoords();
	Play play;
	Point3D directionPoint = new Point3D(0, 0, 0);
	boolean addPlayer, playerExist ,fileLoaded;
	int x = -1, y = -1;
	double rotationRequired = 90;
	//public static double GUI.ratioWidth=1;
	//public static  double GUI.ratioHeight=1;
	private BufferedImage myImage = null;
	
	/*
	 * CONSTRUCTOR
	 */
	public myPanel() {
		this.addMouseListener(this);

		try {
			myImage = ImageIO.read(new File("Ariel1.png"));
//			this.addComponentListener(new ComponentAdapter() { // detects window changing
//				public void componentResized(ComponentEvent e) {
//					dimensionSize = e.getComponent().getSize();
//					GUI.ratioHeight = dimensionSize.getHeight() / myImage.getHeight();
//					GUI.ratioWidth = dimensionSize.getWidth() / myImage.getWidth();
//					System.out.println(GUI.ratioHeight + " | "+GUI.ratioWidth);
//				}
//			});		
			//GUI.ratioHeight=GUI.GUI.ratioHeight;
		//	GUI.ratioWidth=GUI.GUI.ratioWidth;
			
		} catch (IOException e) {
			System.out.println(e);
		}
	}
	/*
	 * (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 * paint method
	 */
	public void paintComponent(Graphics g) {
	//	GUI.ratioHeight=GUI.GUI.ratioHeight;
	//	GUI.ratioWidth=GUI.GUI.ratioWidth;
		super.paintComponent(g);
		g.drawImage(myImage, 0, 0, this.getWidth(), this.getHeight(), this);
		if (x != -1 && y != -1) {
			int r = 20;
			
			// print boxes
			synchronized (game) {

				itBox = game.boxes.iterator();
				while (itBox.hasNext()) {
					Box box = itBox.next();
					int deltaX = (int) (box.getMaxInPixels().x() - box.getMinInPixels().x());
					int deltaY = (int) (box.getMinInPixels().y() - box.getMaxInPixels().y());

					g.fillRect((int) (box.getMinInPixels().x() * GUI.ratioWidth),
							(int) (box.getMaxInPixels().y() * GUI.ratioHeight+10), (int) (deltaX * GUI.ratioWidth+20),
							(int) (deltaY * GUI.ratioHeight));
				}
				if (addPlayer && !playerExist) { // if the addPlayer has been pressed&&the player isnt in the map yet
					Point3D point = map.pixels2polar((int)(x*GUI.ratioWidth), (int)(y*GUI.ratioHeight));
					game.player.setPoint(point);
					g.drawImage(Player.playerImage, (int) (game.player.getLocationInPixels().x()),
							(int) (game.player.getLocationInPixels().y()), (int) (2 * r ),
							(int) (2 * r), this);
					playerExist = true;
				}

				// if the player already exists we want to draw him again
				if (playerExist) {
					//System.out.println(rotationRequired); // calculate angle in degrees
					AffineTransform tx = AffineTransform.getRotateInstance(Math.toRadians(rotationRequired),
							Player.playerImage.getWidth()*GUI.ratioWidth/ 2, Player.playerImage.getHeight()*GUI.ratioHeight / 2); // set up image rotation configuration
					AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
					// Drawing the rotated image at the required drawing locations
					g.drawImage((op.filter(Player.playerImage, null)), (int) (game.player.getLocationInPixels().x()),
							(int) (game.player.getLocationInPixels().y() ), (int) (2 * r ),
							(int) (2 * r * GUI.ratioHeight), this);

				}

				// print fruits
				itFru = game.fruits.iterator();
				while (itFru.hasNext()) {
					Fruit fTemp = itFru.next();
					g.drawImage(Fruit.fruitImage, (int) (fTemp.getLocationInPixels().x() * GUI.ratioWidth),
							(int) (fTemp.getLocationInPixels().y() * GUI.ratioHeight), (int) (2 * r * GUI.ratioWidth),
							(int) (2 * r * GUI.ratioHeight), this);
				}
				// print packmans
				itPac = game.packmans.iterator(); // for the repaint we need to draw every packman again
				while (itPac.hasNext()) {
					Packman pTemp = itPac.next();
					g.drawImage(Packman.packmanImage, (int) (pTemp.getLocationInPixels().x() * GUI.ratioWidth),
							(int) (pTemp.getLocationInPixels().y() * GUI.ratioHeight), (int) (2 * r * GUI.ratioWidth),
							(int) (2 * r * GUI.ratioHeight), this);
				}
				// print ghost
				itGhost = game.ghosts.iterator();
				while (itGhost.hasNext()) {
					Ghost pGhost = itGhost.next();
					g.drawImage(Ghost.ghostImage, (int) (pGhost.getLocationInPixels().x() * GUI.ratioWidth),
							(int) (pGhost.getLocationInPixels().y() * GUI.ratioHeight), (int) (2 * r * GUI.ratioWidth),
							(int) (2 * r * GUI.ratioHeight), this);
				}
			}

		}
	}

	public double orientation(Point3D current, Point3D next) {
		return mc.azimuth_elevation_dist(current, next)[0];
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println("(" + e.getX() + "," + e.getY() + ")");
		x = (int)(e.getX()/GUI.ratioWidth);
		y = (int)(e.getY()/GUI.ratioHeight);
//		
//		System.out.println("Pixel:" + x+","+y);		
//		Point3D latLon = Map.pixels2polar(x, y);
//		System.out.println("LatLon:" + latLon.y()+","+latLon.x());
//		Point3D pixel = Map.polar2pixels(new Point3D(latLon.x(), latLon.y()));
//		System.out.println("Pixel back:" + pixel.x()+","+pixel.y());
//
//		
		
		if (playerExist) 
		{
			directionPoint = new Point3D(x, y, 0);
			rotationRequired = 360
					- (orientation(game.player.getPoint(), Map.pixels2polar(directionPoint.ix(), directionPoint.iy())));
			System.out.println(rotationRequired);
		}
		repaint();

	}
	public void clear() {
		x = -1;
		y = -1;
		addPlayer = false;
		playerExist = false;
		fileLoaded=false;
		game.packmans.clear(); // not sure what to fill inside the
		game.fruits.clear();
		game.boxes.clear();
		game.ghosts.clear();
		repaint();
	}


	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	
	/*
	 * loading the file
	 * @return the string of the csv file
	 * @param geting the gui
	 */
	public String loadFile(GUI gui) {
		// try read from the file (Copied code from Elizabeth )
		FileDialog fd = new FileDialog(gui, "Open text file", FileDialog.LOAD);
		fd.setFile("*.csv");
		fd.setDirectory("C:");
		fd.setFilenameFilter(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".csv");
			}
		});
		fd.setVisible(true);
		String folder = fd.getDirectory();
		String fileName = fd.getFile();
		if (fileName != null) {
			System.out.println("The file that opened is: " + folder + fileName);
			play = new Play(folder + fileName);
			game.buildAgame(play.getBoard());
			x = 1;
			y = 1;
			this.repaint();

		}
		return fileName;
	}
	
	//pop up message for the game results
	//credit for https://stackoverflow.com/questions/7080205/popup-message-boxes
	/*
	 * @param info and title 
	 * show the message
	 */
	public void popUp(String infoMessage, String titleBar)
	{
		JOptionPane.showMessageDialog(null, infoMessage, titleBar, JOptionPane.INFORMATION_MESSAGE);
	}

	/*
	 * @param setting GUI 
	 * RUN GAME
	 */
	public void startPlay(GUI gui) {
		if (playerExist) {
			new myThread(this).start();
		}
	}
	/*
	 * @param setting GUI run activated
	 */
	public void startSimu(GUI gui) {
		if (playerExist) {
			new MyThreadSimu(this).start();
		}
	}

}
