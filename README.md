# Final Project : 

## Intrudction

Welcome  , This project siumulates packman game on google earth maps .
CSV example files are attached.

#### Notice
 * In order to use your own csv file . the file must answer the following  requirments:
 
 ![Image description](https://github.com/Sniryefet/papi3/blob/master/Pictures/csv_format.PNG)
 
 **make sure the langtitude and longtitude  are in placed .**
 
 * There are four types as seen in the picture above  , "G" stands for Ghost , "F" for fruit , "P" for packman, "B" for Box  (The "player" is being add manually).



## How To Play

There are two options running the game :

1. **Auto Mode** 

	![Image description](https://github.com/Sniryefet/papi3/blob/master/Pictures/run%20simulation.PNG)

	### How it works
	
	After uploading a file , setting your player locations using "Insert" --> "Insert Player" 
	Click on "Run" as specified in the picture above.
	Then the game will start running .
	
	**How the player is moving?**
	After "Run" was activated and the game is running "player" movement is mouse sensative moving the "player" to the location of the mouse click.
	
2. **Manual Mode**

	 ![Image description](https://github.com/Sniryefet/papi3/blob/master/Pictures/manual%20run.PNG)
	 
	 In this mode the player is moving automatically based on Dijkstra algorithm.


## Auto Mode Algorithm

The algorithm we used calculating the path for the "player" is  based on Dijkstra algorithm
gettting the minimum distance from the player for a given fruit , Then going over all the fruits getting the minmum of all distances.

## Your Score :  

In the end of the game you will see your score.

 The score is being calculated like so :
 
 * for each fruit eaten by the player you will get 1 point
 * Same rule stands for eating packman( 1 point)
 * The game is over when 100 seconds passed or all fruits were eaten. 
 You will get bonus points of 1 point for every second left (Incase all fruits were eaten).


