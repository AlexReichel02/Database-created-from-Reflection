/***************************************************************
  Student Name: Alex Reichel
  File Name: TestDB.java
  Assignment number Project 01

  Other comments regarding the file - description of why it is there, etc.
***************************************************************/
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.io.IOException;
import java.io.FileWriter; 
import java.util.ArrayList;
import java.util.Random;
import java.util.*;
import java.io. * ;
import java.util.Scanner;
import java.lang.reflect.*;
import java.util.Arrays;
import java.lang.reflect.Modifier;
/**
   Tests a database installation by creating and querying
   a sample table.
*/
public class TestDB 
{
	
	
	
	
   public static void main(String[] args) throws Exception
   {  
	   
	   FileMaker obj =  new FileMaker();
	   obj.makeFile();
	   System.out.println();
	   obj.fillArrays();
	   obj.writeFile("Vehicles.dat");
	   System.out.println();
	   
	 // System.out.println("this is it");
      DataManager.init("database.properties");
      
      
      Connection conn = DataManager.getConnection();
      Statement stat = conn.createStatement();    
       
      
 	   try {  
		  stat.execute("DROP TABLE Vehicles"); 
      }
	   catch (Exception e)
		{ System.out.println("test drop failed"); }    
 	   
 	   
 	 //DataManager.tryDropTable();
 	   

 	  DataManager.createTable();
 	  System.out.println();
	  DataManager.queryCols();
	  System.out.println();
	  DataManager.queryStrCols("chevy", "toyota");
	  System.out.println();
	  DataManager.queryNoStrCols(2500);
	  DataManager.tryDropTable();
	  
	  conn.close();
 		
 	   System.out.println();
 	   System.out.println("Printing Database Log: ");
 	   System.out.println("--------------------------------");
 	   DataManager.printFile();
 		
   }
}
    	  
    	  
    	  
    	
			  
			

   

      
       	  
