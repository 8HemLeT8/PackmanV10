package File_format;

import java.util.ArrayList;
import java.util.Iterator;
import Geom.*;
import MyFrame.*;
import GIS.*;

public class List2types {
	
	/*
	 * @param ArrayList<ArrayList<String>> table which contain what we read from the csv file
	 * @return retun Arraylist containing fruits and packman together
	 */
	public static ArrayList<Types> makeTypes(ArrayList<String> table) {
		ArrayList<Types> types=new ArrayList<Types>();
		Iterator<String> it = table.iterator();
		//it.next();
		while (it.hasNext()) {
			String k = new String(it.next());
			String[] p = k.split(",");
			if(p[0].equals("F")) {
				Fruit tut = new Fruit(p[3],p[2],p[4],p[5]);
				types.add(tut);
			}
			else if(p[0].equals("P")) {
				Packman hamudi = new Packman(p[1],p[3],p[2],p[4],p[5],p[6]);
				types.add(hamudi);
			}
			else if(p[0].equals("G")) {
				Ghost mafhid = new Ghost(p[1],p[3],p[2],p[4],p[5],p[6]);
				types.add(mafhid);
			}
			else if(p[0].equals("B")) {
				Box black = new Box(p[1],p[3],p[2],p[6],p[5]);
				types.add(black);
			} else if(p[0].equals("M")) {
				Player player = new Player();
				player.setPoint(new Point3D(Double.parseDouble(p[3]), Double.parseDouble(p[2]),0));
				types.add(player);
			}
			
			
			
			
			else {
				System.out.println("you are wrong smh");
			}
			
		}
		return types;
	}

}
