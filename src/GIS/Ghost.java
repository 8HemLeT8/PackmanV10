package GIS;
import Geom.Point3D;
import MyFrame.Map;

public class Ghost implements Types{
private	Point3D location;
private	double speed;
private	int id;
private	double radius;
private Map map =new Map();


	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	public void setLocation(Point3D location) {
		this.location = location;
	}

	public Ghost(String id,String Lat1 ,String Lon1 ,String Alt1, String Speed1, String radius) {
		double Lat=Double.parseDouble(Lat1);
		double Lon=Double.parseDouble(Lon1);
		double Alt=Double.parseDouble(Alt1);
		double Speed=Double.parseDouble(Speed1);
		double radius1=Double.parseDouble(radius);
		int idi = Integer.parseInt(id);
		this.id=idi;
		this.location = new Point3D(Lat,Lon,Alt);
		this.speed=Speed;
		this.radius=radius1;
	}	
	public Point3D getLocationInPixels() {
		return map.polar2pixels(location);
	}

	@Override
	public Point3D getLocation() {
		// TODO Auto-generated method stub
		return null;
	}




}
