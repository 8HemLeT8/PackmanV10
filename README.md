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
	
   
2. **Manual Mode**

	 ![Image description](https://github.com/Sniryefet/papi3/blob/master/Pictures/manual%20run.PNG)


## Auto Mode Algorithm

The algorithm we used calculating the path for the "player" is  based on Dijkstra algorithm
gettting the minimum distance from the player for a given fruit , Then going over all the fruits getting the minmum of all distances.
