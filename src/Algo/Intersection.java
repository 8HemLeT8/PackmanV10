package Algo;

import java.awt.Point;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;

import GIS.Box;
import Geom.Point3D;
import MyFrame.Map;

public class Intersection {
	static int x1;
	static int x2;
	static int y1;
	static int y2;
	static Map map = new Map();

	/*
	 * @return retun if the point is contains in the boxes
	 */
	public static boolean isContains(Point3D point, Box box) {
		Point p2d = new Point(point.ix(), point.iy());
		
		Rectangle2D rect = new Rectangle2D.Double();
		Point3D p1 = map.polar2pixels(box.getminPoint());
		Point3D p2 = map.polar2pixels(box.getmaxPoint());
		rect.setRect(p1.x(), p2.y(), Math.abs(p2.x()-p1.x()), Math.abs(p2.y()-p1.y()));
		
		return rect.contains(p2d);

	}
	/*
	 * @return retun if the point is contains in the boxes
	 * @param getting the point and the arraylist of boxes
	 */
	public static boolean isContains(Point3D point, ArrayList<Box> box) {

		Iterator<Box> iter = box.iterator();
		while(iter.hasNext()) {
			Box b = iter.next();
			if(isContains(point, b))
				return true;
		}

		return false;
	}


	/*
	 * @return true or false if the line intesect
	 * @param gettign start point adn nd point for the line and a box
	 */
	public static boolean isIntersect2(Point3D start,Point3D target , Box box) {
		Line2D line = new Line2D.Double(start.x(), start.y(), target.x(), target.y());
		Rectangle2D rect = new Rectangle2D.Double();
		Point3D p1 = map.polar2pixels(box.getminPoint());
		Point3D p2 = map.polar2pixels(box.getmaxPoint());

		rect.setRect(p1.x(), p2.y(), Math.abs(p2.x()-p1.x()), Math.abs(p2.y()-p1.y()));

		
		boolean intersect = line.intersects(rect);
		//if(intersect)
		//	System.out.println("DEBUG: "+ start+", "+target + " | " + rect);
		return intersect;
	}
	
	/*
	 * @return true or false if the line intesect
	 * @param gettign start point adn nd point for the line and a box
	 */
	public static boolean isIntersectBoxes(Point3D start, Point3D target, ArrayList<Box> box) {

		Iterator<Box> iter = box.iterator();
		while(iter.hasNext()) {
			Box b = iter.next();
			if(isIntersect2(start, target, b))
				return true;
		}

		return false;
	}

	
	



}
