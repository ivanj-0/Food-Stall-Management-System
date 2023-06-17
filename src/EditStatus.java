import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class EditStatus extends RediID {
	
	private String student_ID;
	private String redi_ID;
	private int item_ID;
	private String status;
	private String file_Location;
	
	public EditStatus() {
		setFile_Location(Locations.getOrder_location());
		setRedi_ID(RediID.getId());
	}

	public void editOption() throws IOException {
		int min_order_flag=0;
		Scanner in=new Scanner(System.in);
		File file = new File(file_Location);
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		String line = br.readLine();
		String[] tempArr;
		System.out.println("Waiting orders will be displayed one by one.");
		System.out .println("0: Reject, 1: Accept, 2: Stop checking orders");
		int order_flag=-1;
		while(order_flag!=2&&line!=null){
			tempArr = line.split(",");
			if(tempArr.length == 9 && tempArr[8].equalsIgnoreCase("Wait") && tempArr[2].equalsIgnoreCase(redi_ID)){
				System.out.println("Student ID: "+tempArr[0]+" Item ID: "+tempArr[3]+" Item name: "+tempArr[4]+" Quantity: "+tempArr[6]);
				System.out.println("Choose status flag 0, 1 or 2:");
				min_order_flag=1;
				setStudent_ID(tempArr[0]);
				setRedi_ID(tempArr[2]);
				setItem_ID(Integer.parseInt(tempArr[3]));
				order_flag=in.nextInt();
				if(order_flag!=2) {
					if (order_flag == 1) setStatus("Accept");
					if (order_flag == 0) setStatus("Reject");

					boolean check = edit();
					if (check) System.out.println("Done!!");

				}
				else break;
			}
			line= br.readLine();
		}
		if(min_order_flag==0){System.out.println("No waiting orders in "+RediID.getId());}
		System.out.println("Exiting order status system");
	}
	
	private boolean edit(){
        try {
            File file = new File(getFile_Location());
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            
            String line = br.readLine();
            String[] tempArr;
            
            while (line != null) {
                tempArr = line.split(",");

                if (tempArr[0].equals(getStudent_ID()) && tempArr[2].equals(getRedi_ID()) && tempArr[3].equals(Integer.toString(getItem_ID()))){
                	String oldContent = tempArr[0]+","+tempArr[1]+","+tempArr[2]+","+tempArr[3]+","+tempArr[4]+","+tempArr[5]+","+tempArr[6]+","+tempArr[7]+","+tempArr[8];
                	String newContent = tempArr[0]+","+tempArr[1]+","+tempArr[2]+","+tempArr[3]+","+tempArr[4]+","+tempArr[5]+","+tempArr[6]+","+tempArr[7]+","+getStatus();
                	change(oldContent,newContent);
                	
                	if(getStatus().equals("Reject")) IncreaseQuantity.increaseQuantity(tempArr[2],tempArr[3],tempArr[6]);
                    return true;
                }
                line=br.readLine();
            }
        }
        catch(IOException e){
        	e.printStackTrace();
        }
        
        return false;
    }
	
	private void change(String oldString, String newString){
        File fileToBeModified = new File(getFile_Location());
        String oldContent = "";
        BufferedReader reader = null;
        FileWriter writer = null;
        try{
            reader = new BufferedReader(new FileReader(fileToBeModified));
             
            //Reading all the lines of input text file into oldContent  
            String line = reader.readLine();
            while (line != null) {
                oldContent = oldContent + line + System.lineSeparator();
                line = reader.readLine();
            }
            
            //Replacing oldString with newString in the oldContent             
            String newContent = oldContent.replace(oldString,newString);
         
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

	
	private String getStudent_ID() {
		return student_ID;
	}

	private void setStudent_ID(String student_ID) {
		this.student_ID = student_ID;
	}

	private String getRedi_ID() {
		return redi_ID;
	}

	private void setRedi_ID(String redi_ID) {
		this.redi_ID = redi_ID;
	}

	private int getItem_ID() {
		return item_ID;
	}

	private void setItem_ID(int item_ID) {
		this.item_ID = item_ID;
	}

	private String getStatus() {
		return status;
	}

	private void setStatus(String status) {
		this.status = status;
	}

	private String getFile_Location() {
		return file_Location;
	}

	private void setFile_Location(String file_Location) {
		this.file_Location = file_Location;
	}

	public static void main(String[] args) throws IOException {
		EditStatus flag = new EditStatus();
		flag.editOption();
	}
}
