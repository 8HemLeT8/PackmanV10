package MyFrame;

import GIS.*;
import Geom.Point3D;

public class Map {
	static double rangeXpixels=1418;
	static double rangeYpixels=-584;
	static double rangeXpolar=0.0100;
	static double rangeYpolar=0.0038;
	static double PolarX0=35.2023;
	static double PolarY0=32.1057;
	 
	 /*
	  *@return return the rangeXpixles field 
	  */
	 public double getRangeXpixels() {
		return rangeXpixels;
	}
	 /*
	  * @param setting the range in pixels for the given value rangeYpixels
	  */
	public void setRangeXpixels(double rangeXpixels) {
		this.rangeXpixels = rangeXpixels;
	}
	/*
	 * @return getRangeYpixles
	 */
	public double getRangeYpixels() {
		return rangeYpixels;
	}
	/*
	 * @param setting the range in pixels for the given value rangeYpixels
	 */
	public void setRangeYpixels(double rangeYpixels) {
		this.rangeYpixels = rangeYpixels;
	}
	/*
	 * @return return the x value as polar
	 */
	public double getRangeXpolar() {
		return rangeXpolar;
	}
	/*
	 * @param double rangeXpolar value for the field
	 *set x polar value 
	 */
	public void setRangeXpolar(double rangeXpolar) {
		this.rangeXpolar = rangeXpolar;
	}
	/*
	 * @return return the value of polar y
	 */
	public double getRangeYpolar() {
		return rangeYpolar;
	}
	/*
	 * SET this polar value
	 */
	public void setRangeYpolar(double rangeYpolar) {
		this.rangeYpolar = rangeYpolar;
	}

	
	 /*
	  * @return a point which converted to polar
	  * @param int x,int y coodinates of the given point in pixels
	  */
	public static Point3D pixels2polar(int x,int y) {
		double newX = ((rangeXpolar*(x/rangeXpixels)+PolarX0));
		double newY = ((rangeYpolar*(y/rangeYpixels)+PolarY0));
		return new Point3D(newX,newY,0);
	}
	/*
	 * @return a point which converted to pixels
	 * @param int x,int y coodinates of the given point in polar
	 */
	public static Point3D polar2pixels(Point3D temp) {
		double newX = Math.round(((temp.x()-PolarX0)/rangeXpolar)*rangeXpixels);
		double newY = Math.round(((temp.y()-PolarY0)/rangeYpolar)*rangeYpixels);
		return new Point3D(newX,newY,0);
	}

	

}
