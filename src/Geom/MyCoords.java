package Geom;

public class MyCoords implements coords_converter{
	private double r =6371000; 

	
	/** computes the 3D distance (in meters) between the two gps like points
	 * @return distance between two gps points
	 * @param gps0 first gps point
	 * @param gps1 second gps point
	 *  */
	public double distance3d(Point3D gps0, Point3D gps1) {
		int earthR = 6371*1000;
		double pi=Math.PI;

		double latDistance = gps1.x() - gps0.x();
		double lonDistance = gps1.y() - gps0.y();
		double height =gps1.z()-gps0.z();

		double latRadian =(pi*latDistance)/180;
		double lonRadian = (pi*lonDistance)/180;

		double latConvertMeters= Math.sin(latRadian)*earthR;
		double lonConvertMeters=Math.sin(lonRadian)*earthR*Math.cos(gps1.x()*pi/180);

		return Math.sqrt(latConvertMeters*latConvertMeters+lonConvertMeters*lonConvertMeters);

	}


	/** computes the 3D vector (in meters) between two gps like points
	 * @param gps0 first gps point
	 * @param gps1 second gps point
	 * @return 3D vector (in meters) between two gps like points
	 *  */
	public Point3D vector3D(Point3D gps0, Point3D gps1) {
		double normal = gps0.normal();
		double x = sin(Math.toRadians(gps1.x()-gps0.x()))*r;
		double y = sin(Math.toRadians(gps1.y()-gps0.y()))*r*normal;
		return new Point3D(x,y,gps1.z()-gps0.z());
	}
	/** computes the polar representation of the 3D vector be gps0-->gps1 
	 * Note: this method should return an azimuth (aka yaw), elevation (pitch), and distance
	 * @param gps0 first gps point
	 * @param gps1 second gps point
	 * @return polar representation of the 3D vector be gps0-->gps1 
	 * */
	
	/*
	public double[] azimuth_elevation_dist(Point3D gps0, Point3D gps1) {

		//distance
		double distance=distance3d(gps0,gps1);

		//azimuth
		double deltaY=Math.toRadians(gps1.y()-gps0.y());
		double gps0XtoR=Math.toRadians(gps0.x());
		double gps1XtoR=Math.toRadians(gps1.x());	
		double azimuth = Math.atan2(sin(deltaY)*cos(gps1XtoR),
				cos(gps0XtoR)*sin(gps1XtoR)-sin(gps0XtoR)*cos(gps1XtoR)*cos(deltaY));
		azimuth=Math.toDegrees(azimuth);
		
		while(azimuth<0||azimuth>360) {
		if(azimuth<0) {
			azimuth+=360;
		}else if(azimuth>360) {
			azimuth-=360;
		}
		}
		//elevation		
		double elevation =Math.toDegrees(Math.asin((gps1.z()-gps0.z())/distance));

		double b [] = {azimuth,elevation,distance};

		return b;

		

	}
*/
	public double[] azimuth_elevation_dist(Point3D gps0, Point3D gps1){
		double[] arr = new double[3];

		/*
		 * deltaLamba =  longitude(gps1) - longitude(gps0) in radians
		 * phi1 = latitude(gps0) in radians
		 * phi2 = latitude(gps1) in radians
		 * alphaRad = the Azimuth angle in radians
		 * alphaDeg = the Azimuth angle in degrees
		 */
		double deltaLamda,phi1,phi2,alphaRad,alphaDeg;
		deltaLamda = Math.toRadians(gps1.y()) - Math.toRadians(gps0.y());
		phi1 = Math.toRadians(gps0.x());
		phi2 = Math.toRadians(gps1.x());

		double x = Math.sin(deltaLamda)*Math.cos(phi2);//Split for checking
		double y = (Math.cos(phi1)*Math.sin(phi2))-(Math.sin(phi1)*Math.cos(phi2)*Math.cos(deltaLamda));//split for checking
		alphaRad = Math.atan2(x,y);
		alphaDeg = Math.toDegrees(alphaRad);
		if (alphaDeg<0)//just so it will give positive degree
			alphaDeg = 360+alphaDeg;

		arr[0] = alphaDeg;
		double A = (gps1.z()-gps0.z());
		double C = distance3d(gps0,gps1);
		double B = Math.sqrt(C*C-A*A);
		arr[1] = Math.toDegrees(Math.atan(A/B));
		arr[2] = distance3d(gps0, gps1);
		//System.out.println("Tan-1: "+ Math.toDegrees(Math.atan(A/B))+" | Sin-1: " + Math.toDegrees(Math.asin(A/C)));
		//System.out.println("angle:" +Math.toDegrees(p2m(gps0).angleZ(p2m(gps1))));




		return arr;
	}


	/**
	 * return true iff this point is a valid lat, lon , lat coordinate: [-180,+180],[-90,+90],[-450, +inf]
	 * @param p
	 * @return true iff this point is a valid lat, lon , lat coordinate
	 */
	@Override
	public boolean isValid_GPS_Point(Point3D p) {
		if(p.x()<-90 || p.x()> 90 || 
				p.y()<-180 || p.y()> 180|| 
					p.z()<-450) return false; 

		return true;
	}

	//returns new point in radian (assuming gps is given in deg)
	/**
	 * @param gps
	 * @param local_vector_in_meter
	 * @return point in radian (assuming gps is given in deg)
	 */
	@Override
	public Point3D add(Point3D gps, Point3D local_vector_in_meter) {
		double x = Math.toDegrees(Math.asin(local_vector_in_meter.x()/r))+gps.x();
		double y=Math.toDegrees(Math.asin(local_vector_in_meter.y()/r*gps.normal()))+gps.y();
		double z =gps.z()+local_vector_in_meter.z();
		
		return new Point3D(x,y,z);

	}
	/**
	 * private function to ease calculation
	 * @param x
	 * @return cos of x
	 */
	private double cos(double x) {
		return Math.cos(x);
	}
	/**
	 * private function to ease calculation
	 * @param y
	 * @return return sin of y
	 */
	private double sin(double y) {
		return Math.sin(y);
	}
	
	
	
}
