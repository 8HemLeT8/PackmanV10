package MyFrame;

import java.util.ArrayList;					//~~~~~~~~~~COMPARE ALL POINTS  ~~~~~~~~~~~~
import java.util.Iterator;					//~~~~~~~~~~TO SEE IF THE 	    ~~~~~~~~~~~~
//~~~~~~~~~~COORDINATES SUPPOSE ~~~~~~~~~~~~
import Geom.MyCoords;						//~~~~~~~~~~TO BE POLAR	        ~~~~~~~~~~~~
import Geom.Point3D;						//~~~~~~~~~~OR PIXEL		    ~~~~~~~~~~~~

public class MyThreadSimu extends Thread{
	myPanel panel;
	ArrayList<String> myGame = new ArrayList(); //holding current game info
	ArrayList<Point3D> path = new ArrayList();
	MyCoords myCoords = new MyCoords();
	Point3D directionPoint;
	double rotationRequired;

	public MyThreadSimu(myPanel panel) {
		this.panel=panel;
	}
	public void run() {

		//return if there is no player,fruits or the play object isn't initilaized
		if(panel.game.fruits.size()==0 || panel.playerExist==false || panel.play==null) return ; 
		
		
		//~~~~ FIXED things - speed ,first init location , ID , start Boaz's server ~~~~
		
		panel.play.setIDs(1911,1502); //setting our id and deliever it to the server
		Point3D playerPosition = panel.game.player.getPoint();
		panel.play.setInitLocation(playerPosition.y()*GUI.ratioHeight,playerPosition.x()*GUI.ratioWidth);
		panel.play.start(); // default max time is 100 seconds (1000*100 ms).
		double speed = panel.game.player.getSpeed();  //packman's speed
		
		//~~~~ FIXED things - speed ,first init location , ID , start Boaz's server ~~~~

		
		
		// @@@@@@ SIMULATION STARTS @@@@@@@
		while(panel.game.fruits.size()>0 && panel.play.isRuning()) {
			
			Point3D currentSrc = new Point3D(panel.game.player.getLocationInPixels().x()  //maybe swap is needed
					,panel.game.player.getLocationInPixels().y()); // between x&y .src point to send to the path
			
			//setting shortest path to a fruit
			path=Algo.PathMaker.graphMake(panel.game.boxes,panel.game.fruits,currentSrc); //
			if(path.size()<1) return; 			//if the path contains only the src himself return we've finished
			
			//setting point for the interval 
			Iterator<Point3D> startIter=path.iterator(); 
			Iterator<Point3D> endIter= path.iterator();	
			Point3D startPoint;
			Point3D endPoint=endIter.next();

			//loop representing the path of intervals for singal
			while(endIter.hasNext()) { 
				
				//setting the points //since they came from the algo assumin they are 
				//in pixel form already *(need to be checked)*
				startPoint =startIter.next();
				endPoint=endIter.next(); 
				
				//******caculation for the num of steps in a singal interval*********
				double deltaPixel_X=endPoint.x()-startPoint.x();	//
				double deltaPixel_Y=endPoint.y()-startPoint.y();	 // 			
				double distanceP= Math.sqrt(Math.pow(deltaPixel_X,2)+Math.pow(deltaPixel_Y,2)); //distance of the interval in pixels
				int steps =(int)(Math.round(distanceP/speed));
				

				//~~~movement in a specific interval~~~ // maybe should be replaced in a while till we hit the 
				//required point in an Approximation of defined radius
				for(int i =0; i <steps*speed ;i++) {
					panel.play.rotate((getAngle(endPoint)+90)); //passing the "player" angle and make the server do one step
					myGame=panel.play.getBoard();           //getting the stats from the board for the next step
					synchronized (panel.game) {		
						gameReset();
						panel.game.buildAgame(panel.play.getBoard()); //building new game object for the new step
					}
					panel.repaint();    //updating the linked panel
					try {
						Thread.sleep(65);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				//~~~~set panel.game.player new location for the last point in the path and remove the fruit~~~~
				//maybe the server does it for us
				
			}//FIRST "WHILE" END (End of path for the closest fruit)
			
		}//SECOND "WHILE" END (NO MORE FRUITS || TIME IS OVER)
		
	}
	

	private double getAngle(Point3D target) {
		directionPoint = new Point3D(target.x(), target.y(),0); // ~~~~need to be point in pixels~~~~
		rotationRequired = 360
				- (panel.orientation(panel.game.player.getPoint(), Map.pixels2polar(directionPoint.ix(), directionPoint.iy())));
		System.out.println(rotationRequired);
		return rotationRequired;
	}
	//reseting the game for the next step
	private void gameReset() {
		panel.game.boxes.clear();
		panel.game.fruits.clear();
		panel.game.ghosts.clear();
		panel.game.packmans.clear();	
	}


}
