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
	ArrayList<Point3D> bestPath = new ArrayList();
	MyCoords myCoords = new MyCoords();
	Point3D directionPoint;
	double rotationRequired;
	
	/*
	 * CONSTRUCTOR
	 * @param setting GUI
	 */
	public MyThreadSimu(myPanel panel) {
		this.panel=panel;
	}
	/*
	 * java run method
	 * (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	public void run() 
	{

		//return if there is no player,fruits or the play object isn't initilaized
		if(panel.game.fruits.size()==0 || panel.playerExist==false || panel.play==null) return ; 


		//~~~~ FIXED things - speed ,first init location , ID , start Boaz's server ~~~~

		panel.play.setIDs(1911,1502); //setting our id and deliever it to the server
		Point3D playerPosition = panel.game.player.getPoint();
		panel.play.setInitLocation(playerPosition.y(),playerPosition.x());
		panel.play.start(); // default max time is 100 seconds (1000*100 ms).
		double speed = panel.game.player.getSpeed();  //packman's speed

		//~~~~ FIXED things - speed ,first init location , ID , start Boaz's server ~~~~



		// @@@@@@ SIMULATION STARTS @@@@@@@
		while(panel.play.isRuning()) 
		{
			System.out.println("in While");
			Point3D currentSrc = new Point3D(panel.game.player.getLocationInPixels().x()  //maybe swap is needed
					,panel.game.player.getLocationInPixels().y()); // between x&y .src point to send to the path

			//setting shortest path to a fruit
			path=Algo.PathMaker.graphMake(panel.game.boxes,panel.game.fruits,currentSrc); //
			if(path.size()<2) break; 			//if the path contains only the src himself return we've finished
			System.out.println("~~~~~~~~~~~~~~ DEBUG  ~~~~~~~~~~~~~~~~~" );
			System.out.println(path);
			//System.out.println(path);
			//setting point for the interval 
			bestPath= new ArrayList();
			for(int i = 0;i<path.size();i++) {
				bestPath.add(new Point3D(path.get(i)));
			}

			Iterator<Point3D> startIter=bestPath.iterator(); 
			Iterator<Point3D> endIter= bestPath.iterator();	
			Point3D startPoint=startIter.next();
			Point3D endPoint=endIter.next();
			endPoint=endIter.next();

			//loop representing the path of intervals for singal fruit

			//setting the points //since they came from the algo assumin they are 
			//in pixel form already *(need to be checked)*
			if(startPoint.distance2D(endPoint)<2) {
				startPoint =startIter.next();
				endPoint=endIter.next(); 
			}
			System.out.println(startPoint);
			System.out.println(endPoint);
			//~~~movement in a specific interval~~~ // maybe should be replaced in a while till we hit the 
			//required point in an Approximation of defined radius
			panel.play.rotate((getAngle(endPoint)+90)); //passing the "player" angle and make the server do one step
			myGame=panel.play.getBoard();           //getting the stats from the board for the next step
			synchronized (panel.game) {		
				gameReset();
				panel.game.buildAgame(panel.play.getBoard()); //building new game object for the new step
			}
			panel.rotationRequired=getAngle(endPoint);
			panel.repaint();    //updating the linked panel
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			//~~~~set panel.game.player new location for the last point in the path and remove the fruit~~~~
			//maybe the server does it for us
			
			
		}//SECOND "WHILE" END (NO MORE FRUITS || TIME IS OVER)

		String statistics= panel.play.getStatistics();

		//game over cause
		String reason;
		if(panel.game.fruits.size()>0)
			reason = " Time is over";
		else 
			reason = " All fruits were eaten";

		//split statistics for the popup message
		String [] splitStat =statistics.split(",");
		panel.popUp(splitStat[0]+"\n" //date time
				+splitStat[1]+"\n" //total time
				+splitStat[3]+"\n" //time left
				+splitStat[2]+"\n" // score
				+"Average score on this map: "+Statistics.getAverageScore(GUI.mapName)+"\n" //avergae score
				+"Best score on this map: "+Statistics.getBestScore(GUI.mapName)+"\n"   //best score
				+splitStat[4]+"\n" //kill by ghosts
				+splitStat[5],		//out of box
				"Game Over : " + reason); //title
		System.out.println("time left : "+splitStat[3]);
		System.out.println("score : " + splitStat[2] );		


	}

	/*
	 * calculating the angle
	 * @param angle for the target point
	 * @return return the angle
	 */
	private double getAngle(Point3D target) {
		directionPoint = new Point3D(target.x(), target.y(),0); // ~~~~need to be point in pixels~~~~
		rotationRequired = 360
				- (panel.orientation(panel.game.player.getPoint(), Map.pixels2polar(directionPoint.ix(), directionPoint.iy())));
		System.out.println(rotationRequired+"YOYOYO");
		return rotationRequired;
	}
	//reseting the game for the next step
	/*
	 * game reset function
	 */
	private void gameReset() {
		panel.game.boxes.clear();
		panel.game.fruits.clear();
		panel.game.ghosts.clear();
		panel.game.packmans.clear();	
	}


}
