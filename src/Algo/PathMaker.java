package Algo;

import java.util.ArrayList;
import GIS.Box;
import GIS.Fruit;
import Geom.Point3D;
import MyFrame.Map;


public class PathMaker {
	static Map map =new Map();

	/*
	 * route calculation for fruit
	 * @return return arraylist of points for the route
	 * @param getting arraylist of fruits of the game and boxes and current player location
	 */
	public static ArrayList<Point3D> graphMake(ArrayList<Box> box ,ArrayList<Fruit> fruit,Point3D currentSrcLocation) {
		int size = box.size()*4+2;
		int []xx = new int [size-2]; //contains all points 4 for each box and 2 more for the src and trg
		int []yy =new int [size-2];
		int indexForX=0;
		int indexForY=0;


		Point3D [] graphArray = new Point3D[size]; //need to add source&target manually
		ArrayList<Point3D> bestPath = new ArrayList<Point3D>();

		int shift = 20;
		for(Box myBox: box) {  // initilaized
			xx[indexForX++]=(int)myBox.getMinInPixels().x()-shift;  // 
			yy[indexForY++]=(int)myBox.getMinInPixels().y()+shift;  // Point (min x, min y) 

			xx[indexForX++]=(int)myBox.getMinInPixels().x()-shift;  //
			yy[indexForY++]=(int)myBox.getMaxInPixels().y()-shift;  // Point (min x , max y)

			xx[indexForX++]=(int)myBox.getMaxInPixels().x()+shift;  //
			yy[indexForY++]=(int)myBox.getMinInPixels().y()+shift;  // Point (max x , min y)

			xx[indexForX++]=(int)myBox.getMaxInPixels().x()+shift;  //
			yy[indexForY++]=(int)myBox.getMaxInPixels().y()-shift;  //Point (max x , max y)
		}



		double minDis=Double.MAX_VALUE;

		//currentSrc location is the num1 spot in the array
		graphArray[0]=new Point3D(currentSrcLocation); 
		for(int i=1;i<size-1;i++) 
		{
			graphArray[i] = new Point3D((int)xx[i-1],(int)yy[i-1]);
		}

		//last spot in the array is missing and need to be set in the loop 
		//to be dinemic
		for(Fruit f : fruit) {  	//loop on all targets

			Point3D fruitP= new Point3D(f.getLocationInPixels().x(),f.getLocationInPixels().y(),0); //target location(dynemic)
			graphArray[size-1] =new Point3D(fruitP); //dynemic target


			Graph G = new Graph(); 
			String source = "src";
			String target = "trg";

			G.add(new Node(source));
			//if there is no Boxes this "For" should not work
			for(int i=1;i<size-1;i++) {
				if(Intersection.isContains(graphArray[i],box))   //if one of the points is inside	//may cause problem since its after the strech
					continue;	
				Node d = new Node(""+i);
				G.add(d); 


			}	
			G.add(new Node(target)); // Node "target" (size-1)

			//initilaized edges
			for(int i=1;i<graphArray.length;i++) {
				for(int j=0;j<i;j++) {   /// j<???
					if(Intersection.isContains(graphArray[i],box)   //if one of the points is inside
							||Intersection.isContains(graphArray[j],box)){ //may cause problem since its after the strech
						continue;
					}
					Node nodeI = G.getNodeByIndex(i);
					Node nodeJ =G.getNodeByIndex(j);
					if(!Intersection.isIntersectBoxes(graphArray[i],graphArray[j] , box)) {	
						G.addEdge(nodeI.get_name(), nodeJ.get_name(),graphArray[i].distance2D(graphArray[j]));
						//System.out.println("edge from: "+nodeI.get_name()+" to: "+nodeJ.get_name());

					}
				}
			}

			//~~~~~~~~~~~~~ DEBUG
			// RUN Boaz's Algo
			Graph_Algo.dijkstra(G, source);
			Node b =G.getNodeByName(target);
			//	System.out.println(G);
			double currentDis=b.getDist();
			if(currentDis<minDis) {
				minDis=currentDis;
				bestPath = new ArrayList<Point3D>();
				bestPath.add(new Point3D(currentSrcLocation));
				for(int i = 1 ;i<b.getPath().size();i++) { //<= since the path not including the target
					bestPath.add(graphArray[Integer.parseInt(b.getPath().get(i))]);
				}
				bestPath.add(new Point3D(fruitP));
				//	System.out.println("shortest path size :"+bestPath.size());
				//get the name of the node 
				//the name is also the index for the point array
				//get the point and add it to the result array
				//add target in the end
			}
		}
		return bestPath;
	}
}
