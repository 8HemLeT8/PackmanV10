package Algo;

import java.util.ArrayList;

import GIS.Box;
import Geom.Point3D;
import MyFrame.Map;

public class Intersection {
	static int x1;
	static int x2;
	static int y1;
	static int y2;
	static Map map = new Map();

	public static boolean isIntersect(Point3D start , Point3D target,ArrayList<Box> box ) {
		boolean flag = false;
		for(Box kufsa : box) {
			x1=(int) kufsa.getMinInPixels().x();
			y1=(int) kufsa.getMinInPixels().y();
			x2=(int) kufsa.getMaxInPixels().x();
			y2=(int) kufsa.getMaxInPixels().y();
			
			int Check= xInWay(start,target,x1);
			if(Check<=y2&&Check>=y1) { flag = true; break;}
			
			Check= xInWay(start,target,x2);
			if(Check<=y2&&Check>=y1) { flag = true; break;}
			
			Check= yInWay(start,target,y1);
			if(Check<=x2&&Check>=x1) { flag = true; break;}
			
			Check= yInWay(start,target,y2);
			if(Check<=x2&&Check>=x1) { flag = true; break;}

		}
		return flag;
	}
	private static int xInWay(Point3D start , Point3D target, int xValue) {
		Point3D newStart=map.polar2pixels(start);
		Point3D newTarget=map.polar2pixels(target);
		double m= (newTarget.y()-newStart.y())/(newTarget.x()-newStart.x());
		double n= (newStart.y()-m*newStart.x());
		return (int) (m*xValue+n);
	}

	private static int yInWay(Point3D start , Point3D target, int yValue) {
		Point3D newStart=map.polar2pixels(start);
		Point3D newTarget=map.polar2pixels(target);
		double m= (newTarget.y()-newStart.y())/(newTarget.x()-newStart.x());
		double n= (newStart.y()-m*newStart.x());
		return (int) ((yValue-n)/m);
	}
	
	/*
	public static boolean isIntersect(Point3D start,Point3D target ,ArrayList<Box> box) {
		//we need a line eqution
		Point3D newStart=map.polar2pixels((int)start.x(),(int)start.y());
		Point3D newTarget=map.polar2pixels((int)target.x(),(int)target.y());
		
		double slope = (newTarget.y()-newStart.y()/(newTarget.x()-newStart.x()));

		return false;
	}
	private double findY(int slope) {
		
		
		return 0;
	}
	*/
	public static void main (String [] arg) {
		//point , start and target
		int x1 = 0;
		int y1=0;
		int x2=4;
		int y2=4;
		Point3D start = new Point3D(x1,y1,0);
		Point3D target = new Point3D(x2,y2,0);
		start=map.pixels2polar(x1, y1);
		target=map.pixels2polar(x2, y2);

		
		//box lines
		int x1Box=2;
		int x2Box=4;
		int y1Box=0;
		int y2Box=2;
		
		
		//
		boolean flag = false;
		int Check= xInWay(start,target,x1Box);
		if(Check<=y2Box&&Check>=y1Box) { flag= true;}
		
		Check= xInWay(start,target,x2Box);
		if(Check<=y2Box&&Check>=y1Box) { flag = true;}
		
		Check= yInWay(start,target,y1Box);
		if(Check<=x2Box&&Check>=x1Box) { flag = true;}
		
		Check= yInWay(start,target,y2Box);
		if(Check<=x2Box&&Check>=x1Box) { flag = true;}
		
		System.out.println(flag);
		
		
	}
	
}
