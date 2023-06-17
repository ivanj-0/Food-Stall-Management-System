import java.io.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
public class IncreaseQuantity {
    public static void increaseQuantity(String Redi_ID, String Item_ID, String quantity) {
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

                    if(tempArr[4]=="-1") newString = newString + quantity;
                    else newString = newString + Integer.toString(Integer.parseInt(tempArr[4]) + Integer.parseInt(quantity));
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
    }
}
