package GIS;

import Geom.Point3D;
import MyFrame.Map;

public class Box implements Types{
	Point3D point1;
	Point3D point2;
	private Map map =new Map();

	
	public Box(String ID ,String Lat1 ,String Lon1, String Lat2 ,String Lon2) {
		double Lat=Double.parseDouble(Lat1);
		double Lon=Double.parseDouble(Lon1);
		double Lat22=Double.parseDouble(Lat2);
		double Lon22=Double.parseDouble(Lon2);
		this.point1=new Point3D(Lat,Lon,0);
		this.point2 = new Point3D(Lat22,Lon22,0);

	}

	public Point3D getPoint1() {
		return point1;
	}
	

	public void setPoint1(Point3D point1) {
		this.point1 = point1;
	}


	public Point3D getPoint2() {
		return point2;
	}

	public void setPoint2(Point3D point2) {
		this.point2 = point2;
	}
	public Point3D getLocationInPixelsMin() {
		return map.polar2pixels(point1);
	}
	public Point3D getLocationInPixelsMax() {
		return map.polar2pixels(point2);
	}

	@Override
	public Point3D getLocation() {
		// TODO Auto-generated method stub
		return null;
	}

}
