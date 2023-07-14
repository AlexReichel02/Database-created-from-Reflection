/***************************************************************
  Student Name: Alex Reichel
  File Name: FileMaker.java
  Assignment number Project 01

  Other comments regarding the file - description of why it is there, etc.
***************************************************************/

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.io.IOException;
import java.io.FileWriter; 
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class FileMaker {

	 public ArrayList<String> cars = new ArrayList<String>();
	 public ArrayList<Double> weight = new ArrayList<Double>();
	 public String randCar="";
	 public Double randWeight;
	 public Random random = new Random(); 

	 
	 /**
		Creates a empty FileMaker object
	  */
	 public FileMaker() {
	 
	 }
	 
	 /**
	 Creates a file named Vehicles.dat
	 Provides error detection
    */
	public void makeFile() {
			 try {
			      File myObj = new File("Vehicles.dat");
			      if (myObj.createNewFile()) {
			        System.out.println("Vehicle File created: " + myObj.getName());
			      } else {
			        System.out.println("Vehicle File already exists.");
			      }
			    } catch (IOException e) {
			      System.out.println("An error occurred.");
			      e.printStackTrace();
			    }
		}
	
	 /**
    Fills class array-list with specific car names 
	Fills class array-list with certain car weight values
	    */
	public void fillArrays() {
		
		   cars.add("chevy");
		   cars.add("ford");
		   cars.add("toyota");
		   cars.add("nissan");
		   cars.add("hyundai");
		   
		   
		   for(double i=1500; i < 4001; i++) {
			   weight.add(i);
		   }
	}
	
	
	/**
	 Function takes in a string parameter in place for the specific file that needs to be written to
     Function writes random 10 lines each with a random car type and random weight
     Function also writes more info on each line of vehicles depending on whether their foreign made and their car size
	 */
	public void writeFile(String fileName) {
		
		boolean import1=false;
		double engineSize;
		 FileWriter myWriter = null;
		 
		try {
			myWriter = new FileWriter(fileName);
		} catch (IOException e) {
			System.out.println("Error");
			e.printStackTrace();
		}
		
		
		   for(int i=1;i<=10;i++) {
			   
			   randCar  = cars.get(random.nextInt(cars.size()));
			   randWeight  = weight.get(random.nextInt(weight.size()));
			   
			   if(randCar == "Chevy" || randCar == "Ford") {
				   import1=false;
			   }else {
				   import1=true;
			   }
			   
			   
			   if(randWeight >= 1500 && randWeight<=2000) {
				   
				   engineSize = 2.5;
				   
				   try {
					System.out.println("Vehicle " + i +"/10 just created")  ; 
					myWriter.write(randCar + " " + randWeight + " " + "Compact" + " " + engineSize + " " + import1 + "\n");
				} catch (IOException e) {
					System.out.println("Error");
					e.printStackTrace();
				}
				   
			   }else if(randWeight > 2000 && randWeight <=2500) {
				   
				   engineSize = 4.5;
				   
				   try {
					   System.out.println("Vehicle " + i +"/10 just created");
					myWriter.write(randCar + " " + randWeight + " " + "Intermediate" + " " + engineSize + " " + import1+ "\n");
				} catch (IOException e) {
					System.out.println("Error");
					e.printStackTrace();
				}
				   
			   }else if(randWeight > 2500 && randWeight <=4000) {
				   
				   engineSize = 6.5;
				   
				   try {
					   System.out.println("Vehicle " + i +"/10 just created");
					myWriter.write(randCar + " " + randWeight + " " + "FullSized" + " " + engineSize + " "+ import1 +  "\n");
				} catch (IOException e) {
					System.out.println("Error");
					e.printStackTrace();
				}   
			   }  
		   }
		   
		   
		   try {
			myWriter.close();
		   } catch (IOException e) {
		 System.out.println("Error");
			e.printStackTrace();
		}
		
	}
	
	 
	/**
	Reads data from "Vehicles.dat"
	extracts and formats the contents of the file
	returns a formatted paragraph each line containing formated info on a specific vehicle
    */
	public String printFile() {
		String make;
		String size;
		String weight;
		String engineSize;
		String import1;
	
		String str=" ";
		
		try {
			
		      File myObj = new File("Vehicles.dat");
		      Scanner myReader = new Scanner(myObj);
		      myReader.useDelimiter(" |\n");
		      
		      	while (myReader.hasNext()) {
		      		
		      		make = myReader.next();
		      		str += String.format("('%s',", make);
		      		
		    		weight = myReader.next();
		      		str += String.format("%s,", weight);
		      		
		      		size = myReader.next();
		      		str += String.format("'%s',", size);
		      		
		    
		      		engineSize = myReader.next();
		      		str += String.format("%s,", engineSize);
		      		
		      		import1 = myReader.next();
		      		
		      		if(myReader.hasNext()==true) {
		      		str += String.format("%s)",import1);
		      		}else {
		      			str += String.format("%s)",import1);
		      		}
		      		
		      str +=  "\n";
		       
		      	}
		       
		      myReader.close();
		      	
		      
		    } catch (FileNotFoundException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
		
		return str;
		
	}
	 
	
	
}
