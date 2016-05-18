/**
 * Dijkstra.java
 * @author Hrishikesh Karale
 * @id hhk9433
 * @date 05/2/2015
 * @version v1.3
 * 
 * This program takes the input files as command line parameters.
 * FOR EX: java ReadFile inputfile1.txt inputfile2.txt inputfile3.txt
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * This class reads multiple files and writes them in a file named Part_I.txt
 */
public class ReadFile {
	/**
	 * This is the main method of our class. We first create the output file 
	 * and then start reading and simultaneously writting the file content 
	 * into out output file.
	 * @param args
	 */
	public static void main(String args[]){
		//keeps track of file being read.
		int i=0;
		//stores current line being read.
		String line= null;
		//file object with Part_I.txt is created.
		File flObject= new File("Part_I.txt");
		//writer object is initialized to null.
		FileWriter flWtrObject= null;
		//buffered writer object is initialized to null.
		BufferedWriter buffWrtObject= null;
		//buffered reader is initialized to null.
		BufferedReader buffRdrObject= null;

		try {
			//new file is created.
			flObject.createNewFile();
		} catch (IOException e1) {
			System.out.println("Problem creating file");
		}
		
		try {
			//filewriter is initialized for output file.
			flWtrObject= new FileWriter(flObject);
		} catch (IOException e1) {
			System.out.println("Problem creating fileWriter");
		}
		
		//buffered writer object is initialized for output file.
		buffWrtObject= new BufferedWriter(flWtrObject);
		
		//runs for total no of files given at command line
		while(i<args.length){
			//filereaderobject is set to null.
			FileReader flRdrObject= null;
			try{
				//filereader object is initialized to read current file.
				flRdrObject= new FileReader(args[i]);
			}
			catch(Exception e){
				System.out.println("No such file found: "+args[i]);
			}
			
			//bufferedreader is set to read current file.
			buffRdrObject= new BufferedReader(flRdrObject);
			
			try {
				//line being read is stored in line variable.
				line= buffRdrObject.readLine();
			} catch (IOException e) {
				System.out.println("IOEXCEPTION for file: "+args[i]);
			}
			
			//loops until we reach end of file.
			while(line!=null){
				try {
					//write current line into our output file.
					buffWrtObject.write(line);
					try {
						//read new line and store in line variable.
						line= buffRdrObject.readLine();
					} catch (IOException e) {
						System.out.println("IOEXCEPTION for file: "+args[i]);
					}
					//check if we need to insert a new line in output file.
					if(line!=null)
						buffWrtObject.newLine();					
				} 
				catch (Exception e) {
					System.out.println("Problem writting into file");
					e.printStackTrace();
				}
			}
			try {
				//close buffered reader for current file.
				buffRdrObject.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			//increment file count so as to read next file from command line.
			i++;
		}
		
		try{
			//close buffered writer for output file.
			buffWrtObject.close();
		}
		catch (Exception e){	
			System.out.println("Problem closing writer stream");
			e.printStackTrace();
		}			
	}
}
