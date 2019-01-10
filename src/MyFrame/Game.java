package MyFrame;
import File_format.*;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import GIS.*;
import java.util.Iterator;

import javax.imageio.ImageIO;
public class Game {
	public ArrayList<Fruit> fruits =new ArrayList();
	public ArrayList<Packman> packmans =new ArrayList();
	public ArrayList<Box> boxes =new ArrayList();
	public ArrayList<Ghost> ghosts =new ArrayList();
	public Player player = new Player();
	String path="game_1543684662657.csv";

/*
 * @param String filePath getting a file from the user 
 * (not with "insert" at the manu bar list)
 * , analaize it to fruits and packman with the reader and the type maker  	
 */
public void buildAgame(ArrayList<String> table) {
	ArrayList<Types> types = new ArrayList<Types>();
	types=List2types.makeTypes(table);
	for(Types t : types) {
		if(t instanceof Fruit) {
			fruits.add((Fruit) t);
		}
		else if(t instanceof Packman) {
			packmans.add((Packman) t);
		}
		else if(t instanceof Ghost) {
			ghosts.add((Ghost) t);
		}
		else if(t instanceof Box) {
			boxes.add((Box) t);
		}
		else if(t instanceof Player) {
			player = (Player)t;
		}
	}
}

}


