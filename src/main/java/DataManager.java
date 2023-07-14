/***************************************************************
  Student Name: Alex Reichel
  File Name: DataManager.java
  Assignment number Project 01

  Other comments regarding the file - description of why it is there, etc.
***************************************************************/
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.io.FileWriter; 
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;



public class DataManager{
	
	   private static String url;
	   private static String username;
	   private static String password;
	   private static Connection con;
	   private static Logger logger;
	   private static File myFile;
	   private static FileWriter myWriter;
	
	   /**
		 Creates an empty DataManager object
	    */
	   public DataManager() {
			 
		 }
	   
	   /**
		 Function creates a file for the database operations and every action associated with the database
	    */
	   public static void makeDBFile() {
			
			 try {
				   myFile = new File("dbOperations.log");
			      if (myFile.createNewFile()) {
			        System.out.println("Vehicle File created: " + myFile.getName());
			      } else {
			        System.out.println("Vehicle File already exists.");
			      }
			    } catch (IOException e) {
			      System.out.println("An error occurred.");
			      e.printStackTrace();
			    }
			 
			  
				
		}
		
		
	   /**
		 Function takes in a string parameter representing what needs to be written to the file
		 Function writes the string parameter to the specific file
	    */
		public static void writeDBLog(String entry) {
			
			myWriter = null;
			try {
				 myWriter = new FileWriter(myFile,true);
			} catch (IOException e) {
				System.out.println("Error");
				e.printStackTrace();
			}
			
			
				try {
					myWriter.write(entry);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				try {
					myWriter.close();
				   } catch (IOException e) {
				 System.out.println("Error");
					e.printStackTrace();
				}
				
		}
			
		/**
		 Function prints the contents of "dbOperations.log"
	    */
		public static void printFile() {
	
			try {
				
			      File myObj = new File("dbOperations.log");
			      Scanner myReader = new Scanner(myObj);
			      
			      	while (myReader.hasNextLine()) {
			      		
			      		System.out.println(myReader.nextLine());
			       
			      	}
			       
			      myReader.close();
			      	
			      
			    } catch (FileNotFoundException e) {
			      System.out.println("An error occurred.");
			      e.printStackTrace();
			    }
			
			
		}
	   
		/**
		 Function tries to drop the data base table
	    */
	   public static void tryDropTable() {
			  
			  String str = "DROP TABLE Vehicles";
			  PreparedStatement create;
			  System.out.println();
			  if (logger.isLoggable(Level.INFO)) {
		            logger.info("Query Entry: Table Drop test");
		        }
			  
			  try {  
				  create = con.prepareStatement(str);
				  create.execute(str);
		      }
			   catch (Exception e)
				{ System.out.println("drop failed"); 
			
				}    
		  }
	   
	   
	   /**
		 Function creates the database table Vehicles
		 Using reflection to get the fields from the vehicle class
		 Formats a string from the fields to represent the column values of the database table
		 Function
	    */
	   public static void createTable() throws SQLException {
		  
		   
		   makeDBFile();
		   
		   	try {
				makeDBLog();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		   	List<Field> privFields = new ArrayList<>();
			Field[] privateFields = Vehicle.class.getDeclaredFields();
			
			for(Field field: privateFields) {
					privFields.add(field);
				}
			
			
		   String name;
		   String statement = "CREATE TABLE Vehicles (";
		   for(int i=0;i<privateFields.length;i++) {
			   
			   
			   name =  privateFields[i].getName();
			   statement +=name;
			   
			  if( privateFields[i].getType().equals(String.class)) {
				 
				  statement += " VARCHAR(20),";
			  }else 
			  {
				  statement += " " + privateFields[i].getType()+ ",";
			  }

		
		   }
		   
		   String result = null;
		   if ((statement != null) && (statement.length() > 0)) {
		      result = statement.substring(0, statement.length() - 1);
		   }
		   
		   result += ")";
		   	   
		   
		   con = getConnection();
		   PreparedStatement create = con.prepareStatement(result);
		   create.execute();
		   
		   System.out.println();
		   System.out.println("Database Table created");
		   writeDBLog("Database Table created" + "\n");
		   if (logger.isLoggable(Level.INFO)) {
	            logger.info("Database Table created");
	        }
		   

		   System.out.println();
		   fillTable(result);
		  
		   
		   
	   }
	   
	   /**
		 Function fills the database table with the formatted paragraph from the printFile() function from the FileMaker class
		 Paragraph gets parsed into individual lines then added into the database table
	    */
	   public static void fillTable(String str) {
		  
		   
		   String str1="";
		   FileMaker obj=new FileMaker();
		   str1 = obj.printFile();
		   String lines[] = str1.split("\n");
		   String execute1="";
		   String execute="INSERT INTO Vehicles VALUES ";
		   
		   for(int i=0;i<lines.length;i++) {
			   execute1 = execute + lines[i];
			   PreparedStatement insertRecord;
			try {
				insertRecord = con.prepareStatement(execute1);
		
				insertRecord.execute();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			
			 
		   }
		   
		   System.out.println();
		   System.out.println("Database Table Filled ");
		   writeDBLog("Database Table Filled "+ "\n");
		   
		   if (logger.isLoggable(Level.INFO)) {
	            logger.info("Database Table Filled");
	        }
		 
	   }
	   
	  
	   /**
		 Function creates a logger to the console 
	    */
	   public static void makeDBLog() throws SecurityException, IOException {
		   logger = Logger.getLogger(DataManager.class.getName());
		}
		
	  
	  
	   /**
		 Function prints the contents of whole currennt DataBase
	    */
	  public static void queryCols() {
		  
		  String str="SELECT * FROM Vehicles";
		  System.out.println(str);
		  if (logger.isLoggable(Level.INFO)) {
	            logger.info("Query Entry: " + str);
	        }
		  PreparedStatement create;
		  
		  
		try {
			create = con.prepareStatement(str);
			ResultSet result = create.executeQuery();
			readResults(result);  
			
			  
		} catch (SQLException e) {
			e.printStackTrace();
		}
		  
			  
	  }
	  
	  
	  /**
		 Function prints the contents of the ResultSet Obj
		 Function takes in a ResultSet Obj, and formats into columns printing the specific data
	    */
    public static void readResults(ResultSet obj) throws SQLException {
    	
    	if (logger.isLoggable(Level.INFO)) {
            logger.info("Query Entry: Current Data Table");
        }
    	
    	writeDBLog("Current Data Table" + "\n");
    	
			  System.out.println("Table Data");
			  System.out.println("------------------------------------------");
				ResultSetMetaData rsm = obj.getMetaData();
				int cols = rsm.getColumnCount();
				
				if(!obj.next()) {
					System.out.println("No data in table");
					return;
				}
				
				  while(obj.next())
				  {
				    for(int i = 1; i <= cols; i++)
				    System.out.print(obj.getString(i)+"  ");
				    System.out.println("");      
				  }
				  
		  }
	  
    /**
	 Function prints the contents of the Database table dependent on the type
	 Function takes in a double, representing the type of data that needs to be printed from data table
   */
	  public static void queryNoStrCols(double type1) {
		  
		  String base = "Select * from Vehicles WHERE (weight >(?))";
		  String base2 = "Select * from Vehicles WHERE (weight > 2500 pounds)";
		  System.out.println(base2);
		  
		 
		  
		  if (logger.isLoggable(Level.INFO)) {
	            logger.info("Query Entry: " + base2);
	        }
	  
		  PreparedStatement weightVals;
		  
			try {
				weightVals = con.prepareStatement(base);
				weightVals.setDouble(1, type1);
				ResultSet result = weightVals.executeQuery();
				readResults(result);  
				
				  
			} catch (SQLException e) {
				e.printStackTrace();
			} 
			
			 writeDBLog(base2 + "\n");
		  
	  }
 
	 
	  /**
		 Function prints the contents of the Database table dependent on the type
		 Function takes in a two strings, representing the types of data that needs to be printed from data table
	   */
	  public static void queryStrCols(String type1,String type2) {
	  
		
		  String base = "Select * from Vehicles WHERE (make =  LOWER(?) OR  make =  LOWER(?))";
		  String base2 = "Select * from Vehicles WHERE (make = Chevy OR  make =  Toyota)";
		  System.out.println(base2);
		
		 
		  if (logger.isLoggable(Level.INFO)) {
	            logger.info("Query Entry: " + base2);
	        }
		  
		  PreparedStatement stringVals;
		  
			try {
				stringVals = con.prepareStatement(base);
				stringVals.setString(1, type1);
				stringVals.setString(2, type2);
				ResultSet result = stringVals.executeQuery();
				
				readResults(result);  
				
				  
			} catch (SQLException e) {
				e.printStackTrace();
			} 
			
			
			 writeDBLog(base2 + "\n");
			
	  }
	   
	
	 public static void init(String fileName)
	         throws IOException, ClassNotFoundException
	   {  
	      Properties props = new Properties();
	      FileInputStream in = new FileInputStream(fileName);
	      props.load(in);

	      String driver = props.getProperty("jdbc.driver");
	      url = props.getProperty("jdbc.url");
	      username = props.getProperty("jdbc.username");
	      if (username == null) username = "";
	      password = props.getProperty("jdbc.password");
	      if (password == null) password = "";
	      if (driver != null)
	         Class.forName(driver);
	   }

	   
	   public static Connection getConnection() throws SQLException
	   {
	      return DriverManager.getConnection(url, username, password);
	   }
	   
	   
		

}
