package Algo;

import java.util.ArrayList;

//import org.graalvm.compiler.lir.LIRInstruction.Temp;

import GIS.Box;
import GIS.Fruit;
import Geom.Point3D;
import MyFrame.Map;


public class PathMaker {
	static Map map =new Map();

	public static ArrayList<Point3D> graphMake(ArrayList<Box> box ,ArrayList<Fruit> fruit,Point3D currentSrcLocation) {
		ArrayList<Point3D> returns = new ArrayList<Point3D>();
		int size = box.size()*4+2;
		int []xx = new int [size]; //contains all points 4 for each box and 2 more for the src and trg
		int []yy =new int [size];
		int indexForX=0;
		int indexForY=0;

		Point3D [] temp =new Point3D[size]; //need to add source&target manually


		for(Box myBox: box) {  // initilaized
			xx[indexForX++]=(int)myBox.getMinInPixels().x()-5;  // 
			yy[indexForY++]=(int)myBox.getMinInPixels().y()-5;  // Point (min x, min y) 

			xx[indexForX++]=(int)myBox.getMinInPixels().x()-5;  //
			yy[indexForY++]=(int)myBox.getMaxInPixels().y()+5;  // Point (min x , max y)

			xx[indexForX++]=(int)myBox.getMaxInPixels().x()+5;  //
			yy[indexForY++]=(int)myBox.getMinInPixels().y()-5;  // Point (max x , min y)

			xx[indexForX++]=(int)myBox.getMaxInPixels().x()+5;  //
			yy[indexForY++]=(int)myBox.getMaxInPixels().y()+5;  //Point (max x , max y)
		}

		Point3D currentSrc = new Point3D(currentSrcLocation);


		double minDis=Double.MAX_VALUE;
		Point3D bestTarget = null;		
		ArrayList<String> bestPath = new ArrayList<String>();

		//currentSrc location is the num1 spot in the array
		temp[0]=new Point3D(currentSrcLocation); 
		for(int i=1;i<size-1;i++) 
		{
			temp[i] = new Point3D((int)xx[i],(int)yy[i]);
		}

		//last spot in the array is missing and need to be set in the loop 
		//to be dinemic
		for(Fruit f : fruit) {  	//loop on all targets

			Point3D fruitP= new Point3D(f.getLocationInPixels().x(),f.getLocationInPixels().y(),0); //target location(dynemic)
			temp[size-1] =new Point3D(fruitP); //dynemic target

			//need to initilaized the source
			//initilaized the fruits


			Graph G = new Graph(); 
			String source = "src";
			String target = "trg";
			G.add(new Node(source));
			G.add(new Node(target)); // Node "target" (size-1)

			for(int i=1;i<size-1;i++) {
				Node d = new Node(""+i);
				G.add(d);
				temp[i] = new Point3D(xx[i], yy[i]);
			}	


			for(int i=1;i<temp.length;i++) {
				for(int j=0;j<i;j++) {
					Node nodeI = G.getNodeByIndex(i);
					Node nodeJ =G.getNodeByIndex(j);
					if(!Intersection.isIntersect(temp[i], temp[j], box))
						G.addEdge(nodeI.get_name(), nodeJ.get_name(),temp[i].distance2D(temp[j]));
				}
			}
			System.out.println(G);
			Graph_Algo.dijkstra(G, source);
			Node b =G.getNodeByName(target);
			double currentDis=b.getDist();
			System.out.println(currentDis);
	
			if(currentDis<minDis) {
				minDis=currentDis;
				returns = new ArrayList<Point3D>();
				bestTarget=temp[b.get_id()];
				bestPath=b.getPath();
				returns.add(temp[0]); // (?) SOURCE 
				for(int i=1;i<bestPath.size();i++) {
					returns.add(temp[Integer.parseInt(bestPath.get(i))]); // (?)
				}

			}
		}
		return returns;
	}
}
