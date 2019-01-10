package GIS;

import Geom.Point3D;
import MyFrame.Map;

public class Box implements Types{
	Point3D minPoint;
	Point3D maxPoint;
	private Map map =new Map();

	/*
	 * @param getting data for the constructor
	 */
	public Box(String ID ,String Lat1 ,String Lon1, String Lat2 ,String Lon2) {
		double Lat=Double.parseDouble(Lat1);
		double Lon=Double.parseDouble(Lon1);
		double Lat22=Double.parseDouble(Lat2);
		double Lon22=Double.parseDouble(Lon2);
		this.minPoint=new Point3D(Lat,Lon,0);
		this.maxPoint = new Point3D(Lat22,Lon22,0);

	}
	
	/*
	 * @return return the point
	 */
	public Point3D getminPoint() {
		return minPoint;
	}
	
	/*
	 * setting the point
	 */
	public void setminPoint(Point3D minPoint) {
		this.minPoint = minPoint;
	}

	/*
	 * @return return get max point
	 */
	public Point3D getmaxPoint() {
		return maxPoint;
	}
	/*
	 * set max point
	 */
	public void setmaxPoint(Point3D maxPoint) {
		this.maxPoint = maxPoint;
	}
	/*
	 * @return return min point in pixel
	 */
	public Point3D getMinInPixels() {
		return map.polar2pixels(minPoint);
	}
	/*
	 * @return return max in pixels
	 */
	public Point3D getMaxInPixels() {
		return map.polar2pixels(maxPoint);
	}

	@Override
	public Point3D getLocation() {
		// TODO Auto-generated method stub
		return null;
	}

}
