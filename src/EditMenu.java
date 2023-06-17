import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class EditMenu extends RediID{
	private int choice;
	private String redi_ID;
	private int item_ID;
	private String item_Name;
	private int item_Price;
	private int default_Quantity;
	private String file_Location;
	
	public EditMenu() {
		this.setDefault_Quantity(Integer.MAX_VALUE);
		this.setFile_Location(Locations.getMenu_location());
	}

	public void edit(){
		System.out.println("Welcome to the Edit Menu Option.");
		System.out.println("1:Modify Existing Item  2:Add New Item");
		System.out.println("Select: ");
		int option;
		Scanner sc = new Scanner(System.in);
		option = sc.nextInt();
		setChoice(option);

		
		switch(getChoice()) {
			case 1:
				ShowMenu sm=new ShowMenu();
				sm.show(RediID.getId());
				modify();
					break;
			case 2:	add();
					break;
			default: System.out.println("Wrong Selection!!");
		}
	}

	private void modify() {
		System.out.println("Enter Details:");
		Scanner sc = new Scanner(System.in);
		String input1=null;
		int input2=0;

		setRedi_ID(RediID.getId());
		
		System.out.println("Enter the Item_ID: ");
		input2=sc.nextInt();
		setItem_ID(input2);
		
		System.out.println("Enter the Item_Name: ");
		input1=sc.next();
		setItem_Name(input1);
		
		System.out.println("Enter the old Item_Price: ");
		input2=sc.nextInt();
		setItem_Price(input2);
		
		System.out.println("Enter the old Quantity(Quanity_Max Enter 0; Not Available Enter -1): ");
		input2=sc.nextInt();
		setDefault_Quantity(input2);
		
		String oldContent = getRedi_ID()+","+getItem_ID()+","+getItem_Name()+","+getItem_Price()+",";
		
		if(getDefault_Quantity()==-1) oldContent += "-1";
		else oldContent += getDefault_Quantity();
		
		System.out.println("What you want to modify?");
		System.out.println("1:Price  2:Quantity");
		choice=sc.nextInt();
		
		switch(choice) {
			case 1: System.out.println("Enter the new Item_Price: ");
					input2=sc.nextInt();
					setItem_Price(input2);
					break;
			case 2: System.out.println("Enter the new Quantity(Quanity_Max Enter 0; Not Available Enter -1): ");
					input2=sc.nextInt();
					setDefault_Quantity(input2);
					break;
			default: System.out.println("Wrong Selection!!"); 
		}
		
		String newContent = getRedi_ID()+","+getItem_ID()+","+getItem_Name()+","+getItem_Price()+",";
		
		if(getDefault_Quantity()==-1) newContent += "-1";
		else newContent += getDefault_Quantity();
		
		boolean check = modifyItem(oldContent, newContent);
		
		if(check) System.out.println("Item Modified!!!");
		else System.out.println("No Such Data Exist");
        sc.close();
	}
	
	private void add() {
		System.out.println("Enter Details:");
		Scanner sc = new Scanner(System.in);
		String input1=null;
		int input2=0;
		setRedi_ID(RediID.getId());
		
		System.out.println("Enter the Item_ID: ");
		input2=sc.nextInt();
		setItem_ID(input2);
		
		System.out.println("Enter the Item_Name: ");
		input1=sc.next();
		setItem_Name(input1);
		
		System.out.println("Enter the Item_Price: ");
		input2=sc.nextInt();
		setItem_Price(input2);
		
		System.out.println("Enter the Quantity(Quanity_Max Enter 0; Not Available Enter -1): ");
		input2=sc.nextInt();
		setDefault_Quantity(input2);
		
		String content = getRedi_ID()+","+getItem_ID()+","+getItem_Name()+","+getItem_Price()+",";
		
		if(getDefault_Quantity()==-1) content+="-1";
		else content+=getDefault_Quantity();
		
		addItem(content);
		
        System.out.println("Item Added!!!");
        
        sc.close();
	}
	
	private void addItem(String itemData) {
		File path = new File(getFile_Location());
        BufferedReader reader = null;
        FileWriter writer=null;
        String content = "";
		try {
			reader = new BufferedReader(new FileReader(path));
			String line = reader.readLine();
			
            while (line != null){
	             content = content + line + System.lineSeparator();
	             line = reader.readLine();
	        }
            
            content = content + itemData + System.lineSeparator();
			writer=new FileWriter(path);
			writer.write(content);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
        finally {
        	try {
    			writer.flush();
    			writer.close();
    		} 
        	catch (IOException e) {
    			e.printStackTrace();
    		}
		}
	}
	
	private boolean modifyItem(String oldString, String newString){
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
                
             // to check if data entered exist or not
                if(!oldContent.contains(oldString)) return false;
                return true;
            } 
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        return true;
    }

	private String getFile_Location() {
		return file_Location;
	}

	private int getDefault_Quantity() {
		return default_Quantity;
	}

	private int getItem_Price() {
		return item_Price;
	}

	private String getItem_Name() {
		return item_Name;
	}

	private int getItem_ID() {
		return item_ID;
	}

	private String getRedi_ID() {
		return redi_ID;
	}

	private int getChoice() {
		return choice;
	}

	private void setFile_Location(String location) {
		file_Location = location;
		
	}
	
	private void setChoice(int option) {
		choice = option;
	}
	
	private void setDefault_Quantity(int input) {
		if(input==0) default_Quantity=Integer.MAX_VALUE;
		else default_Quantity=input;
	}

	private void setItem_Price(int input) {
		item_Price=input;
	}

	private void setItem_Name(String input) {
		item_Name=input;
	}

	private void setItem_ID(int input) {
		item_ID=input;
	}

	private void setRedi_ID(String input) {
		redi_ID=input;	
	}
	
	public static void main(String[] args) {
		EditMenu item = new EditMenu();
		item.edit();			
	}
}
