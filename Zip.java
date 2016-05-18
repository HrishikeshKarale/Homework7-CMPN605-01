/**
 * Zip.java
 * @author Hrishikesh Karale
 * @id hhk9433
 * @date 05/1/2015
 * @version v1.8
 */

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * This class takes in a file Part_I.txt and archives it in Part_II.zip.
 */
public class Zip {

	/**
	 * This is the main method f our program. This method opens a file to be 
	 * archived. It then creates a zip file and adds our previous file to the 
	 * zip and then closes it.
	 * @param args
	 * @throws IOException 
	 */
    public static void main(String[] args) throws IOException {
    	//file object is initialized to null.
    	File iptFlObject= null;
    	//fileoutputstream object is initialized to null.
    	FileOutputStream flOptStrmObject= null;
    	//zipoutputstream object is initialized to null.
    	ZipOutputStream zpOptStrmObject= null;
        byte[] buffer = new byte[512];
        int bytes;
    	
	    try {
        	//make file object of the text file to be archived.
        	iptFlObject= new File("Part_I.txt");
        	//careate a fileOputpuStream object with new filename as parameter.
            flOptStrmObject = new FileOutputStream("Part_II.zip");
            //create a zipOutputStream obejct with the previou object as parameter.
            zpOptStrmObject = new ZipOutputStream(flOptStrmObject);
            /*
             * create a ZipEntry Object and give it our file to be archived 
             * as parameter so that it knows which files are included in zip.
             */
            ZipEntry zpEtryObject = new ZipEntry(iptFlObject.getName());
            //put entry into zip file.
            zpOptStrmObject.putNextEntry(zpEtryObject);

            //FileInputStream Object created so as to access the input file to be archived.
            FileInputStream flIptStrmObject = new FileInputStream(iptFlObject);
            

            // Read the input file by chucks of 512 bytes
            // and write the read bytes to the zip stream
            while ((bytes = flIptStrmObject.read(buffer)) > 0) {
                zpOptStrmObject.write(buffer, 0, bytes);
            }

            System.out.println("Input file :" + "Part_I.txt"+
            		" is zipped to archive :"+"Part_II.zip");
            flIptStrmObject.close();
        }
        catch (Exception e) {
        	System.out.print("ERROR CAUGHT:");
            e.printStackTrace();
        }
	    //close outputstreams.
	    finally{
	    	zpOptStrmObject.close();
            flOptStrmObject.close();
	    }
    }
}