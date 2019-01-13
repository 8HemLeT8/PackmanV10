package GIS;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.imageio.ImageIO;

import GIS.*;
import MyFrame.Map;
import Geom.Point3D;

public class Fruit implements Types{
	private Point3D p;
	private double weight;
	private Map N = new Map();
	public static BufferedImage fruitImage=null;
	/*
	 * constructor for fruit for given doubles
	 */
	public Fruit(double Lat, double Lon, double Alt ,double weight) {
		this.p=new Point3D(Lat,Lon,Alt);
		this.weight=weight;
	}
	/*
	 * constructor for fruit based on String parameters
	 */
	public Fruit(String Lat1 ,String Lon1 ,String Alt1, String weight1) {
		double Lat=Double.parseDouble(Lat1);
		double Lon=Double.parseDouble(Lon1);
		double Alt=Double.parseDouble(Alt1);
		double weight=Double.parseDouble(weight1);
		this.p=new Point3D(Lat,Lon,Alt);
		this.weight=weight;
		try{fruitImage = ImageIO.read(new File("fruit1.png"));}
		catch(IOException e) {System.out.println(e);}
	}
	/*
	 * @return return the weight
	 */
	public double getWeight() {
		return this.weight;
	}
	/*
	 * set weight
	 */
	public void setWeight(double x) {
		this.weight=x;
	}
	/*
	 * constructor for p for given point
	 */
	public Fruit(Point3D p) {
		this.p=p;
	}
	/*
	 * construtor for the fruit in pixel
	 */
	public Fruit(int PixelsX,int PixelsY) {
		this.p=N.pixels2polar(PixelsX,PixelsY);
		this.weight=1;
		try{fruitImage = ImageIO.read(new File("fruit1.png"));}
		catch(IOException e) {System.out.println(e);}
	}
	/*
	 * (non-Javadoc)
	 * @see GIS.Types#getLocation()
	 * @return return the location of the fruit
	 */
	public Point3D getLocation() {
		return p;
	}
	/*
	 * @return return the location in pixels
	 */
	public Point3D getLocationInPixels() {
		return N.polar2pixels(p);
	}
	/*
	 * set point for the fruit
	 */
	public void setP(Point3D p) {
		this.p = p;
	}
	
	

}