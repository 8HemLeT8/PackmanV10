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
	static String[] columnNames = {"First ID", "Second ID", "Third ID", "Game date","Map name","Score!"};

	public static void showStatistics() {



		frame1 = new JFrame("Database Search Result");
		frame1.setLayout(new BorderLayout());


		DefaultTableModel model = new DefaultTableModel();
		model.setColumnIdentifiers(columnNames);


		table = new JTable();
		table.setModel(model);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setGridColor(Color.BLUE);

		table.setFillsViewportHeight(true);
		JScrollPane scroll = new JScrollPane(table);


		TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());

		table.setAutoCreateRowSorter(true);

		sorter.setComparator(0, new Comparator<String>() {

			@Override
			public int compare(String name2, String name1) {
				return -(int)(Double.parseDouble(name1)-(Double.parseDouble(name2)));
			}
		});
		sorter.setComparator(5, new Comparator<String>() {

			@Override
			public int compare(String name2, String name1) {
				double d= (Double.parseDouble(name2)-(Double.parseDouble(name1)));
				if(d>0) return 1;
				else if(d<0) return -1;
				else return 0;
			}
		});

		sorter.setComparator(2, new Comparator<String>() {

			@Override
			public int compare(String name2, String name1) {
				return -(int)(Double.parseDouble(name1)-(Double.parseDouble(name2)));
			}
		});

		sorter.setComparator(1, new Comparator<String>() {

			@Override
			public int compare(String name2, String name1) {
				return -(int)(Double.parseDouble(name1)-(Double.parseDouble(name2)));
			}
		});

		table.setRowSorter(sorter);



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
					DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);


			statement = connection.prepareStatement("SELECT * FROM logs");
			ResultSet resultSet = statement.executeQuery(allCustomersQuery);
			int i = 0;
			while (resultSet.next()) {
				FirstID=	resultSet.getString("FirstID");
				SecondID=	resultSet.getString("SecondID");
				ThirdID=	resultSet.getString("ThirdID");
				score=		resultSet.getString("Point");
				LogTime=	resultSet.getTimestamp("LogTime");
				Map=	    resultSet.getString("SomeDouble");
				switch(Map) {
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
				if(!Map.equals("BYE"))
					model.addRow(new Object[]{FirstID, SecondID, ThirdID, LogTime,Map,score});
				i++;		
			}
			if (i < 1) {
				JOptionPane.showMessageDialog(null, "No Record Found", "Error", JOptionPane.ERROR_MESSAGE);
			}
			if (i == 1) {
				System.out.println(i + " Record Found");
			} else {
				System.out.println(i + " Records Found");
			}
		} 
		catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		frame1.add(scroll);
		frame1.setVisible(true);
		frame1.setSize(400, 300);
	}

	/*
	public static void main(String args[]) {
		showStatistics();


	}
	 */

}