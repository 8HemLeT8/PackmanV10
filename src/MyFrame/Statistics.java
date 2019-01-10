package MyFrame;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Comparator;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class Statistics extends JFrame{

	private static JFrame frame1;
	private static String jdbcUrl="jdbc:mysql://ariel-oop.xyz:3306/oop"; //?useUnicode=yes&characterEncoding=UTF-8&useSSL=false";
	private static String jdbcUser="student";
	private static String jdbcPassword="student";

	static Statement statement;
	static JTable table;
	static String[] columnNames = {"First ID", "Second ID", "Third ID", "Game date","Map name","Score!"};    //column names 
	
	
	/*
	 * function to show the statistics
	 */
	public static void showStatistics() {

		frame1 = new JFrame("Database Search Result");   
		frame1.setLayout(new BorderLayout());


		DefaultTableModel model = new DefaultTableModel();
		model.setColumnIdentifiers(columnNames);                     //make new default table model with my column names


		table = new JTable();
		table.setModel(model);										//create a table in the frame using the model we made
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setGridColor(Color.BLUE);

		table.setFillsViewportHeight(true);
		JScrollPane scroll = new JScrollPane(table);				//scroller fro the table


		TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());

		table.setAutoCreateRowSorter(true);							//default raw sorter

		sorter.setComparator(0, new Comparator<String>() {         //sorter for raw number 0 

			@Override
			public int compare(String name2, String name1) {
				return -(int)(Double.parseDouble(name1)-(Double.parseDouble(name2)));
			}
		});
		sorter.setComparator(5, new Comparator<String>() {			  //sorter for raw number 5

			@Override
			public int compare(String name2, String name1) {		
				double d= (Double.parseDouble(name2)-(Double.parseDouble(name1)));
				if(d>0) return 1;
				else if(d<0) return -1;
				else return 0;
			}
		});

		sorter.setComparator(2, new Comparator<String>() {			  //sorter for raw number 2

			@Override
			public int compare(String name2, String name1) {
				return -(int)(Double.parseDouble(name1)-(Double.parseDouble(name2)));
			}
		});

		sorter.setComparator(1, new Comparator<String>() {			  //sorter for raw number 1

			@Override
			public int compare(String name2, String name1) {
				return -(int)(Double.parseDouble(name1)-(Double.parseDouble(name2)));
			}
		});

		table.setRowSorter(sorter);     //update the table raw sorter with the changes for the specific raws



		scroll.setHorizontalScrollBarPolicy(
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setVerticalScrollBarPolicy(
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		String FirstID ;
		String SecondID;
		String ThirdID ;
		Timestamp LogTime =null;
		String score ;
		String Map ;

		try {
			String allCustomersQuery = "SELECT * FROM logs;";

			Connection connection = 
					DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);   //connect to the databse with the username and password

			statement = connection.prepareStatement("SELECT * FROM logs");    //command for the database to take its' content
			ResultSet resultSet = statement.executeQuery(allCustomersQuery);   //execute the command to take all the inforamtion and save it
			int i = 0;
			while (resultSet.next()) {								//for each raw in the database
				FirstID=	resultSet.getString("FirstID");     //read the content of each column in that box and put it in an java object
				SecondID=	resultSet.getString("SecondID");
				ThirdID=	resultSet.getString("ThirdID");
				score=		resultSet.getString("Point");
				LogTime=	resultSet.getTimestamp("LogTime");
				Map=	    resultSet.getString("SomeDouble");
				switch(Map) {                                       //replace the hashcodes of the maps buy their names:
				case "2128259830":
					Map="Ex4_OOP_example1.csv"; break;
				case "1149748017":
					Map="Ex4_OOP_example2.csv"; break;
				case "-683317070":
					Map="Ex4_OOP_example3.csv"; break;
				case "1193961129":
					Map="Ex4_OOP_example4.csv"; break;
				case "1577914705":
					Map="Ex4_OOP_example5.csv"; break;
				case "-1315066918":
					Map="Ex4_OOP_example6.csv"; break;
				case "-1377331871":
					Map="Ex4_OOP_example7.csv"; break;
				case "306711633":
					Map="Ex4_OOP_example8.csv"; break;
				case "919248096":
					Map="Ex4_OOP_example9.csv"; break;
				default: Map="BYE";
				}

				//if(FirstID==1911||FirstID==333) 
				if(!Map.equals("BYE"))   //add to our table only the results of the correct maps
					model.addRow(new Object[]{FirstID, SecondID, ThirdID, LogTime,Map,score});
				i++;		
			}
			if (i < 1) {
				JOptionPane.showMessageDialog(null, "No Record Found", "Error", JOptionPane.ERROR_MESSAGE);  //if the database is empty return an error
			}
			if (i == 1) {
				System.out.println(i + " Record Found");			//print how much rows found in the database
			} else {
				System.out.println(i + " Records Found");
			}
		} 
		catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);  //if failed return Error
		}
		frame1.add(scroll);
		frame1.setVisible(true);        //show the new frame with the table 
		frame1.setSize(400, 300);
	}
	
	/*
	 * @return return the average score
	 * @param getting the map name
	 */
	public static double getAverageScore(String mapName) {
		switch(mapName) {                                       //replace the hashcodes of the maps buy their names:
		case "Ex4_OOP_example1.csv":
			mapName="2128259830"; break;
		case "Ex4_OOP_example2.csv":
			mapName="1149748017"; break;
		case "Ex4_OOP_example3.csv":
			mapName="-683317070"; break;
		case "Ex4_OOP_example4.csv":
			mapName="1193961129"; break;
		case "Ex4_OOP_example5.csv":
			mapName="1577914705"; break;
		case "Ex4_OOP_example6.csv":
			mapName="-1315066918"; break;
		case "Ex4_OOP_example7.csv":
			mapName="-1377331871"; break;
		case "Ex4_OOP_example8.csv":
			mapName="306711633"; break;
		case "Ex4_OOP_example9.csv":
			mapName="919248096"; break;
		default: mapName="BYE";
		}
		String score ;
		double average = 0;
		int counter=0;
		try {
			String allCustomersQuery = "SELECT * FROM logs;";

			Connection connection = 
					DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);   //connect to the databse with the username and password

			statement = connection.prepareStatement("SELECT * FROM logs");    //command for the database to take its' content
			ResultSet resultSet = statement.executeQuery(allCustomersQuery);   //execute the command to take all the inforamtion and save it
			while (resultSet.next()) {								//for each raw in the database
				score=resultSet.getString("Point");
				if(resultSet.getString("SomeDouble").equals(mapName)) {
					counter++;
					average+=Double.parseDouble(score);		
				}
			}
			if (counter< 1) {
				JOptionPane.showMessageDialog(null, "No Record Found", "Error", JOptionPane.ERROR_MESSAGE);  //if the database is empty return an error
			}

		} 
		catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);  //if failed return Error
		}
		return average/counter;		
	}

	public static double getBestScore(String mapName) {
		switch(mapName) {                                       //replace the hashcodes of the maps buy their names:
		case "Ex4_OOP_example1.csv":
			mapName="2128259830"; break;
		case "Ex4_OOP_example2.csv":
			mapName="1149748017"; break;
		case "Ex4_OOP_example3.csv":
			mapName="-683317070"; break;
		case "Ex4_OOP_example4.csv":
			mapName="1193961129"; break;
		case "Ex4_OOP_example5.csv":
			mapName="1577914705"; break;
		case "Ex4_OOP_example6.csv":
			mapName="-1315066918"; break;
		case "Ex4_OOP_example7.csv":
			mapName="-1377331871"; break;
		case "Ex4_OOP_example8.csv":
			mapName="306711633"; break;
		case "Ex4_OOP_example9.csv":
			mapName="919248096"; break;
		default: mapName="BYE";
		}
		double score =0;
		double best = 0;
		int counter=0;
		try {
			String allCustomersQuery = "SELECT * FROM logs;";

			Connection connection = 
					DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);   //connect to the databse with the username and password

			statement = connection.prepareStatement("SELECT * FROM logs");    //command for the database to take its' content
			ResultSet resultSet = statement.executeQuery(allCustomersQuery);   //execute the command to take all the inforamtion and save it
			while (resultSet.next()) {								//for each raw in the database
				counter++;

				if(resultSet.getString("SomeDouble").equals(mapName)) {
					score=Double.parseDouble(resultSet.getString("Point"));
					if(best<score) best=score;
						
					
				}
			}
			if (counter< 1) {
				JOptionPane.showMessageDialog(null, "No Record Found", "Error", JOptionPane.ERROR_MESSAGE);  //if the database is empty return an error
			}

		} 
		catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);  //if failed return Error
		}
		return best;		
	}
	
}