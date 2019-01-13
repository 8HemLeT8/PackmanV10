package GIS;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Geom.Point3D;
import MyFrame.Map;

public class Ghost implements Types{
private	Point3D location;
private	double speed;
private	int id;
private	double radius;
private Map map =new Map();
public static BufferedImage ghostImage;
	/*
	 * @return speed
	 */
	public double getSpeed() {
		return speed;
	}
	/*
	 * set speed
	 * @param speed param
	 */
	public void setSpeed(double speed) {
		this.speed = speed;
	}
	/*
	 * @return return id
	 */
	public int getId() {
		return id;
	}
	/*
	 * setting id
	 */
	public void setId(int id) {
		this.id = id;
	}
	/*
	 * @return return radius
	 */
	public double getRadius() {
		return radius;
	}
	/*
	 * setting the radius
	 */
	public void setRadius(double radius) {
		this.radius = radius;
	}
	/*
	 * set location
	 */
	public void setLocation(Point3D location) {
		this.location = location;
	}
	/*
	 * CONSTRUCTOR
	 */
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
		
		try{ghostImage = ImageIO.read(new File("Ghost.jpg"));}
		catch(IOException e) {System.out.println(e);}
	}	
	/*
	 * @return location in pixels
	 */
	public Point3D getLocationInPixels() {
		return map.polar2pixels(location);
	}

	@Override
	public Point3D getLocation() {
		// TODO Auto-generated method stub
		return null;
	}




}
