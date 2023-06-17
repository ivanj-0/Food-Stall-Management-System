import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Scanner;


public class StudentStats implements Stats {
    private String location;
    
    public StudentStats() {
		setLocation(Locations.getOrder_location());
	}
    
    public void display() {
    	System.out.println("Welcome to the Stats Option.");
    	System.out.println("1:Total Expenses  2:Expenses in Current Month  3:Amount Spent on Item A at Redi B");
		System.out.println("Select: ");
    	
		int option;
		Scanner sc = new Scanner(System.in);
		option = sc.nextInt();
		

		String student_ID = StudentID.getId();
		switch(option) {
			case 1: totalExpenses(student_ID);
					break;
			case 2:	System.out.println("Enter the Month(Numeral): ");
					int month = sc.nextInt();
					monthlyExpenses(student_ID, month);
					break;
			case 3: System.out.println("Enter the Item_ID: ");
					String item_ID = sc.next();
					System.out.println("Enter the Redi_ID: ");
					String redi_ID = sc.next();
					specificExpenses(student_ID,item_ID,redi_ID);
					break;
			default: System.out.println("Wrong Selection!!");
		}
    }
    
    private void totalExpenses(String studentid) {
        double spent = 0.0;
        try {
            File file = new File(getLocation());
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            String[] tempArr;
            while (line != null) {
                tempArr = line.split(",");
                if (tempArr[0].equals(studentid) && tempArr[8].equals("Accept")) {
                    spent += Double.parseDouble(tempArr[7]);
                }
                line = br.readLine();
            }
        } catch (Exception e) {
        }
        System.out.println("Money spent by student is Rupees " + spent);
    }

	private void specificExpenses(String student_ID, String item_ID, String redi_ID) {
        double spent = 0.0;
        try {
            File file = new File(getLocation());
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            String[] tempArr;
            while (line != null) {
                tempArr = line.split(",");
                if (tempArr[0].equals(student_ID) && tempArr[2].equalsIgnoreCase(redi_ID) && tempArr[3].equals(item_ID) && tempArr[8].equals("Accept")) {
                    spent += Double.parseDouble(tempArr[7]);
                }
                line = br.readLine();
            }
        } catch (Exception e) {
        }
        System.out.println("Money spent by student on this item at " + redi_ID + " Redi is Rupees " + spent);
    }

    private void monthlyExpenses(String student_ID, int month) {
        double spent = 0.0;
        try {
            File file = new File(getLocation());
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            String[] tempArr;
            
            while (line != null) {
                tempArr = line.split(",");
                if (tempArr[0].equals(student_ID) && tempArr[8].equals("Accept")) {
                	long timeStamp = Long.parseLong(tempArr[1]); //epoch time
                	int orderMonth = Instant.ofEpochMilli(timeStamp).atZone(ZoneId.systemDefault()).toLocalDate().getMonthValue();
                	
                	if(orderMonth == month) spent += Double.parseDouble(tempArr[7]);
                	
                }
                line = br.readLine();
            }
        } catch (Exception e) {
        }
        System.out.println("Money spent by student in this month is Rupees " + spent);
    }

	private String getLocation() {
		return location;
	}

	private void setLocation(String location) {
		this.location = location;
	}
	

}