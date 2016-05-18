/**
 * GrepPattern.java
 * @author Hrishikesh Karale
 * @id hhk9433
 * @date 05/2/2015
 * @version v1.4
 * 
 * 
 * NOTE: this program takes in a command line input: file to be scanned.
 * forEX: java GrepPatern sample_results.text
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


/**
 * This program takes in a filename as command line parameter and greps specific
 * parts and saves them in files created during runtime. 
 */
class GrepPatern{
	//File object of Accuracy_result.txt as name is created.
	private File flObject1= new File("Accuracy_results.txt");
	//File object of Performance_measures.txt as name is created.
	private File flObject2= new File("Performance_measures.txt");
	//File object of Confusion_matrix.txt as name is created.
	private File flObject3= new File("Confusion_matrix.txt");
	
	//used to denote presence of data within strings
	boolean trigger1= false;
	boolean trigger2= false;
	boolean trigger3= false;
	
	/**
	 * This is the main method of our class. It takes in the input file as 
	 * command line parameter and then creates files if req by the program 
	 * at runtime to store the result of grep. 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String args[]) throws IOException{
		//keps track of strings to skip when storing data in files.
		int skip1=1, skip2=2, skip3=4;
		//keeps track of first "===" for grep3
		boolean found1= false;
		//keeps track of presence of "Confusion" followed by "===" for gre3
		boolean found2= false;
		//keeps track of "Matrix" followed by "Confusion" for grep3
		boolean found3= false;
		//object of class is created.
		GrepPatern gpObject= new GrepPatern();
		
		//buffered writers are initialized for every fileobject to null
		BufferedWriter buffWtrObject1= null;
		BufferedWriter buffWtrObject2= null;
		BufferedWriter buffWtrObject3= null;
		//buffered reader isinitialized to null for nput file
		BufferedReader buffRdrObject= null;
		
		String line= null;
		try{
			//input file reader object is being created.
			FileReader flRdrObject= new FileReader(args[0]);
			//buffered reader for input file is created.
			buffRdrObject= new BufferedReader(flRdrObject);
			line= buffRdrObject.readLine();
			while(line!= null){
				//line is split using " "(single whitespace) as token
				String []array= line.split(" ");
				//iterate over tokens after splitting the line.
				for(int i=0; i<array.length; i++){
					//check is the line containg string suymmary.
					if(array[i].equals("Summary")){
						//trigger set to true sa first grep found
						gpObject.trigger1=true;
						//create file for first grep
						gpObject.flObject1.createNewFile();
						//create file writer object for the file.
						FileWriter fr1= new FileWriter(gpObject.flObject1);
						//bufferedwriter is initialized for grep1
						buffWtrObject1= new BufferedWriter(fr1);
					}
					
					//checks if line contains the word Detailed.
					if(array[i].equals("Detailed")){
						//checks for accuracy in the line being scanned.
						if(array[i+1].equals("Accuracy")){
							//grep1 trigger set to false as we found the last string for grep1.
							gpObject.trigger1= false;
							//close bufferedWriter for grep1
							buffWtrObject1.close();
							//trigger for grep2 set as true.
							gpObject.trigger2= true;
							//new file to store output for grep2 is created
							gpObject.flObject2.createNewFile();
							//fileWriter created for new output file for grep2
							FileWriter fr2= new FileWriter(gpObject.flObject2);
							//buffered writer created for grep2 output file.
							buffWtrObject2= new BufferedWriter(fr2);
						}
					}

					////checks for "Confusion" in string
					if(array[i].equals("Confusion")){
						//checks for Matrix followed by confusion in string.
						if(array[i+1].equals("Matrix")){
							//sets trigger to false as grep2 completed.
							gpObject.trigger2= false;
							//closes bufferedWriter object for output for grep2 
							buffWtrObject2.close();
						}
					}
					
					//checks if first condition for grep3 is qialified.
					if(!gpObject.trigger3 &&(i<=array.length-4 &&(array[i].equals("===")|| found1))){
						found1= true;
					}
					else{found1=false;}

					//checks following confition i.e. "Confusion" followed by "===" is found
					if(!gpObject.trigger3 && found1 && (array[i+1].equals("Confusion")||found2)){
						found2= true;
					}
					else{found2= false; found1=false;}
					
					////checks for "Matrix" followed by "Confusion" for grep3 is met.
					if(!gpObject.trigger3 &&found2 &&(array[i+2].equals("Matrix")||found3)){
							found3= true;
					}
					else{found3= false;found2= false; found1=false;}
					
					//checksif last condition for grep3 is met.
					if(!gpObject.trigger3 &&found3 && array[i+3].equals("===")){
						//sets trigger for grep3 as true as confitions is met.
						gpObject.trigger3= true;
						//new file for grep3 is created.
						gpObject.flObject3.createNewFile();
						// filewriter object is created for output file for grep3.
						FileWriter fr3= new FileWriter(gpObject.flObject3);
						//bufferedWriiter object created for output file for grep3.
						buffWtrObject3= new BufferedWriter(fr3);
					}
					else{found3= false;found2= false;found1=false;}	
				
					//writes in output file for grep1
					if(gpObject.trigger1 ){
						//checks if strings need to be skipped
						if(skip1==0){
							buffWtrObject1.write(array[i]);
							if(i!=array.length-1)
								buffWtrObject1.write(" ");
						}
						else
							skip1--;
					}
					
					//writes in outputfile fore grep2.
					if(gpObject.trigger2){
						//checks if strings need to be skipped
						if(skip2==0){
							buffWtrObject2.write(array[i]);
							if(i!=array.length-1)
								buffWtrObject2.write(" ");
						}
						else 
							skip2--;
					}
					
					//writes in output file for grep3.
					if(gpObject.trigger3){
						//checks if strings need to be skipped
						if(skip3==0){
							buffWtrObject3.write(array[i]);
							if(i!=array.length-1)
								buffWtrObject3.write(" ");						
						}
						else
							skip3--;
					}
				}
				//inserts new line in output file for grep1.
				if(gpObject.trigger1){
					buffWtrObject1.newLine();
				}
				
				//inserts new line in output file for grep2.
				if(gpObject.trigger2){
					buffWtrObject2.newLine();
				}
				
				//inserts new line in output file for grep3.
				if(gpObject.trigger3){
					buffWtrObject3.newLine();
				}
				line = buffRdrObject.readLine();
			}
		}
		catch(Exception e){
			System.out.print("ERROR: ");
			e.printStackTrace();
		}
		//buffered writer object is closed for output file of grep3.
		buffWtrObject3.close();
		//bufferedReader is closed for input file.
		buffRdrObject.close();
		System.out.println("DONE!!");
	}
}