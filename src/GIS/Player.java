package GIS;

import Geom.Point3D;
import MyFrame.Map;

public class Player implements Types{
	
	Point3D point;
	int speed;
	double radius;
	private Map map =new Map();

	public Point3D getLocationInPixels() {
		return map.polar2pixels(point);
	}
	public Point3D getPoint() {
		return point;
	}
	public void setPoint(Point3D point) {
		this.point = point;
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public double getRadius() {
		return radius;
	}
	public void setRadius(double radius) {
		this.radius = radius;
	}
	@Override
	public Point3D getLocation() {
		// TODO Auto-generated method stub
		return null;
	}
	

}
