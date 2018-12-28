package MyFrame;

import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Geom.Point3D;

public class myThread extends Thread{
	myPanel panel=new myPanel();
	ArrayList<String> myGame = new ArrayList(); //holding current game info

	public myThread (myPanel panel) {
		this.panel=panel;
	}
	public void run(){
		if(panel.play==null) return;
		long endDate = new Date().getTime()+(100*1000);
		panel.play.setIDs(333); //setting my id and deliever it to the server
		Point3D playerPosition = panel.game.player.getPoint();
		panel.play.setInitLocation(playerPosition.y(),playerPosition.x());
		panel.play.start(); // default max time is 100 seconds (1000*100 ms).
		while(panel.game.fruits.size()>0&&(endDate-new Date().getTime()>=0)) {  //while there is still fruit and the time isn't over
			panel.play.rotate((panel.rotationRequired+90)); //passing the "player" angle and make the server do one step
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
		panel.play.stop();
		//add pop up message (why the game has ended),
		//it due to time running or fruits are over + game statistics 
		String statistics= panel.play.getStatistics();
		
		//game over cause
		String reason;
		if(panel.game.fruits.size()>0)
			reason = " Time is over";
		else 
			reason = " All fruits were eaten";
		
		String [] splitStat =statistics.split(",");
		panel.popUp(splitStat[0]+"\n" //date time
			 +splitStat[1]+"\n" //total time
			 +splitStat[3]+"\n" //time left
			 +splitStat[2]+"\n" // score
			 +splitStat[4]+"\n" //kill by ghosts
			 +splitStat[5],		//out of box
			 "Game Over : " + reason); //title
	}

	//reseting the game for the next step
	private void gameReset() {
		panel.game.boxes.clear();
		panel.game.fruits.clear();
		panel.game.ghosts.clear();
		panel.game.packmans.clear();	
	}
	
	
}