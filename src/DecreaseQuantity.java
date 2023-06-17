import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class DecreaseQuantity {
    //Decreasing quantity when order gets rejected
	 public static boolean decreaseQuantity(String Redi_ID, String Item_ID, String quantity){
		File fileToBeModified = new File(Locations.getMenu_location());
        String oldContent = "";
        BufferedReader reader = null;
        FileWriter writer = null;
        try{
            reader = new BufferedReader(new FileReader(fileToBeModified));
             
            //Reading all the lines of input text file into oldContent  
            String oldString = null, newString = null;
            String line = reader.readLine();
            
            while (line != null) {
            	String []tempArr = line.split(",");
            	
            	if(tempArr[0].equals(Redi_ID) && tempArr[1].equals(Item_ID)){
            		oldString = tempArr[0]+","+tempArr[1]+","+tempArr[2]+","+tempArr[3]+","+tempArr[4];
            		newString = tempArr[0]+","+tempArr[1]+","+tempArr[2]+","+tempArr[3]+",";
            		
            		int present_quantity = Integer.parseInt(tempArr[4]);
            		int requested_quantity = Integer.parseInt(quantity);
            		
            		if(present_quantity < requested_quantity) return false;
            		
            		else if(present_quantity == requested_quantity) newString = newString + "-1";
            		else newString = newString + Integer.toString(present_quantity - requested_quantity);
            	}
                oldContent = oldContent + line + System.lineSeparator();
                line = reader.readLine();
            }
                        
            //Replacing oldString with newString in the oldContent             
            String newContent = oldContent.replaceAll(oldString, newString);
             
            //Rewriting the input text file with newContent             
            writer = new FileWriter(fileToBeModified);
            writer.write(newContent);
        }
        catch (IOException e){
            e.printStackTrace();
        }
        finally{
            try{
                //Closing the resources 
                reader.close();
                writer.close();
            } 
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        return true;
	}
}
